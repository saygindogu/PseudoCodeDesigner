package pcd.step;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;

import pcd.Project;
import pcd.variable.VariableListPanel;

public class InsertMethodPanel extends JPanel {
	
	// Properties
	PCDTreeNode node;
	Method method;
	JScrollPane inputScrollPane, outputScrollPane;
	JPanel inputPanel, outputPanel;
	VariableNameChangePanel inputVariableNameChangePanel, outputVariableNameChangePanel;
	JButton okButton, cancelButton;
	ActionListener listener;
	JTree tree;
	Project project;
	
	// Constructor
	public InsertMethodPanel( PCDTreeNode node, Method method, JTree tree, Project project)
	{
		this.method = method;
		this.tree = tree;
		this.node = node;
		this.project = project;
		
		
		inputVariableNameChangePanel = new VariableNameChangePanel(method.getInputs());
		outputVariableNameChangePanel = new VariableNameChangePanel(method.getOutputs());
		
		listener = new InsertMethodActionListener();
		
		okButton = new JButton( "OK");
		okButton.setActionCommand( "OK");
		okButton.addActionListener( listener);
		
		cancelButton = new JButton( "Cancel");
		cancelButton.setActionCommand( "Cancel");
		cancelButton.addActionListener( listener);
		
		inputPanel = new JPanel();
		outputPanel = new JPanel();
		
		setLayout(new BoxLayout( this, BoxLayout.X_AXIS));
		inputPanel.setLayout(new BoxLayout( inputPanel, BoxLayout.X_AXIS));
		outputPanel.setLayout(new BoxLayout( outputPanel, BoxLayout.X_AXIS));
		
		inputPanel.add( inputVariableNameChangePanel);
		outputPanel.add( outputVariableNameChangePanel);
		
		
		inputScrollPane = new JScrollPane( inputPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED  );
		outputScrollPane = new JScrollPane( outputPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED  );
		
		inputScrollPane.getVerticalScrollBar().setUnitIncrement(13);
		outputScrollPane.getVerticalScrollBar().setUnitIncrement(13);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		inputScrollPane.setPreferredSize( new Dimension( (int) (toolkit.getScreenSize().getWidth() * 0.35f) , (int) (toolkit.getScreenSize().getHeight() * 0.70f) ) );
		outputScrollPane.setPreferredSize( new Dimension( (int) (toolkit.getScreenSize().getWidth() * 0.35f) , (int) (toolkit.getScreenSize().getHeight() * 0.70f) ) );
		
		add( inputScrollPane);
		add( outputScrollPane);
		add( okButton);
		add( cancelButton);
	}
	
	public class InsertMethodActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if( command.equals( "OK")){
				Step step;
				ArrayList<String> inputs = new ArrayList<String>();
				ArrayList<String> outputs = new ArrayList<String>();
				
			    ArrayList<JTextField> textFields = new ArrayList<JTextField>();
			    ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
			    
			    textFields = inputVariableNameChangePanel.getTextFieldList();
			    checkBoxes = inputVariableNameChangePanel.getCheckBoxList();
				
				for( int i = 0; i < textFields.size(); i++) {
					if( checkBoxes.get(i).isSelected())
					{
						inputs.add( textFields.get(i).getText());
					}
					else {
						inputs.add( "");
					}
				}
				
				textFields = outputVariableNameChangePanel.getTextFieldList();
			    checkBoxes = outputVariableNameChangePanel.getCheckBoxList();
				
				for( int i = 0; i < textFields.size(); i++) {
					if( checkBoxes.get(i).isSelected())
					{
						outputs.add( textFields.get(i).getText());
					}
					else {
						outputs.add( "");
					}
				}
				
				/////// ARRAY BUSINESS ////////////////////////
				String[] inputStrings = new String[inputs.size() ];
				String[] outputStrings = new String[outputs.size() ];
				String[] oldInputStrings = new String[method.getInputs().size()];
				String[] oldOutputStrings = new String[method.getOutputs().size()];
				
				for( int i = 0; i < inputs.size(); i++){
					inputStrings[i] = inputs.get(i);
				}
				for( int i = 0; i < outputs.size(); i++){
					outputStrings[i] = outputs.get(i);
				}
				for( int i = 0; i < oldInputStrings.length; i++){
					oldInputStrings[i] = method.getInputs().get(i).getName();
					if( !inputStrings[i].equals( "") )
					{
						method.getInputs().get(i).setName( inputStrings[i]) ;
					}
				}
				for( int i = 0; i < oldOutputStrings.length; i++){
					oldOutputStrings[i] = method.getOutputs().get(i).getName();
					if( !outputStrings[i].equals( "") )
					{
						method.getOutputs().get(i).setName( outputStrings[i]) ;
					}
				}
				//////////////////////////////////////////////////////
				
				
				node.changeWord( oldInputStrings , inputStrings);
				node.changeWord( oldOutputStrings , outputStrings);
				
				node.removePropertyChangeListeners();
				node.addPropertychangeListener( project);
				
				step = new Step( method.statement );
				
				for( int i = 0; i < method.getInputs().size(); i++){
					if( ! method.getInputs().get(i).getName().equals( "")){
						project.getVariableList().addVariable( method.getInputs().get(i) );
					}
				}
				for( int i = 0; i < method.getOutputs().size(); i++){
					if( ! method.getOutputs().get(i).getName().equals( "")){
						project.getVariableList().addVariable(  method.getOutputs().get(i) );
					}
				}
				
				if( tree.getSelectionPath() != null ){
					PCDTreeNode selected = ((PCDTreeNode) tree.getSelectionPath().getLastPathComponent() );
					if( selected.isRoot() )
					{
						node.setUserObject( step);
						PCDTreeNode parent = (PCDTreeNode)selected.getParent();
						parent.addChild( node ,parent.getIndex( selected) + 1 );
					}
					else{
						node.setUserObject( step);
						selected.addChild( node  );
					}
				}
				else{
					node.setUserObject( step);
					((PCDTreeNode)((DefaultTreeModel)tree.getModel()).getRoot() ).addChild(  node );
				}
				
				Window w = SwingUtilities.getWindowAncestor( InsertMethodPanel.this );
				w.dispose();
			}
			else if( command.equals( "Cancel")){
				
				Window w = SwingUtilities.getWindowAncestor(  InsertMethodPanel.this  );
				w.dispose();
			}
		}
	}

}
