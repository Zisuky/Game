/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilz;

import main.Game;

public class constants {
    public static final float GRAVITY = 0.04f * Game.SCALE;
    public static final float ANI_SPEED = 25;
    public static class Enemy {
        
        public static final int CRABBY = 0;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int CRABBY_WIDTH_DEFAULT = 72;
        public static final int CRABBY_HEIGHT_DEFAULT = 32;

        public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * Game.SCALE);
        public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * Game.SCALE);

        public static final int CRABBY_DRAWOFFSET_X = (int) (26 * Game.SCALE);
        public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * Game.SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state) {

            switch (enemy_type) {
                case CRABBY:
                    switch (enemy_state) {
                        case IDLE:
                            return 9;
                        case RUNNING:
                            return 6;
                        case ATTACK:
                            return 7;
                        case HIT:
                            return 4;
                        case DEAD:
                            return 5;
                    }
            }

            return 0;

        }

        public static int GetMaxHealth(int enemy_type) {
            switch (enemy_type) {
                case CRABBY:
                    return 10;
                default:
                    return 1;
            }
        }

        public static int GetEnemyDmg(int enemy_type) {
            switch (enemy_type) {
                case CRABBY:
                    return 15;
                default:
                    return 0;
            }

        }
    }

    public static class Buttons {

        public static final int B_WIDTH_DEFAULT = 140;
        public static final int B_HEIGHT_DEFAULT = 56;
        public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
        public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
    }

    public static class Environment {

        public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
        public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
        public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
        public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

//		public static final int RE_LAU_WIDTH_DEFAULT= 448;
//		public static final int RE_LAU_HEIGHT_DEFAULT= 101;
        public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
        public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
        public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);

//                public static final int RE_LAU_WIDTH = (int)(RE_LAU_WIDTH_DEFAULT * Game.SCALE);
//                public static final int RE_LAU_HEIGHT = (int)(RE_LAU_HEIGHT_DEFAULT * Game.SCALE);
    }

    public static class PauseButton {

        public static final int SOUND_SIZE_DEFAULT = 42;
        public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
    }

    public static class UrmButton {

        public static final int URM_SIZE_DEFAULT = 56;
        public static final int URM_SIZE = (int) (URM_SIZE_DEFAULT * Game.SCALE);
    }

    public static class VolumeButtons {

        public static final int VOLUME_DEFAULT_WIDTH = 28;
        public static final int VOLUME_DEFAULT_HEIGHT = 44;
        public static final int SLIDER_DEFAULT_WIDTH = 215;

        public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
        public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
        public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
    }

    public static class Direction {

        public static final int UP = 0;
        public static final int DOWN = 1;
        public static final int LEFT = 2;
        public static final int RIGHT = 3;
    }

    public static class PlayerConstants {

        public static final int RUNNING = 1;
        public static final int IDLE = 0;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int TAKEHIT = 4;
        public static final int TAKEHITWHITE = 5;
        public static final int DIE = 6;
        public static final int ATTACK1 = 7;
        public static final int ATTACK2 = 8;

        public static int getPriceAmount(int player_action) {
            switch (player_action) {
                case RUNNING:
                case IDLE:
                    return 8;
                case JUMP:
                case FALLING:
                    return 2;
                case TAKEHIT:
                case TAKEHITWHITE:
                    return 4;
                case DIE: 
                case ATTACK1:
                case ATTACK2:
                    return 6;
                default:
                    return 1;
            }
        }

    }
}
