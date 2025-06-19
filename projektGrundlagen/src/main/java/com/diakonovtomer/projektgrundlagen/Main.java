/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen;

// Imports
import com.diakonovtomer.projektgrundlagen.menu.MenuScreen;
import com.diakonovtomer.projektgrundlagen.menu.GameScreen;
import com.diakonovtomer.projektgrundlagen.menu.AboutScreen;
import com.diakonovtomer.projektgrundlagen.menu.SettingsScreen;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class of the Platformer JavaFX application.
 * <p>
 * Manages the initialization and switching between the main menu,
 * game screen, about screen, and settings screen.
 * </p>
 * <p>
 * The entry point {@code main()} calls {@code launch()},
 * which starts the JavaFX application and invokes {@code start()}.
 * </p>
 * 
 * @author Artiem Diakonov
 */
public class Main extends Application {
  
    private Stage primaryStage;
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private AboutScreen aboutScreen;
    private SettingsScreen settingsScreen;
    
    /**
     * Initializes the JavaFX application.
     * Sets the initial screen, initializes menu and settings screens.
     *
     * @param stage главный Stage приложения / the primary application stage
     */
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        // Initializing a JavaFX application
        primaryStage.setTitle(Constant.APP_TITLE);
        menuScreen = new MenuScreen(Constant.WIDTH, Constant.HEIGHT, new MenuScreen.MenuListener() {
            @Override
            public void onStartGame() {
                showGameScreen();
            }

            @Override
            public void onShowScores() {
                showAboutScreen();
            }

            @Override
            public void onShowSettings() {
                showSettingsScreen();
            }

            @Override
            public void onExit() {
                primaryStage.close();
            }
        });
        
        aboutScreen = new AboutScreen(Constant.WIDTH, Constant.HEIGHT, () -> showMainMenu());
        settingsScreen = new SettingsScreen(Constant.WIDTH, Constant.HEIGHT, () -> showMainMenu());
 
        showMainMenu();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    

    /**
     * Switches the view to the main menu.
     * Releases resources from the game screen if it was active.
     */
    private void showMainMenu() {
        if (gameScreen != null) {
            gameScreen.stop(); // <-- остановка GameLoop
            gameScreen = null;
            System.gc(); 
        }
        primaryStage.setScene(menuScreen.getScene());
    }
    
    /**
     * Starts and displays the game screen.
     */
    private void showGameScreen() {
        gameScreen = new GameScreen(Constant.WIDTH, Constant.HEIGHT, () -> showMainMenu());
        primaryStage.setScene(gameScreen.getScene());
    }
    
    /**
     * Displays the "About" screen.
     */
    private void showAboutScreen() {
        primaryStage.setScene(aboutScreen.getScene());
    }
    
    /**
     * Displays the settings screen.
     */
    private void showSettingsScreen() {
        primaryStage.setScene(settingsScreen.getScene());
    }

    /**
     * Application entry point.
     * Calls {@code launch()} to start JavaFX.
     *
     * @param args аргументы командной строки / command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }    
}