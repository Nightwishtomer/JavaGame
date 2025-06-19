/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.menu;

import com.diakonovtomer.projektgrundlagen.Constant;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Represents the Settings screen of the game.
 * <p>
 * This screen displays a simple settings menu with a label and a button
 * to navigate back to the main menu.
 * </p>
 * 
 * Usage example:
 * <pre>
 *     SettingsScreen settingsScreen = new SettingsScreen(800, 600, () -> {
 *         // handle back to menu event here
 *     });
 *     Scene scene = settingsScreen.getScene();
 * </pre>
 * 
 * The layout consists of a centered label and a bottom-aligned
 * button for returning to the main menu.
 * 
 * The back button triggers a callback through the provided listener.
 * 
 * @author adiakonov
 */
public class SettingsScreen {
    
    private Scene scene;

    /**
     * Constructs a SettingsScreen with specified width, height, and a listener
     * to handle the "Back to menu" button action.
     *
     * @param width    the width of the scene in pixels
     * @param height   the height of the scene in pixels
     * @param listener a callback interface to handle back navigation events
     */
    public SettingsScreen(int width, int height, GameScreenListener listener) {
        BorderPane root = new BorderPane();

        Label label = new Label("Settings menu");
        Button btnBack = new Button("Back to menu");

        btnBack.setOnAction(e -> listener.onBackToMenu());

        root.setCenter(label);
        root.setBottom(btnBack);
        BorderPane.setAlignment(btnBack, javafx.geometry.Pos.CENTER);
        root.setStyle(Constant.MENU_BUTTON_STYLE);

        scene = new Scene(root, width, height);
    }

    /**
     * Returns the JavaFX Scene representing this Settings screen.
     * 
     * @return the Scene object with the settings menu UI
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Listener interface to handle actions on the Settings screen.
     */
    public interface GameScreenListener {
        /**
         * Called when the user chooses to navigate back to the main menu.
         */
        void onBackToMenu();
    }
}
