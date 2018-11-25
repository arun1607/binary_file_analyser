package com.app.learning.core;

import com.app.learning.exception.ElfParsingException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Logger;

import static com.app.learning.core.Constants.*;

public class ElfParser {

    private static final Logger LOGGER = Logger.getLogger(ElfParser.class.getName());
    private byte fileClass;
    private byte fileEncoding;


    private final ByteArrayInputStream byteArrayInputStream;
    private byte elfVersion;
    private short fileType;
    private short supportedMachineArchitecture;
    private int fileVersion;
    private long entryPoint;
    private long programHeaderOffset;
    private short elfHeaderSize;
    private short programEntryHeaderSize;
    private short numberOfSectionHeader;
    private short numberOfProgramHeader;
    private short sectionHeaderEntrySize;


    public ElfParser(final byte[] data) {
        byteArrayInputStream = new ByteArrayInputStream(data);
    }

    public void parse() throws ElfParsingException {
        byte[] ident = new byte[16];
        try {
            read(ident);
            if (!(0x7f == ident[0] && 'E' == ident[1] && 'L' == ident[2] && 'F' == ident[3]))
                throw new ElfParsingException("File parsing not supported");

            fileClass = ident[4];
            if (!(fileClass == CLASS_32 || fileClass == CLASS_64)) {
                throw new ElfParsingException("Invalid object size class: " + fileClass);
            }
            if (fileClass == CLASS_32) {
                LOGGER.info("File is 32 bit");
            } else {
                LOGGER.info("File is 64 bit");
            }

            fileEncoding = ident[5];
            if (!(fileEncoding == DATA_LSB || fileEncoding == DATA_MSB))
                throw new ElfParsingException("Invalid fileEncoding: " + fileEncoding);

            if (fileEncoding == DATA_LSB) {
                LOGGER.info("File is little endian encoded");
            } else {
                LOGGER.info("File is big endian encoded");
            }

            elfVersion = ident[6];
            if (elfVersion != 1) throw new ElfParsingException("Invalid elf version: " + elfVersion);

            LOGGER.info(String.format("ELF version is : %s", elfVersion));

            // Buffer data. To be ignored.
            // ident[7];
            // ident[8];
            // ident[9-15]

            fileType = readShort();

            if (fileType == FT_REL) {
                LOGGER.info("File is relocatable type");
            } else if (fileType == FT_EXEC) {
                LOGGER.info("File is executable type");
            } else if (fileType == FT_DYN) {
                LOGGER.info("File is shared object type");
            } else if (fileType == FT_CORE) {
                LOGGER.info("File is core type");
            } else {
                LOGGER.info("Unknown file type");
            }

            supportedMachineArchitecture = readShort();

            ArchitectureType architectureType = ArchitectureType.getInstance(supportedMachineArchitecture);
            if (architectureType != null)
                LOGGER.info(String.format("Supported architecture is %s", architectureType.toString()));

            fileVersion = readInt();

            LOGGER.info(String.format("File version is : %d", fileVersion));

            entryPoint = readIntOrLong();

            LOGGER.info(String.format("Entry point address : %d", entryPoint));


            programHeaderOffset = readIntOrLong();

            LOGGER.info(String.format("Program header offset is %d", programHeaderOffset));

            final long sectionHeaderOffset = readIntOrLong();

            LOGGER.info(String.format("Section header offset is %d", sectionHeaderOffset));

            readInt();

            elfHeaderSize = readShort();

            LOGGER.info(String.format("ELF header size is %d ", elfHeaderSize));

            programEntryHeaderSize = readShort();

            LOGGER.info(String.format("Program entry header size is %d ", programEntryHeaderSize));
            numberOfProgramHeader = readShort();

            LOGGER.info(String.format("Number of program header are %d ", numberOfProgramHeader));
            sectionHeaderEntrySize = readShort();

            LOGGER.info(String.format("Section entry header size ", sectionHeaderEntrySize));
            numberOfSectionHeader = readShort();

            if (numberOfSectionHeader == 0) {
                throw new ElfParsingException("e_shnum is SHN_UNDEF(0), which is not supported yet"
                        + " (the actual number of section header table entries is contained in the sh_size field of the section header at index 0)");
            }

            LOGGER.info(String.format("Number of section header are %d ", numberOfSectionHeader));


        } catch (IOException e) {
            throw new ElfParsingException("Error occurred in processing file", e);
        }

    }

