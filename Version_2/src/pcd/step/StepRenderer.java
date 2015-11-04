package pcd.step;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

public class StepRenderer implements TreeCellRenderer {
	
	public StepRenderer(){
		
	}
	
	public Component getTreeCellRendererComponent(JTree tree,
            Object value,
            boolean selected,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus){
		
		PCDTreeNode node = (PCDTreeNode) value;
		if( ((Step) node.getUserObject()).isCurrentStep ){
			tree.expandPath( tree.getPathForRow( row ) );
		}
		JPanel rendererPanel = new StepRendererPanel( (Step) node.getUserObject(), tree);
		
		if( selected){
			rendererPanel.requestFocus();
		}
		
		return rendererPanel;
		
	}
}
