import java.util.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * MethodListPanel allows user to select an existing method from the file.
 *
 *
 * @author Ali Naci Uysal from group G1B (R2D2)
 * @version 1.00 2013/4/19
 */
;
public class MethodListPanel extends JPanel
{
	// PROPERTIES
	private ArrayList<JCheckBox> 	checkBoxList;
	private ArrayList<JCheckBox> 	selectedCheckBoxList;	
	private MethodList 				list;
	private MethodList				selectedMethods;
	private JScrollPane 			scroll;
	private JPanel					generalPanel;
	
	// CONSTRUCTOR
	public MethodListPanel( MethodList list)
	{
		generalPanel = new JPanel();
		
		
		checkBoxList = new ArrayList<JCheckBox>( list.size() );
		this.list = list;
		selectedMethods = new MethodList();
		
		generalPanel.setLayout( new BoxLayout( generalPanel, BoxLayout.Y_AXIS) );
		for( int i = 0; i < list.size(); i++ )
		{	
			JCheckBox checkBox = new JCheckBox( "" + list.get(i).getName() );
			checkBox.addItemListener( new CheckBoxListener() );
			checkBoxList.add( checkBox);
			generalPanel.add( checkBox);
		}
		
		
		scroll = new JScrollPane( generalPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.getVerticalScrollBar().setUnitIncrement( 9);
		scroll.setPreferredSize( new Dimension( 300,100) );	
		
		add( scroll);
		
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS) );
		setVisible (true);
		
	}
	
	
	public ArrayList<JCheckBox> getSelectedCheckBoxes( ArrayList<JCheckBox> checkBoxList)
	{
		selectedCheckBoxList = new ArrayList<JCheckBox>( list.size() );
		for ( int i = 0; i < checkBoxList.size(); i++ )
		{
			if ( checkBoxList.get(i).isSelected() )
				selectedCheckBoxList.add( checkBoxList.get(i) );	
		}
		return selectedCheckBoxList;
	}
	
	
	// Represents the listener for checkboxes of the methods in the list
	private class CheckBoxListener implements ItemListener
	{
		public void itemStateChanged( ItemEvent e)
		{
			if ( e.getSource() instanceof JCheckBox )
			{
				if (  ( (JCheckBox)(e.getSource() ) ).isSelected() )
				{
					selectedMethods.addMethod( list.get( checkBoxList.indexOf( (JCheckBox) e.getSource() ) ) );
				}
				else
				{
					selectedMethods.removeMethod( list.get( checkBoxList.indexOf( (JCheckBox) e.getSource() ) ) );
				}
			}
		}
	}
	
	public MethodList getSelectedMethods()
	{
		return selectedMethods;
	}
	
}
