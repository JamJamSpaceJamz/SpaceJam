package objects;

import game.Helper;
import game.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import java.lang.Math;

public class Bullet extends Obj
{
	private float range, width, hieght;
	private float points[];
	private float damage;

	public Bullet(float[] location, float rotation, float range, float damage, List<Obj> inst)
	{
		// adding so its not null, should fix later
		this.location = location;
		
		width = 2f;
		hieght = 10f;
		
		points = makePoints(location, rotation);
		shape = new Polygon(points);
		this.range = range;
		this.velocity = new float[2];
		this.velocity[0] = 30 * Helper.cos(rotation);
		this.velocity[1] = 30 * Helper.sin(rotation);
		this.damage = damage;
		System.out.println(velocity[0] + "  " + velocity[1]);

		objInst = inst;
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
		pts[2] = pts[0] + hieght * cos;
		pts[3] = pts[1] + hieght * sin;

		//upper right pt
		pts[4] = pts[2] + width * sin;
		pts[5] = pts[3] - width * cos;

		//lower right pt
		pts[6] = pts[4] - hieght * cos;
		pts[7] = pts[5] - hieght * sin;

		return pts;
	}

	public void update(int delta)
	{
		//  removes this bullet from the list if it has gone too far
		if (range <= 0)
		{
			objInst.remove();
		}

		float speed = (float)Math.sqrt(velocity[0] * velocity[0] + velocity[1] * velocity[1]);
		speed *=  delta * .01f;
		
		range -= speed;

		for (int i = 0; i < 8; i += 2)
		{
			points[i] += this.velocity[0];
			points[i+1] += this.velocity[1];
		}
	}
	
	@Override
	public void draw(Graphics g)
	{
		shape = new Polygon(points);
		g.setColor(Color.yellow);
		g.fill(shape);
		//		g.drawLine(points[0], points[1], points[2], points[3]);
	}

	// returns the shape needed to draw the shape
	public Shape shape()
	{
		return shape;
	}

	@Override
	public float[] setSpeed(float[] speed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void collide(Obj hitter, int delta)
	{
		if (hitter instanceof Asteroid)
		{
			collided = true;
			objInst.remove();
			hitter.damage(damage);
		}
	}

}
