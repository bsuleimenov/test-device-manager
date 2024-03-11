package kz.company.testdevicemanager.common;

import kz.company.testdevicemanager.common.valueobject.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUser {

    // Private constructor to prevent instantiation
    private AuthenticatedUser() {}

    // Method to retrieve the currently authenticated user along with roles
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            return new User(username);
        }

        // No authenticated user, throw an exception
        throw new IllegalStateException("No authenticated user found");
    }
}
