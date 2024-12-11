/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameStates;

import entities.*;
import main.Game;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import levels.LevelManager;
import objects.ObjectManager;
import ui.*;
import utilz.*;
import static utilz.constants.Environment.*;

/**
 *
 * @author loisu
 */
public class Playing extends State implements StateMethods {

    

    private Player player;
    private LevelManager levelManager;
    private enemyManager enemyManager;
    private pauseOverlay pauseOverlay;
    private gameOverOverlay gameOverOverLay;
    private levelCompletedOverlay levelCompletedOverlay;
    private ObjectManager objectManager;

    private boolean paused = false;
    private boolean lvlCompleted;
    private boolean gameOver;
    
    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    
    private int maxLvlOffsetX;

    private BufferedImage bgImg, bigCloud, smallCloud;
    private int[] smallCloudsPos;
    private Random rnd = new Random();

    


    public Playing(Game game) {
        super(game);
        initClasses();
        
        bgImg = loadSave.getSpriteAtlas(loadSave.PLAYING_BG);
        bigCloud = loadSave.getSpriteAtlas(loadSave.BIG_CLOUD);
        smallCloud = loadSave.getSpriteAtlas(loadSave.SMALL_CLOUD);
        smallCloudsPos = new int[8];
        
        for(int i = 0; i < smallCloudsPos.length; i++) 
            smallCloudsPos[i] = (int) (90 * Game.SCALE) + rnd.nextInt((int) (100 * Game.SCALE));
        
        calcLvlOffset();
        loadStartLevel();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new enemyManager(this);
        objectManager = new ObjectManager(this);
        
        player = new Player(150, 200, (int) (150 * Game.SCALE), (int) (150 * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        
        pauseOverlay = new pauseOverlay(this);
        gameOverOverLay = new gameOverOverlay(this);
        levelCompletedOverlay = new levelCompletedOverlay(this);
    }

    @Override
    public void update() {
        if (paused) {
            pauseOverlay.update();
        } else if (lvlCompleted) {
            levelCompletedOverlay.update();
        } else if (!gameOver) {
            levelManager.update();
            objectManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            checkCloseToBorder();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder) {
            xLvlOffset += diff - rightBorder;
        } else if (diff < leftBorder) {
            xLvlOffset += diff - leftBorder;
        }

        if (xLvlOffset > maxLvlOffsetX) {
            xLvlOffset = maxLvlOffsetX;
        } else if (xLvlOffset < 0) {
            xLvlOffset = 0;
        }

    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(bgImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        drawDecor(g);
        
        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);
        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver) {
            gameOverOverLay.draw(g);
        } else if (lvlCompleted) {
            levelCompletedOverlay.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked!");
        if (!gameOver);
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.setAttack(true);
            }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mousePressed(e);
            } else if (lvlCompleted) {
                levelCompletedOverlay.mousePressed(e);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mouseReleased(e);
            } else if (lvlCompleted) {
                levelCompletedOverlay.mouseReleased(e);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mouseMoved(e);
            } else if (lvlCompleted) {
                levelCompletedOverlay.mouseMoved(e);
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mouseDragged(e);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (gameOver) {
            gameOverOverLay.keyPressed(e);
        } else {
            switch (e.getKeyCode()) {

                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;

                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;

                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
        }

    }

    public void unpauseGame() {
        paused = false;

    }

    void windowFocusLost() {
        player.resetDirBoolean();
    }

    public Player getPlayer() {
        return player;
    }

    private void drawDecor(Graphics g) {
        for (int i = 0; i < 3; i++) {
            g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * 0.3), (int) (204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
        }

        for (int i = 0; i < smallCloudsPos.length; i++) {
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
        }

    }

    public void resetAll() {
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
    }

    public void loadNextLevel() {
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }

    private void loadStartLevel() {
        
        enemyManager.loadEnemy(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
	
    }

    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }
    
    
    public enemyManager getEnemyManager() {
        return enemyManager;
    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.lvlCompleted = levelCompleted;
    }
    
    public void checkObjectHit(Rectangle2D.Float attackBox) {
		objectManager.checkObjectHit(attackBox);
    }
    
    public void checkSpikesTouched(Player p) {
        objectManager.checkSpikesTouched(p);
    }
}
