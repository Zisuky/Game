/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilz;

import entities.Crabby;
import java.awt.*;
import main.Game;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import objects.spike;
import static utilz.constants.Enemy.CRABBY;
import static utilz.constants.objectConstants.*;


/**
 *
 * @author loisu
 */
public class HelpMethods {

    public static Boolean CanMoveHere(float x, float y, float width, float height, int[][] lvData) {
        if (!IsSolid(x, y, lvData)) {
            if (!IsSolid(x + width, y + height, lvData)) {
                if (!IsSolid(x + width, y, lvData)) {
                    if (!IsSolid(x, y + height, lvData)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILE_SIZE;
        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }
        float xIndex = x / Game.TILE_SIZE;
        float yIndex = y / Game.TILE_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];
        if(value >= 48 || value < 0 || value != 11){
            return true;
        }
        return false;
    }

    public static float getEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILE_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILE_SIZE;
            int xOffset = (int) (Game.TILE_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else // Left
        {
            return currentTile * Game.TILE_SIZE;
        }
    }

    public static float getEntityYposUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int CurrentTile = (int) (hitbox.y / Game.TILE_SIZE);
        if (airSpeed > 0) {
            //falling
            int tileYpos = CurrentTile * Game.TILE_SIZE;
            int yOffset = (int) (Game.TILE_SIZE - hitbox.height);
            return tileYpos + yOffset - 1;
        } else //jumping
        {
            return CurrentTile * Game.TILE_SIZE;
        }

    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        //check pixel left and right
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData)) {
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData)) {
                return false;
            }
        }

        return true;

    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        if (xSpeed > 0) {
            return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        } else {
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        }
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData)) {
                return false;
            }
            if (!IsTileSolid(xStart + i, y + 1, lvlData)) {
                return false;
            }

        }

        return true;
    }

    public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXTile = (int) (firstHitbox.x / Game.TILE_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILE_SIZE);

        if (firstXTile > secondXTile) {
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        } else {
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
        }

    }

    public static int[][] GetLevelData(BufferedImage img) {
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48) {
                    value = 0;
                }
                lvlData[j][i] = value;
            }
        }
        return lvlData;
    }

    public static ArrayList<Crabby> GetCrabs(BufferedImage img) {
        ArrayList<Crabby> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == CRABBY) {
                    list.add(new Crabby(i * Game.TILE_SIZE, j * Game.TILE_SIZE));
                }
            }
        }
        return list;
    }
    
    
    public static Point GetPlayerSpawn(BufferedImage img) {
        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100) {
                    return new Point(i * Game.TILE_SIZE, j * Game.TILE_SIZE);
                }
            }
        }
        return new Point(1 * Game.TILE_SIZE, 1 * Game.TILE_SIZE);
    }

    public static ArrayList<spike> getSpikes(BufferedImage img) {
        ArrayList<spike> list = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++) 
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == SPIKE) 
                    list.add(new spike(i * Game.TILE_SIZE, j * Game.TILE_SIZE, SPIKE));
                
            }
        
        return list;
    }

}
