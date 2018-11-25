package com.app.learning.handler;

import com.app.learning.exception.ElfParsingException;
import com.app.learning.exception.InvalidFileException;

public interface FileAnalyser {

    FileHandler getFileHandler(String filePath) throws ElfParsingException, InvalidFileException;
}
