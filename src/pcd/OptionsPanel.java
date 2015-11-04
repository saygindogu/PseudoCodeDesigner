package pcd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTree;

public class OptionsPanel extends JPanel {

	private OptionsGUI	options;
	
	public OptionsPanel(Project project, JTree tree, PseudoCodeDesigner pcd, Color color){
		super();

		options = new OptionsGUI( project, tree, pcd, color);
		
		setBackground( color);
		setLayout( new BorderLayout() );
		add( options.panel);
		setPreferredSize( new Dimension( 800, 100));
	}
}
