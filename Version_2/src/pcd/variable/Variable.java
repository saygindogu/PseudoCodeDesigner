/** Variable.java
 * 
 * This class holds the basic variable informations in Pseudo Code Designer
 * 
 * @author Saygın Doğu
 * @author Kerim Bartu Kömürcü
 */
package pcd.variable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Random;

public class Variable implements Comparable<Variable>, Serializable
{
	// PROPERTIES
	public static final byte 		DEFAULT_FONT_SIZE = 12;
	
	private String 					name;
	
	public transient static String[]				colors = { "aqua", "blue", "fuchsia", "gray", "green", "lime", "maroon" // Colors for the toHTMLString()
							,"navy", "olive", "orange", "purple", "red", "silver", "teal", "lightblue"
							, "darkorange", "firebrick", "darkgreen", "lightcoral" };
	private VariableList list;
	
	private int randomColorSelector;
	
	// CONSTRUCTORS
	/**
	 * Constructor 1
	 * @param name - name of the new variable
	 */
	public Variable(String name)
	{
		this.name = name;
		
		Random generator = new Random();
		randomColorSelector = generator.nextInt(19);
	}
	
	// METHODS
	
	/**
	 * 
	 * @param name - new name of the variable
	 */
	public void setName( String name)
	{
		this.name = name;
	}
	
	public void setVariableList( VariableList list){
		this.list = list;
	}
	
	public VariableList getVariableList(){
		return list;
	}
	
	/**
	 * 
	 * @return the name of the variable
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * 
	 * @param fontSize - new fontSize of the variable
	 * 
	 */
	
	/**
	 * 
	 * @return the name of the variable
	 */
	@Override
	public String toString()
	{
		return this.getName();
	}
	
	/**
	 * @return Compares the variable objects
	 */
	@Override
	public int compareTo( Variable v) 
	{
		return name.compareTo( v.toString() );
	}
	
	/**
	 * 
	 * @param obj - A Variable to compare with this variable
	 * @return true if they are same otherwise return false
	 */
	public boolean equals( Variable obj)
	{
		if( name.equals( ( (Variable) obj).toString() ) )
			return true;
		
		return false;
	}

	
	/**
	 * 
	 * @param fontSize - fontSize of the variable
	 * @return the highlighted HTML representation of the Variable
	 */
	public String toHTMLString( byte fontSize)
	{
		if ( name.length() != 0 )
		{
			StringBuffer temp = new StringBuffer( "<span style=\"color: "
											+ colors[randomColorSelector] 
											+";font-weight:bold;font-size: " 
											+ fontSize + "\">" );
			temp.append(name);
			temp.append( "</span>" );
			
			return temp.toString();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @return the highlighted HTML representation of the Variable
	 */
	public String toHTMLString()
	{
		return toHTMLString( DEFAULT_FONT_SIZE);
	}
}
