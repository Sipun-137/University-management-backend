package com.sipun.UniversityBackend.faculty;

public class NoAvailableFacultyException extends RuntimeException {
    public NoAvailableFacultyException(String message) {
        super(message);
    }
}
