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
 * Represents a solid platform tile in the game world.
 * <p>
 * This class extends {@link EntitiesObjects} and defines a platform-type entity
 * that cannot be passed through by the player. It is rendered with a custom image
 * and has a solid physical presence in the game environment.
 * </p>
 * 
 * @author adiakonov
 */
public class EntitiesObjectPlatform extends EntitiesObjects {
    
    /** Image used to render the object. */
    private Image image;
    
    /**
     * Constructs a new {@code EntitiesObjectPlatform} at the specified grid position.
     *
     * @param x the X-coordinate (in grid units)
     * @param y the Y-coordinate (in grid units)
     */
    public EntitiesObjectPlatform(int x, int y) {
        super(x, y, Constant.PLATFORM);
        this.color = Color.DARKGREEN;
        this.isSolid = true;
        this.image = new Image(getClass().getResourceAsStream(Constant.ASSETS_URL + Constant.PLATFORM_PNG));
    }
   
    /**
     * Renders the platform using the provided game renderer.
     *
     * @param renderer the renderer used to draw the platform
     */
    public void render(GameRenderer renderer) {
        renderer.renderTileImage(image ,posX, posY, width, height );
    }
}