package game;


import objects.Asteroid;
import objects.Bullet;
import objects.CollisionChecker;
import objects.Obj;
import objects.Ship;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;

public class SimpleTest extends BasicGame 
{
	private Ship ship;
	public List<Obj> bulletList;
	public List<Obj> astList;
	public List<List<Obj>> gameList;
	public GameContainer container;
	public SimpleTest() 
	{
		super("SpaceGame");
		
	}

	// initializes every variable needed in SpaceJam
	@Override
	public void init(GameContainer container) throws SlickException 
	{
		this.container = container;
		container.setTargetFrameRate(60);
		int size = 5;
		int speed = 3;
		int rotation = 20;
		float range = 40;
		ship = new Ship(size, speed, rotation, range, this);
		List<Obj> shipList = new List<Obj>();
		shipList.add(ship);
		
		
		bulletList = new List<Obj>();
		
		astList = new List<Obj>();
		for (int i = 0; i < 2; i++)
		{
			float[] loc = new float[2];
			loc[0] = (float) (Math.random()* container.getWidth());
			loc[1] = (float) (Math.random()* container.getHeight());
			
			List<Obj> pointer = astList;
			while (pointer.next != null)
			{
				pointer = pointer.next;
			}
			List<Obj> wrapper = new List<Obj>();
			Asteroid ast = new Asteroid(loc, (float) (Math.random() * 10 + 4), wrapper, this);
			wrapper.data = ast; wrapper.previous = pointer;
			pointer.next = wrapper;
		}
		
		gameList = new List<List<Obj>>();
		gameList.add(bulletList);
		gameList.add(astList);
		gameList.add(shipList);
	}

	// updates every object (called once 
	// per frame before draw)
	@Override
	public void update(GameContainer container, int delta) throws SlickException 
	{
		//ship.update(delta);
		CollisionChecker.checkAll(gameList, delta);
		List<List<Obj>> pointer = gameList.next;
		while(pointer != null)
		{
			List<Obj> pointer1 = pointer.data.next;
			while (pointer1 != null)
			{
				pointer1.data.update(delta);
				pointer1 = pointer1.next;
			}
			pointer = pointer.next;
		}
		
	}
	
	// checks for keyPresses
	@Override
	public void keyPressed(int key, char c)
	{
		if (c == 'a')
			ship.rotateLeft(true);
		else if (c == 'd')
			ship.rotateRight(true);
		else if (c == 'w')
			ship.accelerate(true);
		else if (c == ' ')
		{
			//System.out.println(ship.location[0] + "  " + ship.location[1]);
			ship.fire();
		}
		else if (c == 's')
			ship.stop(true);
	}
	
	// checks for keyReleases
	@Override
	public void keyReleased(int key, char c)
	{
		if (c == 'a')
			ship.rotateLeft(false);
		else if (c == 'd')
			ship.rotateRight(false);
		else if (c == 'w')
			ship.accelerate(false);
		else if (c == 's')
			ship.stop(false);
	}
	
	// draws each of the shapes (called after update)
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException 
	{
		
		g.setLineWidth(.3f);
		//System.out.println(container.getHeight() + "  " + container.getWidth());
		//ship.draw(g);

		List<List<Obj>> pointer = gameList.next;
		while(pointer != null)
		{
			List<Obj> pointer1 = pointer.data.next;
			while (pointer1 != null)
			{
				pointer1.data.draw(g);
				pointer1 = pointer1.next;
			}
			pointer = pointer.next;
		}
		
	}
	
	// starts the game (Do not touch this)
	public static void main(String[] args) 
	{
		try 
		{
			AppGameContainer app = new AppGameContainer(new SimpleTest());
			app.start();
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}
}