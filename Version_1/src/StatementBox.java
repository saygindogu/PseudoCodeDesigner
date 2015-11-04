/**
 * @(#)StatementBox.java
 *							The steps of the algorithms in PCD.
 *							A model class which holds the required information for an algorithm step
 *
 * @author R2D2 - Z. Saygýn Doðu
 * @version 1.00 2013/4/18
 */
import java.util.*;
import java.io.Serializable;
import java.beans.*;

public class StatementBox implements Iterable, Comparable, Selectable, Serializable  {

    //PROPERTIES
    final int DEFAULT_CHANGE = 3;

    
	private VariableList inputs; 
	private VariableList outputs;
	private VariableList localVariables;
	private StatementBoxList subBoxes;
	private StatementBoxList upperList;
	private StringBuffer statement;
	private boolean expanded;
	private int[] id;
	private int fontSize;
	private PropertyChangeSupport pcs;
	private boolean selected;
	private Project projectReferance;

    
    //CONSTRUCTORS
    public StatementBox() {
    	
    	pcs = new PropertyChangeSupport( this);
    	
    	selected = false;
    	
    	inputs = new VariableList();
    	outputs = new VariableList();
    	localVariables = new VariableList();
    	subBoxes = new StatementBoxList( 2);

    		
    	statement = new StringBuffer();
    	expanded = false;
    	
    	id = new int[1];
    	id[0] = 1;
    	fontSize = 12;
    }
    
    public StatementBox( int[] id) {
    	
    	pcs = new PropertyChangeSupport( this);
    	
    	selected = false;
    	
    	inputs = new VariableList();
    	outputs = new VariableList();
    	localVariables = new VariableList();
    	subBoxes = new StatementBoxList( id.length + 1);

    	
    	statement = new StringBuffer();
    	expanded = false;
    	
    	this.id = id;
    	fontSize = 12;
    }
    
    //METHODS
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////		GET METHODS			//////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
    public Project getProjectReferance()
    {
    	return projectReferance;
    }
    
    public void setSelected( boolean selected)
    {
    	if( selected)
    	{
    		this.selected = selected;
    		
    		ArrayList<StatementBox> allBoxes = getProjectReferance().getAllBoxes();
    		
    		for( int i = 0; i < allBoxes.size() ; i++)
    		{
    			if( allBoxes.get( i).isSelected() && i != allBoxes.indexOf( this) )
    			{
    				allBoxes.get( i).setSelected( false);
    			}
    		}
			
    	}
    	else
    	{
    		boolean selectedAnother = false;
    		ArrayList<StatementBox> allBoxes = getProjectReferance().getAllBoxes();
    		
    		for( int i = 0; i < allBoxes.size() ; i++)
    		{
    			if( allBoxes.get( i).isSelected() && i != allBoxes.indexOf( this) )
    			{
    				selectedAnother = true;
    				break;
    			}
    		}
    		
    		if( !selectedAnother)
    		{
    			selected = true;
    		}
    		this.selected = selected;
    	}
    	
    	pcs.firePropertyChange( "selected", null, this);
    }
    
    public boolean isSelected()
    {
    	return selected;
    }
    
    public void setProjectReferance( Project referance)
    {
    	projectReferance = referance;
    	subBoxes.setProjectReferance( referance);
    }
    
    //Return the upper list in which this box is contained
    public StatementBoxList getUpperList()
    {
    	return upperList;
    }
    
    
    //Return the statement
	public String getStatement(){
		
		return statement.toString();
	}
	
	//Return a String that is an HTML code which contains plain text
	public String getHTMLStatement(){
		return "<HTML><head><title>" + getStringID() + "</title></head><body style=\" font-size: " + fontSize + "px;\"><center><p>" + statement.toString() + "</p></center></body></HTML>";
	}
	
	//Return a String that is an HTML code which contains the variables highlighted
	public String getHTMLVariablesHighlighted(){
		
		//StringBuffer temp = new StringBuffer( "<HTML><head><title>" + getStringID() + "</title></head><body style=\" font-size: 12px;\"><center><p>");
		
		StringBuffer temp = new StringBuffer( "<HTML><head><title>" + getStringID() + "</title></head><body style=\" font-size: ");
		temp.append( "" + fontSize);
		temp.append( "px;\"><p>" );
	
			
		Scanner iterator = ( Scanner) iterator();
		
		while( iterator.hasNext())
		{
			String word = iterator.next();
		
			if( isVariable( word ) )
			{
				temp.append( getVariable( word).toHTMLString() + " ");
			}
			else
			{
				temp.append( word + " " );
			}
		}
		temp.append( "</p></body></HTML>");
		return temp.toString();
	}
	
