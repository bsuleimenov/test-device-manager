package kz.company.testdevicemanager.common.valueobject;

import java.util.Objects;

/**
 * Represents a user with a username.
 */
public record User(String username) {

    /**
     * Constructs a new User object with the specified username.
     *
     * @param username The username of the user.
     * @return A new User object.
     * @throws NullPointerException if username is null.
     */
    public static User of(String username) {
        Objects.requireNonNull(username, "Username must not be null");

        return new User(username);
    }
}
