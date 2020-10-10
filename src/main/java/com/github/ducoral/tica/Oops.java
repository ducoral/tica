package com.github.ducoral.tica;

public class Oops extends RuntimeException {

    public Oops(String message, Object... arguments) {
        super(String.format(message, arguments));
    }

    public Oops(String message, Throwable cause, Object... arguments) {
        super(String.format(message, arguments), cause);
    }
}
