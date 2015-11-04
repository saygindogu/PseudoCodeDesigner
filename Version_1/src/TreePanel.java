/**
 * 
 * TreePanel.java  the overview panel that presents
 * 				   the steps as a tree model.
 * 
 * @author G1B R2D2 - Bengisu Batmaz
 * 
 */

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.beans.*;

class TreePanel extends JPanel implements PropertyChangeListener{

	private JTree 					tree;
	private StatementBoxList list;
	DefaultTreeModel 		treeModel;
	DefaultMutableTreeNode parent;
	
	public TreePanel( StatementBoxList list){
		// The tree look
		super(new GridLayout(1,0));
		
		this.list = list;
		
		// the base file and model for the tree panel.
		parent = new DefaultMutableTreeNode("Overview");
		treeModel = new DefaultTreeModel(parent);
		tree = new JTree(treeModel);
		
		
		// single selection at a time
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(false);
		
		createNodes(parent, list, 1);
	    Dimension minimumSize = new Dimension(100, 300);
	    
	    
	    tree.setSize( minimumSize);
        
        treeModel.addTreeModelListener( new TreeModelListener(){
        	@Override
			public void treeNodesChanged(TreeModelEvent e) {
		        DefaultMutableTreeNode node;
		        node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());

		        int index = e.getChildIndices()[0];
		        node = (DefaultMutableTreeNode)(node.getChildAt(index));
		        treeModel.reload();
		    }
        	@Override
		    public void treeNodesInserted(TreeModelEvent e) {}
        	@Override
		    public void treeNodesRemoved(TreeModelEvent e) {}
        	@Override
		    public void treeStructureChanged(TreeModelEvent e) {
        		treeModel.reload();
        	}
		});
        
        add( tree);
        	
       list.addPropertyChangeListener(this);
		
	}
	
	// Creates the nodes. Here its taken as String as an example 
	void createNodes( DefaultMutableTreeNode parent, StatementBoxList list, int deep){
		DefaultMutableTreeNode steps = null;
		
		if( deep == 1)
		{
			steps = new DefaultMutableTreeNode( "Problem Description");
			parent.add(steps);
		}
		
		for( int i = 0; i < list.size(); i++){
			if( list.getDepth() == deep ){
				steps = new DefaultMutableTreeNode( "Step "+ list.get(i).getStringID());
				parent.add(steps);
			}
			
			if( list.get( i).checkExpanded() ){
				
					createNodes( steps, list.get(i).getSubBoxes() , list.get(i).getSubBoxes().getDepth() );
				}
			
			
			
		}
	}
	
	
	
	void removeCurrentNode(){
		TreePath selection = tree.getSelectionPath();
		if (selection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (selection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
            }
        }
		Toolkit.getDefaultToolkit().beep();
	}
	
	public void propertyChange( PropertyChangeEvent e){
		removeAll();
		parent = new DefaultMutableTreeNode("Overview");
		treeModel = new DefaultTreeModel(parent);
		createNodes(parent, list, 1);
		tree = new JTree( treeModel);
		add( tree);
		repaint();
		revalidate();
	}
	 
   

}