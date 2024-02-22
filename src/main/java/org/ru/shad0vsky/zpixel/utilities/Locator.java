package org.ru.shad0vsky.zpixel.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@UtilityClass
public class Locator {

    public static Location createLocationFromResultSet(ResultSet resultSet) throws SQLException {
        String worldName = resultSet.getString("world");
        World  world     = Bukkit.getWorld(worldName);
        double x         = resultSet.getDouble("x");
        double y         = resultSet.getDouble("y");
        double z         = resultSet.getDouble("z");
        float  yaw       = resultSet.getFloat("yaw");
        float  pitch     = resultSet.getFloat("pitch");
        return new Location(Optional.ofNullable(world).orElseThrow(IllegalArgumentException::new), x, y, z, yaw, pitch);
    }
}
