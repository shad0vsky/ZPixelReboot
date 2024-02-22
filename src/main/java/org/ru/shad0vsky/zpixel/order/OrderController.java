package org.ru.shad0vsky.zpixel.order;

import lombok.Getter;
import lombok.NonNull;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.ru.shad0vsky.zpixel.ZPixelReboot;
import org.ru.shad0vsky.zpixel.player.PlayerEntity;

import java.util.HashSet;
import java.util.Set;

public class OrderController implements Listener {

    private final @NonNull JavaPlugin  plugin;
    private final @NonNull OrderReader reader;
    @Getter
    private final @NonNull Set<Player> failedOrderAttempts = new HashSet<>();

    public OrderController(@NonNull JavaPlugin plugin, @NonNull OrderReader reader) {
        this.plugin = plugin;
        this.reader = reader;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onConnection(@NonNull PlayerJoinEvent event) {

        if ( reader.orderModeEnforced ) {
            Player       player       = event.getPlayer();
            PlayerEntity playerEntity = PlayerEntityService.get(player);

            if ( playerEntity != null ) {

                return;
            }

            failedOrderAttempts.add (player);
            player             .kick(Component.text(reader.protectorMissingOrderMessage), PlayerKickEvent.Cause.PLUGIN);
            ZPixelReboot.getInstance().getLogger().info(reader.protectorMissingOrderMessageAlert);
        }
    }
}
