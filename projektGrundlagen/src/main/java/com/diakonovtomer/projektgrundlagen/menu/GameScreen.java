/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.menu;

import com.diakonovtomer.projektgrundlagen.entities.EntitiesPlayer;
import com.diakonovtomer.projektgrundlagen.game.GameLoop;
import com.diakonovtomer.projektgrundlagen.game.GameCamera;
import com.diakonovtomer.projektgrundlagen.game.GameState;
import com.diakonovtomer.projektgrundlagen.Constant;
import com.diakonovtomer.projektgrundlagen.game.GameRenderer;
import com.diakonovtomer.projektgrundlagen.level.Level;
import com.diakonovtomer.projektgrundlagen.level.Generator;
import javafx.scene.canvas.Canvas;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents the main game screen where gameplay takes place.
 * <p>
 * This class initializes and contains all core components of the game, including:
 * the canvas for rendering, game camera, player, game loop, and a level loader.
 * Also provides a back button to return to the main menu.
 * </p>
 * 
 * <p>Components:</p>
 * <ul>
 *   <li>{@link Canvas} - main drawing surface</li>
 *   <li>{@link GameCamera} - manages what portion of the level is visible</li>
 *   <li>{@link Level} - handles map and layer logic</li>
 *   <li>{@link GameLoop} - main update/render loop</li>
 *   <li>{@link EntitiesPlayer} - the player character</li>
 * </ul>
 *
 * <p>Usage:</p>
 * <pre>{@code
 * GameScreen gameScreen = new GameScreen(800, 600, new GameScreen.GameScreenListener() {
 *     public void onBackToMenu() {
 *         // handle return to main menu
 *     }
 * });
 * Scene scene = gameScreen.getScene();
 * }</pre>
 * 
 * @author adiakonov
 */
public class GameScreen {
    private final Scene scene;
    private final String levelName = "level_output.xml";
    private final Level level1;
    private final GameLoop loop;
    private final Generator gen = new Generator(100, 10);
    
    /**
     * Constructs the game screen with a level, player, and rendering loop.
     *
     * @param width     the width of the scene in pixels
     * @param height    the height of the scene in pixels
     * @param listener  a callback to handle "back to menu" actions
     */
    public GameScreen(int width, int height, GameScreenListener listener) {
        GameState.reset();
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(Constant.GAMEWIDTH, Constant.GAMEHEIGHT); // Рвзмер Canvas
        root.setCenter(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GameCamera camera = new GameCamera(
            Constant.GAMEWIDTH,
            Constant.GAMEHEIGHT,
            Constant.LEVEL_WIDTH,
            Constant.LEVEL_HEIGHT,
            Constant.TILE_SIZE,
            true
        );      
        GameRenderer renderer = new GameRenderer(gc, camera);
        
        this.level1 = new Level(levelName, renderer);
        double[] pos = level1.findPlayerPositionPix();
        
        double playerY = pos[0];
        double playerX = pos[1];
        EntitiesPlayer entitiesPlayer = new EntitiesPlayer(playerY, playerX, level1);
        // Bottom panel with a back button
        VBox bottomPanel = new VBox(10);
        bottomPanel.setAlignment(Pos.CENTER);
        
        Button btnBack = new Button(Constant.MENU_BUTTON_BACK);
        btnBack.setFocusTraversable(false);
        btnBack.setOnAction(e -> listener.onBackToMenu());

        bottomPanel.getChildren().addAll(btnBack);
        root.setBottom(bottomPanel);
        BorderPane.setAlignment(btnBack, javafx.geometry.Pos.CENTER);
        root.setStyle(Constant.MENU_BUTTON_STYLE);      
        
        scene = new Scene(root, width, height);
        canvas.requestFocus(); // initial keyboard focus
        
        loop = new GameLoop(scene, gc, renderer, camera, level1, entitiesPlayer);
        loop.start();
    }
    
    /**
     * Returns the JavaFX Scene representing this game screen.
     * 
     * @return the current game scene
     */
    public Scene getScene() {
        return scene;
    }
    
    /**
     * Stops the main game loop.
     * Useful when exiting the game screen or pausing.
     */
    public void stop() {
        loop.stop();
    }

    /**
     * Interface for listening to actions from the game screen UI.
     */
    public interface GameScreenListener {
        void onBackToMenu();
    }
}
