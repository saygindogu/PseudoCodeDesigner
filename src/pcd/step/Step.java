/** Step.java
 * 
 * This class holds the information for the basic algorithm steps in a Pseudo Code Designer project.
 * 
 * @author Zahit Saygın Doğu
 * @author Kerim Bartu Kömürcü
 */
package pcd.step;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import pcd.variable.Variable;
import pcd.variable.VariableList;

public class Step implements Iterable<String>, Comparable<Step>, Serializable {
	
	//PROPERTIES
	
	public static final byte 		DEFAULT_FONT_SIZE = 12;

	public static final String GENERAL = "General Step";
	public static final String INPUT = "Input Step";
	public static final String OUTPUT = "Output Step";
	public static final String ASSIGNMENT = "Assignment Step";
	public static final String CONDITIONAL = "Conditional Step";
	
	protected String				statement;
	protected String				type;
	protected boolean 				selected;
	protected VariableList			variables;
	protected boolean				isCurrentStep;
	protected StepID				stepID;
	protected PropertyChangeSupport	porpertyChangeSupport;

	
	
	//CONSTRUCTORS
	/**
	 * Default constructor
	 * 
	 * Initializes all properties to their default value
	 */
	public Step(){
		
		statement = "";
		type = GENERAL;
		selected = false;
		variables = new VariableList();
		isCurrentStep = false;
		
		stepID = new StepID();
		
		porpertyChangeSupport = new PropertyChangeSupport( this);
	}
	
	/**
	 * 
	 * @param id - the identification number array of the step
	 */
	public Step( byte[] id){
		
		statement = "";
		type = GENERAL;
		variables = new VariableList();
		isCurrentStep = false;
		
		stepID = new StepID(id);
		
		porpertyChangeSupport = new PropertyChangeSupport( this);
	}
	
	/**
	 * 
	 * @param statement - the information in the algorithm step
	 */
	public Step( String statement ){
		
		this.statement = statement;
		type = GENERAL;
		variables = new VariableList();
		isCurrentStep = false;
		
		stepID = new StepID();
		
		porpertyChangeSupport = new PropertyChangeSupport( this);
	}
	
	public String getName(){
		String name;
		Scanner scan = new Scanner( statement);
		if( scan.hasNext() )
		{
			name = scan.next();
		}
		else{
			name = "Default";
		}
		scan.close();
		return name;
	}
	
	/**
	 * 
	 * @param id - the identification number array of the step
	 * @param statement - the information in the algorithm step
	 */
	public Step( byte[] id, String statement ){
		
		this.statement = statement;
		type = GENERAL;
		variables = new VariableList();
		isCurrentStep = false;
		
		stepID = new StepID(id);
		
		porpertyChangeSupport = new PropertyChangeSupport( this);
	}
	
	public Step( String statement, String type ){
		this.statement = statement;
		this.type = type;
		variables = new VariableList();
		isCurrentStep = false;
		
		stepID = new StepID();
		
		porpertyChangeSupport = new PropertyChangeSupport( this);
	}
	
	//METHODS
	public Step copy(){
		Step newStep = new Step( statement);
		VariableList newVariableList = new VariableList();
		newVariableList.addVariable(variables);
		
		newStep.setID( new StepID( stepID.toArray() ) );
		newStep.setVariables( newVariableList);
		newStep.setSelected( selected);
		newStep.setIsCurrentStep( isCurrentStep);
		newStep.setType( type );
		
		return newStep;
	}
	
	public void setType( String newType){
		type = newType;
	}
	
	public void setStatement(String statement) {
		this.statement = statement;
		
	}
	
	public String getType(){
		return type;
	}
	
	public void setIsCurrentStep(boolean isCurrentStep) {
		this.isCurrentStep = isCurrentStep;
		
	}

	public void setVariables(VariableList variables) {
		this.variables = variables;
		
	}

	/**
	 * 
	 * @param newID
	 */
	public void setID( StepID newID){
		stepID = new StepID( newID) ;
	}
	
	/**
	 * creates HTML representation of statement with default font size
	 * 
	 * @return plain HTML representation of the information in this Step
	 */
	public String getHTMLStatement(){
		
		return getHTMLStatement( DEFAULT_FONT_SIZE);
	}
	
