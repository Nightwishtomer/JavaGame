/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.game;

import com.diakonovtomer.projektgrundlagen.Constant;

/**
 * Handles camera logic for following the player within the game world.
 * <p>
 * The camera adjusts its offset based on the player's position, ensuring that
 * the player stays centered on the screen (horizontally, and optionally vertically),
 * while also accounting for screen and level size constraints.
 * </p>
 * 
 * @author adiakonov
 */
public class GameCamera {
    /** Width of the visible screen in pixels. */
    private final int screenWidth;
    
    /** Height of the visible screen in pixels. */
    private final int screenHeight;
    
    /** Total width of the level in pixels. */
    private final int levelWidth;
    
     /** Total height of the level in pixels. */
    private final int levelHeight;
    
     /** Size of a single tile in pixels. */
    private final int tileSize;

    /** Horizontal offset of the camera from the level's origin. */
    private double offsetX;
    
    /** Vertical offset of the camera from the level's origin. */
    private double offsetY;

    /** Determines whether the camera follows the player vertically. */
    private final boolean verticalFollow; // если true - камера двигается и по Y, иначе фиксирована

    /**
     * Constructs a new {@code GameCamera} instance.
     *
     * @param screenWidth     the width of the screen in pixels
     * @param screenHeight    the height of the screen in pixels
     * @param levelWidth      the width of the level in pixels
     * @param levelHeight     the height of the level in pixels
     * @param tileSize        the size of one tile in pixels
     * @param verticalFollow  whether the camera should follow the player vertically
     */
    public GameCamera(int screenWidth, int screenHeight, int levelWidth, int levelHeight, int tileSize, boolean verticalFollow) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.tileSize = tileSize;
        this.verticalFollow = verticalFollow;
    }

    /**
     * Updates the camera's offset based on the player's current pixel position.
     * <p>
     * Horizontally, the camera always follows the player.
     * Vertically, it follows only if {@code verticalFollow} is true.
     *
     * @param playerPixelX  the X-position of the player in pixels
     * @param playerPixelY  the Y-position of the player in pixels
     */
    public void update(double playerPixelX, double playerPixelY) {
        offsetX = playerPixelX - Constant.GAMEWIDTH/2;
        if (verticalFollow) {
            offsetY = playerPixelY - Constant.GAMEHEIGHT/2;
        } else {
            offsetY = 0;
        }
    }
    
    /**
     * Returns the X-coordinate of the player relative to the camera's viewport.
     *
     * @return the X-coordinate (in pixels) of the player on screen
     */
    public int getPlayerCameraX() {
        return Constant.GAMEWIDTH / 2;
    }
    
    /**
     * Returns the Y-coordinate of the player relative to the camera's viewport.
     *
     * @return the Y-coordinate (in pixels) of the player on screen
     */
    public int getPlayerCameraY() {
        return Constant.GAMEHEIGHT / 2;
    }

    /**
     * Returns the current horizontal offset of the camera.
     *
     * @return the offset in pixels on the X-axis
     */
    public double getOffsetX() {
        return offsetX;
    }

    /**
     * Returns the current vertical offset of the camera.
     *
     * @return the offset in pixels on the Y-axis
     */
    public double getOffsetY() {
        return offsetY;
    }
}