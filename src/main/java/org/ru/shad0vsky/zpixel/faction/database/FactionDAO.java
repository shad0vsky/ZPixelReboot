package org.ru.shad0vsky.zpixel.faction.database;

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

public class FactionDAO implements ZPixelDatabaseDAO<FactionEntity> {

    private static final    String     CREATE_QUERY    = "CREATE TABLE IF NOT EXISTS factions (name VARCHAR(16) PRIMARY KEY, uuid VARCHAR(36) NOT NULL, displayName VARCHAR(16), displayColor CHAR(1))";
    private static final    String     INSERT_QUERY    = "INSERT INTO factions (name, uuid, displayName, displayColor) VALUES (?, ?, ?, ?)";
    private static final    String     UPDATE_QUERY    = "UPDATE factions SET displayName = ?, displayColor = ? WHERE name = ?";
    private static final    String     DELETE_QUERY    = "DELETE FROM factions WHERE name = ?";
    private static final    String     GET_BY_ID_QUERY = "SELECT * FROM factions WHERE name = ?";
    private static final    String     GET_ALL_QUERY   = "SELECT * FROM factions";
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
            statement.setString(3, entity.getID());
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
        FactionEntity factionEntity = null;

        try ( PreparedStatement statement = ZPixelReboot.getInstance().getDatabase().getConnection().prepareStatement(GET_BY_ID_QUERY) ) {
            statement.setString(1, entityID);

            ResultSet resultSet = statement.executeQuery();

            if ( resultSet.next() ) {
                factionEntity = mapResultSetToFactionEntity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return factionEntity;
    }

    @Override
    public List<FactionEntity> get() {
        List<FactionEntity> factionEntities = new ArrayList<>();

        try ( Statement statement = ZPixelReboot.getInstance().getDatabase().getConnection().createStatement();
              ResultSet resultSet = statement.executeQuery(GET_ALL_QUERY) ) {

            while ( resultSet.next() ) {
                FactionEntity factionEntity = mapResultSetToFactionEntity(resultSet);
                factionEntities.add(factionEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return factionEntities;
    }

    private FactionEntity mapResultSetToFactionEntity(@NonNull ResultSet resultSet) throws SQLException {
        String name         = resultSet.getString("name");
        UUID   uuid         = UUID.fromString(resultSet.getString("uuid"));
        String displayName  = resultSet.getString("displayName");
        char   displayColor = resultSet.getString("displayColor").charAt(0);

        return new FactionEntity(name, uuid, displayName, displayColor);
    }
}
