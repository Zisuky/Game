/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;


import gameStates.Playing;
import java.awt.Color;
import main.Game;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import static utilz.HelpMethods.*;
import static utilz.constants.*;
import static utilz.constants.PlayerConstants.*;

import utilz.loadSave;

/**
 *
 * @author loisu
 */
public class Player extends Entity{
    private BufferedImage[][] animations;
    
    
    private boolean moving = false, attacking = false;
    private boolean  left, right, jump;
    
    private int [][] lvlData;
    
    private float xDrawOffset = 65 * Game.SCALE;
    private float yDrawOffset = 60 * Game.SCALE;
    
    //jump && gravity

    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeed= 0.5f * Game.SCALE;

     
    //status bar
    private BufferedImage statusBarImg;

    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);


    
    private int healthWidth = healthBarWidth;

    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked;
    private Playing playing;
    
    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height); 
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.walkSpeed = Game.SCALE * 1.0f;
        loadAnimation();
        initHitBox(20,29);
        initAttackBox();
    }
    private void initAttackBox() {
	attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
    }
    
    public void update(){
        updateHealthBar();
        
        if (currentHealth <= 0) {
            playing.setGameOver(true);
            return;
        }
        
        updateAttackBox();
        updatePos();
        if (attacking) {
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();
        
    }
    
    private void checkAttack() {
        if (attackChecked || aniIndex != 1) 
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
    }
    public void render(Graphics g, int xlvlOffset){
	g.drawImage(animations[state][aniIndex], (int) (hitbox.x - xDrawOffset) - xlvlOffset + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
//        drawHitbox(g, xlvlOffset);
//        drawAttackBox(g, xlvlOffset);
        drawUI(g);
    }
    
    
    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= ANI_SPEED)      {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getPriceAmount(state)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }
    
    private void setAnimation() {
        
        int startAni = state;
            if (moving) 
                state = RUNNING;

            else
                    state = IDLE;
            if (inAir)
                if (airSpeed < 0) 
                    state = JUMP;
                else 
                    state = FALLING;
            
            if(attacking){
                state = ATTACK1;
                if (startAni != ATTACK1) {
                    aniIndex = 1;
                    aniTick = 0;
                    return;
                }
            if(startAni != state)
                resetAniTick();
            }         
    }
    
    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }
    
    private void updatePos() {
        moving = false;
        
         if (jump) 
            jump();
         if (!inAir) 
             if ((!left && !right) || (left && right)) 
                 return;
         float xSpeed = 0;
         if(left){
             xSpeed -= walkSpeed;
             flipX = width;
             flipW = -1;
             
         }
         if(right){
             xSpeed += walkSpeed;
             flipX = 0;
             flipW = 1;
             
         }
         if (!inAir) 
             if (!IsEntityOnFloor(hitbox, lvlData)) 
                 inAir = true;
        
         if (inAir) {
             if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                 hitbox.y += airSpeed;
                 airSpeed += GRAVITY;
                 updateXpos(xSpeed);
             }else{
                 hitbox.y = getEntityYposUnderRoofOrAboveFloor(hitbox, airSpeed);
                 if (airSpeed > 0 ) 
                     resetInAir();
                 else 
                     airSpeed = fallSpeed;
                 updateXpos(xSpeed);
             }
        } else
             updateXpos(xSpeed);
         moving = true;
         
    }
    
    private void jump() {
        if (inAir) 
            return;
        inAir = true;
        airSpeed = jumpSpeed;
        
    }
    
    private void updateXpos(float xSpeed){
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
                hitbox.x += xSpeed;        
        }else {
           hitbox.x = getEntityXPosNextToWall(hitbox, xSpeed);
        }
    }
    
    
    
    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }
    
    private void loadAnimation() {
        BufferedImage img = loadSave.getSpriteAtlas(loadSave.PLAYER_ATLAS);
        
       
            animations = new BufferedImage[9][8];
            for (int j = 0; j < animations.length; j++)
                for (int i = 0; i < animations[j].length; i++)
                    animations[j][i] = img.getSubimage(i * 200, j*200, 200, 200);
        
        statusBarImg = loadSave.getSpriteAtlas(loadSave.STATUS_BAR);
    }
    
    private void drawUI(Graphics g) {
        
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.RED);
	g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
	
    }
    
    public void loadLvlData(int [][] lvlData ){
        this.lvlData = lvlData;
        if (!IsEntityOnFloor(hitbox, lvlData)) 
            inAir = true;
        
    }
    
    
    private void updateHealthBar() {
        
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);

    }
    
    public void changeHealth(int value) {
        currentHealth += value;

        if (currentHealth <= 0) {
            currentHealth = 0;
        } else if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }

    
    private void updateAttackBox() {
        if (right) {
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
        } else if (left) {
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);
        }

        attackBox.y = hitbox.y + (Game.SCALE * 10);
    }
    
    public void resetDirBoolean(){
        left = false;
        right= false;

    }
   
    public void setAttack(boolean attacking){
        this.attacking = attacking;
    }
    
    
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
    
    public void setJump(boolean jump){
        this.jump = jump;
    }

    public void resetAll() {
        resetDirBoolean();
        inAir = false;
        attacking = false;
        moving = false;
        state = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;

        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
        
    }

    public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitbox.x = x;
		hitbox.y = y;
	}
    

    

    
    



    

    
    
    
}
