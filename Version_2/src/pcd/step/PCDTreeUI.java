package pcd.step;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreePath;

public class PCDTreeUI extends BasicTreeUI {
	
	public static final int OFFSET = 20;
	
	@Override
    protected void paintRow( Graphics g, Rectangle clipBounds, Insets insets, Rectangle bounds,
        TreePath path, int row, boolean isExpanded, boolean hasBeenExpanded, boolean isLeaf )
    {
		int pathCount = path.getPathCount();
		int indent = OFFSET * pathCount;
		
		boolean isRowSelected = tree.isRowSelected( row );
 
        if( isRowSelected )
        {
            Graphics g2 = g.create();
 
            g2.setColor( new Color( 128, 200, 124) );
            g2.fillRect( 0 + indent, bounds.y + 6, tree.getWidth() - indent, bounds.height - 12 );
 
            g2.dispose();
        }
 
        else
        {
            Graphics g2 = g.create();
 
            if( row % 2 == 0 )
            {
                g2.setColor( new Color( 90, 71, 69) );
            }
            else
            {
                g2.setColor( new Color( 138, 136, 94) );
            }
            g2.fillRect( 0 + indent, bounds.y + 6, tree.getWidth() - indent, bounds.height - 12);
            
            g2.dispose();

        }
        
        Graphics g3 = g.create();
        
        super.paintRow(g, clipBounds, insets, bounds, path, row, isExpanded, hasBeenExpanded, isLeaf);
    }
	
}
