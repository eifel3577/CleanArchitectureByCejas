package com.example.cleanarchitecturebycejas.Domain.Extension;

/**
 * Interface to represent a wrapper around an {@link java.lang.Exception} to manage errors.
 */
public interface ErrorBundle {
    Exception getException();

    String getErrorMessage();
}