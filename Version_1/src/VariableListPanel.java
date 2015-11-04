import javax.swing.*;
import javax.swing.event.AncestorListener;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


public class VariableListPanel extends JPanel implements PropertyChangeListener{
	
	VariableList 		list;
	VariablePanel		pane;
	Project				project;
	
	public VariableListPanel( VariableList list, Project pro){

		this.list = list;
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS) );
		
		for ( int i = 0; i < list.size(); i++ )
		{
			int n = i % 2;
			project = pro;
			pane = new VariablePanel( list.get(i), project);
			switch( n){
				default:pane.setColor( new Color( 159, 176, 255));
						add(pane);
						break;
						
				case 0:	pane.setColor(new Color( 159, 176, 255));
						add(pane);
						break;
						
				case 1:	pane.setColor( new Color( 255, 255, 255));
						add(pane);
						break;
			}
		}
		this.list.addPropertyChangeListener( this);
	}
	
	public void propertyChange( PropertyChangeEvent e) {
		list = (VariableList) e.getSource();
		
		removeAll();
		
		
		for ( int i = 0; i < list.size(); i++ )
		{
			int n = i%2;
			pane = new VariablePanel( list.get(i), project);
			switch(n){
				default:pane.setColor( new Color( 159,176,255));
						add( pane);
						break;
						
				case 0:	pane.setColor(new Color( 159, 176, 255));
						add( pane);
						break;
						
				case 1:	pane.setColor( new Color( 255, 255, 255));
						add( pane);
						break;
			}
		}

		repaint();
		revalidate();
	}
	
	public VariableList getVariableList()
	{
		return list;
	}
	private void addVariablePane( VariablePanel v){
		if( list.indexOf(v) % 2 == 1)
			v.setBackground( new Color( 159, 176, 255));
		else if (list.indexOf(v)%2 ==0)
			v.setBackground(new Color(255,255,255));
		
		add( v);
		revalidate();
	}


	
	
}
