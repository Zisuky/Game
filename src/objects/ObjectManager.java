package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;
import gameStates.Playing;
import levels.Level;
import main.Game;

import static utilz.Constants.ObjectConstants.*;
import static utilz.HelpMethods.CanCannonSeePlayer;
import static utilz.HelpMethods.IsProjectileHittingLevel;
import static utilz.Constants.Projectiles.*;

import utilz.loadSave;

public class ObjectManager {

    private Playing playing;
    private BufferedImage[][] potionImgs, containerImgs;
    private BufferedImage[] cannonImg;
    private BufferedImage spikeImg, cannonBallImg;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;
    private ArrayList<Cannon> cannon;
    private ArrayList<Projectile> projectile = new ArrayList<>();

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
    }

    public void checkSpikesTouched(Player p) {
        for (Spike s : spikes) 
            if (s.getHitbox().intersects(p.getHitbox())) 
                p.kill();
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
//		for (Potion p : potions)
//			if (p.isActive()) {
//				if (hitbox.intersects(p.getHitbox())) {
//					p.setActive(false);
//					applyEffectToPlayer(p);
//				}
//			}
    }

//	public void applyEffectToPlayer(Potion p) {
//		if (p.getObjType() == RED_POTION)
//			playing.getPlayer().changeHealth(RED_POTION_VALUE);
//		else
//			playing.getPlayer().changePower(BLUE_POTION_VALUE);
//	}
    public void checkObjectHit(Rectangle2D.Float attackbox) {
//		for (GameContainer gc : containers)
//			if (gc.isActive() && !gc.doAnimation) {
//				if (gc.getHitbox().intersects(attackbox)) {
//					gc.setAnimation(true);
//					int type = 0;
//					if (gc.getObjType() == BARREL)
//						type = 1;
//					potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2), (int) (gc.getHitbox().y - gc.getHitbox().height / 2), type));
//					return;
//				}
//			}
    }

    public void loadObjects(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        spikes = newLevel.getSpike();
        cannon = newLevel.getCannon();
        projectile.clear();
    }

    private void loadImgs() {
//		BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
//		potionImgs = new BufferedImage[2][7];
//
//		for (int j = 0; j < potionImgs.length; j++)
//			for (int i = 0; i < potionImgs[j].length; i++)
//				potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);
//
//		BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
//		containerImgs = new BufferedImage[2][8];
//
//		for (int j = 0; j < containerImgs.length; j++)
//			for (int i = 0; i < containerImgs[j].length; i++)
//				containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);

        spikeImg = loadSave.GetSpriteAtlas(loadSave.TRAP);
        cannonImg = new BufferedImage[7];
        BufferedImage temp = loadSave.GetSpriteAtlas(loadSave.CANNON);

        for (int i = 0; i < cannonImg.length; i++) {
            cannonImg[i] = temp.getSubimage(i * 40, 0, 40, 26);
        }

        cannonBallImg = loadSave.GetSpriteAtlas(loadSave.CANNON_BALL);
    }

    public void update(int[][] lvlData, Player player) {
//		for (Potion p : potions)
//			if (p.isActive())
//				p.update();
//
		for (GameContainer gc : containers)
			if (gc.isActive())
				gc.update();

        updateCannon(lvlData, player);
        updateProjectiles(lvlData, player);
    }

    private void updateProjectiles(int[][] lvlData, Player player) {
        for (Projectile p : projectile) {
            if (p.isActive()) {
                p.updatePos();
		if (p.getHitbox().intersects(player.getHitbox())) {
                    player.changeHealth(-25);
                    p.setActive(false);
		} else if (IsProjectileHittingLevel(p, lvlData))
                    p.setActive(false);
            }
        }
    }

    private void updateCannon(int[][] lvlData, Player player) {
		for (Cannon c : cannon) {
			if (!c.doAnimation)
				if (c.getTileY() == player.getTileY())
					if (isPlayerInRange(c, player))
						if (isPlayerInfrontOfCannon(c, player))
							if (CanCannonSeePlayer(lvlData, player.getHitbox(), c.getHitbox(), c.getTileY()))
								c.setAnimation(true);

			c.update();
			if (c.getAniIndex() == 4 && c.getAniTick() == 0)
				shootCannon(c);
		}
	}

    public void draw(Graphics g, int xLvlOffset) {
//		drawPotions(g, xLvlOffset);
//		drawContainers(g, xLvlOffset);
        drawTraps(g, xLvlOffset);
        drawCannon(g, xLvlOffset);
        drawProjectiles(g, xLvlOffset);
    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        for (Spike s : spikes) {
            g.drawImage(spikeImg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
        }

    }

    private void drawProjectiles(Graphics g, int xLvlOffset) {
        for (Projectile p : projectile) {
            if (p.isActive()) {
                g.drawImage(cannonBallImg, (int) (p.getHitbox().x - xLvlOffset), (int) (p.getHitbox().y), CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);
            }
        }

    }

    private void drawCannon(Graphics g, int xLvlOffset) {
        for (Cannon c : cannon) {
            int x = (int) (c.getHitbox().x - xLvlOffset);
            int width = CANNON_WIDTH;

            if (c.getObjType() == CANNON_RIGHT) {
                x += width;
                width *= -1;
            }

            g.drawImage(cannonImg[c.getAniIndex()], x, (int) (c.getHitbox().y), width, CANNON_HEIGHT, null);
        }
    }
//	private void drawContainers(Graphics g, int xLvlOffset) {
//		for (GameContainer gc : containers)
//			if (gc.isActive()) {
//				int type = 0;
//				if (gc.getObjType() == BARREL)
//					type = 1;
//				g.drawImage(containerImgs[type][gc.getAniIndex()], (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset), (int) (gc.getHitbox().y - gc.getyDrawOffset()), CONTAINER_WIDTH,
//						CONTAINER_HEIGHT, null);
//			}
//	}

//	private void drawPotions(Graphics g, int xLvlOffset) {
//		for (Potion p : potions)
//			if (p.isActive()) {
//				int type = 0;
//				if (p.getObjType() == RED_POTION)
//					type = 1;
//				g.drawImage(potionImgs[type][p.getAniIndex()], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getyDrawOffset()), POTION_WIDTH, POTION_HEIGHT,
//						null);
//			}
//	}
    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrentLevel());
        for (Potion p : potions) {
            p.reset();
        }
        for (GameContainer gc : containers) {
            gc.reset();
        }
        for (Cannon c : cannon) {
            c.reset();
        }
    }

    private boolean isPlayerInRange(Cannon c, Player player) {
        int absValue = (int) Math.abs(player.getHitbox().x - c.getHitbox().x);
        return absValue <= Game.TILES_SIZE * 5;
    }

    private boolean isPlayerInfrontOfCannon(Cannon c, Player player) {
        if (c.getObjType() == CANNON_LEFT) {
            if (c.getHitbox().x > player.getHitbox().x) {
                return true;
            }

        } else if (c.getHitbox().x < player.getHitbox().x) {
            return true;
        }
        return false;
    }

    private void shootCannon(Cannon c) {
        c.setAnimation(true);
        int dir = 1;
        if (c.getObjType() == CANNON_LEFT) {
            dir = -1;
        }

        projectile.add(new Projectile((int) c.getHitbox().x, (int) c.getHitbox().y, dir));

    }

}
