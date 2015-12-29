package com.ion.flyingbee.factories;

import com.ion.flyingbee.models.Bee;
import com.ion.flyingbee.models.Entity;
import com.ion.flyingbee.models.Flower;
import com.ion.flyingbee.models.Wasp;

/**
 * Created by Ion on 26.12.2015.
 */
public class EntityFactory {

    /**
     * Generates a new Entity based on the given id.
     * @param id found in EntityId class.
     * @return a new Entity or null if no Entity was bound to the given id.
     */
    public static Entity getEntity(int id) {
        switch (id) {
            case 0:
                return new Bee();
            case 1:
                return new Wasp();
            case 2:
                return new Flower();
            default:
                return null;
        }
    }
}
