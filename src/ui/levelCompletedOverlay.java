package ui;

import gameStates.GameState;
import gameStates.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;



import main.Game;
import static utilz.constants.UrmButton.*;
import utilz.loadSave;


public class levelCompletedOverlay {

	private Playing playing;
	private urmButton menu, next;
	private BufferedImage img;
	private int bgX, bgY, bgW, bgH;

	public levelCompletedOverlay(Playing playing) {
		this.playing = playing;
		initImg();
		initButtons();
	}

	private void initButtons() {
		int menuX = (int) (330 * Game.SCALE);
		int nextX = (int) (445 * Game.SCALE);
		int y = (int) (195 * Game.SCALE);
		next = new urmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
		menu = new urmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
	}

	private void initImg() {
		img = loadSave.getSpriteAtlas(loadSave.COMPLETED);
		bgW = (int) (img.getWidth() * Game.SCALE);
		bgH = (int) (img.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (75 * Game.SCALE);
	}

	public void draw(Graphics g) {
		// Added after youtube upload
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

		g.drawImage(img, bgX, bgY, bgW, bgH, null);
		next.draw(g);
		menu.draw(g);
	}

	public void update() {
		next.update();
		menu.update();
	}

	private boolean isIn(urmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		next.setMouseOver(false);
		menu.setMouseOver(false);

		if (isIn(menu, e))
			menu.setMouseOver(true);
		else if (isIn(next, e))
			next.setMouseOver(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(menu, e)) {
			if (menu.isMousePressed()) {
				playing.resetAll();
				GameState.state = GameState.MENU;
			}
		} else if (isIn(next, e)){
			if (next.isMousePressed())
				playing.loadNextLevel();
                }
                next.resetBools();
		menu.resetBools();
		
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(menu, e))
			menu.setMousePressed(true);
		else if (isIn(next, e))
			next.setMousePressed(true);
	}

}
