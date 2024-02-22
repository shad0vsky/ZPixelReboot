package org.ru.shad0vsky.zpixel.faction_dep.gui.listeners;

import com.samjakob.spigui.buttons.SGButtonListener;
import lombok.AllArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.ru.shad0vsky.zpixel.faction.database.FactionDAO;
import org.ru.shad0vsky.zpixel.faction.database.FactionEntity;
import org.ru.shad0vsky.zpixel.player.PlayerEntity;
import org.ru.shad0vsky.zpixel.player.PlayerEntityDAO;

import java.util.UUID;

@AllArgsConstructor
public class FactionButtonListener implements SGButtonListener {

    private final String factionName;

    @Override
    public void onClick(InventoryClickEvent event) {
        if ( event.getWhoClicked() instanceof Player player ) {

            String       playerEntityID = player.getName();
            PlayerEntity playerEntity   = PlayerEntityDAO.getInstance().get(playerEntityID);
            if ( playerEntity == null ) {
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 1, 1);
                player.closeInventory();
                return;
            }

            FactionEntity factionEntity = FactionDAO.getInstance().get(factionName);
            if ( factionEntity == null ) {
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 1, 1);
                player.closeInventory();
                return;
            }

            UUID factionUUID = factionEntity.getUUID();
            playerEntity   .setFactionUUID      (factionUUID);
            PlayerEntityDAO.getInstance().update(playerEntity);
        }
    }

}
