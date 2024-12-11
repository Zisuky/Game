/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

/**
 *
 * @author loisu
 */


import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;
import gameStates.Playing;
import java.awt.geom.Rectangle2D;

import levels.Level;
import static utilz.constants.objectConstants.*;
import utilz.loadSave;


public class ObjectManager {

	private Playing playing;
	private BufferedImage spikeImg;
	private ArrayList<spike> spikes;

	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadImgs();
	}

	public void checkSpikesTouched(Player p) {
		for (spike s : spikes)
			if (s.getHitbox().intersects(p.getHitbox()))
				p.kill();
	}

	public void checkObjectHit(Rectangle2D.Float attackbox) {
            
        }

	

	public void loadObjects(Level newLevel) {
		spikes = newLevel.getSpike();
	}

	private void loadImgs() {
		spikeImg = loadSave.getSpriteAtlas(loadSave.TRAP);
	}

	public void update() {}

	public void draw(Graphics g, int xLvlOffset) {
		drawTraps(g, xLvlOffset);
	}

	private void drawTraps(Graphics g, int xLvlOffset) {
		for (spike s : spikes)
			g.drawImage(spikeImg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);

	}

	
	public void resetAllObjects() {}
}


