package game;

import objects.Asteroid;
import objects.Base;
import objects.CollisionChecker;
import objects.Obj;

import game.Team.objectType;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ships.AutonomousShip;
import ships.Ship;
import ships.UserShip;

public class SimpleTest extends BasicGameState
{
	private Ship ship;
	public Team[] allTeams;    // neutralTeam;
	public List<List<Obj>> gameList;
	public GameContainer container;
	
	public SimpleTest() { super(); }

	@Override
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		int size = 5;
		int speed = 3;		
		this.container = container;
		container.setTargetFrameRate(60);
		int rotation = 20;
		float range = 100;
		float health = 30;
		int capacity = 15;
		final int NUM_PLAYERS = 1;
		allTeams = new Team[NUM_PLAYERS + 1];
		
		// Make the NPCs (all ways team 0)
		allTeams[0] = new NeutralTeam(0, this);
		
		// Make a team for each player
		for (int i = 1; i <= NUM_PLAYERS; i++)
		{
			allTeams[i] = new PlayerTeam(i, this);
		}
		
		gameList = new List<List<Obj>>();
		
		// Fill gameList
		for (int i = 0; i <= NUM_PLAYERS; i++)
		{
			gameList.add(allTeams[i].getAllUnits());
		}	
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.setLineWidth(.3f);
		
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

	public void update(GameContainer container, int delta) throws SlickException 
	{
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
			ship.fire();
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
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int delta)
			throws SlickException {
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

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}
}