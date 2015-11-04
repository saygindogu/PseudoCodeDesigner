/**
 * @(#)LeftAreaPanel.java
 *
 *
 * @author R2D2 - Z. Saygýn Doðu
 * @version 1.00 2013/5/7
 */
 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.*;


public class LeftAreaPanel extends JPanel {
	
	VariableListPanel varList;
	TreePanel treePanel;
	JSplitPane splitPane;
	JScrollPane variables;
	JScrollPane treeScrollPane;
	JButton addVariableButton;
	JPanel variablesPanel;
	JPanel addVariableButtonPanel;
	
    public LeftAreaPanel( Project p, int x, int y) {
    	
    	variablesPanel = new JPanel();
    	addVariableButtonPanel = new JPanel();
    	variablesPanel.setLayout( new BorderLayout() );
    	varList = new VariableListPanel( p.getVariableList(), p);
    	treePanel = new TreePanel(  p.getSteps() );
    	addVariableButton = new JButton( "Add New Variable");
    	variables = new JScrollPane( varList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
    	treeScrollPane = new JScrollPane( treePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	variables.getVerticalScrollBar().setUnitIncrement( 13);
    	treeScrollPane.getVerticalScrollBar().setUnitIncrement( 10);
    	treeScrollPane.setPreferredSize( new Dimension (x, y * 2 / 7) );
    	splitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT , treeScrollPane, variablesPanel );
    	variables.setSize( x, y * 2 / 7);
    	variablesPanel.setSize( x, y * 2 / 7);
    	variablesPanel.add( variables);
    	variablesPanel.add( addVariableButtonPanel,  BorderLayout.SOUTH);
    	addVariableButtonPanel.add( addVariableButton);
    	addVariableButton.addActionListener( new AddButtonListener() );
    	
    	splitPane.setPreferredSize( new Dimension(  x, y) );
    	setPreferredSize( new Dimension(  x, y) );
    	add( splitPane );
    }
    
    private class AddButtonListener implements ActionListener{
    	int count = 1;
		public void actionPerformed( ActionEvent ae){
			count = varList.getVariableList().size() + 1;
			Variable v = new Variable("Variable" + count);
			varList.getVariableList().addVariable(v);
			count++;
			revalidate();
		}
	}
    
    
}