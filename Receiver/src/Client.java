import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

public class Client 
{
    public static void main(String[] args) 
    {
    	String address = "127.0.0.1";
    	int portNumber = 8080;

    	try 
    	{
    		System.out.println("Connecting to server: " + address +
    				" on port: " + portNumber);

    		Socket client = new Socket(address, portNumber);
    		System.out.println("Client connected to " +
    				client.getRemoteSocketAddress());

    		DataOutputStream out = new DataOutputStream(client.getOutputStream());
    		DataInputStream in = new DataInputStream(client.getInputStream());
    		
    		SAXBuilder builder = new SAXBuilder();
    		Document document = (Document) builder.build(in);
    		
    		Deserializer deserializer = new Deserializer();
    		Object obj = deserializer.deserialize(document);

    		Inspector inspector = new Inspector();
    		
    		inspector.inspect(obj, true);
    		
    		client.close();
    	} catch (Exception e) 
    	{
    		e.printStackTrace();
    	}
    }
}
