/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package levels;

import gameStates.GameState;
import main.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import utilz.loadSave;

/**
 *
 * @author loisu
 */
public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex = 0;
    
    public LevelManager(Game game){
          this.game = game;
          imfortMap();
          levels = new ArrayList<>();
          buildAllLevels();
    }
    
    private void imfortMap() {
        BufferedImage img = loadSave.getSpriteAtlas(loadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for(int j = 0; j <4; j++)
            for(int i = 0; i < 12; i++){
                int index = j*12 + i;
                levelSprite[index] = img.getSubimage(i*32, j*32, 32,32);
    }
    }
        
    public void draw(Graphics g, int lvlOffset){
      
        for (int j = 0; j < Game.TILE_INT_HEIGHT; j++) {
            for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], Game.TILE_SIZE * i - lvlOffset, Game.TILE_SIZE * j, Game.TILE_SIZE, Game.TILE_SIZE, null);
            }
        }
    }
    
    public void update(){}
    
    public Level getCurrentLevel(){
        return levels.get(lvlIndex);
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = loadSave.GetAllLevels();
        for (BufferedImage img : allLevels) 
            levels.add(new Level(img));
    }
    
    public void loadNextLevel() {
        lvlIndex++;
        if (lvlIndex >= levels.size()) {
            lvlIndex = 0;
            System.out.println("You've finished!");
            GameState.state = GameState.MENU;
        } 
        
        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemy(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }
    
    public int getAmountOfLevels() {
	return levels.size();
    }
    
}
