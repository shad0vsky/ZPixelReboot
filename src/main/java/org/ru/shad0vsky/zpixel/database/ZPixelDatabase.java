package org.ru.shad0vsky.zpixel.database;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@AllArgsConstructor
public final class ZPixelDatabase {

    private final String     url;
    private final String     username;
    private final String     password;
    private       Connection connection;

    public @NotNull Connection getConnection() throws SQLException {

        if ( connection == null ) {
            connection = DriverManager.getConnection(url, username, password);
        }

        return connection;
    }
}
