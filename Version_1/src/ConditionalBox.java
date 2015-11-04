/**
 * ConditionalBox - Interface for condition parts of decision and repetition statements. 
 * @author Can Mergenci
 * @version 1.00 20/04/2013
 */
 import java.util.ArrayList;
public interface ConditionalBox {
	
	public boolean isConditionTrue();
	public void setCondition( boolean condition );
	public ArrayList<StatementBox> getAllBoxes();
	public String getCondition();
}
