/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import main.Game;
import gameStates.*;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static utilz.constants.PauseButton.*;
import static utilz.constants.UrmButton.*;
import static utilz.constants.VolumeButtons.*;
import utilz.loadSave;

/**
 *
 * @author loisu
 */
public class pauseOverlay {
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private soundButton musicButton, sfxButton;
    private urmButton menuB, replayB, unpauseB;
    private volumeButton volumeButton;
    
    public pauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		createSoundButtons();
		createUrmButtons();
		createVolumeButton();

	}

    private void loadBackground() {
        backgroundImg = loadSave.getSpriteAtlas(loadSave.PAUSE_BACKGD);
	bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
	bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
	bgX = Game.GAME_WIDTH / 2 - bgW / 2;
	bgY = (int) (25 * Game.SCALE);
    }
    
    public void update(){
        musicButton.update();
	sfxButton.update();
        menuB.update();
        replayB.update();
        unpauseB.update();
        volumeButton.update();
    }
    
    private void createSoundButtons() {
        int soundX = (int) (450 * Game.SCALE);
	int musicY = (int) (140 * Game.SCALE);
	int sfxY = (int) (186 * Game.SCALE);
	musicButton = new soundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
	sfxButton = new soundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }
    
    public void draw(Graphics g){
            g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
            musicButton.draw(g);
            sfxButton.draw(g);
            menuB.draw(g);
            replayB.draw(g);
            unpauseB.draw(g);
            volumeButton.draw(g);
        }
    private boolean isIn(MouseEvent e, pauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
    }
    
    
    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else if (isIn(e, replayB)) {
            replayB.setMousePressed(true);
        } else if (isIn(e, unpauseB)) {
            unpauseB.setMousePressed(true);
        } else if (isIn(e, volumeButton)) {
            volumeButton.setMousePressed(true);
        }
    }
    
     public void mouseReleased(MouseEvent e) {
         if (isIn(e, musicButton)) {
             if (musicButton.isMousePressed()) 
                 musicButton.setMuted(!musicButton.isMuted());
        } else if (isIn(e, sfxButton)) {
             if (sfxButton.isMousePressed()) 
                 sfxButton.setMuted(!sfxButton.isMuted());
        } else if (isIn(e, menuB)) {
             if (menuB.isMousePressed()) {
                 GameState.state = GameState.MENU;
                 playing.unpauseGame();
             }
         } else if (isIn(e, replayB)) {
             if (replayB.isMousePressed()) {
                 System.out.println("replay lvl!");
                 playing.resetAll();
                 playing.unpauseGame();
             }
         } else if (isIn(e, unpauseB)) {
             if (unpauseB.isMousePressed()) {
                 playing.unpauseGame();
             }
         }
         
         musicButton.resetBools();
         sfxButton.resetBools();
         menuB.resetBools();
         replayB.resetBools();
         unpauseB.resetBools();
         volumeButton.resetBools();
     }
     
     
     public void mouseMoved(MouseEvent e) {
         musicButton.setMouseOver(false);
	 sfxButton.setMouseOver(false);
         menuB.setMouseOver(false);
         replayB.setMouseOver(false);
         unpauseB.setMouseOver(false);
         volumeButton.setMouseOver(false);
         
          if (isIn(e, musicButton)) {
             musicButton.setMouseOver(true);
         } else if (isIn(e, sfxButton)) {
             sfxButton.setMouseOver(true);
         } else if (isIn(e, menuB)) {
             menuB.setMouseOver(true);
         } else if (isIn(e, replayB)) {
             replayB.setMouseOver(true);
         } else if (isIn(e, unpauseB)) {
             unpauseB.setMouseOver(true);
         } else if (isIn(e, volumeButton))
             volumeButton.setMouseOver(true);
     }
     
    public void mouseDragged(MouseEvent e) {
            if (volumeButton.isMousePressed()) {
                volumeButton.changeX(e.getX());
            }

        }
    private void createUrmButtons() {
        int menuX = (int) (313 * Game.SCALE);
        int replayX = (int) (387 * Game.SCALE);
        int unpauseX = (int) (462 * Game.SCALE);
        int bY = (int) (325 * Game.SCALE);

        menuB = new urmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
        replayB = new urmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
        unpauseB = new urmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
    }

    private void createVolumeButton() {
        int vX = (int) (309 * Game.SCALE);
        int vY = (int) (278 * Game.SCALE);
        volumeButton = new volumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }
    
    
    
}


