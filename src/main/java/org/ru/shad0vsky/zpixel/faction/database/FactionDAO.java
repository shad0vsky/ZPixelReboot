package org.ru.shad0vsky.zpixel.faction.database;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.ru.shad0vsky.zpixel.ZPixelReboot;
import org.ru.shad0vsky.zpixel.database.ZPixelDatabaseDAO;
import org.ru.shad0vsky.zpixel.utilities.Locator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FactionDAO implements ZPixelDatabaseDAO<FactionEntity> {

    private static final    String     CREATE_QUERY     = "CREATE TABLE IF NOT EXISTS factions (name VARCHAR(16) PRIMARY KEY, uuid VARCHAR(36) NOT NULL, display_name VARCHAR(16), display_color CHAR(1), allies TEXT, world VARCHAR(16), x DOUBLE, y DOUBLE, z DOUBLE, yaw FLOAT, pitch FLOAT, faction_members_count INT, faction_killed_enemies_count INT, faction_killed_members_count INT, faction_killed_allies_count INT)";
    private static final    String     INSERT_QUERY     = "INSERT INTO factions (name, uuid, display_name, display_color, allies, world, x, y, z, yaw, pitch, faction_members_count, faction_killed_enemies_count, faction_killed_members_count, faction_killed_allies_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final    String     UPDATE_QUERY     = "UPDATE factions SET display_name = ?, display_color = ?, allies = ?, world = ?, x = ?, y = ?, z = ?, yaw = ?, pitch = ?, faction_members_count = ?, faction_killed_enemies_count = ?, faction_killed_members_count = ?, faction_killed_allies_count = ? WHERE name = ?";
    private static final    String     DELETE_QUERY     = "DELETE FROM factions WHERE name = ?";
    private static final    String     SELECT_ONE_QUERY = "SELECT * FROM factions WHERE name = ?";
    private static final    String     SELECT_ALL_QUERY = "SELECT * FROM factions";
    private static volatile FactionDAO instance;

    static {
        try ( Statement statement = ZPixelReboot.getInstance().getDatabase().getConnection().createStatement() ) {
            statement.executeUpdate(CREATE_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private FactionDAO() {

    }

    public static FactionDAO getInstance() {
        if ( instance == null ) {
            synchronized (FactionDAO.class) {
                if ( instance == null ) {
                    instance = new FactionDAO();
                }
            }
        }
        return instance;
    }

    @Override
    public void create(@NonNull FactionEntity entity) {
        try ( PreparedStatement statement = ZPixelReboot.getInstance().getDatabase().getConnection().prepareStatement(INSERT_QUERY) ) {
            statement.setString(1, entity.getID());
            statement.setString(2, entity.getUUID().toString());
            statement.setString(3, entity.getDisplayName());
            statement.setString(4, String.valueOf(entity.getDisplayColor()));
            statement.setString(5, String.join(",", entity.getAllies()));
            statement.setString(6, entity.getBaseLocation().getWorld().getName());
            statement.setDouble(7, entity.getBaseLocation().getX());
            statement.setDouble(8, entity.getBaseLocation().getY());
            statement.setDouble(9, entity.getBaseLocation().getZ());
            statement.setFloat(10, entity.getBaseLocation().getYaw());
            statement.setFloat(11, entity.getBaseLocation().getPitch());
            statement.setInt(12, entity.getFactionMembersCount());
            statement.setInt(13, entity.getFactionKilledEnemiesCount());
            statement.setInt(14, entity.getFactionKilledMembersCount());
            statement.setInt(15, entity.getFactionKilledAlliesCount());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(@NonNull FactionEntity entity) {
        try ( PreparedStatement statement = ZPixelReboot.getInstance().getDatabase().getConnection().prepareStatement(UPDATE_QUERY) ) {
            statement.setString(1, entity.getDisplayName());
            statement.setString(2, String.valueOf(entity.getDisplayColor()));
            statement.setString(3, String.join(",", entity.getAllies()));
            statement.setString(4, entity.getBaseLocation().getWorld().getName());
            statement.setDouble(5, entity.getBaseLocation().getX());
            statement.setDouble(6, entity.getBaseLocation().getY());
            statement.setDouble(7, entity.getBaseLocation().getZ());
            statement.setFloat(8, entity.getBaseLocation().getYaw());
            statement.setFloat(9, entity.getBaseLocation().getPitch());
            statement.setInt(10, entity.getFactionMembersCount());
            statement.setInt(11, entity.getFactionKilledEnemiesCount());
            statement.setInt(12, entity.getFactionKilledMembersCount());
            statement.setInt(13, entity.getFactionKilledAlliesCount());
            statement.setString(14, entity.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(@NonNull FactionEntity entity) {
        try ( PreparedStatement statement = ZPixelReboot.getInstance().getDatabase().getConnection().prepareStatement(DELETE_QUERY) ) {
            statement.setString(1, entity.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FactionEntity get(@NonNull String entityID) {
        try ( PreparedStatement statement = ZPixelReboot.getInstance().getDatabase().getConnection().prepareStatement(SELECT_ONE_QUERY) ) {
            statement.setString(1, entityID);
            ResultSet resultSet = statement.executeQuery();
            if ( resultSet.next() ) {
                String       name                      = resultSet.getString("name");
                UUID         uuid                      = UUID.fromString(resultSet.getString("uuid"));
                String       displayName               = resultSet.getString("display_name");
                char         displayColor              = resultSet.getString("display_color").charAt(0);
                List<String> allies                    = Arrays.asList(resultSet.getString("allies").split(","));
                Location     baseLocation              = new Location(Bukkit.getWorld(resultSet.getString("world")), resultSet.getDouble("x"), resultSet.getDouble("y"), resultSet.getDouble("z"), resultSet.getFloat("yaw"), resultSet.getFloat("pitch"));
                int          factionMembersCount       = resultSet.getInt("faction_members_count");
                int          factionKilledEnemiesCount = resultSet.getInt("faction_killed_enemies_count");
                int          factionKilledMembersCount = resultSet.getInt("faction_killed_members_count");
                int          factionKilledAlliesCount  = resultSet.getInt("faction_killed_allies_count");
                return new FactionEntity(name, uuid, displayName, displayColor, allies, baseLocation, factionMembersCount, factionKilledEnemiesCount, factionKilledMembersCount, factionKilledAlliesCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FactionEntity> get() {
        List<FactionEntity> factions = new ArrayList<>();
        try ( PreparedStatement statement = ZPixelReboot.getInstance().getDatabase().getConnection().prepareStatement(SELECT_ALL_QUERY) ) {
            ResultSet resultSet = statement.executeQuery();
            while ( resultSet.next() ) {
                String       name                      = resultSet.getString("name");
                UUID         uuid                      = UUID.fromString(resultSet.getString("uuid"));
                String       displayName               = resultSet.getString("display_name");
                char         displayColor              = resultSet.getString("display_color").charAt(0);
                List<String> allies                    = Arrays.asList(resultSet.getString("allies").split(","));
                Location     baseLocation              = Locator.createLocationFromResultSet(resultSet);
                int          factionMembersCount       = resultSet.getInt("faction_members_count");
                int          factionKilledEnemiesCount = resultSet.getInt("faction_killed_enemies_count");
                int          factionKilledMembersCount = resultSet.getInt("faction_killed_members_count");
                int          factionKilledAlliesCount  = resultSet.getInt("faction_killed_allies_count");

                FactionEntity faction = new FactionEntity(name, uuid, displayName, displayColor, allies, baseLocation, factionMembersCount, factionKilledEnemiesCount, factionKilledMembersCount, factionKilledAlliesCount);
                factions.add(faction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return factions;
    }
}
