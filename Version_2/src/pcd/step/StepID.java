/** StepID.java
 * 
 * This class holds the identification number of a Step in Pseudo Code Designer
 * 
 * @author Saygın Doğu
 * @author Kerim Bartu Kömürcü
 */

package pcd.step;

import java.io.Serializable;

import pcd.exception.TooMuchStepException;

public class StepID implements Comparable<StepID>, Serializable{
	
	public static final byte IF_TRUE_PART_ID = -1;
	public static final byte IF_FALSE_PART_ID = -2;
	
	// PROPERTIES
	private byte[] stepID;
	
	// CONSTRUCTORS
	/**
	 * Default constructor
	 * 
	 * Initializes all properties to their default value
	 */
	public StepID()
	{
		stepID = new byte[1];
		stepID[0] = 1;// default step id
	}
	
	/**
	 * copy constructor
	 * 
	 * @param stepID - the stepID object to be copied.
	 */
	public StepID( StepID stepID)
	{
		createID( stepID.toArray() );
	}
	
	/**
	 * 
	 * @param idList - the array of identification numbers of an existing project steps
	 */
	public StepID(byte[] idList)
	{
		createID(idList);
	}
	
	// METHODS
	
	/**
	 * 
	 * @return - array representation of this StepID object.
	 */
	public byte[] toArray(){
		return stepID;
	}
	
	/**
	 * @param id - the identification number of a step
	 * 
	 */
	public void extendID( byte id)
	{
		byte[] temp = stepID;
		stepID = new byte[ stepID.length + 1];
		
		for( int i = 0; i < temp.length; i++)
		{
			stepID[i] = temp[i];
		}
		
		stepID[ stepID.length - 1] = id;
	}
	
	private void createID( byte[] idList)
	{
		stepID = new byte[ idList.length];
		for( int i = 0; i < idList.length; i++)
		{
			stepID[i] = idList[i];
		}
	}
	
	/**
	 * @param idList - the identification number of a step
	 *
	 */
	public void collapseID( byte id)
	{
		byte[] temp = stepID;
		stepID = new byte[ stepID.length - 1 ];
		
		for( int i = 0; i < stepID.length; i--)
		{
			stepID[i] = temp[i];
		}
	}
	
	/**
	 * 
	 * @return the element count int array
	 */
	public int size(){
		return stepID.length;
	}
	

	/**
	 * 
	 * @return the previous id number of this step, 
	 * 	if this is a first step of an extended step,
	 * returns the id of the upper step
	 */
	public StepID getPreviousID()
	{
		int previousIDLength = 0;
		byte[] previousID;
		
		for( int i = stepID.length - 1; previousIDLength == 0 && i >= 0 ; i-- )
		{
			if( stepID[i] > 1 || stepID[i] < -2)
			{
				previousIDLength = i + 1;
			}
		}
		
		if( previousIDLength != 0 )
		{	
			previousID = new byte[ previousIDLength];
		}
		else //if there is no step to go previous...
		{
			return new StepID();
		}
		
		for( int i = 0; i < previousID.length - 1; i++)
		{
			previousID[i] = stepID[i];
		}
		previousID[ previousID.length - 1] = (byte)(stepID[ previousID.length - 1] - 1);
		
		return new StepID( previousID);
	}
	
	/**
	 * 
	 * @return String representation of the identification array
	 */
	public String toString(){
		
		String temp = "";
		
		for( int i = 0; i < stepID.length - 1; i++)
		{
			temp = temp + getByteString( stepID[i] ) + ".";
		}
		
		temp = temp + getByteString( stepID[ stepID.length - 1] );
		
		return temp;
	}
	
	/**
	 * 
	 * @param b - byte value
	 * @return String representation ( to be shown in StringID ) of the byte value
	 */
	private String getByteString( byte b)
	{
		if( b >= 0)
		{
			return "" + b;
		}
		else if( b < -2)
		{
			return "" + (256 + b );
		}
		else if( b == IF_TRUE_PART_ID )
		{
			return "T";
		}
		else if( b == IF_FALSE_PART_ID )
		{
			return "F";
		}
		else
			return "";
	}
	
	/**
	 * 
	 * @return the next step's id number
	 */
	public StepID getNextID() throws TooMuchStepException
	{
		byte[] nextID =  new byte[ stepID.length ];
		
		for( int i = 0; i < stepID.length - 2; i++)
		{
			nextID[i] = stepID[i];
		}
		
		if ( stepID[ stepID.length - 1 ] > 0 || stepID[ stepID.length - 1] < -3 )
		{
			nextID[ stepID.length - 1] = (byte) ( stepID[ stepID.length - 1 ] + 1 );
		}
		else if ( stepID[ stepID.length - 1 ] >= -3)
		{
			System.out.println("You are trying to add too much steps!");
			throw new TooMuchStepException( "Can't have 253 steps in a row!");
		}
		
		return new StepID( nextID);
		
		
		
	}
	
	/**
	 * @param o - object to be compared ( Supposed to be instance of Step)
	 * @return the equality state of these objects.
	 */
	public boolean equals( StepID id)
	{
		boolean equal;
		
			byte[] other = id.toArray();
			
			if( other.length != stepID.length )
			{
				equal = false;
			}
			else
			{
				equal = true;
				for( int i = 0; i < stepID.length; i++ )
				{
					if( other[i] != stepID[i] )
					{
						equal = false;
						break;
					}
				}
			}
		
		return equal;
	}

	/**
	 * @param o - the object ( supposed to be an instance of StepID) to be compared with this
	 * 
	 * @return 	- 0 if the id's are same ( this is equal to o)
	 * 			- 1 if this step comes after the step o, ( this is greater than o)
	 * 			- (-1) if step o, comes after this step ( this is less than o)
	 */
	@Override
	public int compareTo(StepID id) {
		
		if( equals( id) )
		{
			return 0;
		}
		else
		{
			StepID other = id;
			
			if( stepID.length >= other.stepID.length )
			{
				for( int i = 0; i < other.stepID.length ; i++)
	    		{
	    			if( stepID[i] > other.stepID[i])
	    			{
	    				return 1;
	    			}
	    			else if( stepID[i] < other.stepID[i] )
	    			{
	    				return -1;
	    			}
	    		}
	    		
	    		//other.stepID contains all first elements in getID(), 
				//so this step is a sub step of other, therefore this step comes later.
	    		return 1;
			}
			else
			{
				for( int i = 0; i < stepID.length ; i++)
	    		{
	    			if( stepID[i] > other.stepID[i])
	    			{
	    				return 1;
	    			}
	    			else if( stepID[i] < other.stepID[i] )
	    			{
	    				return -1;
	    			}
	    		}
	    		
	    		//stepID contains all first elements in other.stepID,
				//so other is a sub step of this step, therefore this step comes earlier.
	    		return -1;
			}
		}
	}
}
