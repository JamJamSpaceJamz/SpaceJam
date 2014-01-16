package objects;

import game.List;

public class CollisionChecker 
{
	// goes through every type of object in the gamelist and checks for collisions
	// with every other object
	// (works in time N^2)
	public static void checkAll(List<List<Obj>> objList, int delta)
	{
		List<List<Obj>> list1 = objList.next;
		while(list1 != null)
		{
			List<Obj> obj1 = list1.data.next;
			while(obj1 != null)
			{
				List<List<Obj>> list2 = list1;
				List<Obj> obj2 = obj1.next;
				while(list2 != null)
				{
					while(obj2 != null)
					{

						if (obj2.data.shape.intersects(obj1.data.shape))
						{
							obj2.data.collide(obj1.data, delta);
							obj1.data.collide(obj2.data, delta);
						}
						obj2 = obj2.next;
					}
					list2 = list2.next;
					if (list2 != null)
						obj2 = list2.data.next;
				}
				obj1 = obj1.next;
			}
			list1 = list1.next;
			if (list1 != null)
				obj1 = list1.data.next;
		}
	}
	
	// takes an object and a delta and reverts it to 
	// how it was in the last frame
	public static void backStep(Obj thing, int delta)
	{
		float[] speed = thing.setSpeed(null);
		speed[0] *= -1;
		speed[1] *= -1;
		thing.update(delta);
		speed[0] *= -1;
		speed[1] *= -1;
	}

}

