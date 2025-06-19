/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.level;

import java.io.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import com.diakonovtomer.projektgrundlagen.Constant;

/**
 * The {@code Generator} class is responsible for procedurally generating a 2D platformer level.
 * It creates two layers: a ground layer (platforms, ground) and an object layer (player, keys, spikes, door).
 * The level includes random terrain generation using noise, placement of interactive elements,
 * and a reachability check to ensure the level is solvable.
 * <p>
 * The level is generated during the instantiation of this class and automatically saved to an XML file.
 * 
 * <p><b>Usage example:</b>
 * <pre>{@code
 * Generator generator = new Generator(100, 10);
 * }</pre>
 * 
 * @author adiakonov
 */
public class Generator {
    
     /** Width of the level in tiles */
    private final int width;
    
    /** Height of the level in tiles */
    private final int height;
    
    /** 2D array representing the ground layer (terrain and platforms) */
    private final GroundTileType[][] groundLayer;
    
    /** 2D array representing the object layer (player, keys, spikes, door) */
    private final ObjectTileType[][] objectLayer;
    
    /** Stores the Y and X position of the player [Y, X] */
    private final int[] playerPosition = {-1, -1};
    
    /** Stores the Y and X position of the door [Y, X] */
    private final int[] doorPosition = {-1, -1};
    
    /** Random number generator for all procedural elements */
    private final Random random;

    /** OpenSimplex2 noise generator for terrain shaping */
    private final OpenSimplex2F noise;

    /**
     * Constructs a new {@code Generator} object, generates the level,
     * prints it to console, and saves it to an XML file.
     *
     * @param width  the width of the level in tiles
     * @param height the height of the level in tiles
     */
    public Generator(int width, int height) {
        this.width = width;
        this.height = height;
        this.groundLayer = new GroundTileType[height][width];
        this.objectLayer = new ObjectTileType[height][width];
        long seed = System.currentTimeMillis();
        this.random = new Random(seed);
        this.noise = new OpenSimplex2F(seed);
        generateLevel();
        //printLevel();
        saveLevelToFile("level_output.xml");
    }   
    
