/**
 * @(#)StatementBoxList.java
 *
 *
 * @author R2D2 - Z. Saygýn Doðu
 * @version 1.00 2013/4/19
 */
import java.util.*;
import java.io.Serializable;
import java.beans.*;


public class StatementBoxList extends ArrayList<StatementBox> implements Serializable {

	//PROPERTIES
	private int deep;
	private PropertyChangeSupport pcs;
	private Project projectReferance;
	
	//CONSTRUCTORS
    public StatementBoxList() {
    	super( 0);
    	
    	pcs = new PropertyChangeSupport( this);
    	deep = 1;
    }
    
    public StatementBoxList( int depth)
    {
    	super( 0);
    	
    	pcs = new PropertyChangeSupport( this);
    	deep = depth;
    }
    
    //METHODS
    
    public void addBox( StatementBoxList list)
    {
    	for( int i = 0; i <list.size(); i++)
    	{
    		addBox (list.get(i) );
    	}
    }
    
     // Removes all the boxes
    public void removeAll()
    {
    	for (int i = size() - 1; i >= 0; i--)
    	{
    		removeBox(get(i) );
    	}
    }
    
    public void setProjectReferance( Project referance)
    {
    	projectReferance = referance;
    	
    	for( int i = 0; i < size(); i++)
    	{
    		get(i).setProjectReferance( referance);
    	}
    }
    
    //Set the finder of all boxes in the list
//    public void setFinder( Finder f)
//    {
//    	finder = f;
//    	
//    	for( int i = 0; i < size(); i++)
//    	{
//    		get(i).setFinder( f);
//    	}
//    }
    
    //Return all boxes this list contains with all subboxes of each box in the same list
    public ArrayList<StatementBox> getAllBoxes()
    {
    	ArrayList<StatementBox> allBoxes = new ArrayList<StatementBox>();
    	
    	for( int i = 0; i < size(); i++)
    	{
    		allBoxes.add( get(i) );
    		
    		if( get(i) instanceof ConditionalBox )
    		{
    			allBoxes.addAll( ((ConditionalBox ) get(i) ).getAllBoxes() );
    		}
    		else
    		{
	    		if( get(i).checkExpanded() )
	    		{
	    			allBoxes.addAll( get(i).getSubBoxes().getAllBoxes() );
	    		}
    		}
    	}
    	
    	return allBoxes;
    	
    }
    
    //Remove the desired box from the list
    public void removeBox( StatementBox b)
    {
    	if( deep == 1 && size() <= 1)
    	{
    		super.remove( b);
    		addBox( new StatementBox() );
    	}
    	else if( indexOf( b) < size() - 1 ) //Handle ID's 
    	{
    		for( int i = indexOf( b); i < size(); i++)
    		{
    			get( i).setID( get(i).getPreviousID() );
    		}
    		super.remove( b);
    	}
    	else
    	{
    		super.remove( b);
    	}
    	
    	pcs.firePropertyChange( "remove", null, this);
    }
    
    public StatementBox getSelectedBox()
    {
    	ArrayList<StatementBox> allBoxes = getAllBoxes();
    	
    	for( int i = 0; i < allBoxes.size(); i++)
    	{
    		if( allBoxes.get(i).isSelected() )
    		{
    			return allBoxes.get(i);
    		}
    	}
    	return null;
    }
    
