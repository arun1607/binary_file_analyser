package com.app.learning.handler;

import com.app.learning.core.FileType;

public class PEFileHandler implements FileHandler {

    private final String file;

    private final FileType fileType;

    public PEFileHandler(final String file, final FileType fileType) {
        this.file = file;
        this.fileType = fileType;
    }

    @Override
    public void extractMetaData() {

    }

    @Override
    public FileType getFileType() {
        return fileType;
    }
}
