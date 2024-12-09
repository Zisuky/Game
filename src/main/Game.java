/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;



import gameStates.*;
import java.awt.Graphics;
import utilz.loadSave;


/**
 *
 * @author loisu
 */
public class Game implements Runnable{
    private GameWindow gamewindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS = 120;
    private final int UPS = 200;
    
    private Playing playing;
    private Menu menu;
    
    public static int TILES_DEFAULE_SIZE = 32;
    public static float SCALE = 1.5f;
    public static int TILE_INT_WIDTH = 26;
    public static int TILE_INT_HEIGHT = 14;
    public static int TILE_SIZE = (int)(TILES_DEFAULE_SIZE * SCALE);
    public static int GAME_WIDTH = TILE_SIZE * TILE_INT_WIDTH;
    public static int GAME_HEIGHT = TILE_SIZE * TILE_INT_HEIGHT;
    
    public static final int CRABBY_DRAWOFFSET_X = (int) (26 * Game.SCALE);
    public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * Game.SCALE);
   
    public Game() {

        initClasses();
        
        gamePanel = new GamePanel(this);
        gamewindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        
        StartGameLoop();
    }
    
    private void initClasses(){
        menu = new Menu(this);
        playing = new Playing(this);
    }
    
    private void StartGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    private void update(){
        switch (GameState.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
            case QUIT:
            default:
                System.exit(0);
    }
    }    
    
    public void render(Graphics g){
       switch (GameState.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            
            default:
                throw new AssertionError();
    }
        
    }    
    
    @Override
    public void run() {
        
        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;
        long previousTime = System.nanoTime();
        
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        
        double deltaU = 0;
        double deltaF = 0;
        
        
        
        while(true){
            
            long currenTimes = System.nanoTime();
            
            deltaU += (currenTimes - previousTime) / timePerUpdate;
            deltaF += (currenTimes - previousTime) / timePerFrame;
            previousTime = currenTimes;
            
            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }
            
            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }
            
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + "| UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
    void windowFocusLost() {
        if (GameState.state == GameState.PLAYING) {
            playing.getPlayer().resetDirBoolean();
        }
    }
    
    public Menu getMenu(){
        return menu;
    }
    
    public Playing getPlaying(){
        return playing;
    }
   
    

    
}
