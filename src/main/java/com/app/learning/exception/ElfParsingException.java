package com.app.learning.exception;

public class ElfParsingException extends Exception {

    public ElfParsingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ElfParsingException(final Throwable throwable) {
        super(throwable);
    }

    public ElfParsingException(final String s) {
        super(s);
    }
}
