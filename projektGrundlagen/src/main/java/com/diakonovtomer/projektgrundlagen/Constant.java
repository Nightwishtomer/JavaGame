package com.diakonovtomer.projektgrundlagen;

/**
 * The Constant class contains all constants used in the Platformer game.
 * These values define interface parameters, map settings, player properties,
 * and other configuration values.
 * <p>
 * This class is not intended to be instantiated.
 * </p>
 * 
 * @author Artiem Diakonov
 */
public final class Constant {

    /**
     * Private constructor prevents instantiation of this class.
     */
    private Constant() {}

    // App
    /** Application title. */
    public static final String APP_TITLE = "Platformer Game by Artiem Diakonov";

    // Menu
    /** Button text: Start game. */
    public static final String MENU_BUTTON_START = "Start game";
    /** Button text: About. */
    public static final String MENU_BUTTON_ABOUT = "About the program";
    /** Button text: Settings. */
    public static final String MENU_BUTTON_SETTINGS = "Settings";
    /** Button text: Exit. */
    public static final String MENU_BUTTON_EXIT = "Exit";
    /** Button text: Back to menu. */
    public static final String MENU_BUTTON_BACK = "Back to menu";

    /** Style for menu buttons. */
    public static final String MENU_BUTTON_STYLE = "-fx-padding: 20;";

    // Map
    /** Map layer name: ground. */
    public static final String MAP_LAYER_NAME_GROUND = "ground";
    /** Map layer name: object. */
    public static final String MAP_LAYER_NAME_OBJECT = "object";

    // Tiles
    /** Symbol for empty space. */
    public static final char EMPTY = '.';
    /** Symbol for ground. */
    public static final char GROUND = '#';
    /** Symbol for platform. */
    public static final char PLATFORM = '~';
    /** Symbol for spikes. */
    public static final char SPIKE = '^';
    /** Symbol for player. */
    public static final char PLAYER = '+';
    /** Symbol for key. */
    public static final char KEY = '?';
    /** Symbol for door. */
    public static final char DOOR = '0';

    /** Tile size in pixels. */
    public static final int TILE_SIZE = 32;

    // Window
    /** Application window height. */
    public static final int HEIGHT = 410;
    /** Application window width. */
    public static final int WIDTH = 700;
    /** Game screen height. */
    public static final int GAMEHEIGHT = 320;
    /** Game screen width. */
    public static final int GAMEWIDTH = 640;

    // Level
    /** Level width in tiles. */
    public static final int LEVEL_WIDTH = 100;
    /** Level height in tiles. */
    public static final int LEVEL_HEIGHT = 10;

    // Player
    /** Player height in pixels. */
    public static final int PLAYER_HEIGHT = 32;
    /** Player width in pixels. */
    public static final int PLAYER_WIDTH = 32;
    /** Player hitbox height (percentage of total height). */
    public static final double PLAYER_HEIGHT_HITBOX = 0.8;
    /** Player hitbox width (percentage of total width). */
    public static final double PLAYER_WIDTH_HITBOX = 0.9;
    /** Gravity acceleration. */
    public static final double GRAVITY = 0.1;
    /** Jump force. */
    public static final double JUMP_FORCE = -4.5;
    /** Movement speed on ground. */
    public static final double MOVE_SPEED = 3;
    /** Movement speed in air. */
    public static final double MOVE_SPEED_FALL = 2.5;
    /** Maximum fall speed. */
    public static final double MAX_FALL_SPEED = 5;
    /** Maximum jump height in pixels. */
    public static final double MAX_JUMP_HEIGHT = 3 * TILE_SIZE;

    /** God mode (cheat mode). */
    public static final boolean GOD_MODE = false;

    // GameState
    /** Starting number of lives. */
    public static final int START_LIVES = 3;
    /** Maximum number of lives. */
    public static final int MAX_LIVES = 3;
    /** Starting number of keys. */
    public static final int START_KEYS = 0;
    /** Maximum number of keys. */
    public static final int MAX_KEYS = 10;

    // Renderer
    /** Path to assets folder. */
    public static final String ASSETS_URL = "/img/";

    // Renderer - UI
    /** Icon for full life. */
    public static final String UI_LIFE_FULL = "life_full.png";
    /** Icon for empty life. */
    public static final String UI_LIFE_EMPTY = "life_empty.png";
    /** Icon for key collected. */
    public static final String UI_KEY_FULL = "key_full.png";
    /** Icon for missing key. */
    public static final String UI_KEY_EMPTY = "key_empty.png";

    // Renderer - OBJECTS
    /** Platform image. */
    public static final String PLATFORM_PNG = "platform.png";
    /** Ground image. */
    public static final String GROUND_PNG = "ground.png";
    /** Key image. */
    public static final String KEY_PNG = "key.png";
    /** Spikes image. */
    public static final String SPIKES_PNG = "spikes.png";
    /** Door image. */
    public static final String DOOR_PNG = "door.png";

    // Renderer - PLAYER
    /** Player image (facing left). */
    public static final String PLAYER_PNG_L = "player_l.png";
    /** Player image (facing right). */
    public static final String PLAYER_PNG_R = "player_r.png";
    /** Parallax background image 1. */
    public static final String BACKGROUND_PNG_01 = "background_01.png";
    /** Parallax background image 2. */
    public static final String BACKGROUND_PNG_02 = "background_02.png";       
    
    // Generator
    /** Minimum height of the ground (used for noise-based generation) */
    public static final int GEN_MIN_GROUND_HEIGHT = 1;
    /** Maximum height of the ground (used for noise-based generation) */
    public static final int GEN_MAX_GROUND_HEIGHT = 5;
    
    public static final double GEN_PERLIN_NOISE_SCALE_1D_SCALE_MIN = 0.02;
    public static final double GEN_PERLIN_NOISE_SCALE_1D_SCALE_MAX = 0.045;
    public static final double GEN_PERLIN_NOISE_SCALE_2D_SCALE = 0.07;
}
