package com.diakonovtomer.projektgrundlagen.level;

public enum ObjectTileType {
    NONE('.'),
    KEY('?'),
    SPIKE('^'),
    DOOR('0'),
    PLAYER('+');
    
    public final char symbol;
    
    ObjectTileType(char symbol) {
        this.symbol = symbol;
    }
}
