/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.game;

import com.diakonovtomer.projektgrundlagen.entities.EntitiesPlayer;
import com.diakonovtomer.projektgrundlagen.collision.CollisionHandler;
import com.diakonovtomer.projektgrundlagen.level.Level;                                                   
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import java.util.HashSet;
import java.util.Set;

/**
 * GameLoop handles the core update and rendering cycle of the game.
 * <p>
 * It processes player input, updates game objects, handles collisions, 
 * and renders the game world and UI elements on each frame.
 * It extends {@link AnimationTimer} to run the loop using JavaFX's animation system.
 * </p>
 *
 * <p>
 * The loop stops automatically when the game is either won or lost.
 * </p>
 *
 * @author adiakonov
 */
public class GameLoop extends AnimationTimer {
    private final GameRenderer renderer;
    private final GraphicsContext gc;
    private final EntitiesPlayer entitiesPlayer;
    private final Set<String> keys = new HashSet<>();
    private final Level level;
    private final CollisionHandler collisionHandler;
    private final GameCamera camera;

    /**
     * Constructs the GameLoop with all required dependencies.
     *
     * @param scene the JavaFX scene to capture key input from
     * @param gc the graphics context used for drawing
     * @param renderer the object responsible for rendering visuals
     * @param camera the game camera that follows the player
     * @param level1 the current game level
     * @param entitiesPlayer the player entity
     */
    public GameLoop(Scene scene, GraphicsContext gc, GameRenderer renderer, GameCamera camera, Level level, EntitiesPlayer entitiesPlayer) {
        this.gc = gc;
        this.level = level;
        this.entitiesPlayer = entitiesPlayer;
        this.camera = camera;
        this.renderer = renderer;
        this.collisionHandler = new CollisionHandler(level);
        scene.setOnKeyPressed(e -> keys.add(e.getCode().toString()));
        scene.setOnKeyReleased(e -> keys.remove(e.getCode().toString()));
    }

    /**
     * Called every frame while the game is running.
     * Handles game state updates, collision detection,
     * and rendering of the scene and UI.
     *
     * @param now the timestamp of the current frame given in nanoseconds
     */
    @Override
    public void handle(long now) {
        if (GameState.getGameOverState()) {
            renderer.gameOver(gc); // Display "Game Over"
            this.stop();
            return; 
        }
        if (GameState.checkIsWin()) {
            renderer.gameWin(gc); // Display "Win"
            this.stop();
            return; 
        }
        
        update();
        
        renderer.clear(gc);
        renderer.renderParallax();
        camera.update(entitiesPlayer.getX(), entitiesPlayer.getY());
        level.renderGround(); // Отрисовка земли
        level.renderObject(); // Отрисовка обьектов
        entitiesPlayer.render(renderer); // Отрисовка героя
        this.renderUI();
    }

    /**
     * Updates player movement, collision, and game object states.
     * Reads current key states to determine player actions.
     */
    private void update() {
        // Горизонтальное движение       
        if (keys.contains("LEFT")) {
            //player.moveLeft(collisionHandler);
            entitiesPlayer.moveLeft(collisionHandler);
        } else if (keys.contains("RIGHT")) {
            entitiesPlayer.moveRight(collisionHandler);
        } else {
            entitiesPlayer.stopMoving();
        }

        // Прыжок
        if (keys.contains("SPACE")) {
            entitiesPlayer.jump(); // Прыжок
        } else {
            entitiesPlayer.stopJump();
        }
        entitiesPlayer.applyGravity(); // Гравитация
        entitiesPlayer.jumpLimit(); // Ограничение по высоте прыжка
        entitiesPlayer.updatePosition(collisionHandler); // Проверка позиций и коллизий
        entitiesPlayer.checkObjectCollision(collisionHandler);
        entitiesPlayer.updateTeilPosition(); // синхронизируем тайловые координаты        
    }    
    
    /**
     * Renders the user interface including player lives and keys.
     */
    private void renderUI(){
        GameState.renderUILifes(gc, renderer);
        GameState.renderUIKeys(gc, renderer);
    }
}
