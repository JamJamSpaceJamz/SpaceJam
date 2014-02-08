package objects;

import game.Helper;
import game.SimpleTest;
import game.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import objects.Obj;

public class Turret extends Obj
{
	private float size, range, rotation, distance;
	private Base home;
	private Color color;
	
	public Turret (Base home, float rotation, SimpleTest gameInst)
	{
		this.size = 3f; 				// the radius of the turret
		this.rotation = rotation; 		// angular position relative to the base it's bound to
		this.distance = 50f; 			// distance from the base it's bound to
		this.color = Color.cyan;
		this.range = 10f; 				// distance the turret can see/shoot
		this.health = 40f; 				// I have no idea what this should be.
		this.gameInst = gameInst;
		this.home = home; 				// the base the turret is bound to
		this.team = home.team;
		this.location = new float[2];
		this.location[0] = distance * Helper.cos(rotation) + home.location[0];
		this.location[1] = distance * Helper.sin(rotation) + home.location[1];
	
		velocity = new float[2];
	}
	
	@Override
	public void draw(Graphics g)
	{
		Circle circle = new Circle(location[0], location[1], size);
		shape = circle;
		g.setColor(color);
		g.fill(circle);	
	}
	
	@Override
	public void update(int delta)
	{
		rotation += 0.075f * delta;		// modify the turret's position each frame
		if (rotation >= 360)			// if the turret finished one revolution, reset the angle
		{
			rotation -= 360;
		}
		
		// Set the turret's position according to the position of the base it's bound to
		// and match the base's velocity.
		location[0] = distance * Helper.cos(360f - rotation) + home.location[0];
		location[1] = distance * Helper.sin(360f - rotation) + home.location[1];
		
		velocity = home.velocity;
	}
	
	@Override
	public boolean collide(Obj hitter) {
		return false;
	}
}