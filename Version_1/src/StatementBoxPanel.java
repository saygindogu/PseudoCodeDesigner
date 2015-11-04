/**
 * @(#)StatementBoxPanel.java
 *
 *
 * @author R2D2 - Z. Saygýn Doðu
 * @version 1.00 2013/4/19
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.border.*;
import java.util.ArrayList;
import java.io.*;

public class StatementBoxPanel extends BoxPanel implements PropertyChangeListener, Serializable 
{
	private StatementBox box;
	private JEditorPane editorPane;
	private JLabel numberLabel;
	private JButton remove, addNew, expand, edit, swapUp, swapDown;
	private JPanel buttonPanel, mainBoxPanel, swapButtonPanel;
	private HTMLFilter filter;
	private StatementBoxListPanel subBoxes;
	private MouseListener mouseListener;
	private ImageIcon swapUpIcon, swapDownIcon, removeIcon, addIcon, expandIcon1, expandIcon2, editIcon1, editIcon2;
	
    public StatementBoxPanel( StatementBox statementBox ) {
    	super();
    	
    	//Initilise box
    	box = statementBox;
    	
    	if ( box.isSelected() )
    	{
    		grabFocus();
    	}
    	
    	addFocusListener( new StatementBoxFocusListener() );
    	
    	//add PropertyChangeListener
    	box.addPropertyChangeListener( this);
    	
    	
    	//Set the border
    	setBorder( BorderFactory.createLineBorder( Color.BLACK, 5) );
    	
    	//Initilise mouse listener
    	mouseListener = new StatementBoxMouseListener();
    	addMouseListener( mouseListener );
    	
    	//initilise sub boxes
    	subBoxes = new StatementBoxListPanel( box.getSubBoxes() );
    	
    	//Initilize Panels
    	buttonPanel = new JPanel();
    	mainBoxPanel = new JPanel();
    	swapButtonPanel = new JPanel();
    	
    	//Set Layout settings
    	setLayout( new BoxLayout( this, BoxLayout.Y_AXIS));
    	buttonPanel.setVisible( false);
    	swapButtonPanel.setLayout( new BoxLayout( swapButtonPanel, BoxLayout.Y_AXIS));
    	
    	
    	
    	//Initilise EditorPane
    	if( box.getStatement().length() > 0)
    	{
    		editorPane = new JEditorPane( "text/html" , box.getHTMLVariablesHighlighted() );
    	}
    	else
    	{
    		editorPane = new JEditorPane( "text/html" , "<html><body><p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</p></body></html>" );
    	}
    	editorPane.setEditable( false);
    	editorPane.addMouseListener( mouseListener );
    	editorPane.addKeyListener( new StatementBoxKeyListener() );
    	editorPane.addFocusListener( new StatementBoxFocusListener() );
    	//editorPane.setPreferredSize( new Dimension ( 230, 50) );
    	editorPane.setSize( 230, 50);
    	editorPane.setMinimumSize( new Dimension ( 230, 50) );
    	editorPane.setMaximumSize( new Dimension ( 550, 120) );
    	
    	
    	
    	//Initilise Filter
    	filter = new HTMLFilter( editorPane.getText() );
    	
    	
    	//Initilise Label
    	numberLabel = new JLabel( "" + box.getID()[ box.getID().length - 1] );
    	
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
    	//expand = new JButton( "\\/");
    	expand = new JButton( expandIcon1 );
    	//edit = new JButton( "Edit");
    	edit = new JButton( editIcon1);
    	swapDown = new JButton( swapDownIcon );
    	swapUp = new JButton( swapUpIcon );
    	
    	
    	
    	remove.addActionListener( new RemoveButtonListener());
    	addNew.addActionListener( new AddNewBoxButtonListener());
    	expand.addActionListener( new ExpandButtonListener());
    	edit.addActionListener( new EditButtonListener());
    	swapDown.addActionListener( new SwapDownButtonListener());
    	swapUp.addActionListener( new SwapUpButtonListener());
    	
    	remove.setToolTipText( "Remove");
    	addNew.setToolTipText( "Add new step under this step");
    	expand.setToolTipText( "Show substeps");
    	edit.setToolTipText( "Edit the text of this step");
    	swapDown.setToolTipText( "Swap down this step");
    	swapUp.setToolTipText( "Swap up text of this step");
    	
    	//Add components to panels
    	buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.Y_AXIS));
    	buttonPanel.add( remove);
    	buttonPanel.add( addNew);
    	buttonPanel.add( edit);
    	buttonPanel.add( expand);
    	
    	
    	swapButtonPanel.add( swapUp);
    	swapButtonPanel.add( swapDown);
    
    	mainBoxPanel.add( swapButtonPanel);
    	mainBoxPanel.add( numberLabel);
    	mainBoxPanel.add( editorPane);
    	mainBoxPanel.add( buttonPanel);
    	
    	//set Color of this panel
    	//setColor( new Color( 201,186,18));
    	
    	setColor( new Color( 188, 223, 227) );
    	
    	
    	add( mainBoxPanel);
    	
    	if( box.isExpanded() )
    	{
    		add( subBoxes);
    	}
    }
    

    ///////////////////////////////////////////////////////////////
    //////// IMPLEMENT PROPERTY CHANGE LISTENER //////////////////
    ///////////////////////////////////////////////////////////////
    
    public void propertyChange( PropertyChangeEvent e)
    {
    	box = (StatementBox) (e.getSource());
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
    	
    	editorPane.setText( box.getHTMLVariablesHighlighted() );
    	
    	repaint();
    	revalidate();
    }
    
    public void setColor( Color c)
    {
    	setBackground( c);
    	buttonPanel.setBackground( c);
    	mainBoxPanel.setBackground( c);
    	
    	//Change Button's 
    	swapDown.setBackground(c);
    	swapUp.setBackground(c);
    	remove.setBackground(c);
    	addNew.setBackground(c);
    	expand.setBackground(c);
    	edit.setBackground(c);
    	swapDown.setBorderPainted(false);
    	swapUp.setBorderPainted(false);
    	remove.setBorderPainted(false);
    	addNew.setBorderPainted(false);
    	expand.setBorderPainted(false);
    	edit.setBorderPainted(false);
    	
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SUBCLASSES
    //////////////////////////////
    
    private class RemoveButtonListener implements ActionListener
    {
    	public void actionPerformed( ActionEvent e)
    	{
    		box.getUpperList().removeBox( box);
    		
    	}
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class EditButtonListener implements ActionListener
    {
    	public void actionPerformed( ActionEvent e)
    	{
    		if( ! ( editorPane.isEditable() ) )
    		{
    			editorPane.setEditable( true);
    			editorPane.requestFocus();
    			//(( JButton)(e.getSource()) ).setText( "Done");
    			edit.setIcon( editIcon2);
    			
    		}
    		else
    		{
    			editorPane.setEditable( false);
    			//(( JButton)(e.getSource()) ).setText( "Edit");
    			edit.setIcon( editIcon1);
    			filter = new HTMLFilter( editorPane.getText() );
    			box.setStatement( filter.getBodyContents() );
    			editorPane.setText( box.getHTMLVariablesHighlighted() );
    			
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
    private class ExpandButtonListener implements ActionListener
    {
    	public void actionPerformed( ActionEvent e)
    	{	
    		if( ! box.isExpanded() )
    		{
    			if( box.checkExpanded() )
    			{
		    		subBoxes = new StatementBoxListPanel( box.getSubBoxes() );
		    		add( subBoxes);
		    	}
		    	else
		    	{
		    		box.addSubBox();
		    	
		    		subBoxes = new StatementBoxListPanel( box.getSubBoxes());
		    		add( subBoxes);
		    	}
		    	box.setExpanded( true);
		    	//(( JButton)(e.getSource()) ).setText( "/\\");
		    	expand.setIcon( expandIcon2);
		    	expand.setToolTipText( "Hide substeps");
    		}
    		else
    		{
    			remove( subBoxes);
    			box.setExpanded( false);
    			
    			if( !box.checkExpanded() )
    			{
    				box.setExpanded( box.checkExpanded() );
    				actionPerformed( e);
    			}
    			else if( box.getSubBoxes().getFirstStep().getStatement().length() <= 0)
    			{
    				box.getSubBoxes().remove( box.getSubBoxes().getFirstStep());
    				
    			}
    			//( ( JButton)(e.getSource()) ).setText( "\\/");
    			expand.setIcon( expandIcon1);
    			expand.setToolTipText( "Show substeps");
    		}
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
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private class StatementBoxMouseListener extends MouseAdapter implements Serializable
	{
		public void mouseClicked( MouseEvent e)
		{
			if( e.getSource() instanceof JPanel )
			{
				grabFocus();
			}
			else if( e.getSource() instanceof JEditorPane && e.getClickCount() == 2 )
			{
				editorPane.setEditable( true);
    			editorPane.requestFocus();
    			//(( JButton)(e.getSource()) ).setText( "Done");
    			edit.setIcon( editIcon2);
			}
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
	private class StatementBoxKeyListener extends KeyAdapter 
	{
		public void keyPressed( KeyEvent e)
		{
			if( e.getSource() instanceof JEditorPane && e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				HTMLFilter filter = new HTMLFilter( editorPane.getText());
				String content = filter.getBodyContents();
				
				if( editorPane.isEditable() )
				{
						if( content.trim().equalsIgnoreCase( "if") )
						{
							StatementBoxList boxList = box.getUpperList();
							StatementBox box2 = new DecisionBox( box.getID() );
							boxList.addBox( box2);
							revalidate();
						}
						else if( content.trim().equalsIgnoreCase( "while") )
						{
							StatementBoxList boxList = box.getUpperList();
							StatementBox box2 = new RepetitionBox( box.getID() );
							boxList.addBox( box2);
							revalidate();
						}
						else
						{
							editorPane.setEditable( false);
			    			edit.setIcon( editIcon1);
			    			filter = new HTMLFilter( editorPane.getText() );
			    			box.setStatement( filter.getBodyContents() );
			    			editorPane.setText( box.getHTMLVariablesHighlighted() );
						}
				}
				
			}
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	
}// end of class StatementBoxPanel