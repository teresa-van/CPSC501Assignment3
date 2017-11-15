import java.lang.Object;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.*;

public class Serializer {

	public Serializer()
	{
		
	}
	
	//Wrapper Class Check, Credit: https://stackoverflow.com/questions/709961/determining-if-an-object-is-of-primitive-type
	private static final Set<Class> WRAPPER_TYPES = new HashSet(Arrays.asList(
		    Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class));
	
	public static boolean isWrapperType(Class c) 
	{
	    return WRAPPER_TYPES.contains(c);
	}
	
	public org.jdom2.Document serialize(Object obj)
	{		
		Class objClass = obj.getClass();
		int id = obj.hashCode();
		
		Element root = new Element("serialized");
		Element objElem = new Element("object");
		objElem.setAttribute("class", objClass.getName());
		objElem.setAttribute("id", Integer.toString(id));
		root.addContent(objElem);

		Field[] fields = objClass.getDeclaredFields();
		for (Field field : fields)
		{	
			try
			{
				//Set field to accessible and get the value of the object
				field.setAccessible(true);
				Object value = field.get(obj);
				try
				{
					//Check if the value is a primitive or not
					if (value.getClass().isPrimitive() || isWrapperType(value.getClass())) 
					{
						Element fieldElem = new Element("field");
						fieldElem.setAttribute("name", field.getName());
						fieldElem.setAttribute("declaringclass", field.getDeclaringClass().getName());
						objElem.addContent(fieldElem);
						
						Element valueElem = new Element("value");
						valueElem.addContent(field.get(obj).toString());
						fieldElem.addContent(valueElem);
					}
					//Check if the value is an array
					else if (value.getClass().isArray())
					{
						Element arrayElem = new Element("object");
						arrayElem.setAttribute("class", value.getClass().getName());
						arrayElem.setAttribute("id", Integer.toString(value.hashCode()));
						arrayElem.setAttribute("length", Integer.toString(Array.getLength(value)));
						for (int i = 0; i < Array.getLength(value); i++)
						{
							Element valueElem = new Element("value");
							valueElem.addContent(Array.get(value, i).toString());
							arrayElem.addContent(valueElem);
						}
						root.addContent(arrayElem);
					}
					else
					{
						Element fieldElem = new Element("field");
						fieldElem.setAttribute("name", field.getName());
						fieldElem.setAttribute("declaringclass", field.getDeclaringClass().getName());
						objElem.addContent(fieldElem);
						
						Element reference = new Element("reference");
						reference.addContent(Integer.toString(field.hashCode()));
						fieldElem.addContent(reference);
					}
				}
				catch (NullPointerException e) { System.out.println("\t " + field.getName() + " = Null"); }
			}
			catch (Exception e) { e.printStackTrace(); }
		}
		
		Document document = new Document(root);
		
		return document;
	}
}
