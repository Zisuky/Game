/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameStates;

import main.Game;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import ui.menuButton;
import utilz.loadSave;

/**
 *
 * @author loisu
 */
public class Menu extends State implements StateMethods {

    private menuButton[] buttons = new menuButton[3];
    private BufferedImage bgImg, bgImgP;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        bgImgP = loadSave.getSpriteAtlas(loadSave.MENU_BACKGROUND);
    }

    private void loadButtons() {
        buttons[0] = new menuButton(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, GameState.PLAYING);
        buttons[1] = new menuButton(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, GameState.OPTIONS);
        buttons[2] = new menuButton(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 2, GameState.QUIT);
    }

    @Override
    public void update() {
        for (menuButton mb : buttons) {
            mb.update();
        }
    }

    private void loadBackground() {
        bgImg = loadSave.getSpriteAtlas(loadSave.MENU_BACKGD);
        menuWidth = (int) (bgImg.getWidth() * Game.SCALE);
        menuHeight = (int) (bgImg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45 * Game.SCALE);

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(bgImgP, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT,null);
        
        g.drawImage(bgImg, menuX, menuY, menuWidth, menuHeight, null);
        for (menuButton mb : buttons) 
            mb.draw(g);
        
    }

    

    @Override
    public void mousePressed(MouseEvent e) {
        for (menuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (menuButton mb : buttons) {
            if (isIn(e, mb)) {
                if (mb.isMousePressed()) {
                    mb.applyGamestate();
                }
                break;
            }
        }

        resetButtons();

    }

    private void resetButtons() {
        for (menuButton mb : buttons) {
            mb.resetBools();
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (menuButton mb : buttons) {
            mb.setMouseOver(false);
        }

        for (menuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameState.state = GameState.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }

  
}
