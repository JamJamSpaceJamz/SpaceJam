package objects;

import game.Helper;
import game.List;
import game.SimpleTest;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

public class Asteroid extends Obj
{
	private final float TURNSPEED = 1f;
	private float[] points, sizeVars, angles;
	private float size, totalHealth;
	private int direction;
	
	public Asteroid(float[] location, float size, List<Obj> objInst, SimpleTest gameInst)
	{
		this.gameInst = gameInst;
		this.velocity = new float[2];
		this.velocity[0] = (float) (0.1*Math.random());
		this.velocity[1] = (float) (0.1*Math.random());
		points = initPts(location, size);
		this.location = location;
		this.size = size;
		this.objInst = objInst;
		this.mass = this.size * 50;
		health = 50*size;
		totalHealth = health;
		if (Math.random() > .5)
			direction = 1;
		else
			direction = -1;	
	}
	
	private float[] initPts(float[] location, float size)
	{
		// 5 to 10 vertices for an asteroid
		int numPts = (int) (Math.random()*10 + 5);
		float[] pts = new float[numPts*2];
		sizeVars = new float[numPts];
		angles = new float[numPts];
		
		for (int i = 0; i < numPts; i++)
		{
			float degree = (float) (Math.random()*360/numPts + i*360/numPts);
			angles[i] = degree;
			
			float sizeVar = (float) (Math.random()*size/2);
			sizeVars[i] = sizeVar;
			pts[2*i] = (size + sizeVar)*Helper.cos(degree) + location[0];
			pts[2*i + 1] = (size + sizeVar)*Helper.sin(degree) + location[1];
		}
		
		return pts;
	}
	
	public void draw(Graphics g)
	{
		
		shape = new Polygon(points);
		g.setColor(Color.gray);
		g.fill(shape);
	}
	
	public void update(int delta)
	{
		checkBorders();
		for (int i = 0; i < angles.length; i++)
		{
			angles[i] += TURNSPEED* direction * .01f*delta;
			points[2*i] = (size + sizeVars[i])*Helper.cos(angles[i]) + location[0];
			points[2*i + 1] = (size + sizeVars[i])*Helper.sin(angles[i]) + location[1];
		}
		
		location[0] += velocity[0]*delta*.01f;
		location[1] += velocity[1]*delta*.01f;
	}
	
	private void checkBorders()
	{
		// the amount the asteroid is allowed out of bounds
		int outOfBounds = 3;
		int width = gameInst.container.getWidth() + outOfBounds;
		int height = gameInst.container.getHeight() + outOfBounds;
		
		if (location[0] > width)
			location[0] = -outOfBounds;
		if (location[0] < -outOfBounds)
			location[0] = width;
		
		if (location[1] > height)
			location[1] = -outOfBounds;
		if (location[1] < -outOfBounds)
			location[1] = height;
	}
	
	@Override
	public boolean collide(Obj hitter) 
	{
		return true;
	}
	
	@Override
	public void damage(float amount)
	{
		health -= amount;
		// creates credits with a 1 in 10 chance
		int dropChance = (int) (Math.random()*2);
		if (dropChance == 100 )
		{
			System.out.println("droppin");
			int worth = (int) (Math.random() * 3 + 1);
			int direction = (int) (Math.random() *360);
			float[] location = new float[2];
			location[0] = this.location[0] + Helper.cos(direction)*size*2.03f;
			location[1] = this.location[1] + Helper.sin(direction)*size*2.03f;
			Credit crd = new Credit(location, direction, worth*15, gameInst);
			gameInst.creditList.add(crd);
		}
		if (health <= 0)
		{
			System.out.println("removing asteroid");
			objInst.remove();
		}
	}
}
