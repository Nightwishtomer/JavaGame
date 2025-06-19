/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.entities;

import com.diakonovtomer.projektgrundlagen.Constant;
import com.diakonovtomer.projektgrundlagen.game.GameRenderer;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

/**
 * Represents a key object in the game world.
 * <p>
 * This class extends {@link EntitiesObjects} and defines a non-solid collectible object.
 * When the player interacts with the key, it increases the key count in the game state
 * and marks itself as used, preventing further interaction or rendering.
 * </p>
 * 
 * @author adiakonov
 */
public class EntitiesObjectKey extends EntitiesObjects {
    
    /** Image used to render the object. */
    private Image image;
    
    /**
     * Constructs a new {@code EntitiesObjectKey} at the specified grid position.
     *
     * @param x the X-coordinate (in grid units)
     * @param y the Y-coordinate (in grid units)
     */
    public EntitiesObjectKey(int x, int y) {
        super(x, y, Constant.KEY);
        this.color = Color.GOLD;
        this.isSolid = false;
        this.isObject = true;
        this.image = new Image(getClass().getResourceAsStream(Constant.ASSETS_URL + Constant.KEY_PNG));
    }
    
    /**
     * Handles the logic that occurs when the key is collected.
     * <p>
     * Increases the player's key count in the game state, marks the key
     * as used, and clears it from the map.
     */
    public void action() {
        gameState.changeKeys(1);
        this.isUsed = true;
        this.clear();
    }
    
    /**
     * Called when the player touches or interacts with the key.
     * <p>
     * If the key hasn't already been used, the {@link #action()} method is invoked.
     */
    public void onTouch() {
        if (!this.isUsed) {
            this.action();
        }
    }
    
    /**
     * Renders the key object using the provided game renderer.
     * <p>
     * If the key has already been collected, it will not be rendered.
     *
     * @param renderer the renderer used to draw the key
     */
    public void render(GameRenderer renderer) {
        if (isUsed) {
            return; // Do not render if the key is already collected
        }
        renderer.renderTileImage(image ,posX, posY, width, height - 10);
    }
}