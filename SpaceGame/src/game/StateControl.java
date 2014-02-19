package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StateControl extends StateBasedGame 
{
	private final static int WIDTH = 1000;
	private final static int HEIGHT = 600;
	private final static int FPS = 60;
	public static void main(String[] args)
	{
		System.out.println("Started");
		try {
            AppGameContainer app = new AppGameContainer(new StateControl("I'm in Spaaaaceeee."));
            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.setTargetFrameRate(FPS);
            app.setShowFPS(true);
            app.start();
        } catch(SlickException e) {
            e.printStackTrace();
        }
	}
	public StateControl(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new Menu());
		addState(new SimpleTest());
		
	}

}
