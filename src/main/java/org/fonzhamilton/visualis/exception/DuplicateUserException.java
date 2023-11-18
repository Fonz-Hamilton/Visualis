package org.fonzhamilton.visualis.exception;

public class DuplicateUserException extends RuntimeException{
    private final String emailMessage;
    private final String usernameMessage;

    public DuplicateUserException(String emailMessage, String usernameMessage) {
        super("Duplicate user or email");
        this.emailMessage = emailMessage;
        this.usernameMessage = usernameMessage;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public String getUsernameMessage() {
        return usernameMessage;
    }
}
