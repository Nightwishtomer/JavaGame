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
 * Represents a door entity object in the game world.
 * <p>
 * This class extends {@link EntitiesObjects} and defines behavior for a non-solid,
 * interactive door object. The door can react to player interaction and can be rendered
 * using a specific image. When the player has the necessary key(s), interacting with the
 * door sets the door state to open.
 * </p>
 * 
 * @author adiakonov
 */
public class EntitiesObjectDoor extends EntitiesObjects {
    
    /** Image used to render the object. */
    private Image image;
    
    /**
     * Constructs a new {@code EntitiesObjectDoor} at the specified grid position.
     *
     * @param x the X-coordinate (in grid units)
     * @param y the Y-coordinate (in grid units)
     */
    public EntitiesObjectDoor(int x, int y) {
        super(x, y, Constant.DOOR);
        this.color = Color.DIMGRAY;
        this.isSolid = false;
        this.isObject = true;
        this.image = new Image(getClass().getResourceAsStream(Constant.ASSETS_URL + Constant.DOOR_PNG));
    }
    
    /**
     * Placeholder for an action method.
     * <p>
     * This method can be overridden or expanded to define specific behavior
     * when the door is interacted with or triggered.
     */
    public void action() {
    
    }
    
    /**
     * Called when a player touches or interacts with the door.
     * <p>
     * If the player has collected the required keys, this method sets
     * the door state to "open" in the game state.
     */
    public void onTouch() {
        if (gameState.checkIsKeys()) {
             gameState.setDoor(true);
         }   
    }
    
    /**
     * Renders the door object using the provided game renderer.
     *
     * @param renderer the renderer used to draw the door
     */
    public void render(GameRenderer renderer) {
        renderer.renderTileImage(image ,posX, posY, width, height );
    }

}