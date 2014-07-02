package game;

// Static class used to store all constants for the game.
public class Constants {
	public static float scale;
	
	// Game variables
	public static int game_toggle;
	public static int game_target_framerate;
	public static int game_num_players;
	public static int game_init_asteroids;
	public static float game_delta_scale;
	
	// Team variables
	public static int team_initial_credits;
	public static int team_ship_cost;
	
	// Ship variables
	public static int ship_initial_speed;
	public static int ship_capacity;
	public static int ship_cooldown;
	public static int ship_mass;
	public static float ship_size;
	public static float ship_max_speed;
	public static float ship_default_turn_accuracy;
	
	// Fighter variables
	public static float fighter_range;
	public static float fighter_health;
	public static float fighter_damage;
	
	// AutoShip variables
	public static int autoship_search_timer;
	
	// Base variables
	public static int base_mass;
	public static int base_numPoints;
	public static float base_turnspeed;
	public static float base_size;
	public static float base_spawn_dist;
	public static float base_range;
	public static float base_health;
	
	// Bullet variables
	public static float bullet_init_speed;
	public static float bullet_width;
	public static float bullet_height;
	
	public Constants(float scale) {
		this.scale = scale;
		
		// Game variables
		game_toggle = 0;
		game_target_framerate = 60;
		game_num_players = 4;
		game_init_asteroids = (int) (scale * 25f);
		game_delta_scale = 0.01f;
		
		// Team variables
		team_initial_credits = 5000;
		team_ship_cost = 500;
		
		// Ship variables
		ship_initial_speed = 3;
		ship_size = scale * 5;
		ship_capacity = 15;
		ship_max_speed = 12;
		ship_cooldown = 5;
		ship_mass = 75;
		ship_default_turn_accuracy = 3;
		
		// Fighter variables
		fighter_range = scale * 100f;
		fighter_health = 30;
		fighter_damage = 30;
		
		// AutoShip variables
		autoship_search_timer = 100;
		
		// Base variables
		base_numPoints = 6;
		base_mass = 1000;
		base_health = 1000;
		base_turnspeed = 0.5f;
		base_size = scale * 30f;
		base_spawn_dist = scale * 70f;
		base_range = scale * 100f;
		
		// Bullet variables
		bullet_init_speed = 30f;
		bullet_width = scale * 2f;
		bullet_height = scale * 10f;
	}
}