	/**
	 * 
	 * @param fontSize
	 * @return plain HTML representation of the information in this Step
	 */
	public String getHTMLStatement( int fontSize){

		Scanner scan = new Scanner( statement.toString() );
		if( System.getProperty("LineSeperator") != null)
			scan.useDelimiter( System.getProperty("LineSeperator"));
		else
			scan.useDelimiter( "\n");
		String statementString = "";
		
		while( scan.hasNext() )
		{
			statementString = statementString + scan.next()  + "<br>";
		}
		
		scan.close();
		
		return "<HTML><head><title>" + stepID.toString()
				
				+ "</title></head><body style=\" font-size: " + fontSize 
				+ "px;\"><center><p>" + statementString
				+ "</p></center></body></HTML>";
	}
	
	/**
	 * 
	 * @return HTML representation of the information in this Step with variables highlighted
	 */
	public String getHTMLVariablesHighlighted(){
		
		return getHTMLVariablesHighlighted( DEFAULT_FONT_SIZE );
	}
	
	/**
	 * 
	 * @param fontSize
	 * @return HTML representation of the information in this Step with variables highlighted
	 */
	public String getHTMLVariablesHighlighted( byte fontSize ){
		//TODO çok satırlı hale getirmenin bir yolunu bulmak lazım..
		
		StringBuffer temp = new StringBuffer( "<HTML><head><title>" + stepID.toString() 
												+ "</title></head><body style=\" font-size: ");
		temp.append( "" + fontSize);
		temp.append( "px;\"><p>" );
		
		//////////////////////////////////////////////////////////////////////////
		Scanner scan = new Scanner( statement );
		
		if( System.getProperty("LineSeperator") != null)
			scan.useDelimiter( System.getProperty("LineSeperator"));
		else
			scan.useDelimiter( "\n");
		String statementString = "";
		
		while( scan.hasNext() )
		{
			statementString = statementString + scan.next()  + " <br> ";
		}
		
		scan.close();
		
	
		///////////////////////////////////////////////////////////////////////////////////	
		Scanner iterator = new Scanner( statementString);
		
		while( iterator.hasNext())
		{
			String word = iterator.next();
		
			if( isVariable( word ) )
			{
				temp.append( getVariable( word).toHTMLString( fontSize) + " ");
			}
			else
			{
				temp.append( word + " " );
			}
		}
		temp.append( "</p></body></HTML>");
		return temp.toString();
		
	}
	
	
	/**
	 * 
	 * @param word - the key word to be checked if it is a variable
	 * @return the state of the word being a variable or not
	 */
	public boolean isVariable( String word){
		if (variables.findVariable(word) != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param word
	 * @return the variable with the name word
	 */
	public Variable getVariable( String word){
			return variables.findVariable(word);
	}

	@Override
	public Iterator<String> iterator() {
		return new Scanner( statement);
	}
	
	/**
	 * 
	 * @param o - object to be compared ( Supposed to be instance of Step)
	 * 
	 * @return - 0 if id's of this step and o are same.
	 * 			- 1 if this step comes after the step o
	 * 			- (-1) if this step comes before the step o
	 */
	
	@Override
	public int compareTo(Step s) {
		return stepID.compareTo( s.stepID );
	}
	
	/**
	 * @param o - object to be compared ( Supposed to be instance of Step)
	 * @return the equality state of these objects.
	 */
	public boolean equals( Object o){
		return stepID.equals( o);
	}

	public StepID getID() {
		return stepID;
	}

	public void addVariable(Variable variable) {
		variables.add( variable);
		
	}

	public void removeVariable(Variable variable) {
		variables.remove( variable);
		
	}

	public void changeText(String oldValue, String newValue) {
		
		//TODO satır satır ayır
		//satırdaki kelimeleri değiştir
		//satır satır birleştir
		//methoddakini de bunu kullan
		Scanner lineSeperator = new Scanner( statement );
		Scanner scan;
		ArrayList<String> lines = new ArrayList<String>();
		String newStatement = "";
		
		//Set the proper delimiter
		if( System.getProperty("LineSeperator") != null)
			lineSeperator.useDelimiter( System.getProperty("LineSeperator"));
		else
			lineSeperator.useDelimiter( "\n");
		
		//Get the lines seperately
		while( lineSeperator.hasNext() ){
			lines.add( lineSeperator.next() );
		}
		//TODO son satiri almiyorsa düsün..
		
		//Process all lines seperately and combine them properly
		for( int i = 0; i < lines.size(); i++){
			scan = new Scanner( lines.get(i));
			
			while( scan.hasNext() ){
				String word = scan.next();
				
				if( word.equals( oldValue )){
					newStatement = newStatement + " " + newValue;
				}
				else{
					newStatement = newStatement + " " + word;
				}
			}
			
			newStatement = newStatement + "\n";
		}
		
		statement = newStatement;
		
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean b) {
		selected = b;
	}
}
