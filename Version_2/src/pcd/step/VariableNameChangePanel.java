package pcd.step;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pcd.variable.VariableList;
import pcd.variable.VariablePanel;

public class VariableNameChangePanel extends JPanel {
	
	// Properties
	private ArrayList<JCheckBox> checkBoxList;
	private ArrayList<JTextField> textFieldList;
	private FocusListener listener;
	
	// Constructor
	public VariableNameChangePanel(VariableList variableList)
	{
		checkBoxList = new ArrayList<JCheckBox>();
		textFieldList = new ArrayList<JTextField>();
		listener = new VariableNameChangePanelFocusListener();
		
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS));
		
		for ( int i = 0; i < variableList.size(); i++)
		{
			JPanel nameChange = new JPanel();
			VariablePanel variable = new VariablePanel( variableList.get(i), false );
			variable.setPreferredSize( new Dimension( 290,50 ));
			nameChange.setPreferredSize( new Dimension( 190,50 ));
			JPanel panel = new JPanel();
			
			//panel.setLayout( new BoxLayout(panel, BoxLayout.X_AXIS ));
			panel.setBackground( new Color ( 85, 85, 85));
			
			checkBoxList.add( new JCheckBox());
			textFieldList.add( new JTextField(13));
			
			textFieldList.get(i).addFocusListener(listener);
			
			nameChange.setBackground( new Color( 85,85,85));
			variable.setColor( new Color( 60,156,186) );
			checkBoxList.get(i).setBackground( new Color( 85,85,85) );
			
			nameChange.add( textFieldList.get(i));
			nameChange.add( checkBoxList.get(i));
			
			panel.add( variable);
			panel.add( nameChange);
			panel.setBorder( BorderFactory.createLoweredBevelBorder() );
			
			
			add( panel);
		}
		
	}
	
	public ArrayList<JCheckBox> getCheckBoxList()
	{
		return checkBoxList;
	}
	
	public ArrayList<JTextField> getTextFieldList(){
		return textFieldList;
	}
	
	private class VariableNameChangePanelFocusListener implements FocusListener 
	{

		@Override
		public void focusGained(FocusEvent e) {
			int index = textFieldList.indexOf( e.getSource());
			
			checkBoxList.get(index).setSelected(true);
		}

		@Override
		public void focusLost(FocusEvent e) {
			
			int index = textFieldList.indexOf( e.getSource());
			if( textFieldList.get(index).getText().equals("") || textFieldList.get(index).getText().contains(" ") || textFieldList.get(index).getText() == null )
			{
				textFieldList.get(index).setText("");
				checkBoxList.get(index).setSelected(false);
			}
		}
		
	}

}
