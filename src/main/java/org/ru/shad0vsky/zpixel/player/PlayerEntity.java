package org.ru.shad0vsky.zpixel.player;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.ru.shad0vsky.zpixel.database.ZPixelDatabaseEntity;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class PlayerEntity implements ZPixelDatabaseEntity {

    @Getter(value = AccessLevel.PRIVATE)
    private @NonNull  String  name;
    private @NonNull  String  firstname;
    private @NonNull  String  surname;
    private @Nullable String  alias;
    private @Nullable UUID    factionUUID;
    private           boolean isVerified;
    private           boolean isWhitelistIgnored;

    @Override
    public @NotNull String getID() {
        return name;
    }
}
