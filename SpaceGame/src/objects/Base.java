package objects;

import game.Helper;
import game.List;
import game.SimpleTest;
import game.Team;
import game.Team.objectType;
import game.PlayerTeam;
import game.Constants;
import objects.Bullet;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

public class Base extends Obj
{
	private final float size, range;
	private final float TURNSPEED = Constants.base_turnspeed;
	private final Color color;
	private float rotation;
	private float[] points;
	
	public Base(float size, float[] loc, Color color, SimpleTest gameInst, Team team)
	{
		this.size = size;
		this.location = loc;
		this.color = color;
		this.gameInst = gameInst;
		this.mass = Constants.base_mass;
		this.team = team;
		this.range = Constants.base_range;
		this.health = Constants.base_health;
		
		rotation = 0;
		velocity = new float[2];
		velocity[0] = 0;
		velocity[1] = 0;
		initPts();
		// Base should not start with turrets.
//		generateTurrets(5);
	}
	
	private void initPts()
	{
		// number of sides the base has
		int numPts = Constants.base_numPoints;
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
		gfloat.setColor(getColor());
		shape = new Polygon(points);
		gfloat.fill(shape);
		gfloat.drawString("" + ((PlayerTeam) team).setCredits(0), location[0], location[1] + size*1.3f);
	}
	
	private void generateTurrets(int numTurrets)
	{
		//List<Obj> pointer = gameInst.turretList;		
		float dir;
		for (int i=0; i<numTurrets; i++)
		{
			dir = i*360f/numTurrets;
			Turret turret = new Turret(this, dir, gameInst);
			team.addUnit(objectType.TURRET, turret);
		}
	}

	@Override
	public void update(int delta) 
	{
		int numPts = Constants.base_numPoints;
		
		for (int i = 0; i < numPts; i++)
		{
			float dir = i*360f/numPts;
			rotation += TURNSPEED * Constants.game_delta_scale * delta;
			points[2*i] = size*Helper.cos(dir + rotation) + location[0];
			points[2*i + 1] = size *Helper.sin(dir + rotation) + location[1];
		}
		
		checkBorders();
		location[0] += velocity[0]*delta*Constants.game_delta_scale;
		location[1] += velocity[1]*delta*Constants.game_delta_scale;
	}
	
	public void credit(int amount)
	{
		((PlayerTeam) team).setCredits(amount);
	}
	
	@Override
	public boolean collide(Obj hitter) 
	{
		if (!(hitter instanceof Bullet)) {		
			CollisionChecker.backStep(hitter, 40);
			hitter.velocity[0] *= -0.5f;
			hitter.velocity[1] *= -0.5f;
		} else {
			((Bullet) hitter).hit(this);
		}
		
		return false;
	}
	
	public void remove()
	{
		int credit = ((PlayerTeam) team).setCredits(0);
		while (credit > 0)
		{
			System.out.println("Ship removed. Credit: " + credit);
			int worth = 40;
			if (credit < 40) worth = credit;
			credit -= worth;
			
			int direction = (int) (Math.random() *360);
			float[] location = new float[2];
			location[0] = this.location[0] + Helper.cos(direction)*size*5.03f;
			location[1] = this.location[1] + Helper.sin(direction)*size*5.03f;
			Credit crd = new Credit(location, direction, worth, gameInst);
			
		}
		objInst.remove();
	}
	
	public float[] getLocation() {
		return this.location;
	}
}
