package org.ru.shad0vsky.zpixel;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.ru.shad0vsky.zpixel.database.ZPixelDatabase;
import org.ru.shad0vsky.zpixel.order.OrderCLI;
import org.ru.shad0vsky.zpixel.order.OrderController;
import org.ru.shad0vsky.zpixel.order.OrderReader;

import java.io.File;

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

        // order setup
        OrderReader     reader     = new OrderReader    ("order" + File.separator + "order.yml");
        OrderController controller = new OrderController(this, reader);
        OrderCLI        cli        = new OrderCLI       (this, reader, controller);
    }

    @Override
    public void onDisable() {
        instance = null;
        database = null;
    }
}
