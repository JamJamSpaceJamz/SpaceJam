package objects;

import game.Constants;
import game.Helper;
import game.List;
import game.Team;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import ships.Ship;

import java.lang.Math;

public class Bullet extends Obj
{
	private float range, width, height;
	private float points[];
	private float damage;

	public Bullet(float[] location, float rotation, float range, float damage, Team team)
	{
		// adding so its not null, should fix later
		this.location = location;
		
		float INITIAL_SPEED = Constants.bullet_init_speed;
		
		width = Constants.bullet_width;
		height = Constants.bullet_height;
		
		points = makePoints(location, rotation);
		shape = new Polygon(points);
		this.team = team;
		this.range = range;
		this.velocity = new float[2];
		this.velocity[0] = INITIAL_SPEED * Helper.cos(rotation);
		this.velocity[1] = INITIAL_SPEED * Helper.sin(rotation);
		this.damage = damage;
//		System.out.println(velocity[0] + "  " + velocity[1]);

//		objInst = inst;
	}

	private float[] makePoints(float[] loc, float rotation)
	{
		float cos = Helper.cos(rotation);
		float sin = Helper.sin(rotation);
		float[] pts = new float[8];

		// lower left pt
		pts[0] = loc[0] - width/2 * sin;
		pts[1] = loc[1] + width/2 * cos;

		//upper left pt
		pts[2] = pts[0] + height * cos;
		pts[3] = pts[1] + height * sin;

		//upper right pt
		pts[4] = pts[2] + width * sin;
		pts[5] = pts[3] - width * cos;

		//lower right pt
		pts[6] = pts[4] - height * cos;
		pts[7] = pts[5] - height * sin;

		return pts;
	}

	public void update(int delta)
	{
		//  removes this bullet from the list if it has gone too far
		if (range <= 0)
			objInst.remove();		

		float speed = (float)Math.sqrt(velocity[0] * velocity[0] + velocity[1] * velocity[1]);
		speed *=  delta * Constants.game_delta_scale;
		
		range -= speed;

		for (int i = 0; i < 8; i += 2)
		{
			points[i] += this.velocity[0] * delta * Constants.game_delta_scale;
			points[i+1] += this.velocity[1] * delta * Constants.game_delta_scale;
		}
	}
	
	@Override
	public void draw(Graphics g)
	{
		shape = new Polygon(points);
		g.setColor(getColor());
		g.fill(shape);
		//		g.drawLine(points[0], points[1], points[2], points[3]);
	}

	// returns the shape needed to draw the shape
	public Shape shape()
	{
		return shape;
	}

	public void hit(Obj target) {
		target.damage(damage);
	}
	
	@Override
	public boolean collide(Obj hitter)
	{
		if (hitter.getTeam() != this.getTeam() && !(hitter instanceof Credit) && !(hitter instanceof Bullet)) {
			collided = true;
			float[] vel = hitter.velocity;
			vel[0] += velocity[0]/100;
			vel[1] += velocity[1]/100;
			this.remove();
			hit(hitter);
		}
		
		return false;
	}
}
