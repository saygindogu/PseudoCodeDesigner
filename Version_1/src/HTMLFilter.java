import java.io.*;
/**
 * @(#)HTMLFilteredReader.java		Filters HTML tags from a String
 *
 *
 * @author Z. Saygýn Doðu
 * @version 1.00 2013/3/1
 */


public class HTMLFilter implements Serializable
{
	
	
	//Properties
	
	private String content;
	
	//Constructor
    public HTMLFilter( String content)
    {
    	this.content = content;
    }
    
    //Methods
    
    // get the content of the page filtered from HTML tags
    
    public String getPlainContents()
    {
    	String tmp = getOneLine() ;
    	int i = 0;
    	int j;
    	
    	//Find the HTML tags and delete them
    	do
    	{
    		if( tmp.charAt( i) == '<' )
    		{
    			j = i;
    			while( ! (tmp.charAt( j) == '>') )
    			{
    				j++;
    			} //HTML tag is tmp.substring( i , j);
    			tmp = tmp.substring( 0, i) + tmp.substring( j + 1);
    		}
    		else
    		{
    			i++;
    		}
    	}while( i < tmp.length());
    	
    	return tmp;
    }
    
    public String getBodyContents()
    {
    	String tmp = removeHeadTag();;
    	int i = 0;
    	int j;
    	
    	//Remove head tag
    	
    	
    	//Find the HTML tags and delete them
    	do
    	{
    		if( tmp.charAt( i) == '<' )
    		{
    			j = i;
    			while( ! (tmp.charAt( j) == '>') )
    			{
    				j++;
    			} //HTML tag is tmp.substring( i , j);
    			tmp = tmp.substring( 0, i) + tmp.substring( j + 1);
    		}
    		else
    		{
    			i++;
    		}
    	}while( i < tmp.length());
    	
    	return tmp;
    }
    
    private String removeHeadTag()
    {
    	String tmp = getOneLine();
    	
    	int i = 0;
    	int j;
    	
    	//Find the Head tag and remove inside it
    	do
    	{
    		if( tmp.charAt( i) == '<' && tmp.substring( i, i + 6).equalsIgnoreCase( "<head>" ) )
    		{
    			j = i + 6;
    			while( ! (tmp.charAt( j) == '<' && tmp.substring( j, j + 7 ).equalsIgnoreCase( "</head>")) )
    			{
    				j++;
    			} //Head tag is tmp.substring( i , j);
    			tmp = tmp.substring( 0, i) + tmp.substring( j + 7);
    			return tmp;
    		}
    		else
    		{
    			i++;
    		}
    	}while( i < tmp.length());
    	
    	return tmp;
    }
    
    private String getOneLine()
    {
    	String tmp = content.trim();
    	int i = 0;
    	
    	//Find the new line characters and remove them
    	do
    	{
    		if( tmp.charAt( i) == '\\' && tmp.charAt( i + 1) == 'n' )
    		{
    			tmp = tmp.substring( 0, i) + tmp.substring( i + 2);
    		}
    		else
    		{
    			i++;
    		}
    	}while( i < tmp.length());
    	
    	return tmp;
    }
    
    
    
}