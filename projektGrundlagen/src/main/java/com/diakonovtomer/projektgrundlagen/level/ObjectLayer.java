/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.level;

import com.diakonovtomer.projektgrundlagen.Constant;
import com.diakonovtomer.projektgrundlagen.level.LevelLoader;
import com.diakonovtomer.projektgrundlagen.entities.EntitiesObjects;

/**
 * Represents the object layer of a game level.
 * <p>
 * The object layer contains interactive or solid entities such as players, spikes, platforms, keys, etc.
 * It is loaded from a character map and converted into a 2D array of {@link EntitiesObjects}.
 * </p>
 * <p>
 * Each cell corresponds to a tile, where the character from the level file determines the type of entity to instantiate.
 * </p>
 * 
 * @author adiakonov
 */
public class ObjectLayer {  
    
    /** 2D array of entities representing the object layer in the level. */
    public EntitiesObjects[][] layer;
    
    /** Source character array loaded from the level file representing the layer. */
    private char[][] sourceLayer;
    
    /** The name or identifier of the level this layer belongs to. */
    private String levelName;
    
    /** Width of the layer in tiles (number of columns). */
    public int lengthX;
    
    /** Height of the layer in tiles (number of rows). */
    public int lengthY;
    
    /**
     * Constructs an ObjectLayer by loading and parsing the specified level's object layer.
     *
     * @param levelName the identifier or file name of the level
     */
    public ObjectLayer(String levelName) {
        this.levelName = levelName;
        this.sourceLayer = LevelLoader.loadLayer(levelName, Constant.MAP_LAYER_NAME_OBJECT);
        this.lengthY = sourceLayer.length;
        this.lengthX = sourceLayer[0].length;
        layer = new EntitiesObjects[lengthY][lengthX];
        enumeration();
    }

    /**
     * Converts a two-dimensional array of characters into EntitiesObjects
     * using the factory method {@code EntitiesObjects.create(x, y, char)}.
     */
    private void enumeration() {
    for (int y = 0; y < lengthY; y++) {
            for (int x = 0; x < lengthX; x++) {
                layer[y][x] = EntitiesObjects.create(x, y, sourceLayer[y][x]);
            }
        }
    }
}