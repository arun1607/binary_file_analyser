package com.app.learning.exception;

public class InvalidFileException extends ElfParsingException {
    public InvalidFileException(final String message, final Throwable cause) {
        super(message, cause);
    }


    public InvalidFileException(final String s) {
        super(s);
    }
}
