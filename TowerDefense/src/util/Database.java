package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public final class Database
{
	
	// Do not let anyone instantiate this class
	// Just following java.lang.Math -SML
	private Database() {}
	
	// Map Textures
	public static final Texture mario_map = new Texture(Gdx.files.internal("data/maps/mario_map_6.png"));
	public static final Texture pokemon_map = new Texture(Gdx.files.internal("data/maps/pokemon_map.png"));
	
	// Tower Textures
	public static final Texture basicTower = new Texture(Gdx.files.internal("data/towers/tower.png"));
	public static final Texture fireTower1 = new Texture(Gdx.files.internal("data/towers/fire_tower_1.png"));
	public static final Texture fireTower2 = new Texture(Gdx.files.internal("data/towers/fire_tower_2.png"));
	public static final Texture hammerBros1 = new Texture(Gdx.files.internal("data/towers/hammer_bros_left_idle.png"));
	public static final Texture hammerBros2 = new Texture(Gdx.files.internal("data/towers/hammer_bros_right_idle.png"));
	public static final Texture psychicTower1 = new Texture(Gdx.files.internal("data/towers/psychic_tower_1.png"));
	public static final Texture psychicTower2 = new Texture(Gdx.files.internal("data/towers/psychic_tower_2.png"));
	
	// Bullet Textures
	public static final Texture basicBullet = new Texture(Gdx.files.internal("data/bullets/bullet.png"));
	public static final Texture fireBullet1 = new Texture(Gdx.files.internal("data/bullets/fire_ball_1.png"));
	public static final Texture fireBullet2 = new Texture(Gdx.files.internal("data/bullets/fire_ball_2.png"));
	public static final Texture hammer1 = new Texture(Gdx.files.internal("data/bullets/hammer/hammer_left_up.png"));
	public static final Texture hammer2 = new Texture(Gdx.files.internal("data/bullets/hammer/hammer_left_left.png"));
	public static final Texture hammer3 = new Texture(Gdx.files.internal("data/bullets/hammer/hammer_left_down.png"));
	public static final Texture hammer4 = new Texture(Gdx.files.internal("data/bullets/hammer/hammer_left_right.png"));
	public static final Texture psychicBall1 = new Texture(Gdx.files.internal("data/bullets/psychic_ball_1.png"));
	public static final Texture psychicBall2 = new Texture(Gdx.files.internal("data/bullets/psychic_ball_2.png"));

	// Enemy Textures
	public static final Texture basicEnemy = new Texture(Gdx.files.internal("data/enemies/mushroom.png"));
	public static final Texture bowser1 = new Texture(Gdx.files.internal("data/enemies/bowserR1.png"));
	public static final Texture bowser2 = new Texture(Gdx.files.internal("data/enemies/bowserR2.png"));
	public static final Texture goomba1 = new Texture(Gdx.files.internal("data/enemies/goomba1.png"));
	public static final Texture goomba2 = new Texture(Gdx.files.internal("data/enemies/goomba2.png"));
	public static final Texture koopa1 = new Texture(Gdx.files.internal("data/enemies/koopa_right_down.png"));
	public static final Texture koopa2 = new Texture(Gdx.files.internal("data/enemies/koopa_right_up.png"));

	// UI Textures
	public static final Texture healthBarMax = new Texture(Gdx.files.internal("data/healthBarMax.png"));
	public static final Texture healthBarSafe = new Texture(Gdx.files.internal("data/healthBarSafe.png"));
	public static final Texture healthBar = new Texture(Gdx.files.internal("data/healthBar.png"));
	public static final Texture hover = new Texture(Gdx.files.internal("data/hover.png"));
	public static final Texture heart0 = new Texture(Gdx.files.internal("data/textures/heart0.png"));
	public static final Texture heart25 = new Texture(Gdx.files.internal("data/textures/heart25.png"));
	public static final Texture heart50 = new Texture(Gdx.files.internal("data/textures/heart50.png"));
	public static final Texture heart75 = new Texture(Gdx.files.internal("data/textures/heart75.png"));
	public static final Texture heart100 = new Texture(Gdx.files.internal("data/textures/heart100.png"));

	
	// Menu Textures
	public static final Texture menu_bg = new Texture(Gdx.files.internal("data/menubg.png"));
	public static final Texture black_bg = new Texture(Gdx.files.internal("data/black_bg.png"));
	public static final Texture diff_bg = new Texture(Gdx.files.internal("data/diffbg.png"));
	public static final Texture level_bg = new Texture(Gdx.files.internal("data/levelbg.png"));
	public static final Texture easy = new Texture(Gdx.files.internal("data/easy.png"));
	public static final Texture medium = new Texture(Gdx.files.internal("data/medium.png"));
	public static final Texture hard = new Texture(Gdx.files.internal("data/hard.png"));
	
	public static final Texture menu_start = new Texture(Gdx.files.internal("data/menu_start.png"));
	public static final Texture menu_instr = new Texture(Gdx.files.internal("data/menu_instr.png"));
	public static final Texture instr_back = new Texture(Gdx.files.internal("data/instr_back.png"));
	public static final Texture mario_map_3 = new Texture(Gdx.files.internal("data/maps/mario_map_3.png"));
	public static final Texture stars0 = new Texture(Gdx.files.internal("data/0_stars.png"));


	
	// Sounds
	public static final Music marioBGM = Gdx.audio.newMusic(Gdx.files.internal("sounds/mario.mp3"));
	public static final Sound basicTowerSound = Gdx.audio.newSound(Gdx.files.internal("sounds/firework.mp3"));
	public static final Sound congratulations = Gdx.audio.newSound(Gdx.files.internal("sounds/congratulations.mp3"));
	
	// Bitmap Fonts
	public static final BitmapFont font = new BitmapFont(Gdx.files.internal("data/nint.fnt"), Gdx.files.internal("data/nint_0.png"), false);
	public static final BitmapFont katana_font = new BitmapFont(Gdx.files.internal("data/snes.fnt"), Gdx.files.internal("data/snes_0.png"), false);
	
	public static void dispose()
	{
		mario_map.dispose();
		pokemon_map.dispose();
	}
}
