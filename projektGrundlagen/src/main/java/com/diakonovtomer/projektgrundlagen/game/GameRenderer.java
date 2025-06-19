/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.game;

import com.diakonovtomer.projektgrundlagen.Constant;
import java.io.InputStream;
import java.util.Objects;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.text.Font;


/**
 *
 * @author adiakonov
 */
public class GameRenderer {         
    private final GameCamera camera;
    private final GraphicsContext gc; 
    
    
    private final Image backgroundFar;
    private final Image backgroundNear;
    //private Image backgroundFar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/BACKGROUND_01.png")));
    //private Image backgroundNear = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constant.ASSETS_URL + "BACKGROUND_02.png")));;
    
    public GameRenderer(GraphicsContext gc, GameCamera camera) {
        this.gc = gc;
        this.camera = camera;
        
        InputStream isFar = getClass().getResourceAsStream(Constant.ASSETS_URL + Constant.BACKGROUND_PNG_01);
        backgroundFar = new Image(Objects.requireNonNull(isFar));
        InputStream isNear = getClass().getResourceAsStream(Constant.ASSETS_URL + Constant.BACKGROUND_PNG_02);
        backgroundNear = new Image(Objects.requireNonNull(isNear));
    }

    public void renderCell(Color color, double posPixX, double posPixY, int width, int height) {
        renderRect(gc, color, posPixX, posPixY, width, height);
    }

    private void renderRect(GraphicsContext gc, Color color, double posPixX, double posPixY, int width, int height) {
        gc.setFill(color);
        double offsetX = camera.getOffsetX();
        double offsetY = camera.getOffsetY();       
        posPixX = posPixX - offsetX;
        posPixY = posPixY - offsetY;
        gc.fillRect(posPixX, posPixY, width, height); 
    }
    
    public void renderPlayer() {
        gc.setFill(Color.BLUE);
        gc.fillRect(camera.getPlayerCameraX(), camera.getPlayerCameraY(), Constant.TILE_SIZE, Constant.TILE_SIZE);
    }
    
    public void renderPlayerImg(Image image) {
        //Image image = new Image(getClass().getResourceAsStream(Constant.ASSETS_URL + imageName));
        gc.drawImage(image, camera.getPlayerCameraX(), camera.getPlayerCameraY(), Constant.TILE_SIZE, Constant.TILE_SIZE);
    }
    
    public void clear (GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }
    
    public void gameOver(GraphicsContext gc) {
        this.clear(gc);
        gc.setFill(Color.CRIMSON); // Цвет текста
        gc.setFont(new Font("Arial", 54)); // Размер и шрифт
        gc.fillText("Game Over", 180, 180); // Текст, координаты X/Y
    }
    
    public void gameWin(GraphicsContext gc) {
        this.clear(gc);
        gc.setFill(Color.CRIMSON); // Цвет текста
        gc.setFont(new Font("Arial", 54)); // Размер и шрифт
        gc.fillText("YOU WIN", 200, 180); // Текст, координаты X/Y
    }
    
    public void renderUILifes(GraphicsContext gc , boolean type,  int position){
        if (gc == null) return; // защита от NPE
        String name = (type) ? Constant.UI_LIFE_FULL : Constant.UI_LIFE_EMPTY;
        Image myImage  = new Image(getClass().getResourceAsStream(Constant.ASSETS_URL + name));
        gc.drawImage(myImage , 25 * position, 10, 20, 20);  // x и y — координаты в пикселях
    }
    
     public void renderUIKeys(GraphicsContext gc , boolean type,  int position){
        if (gc == null) return; // защита от NPE
        String name = (type) ? Constant.UI_KEY_FULL : Constant.UI_KEY_EMPTY;
        Image myImage  = new Image(getClass().getResourceAsStream(Constant.ASSETS_URL + name));
        gc.drawImage(myImage , Constant.GAMEWIDTH - (25 * position), 10, 25, 20);  // x и y — координаты в пикселях
    }
     
     
    public void renderTileImage( Image image, double posPixX, double posPixY, int width, int height) {
        double offsetX = camera.getOffsetX();
        double offsetY = camera.getOffsetY();       
        posPixX = posPixX - offsetX;
        posPixY = posPixY - offsetY;

        //Image image = new Image(getClass().getResourceAsStream(Constant.ASSETS_URL + imageName));
        gc.drawImage(image, posPixX, posPixY, width, height);
    }
    
   public void renderParallax() {
        double cameraX = camera.getOffsetX();

        renderParallaxLayer(backgroundFar, 0.3, cameraX);
        renderParallaxLayer(backgroundNear, 0.6, cameraX);
    }

    private void renderParallaxLayer(Image image, double scrollFactor, double cameraX) {
        if (image == null) return;

        double bgWidth = image.getWidth();
        double bgHeight = image.getHeight();
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        // Смещение по X с учётом коэффициента параллакса
        double offsetX = -(cameraX * scrollFactor) % bgWidth;
        if (offsetX > 0) offsetX -= bgWidth;

        // Заполняем по горизонтали фоном
        for (double x = offsetX; x < canvasWidth; x += bgWidth) {
            gc.drawImage(image, x, canvasHeight - bgHeight);
        }
    }
     
}
