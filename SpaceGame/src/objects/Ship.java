package objects;

import game.Helper;
import game.List;
import game.SimpleTest;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class Ship extends Obj
{
	public float[] location, acceleration;
	private int rotateSpd, speed, size;
	private boolean turnLeft, turnRight, accelerate, stop;
	private float rotation, range;
	private SimpleTest gameInst;

	public Ship (int size, int speed, int rotateSpd, float range, SimpleTest inst)
	{
		location = new float[2];
		location[0] = 200;
		location[1] = 200;
		velocity = new float[2];
		acceleration = new float[2];
		this.speed = speed;
		this.size = size;
		this.rotateSpd = rotateSpd;
		this.range = range;
		rotation = 0;
		gameInst = inst;  
	}

	public void draw(Graphics g)
	{
		float[] points = new float[6];
		points[0] = (size*Helper.cos(0 + rotation) + location[0]);
		points[1] = (size*Helper.sin(0 + rotation) + location[1]);


		points[2] = (size*Helper.cos(130 + rotation) + location[0]);
		points[3] = (size*Helper.sin(130 + rotation) + location[1]);


		points[4] = (size*Helper.cos(230 + rotation) + location[0]);
		points[5] = (size*Helper.sin(230 + rotation) + location[1]);

		shape = new Polygon(points);

		g.setColor(Color.green);
		g.fill(shape);

		// visual representation of the range of the ship
		Circle circle = new Circle(location[0], location[1], range);
		g.draw(circle);
	}

	public void rotateLeft(boolean turn)
	{
		turnLeft = turn;    
	}

	public void rotateRight(boolean turn)
	{
		turnRight = turn;    
	}

	public void stop(boolean truth)
	{
		stop = truth;
	}

	// is called once a frame and updates the ship
	// with its new location
	public void update(int delta)
	{
		float d = .01f*delta;
		if (turnRight)
			rotation += rotateSpd*.01f*delta;
		if (turnLeft)
			rotation -= rotateSpd*.01f*delta;

		if (!checkBorders() && accelerate )
		{
			acceleration[0] = (float) Helper.cos(rotation)*speed;
			acceleration[1] = (float) Helper.sin(rotation)*speed;

			velocity[0] += acceleration[0]*delta*.01f;
			velocity[1] += acceleration[1]*delta*.01f;
		}
		
		for(int i = 0; i < 2; i++)
		{           
			location[i] += velocity[i]*delta*.01f;
			if (stop)
			{
				velocity[i] -= velocity[i]/10 * d;
			}
		}
	}

	private boolean checkBorders()
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
	// creates a bullet and adds it to the game's bulletlist\
	// (future work: recoil?)
	public void fire()
	{

		int counter = 0;
		List<Obj> pointer = gameInst.bulletList;
		while (pointer.next != null)
		{
			counter++;

			pointer = pointer.next;
		}
		System.out.println("fire! " + counter);
		List<Obj> wrapper = new List<Obj>();
		Bullet shot = new Bullet(location, rotation, range, wrapper);
		wrapper.data = shot; wrapper.previous = pointer;
		pointer.next = wrapper;
	}

	// changes acceleration to false or true
	// (needed because of the way keypresses work)
	public void accelerate(boolean acc)
	{
		accelerate = acc;
	}

	public void collide(Obj hitter, int delta)
	{
		collided = true;
		if (hitter instanceof Asteroid)
		{
			// forces this object to move back a step
			CollisionChecker.backStep(this, 2*delta);
			
			// speed of the object you are colliding into
			float[] hSpeed = hitter.setSpeed(null);
			hSpeed[0] += velocity[0] / 2;
			hSpeed[1] += velocity [0] / 2;
			
			velocity[0] *= -1.0/2;
			velocity[1] *= -1.0/2;
		}
	}
}