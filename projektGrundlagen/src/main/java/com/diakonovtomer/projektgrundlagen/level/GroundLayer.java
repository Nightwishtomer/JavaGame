/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.level;

import com.diakonovtomer.projektgrundlagen.Constant;
import com.diakonovtomer.projektgrundlagen.level.LevelLoader;
import com.diakonovtomer.projektgrundlagen.entities.EntitiesObjects;

/**
 * Represents the ground layer of a game level.
 * 
 * Loads the ground layer data from an XML level file using {@link LevelLoader},
 * converts the raw character data into a 2D array of {@link EntitiesObjects} for rendering and logic.
 * 
 * The ground layer typically contains tiles like floor, platforms, walls, etc.
 */
public class GroundLayer {
    
    /** 2D array of entities representing the ground tiles. */
    public EntitiesObjects[][] layer;
    
    /** Raw character map loaded from the level XML file. */
    private char[][] sourceLayer;
    
    /** The level file name used for loading. */
    private String levelName;
    
    /** Width of the layer (number of tiles in X direction). */
    public int lengthX;
    
    /** Height of the layer (number of tiles in Y direction). */
    public int lengthY;
    
    /**
     * Constructs a GroundLayer for the given level.
     * Loads the ground layer data and initializes the entities array.
     * 
     * @param levelName the filename of the level XML (e.g., "level1.xml")
     */
    public GroundLayer(String levelName) {
        this.levelName = levelName;
        this.sourceLayer = LevelLoader.loadLayer(levelName, Constant.MAP_LAYER_NAME_GROUND);
        this.lengthY = sourceLayer.length;
        this.lengthX = sourceLayer[0].length;
        layer = new EntitiesObjects[lengthY][lengthX];
        enumeration();
    }
    
    /**
     * Converts the raw character map into EntitiesObjects by creating
     * instances for each tile based on its character representation.
     */
    private void enumeration() {
    for (int y = 0; y < lengthY; y++) {
            for (int x = 0; x < lengthX; x++) {
                layer[y][x] = EntitiesObjects.create(x, y, sourceLayer[y][x]);
            }
        }
    }
}
