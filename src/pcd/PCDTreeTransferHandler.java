/**
 * PCDTreeTransferHandler.java
 * 
 * Handles the drag-drop transfer events in the trees in Pseudo Code Designer.
 * 
 * @author Zahit Saygın Doğu
 * @author Kerim Bartu Kömürcü
 */
package pcd;

//TODO DEcision taşınırken substepler nedense silinmiyor sebebini anlamadım 
//ince eleyip sık dokumak lazım sanırım anlamak için

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import pcd.step.DecisionStep;
import pcd.step.PCDTreeNode;
import pcd.step.PseudoCodeStepTree;
import pcd.step.Step;

public class PCDTreeTransferHandler extends TransferHandler {
	
	DataFlavor nodesDataFlavor;
    DataFlavor[] flavors = new DataFlavor[1];
    PCDTreeNode[] nodesToRemove;
    int[] rows;

   
    public PCDTreeTransferHandler(){	
        try {
            String mimeType = DataFlavor.javaJVMLocalObjectMimeType +
                              ";class=\"" +
                pcd.step.PCDTreeNode[].class.getName() + "\"";
            
            nodesDataFlavor = new DataFlavor(mimeType);
            flavors[0] = nodesDataFlavor;
        } catch(ClassNotFoundException e) {
            System.out.println("ClassNotFound: " + e.getMessage());
        }
    }
    
    
    //TODO bu eski hallerinden birinen alınıp getirilmiş bir kopya, sorunu böyle zort diye çözebilrim sandım
    // ama olmadı tabi ki -_- neyse ayrıntılı incelemeye zamanım olmadı bunu inceleye inceleye gidersek yukardaki
    //commente alınmış kısımla karşılaştırarak adam edebiliriz.
    // tek selection olayından vazgeçmek geliyor içimden. not: bakınız PseudoStepTree de şu an tek değil..
    /*
     * (non-Javadoc)
     * @see javax.swing.TransferHandler#canImport(javax.swing.TransferHandler.TransferSupport)
     */
    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        //Check for crucial requirements
    	if(!support.isDrop()) {
            return false;
        }
        support.setShowDropLocation(true);
        //If is not drop, cause illegal state exception
        
        if(!support.isDataFlavorSupported( nodesDataFlavor)) {
            return false;
        }
        
        if( !(support.getComponent() instanceof JTree)){
        	return false;
        }
        
        //Make some initilizations
        JTree.DropLocation dropLocation =
                (JTree.DropLocation)support.getDropLocation();
        JTree tree = (JTree)support.getComponent();
        int dropRow = tree.getRowForPath( dropLocation.getPath());
        DefaultMutableTreeNode[] nodesToMove = new DefaultMutableTreeNode[ rows.length];
        DefaultMutableTreeNode[] nextSiblings = new DefaultMutableTreeNode[ rows.length];
        for( int i = 0; i < rows.length; i++){
        	nodesToMove[i] =(DefaultMutableTreeNode) tree.getPathForRow(rows[i]).getLastPathComponent();
        }
        for( int i = 0; i < rows.length; i++){
        	nextSiblings[i] =(DefaultMutableTreeNode) nodesToMove[i].getNextSibling();
        }
        
        int[] targetRowLimits = new int[rows.length];
        for( int i : targetRowLimits ){
        	i = 0;
        }
        
        // Do not allow a drop on the drag source selections.
        for( int row : rows)
        {
        	if( dropRow == row){
        		return false;
        	}
        }
        
