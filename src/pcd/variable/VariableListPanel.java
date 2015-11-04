package pcd.variable;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import pcd.Project;

public class VariableListPanel extends JPanel implements PropertyChangeListener {

	private VariableList variableList;
	private VariablePanel panel;
	
	public VariableListPanel( VariableList variableList) 
	{
		this.variableList = variableList;
		
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS) );
		
		removeAll();
		populate();
		
		
		this.variableList.addPropertyChangeListener( this);
	}
	public void propertyChange( PropertyChangeEvent e) {
		variableList = (VariableList) e.getSource();
		removeAll();

		populate();

		repaint();
		revalidate();
	}
	
	private void populate(){
		for ( int i = 0; i < variableList.size(); i++ )
		{
			int n = i % 2;
			
			panel = new VariablePanel( variableList.get(i), true);
			
			if(n == 0)
			{
				panel.setColor(new Color( 159, 176, 255));
				add( panel);
			}
			else if (n == 1)
			{
				panel.setColor( new Color( 255, 255, 255));
				add( panel);
			}
		}
	}
	
	public VariableList getVariableList()
	{
		return variableList;
	}
	
	public VariablePanel getVariablePanel()
	{
		return panel;
	}
}
