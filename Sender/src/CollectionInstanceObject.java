import java.util.ArrayList;
public class CollectionInstanceObject 
{
	ArrayList<SimpleObject> list;
	
	public CollectionInstanceObject()
	{
		this.list = new ArrayList<SimpleObject>();
		this.list.add(new SimpleObject());
		this.list.add(new SimpleObject());
		this.list.add(new SimpleObject());
	}
}
