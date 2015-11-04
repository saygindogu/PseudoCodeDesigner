import java.io.Serializable;
/**
 * @(#)Finder.java
 *
 *
 * @author  G1B R2D2 - Bengisu Batmaz
 * @version 1.00 2013/4/30
 */
import java.util.Scanner;

public class Finder implements Serializable
{
	
	VariableList	list;
	
    public Finder( VariableList list) {
    	//this.list = createVarList();
    	this.list = list;
    }
    
    public VariableList findVariables(String str){
    	
    	VariableList newList = new VariableList();
    	Scanner scan = new Scanner(str);
    	while( scan.hasNext()){
    		String word = scan.next();
    		for( int i = 0; i < list.size(); i++){
    			if( list.get(i).getName().equalsIgnoreCase(word))
    				newList.addVariable( list.get(i));
    		}
    	}
    	return newList;
    }
    
    public VariableList createVarList()
    {
    	VariableList list = new VariableList();
    	
    	for( int i = 0; i < 10; i++)
    	{
    		Variable var = new Variable( "variable" + i );
    		list.addVariable( var);
    	}
    	return list;
    }
}