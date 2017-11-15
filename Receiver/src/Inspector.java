/*
 * CPSC 501 Assignment #2
 * Written by: Teresa Van (10149274)
 * This class is a reflection object inspector that does a complete introspection of an object at runtime.
 * Created: October 26th, 2017
 * Last Modified: October 27th, 2017
 */

import java.util.*;
import java.lang.reflect.*;

public class Inspector {
	
	//Data structures for holding classes/objects to inspect, and the classes that have already been inspected
	private static Queue<Object> ObjectsToInspect = new ArrayDeque<Object>();
	private static ArrayList<Class> Inspected = new ArrayList<Class>();
	
	//Wrapper Class Check, Credit: https://stackoverflow.com/questions/709961/determining-if-an-object-is-of-primitive-type
	private static final Set<Class> WRAPPER_TYPES = new HashSet(Arrays.asList(
		    Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class));
	
	public static boolean isWrapperType(Class c) {
	    return WRAPPER_TYPES.contains(c);
	}
	//
	
	/*
	 * Constructor
	 */
	public Inspector() { }
	
	/*
	 * Method that does object inspection, outputs all the required information
	 */
	public void inspect(Object obj, boolean recursive)
	{		
		Class objClass = obj.getClass();
		System.out.println("=======================================================");
		System.out.println("OBJECT: " + obj.hashCode());

		Inspected.add(objClass);
		
		System.out.println("=======================================================");	
		System.out.println("Name of Declaring Class: " + objClass.getName());
		
		InspectFields(objClass);
		Inspected.add(obj.getClass());
		InspectFieldValues(objClass, obj, recursive);	
		InspectRecursively(recursive);
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/*
	 * Method for inspecting fields of a class
	 */
	public void InspectFields(Class objClass)
	{
		System.out.println("Fields the Class Declares: ");
		Field[] fields = objClass.getDeclaredFields();
		if (fields.length == 0) System.out.println("\t No fields declared.\n");
		else
		{
			for (Field field : fields)
			{
				System.out.println("\t Field Name: " + field.getName());

				System.out.println("\t Type: " + field.getType().toString());
				int mod = field.getModifiers();
				System.out.println("\t Modifiers: " + Modifier.toString(mod));
				System.out.println("\n");
			}
		}
	}
	
	/*
	 * Method for inspecting field values of a class
	 */
	public void InspectFieldValues(Class objClass, Object obj, boolean recursive)
	{
		System.out.println("Current Value of Each Field: ");
		Field[] fields = objClass.getDeclaredFields();
		if (fields.length == 0) System.out.println("\t No fields.\n");
		else
		{
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
							System.out.println("\t " + field.getName() + " = " + field.get(obj).toString());
						//Check if the value is an array
						else if (value.getClass().isArray())
						{
							System.out.println("\t " + field.getName() + " = Array");
							System.out.println("\t\t Component Type: " + value.getClass().getComponentType());
							System.out.println("\t\t Length: " + Array.getLength(value));
							System.out.print("\t\t Contents: [");
							for (int i = 0; i < Array.getLength(value); i++)
							{
								if (i == Array.getLength(value)-1) System.out.print(Array.get(value, i));
								else System.out.print(Array.get(value, i) + ",");
							}
							System.out.println("]\n");
						}
						//Add object to queue if recursive is set to true and if value is an object reference
						else
						{
							System.out.println("\t " + value.getClass().getName() + " " + field.hashCode());
							if (recursive) 
							{
								if (!ObjectsToInspect.contains(value)) ObjectsToInspect.add(value);
							}
						}
					}
					catch (NullPointerException e) { System.out.println("\t " + field.getName() + " = Null"); }
				}
				catch (Exception e) { e.printStackTrace(); }
			}
			System.out.println("\n");
		}
	}
	
	/*
	 * Method for recursively inspecting the objects/classes stored in the queue
	 */
	public void InspectRecursively(boolean recursive)
	{
		if (recursive)
		{
			//Inspect all objects in the object queue
			while (!ObjectsToInspect.isEmpty())
			{
				if (ObjectsToInspect.element().getClass().getName().equals("java.lang.Class") || 
						ObjectsToInspect.element().getClass().getName().equals("java.lang.Object")) ObjectsToInspect.remove();
				else
				{
					try
					{
						System.out.println("\n\n");
						inspect(ObjectsToInspect.remove(), recursive);
					}
					catch (Exception e) { e.printStackTrace(); }
				}
			}
		}
		
		//Clear all data structures for future test cases
		ObjectsToInspect.clear();
		Inspected.clear();
	}
}
