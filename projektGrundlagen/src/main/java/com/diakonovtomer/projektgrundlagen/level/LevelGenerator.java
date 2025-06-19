/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.level;

import java.util.*;

/**
 * In development...
 * 
 * not working at the moment
 * 
 * @author adiakonov
 */
public class LevelGenerator {
    static final int WIDTH = 30;
    static final int HEIGHT = 10;
    static final char EMPTY = '.';
    static final char GROUND = '#';
    static final char KEY = '?';
    static final char SPIKE = '^';

    public static String[] main(String[] args) {
        String[] level = generateLevel(WIDTH, HEIGHT);
        System.out.println(level[0]);
        for (String line : level) {
            System.out.println(line);
        }
        return level;
    }

    static String[] generateLevel(int width, int height) {
        Random rand = new Random();
        char[][] map = new char[height][width];

        // Пустая карта
        for (int y = 0; y < height; y++) {
            Arrays.fill(map[y], EMPTY);
        }

        // Генерация пола
        Arrays.fill(map[height - 1], GROUND);

        // Платформы
        for (int y = height - 3; y >= 0; y -= 2) {
            int x = 0;
            while (x < width) {
                if (rand.nextDouble() < 0.3) {
                    int length = 2 + rand.nextInt(4); // длина 2–5
                    for (int i = 0; i < length && x + i < width; i++) {
                        map[y][x + i] = GROUND;
                    }

                    // Монетки на платформе
                    if (rand.nextDouble() < 0.6) {
                        int coins = 1 + rand.nextInt(length);
                        for (int i = 0; i < coins && x + i < width; i++) {
                            if (map[y][x + i] == GROUND && rand.nextDouble() < 0.8) {
                                map[y - 1][x + i] = KEY;
                            }
                        }
                    }

                    x += length + rand.nextInt(3);
                } else {
                    x += 1;
                }
            }
        }

        // Ловушки на полу
        for (int x = 0; x < width; x++) {
            if (rand.nextDouble() < 0.1) { // шанс 10%
                map[height - 2][x] = SPIKE;
            }
        }

        // Превращаем в строки
        String[] result = new String[height];
        for (int y = 0; y < height; y++) {
            result[y] = new String(map[y]);
        }
        return result;
    }
    
    static void printMap(char[][] map) {
        for (char[] row : map) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
}