        //Do not allow a drop on a node's own child
        for( int i = 0; i < rows.length; i++){
        	DefaultMutableTreeNode nextSibling = nextSiblings[i];
        	int row = rows[i];
        	
        	if( nextSibling == null && dropRow >= row){
        		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) nodesToMove[i].getParent();
        		for( int j = nodesToMove[i].getLevel(); j >= 0; j--){
        			DefaultMutableTreeNode uncle = parent.getNextSibling();
        			if( uncle != null ){
        				for( int k = row; k < tree.getRowCount(); k++){
                    		if( tree.getPathForRow( k).getLastPathComponent() == uncle ){
                    			targetRowLimits[i] = k;
                    			break;
                    		}
                    	}
                    	if( targetRowLimits[i] != 0 && dropRow < targetRowLimits[i] ){
                    		return false;
                    	}
        				break;
        			}
        			parent = (DefaultMutableTreeNode) parent.getParent();
        		}
            }
        }
        
        for( int i = 0; i < rows.length; i++){
        	int row = rows[i];
        	
        	if( dropRow >= row){
            	for( int j = row; j < tree.getRowCount(); j++){
            		if( tree.getPathForRow( j).getLastPathComponent() == nextSiblings[i] ){
            			targetRowLimits[i] = j;
            			break;
            		}
            	}
            	if( targetRowLimits[i] != 0 && dropRow < targetRowLimits[i] ){
            		return false;
            	}
            }
        	
        }
        
        //DO NOT allow to move a decision step's child
        for( int i = 0; i < rows.length; i++){
        	DefaultMutableTreeNode parent = (DefaultMutableTreeNode) nodesToMove[i].getParent();
        	if( parent.getUserObject() instanceof DecisionStep ){
        		return false;
        	}
        }
        
        return true;
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
     */
    @Override
    protected Transferable createTransferable(JComponent c) {
        JTree tree = (JTree)c;
        rows = tree.getSelectionRows();
        

        TreePath[] paths = tree.getSelectionPaths();
        if(paths != null) {
            // Make up a node array of copies for transfer and
            // another for/of the nodes that will be removed in
            // exportDone after a successful drop.
            List<PCDTreeNode> copies =
                new ArrayList<PCDTreeNode>();
            List<PCDTreeNode> toRemove = new ArrayList<PCDTreeNode>();
            PCDTreeNode node = (PCDTreeNode)paths[0].getLastPathComponent();
            PCDTreeNode copy = node.copy();
            copies.add(copy);
            toRemove.add(node);
            for(int i = 1; i < paths.length; i++) {
                PCDTreeNode next =
                    (PCDTreeNode)paths[i].getLastPathComponent();
                if(next.getLevel() > node.getLevel()) {  // child node
                    copy.add( next.copy() );
                    
                } else {              // sibling
                    copies.add( next.copy() );
                    toRemove.add(next);
                }
            }
            PCDTreeNode[] nodes =
                copies.toArray(new PCDTreeNode[copies.size()]);
            nodesToRemove =
                toRemove.toArray(new PCDTreeNode[toRemove.size()]); //remember nodes to remove later
            return new TransferableNodes(nodes);
        }
        return null;
    }


	/*
     * (non-Javadoc)
     * @see javax.swing.TransferHandler#exportDone(javax.swing.JComponent, java.awt.datatransfer.Transferable, int)
     */
    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        if(action == MOVE) {
        	PseudoCodeStepTree tree = (PseudoCodeStepTree)source;
            DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
            
            if( nodesToRemove != null)
            {
            	// Remove nodes saved in nodesToRemove in createTransferable.
            	for(int i = 0; i < nodesToRemove.length; i++) {
            		model.removeNodeFromParent(nodesToRemove[i]);
            	}
            }
            tree.getProject().manageIDs();
        }
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
     */
    @Override
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.TransferHandler#importData(javax.swing.TransferHandler.TransferSupport)
     */
    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
    	
    	if(!canImport(support)) {
            return false;
        }
        // Extract transfer data.
        PCDTreeNode[] nodes = null;
        try {
            Transferable transferrable = support.getTransferable();
            nodes = (PCDTreeNode[])transferrable.getTransferData(nodesDataFlavor);
        } catch(UnsupportedFlavorException ufe) {
            System.out.println("UnsupportedFlavor: " + ufe.getMessage());
        } catch(java.io.IOException ioe) {
            System.out.println("I/O error: " + ioe.getMessage());
        }
        // Get drop location info.
        JTree.DropLocation dropLocation =
                (JTree.DropLocation)support.getDropLocation();
        int childIndex = dropLocation.getChildIndex();
        TreePath dest = dropLocation.getPath();
        PCDTreeNode parent =
            (PCDTreeNode)dest.getLastPathComponent();
        PseudoCodeStepTree tree = (PseudoCodeStepTree)support.getComponent();
        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
        // Configure for drop mode.
        int index = childIndex;    // DropMode.INSERT
        if(childIndex == -1) {     // DropMode.ON
            index = parent.getChildCount();
        }
        // Add data to model.
        for(int i = 0; i < nodes.length; i++) {
            if( parent.getUserObject() instanceof DecisionStep ){
            	model.insertNodeInto( nodes[i], (PCDTreeNode)parent.getChildAt(0), parent.getChildAt(0).getChildCount() );
            }
            else
            	model.insertNodeInto(nodes[i], parent, index);
            index++;
        }
        tree.getProject().manageIDs();
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getClass().getName();
    }

    /**
     * The Tree Nodes that is to be transferred to another location
     * 
     * @author Zahit Saygın Doğu
     * @author Kerim Bartu Kömürcü
     */
    public class TransferableNodes implements Transferable {
        PCDTreeNode[] nodes;

        public TransferableNodes(PCDTreeNode[] nodes) {
            this.nodes = nodes;
         }

        public Object getTransferData(DataFlavor flavor)
                                 throws UnsupportedFlavorException {
            if(!isDataFlavorSupported(flavor))
                throw new UnsupportedFlavorException(flavor);
            return nodes;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return flavors;
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return nodesDataFlavor.equals(flavor);
        }
    }
    
}
