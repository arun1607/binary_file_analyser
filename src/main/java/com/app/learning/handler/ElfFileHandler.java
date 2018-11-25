package com.app.learning.handler;

import com.app.learning.core.ElfParser;
import com.app.learning.core.FileType;
import com.app.learning.exception.ElfParsingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ElfFileHandler implements FileHandler {

    @Autowired
    private ApplicationContext applicationContext;

    private final String file;
    private final FileType fileType;


    public ElfFileHandler(final String file, final FileType fileType) {
        this.file = file;
        this.fileType = fileType;
    }

    @Override
    public void extractMetaData() throws ElfParsingException {
        loadFile();
    }

    private void loadFile() throws ElfParsingException {
        final byte[] buffer = new byte[(int) file.length()];
        try (FileInputStream in = new FileInputStream(file)) {
            int totalRead = 0;
            while (totalRead < buffer.length) {
                int readNow = in.read(buffer, totalRead, buffer.length - totalRead);
                if (readNow == -1) {
                    throw new ElfParsingException("Premature end of file");
                } else {
                    totalRead += readNow;
                }
            }

            ElfParser elfParser = applicationContext.getBean(ElfParser.class, buffer);
            elfParser.parse();
        } catch (FileNotFoundException e) {
            throw new ElfParsingException("File not found", e);
        } catch (IOException e) {
            throw new ElfParsingException(e);
        }

    }

    @Override
    public FileType getFileType() {
        return fileType;
    }
}
