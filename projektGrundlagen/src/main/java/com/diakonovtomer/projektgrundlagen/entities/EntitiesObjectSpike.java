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
 * Represents a spike trap object in the game world.
 * <p>
 * When the player touches this object, it deals damage (unless in god mode)
 * and becomes temporarily inactive for a short period to avoid rapid repeated damage.
 * </p>
 * 
 * @author adiakonov
 */
public class EntitiesObjectSpike extends EntitiesObjects {
    
    /** Counter for tracking the spike's cooldown period. */
    private int pauseTimerCounter;
    
    /** Cooldown period before the spike can be triggered again. */
    private int pauseTimer;
    
    /** Image used to render the object. */
    private Image image;
    
    /**
     * Constructs a new {@code EntitiesObjectSpike} at the specified grid position.
     *
     * @param x the X-coordinate (in grid units)
     * @param y the Y-coordinate (in grid units)
     */
    public EntitiesObjectSpike(int x, int y) {
        super(x, y, Constant.SPIKE);
        this.color = Color.CRIMSON;
        this.isSolid = false;
        this.isObject = true;
        this.pauseTimerCounter = 0;
        this.pauseTimer = 45; // sec
        this.image = new Image(getClass().getResourceAsStream(Constant.ASSETS_URL + Constant.SPIKES_PNG));
    }
    
    /**
     * Executes the action triggered when the spike activates.
     * <p>
     * Reduces the player's life unless god mode is enabled. Marks the spike as used.
     */
    public void action() {
        if (!Constant.GOD_MODE) {
            this.isUsed = true;
            gameState.changeLives(-1);
        }
    }
    
    /**
     * Handles logic when the player comes into contact with the spike.
     * <p>
     * The spike triggers once on contact and becomes temporarily inactive to prevent
     * continuous damage. Resets after 60 frames (~1 second).
     */
    public void onTouch() {
        ++pauseTimerCounter;
        if (pauseTimerCounter == 1) {
            if (!this.isUsed){
                this.action();
            }
        }
        if (pauseTimerCounter == 60) {
            pauseTimerCounter = 0;
            this.isUsed = false;
        }
    }
    
    /**
     * Renders the spike trap using the provided game renderer.
     *
     * @param renderer the renderer used to draw the spike object
     */
    public void render(GameRenderer renderer) {
        renderer.renderTileImage(image ,posX, posY, width, height );
    }
}