    public Project getProjectReferance()
    {
    	return projectReferance;
    }
    
    
    //Add a box into this list ( or in appropiate list )
	public void addBox( StatementBox b) // STILL NEED TESTING
	{
		boolean added = false;
		
		if( b.getID().length == deep )
		{
			b.setUpperList( this);
			b.setProjectReferance( getProjectReferance() );
			
			
			int size = size(); // this is for not to forget the original size of the collection after going deeper in recursion
			for( int i = 0; i < size; i++)
			{
				if( get(i).compareTo( b) == 0)
				{
					//Remember the box in the location
					StatementBox overlapingBox = get(i);
					
					//Remove the box with same id from the location
					//remove( overlapingBox );
					remove( i);
					
					//Add the new box to that location
					super.add( i, b);
					added = true;
					
					//Set the old box's id to be the next id
					overlapingBox.setID( overlapingBox.getNextID() );
					
				
					//Recursive call of this method to add the old box into the list
					addBox( overlapingBox);
					break;
				}
				else if( get(i).compareTo( b) > 0 )
				{
					super.add( i, b);
					added = true;
					break;
				}
			}
			
			
			if( !added) //The box should go to the end of the list
			{
				
				if( getLastStep() != null && ( b.getID()[deep - 1] > getLastStep().getNextID()[deep - 1] ) )
				{
					b.setID( getLastStep().getNextID() );
				}
				else if(  getLastStep() == null )
				{
					int[] newID = new int[ deep];
					
					for( int i = 0; i < deep - 1; i++)
					{
						newID[ i ] = b.getID()[i];
					}
					newID[ deep - 1 ] = 1; 
					
					b.setID( newID );
				}
				
				
				super.add( b);
			}
		}
		
		else if( b.getID().length > deep )  //the box should go into another list ( a box's subboxes list )
		{
			int[] idOfRequiredBox = new int[ deep];
			
			for( int i = 0; i < idOfRequiredBox.length; i++)
			{
				idOfRequiredBox[i] = b.getID()[i];
			}
			
			try
			{
				getBoxWithID( idOfRequiredBox).addSubBox( b);
			}
			catch( NullPointerException e)
			{
				System.out.println( "NullPointerException: the box does not exist.\nThe box is added to the end of the list.");
				if( getLastStep() != null)
				{
					b.setID( getLastStep().getNextID() );
				}
				else
				{
					int[] newID = new int[ deep];
					
					for( int i = 0; i < deep - 1; i++)
					{
						newID[i] = b.getID()[i];
					}
					newID[deep - 1] = 1;
					b.setID( newID);
				}
				addBox( b);
			}
		}
		else // user is trying to add a super box into a deep list
		{
			System.out.println( "Error: The box is added to the end of the list.");
			if( getLastStep() != null)
			{
				b.setID( getLastStep().getNextID() );
			}
			else
			{
				int[] newID = new int[ deep];
				
				for( int i = 0; i < b.getID().length; i++)
				{
					newID[i] = b.getID()[i];
				}
				for( int i = b.getID().length ; i < deep ; i++)
				{
					newID[i] = 1;
				}
				b.setID( newID);
			}
			addBox( b);
		}
		
		pcs.firePropertyChange( "list", null, this);
	}
    
    //Return the box with a desired id if it exist
    public StatementBox getBoxWithID( int[] id)
    {
    	if( id.length == deep)
    	{
	    	int index = Arrays.binarySearch( toArray(), new StatementBox( id) );
	    	if( index >= 0)
	    	{
	    		return (StatementBox ) toArray()[ index];
	    	}
    	}
    	return null;
    }
    
    //return depth of this list
    public int getDepth()
    {
    	return deep;
    }
    
    //Add a Variable to each box in this list
    public void addVariable( Variable v)
    {
    	for( int i = 0; i < size(); i++)
    	{
    		get(i).addVariable( v);
    	}
    }
    
    //Return String representation of this list
    public String toString()
    {
    	String s = "";
    	
    	for( int i = 0; i < size(); i++)
    	{
    		s = s + "\n" + get(i).toString();
    	}
    	
    	return s;
    }
    
    //Swap up a box
	public void swapUp( StatementBox b ){
		
		int index = indexOf( b);
		
		if(  b.getPreviousStep() != b)
		{
			//Handle ID's
			b.getPreviousStep().setID( b.getID() );
			
			b.setID( b.getPreviousID() );
		
		
			//Swap in actual list
			StatementBox temp = get( index);
			remove( index);
			add( index - 1, temp);
		}
		
		pcs.firePropertyChange( "swap", null, this);
	}
	
	//Swap down a box 
	public void swapDown( StatementBox b ){
		
		int index = indexOf( b);
		
		//Handle ID's
		if( b.getNextStep() != b)
		{
			b.getNextStep().setID( b.getID() );
			b.setID( b.getNextID() );
		
			//Swap in actual list
			StatementBox temp = get( index);
			remove( index);
			add( index + 1, temp);
		}
		
		pcs.firePropertyChange( "swap", null, this);
	}
	
	//Add a new box to the end of the list
	public void addNewBox()
	{
		add( new StatementBox( getLastStep().getNextID()));
	}
	
	//Return the firts box in this list
	public StatementBox getFirstStep()
	{
		return get( 0);
	}
	
	//Return the last box in this list
	public StatementBox getLastStep()
	{
		if( size() > 0 )
		{
			return get( size() - 1);
		}
		else
			return null;
	}
	

	//increase font size of this box's html view
	public void increaseFontSize()
	{
		for( int i = 0; i < size(); i++)
		{
			get(i).increaseFontSize();
			pcs.firePropertyChange( "font", null, this);
		}
	}
	
	//decrease font size of this box's html view
	public void decreaseFontSize()
	{
		for( int i = 0; i < size(); i++)
		{
			get(i).decreaseFontSize();
			pcs.firePropertyChange( "font", null, this);
		}
	}
	
	//set font size of this box's html view
	public void setFontSize( int size)
	{
		for( int i = 0; i < size(); i++)
		{
			get(i).setFontSize( size);
			pcs.firePropertyChange( "font", null, this);
		}
	}
	
	//Add a property change listener to this list
	public void addPropertyChangeListener( PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener( listener );
	}

	public void changeWord(String oldName, String newName) 
	{
		for ( int i = 0; i < size(); i++)
		{
			get(i).changeWord( oldName, newName);
		}
	}

    
    
}