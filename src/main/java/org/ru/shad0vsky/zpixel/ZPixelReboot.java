package org.ru.shad0vsky.zpixel;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZPixelReboot extends JavaPlugin {

    @Getter
    private static ZPixelReboot instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
