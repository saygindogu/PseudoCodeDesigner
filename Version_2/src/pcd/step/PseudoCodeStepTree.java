package pcd.step;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import pcd.OptionsGUI;
import pcd.OptionsGUIListener;
import pcd.PCDTreeTransferHandler;
import pcd.Project;

public class PseudoCodeStepTree extends JTree {
	
	//PROPERTIES
	protected Project project;
	private Clipboard clipboard;

	
	//CONSTRUCTORS
	public PseudoCodeStepTree( Project p){
		super( p.getSteps() );
		project = p;
		
		TreeCellRenderer stepRenderer = new StepRenderer();
		TreeCellEditor stepEditor = new StepEditor( null, null, null);
		
		setEditable(true);
		setDragEnabled(true);
		setDropMode(DropMode.ON_OR_INSERT);
		getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        setTransferHandler( new PCDTreeTransferHandler() );
        setShowsRootHandles(true);
        putClientProperty("JTree.lineStyle", "Horizontal");
        setBackground( new Color( 85, 85, 85)  );
        setRowHeight( 0);
        addTreeSelectionListener( new PCDTreeSelectionListener() );
        setUI( new PCDTreeUI() );
        setScrollsOnExpand( false);
        setToggleClickCount(0);
        
       
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        
        
        setCellRenderer( stepRenderer);
		setCellEditor( stepEditor);
		
		addMouseListener( new PCDStepTreeListener() );
		
	}
	//METHODS
	
	public Project getProject(){
		return project;
	}
	
	private class PCDTreeSelectionListener implements TreeSelectionListener{

		@Override
		public void valueChanged(TreeSelectionEvent e) {

			if( e.getOldLeadSelectionPath() != null )
			{
				((Step)((PCDTreeNode) e.getOldLeadSelectionPath().getLastPathComponent() ).getUserObject()).setSelected( false);
			}
			if (e.getNewLeadSelectionPath() != null)
			{
				((Step)((PCDTreeNode) e.getNewLeadSelectionPath().getLastPathComponent() ).getUserObject()).setSelected( true);
				requestFocus();
			}
		}
		
	}
	
	private class PCDStepTreeListener extends MouseAdapter implements ActionListener{
		
		int y = 0;
		JTree tree;
		JPopupMenu rightClickOptions;
		
		@Override
		public void mouseClicked( MouseEvent e){
			tree = (JTree) e.getSource();
			if( SwingUtilities.isRightMouseButton(e) ){
				y = e.getY();
				
				rightClickOptions = new JPopupMenu();
				rightClickOptions.setLayout( new BoxLayout(rightClickOptions, BoxLayout.X_AXIS));
				
				JButton saveAsMethod = new JButton( "Save as Method");
				saveAsMethod.setToolTipText("Save this step as a method.");
				saveAsMethod.setActionCommand( "saveMethod" );
				saveAsMethod.addActionListener( this);
				
				JButton copy = new JButton( "Copy");
				copy.setToolTipText("Copy this step as a method.");
				copy.setActionCommand( "copy" );
				copy.addActionListener( this);
				
				JButton cut = new JButton( "Cut");
				cut.setToolTipText("Save this step as a method.");
				cut.setActionCommand( "cut" );
				cut.addActionListener( this);
				
				JButton paste = new JButton( "Paste");
				paste.setToolTipText("Save this step as a method.");
				paste.setActionCommand( "paste" );
				paste.addActionListener( this);
	
				rightClickOptions.add( saveAsMethod);
				rightClickOptions.add( copy);
				rightClickOptions.add( cut);
				rightClickOptions.add(paste);
				
				((JComponent)e.getSource()).add( rightClickOptions);
				
				rightClickOptions.show( ((JComponent) e.getSource()), e.getX(), e.getY() );
				
			}
	}

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			PCDTreeNode node = null;
			if( tree.getPathForLocation( 100, y ) != null )
			{ 
				node = (PCDTreeNode)tree.getPathForLocation( 100, y ).getLastPathComponent() ;
			}

