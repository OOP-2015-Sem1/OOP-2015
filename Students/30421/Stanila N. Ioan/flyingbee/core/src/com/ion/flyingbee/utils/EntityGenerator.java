package com.ion.flyingbee.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.ion.flyingbee.factories.EntityFactory;
import com.ion.flyingbee.models.Entity;
import com.ion.flyingbee.models.EntityId;

/**
 * Created by Ion on 27.12.2015.
 */
public class EntityGenerator {

    public static Entity generateNewEntity() {
        int id = MathUtils.random(1, EntityId.NUMBER_OF_ENTITIES - 1);
        Entity newEntity = EntityFactory.getEntity(id);
        int height = 0;
        do {
            height = MathUtils.random(0, Gdx.graphics.getHeight() - newEntity.getHeight()) - Gdx.graphics.getHeight() / 10;
        } while (height < 0);
        newEntity.setInitialPosition(Gdx.graphics.getWidth(), height);
        return newEntity;
    }
}
