import java.util.ArrayList;

/**
 * DecisionBox - Holds decision statements by inheriting StatementBox class.
 * @author G1B R2D2 - Can Mergenci
 * @version 1.00 19/04/2013
 */
public class DecisionBox extends StatementBox implements ConditionalBox {
	
	// PROPERTIES
	public final int IF  = -2;
	public final int ELSE = -1; 
		
	private boolean condition;
	private boolean hasElse;
	private StringBuffer conditionText;
	private StatementBoxList ifPart;
	private StatementBoxList elsePart;
	
	// CONSTRUCTORS
	
	public DecisionBox( int[] id)
	{
		super( id);
		ifPart = new StatementBoxList(super.getSubBoxes().getDepth() + 1);
		elsePart = new StatementBoxList( super.getSubBoxes().getDepth() + 1);
		
		
		conditionText = new StringBuffer();
		condition = false;
		
		addElseBox( new StatementBox() );
		addIfBox( new StatementBox() );
		
		hasElse = true;
	}
	public DecisionBox()
	{
		super();
		ifPart = new StatementBoxList(super.getSubBoxes().getDepth() + 1);
		elsePart = new StatementBoxList( super.getSubBoxes().getDepth() + 1);
		
		conditionText = new StringBuffer();
		condition = false;
		
		
		addElseBox( new StatementBox( ) );
		addIfBox( new StatementBox( ) );
		
		hasElse = true;
	}
	
	// METHODS
	@Override
	public void setUpperList( StatementBoxList list)
	{
		super.setUpperList( list);
//		ifPart.setUpperList( list.getUpperList() );
//		elsePart.setUpperList( list.getUpperList() );
		getPropertyChangeSupport().firePropertyChange( "upperList", null, this);
	}
	
//	@Override
//	public void setFinder( Finder f)
//	{
//		super.setFinder( f);
//		ifPart.setFinder( f);
//		elsePart.setFinder( f);
//	}
	
	@Override
	public void setProjectReferance( Project referance )
	{
		super.setProjectReferance( referance );
		ifPart.setProjectReferance( referance );
		elsePart.setProjectReferance( referance );
	}
	
	@Override
	public boolean checkExpanded()
	{
		return getSubBoxes().size() > 0;
	}
	
	@Override
	public void setID( int[] id ){
		super.setID( id);
		
		if( checkExpanded() )
		{
			for( int i = 0; i < ifPart.size(); i++)
			{
				int[] newID = new int[ ifPart.get(i).getID().length ];
				
				//Construct The new ID of the sub box
				for( int j = 0; j < ifPart.getDepth() - 3 ; j++)
				{
					newID[ j] = id[j];
				}
				
				newID[ifPart.getDepth() - 3] = id[ id.length - 1 ] ;
				newID[ifPart.getDepth() - 2] = ifPart.get(i).getID()[ ifPart.getDepth() - 2 ] ;
				
				for( int j = ifPart.getDepth() - 1; j < ifPart.get(i).getID().length; j++ )
				{
					newID[ j] = ifPart.get(i).getID()[j];
				}
				////////////////////////////////////////////////////////
				
				ifPart.get(i).setID( newID);
			}
			if ( hasElse )
				for( int i = 0; i < elsePart.size(); i++)
				{
					int[] newID = new int[ elsePart.get(i).getID().length ];
					
					//Construct The new ID of the sub box
					for( int j = 0; j < elsePart.getDepth() - 3 ; j++)
					{
						newID[ j] = id[j];
					}
					
					newID[elsePart.getDepth() - 3] = id[ id.length - 1 ] ;
					newID[elsePart.getDepth() - 2] = elsePart.get(i).getID()[ elsePart.getDepth() - 2 ] ;
					
					for( int j = elsePart.getDepth() - 1; j < elsePart.get(i).getID().length; j++ )
					{
						newID[ j] = elsePart.get(i).getID()[j];
					}
					////////////////////////////////////////////////////////
					
					elsePart.get(i).setID( newID);
				}
		}
		
		super.getPropertyChangeSupport().firePropertyChange( "ID", null, this);
	}
		
	@Override
	public void setStatement( String s)
	{
		if( ifPart.get(0) != null )
			ifPart.get(0).setStatement( s);
	}

		
	@Override
	public void addSubBox( StatementBox box )
	{
		addIfBox(box);
	}
	
	@Override
	public StatementBoxList getSubBoxes()
	{
		StatementBoxList subBoxes = new StatementBoxList( super.getSubBoxes().getDepth() + 1 );
		
		for( int i = 0; i < ifPart.size(); i++)
		{
			subBoxes.addBox( ifPart.get(i) );
		}
		
		if( hasElse )
			for( int i = 0; i < elsePart.size(); i++)
			{
				subBoxes.addBox( elsePart.get( i) );
			}
		
		subBoxes.setProjectReferance( getProjectReferance() );
		
		return subBoxes;
	}
	
	@Override
	public String toString()
    {
    	String s = "Decision Box - " + getStringID() + 
    				"\n--------------------------------" +
    				"\nIF " + conditionText +
    				"\n" + ifPart.toString();
    	if( hasElse)
    	{
    		s = s + "\nELSE" +
    				"\n\n" + elsePart.toString() +
    				"\n---------------------------------";
    	}
    	
    	return s;
    }
    
    
	@Override
	public void removeSubBox( StatementBox box )
	{
		if( hasElse )
			elsePart.removeBox( box );
		else
			ifPart.removeBox( box );
	}

	
	
		
	public ArrayList<StatementBox> getAllBoxes()
	{
		ArrayList<StatementBox> allBoxes = new ArrayList<StatementBox>();
		allBoxes.addAll( ifPart.getAllBoxes() );
		if( hasElse )
		{
			allBoxes.addAll( elsePart.getAllBoxes() );
		}
		
		return allBoxes;
	}
	

	public boolean hasElsePart()
	{
		return hasElse;
	}
	
	public String getCondition()
	{
		return conditionText.toString();
	}
	
	public void removeElsePart()
	{
		elsePart = null;
		hasElse = false;
	}
	

	
	public void addIfBox( StatementBox box )
	{
		box.setID( getIfID( box ) );
		ifPart.addBox( box );
	}
	
	public void addElseBox( StatementBox box )
	{
		box.setID( getElseID( box ) );
		elsePart.addBox( box );
		hasElse = true;
	}
	
	
	private int[] getIfID( StatementBox box )
	{
		int[] ifID = new int[ getID().length + 2 ];
		
		ifID[ getID().length + 1] 			= box.getID()[ box.getID().length - 1 ];
		ifID[ getID().length ] 				= IF;
		
		for( int i = 0; i < getID().length; i++)
		{
			ifID[i] = getID()[ i];
		}
		
		return ifID;
	}
	
	private int[] getElseID( StatementBox box )
	{
		int[] elseID = new int[ getID().length + 2 ];
		
		elseID[ getID().length + 1] 			= box.getID()[ box.getID().length - 1 ];
		elseID[ getID().length ] 				= ELSE;
		
		for( int i = 0; i < getID().length; i++)
		{
			elseID[i] = getID()[ i];
		}
		
		return elseID;
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
	
	public StatementBoxList getIfPart()
	{
		return ifPart;
	}
	
	public StatementBoxList getElsePart()
	{
		return elsePart;
	}

}