	//Return the ID of this box
	public int[] getID(){
		return id;
	}
	
	//Return String representation of ID number
	public String getStringID(){
		String temp = "";
		
		for( int i = 0; i < id.length; i++)
		{
			temp = temp + id[i] + ".";
		}
		
		return temp;
	}
	
	public PropertyChangeSupport getPropertyChangeSupport()
	{
		return pcs;
	}
	
	public StatementBox getNextStep()
	{
		int[] nextID = getNextID();
		
		if( upperList.getBoxWithID( nextID ) != null )
		{
			return upperList.getBoxWithID( nextID );
		}
		else//if this statement box is the last step, retun this
		{
			return this;
		}
	}
	
	public StatementBox getPreviousStep()
	{
		int[] previousID = getPreviousID();
		
		if( upperList.getBoxWithID( previousID ) != null )
		{
			return upperList.getBoxWithID( previousID );
		}
		else//if this statement box is the first step, return this
		{
			return this;
		}
	}
	
	//Return an id that should be the next statement box's id.
	public int[] getNextID()
	{
		int[] nextID = new int[ id.length];
		
		for( int i = 0; i < id.length - 1; i++)
		{
			nextID[i] = id[i];
		}
		
		nextID[ id.length - 1] = id[ id.length - 1] + 1;
		
		return nextID;
	}
	
	public StatementBoxList getSubBoxes()
	{
		return subBoxes;
	}
	
