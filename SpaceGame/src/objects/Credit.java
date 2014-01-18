package objects;


import game.Helper;
import game.SimpleTest;

import org.newdawn.slick.Color;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public class Credit extends Obj
{
	public int amount;
	public float direction;
	private Color color;
	private int size;
	private float speed;
	
	public Credit(float[] location, int direction, int amount, SimpleTest gameInst)
	{
		speed = (float) (Math.random() * 1.5 + .5);
		
		System.out.println(amount);
		this.location = location;
		this.direction = direction;
		this.gameInst = gameInst;
		this.amount = amount;
		this.mass = 10;
		size = 4;
		shape = new Circle(location[0], location[1], size);
		velocity = new float[2];
		velocity[0] = (float) (speed * Math.cos(direction));
		velocity[1] = (float) (speed * Math.sin(direction));
		if (amount == 15)
			color = Color.lightGray;
		else if (amount == 30)
			color =  Color.blue;
		else if (amount == 45)
			color = Color.yellow;
		
	}

	public void changeDir(float dir)
	{
		this.direction = dir;
		velocity[0] = (float) (speed*Math.cos(dir));
		velocity[1] = (float) (speed*Math.sin(dir));
		
	}
	public void draw(Graphics gfloat) 
	{
		shape = new Circle(location[0], location[1], size);
		gfloat.setColor(color);
		gfloat.fill(shape);
		
	}

	public void update(int delta)
	{
		checkBorders();

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
	public void collide(Obj hitter, int delta) {
		if (hitter instanceof Ship)
		{
			Ship hit = (Ship) hitter;
			if (hit.credit(amount))
			{
				System.out.println("removing");
				this.remove(); 
			}
		}
		
	}
	
	
	
}
