package game;

// Static class used to store all constants for the game.
public class Constants {
	public static float scale;
	
	// Game variables
	public static int game_toggle = 0;
	public static int game_target_framerate = 60;
	public static int game_num_players = 4;
	public static int game_init_asteroids;
	public static float game_delta_scale = 0.01f;
	
	// Team variables
	public static int team_initial_credits = 5000;
	public static int team_ship_cost = 500;
	
	// Ship variables
	public static int ship_initial_speed = 3;
	public static int ship_capacity = 15;
	public static int ship_cooldown = 5;
	public static int ship_mass = 75;
	public static float ship_size;
	public static float ship_max_speed = 12;
	public static float ship_default_turn_accuracy = 3;
	
	// Fighter variables
	public static float fighter_range;
	public static float fighter_health = 30;
	public static float fighter_damage = 30;
	
	// AutoShip variables
	public static int autoship_search_timer = 100;
	
	// Base variables
	public static int base_mass = 1000;
	public static int base_numPoints = 6;
	public static float base_turnspeed = 0.5f;
	public static float base_size;
	public static float base_spawn_dist;
	public static float base_range;
	public static float base_health = 1000;
	
	// Bullet variables
	public static float bullet_init_speed = 30f;
	public static float bullet_width;
	public static float bullet_height;
	
	public Constants(float scale) {
		this.scale = scale;
		
		game_init_asteroids = (int) (scale * 25f);
		ship_size = scale * 5;
		fighter_range = scale * 100f;
		base_size = scale * 30f;
		base_spawn_dist = scale * 70f;
		base_range = scale * 100f;
		bullet_width = scale * 2f;
		bullet_height = scale * 10f;
	}
}