	//Return an id that should be the previous statement box's id. 
	//If this box is the first step of a particular statement box, return the upper statement box's id.
	public int[] getPreviousID()
	{
		int previousIDLength = 0;
		int[] previousID;
		
		for( int i = id.length - 1; previousIDLength == 0 && i >= 0 ; i-- )
		{
			if( id[i] > 1)
			{
				previousIDLength = i + 1;
			}
		}
		
		if( previousIDLength != 0 )
		{	
			previousID = new int[ previousIDLength];
		}
		else //if there is no box to go previous
		{
			previousID = new int[1];
			
			previousID[0] = 1;
			
			return previousID;
		}
		
		for( int i = 0; i < previousID.length - 1; i++)
		{
			previousID[i] = id[i];
		}
		previousID[ previousID.length - 1] = id[ previousID.length - 1] - 1;
		
		return previousID;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////	SET METHODS			///////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//Set font size of the html view of this box
	public void setFontSize( int size)
	{
		if( size > 0 && size < 50)
		{
			fontSize = size;
		}
		pcs.firePropertyChange( "font", null, this);
	}
	
	//Set the upper list of the box
	public void setUpperList( StatementBoxList list)
	{
		upperList = list;
		pcs.firePropertyChange( "upper", null, this);
	}

	//Set the statement to a specified value
	public void setStatement( String s){
		
		statement = new StringBuffer();
			
		statement.append( s.trim() );

		
		addVariable( detectVariables()  );

		
		pcs.firePropertyChange( "statement", null, this);
	}
	
	//Set the ID of the box
	public void setID( int[] id ){
		this.id = id;
		
		if( checkExpanded() )
		{
			for( int i = 0; i < subBoxes.size(); i++)
			{
				int[] newID = new int[ subBoxes.get(i).getID().length ];
				
				//Construct The new ID of the sub box
				for( int j = 0; j < subBoxes.getDepth() - 2 ; j++)
				{
					newID[ j] = id[j];
				}
				
				newID[subBoxes.getDepth() - 2] = id[ id.length - 1 ] ;
				
				for( int j = subBoxes.getDepth() - 1; j < subBoxes.get(i).getID().length; j++ )
				{
					newID[ j] = subBoxes.get(i).getID()[j];
				}
				////////////////////////////////////////////////////////
				
				subBoxes.get(i).setID( newID);
			}
		}
		
		pcs.firePropertyChange( "ID", null, this);
	}
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////		ADD METHODS						/////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //Add a variable to the list of inputs to this statement box
	public void addInput( Variable v){		
		subBoxes.addVariable( v);
		inputs.add( v);
	}
	
	//Add a variable to the list of outputs to this statement box
	public void addOutput( Variable v){
		subBoxes.addVariable( v);
		outputs.add( v);
	}
	
	//Add a variable to the list of local variables to this statement box
	public void addLocalVariable( Variable v){
		subBoxes.addVariable( v);
		localVariables.add( v);
		
	}
	
	//Add the variable to the local variables as default list
	public void addVariable( Variable v){
		
		subBoxes.addVariable( v);
		localVariables.addVariable( v);
	}
	
	//Add variables to local variables
	public void addVariable( VariableList variables){
		
		for( int i = 0; i < variables.size() ; i++)
		{
			addVariable( variables.get(i) );
		}
	}
	
	//Add PropertyChangeListener to pcs
	public void addPropertyChangeListener( PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener( listener );
	}
	
	//Create an instance of new box with the required ID
    public StatementBox addNewBox(){
    	return new StatementBox( getNextID() );
    }
    
    //Add a sub box to this box
	public void addSubBox( StatementBox b){
		subBoxes.addBox( b);
	}
	
	public void addSubBox()
	{
		StatementBox subBox;
		
		int[] subBoxID = new int[ getID().length + 1];
		
		for( int i = 0; i < getID().length; i++)
		{
			subBoxID[i] = getID()[i];
		}
		subBoxID[ getID().length ] = 1;
		
		subBox = new StatementBox( subBoxID);
		
		addSubBox( subBox);
	}
	
	//Add sub boxes to this box by cloning a list
	public void addSubBox( StatementBoxList list)
	{
		for( int i = 0; i < list.size(); i++)
		{
			addSubBox( list.get(i).copy() );
		}
		
	}
    
     //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////// VARIABLE METHODS///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //Given a String find the variable object in inputs outputs or local variables
	private Variable getVariable( String s)
	{
		//Look at inputs
		for( int i = 0; i < inputs.size(); i++)
		{
			if( inputs.get(i).getName().equalsIgnoreCase( s) )
				return inputs.get(i);
		}
		
		
		//Look at outputs
		for( int i = 0; i < outputs.size(); i++)
		{
			if( outputs.get(i).getName().equalsIgnoreCase( s) )
				return outputs.get(i);
		}
		
		
		//Look at local variables
		for( int i = 0; i < localVariables.size(); i++)
		{
			if( localVariables.get(i).getName().equalsIgnoreCase( s) )
				return localVariables.get(i);
		}
		
		return null;
	}
	
	// Returns all the variables in the variableList
	public VariableList getAllVariables()
	{
		VariableList temp = new VariableList();
		
		
		temp.addVariable( inputs);
		temp.addVariable( outputs);
		temp.addVariable( localVariables);
		
		return temp;
	}
    
//	public void Words( String oldName, String newName)
//	{
//		Iterator iterator = iterator();
//		
//		while( iterator.hasNext() )
//		{
//			String word = iterator.next();
//			
//			if( word.equals( oldName ) )
//			{
//				word = newName;
//			}
//		}
//	}
	
    //Detect the variables that are used in the step. Return the variables founded.
	public VariableList detectVariables(){
		
		return getProjectReferance().getFinder().findVariables( statement.toString() ) ;
	}
	
	//Remove variable from any list
	public void removeVariable( Variable v){
		
		inputs.remove( v);
		outputs.remove( v);
		localVariables.remove( v);
	}
	
	//Given a String determine if it is a variable or not
	// 																			EQUALS AND CONTAINS METHODS SHOULD BE USED HERE!!
	public boolean isVariable( String s)
	{
		boolean isVariable = false;
		
		//Look at inputs
		for( int i = 0; !isVariable && i < inputs.size(); i++)
		{
			isVariable = s.equalsIgnoreCase( inputs.get(i).getName() ); //May not use ignore case
		}
		
		//Look at outputs
		for( int i = 0; !isVariable && i < outputs.size(); i++)
		{
			isVariable = s.equalsIgnoreCase( outputs.get(i).getName() ); //May not use ignore case
		}
		
		//Look at local variables
		for( int i = 0; !isVariable && i < localVariables.size(); i++)
		{
			isVariable = s.equalsIgnoreCase( localVariables.get(i).getName() ); //May not use ignore case
		}
		
		return isVariable;
	}
    
    
   	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////// OTHER METHODS///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
	//Expand the box
	public void setExpanded( boolean b){
		expanded = b;
	}
	
	//increase font size of this box's html view
	public void increaseFontSize()
	{
		fontSize += DEFAULT_CHANGE;
	}
	
	//decrease font size of this box's html view
	public void decreaseFontSize()
	{
		fontSize -= DEFAULT_CHANGE;
	}
	
	//Copy the box													
	public StatementBox copy(){
		
		//clone the ID
		int[] newID = new int[ id.length ];
		for( int i = 0; i < id.length; i++)
		{
			newID[i] = id[i];
		}
		
		
		StatementBox temp = new StatementBox( newID );
		
		temp.setProjectReferance( getProjectReferance() );
		
		temp.setStatement( statement.toString() );
		temp.addVariable( getAllVariables() );
		temp.addSubBox(  getSubBoxes() );
		temp.setExpanded( isExpanded() );
		
		return temp;
	}
	
	
	//Return the expanded state of the box
	public boolean isExpanded(){
		return expanded;
	}
	
	public boolean checkExpanded(){
		return subBoxes.size() > 0;
	}
	

	
	//Remove a sub box from this box
	public void removeSubBox( StatementBox b){
		
		subBoxes.remove( b);
		setExpanded( checkExpanded() );
	}
	
	//String representation of StatementBox object
    public String toString()
    {
    	String s = "Statement Box - " + getStringID() + 
    				"\n--------------------------------" +
    				"\n\n" + statement.toString() +
    				"\n---------------------------------";
    	
    	if( checkExpanded() )
    	{
    		for( int i = 0; i < subBoxes.size() ; i++ )
    		{
    			s = s + "\n\t" + subBoxes.get(i).toString();
    		}
    	}
    	
    	return s;
    }
    
    //compares the ID's and returns true if id's are the same
    public boolean equals( StatementBox b)
    {
    	boolean equal = true;
    	
    	if( getID().length != b.getID().length )
    	{
    		equal = false;
    	}
    	else
    	{
    		for( int i = 0; equal && i < getID().length; i++)
    		{
    			equal = getID()[i] == b.getID()[i];
    		}
    	}
    	
    	return equal;
    }
    
    //Compares an object with this box. Object should be StatementBox otherwise exceptions will be trown
    public int compareTo( Object o)
    {
    	StatementBox b = (StatementBox ) o;
    	
    	if( equals( b)) 
    	{
    		return 0;
    	}
    	else if( getID().length >= b.getID().length )
    	{
    		for( int i = 0; i < b.getID().length ; i++)
    		{
    			if( getID()[i] > b.getID()[i])
    			{
    				return 1;
    			}
    			else if( getID()[i] < b.getID()[i] )
    			{
    				return -1;
    			}
    		}
    		
    		//b.getID() contains all first elements in getID(), so this box is a subbox of b, therefore this box comes later.
    		return 1;
    	}
    	else
    	{
    		for( int i = 0; i < getID().length ; i++)
    		{
    			if( getID()[i] > b.getID()[i])
    			{
    				return 1;
    			}
    			else if( getID()[i] < b.getID()[i] )
    			{
    				return -1;
    			}
    		}
    		
    		//getID() contains all first elements in b.getID(), so b is a this box of b, therefore this box comes earlier.
    		return -1;
    	}
    }
    
    public void changeWord( String oldWord, String newWord )
    {
    	Scanner iterator = (Scanner ) iterator();
    	StringBuffer temp = new StringBuffer();
    	
    	while( iterator.hasNext() )
    	{
    		String word = (String ) iterator.next();
    		
    		if( word.equals( oldWord ) )
    		{
    			temp.append( " " + newWord );
    		}
    		else
    		{
    			temp.append( " " + word );
    		}
    				
    	}
    	
    	subBoxes.changeWord(oldWord, newWord);
    	statement = temp;
    	pcs.firePropertyChange( "change", null, this);
    }
    
    
	
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Implement iterable
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Return a Scanner object with a parameter of "statement".
	public Iterator iterator(){
		return new Scanner( statement.toString() );
	}
 
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
}