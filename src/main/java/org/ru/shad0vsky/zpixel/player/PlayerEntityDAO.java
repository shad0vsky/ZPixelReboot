package org.ru.shad0vsky.zpixel.player;

import lombok.NonNull;
import org.ru.shad0vsky.zpixel.ZPixelReboot;
import org.ru.shad0vsky.zpixel.database.ZPixelDatabaseDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerEntityDAO implements ZPixelDatabaseDAO<PlayerEntity> {

    private static final String CREATE_QUERY  = "CREATE TABLE IF NOT EXISTS players (name VARCHAR(16) PRIMARY KEY, firstname VARCHAR(16) NOT NULL, surname VARCHAR(16), alias VARCHAR(16), factionUUID VARCHAR(36), isVerified BOOLEAN, isWhitelistIgnored BOOLEAN)";
    private static final String INSERT_QUERY  = "INSERT INTO players (name, firstname, surname, alias, factionUUID, isVerified, isWhitelistIgnored) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY  = "UPDATE players SET firstname = ?, surname = ?, alias = ?, factionUUID = ?, isVerified = ?, isWhitelistIgnored = ? WHERE name = ?";
    private static final String DELETE_QUERY  = "DELETE FROM players WHERE name = ?";
    private static final String GET_ONE_QUERY = "SELECT * FROM players WHERE name = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM players";

    static {
        try ( Statement statement = ZPixelReboot.getInstance().getDatabase().getConnection().createStatement() ) {
            statement.executeUpdate(CREATE_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(@NonNull PlayerEntity entity) {
        try ( PreparedStatement statement = ZPixelReboot.getInstance().getDatabase().getConnection().prepareStatement(INSERT_QUERY) ) {
            statement.setString(1, entity.getID());
            statement.setString(2, entity.getFirstname());
            statement.setString(3, entity.getSurname());
            statement.setString(4, entity.getAlias());
            statement.setString(5, entity.getFactionUUID().toString());
            statement.setBoolean(6, entity.isVerified());
            statement.setBoolean(7, entity.isWhitelistIgnored());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(@NonNull PlayerEntity entity) {
        try ( PreparedStatement statement = ZPixelReboot.getInstance().getDatabase().getConnection().prepareStatement(UPDATE_QUERY) ) {
            statement.setString(1, entity.getFirstname());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getAlias());
            statement.setString(4, entity.getFactionUUID().toString());
            statement.setBoolean(5, entity.isVerified());
            statement.setBoolean(6, entity.isWhitelistIgnored());
            statement.setString(7, entity.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(@NonNull PlayerEntity entity) {
        try ( PreparedStatement statement = ZPixelReboot.getInstance().getDatabase().getConnection().prepareStatement(DELETE_QUERY) ) {
            statement.setString(1, entity.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PlayerEntity get(@NonNull String entityID) {
        PlayerEntity playerEntity = null;

        try ( PreparedStatement statement = ZPixelReboot.getInstance().getDatabase().getConnection().prepareStatement(GET_ONE_QUERY) ) {
            statement.setString(1, entityID);

            ResultSet resultSet = statement.executeQuery();

            if ( resultSet.next() ) {
                playerEntity = mapResultSetToPlayerEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerEntity;
    }

    @Override
    public List<PlayerEntity> get() {
        List<PlayerEntity> playerEntities = new ArrayList<>();

        try ( Statement statement = ZPixelReboot.getInstance().getDatabase().getConnection().createStatement();
              ResultSet resultSet = statement.executeQuery(GET_ALL_QUERY) ) {

            while ( resultSet.next() ) {
                PlayerEntity playerEntity = mapResultSetToPlayerEntity(resultSet);
                playerEntities.add(playerEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerEntities;
    }

    private PlayerEntity mapResultSetToPlayerEntity(ResultSet resultSet) throws SQLException {
        String  name               = resultSet.getString("name");
        String  firstname          = resultSet.getString("firstname");
        String  surname            = resultSet.getString("surname");
        String  alias              = resultSet.getString("alias");
        UUID    factionUUID        = UUID.fromString(resultSet.getString("factionUUID"));
        boolean isVerified         = resultSet.getBoolean("isVerified");
        boolean isWhitelistIgnored = resultSet.getBoolean("isWhitelistIgnored");

        return new PlayerEntity(name, firstname, surname, alias, factionUUID, isVerified, isWhitelistIgnored);
    }
}