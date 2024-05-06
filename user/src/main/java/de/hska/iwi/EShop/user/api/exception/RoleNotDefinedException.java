package de.hska.iwi.EShop.user.api.exception;

public class RoleNotDefinedException extends RuntimeException{

    public RoleNotDefinedException() {
        super("No Regular User Role was found.");
    }
}
