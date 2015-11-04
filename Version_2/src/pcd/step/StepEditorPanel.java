package pcd.step;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import pcd.Project;


public class StepEditorPanel extends JPanel
{
	private static final String REMOVE = "Remove";
	private static final String ADD_NEW = "Add";
	private static final String EDIT = "Edit";
	private static final String DONE = "Done";
	private static final String S_UP = "Swap up"; 
	private static final String	S_DOWN = "Swap down";
	private static final String EXPAND = "Expand";
	private static final String TYPE = "Change type of this step";
	
	private Step step;
	private PCDTreeNode node;
	private JTree tree;
	private JLabel numberLabel, conditionalLabel;
	private JTextArea textArea;
	private JTextField conditionField;
	private JButton remove, addNew, edit, expand, swapUp, swapDown, typeButton;
	private JScrollPane textAreaPane;
	private JPanel buttonPanel, buttonRightPanel, swapButtonPanel, westPanel, northPanel, southPanel, centerPanel, conditionalBuffer;
	private PCDStepEditorButtonListener listener;
	private PropertyChangeSupport pcs;
	private ImageIcon removeIcon, addNewIcon, editIcon, expandIcon, swapUpIcon, swapDownIcon, doneIcon, typeIcon;
	
	public StepEditorPanel( Step step, JTree jtree, PCDTreeNode node, StepEditor editor) {
		
		this.step = step;
		this.tree = jtree;
		this.node = node;
		
		pcs = new PropertyChangeSupport( this);
		pcs.addPropertyChangeListener(  ((PseudoCodeStepTree) tree).getProject() );
		
		buttonPanel = new JPanel();
		swapButtonPanel = new JPanel();
		
		textArea = new JTextArea();
		textArea.setText( step.statement);
		textArea.setEditable( true);
		textArea.setFont( new Font( "Lucida Console", Font.PLAIN, Project.fontSize ));
		textArea.addKeyListener( new PCDKeyListener() );
		
		listener = new PCDStepEditorButtonListener();
		
		textAreaPane = new JScrollPane( textArea);
		textAreaPane.setPreferredSize( new Dimension( 600, 100));
		
    	swapButtonPanel.setLayout( new BoxLayout( swapButtonPanel, BoxLayout.Y_AXIS));
    	
    	
    	//Initialize Label
    	String labelText = step.stepID.toString();
    	labelText = "" + step.stepID.toArray()[ step.stepID.toArray().length - 1] ;
    	if( labelText.equals( "" + StepID.IF_TRUE_PART_ID)){
    		labelText = "T";
    	}
    	else if( labelText.equals( "" + StepID.IF_FALSE_PART_ID )){
    		labelText = "F";
    	}
    	else if( labelText.equals( "" + 0)){
    		labelText = "";
    	}
    	
    	numberLabel = new JLabel( labelText );
    	numberLabel.setFont( new Font( "Lucida Console", Font.BOLD, 25));
    	
    	////////////////////////
    	//Buttons
    	removeIcon = new ImageIcon( "Images/closeButton.png");
    	addNewIcon  = new ImageIcon( "Images/add.png");
    	editIcon = new ImageIcon( "Images/edit.png");
    	expandIcon = new ImageIcon( "Images/arrow.png");
    	swapUpIcon = new ImageIcon( "Images/upArrow.png");
    	swapDownIcon = new ImageIcon( "Images/downArrow.png");
    	doneIcon = new ImageIcon( "Images/done.png");
    	
    	//Type Icon
    	if( step.getType().equals( Step.GENERAL ) ){
    		typeIcon = new ImageIcon( "Images/StepTypeIcons/General.png");
    	}
    	else if( step.getType().equals( Step.INPUT )  ){
    		typeIcon = new ImageIcon( "Images/StepTypeIcons/Input.png");
    	}
		else if( step.getType().equals( Step.OUTPUT )  ){
			typeIcon = new ImageIcon( "Images/StepTypeIcons/Output.png");		
		}
		else if( step.getType().equals( Step.ASSIGNMENT )  ){
			typeIcon = new ImageIcon( "Images/StepTypeIcons/Assignment.png");
		}
    	
    	remove = new JButton( removeIcon);
    	addNew = new JButton( addNewIcon);
    	edit = new JButton( doneIcon);
    	expand = new JButton( expandIcon);
    	swapDown = new JButton( swapDownIcon);
    	swapUp = new JButton( swapUpIcon);
    	
    	
    	remove.setBorderPainted( false);
    	addNew.setBorderPainted( false);
    	edit.setBorderPainted( false);
    	expand.setBorderPainted( false);
    	swapDown.setBorderPainted( false);
    	swapUp.setBorderPainted( false);
    	
    	swapDown.setBorder( null);
    	swapUp.setBorder( null);
    	
    	
    	remove.setContentAreaFilled( false);
    	addNew.setContentAreaFilled( false);
    	edit.setContentAreaFilled( false);
    	expand.setContentAreaFilled( false);
    	swapDown.setContentAreaFilled( false);
    	swapUp.setContentAreaFilled( false);
    	
    	
    	remove.setActionCommand( REMOVE);
    	addNew.setActionCommand( ADD_NEW);
    	edit.setActionCommand( DONE);
    	expand.setActionCommand( EXPAND);
    	swapDown.setActionCommand( S_DOWN);
    	swapUp.setActionCommand( S_UP);
    	
    	
    	remove.setToolTipText( "Remove");
    	addNew.setToolTipText( "Add new step under this step");
    	edit.setToolTipText( "Edit the text of this step");
    	expand.setToolTipText( "Expand this step");
    	swapDown.setToolTipText( "Swap down this step");
    	swapUp.setToolTipText( "Swap up text of this step");
    	
    	
    	remove.addActionListener( listener);
    	addNew.addActionListener( listener);
    	edit.addActionListener( listener);
    	expand.addActionListener( listener);
    	swapDown.addActionListener( listener);
    	swapUp.addActionListener( listener);
    	////////////////////////////////////////////////////////////////////////
    	
    	if( node.isRoot() ){
    		remove.setVisible( false);
    	}
    	
    	
    	
    	//ButtonRightPanel
    	buttonRightPanel = new JPanel();
    	buttonRightPanel.setLayout( new BoxLayout( buttonRightPanel, BoxLayout.Y_AXIS));
    	buttonRightPanel.add( remove);
    	buttonRightPanel.add( addNew);
    	buttonRightPanel.add( expand);
    	buttonRightPanel.setOpaque( false);
    	
    	//ButtonPanel
    	buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.X_AXIS));
    	buttonPanel.add( edit);
    	buttonPanel.add( buttonRightPanel);
    	buttonPanel.setOpaque( false);
    	
    	//SwapButtonPanel
    	swapButtonPanel.add( swapUp);
    	swapButtonPanel.add( swapDown);
    	swapButtonPanel.setOpaque( false);
    	
    	
    	//SouthPanel
    	southPanel = new JPanel();
    	southPanel.setOpaque( false);
    	southPanel.setSize( 800, 10);
    	
    	//NorthPanel
    	northPanel = new JPanel();
    	northPanel.setOpaque( false);
    	northPanel.setSize( 800, 20);
    	if( step instanceof ConditionalStep  ){
    		conditionField = new JTextField( 60);
    		conditionField.setText( ((ConditionalStep) step).getConditionText());
    		conditionField.setEditable( true);
    		conditionField.setActionCommand( DONE );
    		conditionField.addActionListener( listener);
    		
    		conditionalBuffer = new JPanel();
    		conditionalBuffer.setPreferredSize( new Dimension( 10, 40));
    		conditionalBuffer.setOpaque( false);
    		
    		if( step instanceof DecisionStep){
    			conditionalLabel = new JLabel( "IF");
    			typeIcon = new ImageIcon( "Images/StepTypeIcons/Decision.png");
    		}
    		else{
    			conditionalLabel = new JLabel( "WHILE");
    			typeIcon = new ImageIcon( "Images/StepTypeIcons/Repetition.png");
    		}
    		
    		northPanel.add( conditionalBuffer);
    		northPanel.add( conditionalLabel);
    		northPanel.add( conditionField);
    	}
    	
    	//TypeButton
    	typeButton = new JButton( typeIcon);
    	typeButton.setBorderPainted( false);
    	typeButton.setContentAreaFilled(false);
    	typeButton.setActionCommand( TYPE);
    	typeButton.setToolTipText( "Change the type of this step");
    	typeButton.addActionListener( listener);
    	
    	FlowLayout fl = new FlowLayout();
    	fl.setAlignment( FlowLayout.LEFT );
    	
    	centerPanel = new JPanel();
    	centerPanel.setOpaque( false);
    	centerPanel.setBorder( BorderFactory.createLineBorder( Color.BLACK, 3, true));
    	centerPanel.setLayout( new BoxLayout( this, BoxLayout.X_AXIS));
    	centerPanel.setLayout( fl);
    	//centerPanel.setPreferredSize( new Dimension( 850, 80));
    	centerPanel.add( swapButtonPanel);
    	
    	centerPanel.add( textAreaPane);
    	centerPanel.add( buttonPanel );
    	
    	FlowLayout lay = new FlowLayout();
    	lay.setAlignment( FlowLayout.CENTER );
    	
    	westPanel = new JPanel();
    	westPanel.setOpaque( false);
    	westPanel.setSize( 20, 100);
    	westPanel.setLayout( new BoxLayout( westPanel, BoxLayout.Y_AXIS ));
    	westPanel.add( numberLabel);
    	westPanel.add( Box.createRigidArea( new Dimension( 10, 10)));
    	westPanel.add( typeButton);
    	westPanel.add( Box.createRigidArea( new Dimension( 10, 10)));
    	westPanel.setAlignmentY( CENTER_ALIGNMENT);
    	
    	
    	setOpaque( false);
    	setLayout( new BorderLayout() );
    	add( westPanel, BorderLayout.WEST );
    	add( northPanel, BorderLayout.NORTH );
    	add( southPanel, BorderLayout.SOUTH );
    	add( centerPanel);
    	
    	//setPreferredSize( new Dimension( 200, 800 ));
    	setMaximumSize( getPreferredSize() );

	}
	

	
	private class PCDStepEditorButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			
			if( command.equals( REMOVE))
			{
				DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
				if( !node.isRoot())
				{
					if( !(((PCDTreeNode) node.getParent()).getUserObject() instanceof DecisionStep) || node.getParent().getChildCount() > 1 ){
						model.removeNodeFromParent(node);
					}
					else{
						JOptionPane.showMessageDialog( null, "You can't remove the if-true part of a decision step!\nInstead, please remove the decision step itself.", "Cannot Remove", JOptionPane.WARNING_MESSAGE );
					}
					
				}
				
				((PseudoCodeStepTree)tree).getProject().manageIDs();
				
			}
			else if( command.equals( ADD_NEW ))
			{
				if( !node.isRoot() )
				{
					PCDTreeNode parent = (PCDTreeNode)node.getParent();
					int index = parent.getIndex( node) + 1;
					parent.addChild( new PCDTreeNode( new Step(), (((PseudoCodeStepTree)tree).getProject()) ), index);
					
				}
				else{
					((PCDTreeNode)node).addChild( new PCDTreeNode( new Step(), (((PseudoCodeStepTree)tree).getProject()) ));
				}
				
				DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
				((PseudoCodeStepTree)tree).getProject().manageIDs();
				
				Project.reloadModel(model, tree);
			}
			else if( command.equals( EDIT ))
			{
				textArea.setEditable( true);
				edit.setIcon( doneIcon);
				edit.setActionCommand( DONE);
				if( step instanceof ConditionalStep  ){
					conditionField.setEditable( true);
				}
			}
			else if( command.equals( DONE ))
			{
				if( step instanceof ConditionalStep  ){
					((ConditionalStep) step).setConditionText( conditionField.getText() );
					conditionField.setFont( new Font( "Berlin Sans FB", Font.ITALIC, Project.fontSize + 5 ));
					step.statement = textArea.getText();
					conditionField.setEditable( false);
				}
				else if( textArea.getText().equalsIgnoreCase( "if")){
					node.setUserObject( new DecisionStep());
					node.add( new PCDTreeNode( new Step(), ((PseudoCodeStepTree)tree).getProject() ) );
					node.add( new PCDTreeNode( new Step(), ((PseudoCodeStepTree)tree).getProject() ) );
					((PseudoCodeStepTree)tree).getProject().manageIDs();
					Project.reloadModel( (DefaultTreeModel)tree.getModel() , tree);
				}
				
				else if( textArea.getText().equalsIgnoreCase( "while") ){
					node.setUserObject( new RepetitionStep());
					((PseudoCodeStepTree)tree).getProject().manageIDs();
					Project.reloadModel( (DefaultTreeModel)tree.getModel() , tree);
				}
				else{
					step.statement = textArea.getText();
				}
				textArea.setEditable( false);
				edit.setIcon( editIcon);
				edit.setActionCommand( EDIT);
				
				//TODO bunu font büyüklüğünü falan da hesaba katarak geliştir
				
			}
			else if( command.equals(EXPAND)){
				((PCDTreeNode)node).addChild( new PCDTreeNode( new Step(), (((PseudoCodeStepTree)tree).getProject()) ));
				DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
				((PseudoCodeStepTree)tree).getProject().manageIDs();
				
				Project.reloadModel(model, tree);
			}
			else if( command.equals( S_UP ))
			{
				DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
				if( !node.isRoot() ){
					PCDTreeNode parent = (PCDTreeNode) node.getParent();
					int index = parent.getIndex( node);
					
					if( index > 0)
					{
						PCDTreeNode previous = (PCDTreeNode) parent.getChildAt(  index - 1 ) ;
						model.removeNodeFromParent( previous);
						parent.insert( previous, index);
						((PseudoCodeStepTree)tree).getProject().manageIDs();
						
						Project.reloadModel(model, tree);
					}
				}
			}
			else if( command.equals( S_DOWN ))
			{
				DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
				if( !node.isRoot() ){
					PCDTreeNode parent = (PCDTreeNode) node.getParent();
					int index = parent.getIndex( node);
					
					if( index < parent.getChildCount() - 1 )
					{
						PCDTreeNode next = (PCDTreeNode) parent.getChildAt(  index + 1 ) ;
						model.removeNodeFromParent( node);
						parent.insert( node, index + 1);
						((PseudoCodeStepTree)tree).getProject().manageIDs();
						
						Project.reloadModel(model, tree);
					}
				}
			}
			else if( command.equals( TYPE ))
			{
				JPanel panel = new JPanel();
				JLabel label = new JLabel( "Please select a type for this step:");
				Vector<String> vector = new Vector<String>();
				vector.add( Step.ASSIGNMENT);
				vector.add( Step.GENERAL);
				vector.add( Step.INPUT);
				vector.add( Step.OUTPUT);
				
				JComboBox<String> jCombo = new JComboBox<String>( vector);
				panel.add( label);
				panel.add( jCombo);
				int choice = JOptionPane.showConfirmDialog( null, panel, "Change Type", JOptionPane.DEFAULT_OPTION);
				
				if( choice == JOptionPane.OK_OPTION)
				{
					step.setType( (String) jCombo.getSelectedItem());
					Project.reloadModel((DefaultTreeModel) tree.getModel(), tree);
				}
			}	
		}	
	}
	
	private class PCDKeyListener implements KeyListener{

		private boolean shift = false;

		@Override
		public void keyPressed(KeyEvent e) {
			if( textArea.isEditable() ){
				if( e.getKeyCode() == ( KeyEvent.VK_SHIFT )){
					shift  = true;
				}
				else if( e.getKeyCode() == ( KeyEvent.VK_ENTER ) && !shift ){
					edit.doClick();
				}
				else if( e.getKeyCode() == ( KeyEvent.VK_ENTER ) && shift ){
					textArea.setText( textArea.getText() + "\n" );
				}
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if( e.getKeyCode() == ( KeyEvent.VK_SHIFT )){
				shift  = false;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {}
		
	}
}
