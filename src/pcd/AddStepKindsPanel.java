package pcd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.DefaultTreeModel;

import pcd.step.DecisionStep;
import pcd.step.PCDTreeNode;
import pcd.step.RepetitionStep;
import pcd.step.Step;

public class AddStepKindsPanel extends JPanel {

	private final static String DECISION = "decisionStep";
	private final static String REPETITION = "repStep";
	
	private JButton addGeneralStep;
	private JButton addInputStep;
	private JButton addOutputStep;
	private JButton addAssignmentStep;
	private JButton addRepetitionStep;
	private JButton addDecisionStep;
	
	private JLabel label;
	
	private Project project;
	private ActionListener listener;
	
	public AddStepKindsPanel( Project project, int direction){
		this.project = project;
		listener = new AddStepButtonsListener();
		label = new JLabel( "Add Steps ");
		label.setFont( new Font( "Berlin Sans FB", Font.PLAIN, 30 ));
		label.setOpaque( true);
		label.setBackground( new Color( 60,156,186) );
		//label.setAlignmentX(0.5f);
		
		setLayout( new BoxLayout( this, direction ));

		addGeneralStep = new JButton( "General Step");
		addInputStep = new JButton( "Input step");
		addOutputStep = new JButton( "Output step");
		addAssignmentStep = new JButton( "Assignment step");
		addRepetitionStep = new JButton( "Repetition step");
		addDecisionStep = new JButton( "Decision step");
		
		addGeneralStep.addActionListener( listener);
		addInputStep.addActionListener( listener);
		addOutputStep.addActionListener( listener);
		addAssignmentStep.addActionListener( listener);
		addRepetitionStep.addActionListener( listener);
		addDecisionStep.addActionListener( listener);
		
		addGeneralStep.setActionCommand( Step.GENERAL);
		addInputStep.setActionCommand( Step.INPUT);
		addOutputStep.setActionCommand( Step.OUTPUT);
		addAssignmentStep.setActionCommand( Step.ASSIGNMENT);
		addRepetitionStep.setActionCommand( REPETITION);
		addDecisionStep.setActionCommand( DECISION);
	
//		addGeneralStep.setAlignmentX(0.5f);
//		addInputStep.setAlignmentX(0.5f);
//		addOutputStep.setAlignmentX(0.5f);
//		addAssignmentStep.setAlignmentX(0.5f);
//		addRepetitionStep.setAlignmentX(0.5f);
//		addDecisionStep.setAlignmentX(0.5f);
		
		addGeneralStep.setPreferredSize( new Dimension( 250, 30) );
		addInputStep.setMinimumSize( new Dimension( 150, 30) );
		addOutputStep.setMinimumSize( new Dimension( 150, 30) );
		addAssignmentStep.setMinimumSize( new Dimension( 150, 30) );
		addRepetitionStep.setMinimumSize( new Dimension( 150, 30) );
		addDecisionStep.setMinimumSize( new Dimension( 150, 30) );
		
		add( label);
		add( addGeneralStep );
		add( addInputStep );
		add( addOutputStep );
		add( addAssignmentStep );
		add( addRepetitionStep );
		add( addDecisionStep );
		
		//setPreferredSize( new Dimension( 100, 100));
	}
	
	private class AddStepButtonsListener implements ActionListener{
	
		@Override
		public void actionPerformed(ActionEvent e) {
			if( e.getActionCommand().equals( DECISION )){
				DefaultTreeModel model = (DefaultTreeModel) project.getSteps() ;
				
				PCDTreeNode selected = project.findSelected ( (PCDTreeNode) model.getRoot() ) ;
				if( selected == null){
					((PCDTreeNode)((DefaultTreeModel)project.getTree().getModel()).getRoot()).addChild( new PCDTreeNode( new DecisionStep() , project));
				}
				else if( selected.isRoot() ){
			    	selected.addChild( new PCDTreeNode( new Step(), selected.getProject() ) );
			    }
			    else{
			    	PCDTreeNode parent = ((PCDTreeNode)selected.getParent() );
			    	parent.insert( new PCDTreeNode( new DecisionStep(), project), parent.getIndex (selected) + 1);
			    }
			    Project.reloadModel( model, project.getTree());
			    project.manageIDs();
			}
			else if( e.getActionCommand().equals( REPETITION )){
				DefaultTreeModel model = (DefaultTreeModel) project.getSteps() ;
				
				PCDTreeNode selected = project.findSelected ( (PCDTreeNode) model.getRoot() ) ;
				if( selected == null){
					((PCDTreeNode)((DefaultTreeModel)project.getTree().getModel()).getRoot()).addChild( new PCDTreeNode( new RepetitionStep() , project));
				}
				else if( selected.isRoot() ){
			    	selected.addChild( new PCDTreeNode( new Step(), selected.getProject() ) );
			    }
			    else{
			    	PCDTreeNode parent = ((PCDTreeNode)selected.getParent() );
			    	parent.insert( new PCDTreeNode( new RepetitionStep(), project), parent.getIndex (selected) + 1);
			    }
			    Project.reloadModel( model, project.getTree());
			    project.manageIDs();
			}
			else
			{	
				DefaultTreeModel model = (DefaultTreeModel) project.getSteps() ;
				
				PCDTreeNode selected = project.findSelected ( (PCDTreeNode) model.getRoot() ) ;
				if( selected == null){
					((PCDTreeNode)((DefaultTreeModel)project.getTree().getModel()).getRoot()).addChild( new PCDTreeNode( new Step( "", e.getActionCommand() ) , project));
				}
				else if( selected.isRoot() ){
			    	selected.addChild( new PCDTreeNode( new Step( "", e.getActionCommand() ), selected.getProject() ) );
			    }
			    else{
			    	PCDTreeNode parent = ((PCDTreeNode)selected.getParent() );
			    	parent.insert( new PCDTreeNode( new Step( "", e.getActionCommand() ), project), parent.getIndex (selected) + 1);
			    }
			    Project.reloadModel( model, project.getTree());
			    project.manageIDs();
			}
			
			PCDTreeNode node = (PCDTreeNode) project.getTree().getModel().getRoot();
			((Step) node.getUserObject()).setSelected( false);
			while( node.getNextNode() != null){
				node = (PCDTreeNode) node.getNextNode();
				((Step) node.getUserObject()).setSelected( false);
			}
			if (project.getTree().getSelectionPath() != null){
				((Step) ((PCDTreeNode) project.getTree().getSelectionPath().getLastPathComponent()).getUserObject()).setSelected( true);
			}
		}
	}
	
}
