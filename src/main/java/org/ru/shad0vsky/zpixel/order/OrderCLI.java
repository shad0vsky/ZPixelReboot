package org.ru.shad0vsky.zpixel.order;

import fr.xephi.authme.api.v3.AuthMeApi;
import lombok.NonNull;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Utility;
import org.bukkit.command.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.ru.shad0vsky.zpixel.ZPixelReboot;
import org.ru.shad0vsky.zpixel.faction.database.FactionDAO;
import org.ru.shad0vsky.zpixel.faction.database.FactionEntity;
import org.ru.shad0vsky.zpixel.player.PlayerEntity;
import org.ru.shad0vsky.zpixel.player.PlayerEntityDAO;

import java.util.*;
import java.util.stream.Stream;

public class OrderCLI implements CommandExecutor, TabCompleter {

    private final @NonNull JavaPlugin      plugin;
    private final @NonNull OrderReader     reader;
    private final @NonNull OrderController controller;

    public OrderCLI(@NonNull JavaPlugin plugin, @NonNull OrderReader reader, @NonNull OrderController controller) {
        this.plugin     = plugin;
        this.reader     = reader;
        this.controller = controller;
        Bukkit.getPluginCommand("order").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if ( sender instanceof Player || sender instanceof BlockCommandSender ) {
            plugin.getLogger().info(reader.cliCommandSentMessage);
            return true;
        }

        if ( args.length != 9 ) {
            plugin.getLogger().info(reader.cliInvalidArgumentsMessage);
            return true;
        }

        String firstname = args[1];
        String surname   = args[2];
        String alias     = args[3].equalsIgnoreCase("null") ? null : args[3];
        if ( invalidName(firstname) || invalidName(surname) || invalidAlias(alias) ) {
            ZPixelReboot.getInstance().getLogger().warning("Invalid names");
            return true;
        }

        String        factionName   = args[4].toLowerCase();
        FactionEntity factionEntity = FactionDAO.getInstance().get(factionName);
        if ( factionEntity == null ) {
            plugin.getLogger().info(reader.cliInvalidFactionMessage);
            return true;
        }

        String password       = args[7];
        String passwordRepeat = args[8];
        if ( !Objects.equals(password, passwordRepeat) ) {
            plugin.getLogger().info(reader.cliPasswordsDoNotMatchMessage);
            return true;
        }

        registerEntity  (args[0], firstname, surname, alias, factionEntity.getUUID(), Boolean.parseBoolean(args[5]), Boolean.parseBoolean(args[6]));
        registerPassword(args[0], password);
        plugin.getLogger().info(reader.cliPlayerRegisteredMessage);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return switch ( args.length - 1 ) {
            case 0    -> matching(controller.getFailedOrderAttempts().stream().map(HumanEntity::getName), args[0]);
            case 1    -> List.of("f");
            case 2    -> List.of("s");
            case 3    -> List.of("a");
            case 4    -> matching(FactionDAO.getInstance().get().stream().map(FactionEntity::getID), args[4]);
            case 5, 6 -> Arrays.asList("false", "true");
            case 7    -> List.of(RandomStringUtils.random(new Random().nextInt(10, 15), true, true));
            case 8    -> List.of(args[7]);
            default -> null;
        };
    }

    private void registerEntity(String name, String firstname, String surname, String alias, UUID factionUUID, boolean isVerified, boolean isWhitelistIgnored) {
        PlayerEntityDAO entityDAO = new PlayerEntityDAO();
        PlayerEntity    entity    = new PlayerEntity(name, firstname, surname, alias, factionUUID, isVerified, isWhitelistIgnored);
        entityDAO.create(entity);
    }

    private void registerPassword(String name, String password) {
        AuthMeApi.getInstance().registerPlayer(name, password);
    }

    @Utility
    private List<String> matching(Stream<String> stream, String arg) {
        return stream.filter(s -> s.startsWith(arg)).toList();
    }

    @Utility
    private boolean invalidName(@NonNull String name) {
        return !name.matches("[\\p{IsCyrillic}-]+");
    }

    @Utility
    private boolean invalidAlias(@Nullable String alias) {
        return alias != null && !alias.matches("^[\\p{IsCyrillic}\\p{IsLatin}-]+$");
    }
}