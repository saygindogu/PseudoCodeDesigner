package pcd.step;

public interface ConditionalStep {

	public String getConditionText();
	public void setConditionText( String text);
	public boolean getConditionValue();
	public void setConditionValue( boolean b);
	
}
