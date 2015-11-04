/** VariableList.java
 * 
 * This class holds a list of Variables in Pseudo Code Designer
 * 
 * @author Zahit Saygın Doğu
 * @author Kerim Bartu Kömürcü
 */

package pcd.variable;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import pcd.PCDPropertyNames;

public class VariableList extends ArrayList<Variable> implements Serializable {
	
	
	//PORPS
	PropertyChangeSupport pcs;
	
	// CONSTRUCTORS
	public VariableList()
	{
		super();
		pcs = new PropertyChangeSupport( this);		
	}
	
	// METHODS
	
	public void addPropertyChangeListener( PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener( listener);
	}
	
	/**
	 * remove all variables from the list
	 */
	public void removeAll()
	{
		int size = size();
		
		while ( size > 0 )
		{
			removeVariable( get(0) );
			size--;
		}
	}
	
	/**
	 * 
	 * @param variable - variable to add
	 */
	public void addVariable( Variable variable )
	{
		boolean isThere = false;
		if( size() > 0)
		{
			int i = 0;
			
			while( !isThere && i < size())
			{
				if( variable.compareTo( this.get(i) ) == 0 )
				{
					isThere = true;
				}
				i++;
			}
		}
		if( !isThere )
		{
			add( variable );
			variable.setVariableList( this);
		}	
		pcs.firePropertyChange( PCDPropertyNames.VARIABLE_ADD, null, variable);
		sortAlphabetically();
	}
	
	/**
	 * Sorts alphabetically the list
	 */
	private void sortAlphabetically() {
		Collections.sort( this);
		pcs.firePropertyChange( PCDPropertyNames.VARIABLE_SORT, null, this);
	}

	/**
	 * 
	 * @param list - variableList to add
	 */
	public void addVariable( VariableList list )
	{
		for( int i = 0; i < list.size(); i++ )
		{
			add( list.get(i) );
			list.get(i).setVariableList( this);
			pcs.firePropertyChange( PCDPropertyNames.VARIABLE_ADD, null, list.get(i) );
		}
		sortAlphabetically();
	}
	
	/**
	 * 
	 * @param vary - variable to remove
	 */
	public void removeVariable( Variable vary )
	{
		remove( vary );
		pcs.firePropertyChange( PCDPropertyNames.VARIABLE_REMOVE, vary, this);
	}
	
	/**
	 * 
	 * @param variable - a variable to change its name
	 * @param newName - newName of the variable
	 */
	public void changeVariableName( Variable variable, String newName )
	{	
		int index = indexOf( variable);
		
		if( index != -1)
		{
			pcs.firePropertyChange( PCDPropertyNames.VARIABLE_NAME, variable.getName(), newName);
			get(index).setName( newName );
			sortAlphabetically();
		}
	}
	
	/**
	 * 
	 * @param name - string to find the variable
	 * @return if it finds the variable with that name returns the variable else returns null 
	 */
	public Variable findVariable( String name )
	{
		for( int i = 0; i < this.size(); i++ )
		{
			if( name.equals( this.get(i).getName() ) )
				return this.get(i);
		}
		return null;
	}
	
	/**
	 * 
	 * @param oldName - oldName of the Variable
	 * @param newName - newName of the Variable
	 */
	public void changeName( String oldName, String newName )
	{
		Variable variable = findVariable( oldName);
		
		if( variable != null)
		{
			variable.setName( newName);
			sortAlphabetically();
			pcs.firePropertyChange( PCDPropertyNames.VARIABLE_NAME, oldName, newName);
		}
	}
	
	/**
	 * 
	 * @param name - the name of the variable that is to be deleted
	 */
	public void remove( String name )
	{
		Variable variable = findVariable( name);
		removeVariable( variable );
		pcs.firePropertyChange( PCDPropertyNames.VARIABLE_REMOVE, this, variable);
	}
	
	
	

}