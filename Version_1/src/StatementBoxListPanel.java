/**
 * @(#)StatementBoxListPanel.java
 *
 *
 * @author R2D2 - Z. Saygýn Doðu
 * @version 1.00 2013/4/28
 */
import javax.swing.*;
import java.awt.*;
import java.beans.*;
import java.util.*;
import java.io.Serializable;

public class StatementBoxListPanel extends JPanel implements PropertyChangeListener,Serializable {
	
	private StatementBoxList list;
	private JPanel panel;
	private Color c;
	
		
    public StatementBoxListPanel( StatementBoxList list) {
    	super();
    	
    	panel = new JPanel();   	
    	
    	panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS ));
    	
    	c =  new Color( 208, 208, 209);
    	setBackground(c );
    	//setBackground( Color.WHITE );
    	
    	list.addPropertyChangeListener( this);
    	this.list = list;
    	
    	addSteps();
    	
    	
    	add( panel);
    }
    
   
    
    private void addSteps()
    {
    	for( int i = 0; i < list.size(); i++)
    	{
    		BoxPanel step;
    		
    		if( list.get(i) instanceof ConditionalBox )
    		{
    			if( list.get(i) instanceof DecisionBox )
	    		{
	    			step = new DecisionBoxPanel( (DecisionBox) list.get(i) );
	    		}
	    		else
	    		{
   					step = new RepetitionBoxPanel( (RepetitionBox) list.get(i));
	    		}
    		}
    		else
    		{
    				step = new StatementBoxPanel( list.get(i) );
    		}

    		
			panel.add( step );
			
			if( i != list.size() - 1)
			{
				JPanel rigid = new JPanel();
				rigid.setPreferredSize( new Dimension( 0, 10) );
				rigid.setBackground( c);
	
				panel.add( rigid );
			}
    	}
    }
    
    
    
    ///////////////////////////////////////////////////////////////
    //////// IMPLEMENT PROPERTY CHANGE LISTENER //////////////////
    ///////////////////////////////////////////////////////////////
    
    public void propertyChange( PropertyChangeEvent e)
    {
    	list = (StatementBoxList) (e.getSource());
    	
    	panel.removeAll();
    	
    	panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS ));
    	
    	addSteps();
    	
    	add( panel);
    	
    	repaint();
    	revalidate();
    }
    
    
}