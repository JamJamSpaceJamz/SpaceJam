package game;



import java.awt.Font;

import objects.Asteroid;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.FontUtils;

public class Menu extends BasicGameState
{
	private int id;
	private Asteroid test;
	private Font fontL = new Font("Console", Font.BOLD, 30);
	private Font fontS = new Font("Console", Font.BOLD, 20);
	private TrueTypeFont ttfL = new TrueTypeFont(fontL, true);
	private TrueTypeFont ttfS = new TrueTypeFont(fontS, true);
	private StateBasedGame game;
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
		this.game = game;
		id = 1;
		int[] testLoc = {400, 100};
		//test = new Asteroid(testLoc, 30, null);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		 	g.setColor(Color.white);
		 	FontUtils.drawCenter(ttfL, "SpaceJamJam", container.getWidth()/2, 10, 0, Color.green);
		 	FontUtils.drawCenter(ttfS, "Coded by:", container.getWidth()/2, 55, 0, Color.green);
		 	FontUtils.drawCenter(ttfS, "David Gilhooley", container.getWidth()/2, 80, 0, Color.green);
		 	FontUtils.drawCenter(ttfS, "&", container.getWidth()/2, 100, 0, Color.green);
		 	FontUtils.drawCenter(ttfS, "Blake Lawson", container.getWidth()/2, 120, 0, Color.green);
		    //ttf.drawString(400, 10, "SpaceJamJam", Color.green);
		    int bo = 70; //bottom offset
		 	FontUtils.drawLeft(ttfS, "1) Play Game", 0, container.getHeight() - bo);
		 	FontUtils.drawLeft(ttfS, "2) Code Ships (Under Development)", container.getWidth()/4, container.getHeight() - bo);
		    FontUtils.drawLeft(ttfS, "3) About", 3*container.getWidth()/4, container.getHeight() - bo);
		    FontUtils.drawRight(ttfS, "4) Quit", 4*container.getWidth()/4, container.getHeight() - bo, 0);
		    
		 	
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}
	
	public void keyReleased(int key, char c) {
	    switch(key) {
	    case Input.KEY_1:
	        game.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
	        break;
	    case Input.KEY_2:
	        // TODO: Implement later
	        break;
	    case Input.KEY_3:
	        // TODO: Implement later
	        break;
	    default:
	        break;
	    }
	}
}
