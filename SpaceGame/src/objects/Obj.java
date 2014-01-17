package objects;

import game.List;
import game.SimpleTest;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public abstract class Obj 
{
	protected Shape shape;
	//  the objects speed and velocity
	protected float[] location, velocity;// = new float[2];
	protected float mass;
	protected boolean collided;
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
	
	public float[] setSpeed(float[] speed) 
	{
		if (speed != null)
			velocity = speed;
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
	abstract public void collide(Obj hitter, int delta);
	
	
	//abstract public float[] setSpeed(float[] speed);
	
}