			//TODO bunları da multiple yapalım bence..
			if( command.equals( "copy")){
				if( tree.getPathForLocation( 100, y ) != null )
				{ 
					node = (PCDTreeNode)tree.getPathForLocation( 100, y ).getLastPathComponent() ;
				}

				if( node != null){
					System.out.println("");
					PCDTreeNode[] nodes = new PCDTreeNode[1];
					nodes[0] = node.copy();
					
					PCDTreeTransferHandler handler = new PCDTreeTransferHandler();
					Transferable transferable = handler.new TransferableNodes(nodes);
					clipboard.setContents( transferable, null);
				}
			}
			else if( command.equals( "cut")){
				if( tree.getPathForLocation( 100, y ) != null )
				{ 
					node = (PCDTreeNode)tree.getPathForLocation( 100, y ).getLastPathComponent() ;
				}

				if( node != null){
					PCDTreeNode[] nodes = new PCDTreeNode[1];
					nodes[0] = node.copy();
					
					PCDTreeTransferHandler handler = new PCDTreeTransferHandler();
					Transferable transferable = handler.new TransferableNodes(nodes);
					clipboard.setContents( transferable, null);
					((DefaultTreeModel)tree.getModel()).removeNodeFromParent( node);
				}
				project.manageIDs();
				Project.reloadModel((DefaultTreeModel)tree.getModel(), tree);
			}
			else if (command.equals( "paste"))
			{
				PCDTreeNode[] nodes;
				Transferable transferable = clipboard.getContents( null);
				String mimeType = DataFlavor.javaJVMLocalObjectMimeType +
	                    ";class=\"" +
	      pcd.step.PCDTreeNode[].class.getName() + "\"";
				try
				{
					DataFlavor nodesDataFlavor = new DataFlavor(mimeType);
					nodes = (PCDTreeNode[])transferable.getTransferData( nodesDataFlavor );
					PCDTreeNode parent = null;
					
					if( tree.getSelectionPath() == null)
					{
						for( int i = 0; i < nodes.length; i++) {
							((PCDTreeNode)project.getSteps().getRoot()).addChild(nodes[i]);
						}
					}
					
					else if( !((PCDTreeNode)tree.getSelectionPath().getLastPathComponent()).isRoot())
					{
						parent =(PCDTreeNode) ((PCDTreeNode)tree.getPathForLocation( 100, y).getLastPathComponent()).getParent();
					}
					if( parent != null)
					{
						for( int i = 0; i < nodes.length; i++){
							parent.addChild(nodes[i], parent.getIndex( (PCDTreeNode)tree.getSelectionPath().getLastPathComponent()  ) + 1);
						}
					}
					project.manageIDs();
					Project.reloadModel((DefaultTreeModel)tree.getModel(), tree);
				}
				catch( Exception exception){
					System.out.println( "caught excepion:");
					exception.printStackTrace();
				}
			}
			else if( command.equals( "saveMethod")){
				JFileChooser fileChooser = new JFileChooser();
				
				NewMethodPanel newMethodPanel = new NewMethodPanel( node);
				JOptionPane.showOptionDialog(null, newMethodPanel, OptionsGUI.NEW_METHOD, JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
				
				if( newMethodPanel.getConstructedMethodNode() != null ){
					int choice = fileChooser.showSaveDialog( null);
					
					if ( choice == JFileChooser.APPROVE_OPTION)
					{
						File file = fileChooser.getSelectedFile();
						
						try {
							FileOutputStream fileOutputStream = new FileOutputStream( file);
							ObjectOutputStream objectOutputStream = new ObjectOutputStream( fileOutputStream);
							
							objectOutputStream.writeObject( newMethodPanel.getConstructedMethodNode() );
							
							objectOutputStream.close();
							fileOutputStream.close();
						}
						catch( Exception exception){
							System.out.println( "Whops!");
							exception.printStackTrace();
						}
					}
				}
			}
			rightClickOptions.setVisible( false);
			
		}
		
	}
}
