package pcd.step;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

public class StepEditor extends DefaultTreeCellEditor implements TreeCellEditor, ActionListener{

	Step step;
	JTree tree;
	StepEditorPanel panel;
	
	public StepEditor( JTree tree, DefaultTreeCellRenderer renderer, TreeCellEditor editor){
		super( tree, renderer, editor );
		
		editingComponent = new WideEditorContainer();
		this.tree = tree;
	}

	@Override
	public Object getCellEditorValue() {
		return step;
	}

	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row) {
		
		PCDTreeNode node = ( PCDTreeNode) value;
		step = (Step) node.getUserObject();
		panel = new StepEditorPanel( step, tree, node, this);
		
		int pathCount = tree.getPathForRow(row).getPathCount();
		
		return panel;
	}
	
//	@Override
//	public void cancelCellEditing(){
//		if( !stopCellEditing() )
//		{
//			panel.finish();
//		}
//		
//		super.cancelCellEditing();
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO add this action listener to relevant places..
	}
	
	@Override
	protected boolean canEditImmediately(EventObject event)
	{
		if( event instanceof MouseEvent && SwingUtilities.isLeftMouseButton((MouseEvent)event))
		{
			MouseEvent me = (MouseEvent) event;
			return( (me.getClickCount() > 1) && inHitRegion(me.getX(), me.getY()));
		}
		return (event == null); 
	}
	
	class WideEditorContainer extends DefaultTreeCellEditor.EditorContainer {
	    JTree tree;

	    TreePath lastPath;

	    int offset;

	    Component editingComponent;

	    public void doLayout() {
	      if (editingComponent != null) {
	        Dimension cSize = getSize();
	        Dimension eSize = editingComponent.getPreferredSize();
	        int n = lastPath.getPathCount();
	        Rectangle r = new Rectangle();
	        r = tree.getBounds(r);
	        eSize.width = r.width - (offset * n);
	        editingComponent.setSize(eSize);
	        editingComponent.setLocation(offset, 0);
	        editingComponent
	            .setBounds(offset, 0, eSize.width, cSize.height);
	        setSize(new Dimension(eSize.width + offset, cSize.height));
	      }
	    }

	    void setLocalCopy(JTree tree, TreePath lastPath, int offset,
	        Component editingComponent) {
	      this.tree = tree;
	      this.lastPath = lastPath;
	      this.offset = offset;
	      this.editingComponent = editingComponent;
	    }
	  }
	
	

	
	
}
