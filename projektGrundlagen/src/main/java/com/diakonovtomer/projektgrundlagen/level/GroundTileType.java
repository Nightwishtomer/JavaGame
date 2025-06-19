package com.diakonovtomer.projektgrundlagen.level;

public enum GroundTileType {
    NONE('.'),
    GROUND('#'),
    PLATFORM('~');
    
    public final char symbol;

    GroundTileType(char symbol) {
        this.symbol = symbol;
    }
    
}
