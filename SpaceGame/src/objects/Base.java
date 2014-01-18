package objects;


import game.Helper;
import game.SimpleTest;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

public class Base extends Obj
{
	private float size;
	private float rotation;
	private Color color;
	private float[] points;
	private float TURNSPEED = 1;
	private int credit;
	
	public Base(float size, float[] loc, Color color, SimpleTest gameInst)
	{
		credit = 0;
		this.size = size;
		this.location = loc;
		this.color = color;
		this.gameInst = gameInst;
		
		rotation = 0;
		velocity = new float[2];
		initPts();
	}
	
	private void initPts()
	{
		// number of sides the base has
		int numPts = 6;
		points = new float[numPts*2];
		for (int i = 0; i < numPts; i++)
		{
			float dir = i*360f/(numPts);
			points[2*i] = location[0] + Helper.cos(dir)*size;
			points[2*i + 1] = location[1] + Helper.sin(dir)*size;
		}
	}
	
	@Override
	public void draw(Graphics gfloat) 
	{
		gfloat.setColor(color);
		shape = new Polygon(points);
		gfloat.fill(shape);
		gfloat.drawString("" + credit, location[0], location[1] + size*1.3f);
	}

	@Override
	public void update(int delta) 
	{
		int numPts = 6;
		
		for (int i = 0; i < 6; i++)
		{
			float dir = i*360f/numPts;
			rotation += TURNSPEED * .01f*delta;
			points[2*i] = size*Helper.cos(dir + rotation) + location[0];
			points[2*i + 1] = size *Helper.sin(dir + rotation) + location[1];
		}
		
		location[0] += velocity[0]*delta*.01f;
		location[1] += velocity[1]*delta*.01f;
		
	}
	
	public void credit(int amount)
	{
		credit += amount;
	}
	
	@Override
	public void collide(Obj hitter, int delta) {
		
		
	}

}
