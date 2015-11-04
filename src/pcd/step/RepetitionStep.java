package pcd.step;

import pcd.variable.VariableList;

public class RepetitionStep extends Step implements ConditionalStep {

	//Properties
	String conditionText;
	boolean conditionValue;
	int repeatCount;
	
	//Constructors
	public RepetitionStep(){
		super();
		
		conditionText = "";
		conditionValue = false;
		repeatCount = 0;
	}
	
	public RepetitionStep( byte[] id){
		super( id);
		
		conditionText = "";
		conditionValue = false;
		repeatCount = 0;
	}
	
	public RepetitionStep( String statement, String condition){
		super( statement);
		
		conditionText = condition;
		conditionValue = false;
		repeatCount = 0;
	}
	
	public RepetitionStep( String statement, String conditionText, boolean conditionValue){
		super( statement);
		
		this.conditionText = conditionText;
		this.conditionValue = conditionValue;
		repeatCount = 0;
	}
	
	//Methods
	
	@Override
	public RepetitionStep copy(){
		RepetitionStep newStep = new RepetitionStep();
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
