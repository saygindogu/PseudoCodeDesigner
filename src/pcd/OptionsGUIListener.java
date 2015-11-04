package pcd;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import pcd.step.InsertMethodPanel;
import pcd.step.Method;
import pcd.step.NewMethodPanel;
import pcd.step.PCDTreeNode;
import pcd.step.Step;
import pcd.variable.Variable;

public class OptionsGUIListener implements ActionListener {
	Project project;
	private JTree tree;
	private PseudoCodeDesigner pcd;
	private JFileChooser fileChooser;
	private Clipboard clipBoard;
	
	public OptionsGUIListener(Project project, JTree tree, PseudoCodeDesigner pcd)
	{
		this.tree = tree;
		this.project = project;
		this.pcd = pcd;
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter( new ProjectFileFilter() );
		fileChooser.addChoosableFileFilter( new MethodFileFilter() );
		clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
		
		fileChooser.setCurrentDirectory( PseudoCodeDesigner.userDefinedSaveDir );
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ( (e.getActionCommand() ).equals( OptionsGUI.NEW_PROJECT ) ) {
			int choice = JOptionPane.showConfirmDialog(null, "Do you want to save existing project?", "Save Resource", JOptionPane.YES_NO_CANCEL_OPTION);
			if( choice == JOptionPane.YES_OPTION) {
				actionPerformed( new ActionEvent(this, 0, OptionsGUI.SAVE_PROJECT));
				String name = JOptionPane.showInputDialog( null, "Please enter a name for the new project", "New Project", JOptionPane.DEFAULT_OPTION);
				pcd.setProject( new Project( name) );
			}
			else if (choice == JOptionPane.NO_OPTION)
			{
				String name = JOptionPane.showInputDialog( null, "Please enter a name for the new project", "New Project", JOptionPane.DEFAULT_OPTION);
				pcd.setProject( new Project( name) );
			}
		}
		else if( e.getActionCommand().equals( OptionsGUI.CHANGE_PROJECT_NAME)){
			String name = JOptionPane.showInputDialog( null, "Please enter the name of the project.");
			project.setName( name);
			pcd.revalidate();
		}
		else if( (e.getActionCommand() ).equals( OptionsGUI.NEW_STEP )){
			DefaultTreeModel model = (DefaultTreeModel) project.getSteps() ;
			
			PCDTreeNode selected = project.findSelected ( (PCDTreeNode) model.getRoot() ) ;
			if( selected == null){
				((PCDTreeNode)((DefaultTreeModel)tree.getModel()).getRoot()).addChild( new PCDTreeNode( new Step() , project));
			}
			else if( selected.isRoot() ){
		    	selected.addChild( new PCDTreeNode( new Step(), selected.getProject() ) );
		    }
		    else{
		    	PCDTreeNode parent = ((PCDTreeNode)selected.getParent() );
		    	parent.insert( new PCDTreeNode( new Step(), project), parent.getIndex (selected) + 1);
		    }
			PCDTreeNode node = (PCDTreeNode) tree.getModel().getRoot();
			((Step) node.getUserObject()).setSelected( false);
			while( node.getNextNode() != null){
				node = (PCDTreeNode) node.getNextNode();
				((Step) node.getUserObject()).setSelected( false);
			}
			if (tree.getSelectionPath() != null){
				((Step) ((PCDTreeNode) tree.getSelectionPath().getLastPathComponent()).getUserObject()).setSelected( true);
			}
		    Project.reloadModel( model, tree);
		    project.manageIDs();
		}	
		else if( (e.getActionCommand() ).equals( OptionsGUI.NEW_METHOD )){
			NewMethodPanel newMethodPanel = new NewMethodPanel( null);
			JOptionPane.showOptionDialog(null, newMethodPanel, OptionsGUI.NEW_METHOD, JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
			
			if( newMethodPanel.getConstructedMethodNode() != null ){
					PCDTreeNode constructedMethodNode = newMethodPanel.getConstructedMethodNode();
					File dir =  new File( PseudoCodeDesigner.userDefinedSaveDir.getAbsolutePath() + "\\methods\\" );
					File file = new File( PseudoCodeDesigner.userDefinedSaveDir + "\\methods\\" + ((Step) constructedMethodNode.getUserObject()).getName() + ".met" );
					dir.mkdirs();
					
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
				//}
			}
		}
		
		else if( (e.getActionCommand() ).equals( OptionsGUI.NEW_VARIABLE )){
			String variableName;
			
			variableName = JOptionPane.showInputDialog(null, "Enter the new variable name:", OptionsGUI.NEW_VARIABLE, JOptionPane.DEFAULT_OPTION);
			if (variableName != null && !variableName.contains( " ") && !variableName.equals("") )
				project.getVariableList().addVariable(new Variable(variableName));
			else if( variableName != null && (variableName.contains( " ") || variableName.equals("")) ){
				JOptionPane.showMessageDialog(null, "Variable names cannot contain empty space.");
			}
		}
		
		else if( (e.getActionCommand() ).equals( OptionsGUI.COPY_STEP )){
			DefaultTreeModel model = (DefaultTreeModel) project.getSteps() ;
			PCDTreeNode selected = project.findSelected ( (PCDTreeNode) model.getRoot() ) ;
			if( selected != null){
				PCDTreeNode[] nodes = new PCDTreeNode[1];
				nodes[0] = selected.copy();
				
				PCDTreeTransferHandler handler = new PCDTreeTransferHandler();
				Transferable transferable = handler.new TransferableNodes(nodes);
				clipBoard.setContents( transferable, null);
			}
		}
		
		else if( (e.getActionCommand() ).equals( OptionsGUI.ALGORITHM_VISUAL_DISPLAY )){
			new AVD( project);
		}
		
		else if( (e.getActionCommand() ).equals( OptionsGUI.CHANGE_FONT_SIZE )){
			if( e.getSource() instanceof JButton ){
				String stringFontSize = JOptionPane.showInputDialog ("Enter the new size: ");
				if ( stringFontSize != null)
				{
					try{
						int fontSize = Integer.parseInt( stringFontSize);
						project.setFontSize( fontSize);
					}
					catch (Exception exception)
					{
						JOptionPane.showMessageDialog(null, "Please enter an integer value");
					}
				}
			}
			else
			{
				try {
					project.setFontSize( Integer.parseInt(((JTextField)e.getSource()).getText() ) );
				}
				catch (Exception exception) {
					JOptionPane.showMessageDialog(null, "Please enter an integer value");
				}
				
			}
		}
		
		else if( (e.getActionCommand() ).equals( OptionsGUI.CHANGE_VARIABLE )){
			String oldName, newName;
			
			JPanel panel = new JPanel();
			JLabel label = new JLabel( "Please select the variable which you want to change");
			Vector<Variable> vector = new Vector<Variable>( project.getVariableList() );
			JComboBox<Variable> comboBox = new JComboBox<Variable>( vector );
			panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS ) );
			panel.add( label);
			panel.add( comboBox);
			int choice = JOptionPane.CLOSED_OPTION  ;
			newName = "defaultVariable";
			if( vector.size() > 0 )
			{
				choice = JOptionPane.showOptionDialog(null, panel, OptionsGUI.CHANGE_VARIABLE, JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, null, null);
				newName = JOptionPane.showInputDialog ("Enter the new name of it: ",  null);
			}
			else{
				JOptionPane.showMessageDialog( null, "There is no variable!", "No Variables", JOptionPane.WARNING_MESSAGE );
			}
			
			if( choice != JOptionPane.CLOSED_OPTION )
			{
				oldName = comboBox.getSelectedItem().toString();
				project.getVariableList().changeName( oldName, newName);
			}
		}
		else if( e.getActionCommand().equals( OptionsGUI.CUT_STEP ) ){
			
			DefaultTreeModel model = (DefaultTreeModel) project.getSteps() ;
			PCDTreeNode selected = project.findSelected ( (PCDTreeNode) model.getRoot() ) ;
			try {
				
				if( selected != null){
					PCDTreeNode[] nodes = new PCDTreeNode[1];
					nodes[0] = selected.copy();
					
					PCDTreeTransferHandler handler = new PCDTreeTransferHandler();
					Transferable transferable = handler.new TransferableNodes(nodes);
					clipBoard.setContents( transferable, null);
					((DefaultTreeModel)tree.getModel()).removeNodeFromParent( selected);
					project.manageIDs();
					Project.reloadModel((DefaultTreeModel)tree.getModel(), tree);
				}
			}
			catch ( Exception notCutable) {
				JOptionPane.showMessageDialog(null, "Please select a step to cut, not the Problem Description");
			}
		}
		
//		else if( (e.getActionCommand() ).equals( OptionsGUI.HELP )){
//			
//		}
		
