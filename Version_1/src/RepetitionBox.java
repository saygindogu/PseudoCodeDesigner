import java.util.ArrayList;

/**
 * RepetitionBox - Holds repetition statements by inheriting StatementBox class.
 * @author G1B R2D2 - Can Mergenci
 * @version 1.00 20/04/2013
 */
public class RepetitionBox extends StatementBox implements ConditionalBox {
	
	// PROPERTIES
	private StringBuffer conditionText;
	private boolean condition;
	
	// CONSTRUCTORS
	public RepetitionBox( int[] id)
	{
		super( id);
		conditionText = new StringBuffer();
		addSubBox();
		condition = false;
	}
	
	public RepetitionBox()
	{
		super();
		conditionText = new StringBuffer();
		addSubBox();
		condition = false;
	}
	
	// METHODS
//	@Override
//	public void setFinder( Finder f)
//	{
//		super.setFinder( f);
//		getSubBoxes().setFinder( f);
//	}
	
	
	public ArrayList<StatementBox> getAllBoxes()
	{
		ArrayList<StatementBox> allBoxes = new ArrayList<StatementBox>();
		allBoxes.addAll( getSubBoxes().getAllBoxes() );
		
		return allBoxes;
	}
	
	@Override
	public String toString()
    {
    	String s = "Repetition Box - " + getStringID() + 
    				"\n--------------------------------" +
    				"\n\nWHILE" + conditionText +
    				"\n" + getSubBoxes().toString() +
    				"\n---------------------------------";
    	
    	return s;
    }
	
	
	@Override
	public void setStatement( String s)
	{
		if( getSubBoxes().get(0) != null )
			getSubBoxes().get(0).setStatement( s);
	}
	
	
	public boolean isConditionTrue()
	{
		return condition;
	}
	
	public void setCondition( boolean condition )
	{
		this.condition = condition;
	}
	
	public void setConditionText( String conditionText )
	{
		this.conditionText = new StringBuffer( conditionText);
	}
	
	public String getCondition()
	{
		return conditionText.toString();
	}

}
