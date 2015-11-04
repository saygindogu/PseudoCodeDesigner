import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.*;

/**
 * NewMethodPanel - allows user to create a method that contains algorithms
 * 
 * @author Ali Naci Uysal from group G1B (R2D2)
 * @version 1.00 02/04/2013
 */
 
public class NewMethodPanel extends JPanel
{
	JPanel 						inputPanel, midPanel, outputPanel, buttonPanel, generalPanel;
	JTextField 					methodName;
	JLabel						inputLabel, label, midLabel, outputLabel;
	JButton						okButton, cancelButton, addOutputButton, addInputButton;
	JScrollPane					jsp;
	OptionsGUI					optionsGui;
	PCDMethod 					newMethod;
	VariableList 				inputList, outputList;
	VariableListPanel 			inputListPanel, outputListPanel;
	StatementBoxList 			statementBoxList;
	StatementBoxListPanel 		statementBoxListPanel;
	
	public NewMethodPanel()
	{
		label = new JLabel( "New PCDMethod");
		methodName = new JTextField();
		
		generalPanel = new JPanel();
		
		//Input Panel
		inputPanel = new JPanel();
		
		inputLabel = new JLabel ("Inputs: ");
		inputList = new VariableList();
		
		
		Finder f = new Finder( inputList);
		inputList = f.findVariables( "");
		
		inputListPanel = new VariableListPanel( inputList, new Project());
		addInputButton = new JButton ("Add Variable");
		
		inputPanel.add( inputLabel );
		inputPanel.add( inputListPanel);
		inputPanel.add( addInputButton);
		addInputButton.addActionListener( new InputButtonListener() );
		inputPanel.setLayout( new BoxLayout( inputPanel, BoxLayout.Y_AXIS) );
		
		// Middle Panel
		midPanel = new JPanel();
		
		midLabel = new JLabel( "Body");
		
		
		statementBoxList = new StatementBoxList();
		statementBoxList.addBox( new StatementBox() );
		statementBoxList.setProjectReferance( new Project() );
		
		statementBoxListPanel = new StatementBoxListPanel( statementBoxList);
		
		midPanel.add( midLabel);
		midPanel.add( statementBoxListPanel);
		
		midPanel.setLayout( new BoxLayout( midPanel, BoxLayout.Y_AXIS) );
		
		//Output Panel
		outputPanel = new JPanel();
		
		outputLabel = new JLabel( "Outputs: ");
		
		outputList = new VariableList();
		outputListPanel = new VariableListPanel( outputList, new Project());
		outputPanel.add( outputLabel);
		outputPanel.add( outputListPanel);
		
		addOutputButton = new JButton ("Add Variable");
		outputPanel.add( addOutputButton);
		addOutputButton.addActionListener( new OutputButtonListener() );
		
		outputPanel.setLayout( new BoxLayout( outputPanel, BoxLayout.Y_AXIS) );
		
		generalPanel.add( label);
		generalPanel.add( methodName);
		generalPanel.add( inputPanel);
		generalPanel.add( midPanel);
		generalPanel.add( outputPanel);
		generalPanel.setLayout( new BoxLayout( generalPanel, BoxLayout.Y_AXIS) );
		
		jsp = new JScrollPane( generalPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.getVerticalScrollBar().setUnitIncrement( 9);
		
		jsp.setPreferredSize( new Dimension ( 400, 400) );
		
		
		add(jsp);
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS) );
	}
	
	// Returns the new method specified in the panel
	public PCDMethod getNewMethod()
	{
		return newMethod;
	}

	// A method that saves the method with given features
	public void saveMethod()
	{
		if ( methodName.getText() == null || methodName.getText().equals("") )
			methodName.getText().equals( "Nameless Method");
		
		else
			newMethod = new PCDMethod( methodName.getText() );
		
		for ( int i = 0; i < inputList.size(); i++ )
		{
			newMethod.addInput( inputList.get(i) );
		}
		
		for ( int n = 0; n < outputList.size(); n++ )
		{
			newMethod.addOutput( outputList.get(n) );
		}
		
		for( int x = 0; x < statementBoxList.size(); x++ )
		{
			newMethod.addSubBox( statementBoxList.get(x) );
		}
		newMethod.addVariable( inputList);
		newMethod.addVariable( outputList);
	}
	
	// Represents a listener class for the "Add Variable" button in InputListPanel
	private class InputButtonListener implements ActionListener{
    	int count = 1;
		public void actionPerformed( ActionEvent ae){
			if ( ae.getSource() == addInputButton )
			count = inputListPanel.getVariableList().size() + 1;
			Variable v = new Variable("Variable" + count);
			inputListPanel.getVariableList().addVariable(v);
			count++;
			revalidate();
		}
	}
	
	// Represents a listener class for the "Add Variable" button in InputListPanel
	private class OutputButtonListener implements ActionListener{
	    int count = 1;
		public void actionPerformed( ActionEvent ae){
			count = outputListPanel.getVariableList().size() + 1;
			Variable v = new Variable("Variable" + count);
			outputListPanel.getVariableList().addVariable(v);
			count++;
			revalidate();
		}
	}
}
