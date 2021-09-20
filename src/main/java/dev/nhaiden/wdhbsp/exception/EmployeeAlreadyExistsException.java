package dev.nhaiden.wdhbsp.exception;

public class EmployeeAlreadyExistsException extends RuntimeException{
    public EmployeeAlreadyExistsException(String msg) {
        super(msg);
    }
}
