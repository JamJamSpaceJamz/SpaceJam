package objects;

import game.List;
import game.SimpleTest;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public abstract class Obj 
{
	protected Shape shape;
	//  the objects speed and velocity
	protected float[] location, velocity, acceleration;
	protected float mass;
	protected boolean collided;
	protected int team; // At some point we're going to need this.
	
	// the specific instance of the object in the game
	// should only be used to remove the object from the game
	public List<Obj> objInst;
	protected SimpleTest gameInst;
	protected float health;
	
	// draws the object onto the main graphics g
	abstract public void draw(Graphics gfloat);
	
	// updates the object using delta (the time in milliseconds between frames)
	abstract public void update(int delta);
	
	// returns the shape needed to draw the shape
	public Shape shape()
	{
		return shape;
	}
	
	public float[] setVel(float[] speed) 
	{
		if (speed != null)
		{
			velocity = speed;
		}
		if (velocity == null)
		{
			float[] temp = new float[2];
			temp[0] = 1;
			temp[1] = 1;
			return temp;
		}
		return velocity;
	}
	
	public void damage(float dmg)
	{
		health -= dmg;
		if (health <= 0)
			objInst.remove();
	}
	
	public void remove()
	{
		objInst.remove();
	}
	
	protected boolean checkBorders()
	{
		boolean check = false;
		int width = gameInst.container.getWidth();
		int height = gameInst.container.getHeight();
		
		if (location[0] >= width)
		{
			check = true;
			if (velocity[0] > 0)
				velocity[0] *= -.5f;
		}
		if (location[0] <= 0)
		{
			check = true;
			if (velocity[0] < 0)
				velocity[0] *= -.5f;
		}
		
		if (location[1] >= height)
		{
			check = true;
			if (velocity[1] > 0)
				velocity[1] *= -.5f;
		}
		if (location[1] <= 0)
		{
			check = true;
			if (velocity[1] < 0)
				velocity[1] *= -.5f;
		}
		return check;
	}
	
	public float directionTo(Obj a)
	{
		float dx =  a.location[0] - this.location[0];
		float dy =  a.location[1] - this.location[1];
		float dir = (float) Math.atan2(dy, dx);
		return (float) Math.toDegrees(dir);
	}
	
	public float directionTo(float x, float y)
	{
		float dx = x - this.location[0];
		float dy = y - this.location[1];
		float dir = (float) Math.atan2(dy, dx);
		return (float) Math.toDegrees(dir);
	}
	
	public float distanceTo(Obj a)
	{
		float dx = this.location[0] - a.location[0];
		float dy = this.location[1] - a.location[1];
		return (float) Math.sqrt(dy*dy + dx*dx);
	}
	
	public float distanceTo(float x, float y)
	{
		float dx = this.location[0] - x;
		float dy = this.location[1] - y;
		return (float) Math.sqrt(dy*dy + dx*dx);
	}
	
	abstract public boolean collide(Obj hitter);
	
	
	//abstract public float[] setSpeed(float[] speed);
	
}
