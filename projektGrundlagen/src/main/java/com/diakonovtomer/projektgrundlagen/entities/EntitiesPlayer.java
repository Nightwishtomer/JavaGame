/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.entities;

import com.diakonovtomer.projektgrundlagen.collision.CollisionHandler;
import com.diakonovtomer.projektgrundlagen.Constant;
import com.diakonovtomer.projektgrundlagen.game.GameState;
import com.diakonovtomer.projektgrundlagen.game.GameRenderer;
import com.diakonovtomer.projektgrundlagen.level.Level;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents the player character in the game world.
 * <p>
 * Handles movement, jumping, gravity, collision detection,
 * and rendering. Stores both tile-based and pixel-perfect positions.
 * </p>
 * 
 * <p>Supports the following behaviors:</p>
 * <ul>
 *   <li>Left/right movement with ground/air distinction</li>
 *   <li>Jumping with height limitation</li>
 *   <li>Gravity and fall speed management</li>
 *   <li>Collision with level tiles and game objects</li>
 *   <li>Rendering direction-aware player sprite</li>
 * </ul>
 * 
 * <p>Position is handled in two coordinate systems:</p>
 * <ul>
 *   <li>{@code x, y} - rounded tile-based location</li>
 *   <li>{@code posX, posY} - real continuous position for physics</li>
 * </ul>
 * 
 * @author adiakonov
 */
public class EntitiesPlayer {
    
    private double x, y;              // Tile-aligned position (rounded)
    private double posX, posY;        // Precise position for physics and rendering
    private double velocityX = 0;
    private double velocityY = 0;
    private boolean onGround = false;
    private boolean direction = true; // true = facing right, false = facing left
    private boolean isJumping = false;
    private double jumpStartY = 0;
    private final Level level;
    private GameRenderer renderer;
    private final Image imageL;
    private final Image imageR;
    private final GameState gameState;
    
    /** Number of keys collected by the player */
    public int keysCollected = 0; // счётчик ключей
    
    /**
     * Constructs a new player entity at the given coordinates.
     *
     * @param x     initial X position (pixels)
     * @param y     initial Y position (pixels)
     * @param level reference to the current level for collision checks
     */     
    public EntitiesPlayer(double y, double x, Level level) {
        this.x = x;
        this.y = y;
        this.posX = x;
        this.posY = y;
        this.level = level;
        this.gameState = new GameState();
        this.imageL = new Image(getClass().getResourceAsStream(Constant.ASSETS_URL + Constant.PLAYER_PNG_L));
        this.imageR = new Image(getClass().getResourceAsStream(Constant.ASSETS_URL + Constant.PLAYER_PNG_R));   
    }
    
    // --- Getters ---
    
    public double getX() { return x; }
    
    public double getY() { return y; }
    
    public double getPosX() { return posX; }
    
    public double getPosY() { return posX; }
    
    public double getVelocityX() { return velocityX; }
    
    public double getVelocityY() { return velocityY; }
    
    public void getDirection() { }
    
    // --- Movement ---
    
    /**
     * Moves the player to the left, applying collision detection.
     * Uses reduced speed if mid-air.
     *
     * @param collisionHandler collision checker instance
     */
    public void moveLeft(CollisionHandler collisionHandler1) {
        direction = false;
        double nextX = posX - (onGround ? Constant.MOVE_SPEED : Constant.MOVE_SPEED_FALL);
        if (!collisionHandler1.isSolidAreaHorizontal(nextX, posY)) {
            velocityX = onGround ? -Constant.MOVE_SPEED : -Constant.MOVE_SPEED_FALL;
        } else {
            velocityX = 0;
        }
    }
    
    /**
     * Moves the player to the right, applying collision detection.
     * Uses reduced speed if mid-air.
     *
     * @param collisionHandler collision checker instance
     */
    public void moveRight(CollisionHandler collisionHandler1) {
        direction = true;
        double nextX = posX + (onGround ? Constant.MOVE_SPEED : Constant.MOVE_SPEED_FALL);
        if (!collisionHandler1.isSolidAreaHorizontal(nextX, posY)) {
            velocityX = onGround ? Constant.MOVE_SPEED : Constant.MOVE_SPEED_FALL;
        } else {
            velocityX = 0;
        }
    }
    
    /**
     * Stops horizontal movement (when key is released).
     */
    public void stopMoving() {
        velocityX = 0;
    }
    
    // --- Jumping and gravity ---
    
    /**
     * Initiates a jump if the player is on the ground.
     */
    public void jump() {
        if (onGround) {
            velocityY = Constant.JUMP_FORCE;
            onGround = false;
            jumpStartY = posY;
            isJumping = true;       
        }
    }
            
    /**
     * Cancels a jump early if the key was released before peak height.
     */
    public void stopJump() {
        if (isJumping && velocityY < 0) {
            velocityY = 0; // остановим прыжок  // Преждевременно обрубаем прыжок, если кнопку отпустили
            isJumping = false;
        }
    }
    
    /**
     * Applies gravity to the player each frame.
     * Caps fall speed at a maximum value.
     */
    public void applyGravity() {
        velocityY += Constant.GRAVITY;
        if (velocityY > Constant.MAX_FALL_SPEED) velocityY = Constant.MAX_FALL_SPEED;
    }
       
    /**
     * Stops upward movement if player has reached max jump height.
     */
    public void jumpLimit() {
        if (isJumping && posY <= jumpStartY - Constant.MAX_JUMP_HEIGHT) {
            if (velocityY < 0) {
                velocityY = 0; // Обрубаем подъём
            }
            isJumping = false;
        }
    }
    
    // --- Position and collision ---
    
    /**
     * Updates the player position based on current velocity and collisions.
     * Applies both X and Y axis checks separately.
     *
     * @param collisionHandler collision checker instance
     */
    public void updatePosition(CollisionHandler collisionHandler1) {
        double nextX = posX + velocityX;
        double nextY = posY + velocityY;
          
        // X-axis collision
        if (!collisionHandler1.isSolidArea(nextX, posY)) { // Проверка коллизий по X
            posX = nextX;
        }

        // Y-axis collision
        if (!collisionHandler1.isSolidArea(posX, nextY)) { // Проверка коллизий по Y
            posY = nextY;
            onGround = false;
        } else {
            if (velocityY > 0) onGround = true; 
            velocityY = 0;
            isJumping = false;
        }
    }
    
    /**
     * Rounds player position to tile coordinates.
     * Should be called after updatePosition.
     */
    public void updateTeilPosition() {
        this.x = (double) Math.round(posX);
        this.y = (double) Math.round(posY);
    }    
       
    /**
     * Checks for collisions with interactive objects like keys, doors, etc.
     *
     * @param collisionHandler collision checker instance
     */
    public void checkObjectCollision(CollisionHandler collisionHandler) {
        double nextX = posX + velocityX;
        int[] collisionCheckObject = collisionHandler.collisionCheckObject((double) nextX, (double) posY);
        
        if (collisionCheckObject[0] != 0 || collisionCheckObject[1] != 0){
            level.getObjectLayer().layer[collisionCheckObject[1]][collisionCheckObject[0]].onTouch();   
        }
    }
    
    // --- Rendering ---
    
    /**
     * Renders the player sprite depending on direction.
     *
     * @param renderer the rendering system to use
     */
    public void render(GameRenderer renderer) {
        if (!direction) {
            renderer.renderPlayerImg(imageL);          
        } else {
            renderer.renderPlayerImg(imageR);
        }
    }
}