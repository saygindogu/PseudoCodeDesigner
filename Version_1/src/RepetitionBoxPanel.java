import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.beans.*;

/**
 * RepetitionBoxPanel - Panel for RepetitionBox class.
 * @author G1B R2D2 - Can Mergenci
 * @version 1.00
 */
class RepetitionBoxPanel extends BoxPanel implements PropertyChangeListener {
	
	// PROPERTIES
	private RepetitionBox box;
	private JLabel whileLabel, numberLabel;
	private JTextField condition;
	private HTMLFilter filter;
	private JPanel whilePanel, buttonPanel, mainPanel, swapPanel;
	private JButton remove, addNew, swapUp, swapDown;
	private StatementBoxListPanel subBoxes;
	private MouseListener mouseListener;
	private ImageIcon swapUpIcon, swapDownIcon, removeIcon, addIcon, expandIcon1, expandIcon2, editIcon1, editIcon2;
	
	// CONSTRUCTOR
	public RepetitionBoxPanel( RepetitionBox box )
	{
		super();
		this.box = box;
		
		if( !box.checkExpanded() )
		{
			box.addSubBox();
		}
		
		subBoxes = new StatementBoxListPanel( box.getSubBoxes() );
		
		//Set the border
    	setBorder( BorderFactory.createLineBorder( Color.BLACK, 5) );
		
		// Labels
		whileLabel = new JLabel( "while" );
		numberLabel = new JLabel( "" + box.getID()[ box.getID().length - 1] );
    	
    	// TextField
    	condition = new JTextField(15);
    	condition.setText( box.getCondition() );
    	
    	// Panels
    	whilePanel = new JPanel();
    	buttonPanel = new JPanel();
    	mainPanel = new JPanel();
    	swapPanel = new JPanel();
    	
    	// Layout manages
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
    	//addNew = new JButton( "+");
    	addNew = new JButton( addIcon );
    	swapDown = new JButton( swapDownIcon );
    	swapUp = new JButton( swapUpIcon );
    	
    	
    	// Add components to buttonPanel.
    	buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.Y_AXIS ) );
    	buttonPanel.add( remove );
    	buttonPanel.add( addNew );
    	buttonPanel.setVisible( false);
    	
    	
    	
    	
    	// Add components to whilePanel.
    	
    	whilePanel.add( numberLabel );
    	whilePanel.add( whileLabel );
    	whilePanel.add( condition );
    	
    	
    	// Add components to swapPanel.
    	swapPanel.add( swapUp );
    	swapPanel.add( swapDown );
    	
    	// Add components to mainPanel.
    	mainPanel.add( whilePanel );
    	mainPanel.add( subBoxes );
    	//subBoxes.addPropertyChangeListener( this);

    	
    	// Add components to this panel.
    	add( swapPanel );
    	add( mainPanel );
    	add( buttonPanel );
    	
    	//Set Background Color
    	setColor( new Color( 226, 201, 149) );
    	
    	this.box.addPropertyChangeListener( this);
    	    	
    	// Add ActionListeners.
    	remove.addActionListener( new RemoveButtonListener());
    	addNew.addActionListener( new AddNewBoxButtonListener());
    	swapDown.addActionListener( new SwapDownButtonListener());
    	swapUp.addActionListener( new SwapUpButtonListener());
    	
    	addFocusListener( new StatementBoxFocusListener() );
    	addMouseListener( new StatementBoxMouseListener() );
    	condition.addActionListener( new ConditionListener() );
		condition.addFocusListener( new StatementBoxFocusListener());
	}
	
	//METHODS
	public void propertyChange( PropertyChangeEvent e)
    {
    	box = (RepetitionBox) (e.getSource());
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
		whilePanel.setBackground( c);
		buttonPanel.setBackground( c);
		mainPanel.setBackground( c);
		swapPanel.setBackground( c);
		
		//Change Button's 
    	swapDown.setBackground(c);
    	swapUp.setBackground(c);
    	remove.setBackground(c);
    	addNew.setBackground(c);
    	swapDown.setBorderPainted(false);
    	swapUp.setBorderPainted(false);
    	remove.setBorderPainted(false);
    	addNew.setBorderPainted(false);
		
	}

	//SUBCLASSES
	
	private class ConditionListener implements ActionListener
	{
		public void actionPerformed( ActionEvent e)
    	{
    		box.setConditionText( condition.getText() );
    	}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private class RemoveButtonListener implements ActionListener
    {
    	public void actionPerformed( ActionEvent e)
    	{
    		box.getUpperList().removeBox( box);
    		
    	}
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	private class StatementBoxMouseListener extends MouseAdapter
		{
			public void mouseClicked( MouseEvent e)
			{
//				if( buttonPanel.isVisible() )
//				{
//					buttonPanel.setVisible( false) ;
//				}
//				else
//				{
//					buttonPanel.setVisible( true) ;
//				}
				grabFocus();
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
	

}
