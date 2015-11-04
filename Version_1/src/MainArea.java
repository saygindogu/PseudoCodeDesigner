/**
 * @(#)MainArea.java
 *
 *
 * @author R2D2 - Z. Saygýn Doðu
 * @version 1.00 2013/4/28
 */
import javax.swing.*;
import java.awt.*;
import java.beans.*;

public class MainArea extends JPanel implements PropertyChangeListener {

	StatementBoxListPanel panel;
	JScrollPane scrollPane;
	Project project;
	
    public MainArea( Project project, int x, int y) {
    	
    	super();
    	
    	setPreferredSize( new Dimension( x, y));
    	this.project = project;
    	project.addPropertyChangeListener( this);
    	
    	this.panel = new StatementBoxListPanel( project.getSteps() );
    	
    	scrollPane = new JScrollPane( panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED  , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	scrollPane.setPreferredSize( new Dimension( x - 15, y - 5));
    	scrollPane.getVerticalScrollBar().setUnitIncrement( 13);
    	
    	add( scrollPane);
    	
    }
    
    public void propertyChange( PropertyChangeEvent e)
    {
    	this.panel = new StatementBoxListPanel( project.getSteps() );
    	revalidate();
    }
    
    
}