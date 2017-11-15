import java.util.List;
import org.jdom2.*;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import java.lang.Class;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class Deserializer 
{
	public Deserializer()
	{
		
	}
	
	public Object deserialize(Document document)
	{
		Object obj = new Object();		
		try
		{
			Element root = document.getRootElement();
			List<Element> objects = root.getChildren("object");
			for (Element e : objects)
			{
				Class objClass = Class.forName(e.getAttributeValue("class"));
				obj = objClass.newInstance();
				List<Element> fields = e.getChildren("field");
				for (Element f : fields)
				{
					Field field = objClass.getDeclaredField(f.getAttributeValue("name"));
					if (field.getType() == int.class) field.set(obj, Integer.parseInt(f.getChildText("value")));
					if (field.getType() == double.class) field.set(obj, Double.parseDouble(f.getChildText("value")));
					if (field.getType() == boolean.class) field.set(obj, Boolean.parseBoolean(f.getChildText("value")));
				}			
			}
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		return obj;
	}
}
