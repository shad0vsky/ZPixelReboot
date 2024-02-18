package org.ru.shad0vsky.zpixel.player;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

@UtilityClass
public final class PlayerEntityService {

    public static PlayerEntity get(@NonNull Player player) {
        String          playerName      = player.getName();
        PlayerEntityDAO playerEntityDAO = new PlayerEntityDAO();
        return playerEntityDAO.get(playerName);
    }
}
