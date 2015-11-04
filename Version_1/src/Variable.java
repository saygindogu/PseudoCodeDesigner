import java.util.Random;
import java.io.*;
/**
 *
 * Variable Class - Variable
 *
 * @author G1B R2D2 - Bengisu Batmaz
 *
 */

class Variable implements Comparable, Serializable
{	
	// PROPERTIES
	private StringBuffer name;
	private VariableList upperList;
	
	// here's some colors that can be useful.
	private String[]	 colors = { "aqua", "blue", "fuchsia", "gray", "green", "lime", "maroon" 
						,"navy", "olive", "orange", "purple", "red", "silver", "teal", "lightblue"
						, "darkorange", "firebrick", "darkgreen", "lightcoral" };
	int i;
	
	// CONSTRUCTOR
	Variable() {
		name = new StringBuffer();
	}
	
	Variable(String name2) {
	
		name = new StringBuffer(name2);
		Random r = new Random();
		i = r.nextInt(19);
	
	}
	
	// METHODS
	
	public VariableList getUpperList()
	{
		return upperList;
	}
	
	public void setUpperList( VariableList list)
	{
		upperList = list;
	}
	
	// Set& Get methods
	public void setName( String name){
		this.name = new StringBuffer( name);
	}
	
	public String getName(){
		return name.toString();
	}
		
	public String toString(){
		return this.getName();
	}
	
	// HTML code version of the toString method that returns the text in color.
	public String toHTMLString(){
		if ( name.length() == 0 )
		      return null;
		else {
//			// for background
//			StringBuffer build = new StringBuffer( "<span style=\"background-color: "+ colors[i] +"\">" );
//			build.append(name.toString());
//			build.append( "</span>" );
//			return build.toString();
		
		
			/****************************************************************************/
			/*					Changing the color of the text.							*/
			/****************************************************************************/
			
			/**
			 *	span tag in html is used to color a part of the text, either background or 
			 *  the text. font-weight is used to change the thickness of the characters.
			 *  background-color, color, font-weight are all CSS properties.
			 */
			 
			StringBuffer build2 = new StringBuffer( "<span style=\"color: "+ colors[i] +";font-weight:bold\">" );
			build2.append(name.toString());
			build2.append( "</span>" );
			return build2.toString();
		}
	}
	
	// Overrides the comparable interface -> compareTo
	@Override
	public int compareTo( Object obj) {
		return name.toString().compareTo( ( ( (Variable) obj).toString() ) );
	}
	
	// Equals method	
	public boolean equals( Variable obj){
		if( name.toString().equalsIgnoreCase( (obj).toString() ) )
			return true;
		else 
			return false;
	}
	
}