    /**
     * Signed byte utility functions used for converting from big-endian (MSB) to little-endian (LSB).
     */
    private short byteSwap(short arg) {
        return (short) ((arg << 8) | ((arg >>> 8) & 0xFF));
    }

    private int byteSwap(int arg) {
        return ((byteSwap((short) arg)) << 16) | (((byteSwap((short) (arg >>> 16)))) & 0xFFFF);
    }

    private long byteSwap(long arg) {
        return ((((long) byteSwap((int) arg)) << 32) | (((long) byteSwap((int) (arg >>> 32))) & 0xFFFFFFFF));
    }

    private short readUnsignedByte() throws ElfParsingException {
        int val = byteArrayInputStream.read();
        if (val < 0) throw new ElfParsingException("Trying to read outside file");
        return (short) val;
    }

    private short readShort() throws ElfParsingException {
        int ch1 = readUnsignedByte();
        int ch2 = readUnsignedByte();
        short val = (short) ((ch1 << 8) + (ch2 << 0));
        if (fileEncoding == DATA_LSB) val = byteSwap(val);
        return val;
    }

    private int readInt() throws ElfParsingException {
        int ch1 = readUnsignedByte();
        int ch2 = readUnsignedByte();
        int ch3 = readUnsignedByte();
        int ch4 = readUnsignedByte();
        int val = ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));

        if (fileEncoding == DATA_LSB) val = byteSwap(val);
        return val;
    }

    private long readLong() throws ElfParsingException {
        int ch1 = readUnsignedByte();
        int ch2 = readUnsignedByte();
        int ch3 = readUnsignedByte();
        int ch4 = readUnsignedByte();
        int val1 = ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
        int ch5 = readUnsignedByte();
        int ch6 = readUnsignedByte();
        int ch7 = readUnsignedByte();
        int ch8 = readUnsignedByte();
        int val2 = ((ch5 << 24) + (ch6 << 16) + (ch7 << 8) + (ch8 << 0));

        long val = ((long) (val1) << 32) + (val2 & 0xFFFFFFFFL);
        if (fileEncoding == DATA_LSB) val = byteSwap(val);
        return val;
    }

    /**
     * Read four-byte int or eight-byte long.
     */
    private long readIntOrLong() throws ElfParsingException {
        return fileClass == CLASS_32 ? readInt() : readLong();
    }

    /**
     * Returns a big-endian unsigned representation of the int.
     */
    private long unsignedByte(int arg) {
        long val;
        if (arg >= 0) {
            val = arg;
        } else {
            val = (unsignedByte((short) (arg >>> 16)) << 16) | ((short) arg);
        }
        return val;
    }


    private void read(byte[] data) throws IOException {
        byteArrayInputStream.read(data);
    }

    public byte getFileClass() {
        return fileClass;
    }

    public byte getFileEncoding() {
        return fileEncoding;
    }

    public ByteArrayInputStream getByteArrayInputStream() {
        return byteArrayInputStream;
    }

    public byte getElfVersion() {
        return elfVersion;
    }

    public short getFileType() {
        return fileType;
    }

    public short getSupportedMachineArchitecture() {
        return supportedMachineArchitecture;
    }

    public int getFileVersion() {
        return fileVersion;
    }

    public long getEntryPoint() {
        return entryPoint;
    }

    public long getProgramHeaderOffset() {
        return programHeaderOffset;
    }

    public short getElfHeaderSize() {
        return elfHeaderSize;
    }

    public short getProgramEntryHeaderSize() {
        return programEntryHeaderSize;
    }

    public short getNumberOfSectionHeader() {
        return numberOfSectionHeader;
    }

    public short getNumberOfProgramHeader() {
        return numberOfProgramHeader;
    }

    public short getSectionHeaderEntrySize() {
        return sectionHeaderEntrySize;
    }
}
