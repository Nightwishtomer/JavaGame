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

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;


/**
 * Represents the "About" screen of the game.
 * <p>
 * This screen displays information about the program and provides
 * a button to navigate back to the main menu.
 * </p>
 * 
 * Usage example:
 * <pre>
 *     AboutScreen aboutScreen = new AboutScreen(800, 600, () -> {
 *         // handle back to menu event here
 *     });
 *     Scene scene = aboutScreen.getScene();
 * </pre>
 * 
 * The screen layout consists of a centered label and a bottom-aligned
 * button to return to the main menu.
 * 
 * The button uses the provided listener to notify about the back navigation.
 * 
 * @author adiakonov
 */
public class AboutScreen {
    private Scene scene;
    
    /**
     * Creates the AboutScreen with specified dimensions and a listener
     * for the back button action.
     *
     * @param width  the width of the scene in pixels
     * @param height the height of the scene in pixels
     * @param listener a callback interface to handle "Back to menu" button clicks
     */
    public AboutScreen(int width, int height, GameScreenListener listener) {
        BorderPane root = new BorderPane();
        
        
         // Многострочный текст в TextArea
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);  // Перенос по словам
        textArea.setEditable(false); // Запрет редактирования
        textArea.setText(
                
                
"Welcome to my 2D platformer, created in Java using JavaFX!\n" +
"The game is a classic platformer where the player has to overcome randomly generated levels, avoiding traps and collecting keys to open the door to the next level. Each level is unique thanks to the built-in generator that creates landscapes with platforms, cliffs, spikes, and obstacles.\n\n" +
"Main features:\n" +
" - 🎮 Keyboard controls for the hero (left, right, jump)\n" +
" - 🧠 Random level generation: hills, platforms at different heights, obstacles\n" +
" - 🔑 Collecting keys (10 in total) to open the door and progress further\n" +
" - ☠️ Dangerous spikes — three hits and it’s game over!\n" +
" - ✅ Passability check: every level is guaranteed to be completable\n" +
" - 🎨 Graphics implemented on Canvas, UI built with JavaFX\n" +
" - ⚙️ Modular project, easy to extend and customize\n\n" +
"Behind the scenes:\n" +
" - The main working prototype of the game was developed in one and a half weeks. This is my first project in Java, although I already had experience in development, including game projects, but exclusively in JavaScript using Canvas.\n" + 
" - The transition from JavaScript to Java was not exactly smooth. Here are a few observations and differences:\n" +
" - 💬 Typing in Java is much stricter, and at first, I often struggled with the compiler. However, errors are caught earlier, and the IDE offers excellent support.\n" +
" - 📦 Project structure in Java requires more discipline: classes, packages, separate files for everything.\n" +
" - 💡 Unlike JS, in Java you can't just 'invenz' a new field for an object on the fly — everything must be declared upfront. This forces you to plan the architecture.\n" +
" - ⚙️ JavaFX pleasantly surprised me — a convenient tool for creating UI and even drawing on Canvas. However, compared to HTML5 and Canvas API in JS, there are more layers of abstraction here.\n" +
" - 🔄 The event system and rendering follow a different philosophy: where in JS I was used to requestAnimationFrame and direct DOM handlers, in Java everything goes through listeners and explicitly defined update cycles.\n" +
               
                
                
                
"\n\n\n" +                 
"// ----------------------------------------------" +                 
"\n\n\n" +                                 
        
"Willkommen zu meinem 2D-Plattformer, entwickelt in Java mit JavaFX!\n\n" + 
"Das Spiel ist ein klassischer Plattformer, bei dem der Spieler zufällig generierte Level überwinden muss, Fallen ausweicht und Schlüssel sammelt, um die Tür zum nächsten Level zu öffnen. Jeder Level ist einzigartig dank des eingebauten Generators, der Landschaften mit Plattformen, Klippen, Stacheln und Hindernissen erzeugt.\n\n" + 
"Hauptmerkmale:\n" + 
" - 🎮 Steuerung des Helden per Tastatur (links, rechts, springen)\n" + 
" - 🧠 Zufällige Levelgenerierung: Hügel, Plattformen in verschiedenen Höhen, Hindernisse\n" + 
" - 🔑 Sammeln von Schlüsseln (insgesamt 10), um die Tür zu öffnen und weiterzukommen\n" + 
" - ☠️ Gefährliche Stacheln — drei Treffer und das Spiel ist vorbei!\n" + 
" - ✅ Begehbarkeitsprüfung: Jeder Level ist garantiert spielbar\n" + 
" - 🎨 Grafik mit Canvas umgesetzt, Benutzeroberfläche mit JavaFX\n" + 
" - ⚙️ Modulares Projekt, leicht erweiterbar und anpassbar\n\n" + 
"Ein Blick hinter die Kulissen:\n" + 
" - Der Hauptprototyp des Spiels wurde in anderthalb Wochen entwickelt. Dies ist mein erstes Projekt in Java, obwohl ich bereits Erfahrung in der Entwicklung hatte, einschließlich Spieleprojekten, jedoch ausschließlich mit JavaScript und Canvas.\n" + 
" - Der Umstieg von JavaScript auf Java verlief nicht ganz reibungslos. Hier ein paar Beobachtungen und Besonderheiten:\n" + 
" - 💬 Die Typisierung in Java ist deutlich strenger, und anfangs hatte ich oft mit dem Compiler zu kämpfen. Dafür werden Fehler früher erkannt und die IDE bietet exzellente Unterstützung.\n" + 
" - 📦 Die Projektstruktur in Java erfordert mehr Disziplin: Klassen, Pakete, für alles separate Dateien.\n" + 
" - 💡 Im Gegensatz zu JS kann man in Java nicht einfach „on the fly“ neue Felder zu einem Objekt hinzufügen — alles muss vorher deklariert sein. Das zwingt einen, die Architektur sorgfältig zu planen.\n" + 
" - ⚙️ JavaFX hat mich positiv überrascht — ein praktisches Tool zur Erstellung von Benutzeroberflächen und sogar zum Zeichnen auf Canvas. Allerdings gibt es hier mehr Abstraktionsebenen als bei HTML5 und Canvas API in JS.\n" + 
" - 🔄 Das Event-System und Rendering folgen einer anderen Philosophie: Während ich in JS an requestAnimationFrame und direkte DOM-Handler gewöhnt war, läuft in Java alles über Listener und explizit definierte Update-Schleifen.\n"
                
        );
        
        // Оборачиваем TextArea в ScrollPane, чтобы при большом тексте была прокрутка
        ScrollPane scrollPane = new ScrollPane(textArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        
        
        //Label label = new Label("About the program");
        Button btnBack = new Button("Back to menu");
        btnBack.setOnAction(e -> listener.onBackToMenu());
        root.setCenter(scrollPane);
        //root.setCenter(label);
        root.setBottom(btnBack);
        BorderPane.setAlignment(btnBack, javafx.geometry.Pos.CENTER);
        root.setStyle(Constant.MENU_BUTTON_STYLE);
        scene = new Scene(root, width, height);
    }

    /**
     * Returns the JavaFX Scene associated with this AboutScreen.
     * 
     * @return the Scene displaying the About screen content
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Listener interface for handling events on the AboutScreen.
     */
    public interface GameScreenListener {
        /**
         * Called when the user wants to navigate back to the main menu.
         */
        void onBackToMenu();
    }
}
