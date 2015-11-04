import java.io.*;
/**
 * PCDMethod Class 
 * @author Ali Naci Uysal from group G1B (R2D2)
 * 
 * @version 1.00 16/04/2013
 */
 
public class PCDMethod extends StatementBox implements Comparable, Serializable
{
	// PROPERTIES
	private StringBuffer 		name;
	private VariableList 		inputList, outputList;
	private StatementBoxList	statementBoxList;

	// CONSTRUCTORS
	public PCDMethod()
	{
		outputList = new VariableList();
		inputList = new VariableList();
		statementBoxList = new StatementBoxList();
		name = new StringBuffer( "");
		setStatement( name.toString() );
	}
	
	public PCDMethod( String name)
	{
		setProjectReferance( new Project() );
		outputList = new VariableList();
		inputList = new VariableList();
		statementBoxList = new StatementBoxList();
		this.name = new StringBuffer( name);
		setStatement( name.toString() );
	}
	
	
	// METHODS
	
	// A method that returns name of the method
	public String getName()
	{
		return name.toString();
	}
	
	// A method that sets the methods name with the given name
	public void setName( String methodName)
	{
		name = new StringBuffer( methodName);
		setStatement( name.toString() );
	}
	
	//
	@Override
	public int compareTo( Object object) 
	{
		try
		{
			return name.toString().compareTo( ( ( (PCDMethod) object).getName() ) );
		}
		catch( ClassCastException c )
		{
			return super.compareTo( object);
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}
		return 0;
		
	}		
}

