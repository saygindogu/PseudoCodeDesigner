package pcd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import pcd.step.ConditionalStep;
import pcd.step.DecisionStep;
import pcd.step.PCDTreeNode;
import pcd.step.PseudoCodeStepTree;
import pcd.step.RepetitionStep;
import pcd.step.Step;

public class RunThePseudoCode extends JFrame {
	
	public static final String NEXT 			= "Next";
	public static final String SIMPLE_NEXT 		= "Simple Next";
	public static final String PAUSE 			= "Pause";
	public static final String PLAY 			= "Play";
	public static final String STOP 			= "Stop" ;
	
	private Project project;
	private JButton nextButton, stopButton, playButton, simpleNextButton;
	private ActionListener listener;
	private boolean stopped;
	
	private PCDTreeNode currentStep;
	private PCDTreeNode currentAnchor;
	private PCDTreeNode currentStopStep;
	private Stack<PCDTreeNode> stack;
	private JScrollPane scrollPane;
	private JTree tree;
	private Timer timer;
	private JPanel treePanel, buttonPanel;
	Toolkit tk;

	public RunThePseudoCode( Project project){
		super();
		
		this.project = new Project ( new DefaultTreeModel ( ((PCDTreeNode)project.getSteps().getRoot()).copy() ) );
		tree = new PseudoCodeStepTree( this.project);
		treePanel = new JPanel();
		buttonPanel = new JPanel();
		tree.setEditable( false);
		stopped = false;
		
		treePanel.setLayout(new BorderLayout());
		
		listener = new RPCActionListener();
		stack = new Stack<PCDTreeNode>();
		scrollPane = new JScrollPane( treePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setAutoscrolls( true);
		scrollPane.getVerticalScrollBar().setUnitIncrement( 10);
	
		timer = new Timer( 1500, listener );
		timer.setActionCommand( NEXT );
		
		nextButton = new JButton( "Next");
		nextButton.setActionCommand( NEXT);
		nextButton.addActionListener( listener);
		
		stopButton = new JButton( "Stop");
		stopButton.setActionCommand( STOP);
		stopButton.addActionListener( listener);
		
		simpleNextButton = new JButton( "SimpleNext" );
		simpleNextButton.setActionCommand( SIMPLE_NEXT);
		simpleNextButton.addActionListener( listener);
		
		playButton = new JButton( "Play" );
		playButton.setActionCommand( PLAY );
		playButton.addActionListener( listener);
		
		getContentPane().setLayout( new BoxLayout( getContentPane(), BoxLayout.Y_AXIS ) );
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		scrollPane.setPreferredSize( new Dimension( (int) (toolkit .getScreenSize().getWidth() * 0.70f) , (int) (toolkit.getScreenSize().getHeight() * 0.70f) ));
		buttonPanel.add( stopButton );
		buttonPanel.add( playButton );
		buttonPanel.add( simpleNextButton );
		buttonPanel.add( nextButton );
		treePanel.add( tree);
		getContentPane().add( scrollPane);
		getContentPane().add( buttonPanel );
	
		setExtendedState( getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		addWindowListener( new RPCWindowListener() );
		init();
		setVisible( true);
	}
	
	private void init() {
		stop();
		PCDTreeNode root = (PCDTreeNode) project.getSteps().getRoot();
		
		((Step) root.getUserObject()).setIsCurrentStep( true);
		currentStep = root;
		stopped = false;
		repaint();	
	}

	private void stop(){
		PCDTreeNode root = (PCDTreeNode) project.getSteps().getRoot();
		PCDTreeNode node = root;
		
		while( node.getNextNode() != null){
			((Step) node.getUserObject()).setIsCurrentStep( false);
			node = (PCDTreeNode) node.getNextNode();
		}
		
		playButton.setText( "Play");
		stopped = true;
	}
	
	private void simpleNext() {
		if( currentStep.isRoot() ){
			next();
		}
		else{
			((Step) currentStep.getUserObject()).setIsCurrentStep( false);
			
			if( currentStep.getNextSibling() != null )
			{
				currentStep = (PCDTreeNode) currentStep.getNextSibling();
				((Step) currentStep.getUserObject()).setIsCurrentStep( true);
				System.out.println( ((Step) currentStep.getUserObject()).getID().toString() );
				//tree.setSelectionPath( new TreePath(currentStep.getPath()) );
				repaint();
				
				if( stack.isEmpty() ){
					
					if( currentStep.getUserObject() instanceof ConditionalStep ){
						handleConditionalSteps();
					}
					
				}
				else // Stack is not empty
				{
					if( currentStopStep != null){
						if( currentStep == currentStopStep){
							((Step) currentStep.getUserObject()).setIsCurrentStep( false);
							if( currentAnchor.getUserObject() instanceof DecisionStep){
								stack.pop();
								System.out.println( "Stack pop!");
								if( currentAnchor.getNextSibling() != null )
								{
									currentStep = (PCDTreeNode) currentAnchor.getNextSibling();
									((Step) currentStep.getUserObject()).setIsCurrentStep( true);
									System.out.println( ((Step) currentStep.getUserObject()).getID().toString() );
									//tree.setSelectionPath( new TreePath(currentStep.getPath()) );
									repaint();
								}
								currentAnchor = null;
								reArrangeTheCurrentStopStep();
							}
							else// anchor is repetition step
							{
								currentStep = (PCDTreeNode) currentAnchor;
								((Step) currentStep.getUserObject()).setIsCurrentStep(true);
								System.out.println( ((Step) currentStep.getUserObject()).getID().toString() );
								//tree.setSelectionPath( new TreePath(currentStep.getPath()) );
								repaint();
							}
							
							if( !stack.isEmpty() ){
								currentAnchor = stack.peek();
								
								if( currentAnchor.getUserObject() instanceof DecisionStep  ){
									if( currentAnchor.getChildCount() > 1 ){
										currentStopStep = (PCDTreeNode) currentAnchor.getChildAt( 1);
									}
									else{//has no else part
										currentStopStep = (PCDTreeNode) currentAnchor.getNextSibling();
									}
								}
								else// repetitionStep
								{
									currentStopStep = (PCDTreeNode) currentAnchor.getNextSibling();
								}
							}
							
							if( currentStep.getUserObject() instanceof ConditionalStep ){
								handleConditionalSteps();
							}
						}
					}
					else// stop step does not exist, it's because there is no such step...
					{
						((Step) currentStep.getUserObject()).setIsCurrentStep( false);
						if( !(currentAnchor.getUserObject() instanceof RepetitionStep) ){
							stop();
							return;
						}
						else{
							currentStep = currentAnchor;
							((Step) currentStep.getUserObject()).setIsCurrentStep( true);
							System.out.println( ((Step) currentStep.getUserObject()).getID().toString() );
							//tree.setSelectionPath( new TreePath(currentStep.getPath()) );
							repaint();
							
							
						}
					}
				}
				if( currentStep.getUserObject() instanceof ConditionalStep ){
					handleConditionalSteps();
				}
			}
			else if( !stack.isEmpty() && currentAnchor.getUserObject() instanceof RepetitionStep ){
				((Step) currentAnchor.getUserObject()).setIsCurrentStep( false);
				currentStep = currentAnchor;
				((Step) currentAnchor.getUserObject()).setIsCurrentStep( true);
				System.out.println( ((Step) currentStep.getUserObject()).getID().toString() );
				//tree.setSelectionPath( new TreePath(currentStep.getPath()) );
				repaint();
				handleConditionalSteps();
			}
			else{
				next();
			}
		}
	}

	private void next() {
		((Step) currentStep.getUserObject()).setIsCurrentStep( false);
		
		if( currentStep.getNextNode() != null )
		{
			currentStep = (PCDTreeNode) currentStep.getNextNode();
			((Step) currentStep.getUserObject()).setIsCurrentStep( true);
			System.out.println( ((Step) currentStep.getUserObject()).getID().toString() );
			//tree.setSelectionPath( new TreePath(currentStep.getPath()) );
			repaint();
			
			if( stack.isEmpty() ){
				
				if( currentStep.getUserObject() instanceof ConditionalStep ){
					handleConditionalSteps();
				}
				
			}
			else // Stack is not empty
			{
				if( currentStopStep != null){
					if( currentStep == currentStopStep){
						((Step) currentStep.getUserObject()).setIsCurrentStep( false);
						if( currentAnchor.getUserObject() instanceof DecisionStep){
							stack.pop();
							System.out.println( "Stack pop!");
							if( currentAnchor.getNextSibling() != null )
							{
								currentStep = (PCDTreeNode) currentAnchor.getNextSibling();
								((Step) currentStep.getUserObject()).setIsCurrentStep( true);
								System.out.println( ((Step) currentStep.getUserObject()).getID().toString() );
								//tree.setSelectionPath( new TreePath(currentStep.getPath()) );
								repaint();
							}
							else{
								stop();
								return;
							}
							currentAnchor = null;
							reArrangeTheCurrentStopStep();
						}
						else// anchor is repetition step
						{
							currentStep = (PCDTreeNode) currentAnchor;
							((Step) currentStep.getUserObject()).setIsCurrentStep(true);
							System.out.println( ((Step) currentStep.getUserObject()).getID().toString() );
							//tree.setSelectionPath( new TreePath(currentStep.getPath()) );
							repaint();
						}
						
						if( !stack.isEmpty() ){
							currentAnchor = stack.peek();
							
							if( currentAnchor.getUserObject() instanceof DecisionStep  ){
								if( currentAnchor.getChildCount() > 1 ){
									currentStopStep = (PCDTreeNode) currentAnchor.getChildAt( 1);
								}
								else{//has no else part
									currentStopStep = (PCDTreeNode) currentAnchor.getNextSibling();
								}
							}
							else// repetitionStep
							{
								currentStopStep = (PCDTreeNode) currentAnchor.getNextSibling();
							}
						}
						
						if( currentStep.getUserObject() instanceof ConditionalStep ){
							handleConditionalSteps();
						}
					}
				}
				else// stop step does not exist, it's because there is no such step, i.e, the algorithm ends after this anchor..
				{
					((Step) currentStep.getUserObject()).setIsCurrentStep( false);
					if( !(currentAnchor.getUserObject() instanceof RepetitionStep) ){
						stop();
					}
					else{
						currentStep = currentAnchor;
						((Step) currentStep.getUserObject()).setIsCurrentStep( true);
						System.out.println( ((Step) currentStep.getUserObject()).getID().toString() );
						//tree.setSelectionPath( new TreePath(currentStep.getPath()) );
						if( currentStep.getNextNode() == null ){// lonely repetition step, can't exit this loop, so warn user!!
							int userChoice = JOptionPane.showConfirmDialog( RunThePseudoCode.this, 
									 
									"WARNING!\nBecause this loop does not contain any substeps, " +
									"RPC will not understand the algorithm is finished even if this condition is false. " +
									"Do you want RPC to stop now?", "A loop at the end of the algorithm!",
									JOptionPane.YES_NO_OPTION );
							if( userChoice == JOptionPane.YES_OPTION ){
								stop();
								return;
							}
						}
						repaint();
					}
				}
			}
			if( currentStep.getUserObject() instanceof ConditionalStep ){
				handleConditionalSteps();
			}
		}
		//reached the end of the algorithm...
		//algorithm may continue...
		else if( !stack.isEmpty() && currentAnchor.getUserObject() instanceof RepetitionStep ){
			((Step) currentAnchor.getUserObject()).setIsCurrentStep( false);
			currentStep = currentAnchor;
			((Step) currentAnchor.getUserObject()).setIsCurrentStep( true);
			System.out.println( ((Step) currentStep.getUserObject()).getID().toString() );
			//tree.setSelectionPath( new TreePath(currentStep.getPath()) );
			
			if( currentStep.getNextNode() == null ){// lonely repetition step, can't exit this loop, so warn user!!
				int userChoice = JOptionPane.showConfirmDialog( RunThePseudoCode.this, 
						 
						"WARNING!\nBecause this loop does not contain any substeps, " +
						"RPC will not understand the algorithm is finished even if this condition is false. " +
						"Do you want RPC to stop now?", "A loop at the end of the algorithm!",
						JOptionPane.YES_NO_OPTION );
				if( userChoice == JOptionPane.YES_OPTION ){
					stop();
					return;
				}
			}
			
			repaint();
			handleConditionalSteps();
		}
		else{
			stop();
		}
	}
	
	private void handleConditionalSteps() {
		System.out.println( "Conditional..");
		ConditionalStep conditional = (ConditionalStep) currentStep.getUserObject();
		int choice = JOptionPane.showConfirmDialog( RunThePseudoCode.this,
										"Is this condition true?\n" + conditional.getConditionText(),
										"Condition detected!" , 
										JOptionPane.YES_NO_OPTION );
		((Step) currentStep.getUserObject()).setIsCurrentStep( false);
		
		if( choice == JOptionPane.YES_OPTION ){
			conditional.setConditionValue( true);
		}
		else if( choice == JOptionPane.NO_OPTION )
		{
			conditional.setConditionValue( false);
		}
		
		
		if( conditional.getConditionValue() )
		{
			if( stack.isEmpty() || stack.peek() != currentStep )
			{
				stack.push( currentStep);
				System.out.println( "stack push!");
			}
			currentAnchor = currentStep;
			
			if( conditional instanceof DecisionStep ){
				if( currentStep.getChildCount() > 1 ){
					currentStopStep = (PCDTreeNode) currentAnchor.getChildAt( 1);
				}
				else// decision has no else part
				{
					currentStopStep = (PCDTreeNode) currentAnchor.getNextSibling();
				}
				currentStep = (PCDTreeNode) currentStep.getChildAt( 0);
			}
			else// conditional is a RepetitionStep
			{
				if( currentAnchor.getNextSibling() != null )
				{
					currentStopStep = (PCDTreeNode) currentAnchor.getNextSibling();
				}
				else{
					currentStopStep = null;
				}
				
				
				if( currentStep.getChildCount() > 0 )
				{
					currentStep = (PCDTreeNode) currentStep.getNextNode();
				}
				//else current step won't be changed but this causes an infinite loop! so ask user what to do..
				int userChoice = JOptionPane.showConfirmDialog( RunThePseudoCode.this, 
						 
						"WARNING!\nBecause this loop does not contain any substeps, " +
						"RPC will not understand the algorithm is finished even if this condition is false. " +
						"Do you want RPC to stop now?", "A loop at the end of the algorithm!",
						JOptionPane.YES_NO_OPTION );
				if( userChoice == JOptionPane.YES_OPTION ){
					stop();
					return;
				}
			}
		}
		else // condition is not true
		{
			if( conditional instanceof DecisionStep ){
				if( currentStep.getChildCount() > 1 ){// has else part
					currentStep = (PCDTreeNode) currentStep.getChildAt( 1);
				}
				else//Decision has no else part
				{
					if( currentStep.getNextSibling() != null )
					{
						currentStep = (PCDTreeNode) currentStep.getNextSibling();
					}
					else{//reached the end of the algorithm
						stop();
					}
				}
			}
			else // conditional is a Repetition
			{
				if( !stack.isEmpty() ) //if it's not the first turn
				{
					stack.pop();
					currentAnchor = null;
					System.out.println( "Stack pop!");
				}
				
				reArrangeTheCurrentStopStep();
				
				//REMINDER: current step was a repetition step and user said that the condition is false..
				if( currentStep.getNextSibling() != null)
				{
					currentStep = (PCDTreeNode) currentStep.getNextSibling();
				}
				else{
					//end of the algorithm
					stop();
					return;
				}
			}
		}
		((Step) currentStep.getUserObject()).setIsCurrentStep( true);
		System.out.println( ((Step) currentStep.getUserObject()).getID().toString() );
		//tree.setSelectionPath( new TreePath(currentStep.getPath()) );
		repaint();
		
		if( currentStep.getUserObject() instanceof ConditionalStep ){
			handleConditionalSteps();
		}
	}

	private void reArrangeTheCurrentStopStep() {
		if( !stack.isEmpty() ){// re arrange the current stop step
			currentAnchor = stack.peek();
			
			if( currentAnchor.getUserObject() instanceof DecisionStep  ){
				if( currentAnchor.getChildCount() > 1 ){//has else part
					currentStopStep = (PCDTreeNode) currentAnchor.getChildAt( 1);
				}
				else{//has no else part
					currentStopStep = (PCDTreeNode) currentAnchor.getNextSibling();
					//can be null..
				}
			}
			else// repetitionStep
			{
				currentStopStep = (PCDTreeNode) currentAnchor.getNextSibling();
				//can be null..
			}
		}
		
	}

	private class RPCWindowListener extends WindowAdapter{

		@Override
		public void windowClosed(WindowEvent arg0) {
			stop();
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			if( playButton.getActionCommand().equals( PAUSE )){
				playButton.doClick();
			}
			stop();
			dispose();
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			if( playButton.getActionCommand().equals( PAUSE )){
				playButton.doClick();
			}	
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			if( playButton.getActionCommand().equals( PAUSE )){
				playButton.doClick();
			}
		}
		
	}
	
	private class RPCActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
				
			if( e.getActionCommand().equals( NEXT )){
				if( !stopped )
				{
					RunThePseudoCode.this.next();
				}
				else{
					init();
					RunThePseudoCode.this.next();
				}
				
			}
			else if( e.getActionCommand().equals( STOP )){
				stop();
				if( playButton.getActionCommand().equals( PAUSE ) ){
					timer.stop();
					playButton.setActionCommand( PLAY);
					playButton.setText( "Play");
				}
			}
			else if( e.getActionCommand().equals( PLAY )){
				if( stopped){
					stopped = false;
					init();
				}
				playButton.setActionCommand( PAUSE );
				playButton.setText( "Pause");
				timer.start();
			}
			else if( e.getActionCommand().equals( SIMPLE_NEXT )){
				if( !stopped )
				{
					RunThePseudoCode.this.simpleNext();
				}
				else{
					init();
					RunThePseudoCode.this.next();
				}
			}
			else if( e.getActionCommand().equals( PAUSE )){
				playButton.setActionCommand( PLAY );
				playButton.setText( "Play");
				timer.stop();
			}
			
			if( stopped){
				int userChoice = JOptionPane.showConfirmDialog( RunThePseudoCode.this, "Reached the end of the algorithm!\nRPC will start from the beggining.", "The End", JOptionPane.OK_CANCEL_OPTION );
				
				if( userChoice != JOptionPane.OK_OPTION ){
					dispose();
				}
			}
			repaint();
			
			
		
		}
		
	}
}
