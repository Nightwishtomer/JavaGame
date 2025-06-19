/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.collision;

import com.diakonovtomer.projektgrundlagen.level.Level;
import com.diakonovtomer.projektgrundlagen.Constant;
import com.diakonovtomer.projektgrundlagen.level.ObjectLayer;
import com.diakonovtomer.projektgrundlagen.level.GroundLayer;

/**
 * Handles all collision detection logic within the game world.
 * <p>
 * This class is responsible for determining whether the player or other entities
 * collide with solid ground tiles or objects based on pixel-level hitbox positioning.
 * </p>
 * <p>
 * Uses a reduced hitbox size for more forgiving movement and accurate collision handling.
 * </p>
 * 
 * <p>
 * It supports solid tile checking, collision testing for horizontal movement, and object collision detection.
 * </p>
 * 
 * @author adiakonov
 */
public class CollisionHandler {
    private final GroundLayer groundLayer;
    private final ObjectLayer objectLayer;
    private Level level;
    private final double playerHitboxWidth;
    private final double playerHitboxHeight;

    /**
     * Constructs a new {@code CollisionHandler} for a given level.
     *
     * @param level the level that contains the ground and object layers used for collision detection
     */
    public CollisionHandler(Level level) {
        this.level = level;
        this.groundLayer = level.getGroundLayer();
        this.objectLayer = level.getObjectLayer();
        this.playerHitboxWidth = Constant.PLAYER_WIDTH * Constant.PLAYER_WIDTH_HITBOX; // 10% kleiner
        this.playerHitboxHeight = Constant.PLAYER_HEIGHT * Constant.PLAYER_HEIGHT_HITBOX; // 10% kleiner
    }
    
    /**
     * Checks if a specific tile in the ground layer is solid.
     *
     * @param x the tile's x-coordinate
     * @param y the tile's y-coordinate
     * @return {@code true} if the tile is solid or out of bounds (treated as solid), {@code false} otherwise
     */
    public boolean isSolid(int x, int y) {
        if (y < 0 || y >= groundLayer.lengthY || x < 0 || x >= groundLayer.lengthX) {   
            return true; // Out-of-bounds is treated as solid wall
        }
        return groundLayer.layer[y][x].isSolid();
    }

    /**
     * Checks whether the player (or entity) collides with any solid tiles.
     * Uses a smaller hitbox for smoother gameplay.
     *
     * @param xPixel the top-left x-position in pixels
     * @param yPixel the top-left y-position in pixels
     * @return {@code true} if any tile within the hitbox is solid, {@code false} otherwise
     */
    public boolean isSolidArea(double xPixel, double yPixel) {
        double hitboxOffsetX = (Constant.PLAYER_WIDTH - playerHitboxWidth) / 2;
        double hitboxOffsetY = (Constant.PLAYER_HEIGHT - playerHitboxHeight) / 2;
        // Angepasste Position mit kleinerer Hitbox
        double adjustedX = xPixel + hitboxOffsetX;
        double adjustedY = yPixel + hitboxOffsetY;
        int leftTile = (int) (adjustedX / Constant.TILE_SIZE);
        int rightTile = (int) ((adjustedX + playerHitboxWidth - 1) / Constant.TILE_SIZE);
        int topTile = (int) (adjustedY / Constant.TILE_SIZE);
        int bottomTile = (int) ((adjustedY + playerHitboxHeight - 1) / Constant.TILE_SIZE);
        for (int y = topTile; y <= bottomTile; y++) {
            for (int x = leftTile; x <= rightTile; x++) {
                if (isSolid(x, y)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Performs a horizontal-only collision check for the player's hitbox.
     * This can be used to optimize side movement detection.
     *
     * @param xPixel the x-position in pixels
     * @param yPixel the y-position in pixels
     * @return {@code true} if a solid tile exists within horizontal movement range, {@code false} otherwise
     */
    public boolean isSolidAreaHorizontal(double xPixel, double yPixel) {
        double hitboxOffsetX = (Constant.PLAYER_WIDTH - playerHitboxWidth) / 2;
        double hitboxOffsetY = (Constant.PLAYER_HEIGHT - playerHitboxHeight) / 2;
        double adjustedX = xPixel + hitboxOffsetX;
        double adjustedY = yPixel + hitboxOffsetY;
        int leftTile = (int) (adjustedX / Constant.TILE_SIZE);
        int rightTile = (int) ((adjustedX + playerHitboxWidth - 1) / Constant.TILE_SIZE);
        int topTile = (int) (adjustedY / Constant.TILE_SIZE);
        int bottomTile = (int) ((adjustedY + playerHitboxHeight - 1) / Constant.TILE_SIZE);
        for (int y = topTile; y <= bottomTile; y++) {
            for (int x = leftTile; x <= rightTile; x++) {
                if (isSolid(x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks for collision with objects in the object layer.
     * If an object is found within the player's hitbox area, its tile coordinates are returned.
     *
     * @param xPixel the x-position in pixels
     * @param yPixel the y-position in pixels
     * @return an array of two integers [x, y] representing the tile coordinates of the object,
     *         or [0, 0] if no object is detected
     */
    public int[] collisionCheckObject(double xPixel, double yPixel) {
        double hitboxOffsetX = (Constant.PLAYER_WIDTH - playerHitboxWidth) / 2;
        double hitboxOffsetY = (Constant.PLAYER_HEIGHT - playerHitboxHeight) / 2;
        double adjustedX = xPixel + hitboxOffsetX;
        double adjustedY = yPixel + hitboxOffsetY;
        int leftTile = (int) (adjustedX / Constant.TILE_SIZE);
        int rightTile = (int) ((adjustedX + playerHitboxWidth - 1) / Constant.TILE_SIZE);
        int topTile = (int) (adjustedY / Constant.TILE_SIZE);
        int bottomTile = (int) ((adjustedY + playerHitboxHeight - 1) / Constant.TILE_SIZE);
        for (int y = topTile; y <= bottomTile; y++) {
            for (int x = leftTile; x <= rightTile; x++) {
                if (isObject(x, y)) {
                    return new int[]{x, y};
                }
            }
        }
        return new int[]{0, 0}; // Default: no object detected
    }
    
    /**
     * Checks if the specified tile coordinates correspond to an object.
     *
     * @param x the tile's x-coordinate
     * @param y the tile's y-coordinate
     * @return {@code true} if the tile contains an object, {@code false} otherwise
     */
    public boolean isObject(int x, int y) {
        if (y < 0 || y >= objectLayer.lengthY || x < 0 || x >= objectLayer.lengthX) {
            return false; // Out-of-bounds is not considered an object
        }
        return objectLayer.layer[y][x].isObject();
    }             
}