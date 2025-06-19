/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.menu;

/**
 *
 * @author adiakonov
 */
import com.diakonovtomer.projektgrundlagen.Constant;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


/**
 * Represents the main menu screen of the game.
 * <p>
 * This screen provides buttons to start the game, show scores (or "About"),
 * open settings, and exit the application.
 * </p>
 * 
 * The buttons are arranged vertically and centered.
 * 
 * <p>Usage example:</p>
 * <pre>
 *     MenuScreen menu = new MenuScreen(800, 600, new MenuScreen.MenuListener() {
 *         {@literal @}Override
 *         public void onStartGame() {
 *             // start game logic
 *         }
 *         {@literal @}Override
 *         public void onShowScores() {
 *             // show scores or about screen
 *         }
 *         {@literal @}Override
 *         public void onShowSettings() {
 *             // open settings menu
 *         }
 *         {@literal @}Override
 *         public void onExit() {
 *             // exit application
 *         }
 *     });
 *     Scene scene = menu.getScene();
 * </pre>
 * 
 * @author adiakonov
 */
public class MenuScreen {

    private Scene scene;

    /**
     * Constructs the MenuScreen with the given width and height,
     * and sets up button actions using the provided listener.
     * 
     * @param width    the width of the scene in pixels
     * @param height   the height of the scene in pixels
     * @param listener a listener to handle menu button actions
     */
    public MenuScreen(int width, int height, MenuListener listener) {
        Button btnStart = new Button(Constant.MENU_BUTTON_START);
        Button btnAbout = new Button(Constant.MENU_BUTTON_ABOUT);
        Button btnSettings = new Button(Constant.MENU_BUTTON_SETTINGS);
        Button btnExit = new Button(Constant.MENU_BUTTON_EXIT);
               
        btnStart.setOnAction(e -> listener.onStartGame());
        btnAbout.setOnAction(e -> listener.onShowScores());
        btnSettings.setOnAction(e -> listener.onShowSettings());
        btnExit.setOnAction(e -> listener.onExit());

        VBox menu = new VBox(20, btnStart, btnAbout, btnSettings, btnExit);
        menu.setAlignment(Pos.CENTER);
        menu.setStyle(Constant.MENU_BUTTON_STYLE);

        scene = new Scene(menu, width, height);
    }

    /**
     * Returns the JavaFX Scene representing this menu screen.
     * 
     * @return the Scene object with the menu UI
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Listener interface to handle menu button actions.
     */
    public interface MenuListener {
        /**
         * Called when the Start Game button is pressed.
         */
        void onStartGame();
        
        /**
         * Called when the About/Scores button is pressed.
         */
        void onShowScores();
        
        /**
         * Called when the Settings button is pressed.
         */
        void onShowSettings();
        
        /**
         * Called when the Exit button is pressed.
         */
        void onExit();
    }
}
