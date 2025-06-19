/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.entities;

import com.diakonovtomer.projektgrundlagen.game.GameRenderer;
import com.diakonovtomer.projektgrundlagen.game.GameState;
import com.diakonovtomer.projektgrundlagen.Constant;
import javafx.scene.paint.Color;

/**
 * Base class representing an entity object on the game map.
 * This class is intended to be extended by specific entity types such as ground, platforms, keys, etc.
 * It contains common properties and methods used across all entity objects.
 * 
 * Each entity has a tile-based position (cellX, cellY) and derived pixel-based position (posX, posY),
 * as well as properties indicating whether it is solid, interactable, or already used.
 * 
 * @author adiakonov
 */
public class EntitiesObjects {
    /** X coordinate of the entity in tile units. */
    protected int cellX;
     /** Y coordinate of the entity in tile units. */
    protected int cellY;
    /** Character identifier representing the type of the entity. */
    protected char parent;
    /** X coordinate in pixel units. */
    protected final double posX;
    /** Y coordinate in pixel units. */
    protected final double posY;
    /** Width of the entity in pixels. */
    protected final int width;
    /** Height of the entity in pixels. */
    protected final int height;
    /** Whether this entity blocks movement. */
    protected boolean isSolid;
    /** Whether this entity is considered an interactable object. */
    protected boolean isObject;
    /** Whether this entity has already been used (e.g. picked up, activated, etc.). */
    protected boolean isUsed;
    /** Color used to render the entity. */
    protected Color color;
    /** Reference to the game state (not initialized in constructor). */
    protected GameState gameState;
    
    /**
     * Constructs a new generic entity object at the specified tile coordinates.
     *
     * @param cellX  X coordinate in tile units
     * @param cellY  Y coordinate in tile units
     * @param parent Character indicating the type of the object
     */
    public EntitiesObjects(int cellX, int cellY, char parent) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.parent = parent;
        this.posX = cellX * Constant.TILE_SIZE;
        this.posY = cellY * Constant.TILE_SIZE;
        this.width = Constant.TILE_SIZE;
        this.height = Constant.TILE_SIZE;
        this.color = Color.BLACK;
        this.isSolid = false;
        this.isObject = false;
        this.isUsed = false;
        this.gameState = gameState;
    }
    
    /**
     * Factory method to create a specific type of entity object based on the parent character.
     *
     * @param cellX  X coordinate in tile units
     * @param cellY  Y coordinate in tile units
     * @param parent Character identifier of the object type
     * @return An instance of a specific EntitiesObject subclass
     */
    public static EntitiesObjects create(int cellX, int cellY, char parent) {
        return switch(parent) {
            case Constant.EMPTY -> new EntitiesObjectEmpty(cellX, cellY);        
            case Constant.GROUND -> new EntitiesObjectGround(cellX, cellY);
            case Constant.PLATFORM -> new EntitiesObjectPlatform(cellX, cellY);
            case Constant.SPIKE -> new EntitiesObjectSpike(cellX, cellY);
            case Constant.KEY -> new EntitiesObjectKey(cellX, cellY);
            case Constant.DOOR -> new EntitiesObjectDoor(cellX, cellY);
            default -> new EntitiesObjectEmpty(cellX, cellY);
        };
    }
    
    /**
     * Gets the tile X coordinate of the entity.
     * @return tile X coordinate
     */
    public int getX() {
        return cellX;
    }
    
    /**
     * Gets the tile Y coordinate of the entity.
     * @return tile Y coordinate
     */
    public int getY() {
        return cellY;
    }
    
    /**
     * Gets the character identifier representing the entity type.
     * @return character symbol
     */
    public char getParent() {
        return parent;
    }
    
     /**
     * Checks if the entity is solid (i.e. blocks movement).
     * @return true if solid, false otherwise
     */
    public boolean isSolid() {
        return isSolid;
    }
    
    /**
     * Marks this entity as cleared (used and no longer interactable or visible).
     * Changes its appearance and flags.
     */
    public void clear() {
        this.parent = '.';
        this.color = Color.TRANSPARENT;
        this.isSolid = false;
        this.isObject = false;
        this.isUsed = true;
    }
    
    /**
     * Checks if the entity is an interactable object.
     * @return true if interactable, false otherwise
     */
    public boolean isObject(){
        return isObject;
    }
    
    /**
     * Checks if the entity has already been used or activated.
     * @return true if used, false otherwise
     */
    public boolean isUsed(){
        return isUsed;
    }
    
    /**
     * Called when the player interacts with the entity (e.g., presses action key).
     * Intended to be overridden.
     */
    public void action() {
    
    }
    
    /**
     * Called when the player touches or walks into the entity.
     * Intended to be overridden.
     */
    public void onTouch() {
    
    }
    
    /**
     * Renders the entity using the provided game renderer.
     * Default rendering is a colored rectangle.
     *
     * @param renderer the renderer used to draw the entity
     */
    public void render(GameRenderer renderer) {
        renderer.renderCell(color, posX, posY, width, height);
    }
}