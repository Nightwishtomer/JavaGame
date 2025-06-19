/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.level;

import com.diakonovtomer.projektgrundlagen.Constant;
import com.diakonovtomer.projektgrundlagen.entities.EntitiesObjects;
import com.diakonovtomer.projektgrundlagen.game.GameRenderer;

/**
 * Represents a single game level, including its ground and object layers.
 * <p>
 * Provides methods to render the level and access or manipulate individual tiles.
 * </p>
 * <p>
 * The level is composed of two layers:
 * <ul>
 *   <li>{@code GroundLayer} — static tiles like floors or terrain</li>
 *   <li>{@code ObjectLayer} — interactive or solid entities like players, spikes, doors, etc.</li>
 * </ul>
 * </p>
 * 
 * @author adiakonov
 */
public class Level {
    
    /** The layer containing ground tiles. */
    private final GroundLayer groundLayer;
    
    /** The layer containing object tiles. */
    private final ObjectLayer objectLayer;
    
    /** Renderer used to draw the level. */
    private final GameRenderer renderer;
    
    /** The name of the level (usually used for loading resources). */
    private final String levelName;
    
    /** Optional: procedural generation map storage. */
    private String [] genMap;

    /**
     * Creates a new Level with the specified name and renderer.
     *
     * @param levelName the name of the level file or identifier
     * @param renderer  the renderer used to draw the tiles
     */
    public Level(String levelName, GameRenderer renderer) {
        this.levelName = levelName;
        this.renderer = renderer;
        this.groundLayer = new GroundLayer(levelName);
        this.objectLayer = new ObjectLayer(levelName);
    }

    /**
     * Finds the player's initial position in the level, in pixel coordinates.
     *
     * @return a double array with X and Y pixel coordinates, or [0, 0] if not found
     */
    public double[] findPlayerPositionPix() {
        for (int y = 0; y < objectLayer.lengthY; y++) {
            for (int x = 0; x < objectLayer.lengthX; x++) {
                if (objectLayer.layer[y][x].getParent() == Constant.PLAYER) {
                    return new double[]{y * Constant.TILE_SIZE, x * Constant.TILE_SIZE};
                }
            }
        }
        return new double[]{0, 0}; // по умолчанию
    }

    /**
     * Clears the specified tile in the given layer type.
     * 
     * <p>Currently works identically for ground and object layers,
     * but may be differentiated later.</p>
     *
     * @param type the layer type (e.g. "ground" or "object")
     * @param posX the X coordinate in tile units
     * @param posY the Y coordinate in tile units
     * @return true if the cell was cleared successfully; false if out of bounds or error
     */
    public boolean clearCell(String type, int posX, int posY) {
    if (Constant.MAP_LAYER_NAME_GROUND.equals(type)) {
            try {
                objectLayer.layer[posY][posX] = EntitiesObjects.create(posX, posY, '.');
            }
            catch(Exception e) {
                return false;
            }
        } else {
            try {
                objectLayer.layer[posY][posX] = EntitiesObjects.create(posX, posY, '.');
            }
            catch(Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Renders all tiles from the ground layer using the current renderer.
     */
    public void renderGround() {
        for (int y = 0; y < groundLayer.lengthY; y++) {
            for (int x = 0; x < groundLayer.lengthX; x++) {
                EntitiesObjects object = groundLayer.layer[y][x];
                object.render(renderer);
            }
        }            
    }

    /**
     * Renders all tiles from the object layer using the current renderer.
     */
    public void renderObject() {
        for (int y = 0; y < objectLayer.lengthY; y++) {
            for (int x = 0; x < objectLayer.lengthX; x++) {
                EntitiesObjects object = objectLayer.layer[y][x];
                object.render(renderer);
            }
        }
    }

    /**
     * Returns the object layer of this level.
     *
     * @return the object layer
     */
    public ObjectLayer getObjectLayer() {
        return objectLayer;
    }

    /**
     * Returns the ground layer of this level.
     *
     * @return the ground layer
     */
    public GroundLayer getGroundLayer() {
        return groundLayer;
    }         
}
