package pcd.step;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import pcd.AddStepKindsPanel;
import pcd.Project;
import pcd.variable.Variable;
import pcd.variable.VariableList;
import pcd.variable.VariableListPanel;

public class NewMethodPanel extends JPanel
{
	// Properties
	private JPanel				inputPanel, outputPanel, treePanel, buttonPanel, generalPanel, stepKindsPanel;
	private VariableListPanel	inputListPanel, outputListPanel;
	private JScrollPane			inputScrollPane, outputScrollPane, generalScrollPane;
	private PseudoCodeStepTree	tree;
	private JLabel				inputLabel, outputLabel, methodBodyLabel;
	private JButton				inputButton, outputButton, confirmButton, cancelButton;
	private VariableList		inputs, outputs;
	private PCDTreeNode 		method;
	private Toolkit 			toolkit;
	private static int 			count = 1;
	private ActionListener 		listener;
	
	// Constructors
	public NewMethodPanel ( PCDTreeNode node )
	{
		method = null;
		listener = new NewMethodListener();
		toolkit = Toolkit.getDefaultToolkit();
		generalPanel = new JPanel();
		generalPanel.setLayout( new BoxLayout(generalPanel, BoxLayout.Y_AXIS));
		
		
		Project project = new Project();
		tree = new PseudoCodeStepTree( project );
		if( node != null){
			TreeModel model = new DefaultTreeModel(node.copy(), false);
			tree.getProject().setSteps( model);
			tree.setModel( model );
			tree.getProject().manageIDs();
		}
		
		stepKindsPanel = new AddStepKindsPanel(project, BoxLayout.X_AXIS );
		
		inputPanel = new JPanel();
		outputPanel = new JPanel();
		treePanel = new JPanel();
		buttonPanel = new JPanel();
		
		inputs = new VariableList();
		outputs = new VariableList();
		
		inputListPanel = new VariableListPanel(inputs);
		outputListPanel = new VariableListPanel(outputs);
		
		inputScrollPane = new JScrollPane(inputListPanel);
		outputScrollPane = new JScrollPane(outputListPanel);
		
		inputButton = new JButton("Add Input Variable");
		outputButton = new JButton("Add Output Variable");
		confirmButton = new JButton("OK");
		cancelButton = new JButton("CANCEL");
		
		inputButton.addActionListener( listener);
		outputButton.addActionListener( listener);
		confirmButton.addActionListener( listener);
		cancelButton.addActionListener( listener);
		
		inputButton.setActionCommand( "AddInputVariable");
		outputButton.setActionCommand( "AddOutputVariable");
		confirmButton.setActionCommand( "OK");
		cancelButton.setActionCommand( "Cancel");
		
		inputLabel = new JLabel( "Input Variables:");
		outputLabel = new JLabel( "Output Variables:");
		methodBodyLabel = new JLabel( "Method Body: ");
		
		inputLabel.setFont( new Font( "Berlin Sans FB", Font.PLAIN, 30 ));
		outputLabel.setFont( new Font( "Berlin Sans FB", Font.PLAIN, 30 ));
		methodBodyLabel.setFont( new Font( "Berlin Sans FB", Font.PLAIN, 30 ));
    	
		inputLabel.setOpaque( true);
		outputLabel.setOpaque( true);
		methodBodyLabel.setOpaque( true);
		
		inputLabel.setBackground( new Color( 60,156,186) );
		outputLabel.setBackground( new Color( 60,156,186) );
		methodBodyLabel.setBackground( new Color( 60,156,186) );

		inputPanel.add(inputScrollPane);
		inputPanel.add(inputButton);
		outputPanel.add(outputScrollPane );
		outputPanel.add(outputButton);
		treePanel.add(tree);
		buttonPanel.add( confirmButton);
		buttonPanel.add(cancelButton);
		
		inputPanel.setBackground( new Color( 85, 85, 85) );
		outputPanel.setBackground( new Color( 85, 85, 85) );
		buttonPanel.setBackground( new Color( 85, 85, 85) );
		
		inputScrollPane.setPreferredSize(new Dimension( 750, 200));
		outputScrollPane.setPreferredSize(new Dimension( 750, 200));
		confirmButton.setPreferredSize( new Dimension( 475, 40));
		cancelButton.setPreferredSize( new Dimension( 475, 40));
		inputButton.setPreferredSize( new Dimension( 200, 200));
		outputButton.setPreferredSize( new Dimension( 200, 200));
		
		generalPanel.setBackground( new Color( 60,156,186) );
		//generalPanel.setBackground( new Color( 85, 85, 85) );
		treePanel.setBackground( new Color( 85, 85, 85) );
		inputScrollPane.setBackground( new Color( 85, 85, 85) );
		outputScrollPane.setBackground( new Color( 85, 85, 85) );
		treePanel.setMinimumSize( new Dimension( 950, 450));

		generalPanel.add( inputLabel);
		generalPanel.add(inputPanel);
		generalPanel.add( methodBodyLabel );
		generalPanel.add(treePanel);
		generalPanel.add( stepKindsPanel );
		generalPanel.add( outputLabel );
		generalPanel.add(outputPanel);
		generalPanel.add(buttonPanel);
		generalScrollPane = new JScrollPane( generalPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		generalScrollPane.setPreferredSize( new Dimension( (int) (toolkit.getScreenSize().getWidth() * 0.70f) , (int) (toolkit.getScreenSize().getHeight() * 0.70f) ) );
		add( generalScrollPane);
		
		inputScrollPane.getVerticalScrollBar().setUnitIncrement(13);
		outputScrollPane.getVerticalScrollBar().setUnitIncrement(13);
		generalScrollPane.getVerticalScrollBar().setUnitIncrement( 10);
		generalScrollPane.getHorizontalScrollBar().setUnitIncrement( 10);
	}
	
	public PCDTreeNode getConstructedMethodNode(){
		return method;
	}
	
//	public static void addAllDepthChildren( PCDTreeNode root, PCDTreeNode child ){
//		root.addChild( child);
//		for( int i = 0; i < child.getChildCount(); i++){
//			
//		}
//	}
//	
	private class NewMethodListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			
			
			if( command.equals( "AddInputVariable")){
				inputs.addVariable( new Variable( "newVariable" + ++count ));
			}
			else if( command.equals( "AddOutputVariable")){
				outputs.addVariable( new Variable( "newVariable" + ++count ));
				
			}
			else if( command.equals( "OK")){
				PCDTreeNode root = (((PCDTreeNode) ((DefaultTreeModel)tree.getModel()).getRoot()));
				method = new PCDTreeNode( new Method( inputs, outputs), new Project() );
				((Method)method.getUserObject()).setStatement( ((Step) root.getUserObject()).statement );
				
				for( int i = 0; i < root.getChildCount(); i++){
					method.addChild( (PCDTreeNode)root.getChildAt(i));
				}
				Window w = SwingUtilities.getWindowAncestor( NewMethodPanel.this );
				w.dispose();
			}
			else if( command.equals( "Cancel")){
				method = null;
				Window w = SwingUtilities.getWindowAncestor( NewMethodPanel.this );
				w.dispose();
			}
			
		}
		
	}
}