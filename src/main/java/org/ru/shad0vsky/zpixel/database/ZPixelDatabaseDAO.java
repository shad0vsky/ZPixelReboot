package org.ru.shad0vsky.zpixel.database;

import lombok.NonNull;

import java.util.List;

public interface ZPixelDatabaseDAO<Entity extends ZPixelDatabaseEntity> {

    void create(@NonNull Entity entity);

    void update(@NonNull Entity entity);

    void delete(@NonNull Entity entity);

    Entity get(@NonNull String entityID);

    List<Entity> get();
}
