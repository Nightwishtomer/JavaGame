/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.game;

import com.diakonovtomer.projektgrundlagen.Constant;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents the global state of the game, including player lives, keys, win/lose status,
 * and UI rendering for lives and keys.
 * <p>
 * This class uses static methods and fields to maintain a singleton-like behavior,
 * accessible globally within the game.
 * </p>
 * 
 * @author adiakonov
 */
public class GameState {
    /** Current number of player lives. */
    private static int lives = Constant.START_LIVES;
    
    /** Current number of collected keys. */
    private static int keys = Constant.START_KEYS;
    
    /** Whether the player has reached the door (used for win condition). */
    private static boolean door = false;
    
    /** True if the player has won the game. */
    private static boolean isWin = false;
    
    /** True if the player has lost the game. */
    private static boolean isGameOver = false;

    /**
     * Sets the state of the door (true if the door has been reached).
     *
     * @param value true if door is active
     */
    public static void setDoor(boolean value) {
        door = value;
    }

    /**
     * Returns the current number of lives.
     *
     * @return the number of remaining lives
     */
    public static int getLives() {
        return lives;
    }
    
    /**
     * Returns the maximum number of lives the player can have.
     *
     * @return the maximum number of lives
     */
    public static int getMaxLives() {
        return Constant.MAX_LIVES;
    }

    /**
     * Changes the number of lives by the specified delta.
     * Triggers game over check after the change.
     *
     * @param delta the number of lives to add (can be negative)
     */
    public static void changeLives(int delta) {
        lives += delta;
        isGameOver = checkIsGameOver();
    }  
    
    /**
     * Returns the current number of collected keys.
     *
     * @return the number of keys
     */
    public static int getKeys() {
        return keys;
    }
    
    /**
     * Returns the maximum number of keys needed to win.
     *
     * @return the maximum number of keys
     */
    public static int getMaxKeys() {
        return Constant.MAX_KEYS;
    }

    /**
     * Changes the number of keys by the specified delta.
     * Triggers win condition check after the change.
     *
     * @param delta the number of keys to add (can be negative)
     */
    public static void changeKeys(int delta) {
        keys += delta;
        isWin = checkIsWin();
    }

    /**
     * Resets the game state to initial values (lives and keys).
     */
    public static void reset() {
        lives = Constant.START_LIVES;
        keys = Constant.START_KEYS;
    }
    
    /**
     * Checks whether the player has collected all required keys.
     *
     * @return true if all keys are collected
     */
    public static boolean checkIsKeys() {
        return getKeys() == getMaxKeys();
    }
    
    /**
     * Checks whether the win condition is met (all keys collected and door reached).
     *
     * @return true if player wins
     */
    public static boolean checkIsWin() {
        return (checkIsKeys() && door);
    }
    
    /**
     * Checks whether the player has run out of lives.
     *
     * @return true if game is over
     */
    public static boolean checkIsGameOver() {
        return getLives() <= 0;
    }
    
     /**
     * Returns the current game over state.
     *
     * @return true if the game is over
     */
    public static boolean getGameOverState() {
        return isGameOver;        
    }
    
    /**
     * Returns the current win state.
     *
     * @return true if the game is won
     */
    public static boolean getGameWinState() {     
        return isWin;
    }
    
    /**
     * Renders the life UI (hearts) using the provided graphics context and renderer.
     *
     * @param gc        the graphics context to draw on
     * @param renderer  the game renderer used to draw UI elements
     */
    public static void renderUILifes(GraphicsContext gc, GameRenderer renderer) {
        int life = getLives();
        int maxLife = getMaxLives();
        for (int i = 1; i <= maxLife; i++) {
            boolean type = life >= i;
            renderer.renderUILifes(gc, type, i);
        }
    }
    
    /**
     * Renders the key UI using the provided graphics context and renderer.
     *
     * @param gc        the graphics context to draw on
     * @param renderer  the game renderer used to draw UI elements
     */
    public static void renderUIKeys(GraphicsContext gc, GameRenderer renderer) {
        int keys = getKeys();
        int maxKeys = getMaxKeys();
        for (int i = 1; i <= maxKeys; i++) {
            boolean type = keys >= i;
            renderer.renderUIKeys(gc, type, i);
        }
    }
}
