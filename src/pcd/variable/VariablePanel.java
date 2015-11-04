package pcd.variable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class VariablePanel extends JPanel{
	
	Variable			variable;
	private JTextField	textField;
	private JPanel		buttonsPanel;
	private JPanel		buttonsOutPanel;
	private JButton		removeButton;
	private ImageIcon	editIcon, doneIcon, removeIcon;

	public VariablePanel( Variable variable, boolean editable){
		
		setLayout( new GridLayout(1, 2) );
		
		this.variable = variable;
		
		textField = new JTextField();
		textField.setText( variable.toString() );
		textField.setEditable( false);
		textField.setBorder( null);
		textField.setFont( new Font( "Segoe UI", Font.BOLD, 17) );
		add( textField);
		
		buttonsPanel = new JPanel();
		buttonsOutPanel = new JPanel();
		
		buttonsPanel.setLayout( new BoxLayout( buttonsPanel, BoxLayout.Y_AXIS ));
		buttonsOutPanel.setLayout( new BorderLayout() );
    	
		setMaximumSize( new Dimension ( 1100, 80) );
    
    	removeIcon =  new ImageIcon( "Images/closeButton.png" );
    	
    	removeButton = new JButton( removeIcon);
    	removeButton.setBorderPainted( false);
    	removeButton.setContentAreaFilled( false);
		
    	removeButton.addActionListener( new RemoveButtonListener());

		buttonsPanel.add(removeButton);
		buttonsOutPanel.add( buttonsPanel, BorderLayout.EAST);
		buttonsOutPanel.setVisible( false);
		add( buttonsOutPanel);
		
		if( editable){
			textField.addMouseListener( new MyMouseListener() );
			textField.addActionListener( new MyActionListener() );
		}
		
		
	}

	private class MyActionListener implements ActionListener
	{
		public void actionPerformed( ActionEvent ae)
		{
			textField.setEditable( false);
			//variable.setName( textField.getText() );
			variable.getVariableList().changeVariableName(variable, textField.getText());
		}
	}

		
	private class MyMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked( MouseEvent e){
			if( !textField.isEditable() ){
				if( e.getClickCount() == 2){
					if( buttonsOutPanel.isVisible())
					{
						textField.setEditable( true);
						textField.requestFocus();
					}
				}
				
				else if( e.getClickCount() == 1){
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
			}
		}
		
	}
	
	private class RemoveButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			variable.getVariableList().removeVariable( variable);
		}
		
	}
	
	public void setColor( Color c)
	{
		setBackground( c);
		buttonsPanel.setBackground( c);
		buttonsOutPanel.setBackground( c);
		removeButton.setBackground( c);
		textField.setBackground( c);
	}
	
}
