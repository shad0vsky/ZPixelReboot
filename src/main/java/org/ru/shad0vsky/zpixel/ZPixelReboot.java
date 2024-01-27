package org.ru.shad0vsky.zpixel;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.ru.shad0vsky.zpixel.database.ZPixelDatabase;

public final class ZPixelReboot extends JavaPlugin {

    @Getter
    private static ZPixelReboot   instance;
    @Getter
    private        ZPixelDatabase database;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        String url      = getConfig().getString("database.url");
        String username = getConfig().getString("database.username");
        String password = getConfig().getString("database.password");
        instance = this;
        database = new ZPixelDatabase(url, username, password, null);
    }

    @Override
    public void onDisable() {
        instance = null;
        database = null;
    }
}
