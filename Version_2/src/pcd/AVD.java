package pcd;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import pcd.step.PseudoCodeStepTree;

public class AVD extends JFrame {

	Project project;
	PseudoCodeStepTree tree;
	JScrollPane scp;
	
	public AVD( Project project){
		super( "AVD - " + project.getName() );
		this.project = project;
		tree = new PseudoCodeStepTree( project);
		scp = new JScrollPane( tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		getContentPane().add( scp);
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( new Dimension( 600, 500));
		setVisible( true);
		
	}
}
