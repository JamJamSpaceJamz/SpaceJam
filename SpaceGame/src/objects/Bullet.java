package objects;



import game.Helper;
import game.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class Bullet extends Obj
{
	private float rotation, range, speed, width, hieght;
	private float points[];

	public Bullet(float[] location, float rotation, float range, List<Obj> inst)
	{
		width = 2f;
		hieght = 10f;

		points = makePoints(location, rotation);
		shape = new Polygon(points);
		this.rotation = rotation;
		this.range = range;
		speed = 30f;

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

		float speed = this.speed * delta * .01f;
		range -= speed;

		float cos = Helper.cos(rotation);
		float sin = Helper.sin(rotation);
		for (int i = 0; i < 8; i += 2)
		{
			points[i] += speed*cos;
			points[i+1] += speed*sin;
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
		}
	}

}
