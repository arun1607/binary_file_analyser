package com.app.learning.handler;

import com.app.learning.core.FileType;
import com.app.learning.exception.ElfParsingException;

public interface FileHandler {

    void extractMetaData() throws ElfParsingException;

    FileType getFileType();
}
