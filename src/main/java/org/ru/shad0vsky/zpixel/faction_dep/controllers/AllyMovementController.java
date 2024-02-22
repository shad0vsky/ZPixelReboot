package org.ru.shad0vsky.zpixel.faction_dep.controllers;

import lombok.NonNull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class AllyMovementController implements Listener {

    @EventHandler
    public void onMovement(@NonNull PlayerMoveEvent event) {
        // показывать игрока при движении
    }
}
