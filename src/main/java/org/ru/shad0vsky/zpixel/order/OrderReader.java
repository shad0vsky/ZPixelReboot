package org.ru.shad0vsky.zpixel.order;

import lombok.NonNull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ru.shad0vsky.zpixel.ZPixelReboot;

import java.io.File;

public class OrderReader {

    protected final boolean orderModeEnforced;
    protected final String  protectorMissingOrderMessage;
    protected final String  protectorMissingOrderMessageAlert;
    protected final String  cliCommandSentMessage;
    protected final String  cliInvalidArgumentsMessage;
    protected final String  cliInvalidNamesMessage;
    protected final String  cliInvalidFactionMessage;
    protected final String  cliPasswordsDoNotMatchMessage;
    protected final String  cliPlayerRegisteredMessage;

    public OrderReader(@NonNull String path) {
        ZPixelReboot.getInstance().saveResource(path, false);
        File              file              = new File(ZPixelReboot.getInstance().getDataFolder(), path);
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        this.orderModeEnforced                       = fileConfiguration.getBoolean("order_mode");
        this.protectorMissingOrderMessage            = fileConfiguration.getString("message.protector.missing_order");
        this.protectorMissingOrderMessageAlert       = fileConfiguration.getString("message.protector.missing_order_alert");
        this.cliCommandSentMessage                   = fileConfiguration.getString("message.cli.command_sent");
        this.cliInvalidArgumentsMessage              = fileConfiguration.getString("message.cli.invalid_arguments");
        this.cliInvalidNamesMessage                  = fileConfiguration.getString("message.cli.invalid_names");
        this.cliInvalidFactionMessage                = fileConfiguration.getString("message.cli.invalid_faction");
        this.cliPasswordsDoNotMatchMessage           = fileConfiguration.getString("message.cli.passwords_do_not_match");
        this.cliPlayerRegisteredMessage              = fileConfiguration.getString("message.cli.player_registered");
    }
}
