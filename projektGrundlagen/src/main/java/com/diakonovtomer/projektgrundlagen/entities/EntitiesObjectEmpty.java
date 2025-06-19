/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.entities;

import com.diakonovtomer.projektgrundlagen.Constant;
import javafx.scene.paint.Color;

/**
 * Represents an empty (non-interactive) entity object in the game world.
 * <p>
 * This class extends {@link EntitiesObjects} and defines a transparent, non-solid
 * tile used to represent empty space in the game grid. It does not have
 * any interaction or rendering behavior beyond occupying a position in the game field.
 * </p>
 * 
 * @author adiakonov
 */
public class EntitiesObjectEmpty extends EntitiesObjects {
    
    /**
     * Constructs a new {@code EntitiesObjectEmpty} at the specified grid position.
     *
     * @param x the X-coordinate (in grid units)
     * @param y the Y-coordinate (in grid units)
     */
    public EntitiesObjectEmpty(int x, int y) {
        super(x, y, Constant.EMPTY);
        this.color = Color.TRANSPARENT;//Color.LIGHTGRAY;
        this.isSolid = false;
    }
}
