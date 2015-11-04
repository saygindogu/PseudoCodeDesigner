import java.util.ArrayList;
import java.util.Collections;
import java.beans.*;

/**
 * VariableList - Holds all variables in an ArrayList.
 * @author G1B R2D2 - Can Mergenci
 * @version 1.00 02/04/2013
 */
public class VariableList extends ArrayList<Variable> {
	
			
	//PORPS
	PropertyChangeSupport pcs;
	
	// CONSTRUCTORS
	public VariableList()
	{
		super();
		pcs = new PropertyChangeSupport( this);
		addVariable( new Variable( "DefaultVariable"));
		
	}
	
	// METHODS
	public void addPropertyChangeListener( PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener( listener);
	}
	
	public void removeAll()
	{
		for (int i = size() - 1; i >= 0; i--)
		{
			removeVariable(get( i) );
		}
	}
	
	public void addVariable( Variable vary )
	{
		// For determining existence of variable in this.
		boolean isThere = false;
		for( int i = 0; i < this.size(); i++ )
		{
			if( vary.compareTo( this.get(i) ) == 0 )
			{
				isThere = true;
				break;
			}
		}
		
		if( !isThere )
		{
			// Add method of ArrayList.
			this.add( vary );
		}
		
		vary.setUpperList( this);
		
		sort();
	}
	
	public void addVariable( VariableList list )
	{
		for( int i = 0; i < list.size(); i++ )
		{
			addVariable( list.get(i) );
		}
		pcs.firePropertyChange( "variable", null, this);
	}
	
	public void removeVariable( Variable vary )
	{
		// Remove method of ArrayList.
		this.remove( vary );
		pcs.firePropertyChange( "variable", null, this);
	}
	
	public void changeVariable( Variable vary, String newName )
	{
		// Index of corresponding variable.
		int index = 0;
		for( int i = 0; i < this.size(); i++ )
		{
			if( vary == this.get(i) )
				index = i;
		}
		// Call setName method of Variable class.
		this.get(index).setName( newName );
		sort();
		pcs.firePropertyChange( "variable", null, this);
	}
	
	public void sort()
	{
		Collections.sort(this);
		pcs.firePropertyChange( "variable", null, this);
	}
	
	public Variable findVariable( String name )
	{
		for( int i = 0; i < this.size(); i++ )
		{
			if( name.equalsIgnoreCase( this.get(i).getName() ) )
				return this.get(i);
			
		}
		return null;
	}
	
	public void changeName( String oldName, String newName )
	{
		findVariable( oldName).setName( newName);
		sort();
		pcs.firePropertyChange( "variable", null, this);
	}
	
	public void remove( String name )
	{
		removeVariable( findVariable( name));
		pcs.firePropertyChange( "variable", null, this);
	}
	
	
	

}
