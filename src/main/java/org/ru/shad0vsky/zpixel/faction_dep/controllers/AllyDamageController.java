package org.ru.shad0vsky.zpixel.faction_dep.controllers;

import lombok.NonNull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class AllyDamageController implements Listener {

    @EventHandler
    public void onDamage(@NonNull EntityDamageByEntityEvent event) {

    }

    @EventHandler
    public void onDeath(@NonNull PlayerDeathEvent event) {

    }
}
