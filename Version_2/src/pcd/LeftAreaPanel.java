package pcd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

import pcd.variable.Variable;
import pcd.variable.VariableListPanel;

public class LeftAreaPanel extends JPanel {
	
	VariableListPanel varList;
	JSplitPane splitPane;
	JScrollPane variables;
	JButton addVariableButton;
	JPanel variablesPanel;
	JPanel addVariableButtonPanel;
	JPanel addStepKindsPanel;
	JLabel variablesLabel;
	
	int newVariableCount = 0;
	
    public LeftAreaPanel( Project p, int x, int y, Color color) {
    	
    	setLayout( new BorderLayout());
    	variablesLabel = new JLabel( "Variables");
    	variablesLabel.setFont( new Font( "Berlin Sans FB", Font.PLAIN, 30 ));
    	variablesLabel.setOpaque( true);
    	//variablesLabel.setBackground( new Color( 93,198,165));
    	variablesLabel.setBackground( new Color( 60,156,186) );
    	variablesPanel = new JPanel();
    	addVariableButtonPanel = new JPanel();
    	addStepKindsPanel = new AddStepKindsPanel( p, BoxLayout.Y_AXIS );
    	addVariableButtonPanel.setBackground(color);
    	variablesPanel.setLayout( new BorderLayout() );
    	variablesPanel.setBackground(color);
    	varList = new VariableListPanel( p.getVariableList());
    	varList.setBackground(color);
    	addStepKindsPanel.setBackground(color);
    	addVariableButton = new JButton( "Add New Variable");
    	variables = new JScrollPane( varList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
    	variables.getVerticalScrollBar().setUnitIncrement( 8);
    	variables.getViewport().setBackground(color);
    	splitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT , addStepKindsPanel , variablesPanel );
    	splitPane.setBackground(color);
    	variables.setPreferredSize( new Dimension ( x, y * 2 / 7));
    	variablesPanel.setPreferredSize( new Dimension( x, y * 2 / 7));
    	variablesPanel.add( variablesLabel, BorderLayout.NORTH );
    	variablesPanel.add( variables);
    	variablesPanel.add( addVariableButtonPanel, BorderLayout.SOUTH);
    	addVariableButtonPanel.add( addVariableButton);
    	addVariableButton.addActionListener( new addVariableButtonListener() );
    	
    	setBackground(color);
    	
    	splitPane.setPreferredSize( new Dimension(  x, y) );
    	//setPreferredSize( new Dimension(  x, y) );
    	add( splitPane );
    }
    
    private class addVariableButtonListener implements ActionListener{
    	
    	public void actionPerformed( ActionEvent e){
    		Variable variable = new Variable( "newVariable" + ++newVariableCount );
    		
    		varList.getVariableList().addVariable( variable);
    	}
    }
}
