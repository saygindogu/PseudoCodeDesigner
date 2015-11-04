package pcd.step;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;

import pcd.Project;

public class StepRendererPanel extends JPanel 
{
	private Step step;
	private JEditorPane editorPane;
	private JPanel westPanel, centerPanel, topPanel, middlePanel, bottomPanel;
	private JPanel conditionalPanel, conditionalPanelWest;
	private JLabel numberLabel, conditionLabel;
	private ImageIcon typeIcon;
	private JButton typeButton;
	
    public StepRendererPanel( Step step, JTree tree)
    {
    	this.step = step;
    	
    	//Type Icon TODO
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
		else
		{
			typeIcon = new ImageIcon( "Images/StepTypeIcons/General.png");
		}
    	
    	
    	//Number Label  	
    	String labelText = step.stepID.toString();
    	numberLabel = new JLabel( labelText);
    	numberLabel.setFont( new Font( "Lucida Console", Font.BOLD, 20));
    	
    	//Initialize EditorPane
    	if( step.statement.length() > 0)
    	{
    		editorPane = new JEditorPane( "text/html" , step.getHTMLVariablesHighlighted( (byte)Project.fontSize) );
    	}
    	else
    	{
    		editorPane = new JEditorPane();
    	}
    	editorPane.setEditable( false);
    	
    	//TopPanel
    	topPanel = new JPanel();
    	topPanel.setOpaque( false);
    	topPanel.setPreferredSize( new Dimension( 800, 10) );
    	
    	//Center Panel
    	centerPanel = new JPanel();
    	centerPanel.setLayout( new BorderLayout() );
    	centerPanel.setOpaque( false);
    	if( step.isCurrentStep )
    	{
    		centerPanel.setBorder( BorderFactory.createLineBorder( Color.RED, 4, true));
    	}
    	else
    	{
    		centerPanel.setBorder( BorderFactory.createLineBorder( Color.BLACK, 3, true));
    	}
    	//centerPanel.setPreferredSize( new Dimension( 800,  tree.getRowHeight() - 20 ));
    	centerPanel.add( editorPane);
    	
    	//WestPanel
    	westPanel = new JPanel();
    	westPanel.setOpaque( false);
    	westPanel.setAlignmentX( TOP_ALIGNMENT);
    	westPanel.setLayout( new BoxLayout( westPanel, BoxLayout.Y_AXIS));
    	//westPanel.setPreferredSize( new Dimension( 50, 30));
    	
    	//Middle Panel
    	middlePanel = new JPanel();
    	middlePanel.setOpaque( false);
    	middlePanel.setLayout( new BoxLayout( middlePanel, BoxLayout.X_AXIS));
    	middlePanel.add( westPanel);
    	middlePanel.add( centerPanel);
    	
    	//BottomPanel
    	bottomPanel = new JPanel();
    	bottomPanel.setOpaque( false);
    	//bottomPanel.setPreferredSize( new Dimension( 800, 10) );
    	
    	//Layout and Components
    	setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ));
    	setOpaque( false);
    	add( topPanel);
    	
    	if( step instanceof ConditionalStep ){
    		
    		//NumberLabel modifications..
    		if( step instanceof DecisionStep ){
        		numberLabel.setText( numberLabel.getText() + " if" );
        		typeIcon = new ImageIcon( "Images/StepTypeIcons/Decision.png");
        	}
        	else if( step instanceof RepetitionStep ){
        		numberLabel.setText( numberLabel.getText() + " while" );
        		typeIcon = new ImageIcon( "Images/StepTypeIcons/Repetition.png");
        	}
    		
    		//Condition Label
    		conditionLabel = new JLabel();
    		conditionLabel.setFont( new Font( "Berlin Sans FB", Font.ITALIC, Project.fontSize + 5 ));
    		conditionLabel.setText(  "   "  + ((ConditionalStep) step).getConditionText() );
    		conditionLabel.setPreferredSize( new Dimension( 800,  20 + Project.fontSize ));
    		conditionLabel.setOpaque( false);
    		
    		//ConditionalWestPanel
    		conditionalPanelWest = new JPanel();
    		//conditionalPanelWest.setPreferredSize( new Dimension( 50, 30));
    		conditionalPanelWest.add( numberLabel);
    		conditionalPanelWest.setOpaque( false);
    	
    		//Conditional Panel
    		conditionalPanel = new JPanel();
    		conditionalPanel.setLayout( new BoxLayout( conditionalPanel, BoxLayout.X_AXIS ));
    		conditionalPanel.add( conditionalPanelWest);
    		conditionalPanel.add( conditionLabel);
    		conditionalPanel.setOpaque( false);
    		
    		//TypeButton
    		typeButton = new JButton( typeIcon );
        	typeButton.setContentAreaFilled( false);
        	typeButton.setBorderPainted( false);
    		
    		westPanel.add( typeButton );
    		//westPanel.add( Box.createRigidArea( new Dimension( 50, 80)));
    		
    		add( conditionalPanel);
    		add( middlePanel);
    		add( bottomPanel);
	
    	}
    	else{
    		westPanel.add( numberLabel);
    		
    		typeButton = new JButton( typeIcon );
        	typeButton.setContentAreaFilled( false);
        	typeButton.setBorderPainted( false);
        	typeButton.setOpaque( false);
        	
    		westPanel.add( typeButton );
        	add( middlePanel);
        	add( bottomPanel);
    	}
    	
    	
    	
    	//setPreferredSize( new Dimension( 200, 800 ));
    }
    
}
