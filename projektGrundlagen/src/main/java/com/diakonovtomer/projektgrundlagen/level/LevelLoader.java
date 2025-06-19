/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.diakonovtomer.projektgrundlagen.level;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Utility class responsible for loading level layers from XML files.
 * 
 * The XML file is expected to define the level dimensions and contain multiple layers,
 * each with a name and rows of characters representing the layout.
 * 
 * Usage example:
 * <pre>
 *   char[][] groundLayer = LevelLoader.loadLayer("level1.xml", "ground");
 *   char[][] objectLayer = LevelLoader.loadLayer("level1.xml", "objects");
 * </pre>
 */
public class LevelLoader {
   
    
    
    /**
     * Loads a specific layer from a level XML file.
     * 
     * The method reads the XML file from the classpath root, parses it,
     * and extracts the 2D character array for the requested layer.
     * 
     * The XML should have the following structure:
     * <pre>
     * &lt;level width="30" height="10"&gt;
     *   &lt;layer name="ground"&gt;
     *     &lt;row&gt;##############################&lt;/row&gt;
     *     &lt;row&gt;#............................#&lt;/row&gt;
     *     ...
     *   &lt;/layer&gt;
     *   &lt;layer name="objects"&gt;
     *     ...
     *   &lt;/layer&gt;
     * &lt;/level&gt;
     * </pre>
     * 
     * @param filename the XML file name located (e.g., "level_output.xml")
     * @param layerName the name of the layer to load (e.g., "ground" or "objects")
     * @return a 2D array of characters representing the requested layer's map
     * @throws RuntimeException if the file is not found, the layer is missing,
     * or any error occurs during parsing
     */
    public static char[][] loadLayer(String filename, String layerName) {
        try {
            InputStream input = new FileInputStream(filename);
            try {
                if (input == null) throw new RuntimeException("Level file not found: " + filename);
                
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(input);

                Element root = doc.getDocumentElement();
                int width = Integer.parseInt(root.getAttribute("width"));
                int height = Integer.parseInt(root.getAttribute("height"));

                char[][] layer = new char[height][width];

                NodeList layers = root.getElementsByTagName("layer");
                for (int i = 0; i < layers.getLength(); i++) {
                    Element layerElement = (Element) layers.item(i);
                    if (layerElement.getAttribute("name").equals(layerName)) {
                        NodeList rows = layerElement.getElementsByTagName("row");
                        for (int y = 0; y < height; y++) {
                            String line = rows.item(y).getTextContent();
                            for (int x = 0; x < width; x++) {
                                layer[y][x] = line.charAt(x);
                            }
                        }
                        return layer;
                    }
                }

                throw new RuntimeException("Layer not found: " + layerName);

            } catch (IOException | RuntimeException | ParserConfigurationException | SAXException e) {
                throw new RuntimeException("Error loading layer: " + layerName);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            throw new RuntimeException("File not found: " + e.getMessage());
        }        
    }
}