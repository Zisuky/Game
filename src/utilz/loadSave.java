package utilz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

public class loadSave {

	public static final String BIG_CLOUD = "big_clouds.png";
	public static final String CANNON = "cannon_atlas.png";
	public static final String CANNON_BALL = "ball.png";
	public static final String COMPLETED = "completed_sprite.png";
	public static final String CONTAINER_ATLAS = "objects_sprites.png";
	public static final String CRAB_ATLAS = "crabby_sprite.png";
	public static final String DEATH = "death_screen.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public static final String MENU_BACKGD = "menu_background.png";
	public static final String MENU_BACKGROUND_IMG = "background_menu.png";
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
		URL url = loadSave.class.getResource("/res/lvls/");
		File file = null;

		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];

		for (int i = 0; i < filesSorted.length; i++)
			for (int j = 0; j < files.length; j++) {
				if (files[j].getName().equals((i + 1) + ".png"))
					filesSorted[i] = files[j];

			}

		BufferedImage[] imgs = new BufferedImage[filesSorted.length];

		for (int i = 0; i < imgs.length; i++)
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}

		return imgs;
	}

}
