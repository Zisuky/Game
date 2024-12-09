/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import gameStates.Playing;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import levels.Level;
import static utilz.constants.Enemy.*;
import utilz.loadSave;

/**
 *
 * @author loisu
 */
public class enemyManager {

    private Playing playing;
    private BufferedImage[][] crabbyArr;
    private ArrayList<Crabby> crabbies = new ArrayList<>();

    public enemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemy(Level level) {
        crabbies = level.getCrabs();

    }

    private void loadEnemyImgs() {
        crabbyArr = new BufferedImage[5][9];
        BufferedImage temp = loadSave.getSpriteAtlas(loadSave.CRAB_ATLAS);
        for (int j = 0; j < crabbyArr.length; j++) {
            for (int i = 0; i < crabbyArr[j].length; i++) {
                crabbyArr[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
            }
        }
    }

    public void update(int[][] lvlData, Player player) {
        boolean isAnyActive = false;
        for (Crabby c : crabbies) 
            if (c.isActive()) {
                c.update(lvlData, player);
                isAnyActive = true;
            }
        
        if (!isAnyActive) {
            playing.setLevelCompleted(true);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawCrabs(g, xLvlOffset);
    }

    private void drawCrabs(Graphics g, int xLvlOffset) {
        for (Crabby c : crabbies) {
            if (c.isActive()) {
                g.drawImage(crabbyArr[c.getEnemyState()][c.getAniIndex()],
                        (int) c.getHitbox().x - xLvlOffset - CRABBY_DRAWOFFSET_X + c.flipX(),
                        (int) c.getHitbox().y - CRABBY_DRAWOFFSET_Y,
                        CRABBY_WIDTH * c.flipW(),
                        CRABBY_HEIGHT, null);
//			c.drawHitbox(g, xLvlOffset);
                c.drawAttackBox(g, xLvlOffset);

            }
        }
    }
    
    public void resetAllEnemies() {
        for (Crabby c : crabbies) {
            c.resetEnemy();
        }
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Crabby c : crabbies) {
            if (c.isActive()) {
                if (attackBox.intersects(c.getHitbox())) {
                    c.hurt(10);
                    return;
                }
            }
        }
    }

    

}
