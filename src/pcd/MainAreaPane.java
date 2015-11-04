package pcd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import pcd.step.PseudoCodeStepTree;

public class MainAreaPane extends JPanel {
	
	//PROPERTIES
	private JTree tree;
	private JScrollPane scrollPane;
	private ImageIcon expanded;
	private ImageIcon collapsed;
	
	
	//CONSTRUCTORS
	public MainAreaPane( Project p, boolean newProject ){
		super();
		
		expanded = new ImageIcon( "Images/expand2.png");
		collapsed = new ImageIcon( "Images/expand1.png");
		UIManager.put("Tree.collapsedIcon", collapsed);
	    UIManager.put("Tree.expandedIcon", expanded);		if( newProject)
		{
			tree = p.getTree();
		}
		else{
			tree = new PseudoCodeStepTree(p);
			p.setTree(tree);
		}
		scrollPane = new JScrollPane( tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		
		setLayout( new BorderLayout() );
		add( scrollPane);
		//setPreferredSize( new Dimension( 1500, 500) );
	}
	public MainAreaPane( Project p, boolean newProject, Dimension d){
		
		expanded = new ImageIcon( "Images/expand2.png");
		collapsed = new ImageIcon( "Images/expand1.png");
		UIManager.put("Tree.collapsedIcon", collapsed);
	    UIManager.put("Tree.expandedIcon", expanded);
		
		if( newProject)
		{
			tree = p.getTree();
		}
		else{
			tree = new PseudoCodeStepTree(p);
			p.setTree(tree);
		}
		scrollPane = new JScrollPane( tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		
		setLayout( new BorderLayout() );
		add( scrollPane);
	    
		setPreferredSize( d);
	}
	
	public JTree getTree(){
		return tree;
	}
}