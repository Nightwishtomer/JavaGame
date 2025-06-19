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
 * Represents a ground tile entity in the game world.
 * <p>
 * This class extends {@link EntitiesObjects} and defines a solid ground tile 
 * that can be rendered with a specific image. Ground tiles are impassable (solid)
 * and visually styled using an image and a background color.
 * </p>
 * 
 * @author adiakonov
 */
public class EntitiesObjectGround extends EntitiesObjects {
    
    /** Image used to render the ground tile. */
    private Image image;
    
    /**
     * Constructs a new {@code EntitiesObjectGround} at the specified grid position.
     *
     * @param x the X-coordinate (in grid units)
     * @param y the Y-coordinate (in grid units)
     */
    public EntitiesObjectGround(int x, int y) {
        super(x, y, Constant.GROUND);
        this.color = Color.SADDLEBROWN;
        this.isSolid = true;
        this.image = new Image(getClass().getResourceAsStream(Constant.ASSETS_URL + Constant.GROUND_PNG));
    }
    
    /**
     * Renders the ground tile using the provided game renderer.
     *
     * @param renderer the renderer used to draw the ground tile
     */
    public void render(GameRenderer renderer) {
        renderer.renderTileImage(image ,posX, posY, width, height );
    }
}