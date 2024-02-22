package org.ru.shad0vsky.zpixel.faction.database;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.ru.shad0vsky.zpixel.database.ZPixelDatabaseEntity;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class FactionEntity implements ZPixelDatabaseEntity {

    @Getter(value = AccessLevel.PRIVATE)
    private final String name;
    private final UUID   UUID;
    private       String displayName;
    private       char   displayColor;

    @Override
    public @NotNull String getID() {
        return name;
    }
}
