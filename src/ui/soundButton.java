/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.PauseButtons.SOUND_SIZE_DEFAULT;

import utilz.loadSave;

/**
 *
 * @author loisu
 */
public class SoundButton extends PauseButton{
    
    private BufferedImage[][] soundImgs;
	private boolean mouseOver, mousePressed;
	private boolean muted;
	private int rowIndex, colIndex;

	public SoundButton(int x, int y, int width, int height) {
		super(x, y, width, height);

		loadSoundImgs();
	}

	private void loadSoundImgs() {
		BufferedImage temp = loadSave.GetSpriteAtlas(loadSave.SOUND_BUTTON);
		soundImgs = new BufferedImage[2][3];
		for (int j = 0; j < soundImgs.length; j++)
			for (int i = 0; i < soundImgs[j].length; i++)
				soundImgs[j][i] = temp.getSubimage(i * SOUND_SIZE_DEFAULT, j * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
	}

	public void update() {
		if (muted)
			rowIndex = 1;
		else
			rowIndex = 0;

		colIndex = 0;
		if (mouseOver)
			colIndex = 1;
		if (mousePressed)
			colIndex = 2;

	}

	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	public void draw(Graphics g) {
		g.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null);
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}
    
}
