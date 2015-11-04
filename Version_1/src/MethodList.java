import java.util.*;
import java.io.*;

/**
 * MethodList - A class that contains methods in an ArrayList
 * 
 * @author Ali Naci Uysal from group G1B (R2D2)
 * @version 1.00 02/04/2013
 */
 
public class MethodList extends ArrayList<PCDMethod> implements Serializable
{
	// PROPERTIES
	private 	FileOutputStream saveFile;
	
	// CONSTRUCTORS
	public MethodList() 
	{
		super();
	}
	
	// METHODS
	
	// A method that saves the current method list
	public void saveList()
	{
		try{
			
			// Opens a file named Methods.met to write to
			saveFile = new FileOutputStream( "Methods.met");
			
			// Creates an ObjectOutputStream to put objects into save file
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			
			// Saves the whole list
			save.writeObject( this);
			
			// Close the file.
			save.close(); 
		}
		catch( Exception e){
			// If error exists then prints the information
			e.printStackTrace(); 
		}
	}
	
	// A method that loads the method list from a file
	public MethodList loadList (File saveFile)
	{		
		try {
			FileInputStream fis = new FileInputStream( saveFile.getName() );
			ObjectInputStream ois = new ObjectInputStream( fis);
			
			// Typecast is needed because ObjectInputStream's readObject() method returns a generic Object
			MethodList ml = (MethodList) ois.readObject();
			
			// Closes the file
			ois.close();
			
			return ml;
		}
		catch( Exception e) {
			// If error exists then prints the information
			e.printStackTrace();
			return null;
		}		
	}
	
	// A method that adds the given method to the method list
	public void addMethod( PCDMethod method)
	{
		this.add( method);
		sort();
	}
	
	// A method that removes the specific method from the method list
	public void removeMethod( PCDMethod method)
	{
		this.remove( method);
	}
	
	// A method that returns the method in the given index 
	public Object getMethod( int index)
	{
		return this.get( index);
	}
	
	// A method of Java's Collections class that sorts the specified list
	public void sort()
	{
		Collections.sort( this);
	}
}
