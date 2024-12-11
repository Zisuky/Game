/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package levels;

import entities.Crabby;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Game;
import objects.spike;
import utilz.HelpMethods;
import static utilz.HelpMethods.*;

/**
 *
 * @author loisu
 */
public class Level {
    private int [][] lvlData;
    private BufferedImage img;
    private ArrayList<Crabby> crabs;
    private ArrayList<spike> spikes;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;
    
    public Level(BufferedImage img){
        this.img = img;
        createLevelData();
        createEnemies();
        calcLvlOffsets();
        calcPlayerSpawn();
        createSpikes();
    }
    
    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }
    
    public int[][] getLevelData(){
        return lvlData;
    } 
    
    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILE_INT_WIDTH;
        maxLvlOffsetX = Game.TILE_SIZE * maxTilesOffset;
    }
    
    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }
    
    private void createEnemies() {
        crabs = GetCrabs(img);
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }
    
    
    public int getLvlOffset(){
        return maxLvlOffsetX;
    }
    
    public ArrayList<Crabby> getCrabs(){
        return crabs;
    }
    
    public Point getPlayerSpawn() {
	return playerSpawn;
    }

    private void createSpikes() {
        spikes = HelpMethods.getSpikes(img);
    }
    
    public ArrayList<spike> getSpike(){
        return spikes;
    }
}
