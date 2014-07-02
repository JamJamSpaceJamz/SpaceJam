package gui;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.FontUtils;

public class BottomMenu 
{
	private static int items = 4;
	private static String[] options = {"[B]uy Objects", "Use [C]ommands", "Toggle [O]ptions", "[Q]uit"};
	
	private static Font fontS = new Font("Console", Font.BOLD, 20);
	private static TrueTypeFont ttfS = new TrueTypeFont(fontS, true);
	
	public static void draw(Graphics g, GameContainer c)
	{
		int w = c.getWidth();
		int h = c.getHeight();
		
		int workingH = h - 53;
		g.setColor(Color.gray);
		Rectangle bkgrnd = new Rectangle(0, workingH, w, h);
		g.fill(bkgrnd);
		g.setColor(Color.white);
		g.setLineWidth(5);
		g.drawLine(0, workingH, w, workingH);
		
		for (int x = 0; x < items; x++)
		{
			int x_loc = w*x/items;
			g.drawLine(x_loc, workingH, x_loc, h);
			int x_width = w/items;
			int x_center = x_loc - x_width/2;
			FontUtils.drawCenter(ttfS, options[x], x_loc, workingH + (h - workingH)/2 - 10, x_width, Color.white);
		}
	}
}
