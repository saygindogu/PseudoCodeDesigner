import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 * DecisionBoxPanel - Panel for DecisionBox class.
 * @author G1B R2D2 - Can Mergenci
 * @version 1.00
 */
 import java.beans.*;
 
public class DecisionBoxPanel extends BoxPanel implements PropertyChangeListener {
	
	// PROPERTIES
	private DecisionBox box;
	private JLabel ifLabel, elseLabel, numberLabel;
	private JTextField condition;
	private HTMLFilter filter;
	private JPanel conditionPanel, elsePanel, buttonPanel, mainPanel, swapPanel, ifPanel, elseContainer, elseLabelPanel;
	private JButton remove, removeElse, addNew, swapUp, swapDown;
	private StatementBoxListPanel subBoxes;
	private MouseListener mouseListener;
	private ImageIcon swapUpIcon, swapDownIcon, removeIcon, addIcon, expandIcon1, expandIcon2, editIcon1, editIcon2;
	
	// CONSTRUCTORS
	public DecisionBoxPanel( DecisionBox box )
	{
		super();
		this.box = box;
		
		ifPanel = new StatementBoxListPanel( box.getIfPart() );
		
		setBorder( BorderFactory.createLineBorder( Color.BLACK, 5) );
		
		// Labels
		ifLabel = new JLabel( "if" );
		elseLabel = new JLabel( "else" );
		numberLabel = new JLabel( "" + box.getID()[ box.getID().length - 1] );
    	
    	// TextField
    	condition = new JTextField(15);
    	condition.setText( box.getCondition() );
    	
    	
    	// Panels

    	
    	buttonPanel = new JPanel();
    	mainPanel = new JPanel();
    	swapPanel = new JPanel();
		conditionPanel = new JPanel();
		
		
    	// Layout manager
    	swapPanel.setLayout( new BoxLayout( swapPanel, BoxLayout.Y_AXIS ) );
    	mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.Y_AXIS ) );
    	
    	
    	//Initilise icons
    	swapDownIcon = new ImageIcon( "down-arrow.png");
    	swapUpIcon = new ImageIcon( "up_arrow.png");
    	removeIcon =  new ImageIcon( "closebutton.png" );
    	addIcon =  new ImageIcon( "add.png" );
    	expandIcon1 =  new ImageIcon( "expand1.png" );
    	expandIcon2 =  new ImageIcon( "expand2.png" );
    	editIcon2 =  new ImageIcon( "done.png" );
    	editIcon1 =  new ImageIcon( "edit.png" );
    	
    	
    	//Initilise buttons
    	//remove = new JButton( "X");
    	remove = new JButton( removeIcon );
    	removeElse = new JButton( removeIcon );
    	//addNew = new JButton( "+");
    	addNew = new JButton( addIcon );
    	swapDown = new JButton( swapDownIcon );
    	swapUp = new JButton( swapUpIcon );
    	
    	
    	// Add components to buttonPanel.
    	buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.Y_AXIS ) );
    	buttonPanel.add( remove );
    	buttonPanel.add( addNew );
    	buttonPanel.setVisible( false);
    	
    	

    	
    	// Add components to swapPanel.
    	swapPanel.add( swapUp );
    	swapPanel.add( swapDown );
    	
    	
    	// Add components to conditionPanel.
    	elseLabelPanel = new JPanel();
    	elseLabel.setLayout( new BorderLayout());
    	
    	elseLabelPanel.add( BorderLayout.WEST ,elseLabel);
    	elseLabelPanel.add( BorderLayout.EAST, removeElse);
    	
    	
    	conditionPanel.add( ifLabel );
    	conditionPanel.add( condition );
    	
    
    	
    	// Add components to mainPanel.
    	mainPanel.add( conditionPanel );
    	mainPanel.add( ifPanel);
    	
    	if( box.hasElsePart() )
    	{	
    		elseContainer = new JPanel();
    		elseContainer.setLayout( new BoxLayout( elseContainer, BoxLayout.Y_AXIS ) );
    		elseContainer.add( elseLabelPanel);
			elsePanel = new StatementBoxListPanel( box.getElsePart() );
    		elseContainer.add( elsePanel);
    		mainPanel.add( elseContainer);
    	}
    	
    	
    	// Add components to this panel.
		add( swapPanel );
		add( numberLabel );
		add( mainPanel );
    	add( buttonPanel );
    	
    	
    	
    	
    	setColor( new Color( 221, 149, 144));
    	
    	// Add ActionListeners.
    	remove.addActionListener( new RemoveButtonListener());
    	addNew.addActionListener( new AddNewBoxButtonListener());
    	swapDown.addActionListener( new SwapDownButtonListener());
    	swapUp.addActionListener( new SwapUpButtonListener());
    	removeElse.addActionListener( new RemoveElseActionListener() );
    	
    	addFocusListener( new StatementBoxFocusListener() );
    	addMouseListener( new StatementBoxMouseListener() );
    	condition.addActionListener( new ConditionListener() );
		condition.addFocusListener( new StatementBoxFocusListener());
		
		box.addPropertyChangeListener( this);
	}
	
	//METHODS
	
	public void propertyChange( PropertyChangeEvent e)
    {
    	box = (DecisionBox) (e.getSource());
    	numberLabel = new JLabel( "" + box.getID()[ box.getID().length - 1] );
    	
    	if( box.isSelected() )
    	{
    		setBorder( BorderFactory.createLineBorder( Color.RED, 5 ));
    		buttonPanel.setVisible( true);
    	}
    	else
    	{
    		setBorder( BorderFactory.createLineBorder( Color.BLACK, 5 ));
    		buttonPanel.setVisible( false);
    	}
    	
    	repaint();
    	revalidate();
    }
	
	public void setColor( Color c)
	{
		setBackground( c);
		conditionPanel.setBackground( c);
		
		buttonPanel.setBackground( c);
		mainPanel.setBackground( c);
		swapPanel.setBackground( c);
		ifPanel.setBackground( c);
		
		if( box.hasElsePart() )
		{
			elsePanel.setBackground( c);
			elseContainer.setBackground( c);
			elseLabelPanel.setBackground( c);
		}
		
		//Change Button's 
    	swapDown.setBackground(c);
    	swapUp.setBackground(c);
    	remove.setBackground(c);
    	addNew.setBackground(c);
    	removeElse.setBackground(c);
    	swapDown.setBorderPainted(false);
    	swapUp.setBorderPainted(false);
    	remove.setBorderPainted(false);
    	addNew.setBorderPainted(false);
    	removeElse.setBorderPainted(false);
	}
	//INNER CLASSES
	
	private class ConditionListener implements ActionListener
	{
		public void actionPerformed( ActionEvent e)
    	{
    		box.setConditionText( condition.getText() );
    	}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private class RemoveButtonListener implements ActionListener
    {
    	public void actionPerformed( ActionEvent e)
    	{
    		box.getUpperList().removeBox( box);
    		
    	}
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  	private class StatementBoxFocusListener implements FocusListener{
	
		public void focusGained( FocusEvent e)
		{
			
			if( e.getSource() instanceof JPanel )
			{
				box.setSelected( true);
			}
			
		}
		public void focusLost( FocusEvent e)
		{
			if( e.getSource() instanceof JPanel )
			{
				box.setSelected( false);	
			}
		
		}
	}
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class AddNewBoxButtonListener implements ActionListener
    {
    	public void actionPerformed( ActionEvent e)
    	{
    		box.getUpperList().addBox( box.addNewBox() );
    		
    		buttonPanel.setVisible( true);
    		revalidate();
    	}
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private class RemoveElseActionListener implements ActionListener
	{
		public void actionPerformed( ActionEvent e)
    	{
    		elseContainer.setVisible( false);
    		box.removeElsePart();
    		revalidate();
    	}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private class SwapUpButtonListener implements ActionListener
	{
		public void actionPerformed( ActionEvent e)
		{
			box.getUpperList().swapUp( box);
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private class SwapDownButtonListener implements ActionListener
	{
		public void actionPerformed( ActionEvent e)
		{
			box.getUpperList().swapDown( box);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
		private class StatementBoxMouseListener extends MouseAdapter
		{
			public void mouseClicked( MouseEvent e)
			{
				grabFocus();
			}
		}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}
