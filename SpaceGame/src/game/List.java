package game;
import objects.Obj;

public class List<Thing> 
{
	// the meat of the list, what it is holding
	public Thing data;
	// the next and previous items in the list
	public List<Thing> previous, next;
	
	// empty initializer
	public List() {}
	
	public List(Thing data, List<Thing> previous)
	{
		this.data = data;
		this.previous = previous;
	}
	
	// remove this node from the whole list
	public void remove()
	{
		previous.next = next;
		if (next != null)
			next.previous = previous;
	}
	
	// creates a wrapper list for the object
	// and adds it immediately after this list
	public void add(Thing a)
	{
		List<Thing> wrapper = new List<Thing>(a, null);
		wrapper.previous = this;
		wrapper.next = this.next;
		if (this.next != null)
			this.next.previous = wrapper;
		if (a instanceof Obj)
		{
			Obj b = (Obj) a;
			b.objInst = (List<Obj>) wrapper;
		}
		this.next = wrapper;
	}	
}
