package com.app.learning.handler;

import com.app.learning.core.FileType;
import com.app.learning.exception.ElfParsingException;
import com.app.learning.exception.InvalidFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class BinaryFileAnalyser implements FileAnalyser {

    private static final Logger LOGGER = Logger.getLogger(BinaryFileAnalyser.class.getName());

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public FileHandler getFileHandler(final String filePath) throws ElfParsingException {
        byte[] buffer = new byte[16];
        try (FileInputStream in = new FileInputStream(new File(filePath))) {

            int read = in.read(buffer, 0, buffer.length);
            if (read == -1) {
                throw new InvalidFileException("File doesn't have data");
            }
        } catch (FileNotFoundException e) {
            throw new ElfParsingException("File not found", e);
        } catch (IOException e) {
            throw new ElfParsingException(e);
        }

        FileType fileType = null;

        if (0x7f == buffer[0] && 'E' == buffer[1] && 'L' == buffer[2] && 'F' == buffer[3]) {
            fileType = FileType.ELF;
            LOGGER.info("ELF file detected.");
        } else if ('M' == buffer[0] && 'Z' == buffer[1]) {
            fileType = FileType.PE;
            LOGGER.info("PE file detected.");
        } else
            throw new InvalidFileException("Bad magic number for file");

        FileHandler fileHandler = applicationContext.getBean(FileHandler.class, fileType, filePath);
        return fileHandler;
    }
}
