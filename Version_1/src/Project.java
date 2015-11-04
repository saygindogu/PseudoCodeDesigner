import java.util.*;
import java.io.*;

/**
 * Project - The main class of the Pseudo Code Designer that integrates the seperate pieces of objects
 * @author Ali Naci Uysal from group G1B (R2D2)
 *
 * @version 1.00 16/04/2013
 */
import java.beans.*;

public class Project implements Serializable
{
	// PROPERTIES
	private 	StatementBoxList steps;
	private 	VariableList variables;
	private		Finder finder;
	private		PropertyChangeSupport pcs;
	private 	String problemDescription;
	
	// CONSTRUCTORS
	public Project()
	{
		steps = new StatementBoxList();
		variables = new VariableList();
		finder = new Finder( variables);
		
		pcs = new PropertyChangeSupport( this);

		steps.addBox( new StatementBox() );
		
		problemDescription = "";
		
		steps.setProjectReferance( this);
		
		pcs.firePropertyChange( "project", null, this);
	}
	
	public String getProblemDescription()
	{
		return problemDescription;
	}
	
	public void setProblemDescription( String s)
	{
		problemDescription = s;
	}
	
	public PropertyChangeSupport getPropertyChangeSupport()
	{
		return pcs;
	}
	
	public void setSteps( StatementBoxList list )
	{
		this.steps = list;
	}
	
	public void setVariableList( VariableList list)
	{
		this.variables = list;
	}
	
	public void setFinder( Finder f )
	{
		this.finder = f;
	}
	
	public Project( PropertyChangeSupport pcs )
	{
	
		steps = new StatementBoxList();
		variables = new VariableList();
		finder = new Finder( variables);
		
		
		this.pcs = pcs;
		
		variables.addVariable( new Variable( "defaultVariable"));
		steps.addBox( new StatementBox() );
		
		steps.setProjectReferance( this);
		
		pcs.firePropertyChange( "project", null, this);
	}
	
	
	public Project( StatementBoxList steps, VariableList variables)
	{
		this.steps = steps;
		this.variables = variables;
		finder = new Finder( variables);
		
		pcs = new PropertyChangeSupport( this);
	
		steps.setProjectReferance( this);
	}
	
	// METHODS
	
	public Finder getFinder()
	{
		return finder;
	}
	
	public ArrayList<StatementBox> getAllBoxes()
	{
		return steps.getAllBoxes();
	}
	
	// A method that loads the project from a file
	public Project loadProject (File saveFile)
	{
		try {
			FileInputStream fis = new FileInputStream( saveFile.getName() );
			ObjectInputStream ois = new ObjectInputStream( fis);
			
			// Typecast is needed because ObjectInputStream's readObject() method returns a generic Object
			Project project = (Project) ois.readObject();
			
			// Closes the file
			ois.close();
			
			pcs.firePropertyChange( "project", null, this);
			return project;	
		}
		catch( Exception e){
			// If error exists then prints the information
			e.printStackTrace();
		}
		pcs.firePropertyChange( "project", null, this);
		return null;		
	}
	
	// A method that that returns an arraylist that contains statement boxes
	public StatementBoxList getSteps()
	{
		return steps;
	}
	
	public void addPropertyChangeListener( PropertyChangeListener listener )
	{
		pcs.addPropertyChangeListener( listener);
	}
	
	public void pasteBox( StatementBox statementBox)
	{
		steps.addBox( statementBox);
		pcs.firePropertyChange( "project", null, this);
	}
	
	public void addNewBox( int[] id)
	{
		StatementBox statementBox = new StatementBox();
		statementBox.setID( id);
		steps.addBox( statementBox);
		pcs.firePropertyChange( "project", null, this);
	}
	
	// A method that removes the statement box from the project
	public void removeBox( int[] id)
	{
		StatementBox statementBox = steps.getBoxWithID( id);
		steps.removeBox( statementBox);
		pcs.firePropertyChange( "project", null, this);
	}
	
	// PCDMethod that adds & removes the given variable to/from the project using the VariableList
	public void addVariable( Variable v)
	{
		variables.addVariable( v);
		pcs.firePropertyChange( "project", null, this);
	}
	
	public void removeVariable( Variable v)
	{
		variables.remove( v);
		pcs.firePropertyChange( "project", null, this);
	}
	
	// A method that adds the given method to the MethodList
	public void insertMethod( PCDMethod method)
	{
		steps.addBox( method);
		pcs.firePropertyChange( "project", null, this);
	}
	
	public VariableList getVariableList()
	{
		pcs.firePropertyChange( "project", null, this);
		return variables;	
	}

	
	public StatementBox getSelectedBox()
	{
		return steps.getSelectedBox();
	}
	
	public StatementBox cutBox( int[] id)
	{
		StatementBox b = getSteps().getBoxWithID( id);
		steps.removeBox( b);
		return b;
	}
	
	public void changeFontSize( int size)
	{
		steps.setFontSize( size);
	}
	
	public StatementBox copyBox( int[] id)
	{
		return steps.getBoxWithID( id).copy();
	}
	
	public void pasteBox( StatementBox boxToPaste, int[] id)
	{
		boxToPaste.setID( id);
		steps.addBox( boxToPaste);
	}

	public void changeWord( String oldName, String newName) 
	{
		steps.changeWord( oldName, newName);
	}
}


