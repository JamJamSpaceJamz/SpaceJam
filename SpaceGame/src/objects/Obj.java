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
	
	public float directionTo(Obj a)
	{
		float dx = -(this.location[0] - a.location[0]);
		float dy = this.location[1] - a.location[1];
		float dir = (float) (Math.atan2(dy, dx) * 180 / (Math.PI));
		if (dir <= 0)
			dir = 360 + dir;
		return dir;
	}
	
	public float distanceTo(Obj a)
	{
		float dx = this.location[0] - a.location[0];
		float dy = this.location[1] - a.location[1];
		return (float) Math.sqrt(dy*dy + dx*dx);
	}
	abstract public void collide(Obj hitter, int delta);
	
	
	//abstract public float[] setSpeed(float[] speed);
	
}