		else if( (e.getActionCommand() ).equals( OptionsGUI.INSERT_METHOD )){
			File dir =  new File( PseudoCodeDesigner.userDefinedSaveDir.getAbsolutePath() + "\\methods\\" );
			fileChooser.setCurrentDirectory( dir);
			int choice = fileChooser.showOpenDialog( null);
			Method method;
			PCDTreeNode node;
			
			if (choice == JFileChooser.APPROVE_OPTION)
			{
				File file = fileChooser.getSelectedFile();
				
				try {
					
					FileInputStream fis = new FileInputStream( file);
					ObjectInputStream ois = new ObjectInputStream( fis);
					node = (PCDTreeNode) ois.readObject();
					method = (Method) node.getUserObject();
					
					ois.close();
				}
				catch( Exception error){
					error.printStackTrace();
					method = null;
					node = null;
					JOptionPane.showMessageDialog( null, "Please try again", "Unexpected file type", JOptionPane.WARNING_MESSAGE );
				}
				
				
				if( method != null && node != null)
				{
					JOptionPane.showOptionDialog(null, new InsertMethodPanel( node, method, tree, project), OptionsGUI.INSERT_METHOD, JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
				}
			}

		}
		
		else if( (e.getActionCommand() ).equals( OptionsGUI.OPEN_PROJECT )){
			int choice = JOptionPane.showConfirmDialog(null, "Do you want to save existing project?", "Save Resource", JOptionPane.YES_NO_CANCEL_OPTION);
			if( choice == JOptionPane.YES_OPTION) {
				actionPerformed( new ActionEvent(this, 0, OptionsGUI.SAVE_PROJECT));
				
				File dir =  new File( PseudoCodeDesigner.userDefinedSaveDir.getAbsolutePath() + "\\projects\\" );
				dir.mkdirs();
				fileChooser.setCurrentDirectory( dir);
				int choice2 = fileChooser.showOpenDialog( null);
				if (choice2 == JFileChooser.APPROVE_OPTION)
				{
					File file = fileChooser.getSelectedFile();
					
					try {
						FileInputStream fis = new FileInputStream( file);
						ObjectInputStream ois = new ObjectInputStream( fis);
						project = (Project) ois.readObject();
						
						ois.close();
					}
					catch( Exception error){
						error.printStackTrace();
						project = null;
						JOptionPane.showMessageDialog( null, "Please try again", "Unexpected file type", JOptionPane.WARNING_MESSAGE );
					}
					
					if( project != null)
					{
						pcd.setProject( project );
					}
				}
			}
			else if (choice == JOptionPane.NO_OPTION)
			{
				File dir =  new File( PseudoCodeDesigner.userDefinedSaveDir.getAbsolutePath() + "\\projects\\" );
				dir.mkdirs();
				fileChooser.setCurrentDirectory( dir);
				int choice2 = fileChooser.showOpenDialog( null);
				if (choice2 == JFileChooser.APPROVE_OPTION)
				{
					File file = fileChooser.getSelectedFile();
					
					try {
						FileInputStream fis = new FileInputStream( file);
						ObjectInputStream ois = new ObjectInputStream( fis);
						project = (Project) ois.readObject();
						
						ois.close();
					}
					catch( Exception error){
						error.printStackTrace();
						project = null;
						JOptionPane.showMessageDialog( null, "Please try again", "Unexpected file type", JOptionPane.WARNING_MESSAGE );
					}
					
					if( project != null)
					{
						pcd.setProject( project );
					}
				}
			}

		}
		
		else if( (e.getActionCommand() ).equals( OptionsGUI.PASTE_STEP )){
			PCDTreeNode[] nodes;
			Transferable transferable = clipBoard.getContents( null);
			String mimeType = DataFlavor.javaJVMLocalObjectMimeType +
                    ";class=\"" +
      pcd.step.PCDTreeNode[].class.getName() + "\"";
			try
			{
				DataFlavor nodesDataFlavor = new DataFlavor(mimeType);
				nodes = (PCDTreeNode[])transferable.getTransferData( nodesDataFlavor );
				PCDTreeNode parent = null;
				
				if( tree.getSelectionPath() != null && !((PCDTreeNode)tree.getSelectionPath().getLastPathComponent()).isRoot())
				{
					parent =(PCDTreeNode) ((PCDTreeNode)tree.getSelectionPath().getLastPathComponent()).getParent();
				}
				if( parent != null)
				{
					for( int i = 0; i < nodes.length; i++){
						parent.addChild(nodes[i], parent.getIndex( (PCDTreeNode)tree.getSelectionPath().getLastPathComponent()  ) + 1);
					}
					Project.reloadModel((DefaultTreeModel)tree.getModel(), tree);
				}
				else
				{
					for( int i = 0; i < nodes.length; i++){
						((PCDTreeNode) tree.getModel().getRoot()).addChild(nodes[i]);
					}
				}
			}
			catch( Exception exception){
				System.out.println( "caught excepion:");
				exception.printStackTrace();
			}
			
			
		}
		else if( (e.getActionCommand() ).equals( OptionsGUI.RUN_THE_PCD )){
			new RunThePseudoCode( project);
			pcd.repaint();
		}
		else if( e.getActionCommand().equals( OptionsGUI.SAVE_PROJECT)){
			File dir =  new File( PseudoCodeDesigner.userDefinedSaveDir.getAbsolutePath() + "\\projects\\" );
			File file = new File( PseudoCodeDesigner.userDefinedSaveDir + "\\projects\\" + project.getName() + ".pcd" );
			dir.mkdirs();
			try {
				FileOutputStream fileOutputStream = new FileOutputStream( file);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream( fileOutputStream);
				
				objectOutputStream.writeObject( project);
				
				objectOutputStream.close();
				fileOutputStream.close();
			}
			catch( Exception exception){
				exception.printStackTrace();
				project = null;
			}
			
			if( project != null)
			{
				pcd.setProject( project );
			}
			else{
				JOptionPane.showMessageDialog(null, "hele bi dur", "123", JOptionPane.ERROR_MESSAGE );
			}
		}
		else if( (e.getActionCommand() ).equals( OptionsGUI.SAVE_PROJECT_AS )){
			File dir =  new File( PseudoCodeDesigner.userDefinedSaveDir.getAbsolutePath() + "\\projects\\" );
			
			fileChooser.setCurrentDirectory(dir);
			int choice = fileChooser.showSaveDialog( null );
			
			if ( choice == JFileChooser.APPROVE_OPTION)
			{
				File file = fileChooser.getSelectedFile();
				
				try {
					FileOutputStream fileOutputStream = new FileOutputStream( file);
					ObjectOutputStream objectOutputStream = new ObjectOutputStream( fileOutputStream);
					
					objectOutputStream.writeObject( project);
					
					objectOutputStream.close();
					fileOutputStream.close();
				}
				catch( Exception exception){
					exception.printStackTrace();
					project = null;
				}
				
				if( project != null)
				{
					pcd.setProject( project );
				}
				else{
					JOptionPane.showMessageDialog(null, "hele bi dur", "123", JOptionPane.ERROR_MESSAGE );
				}
			}

			
		}
		else if( (e.getActionCommand() ).equals( OptionsGUI.REMOVE_VARIABLE )){
			String variableName;
			
			variableName = JOptionPane.showInputDialog(null, "Enter the variable name you want delete:", OptionsGUI.REMOVE_VARIABLE, JOptionPane.DEFAULT_OPTION);
			
			if (variableName != null && !variableName.contains( " ") && !variableName.equals("") )
			{
				if( project.getVariableList().findVariable(variableName) != null)
				{
					project.getVariableList().removeVariable( project.getVariableList().findVariable(variableName));
				}
				else{
					JOptionPane.showMessageDialog(null, "Cannot find that variable.");
				}
			}
			else if( variableName.contains( " ") || variableName.equals("") ){
				JOptionPane.showMessageDialog(null, "Variable names cannot contain empty space.");
			}
		}
		else if( e.getActionCommand().equals( OptionsGUI.CHANGE_DIRECTORY )){
			JOptionPane.showMessageDialog( null, "Please select a file ( any type ) in the directory you want");
			int choice = fileChooser.showDialog( null, "OK");
			if( choice == JFileChooser.APPROVE_OPTION ){
				File file = fileChooser.getCurrentDirectory();
				pcd.userDefinedSaveDir = file;
			}
			
		}

	}
	
	class ProjectFileFilter extends javax.swing.filechooser.FileFilter {
		public boolean accept(File file) {
		String filename = file.getName();
		return filename.endsWith(".pcd");
		}

		public String getDescription() {
		return "PseudoCodeDesigner Project Files" + "*.pcd";
		}
	}

	class MethodFileFilter extends javax.swing.filechooser.FileFilter {
	  public boolean accept(File file) {
	    String filename = file.getName();
	    return filename.endsWith(".met");
	  }

	  public String getDescription() {
	    return "PseudoCodeDesigner Method Files" + "*.met";
	  }
	}

}

