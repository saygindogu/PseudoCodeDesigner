package pcd.step;

import pcd.variable.VariableList;

public class DecisionStep extends Step implements ConditionalStep {

	//Properties
	String conditionText;
	boolean conditionValue;
	
	//Constructors
	public DecisionStep(){
		super();
		
		conditionText = "";
		conditionValue = false;
	}
	
	public DecisionStep( byte[] id){
		super( id);
		
		conditionText = "";
		conditionValue = false;
	}
	
	public DecisionStep( String statement, String condition){
		super( statement);
		
		conditionText = condition;
		conditionValue = false;
	}
	
	public DecisionStep( String statement, String conditionText, boolean conditionValue){
		super( statement);
		
		this.conditionText = conditionText;
		this.conditionValue = conditionValue;
	}
	
	//Methods
	@Override
	public DecisionStep copy(){
		DecisionStep newStep = new DecisionStep();
		newStep.setStatement( this.statement );
		
		VariableList newVariableList = new VariableList();
		newVariableList.addVariable(variables);
		
		newStep.setID( new StepID( stepID.toArray() ) );
		newStep.setVariables( newVariableList);
		newStep.setSelected( selected);
		newStep.setIsCurrentStep( isCurrentStep);
		
		newStep.setConditionText( conditionText);
		newStep.setConditionValue( conditionValue );
		
		return newStep;
	}

	@Override
	public String getConditionText() {
		return conditionText;
	}

	@Override
	public void setConditionText( String text) {
		conditionText = text;
		
	}

	@Override
	public boolean getConditionValue() {
		return conditionValue;
	}

	@Override
	public void setConditionValue(boolean b) {
		conditionValue = b;
		
	}

}
