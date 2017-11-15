import java.util.*;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ObjectCreator 
{
	public Object CreateObject() 
	{		
		int choice;

		SimpleObject simple;
		ObjectWithReferences objWithRef;
		PrimitivesArray primitivesArray;
		ReferencesArray referencesArray;
		
		Scanner in = new Scanner(System.in);
		System.out.println("CREATE AN OBJECT");
		System.out.println("Enter a number to create the corresponding object: ");
		System.out.println("1. Simple Object");
		System.out.println("2. Object with references to other objects");
		System.out.println("3. Object with array of primitives");
		System.out.println("4. Object with array of references");
		System.out.println("5. Object using collection instance");
		
		choice = in.nextInt();
		
		if (choice == 1)
		{			
			simple = new SimpleObject();
			while (choice != 4)
			{
				System.out.println("SET A FIELD");
				System.out.println("Enter a number to set a value for the corresponding field:");
				System.out.println("1. int integerValue =  " + simple.integerPrimitive);
				System.out.println("2. double doubleValue =  " + simple.doublePrimitive);
				System.out.println("3. bool boolValue =  " + simple.boolPrimitive);
				System.out.println("4. Done");
				
				choice = in.nextInt();
				
				if (choice == 1)
				{
					System.out.println("Enter an integer value: ");
					simple.integerPrimitive = in.nextInt();
				}
				else if (choice == 2)
				{
					System.out.println("Enter a double value: ");
					simple.doublePrimitive = in.nextDouble();
				}
				else if (choice == 3)
				{
					System.out.println("Enter a boolean value: ");
					simple.boolPrimitive = in.nextBoolean();
				}
			}
			return simple;
		}
		if (choice == 2)
		{			
			objWithRef = new ObjectWithReferences();
			while (choice != 4)
			{
				System.out.println("SET A FIELD");
				System.out.println("Enter a number to set a value for the corresponding field for the referenced simple object:");
				System.out.println("1. int integerValue =  " + objWithRef.simple.integerPrimitive);
				System.out.println("2. double doubleValue =  " + objWithRef.simple.doublePrimitive);
				System.out.println("3. bool boolValue =  " + objWithRef.simple.boolPrimitive);
				System.out.println("4. Done");
				
				choice = in.nextInt();
				
				if (choice == 1)
				{
					System.out.println("Enter an integer value: ");
					objWithRef.simple.integerPrimitive = in.nextInt();
				}
				else if (choice == 2)
				{
					System.out.println("Enter a double value: ");
					objWithRef.simple.doublePrimitive = in.nextDouble();
				}
				else if (choice == 3)
				{
					System.out.println("Enter a boolean value: ");
					objWithRef.simple.boolPrimitive = in.nextBoolean();
				}
			}
			return objWithRef;
		}
		if (choice == 3)
		{			
			primitivesArray = new PrimitivesArray();
			while (choice != 2)
			{
				System.out.println("SET VALUES");
				System.out.println("Current array elements: " + Arrays.toString(primitivesArray.array));
				System.out.println("Enter a number to complete the corresponding actions:");
				System.out.println("1. Set array values");
				System.out.println("2. Done");
				
				choice = in.nextInt();
				
				if (choice == 1)
				{
					for (int i = 0; i < primitivesArray.array.length; i++)
					{
						System.out.println("Enter an integer value for element " + i + ": ");
						primitivesArray.array[i] = in.nextInt();
					}
				}
			}
			return primitivesArray;
		}
		if (choice == 4)
		{			
			referencesArray = new ReferencesArray();
			while (choice != 2)
			{
				System.out.println("SET VALUES");
				System.out.println("Current array elements: " + Arrays.toString(referencesArray.array));
				System.out.println("Enter a number to complete the corresponding actions:");
				System.out.println("1. Set array values");
				System.out.println("2. Done");
				
				choice = in.nextInt();
				
				if (choice == 1)
				{
					for (int i = 0; i < referencesArray.array.length; i++)
					{
						while (choice != 4)
						{
							System.out.println("SET A FIELD FOR OBJECT AT INDEX " + i);
							System.out.println("Enter a number to set a value for the corresponding field for the referenced simple object:");
							System.out.println("1. int integerValue =  " + referencesArray.array[i].integerPrimitive);
							System.out.println("2. double doubleValue =  " + referencesArray.array[i].doublePrimitive);
							System.out.println("3. bool boolValue =  " + referencesArray.array[i].boolPrimitive);
							System.out.println("4. Done");
							
							choice = in.nextInt();
							
							if (choice == 1)
							{
								System.out.println("Enter an integer value: ");
								referencesArray.array[i].integerPrimitive = in.nextInt();
							}
							else if (choice == 2)
							{
								System.out.println("Enter a double value: ");
								referencesArray.array[i].doublePrimitive = in.nextDouble();
							}
							else if (choice == 3)
							{
								System.out.println("Enter a boolean value: ");
								referencesArray.array[i].boolPrimitive = in.nextBoolean();
							}
						}
					}
				}
			}
			return referencesArray;
		}
		in.close();
		return null;		
	}
}
