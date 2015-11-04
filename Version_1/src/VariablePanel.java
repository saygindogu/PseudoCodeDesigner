import javax.swing.BoxLayout;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * VariablePanel a panel for each variable in VariableList
 *
 * @author G1B R2D2 - Bengisu Batmaz
 *
 */

class VariablePanel extends JPanel
{
	Variable			var;
	private JTextField	text;
	private JPanel		buttons;
	private JPanel		buttonsOutPanel;
	private JButton		remove;
	private JButton		edit;
	private ImageIcon editIcon1, editIcon2, removeIcon;
	protected Project project;
	String oldName;
	
	
	VariablePanel( Variable var, Project pro){
		
		setLayout( new GridLayout(1, 2) );
		this.var = var;
		
		oldName = "";
		
		project = pro;
		text = new JTextField();
		text.setText( var.toString() );
		text.setEditable( false);
		text.setBorder( null);
		text.setFont( new Font( "Times New Roman", Font.PLAIN, 15) );
		add( text);
		
		buttons = new JPanel();
		buttonsOutPanel = new JPanel();
		
		buttons.setLayout( new BoxLayout( buttons, BoxLayout.Y_AXIS ));
		buttonsOutPanel.setLayout( new BorderLayout() );
		
    	removeIcon =  new ImageIcon( "closebutton.png" );
    	editIcon2 =  new ImageIcon( "done.png" );
    	editIcon1 =  new ImageIcon( "edit.png" );
    	
    	setMaximumSize( new Dimension ( 250, 80) );
    	
    	//Initilise buttons
    
    	remove = new JButton( removeIcon );
		remove.setBorderPainted( false);
		remove.setContentAreaFilled( false);
		
    	edit = new JButton( editIcon1);
    	edit.setBorderPainted( false);
    	edit.setContentAreaFilled( false);

		remove.addActionListener( new RemoveButtonListener());
		edit.addActionListener( new EditButtonListener());
		buttons.add(remove);
		buttons.add( edit);
		buttonsOutPanel.add( buttons, BorderLayout.EAST);
		addMouseListener( new MyMouseListener() );
		buttonsOutPanel.setVisible( false);
		add( buttonsOutPanel);
		
		text.addMouseListener( new MyMouseListener() );
		text.addActionListener( new MyActionListener() );
		
	}
	
	private class EditButtonListener implements ActionListener{
		
		
		public void actionPerformed(ActionEvent e)
		{
			if( ! ( text.isEditable() ) )
    		{
    			text.setEditable( true);
    			text.requestFocus();
    			oldName = text.getText();
    			edit.setIcon( editIcon2);
    			
    		}
    		else
    		{
    			text.setEditable( false);
    			var.setName( text.getText() );
    			project.changeWord( oldName , text.getText() );
    			edit.setIcon( editIcon1);
    			
    		}
		}
	}
	
	private class MyActionListener implements ActionListener
	{
		public void actionPerformed( ActionEvent ae)
		{
			text.setEditable( false);
			var.setName( text.getText() );
			project.changeWord( oldName , text.getText() );
			edit.setIcon( editIcon1);
		}
	}

	private class MyMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked( MouseEvent e){
			if( e.getClickCount() == 1){
				if( buttonsOutPanel.isVisible() )
				{
					buttonsOutPanel.setVisible(false);
					revalidate();
				}
				else
				{
					buttonsOutPanel.setVisible(true);
					revalidate();
				}
			}
			if( e.getClickCount() == 2){
				text.setEditable( true);
				text.requestFocus();
			}			
		}
		
	}
	
	public void setColor( Color c)
	{
		this.setBackground( c);
		buttons.setBackground( c);
		buttonsOutPanel.setBackground( c);
		remove.setBackground( c);
		edit.setBackground( c);
		text.setBackground( c);
	}
	
	private class RemoveButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e)
		{
			var.getUpperList().removeVariable( var);
		}
	}

}

