package pcd.step;

import java.awt.Component;
import java.util.Scanner;

import pcd.variable.Variable;
import pcd.variable.VariableList;

public class Method extends Step {

	//Properties
	private VariableList inputs;
	private VariableList outputs;
	
	// Constructors
	public Method( VariableList inputs, VariableList outputs ) {
		super();
		
		this.inputs = inputs;
		this.outputs = outputs;
	}
	
	
	public Method( VariableList inputs, VariableList outputs, String statement ) {
		super( statement);
		
		this.inputs = inputs;
		this.outputs = outputs;
	}
	
	private VariableList[] copyVariables()
	{
		VariableList newInputs = new VariableList();
		VariableList newOutputs = new VariableList();
		for( int i = 0; i < inputs.size(); i++){
			newInputs.add( new Variable( inputs.get(i).getName() ));
		}
		for( int i = 0; i < outputs.size(); i++){
			newOutputs.add( new Variable( outputs.get(i).getName() ));
		}
		
		VariableList[] lists = new VariableList[2];
		lists[0] = newInputs;
		lists[1] = newOutputs;
		
		return lists;
		
		
	}
	
//	public void changeVariableNames( String[] inputs, String[] outputs)
//	{
//		VariableList[] varLists = copyVariables();
//		
//		for (int i = 0; i < inputs.length; i++)
//		{
//			if ( inputs[i]  != null && !inputs[i].equals("") && !inputs[i].contains(" "))
//			{
//				varLists[0].get(i).setName( inputs[i] );
//			}
//			
//			String newStatement = "";
//			Scanner scan = (Scanner ) iterator();
//			
//			while( scan.hasNext()){
//				String word = scan.next();
//				if( word.equals( this.inputs.get(i).getName() )){
//					newStatement = newStatement + varLists[0].get(i).getName() + " ";
//				}
//				else
//				{
//					newStatement = newStatement + word + " ";
//				}
//			}
//			
//			this.statement = newStatement;
//		}
//		
//		for (int i = 0; i < outputs.length; i++)
//		{
//			if ( outputs[i]  != null && !outputs[i].equals("") && !outputs[i].contains(" "))
//			{
//				varLists[0].get(i).setName( outputs[i] );
//			}
//			
//			String newStatement = "";
//			Scanner scan = (Scanner ) iterator();
//			
//			while( scan.hasNext()){
//				String word = scan.next();
//				if( word.equals( this.outputs.get(i).getName() )){
//					newStatement = newStatement + varLists[1].get(i).getName() + " ";
//				}
//				else
//				{
//					newStatement = newStatement + word + " ";
//				}
//			}
//			
//			this.statement = newStatement;
//		}
//	}


	public VariableList getInputs() {
		return inputs;
	}


	public VariableList getOutputs() {
		return outputs;
	}

}
