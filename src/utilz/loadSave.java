package utilz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;

import javax.imageio.ImageIO;

public class loadSave {

	public static final String BIG_CLOUD = "big_clouds.png";
	public static final String CANNON = "cannon_atlas.png";
	public static final String CANNON_BALL = "ball.png";
	public static final String COMPLETED = "completed_sprite.png";
	public static final String CONTAINER_ATLAS = "objects_sprites.png";
	public static final String CRAB_ATLAS = "crabby_sprite.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public static final String MENU_START = "menu_background.png";
	public static final String BACKGROUND = "rice_bg.png";
	public static final String MENU_BUTTON = "button_atlas.png";
	public static final String PAUSE_BACKGD = "pause_menu.png";
	public static final String PLAYER_ATLAS = "player_sprites.png";
	public static final String PLAYING_BG = "playing_bg_img.png";
	public static final String POTION_ATLAS = "potions_sprites.png";
	public static final String SMALL_CLOUD = "small_clouds.png";
	public static final String SOUND_BUTTON = "sound_button.png";
	public static final String STATUS_BAR = "health_power_bar.png";
	public static final String TRAP = "trap_atlas.png";
	public static final String URM_BUTTON = "urm_buttons.png";
	public static final String VOLUME_BUTTON = "volume_buttons.png";
	public static final String PLAYER = "total.png";
        public static final String DEATH_SCREEN = "death_screen.png";
        public static final String OPTIONS_MENU = "options_background.png";

	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = loadSave.class.getResourceAsStream("/res/" + fileName);
		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}

	public static BufferedImage[] GetAllLevels() {
            ArrayList<BufferedImage> levelImages = new ArrayList<>();

            try {
                for (int i = 1; ; i++) {
                    String fileName = "/res/lvls/" + i + ".png";
                    InputStream is = loadSave.class.getResourceAsStream(fileName);

                    if (is == null) break;
                    levelImages.add(ImageIO.read(is));
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return levelImages.toArray(new BufferedImage[0]);
        }


}
