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
						if (obj2.data == null || obj1.data == null)
							System.out.println("null data");
						if (obj2.data.shape.intersects(obj1.data.shape))
						{
							collision(obj1.data, obj2.data);
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
		float[] thingVel = thing.setVel(null);
		thingVel[0] *= -1;
		thingVel[1] *= -1;
		thing.update(delta);
		thingVel[0] *= -1;
		thingVel[1] *= -1;
	}

	private static void checkandUpdate(Obj obj1, Obj obj2)
	{

	}

	// Takes two objects and calculates their change in
	// velocity after collision using center of mass
	// reference frame.
	public static void collision(Obj object1, Obj object2)
	{
		// collide will now return true if the normal collision is supposed to happen
		if (object1.collide(object2))
		{
			if (object2.collide(object1))
			{
				float[] iVel1, iVel2, fVel1, fVel2;
				float icmVel1, icmVel2, fcmVel1, fcmVel2, VelCM;
				float mass1, mass2;

				iVel1 = new float[2];
				iVel2 = new float[2];
				fVel1 = new float[2];
				fVel2 = new float[2];

				mass1 = object1.mass;
				mass2 = object2.mass;

				// Perform calculation twice--once for each direction
				for (int i = 0; i<2; i++)
				{
					iVel1[i] = object1.velocity[i];
					iVel2[i] = object2.velocity[i];
					// Calculate the velocity of the objects' center of mass
					VelCM = mass1 * iVel1[i] + mass2 * iVel2[i];
					VelCM = VelCM / (mass1 + mass2);
					// Calculate the objects' velocities in the center of mass
					// reference frame
					icmVel1 = iVel1[i] - VelCM;
					icmVel2 = iVel2[i] - VelCM;
					// Calculate final velocities in center of mass reference frame
					fcmVel1 = -icmVel1;
					fcmVel2 = -icmVel2;
					// Convert back to ground reference frame
					fVel1[i] = VelCM + fcmVel1;
					fVel2[i] = VelCM + fcmVel2;			
				}
				CollisionChecker.backStep(object1, 40);
				CollisionChecker.backStep(object2, 40);

				// Set new velocity
				object1.setVel(fVel1);
				object2.setVel(fVel2);
			}
		}
	}

}

