package com.louislivi.fastdep.file;

/**
 * FileNotFoundException
 *
 * @author : louislivi
 */
public class FileNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
