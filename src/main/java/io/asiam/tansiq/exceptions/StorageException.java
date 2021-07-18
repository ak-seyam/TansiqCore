package io.asiam.tansiq.exceptions;

public class StorageException extends RuntimeException{
    public  StorageException(String message) {
        super(message);
    }
    public StorageException(String message, Exception parentEx) {
       super(message + ":" + parentEx.getLocalizedMessage());
    }
}