    /**
     * Generates the entire level:
     * <ul>
     *   <li>Initializes the arrays</li>
     *   <li>Generates terrain using noise</li>
     *   <li>Places platforms, gaps, player, door, keys, and spikes</li>
     *   <li>Checks whether the level is passable</li>
     * </ul>
     */
    private void generateLevel() {
        // Creating empty arrays
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                groundLayer[y][x] = GroundTileType.NONE;
                objectLayer[y][x] = ObjectTileType.NONE;
            }   
        }
        
        // noise Perlin settings
        double scale = random.nextDouble(Constant.GEN_PERLIN_NOISE_SCALE_1D_SCALE_MAX - Constant.GEN_PERLIN_NOISE_SCALE_1D_SCALE_MIN) + Constant.GEN_PERLIN_NOISE_SCALE_1D_SCALE_MIN; // noise scale: smaller = smoother
        // noise Perlin
        for (int x = 0; x < width; x++) {
            double noiseValue = noise.noise2(x * scale, 0);
            double normalized = (noiseValue + 1) / 2.0; // from -1..1 -> 0..1
            int groundHeight = Constant.GEN_MIN_GROUND_HEIGHT + (int)((Constant.GEN_MAX_GROUND_HEIGHT - Constant.GEN_MIN_GROUND_HEIGHT) * normalized);
            for (int y = 0; y < height; y++) {
                int flippedY = height - 1 - y;
                if (y < groundHeight) {
                    groundLayer[flippedY][x] = GroundTileType.GROUND;
                } else {
                    groundLayer[flippedY][x] = GroundTileType.NONE;
                }
            }
        }

        // installation of topsoil
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (groundLayer[y][x] == GroundTileType.GROUND) {
                    groundLayer[y][x] = GroundTileType.PLATFORM;
                    break;
                } 
            }
        }
        //setPlatformsWithNoise();
        
        setPlatforms(); // We install platforms
        addPlatformGaps();
        setPlayer(); // Installing a Player on a Map
        setDoor(); // Installing the Door on the Card
        setKeys(); // Installing Keys on the Card
        setSpike(); // Installing Spikes on the Card
        isReachable(); // Checking the passability. If not, create the map again
    }
    
    private void setPlatformsWithNoise() {
        double scale = Constant.GEN_PERLIN_NOISE_SCALE_2D_SCALE; // You can experiment
        for (int x = 0; x < width; x++) {
            double noiseValue = noise.noise2(x * scale, 500); // 500 - shift for another layer
            double normalized = (noiseValue + 1) / 2.0;
            int platformY = height - 4 - (int)(normalized * 4); // Example: from height-4 to height-8
            groundLayer[platformY][x] = GroundTileType.PLATFORM;
        }
    }
    
    /**
     * Adds occasional horizontal gaps to existing platforms.
     * Improves level difficulty and player navigation.
     */
    private void addPlatformGaps() {
        for (int x = 0; x < width - 3; x++) {
            if (random.nextFloat() < 0.15f) { // 15% шанс обрыва
                int gapSize = 1 + random.nextInt(2); // длина обрыва
                for (int i = 0; i < gapSize; i++) {
                    for (int y = 0; y < height-2; y++) {
                        if (groundLayer[y][x + i] == GroundTileType.PLATFORM) {
                            groundLayer[y][x + i] = GroundTileType.NONE;
                        }
                    }
                }
                x += gapSize;
            }
        }
    }

    /**
     * Creates a layered platform structure at predefined heights.
     * Adds horizontal randomness and gaps.
     */
    private void setPlatforms() {
        int[] PLATFORM_HEIGHT = {height - 4, (height - 6), (height - 8)};
        for (int platY : PLATFORM_HEIGHT) {
            int x = 0;
            while (x < width - 1) {
                // We are deciding whether there will be a platform here
                if (random.nextFloat() < 0.3f) { // 30% chance of a platform appearing
                    int platformLength = 1 + random.nextInt(4); // Platform length 
                    for (int i = 0; i < platformLength && x + i < width; i++) {
                        groundLayer[platY][x + i] = GroundTileType.PLATFORM;
                    }
                    x += platformLength + random.nextInt(2); // + interval
                } else {
                    x += 1 + random.nextInt(4); // gap between Platform
                }
            }
        }
    }
    
    /**
     * Tries to place a game object (e.g. player, door, key) in a valid empty spot.
     *
     * @param type The type of object to place
     * @param minX Minimum X coordinate
     * @param maxX Maximum X coordinate
     * @return true if the object was placed successfully, false otherwise
     */
    private boolean placeObject(ObjectTileType type, int minX, int maxX) {
        int randomX = random.nextInt(maxX - minX) + minX;
        List<Integer> possibleYs = new ArrayList<>();

        for (int y = 0; y < height - 1; y++) {
            boolean isAir = groundLayer[y][randomX] == GroundTileType.NONE;
            boolean isSupported = groundLayer[y + 1][randomX] == GroundTileType.GROUND || groundLayer[y + 1][randomX] == GroundTileType.PLATFORM;
            boolean isEmpty = objectLayer[y][randomX] == ObjectTileType.NONE;

            if (isAir && isSupported && isEmpty) {
                possibleYs.add(y);
            } 
        }

        if (!possibleYs.isEmpty()) {
            int randomY = possibleYs.get(random.nextInt(possibleYs.size()));
            objectLayer[randomY][randomX] = type;

            if (type == ObjectTileType.PLAYER) {
                playerPosition[0] = randomY;
                playerPosition[1] = randomX;
            } else if (type == ObjectTileType.DOOR) {
                doorPosition[0] = randomY;
                doorPosition[1] = randomX;
            }

            return true;
        }
        return false;
    }

    /**
     * Places the player at a valid starting location near the left edge.
     */
    private void setPlayer() {
         int attempts = 0;
         while (attempts < 1000) {
             if (placeObject(ObjectTileType.PLAYER, 0, 7)) {
                return;
            }
            attempts++;
        }
    }
        
    /**
     * Places the door at a valid location near the right edge.
     */
    private void setDoor() {
        int attempts = 0;
        while (attempts < 1000) {
            if (placeObject(ObjectTileType.DOOR, width - 10, width - 1)) {
                return;
            }
            attempts++;
        }
    }
    
    /**
     * Randomly places 10 keys throughout the map.
     */
    private void setKeys() {
        int placed = 0;
        int attempts = 0;
        int required = 10;
        
        while (placed < required && attempts < 1000) {
            if (placeObject(ObjectTileType.KEY, 0, width - 1)) {
                placed++;
            }
            attempts++;
        }
    }
            
    /**
     * Randomly places a number of spike tiles (5 to 10).
     */
    private void setSpike() {
        int placed = 0;
        int attempts = 0;
        int minSpikes = 5;
        int maxSpikes = 11;
        int required = random.nextInt(maxSpikes - minSpikes) + minSpikes; // 5..10
        
        while (placed < required && attempts < 1000) {
            if (placeObject(ObjectTileType.SPIKE, 0, width - 1)) {
                placed++;
            }
            attempts++;
        }
    }
    
    /**
     * Recursively checks if the door is reachable from the player's starting position.
     * If not, regenerates the level.
     */
    private void isReachable() {
        int startX = playerPosition[1], startY = playerPosition[0];
        int endX = doorPosition[1], endY = doorPosition[0];
        boolean[][] visited = new boolean[height][width];
        boolean reachable = dfs(startY, startX, endY, endX, visited);
        if (!reachable) generateLevel(); 
    }

    /**
     * Performs a depth-first search to determine if a valid path exists between two tiles.
     *
     * @param y     Current Y coordinate
     * @param x     Current X coordinate
     * @param endY  Goal Y coordinate
     * @param endX  Goal X coordinate
     * @param visited 2D array tracking visited tiles
     * @return true if the path exists, false otherwise
     */
    private boolean dfs(int y, int x, int endY, int endX, boolean[][] visited) {
        // Checking for out of bounds
        if (x < 0 || x >= width || y < 0 || y >= height) return false;

        // If already visited - exit
        if (visited[y][x]) return false;

        // Checking if you can get here
        if (!isPassable(y, x)) return false;

        // If you have reached your goal
        if (y == endY && x == endX) return true;

        visited[y][x] = true;

        //Possible directions of movement: down, up, right, left
        int[][] directions = {
            {1, 0},   // down
            {-1, 0},  // up
            {0, 1},   // right
            {0, -1}   // left
        };
        
        for (int[] dir : directions) {
            int newY = y + dir[0];
            int newX = x + dir[1];
            if (dfs(newY, newX, endY, endX, visited)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether a tile can be traversed.
     *
     * @param y Y coordinate
     * @param x X coordinate
     * @return true if the tile is walkable or empty
     */
    private boolean isPassable(int y, int x) {
        boolean groundPassable = groundLayer[y][x] == GroundTileType.NONE;
        ObjectTileType obj = objectLayer[y][x];
        boolean objectPassable = obj == ObjectTileType.NONE || obj == ObjectTileType.PLAYER || obj == ObjectTileType.DOOR || obj == ObjectTileType.KEY;
        return groundPassable && objectPassable;
    }
     
    /**
     * Prints both ground and object layers to the console for debugging.
     */
    public void printLevel() {
        System.out.println("Level width=" + width + " height=" + height);
        System.out.println("Layer \"ground\"");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(groundLayer[y][x].symbol);
            }
            System.out.println("");
        }      
        System.out.println("");
        System.out.println("");
        System.out.println("Layer \"object\"");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(objectLayer[y][x].symbol);
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("");
        System.out.println("0" + groundLayer[0][0]);
        System.out.println("9" + groundLayer[9][0]);
    }
        
    /**
     * Saves the generated level to an XML file with both layers.
     *
     * @param filename The name of the output file
     */
    public void saveLevelToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // XML header
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.printf("<level width=\"%d\" height=\"%d\">%n", width, height);

            // Ground layer
            writer.println("    <layer name=\"ground\">");
            for (int y = 0; y < height; y++) {
                writer.print("        <row>");
                for (int x = 0; x < width; x++) {
                    writer.print(groundLayer[y][x].symbol);
                }
                writer.println("</row>");
            }
            writer.println("    </layer>");

            // Object layer
            writer.println("    <layer name=\"object\">");
            for (int y = 0; y < height; y++) {
                writer.print("        <row>");
                for (int x = 0; x < width; x++) {
                    writer.print(objectLayer[y][x].symbol);
                }
                writer.println("</row>");
            }
            writer.println("    </layer>");

            writer.println("</level>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GroundTileType[][] getGround() {
        return groundLayer;
    }
  
    public ObjectTileType[][] getObject() {
        return objectLayer;
    }
    
}
