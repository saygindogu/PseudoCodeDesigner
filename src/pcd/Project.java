package pcd;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.plaf.FontUIResource;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import pcd.step.DecisionStep;
import pcd.step.PCDTreeNode;
import pcd.step.PseudoCodeStepTree;
import pcd.step.Step;
import pcd.step.StepID;
import pcd.variable.Variable;
import pcd.variable.VariableList;

public class Project implements PropertyChangeListener, Serializable {

	//PROPERTIES
	private PCDTreeNode problemDescription;
	private TreeModel steps;
	private VariableList variables;
	private transient JTree tree;
	private String name;
	
	//TODO Step'e alırsak protected yapabiliriz daha güvenli olur, ya da başka bir yol buluruz
	public static int fontSize = Step.DEFAULT_FONT_SIZE;
	
	//CONSTRUCTORS
	public Project(){
		problemDescription = new PCDTreeNode( new Step( "Problem Description"), this );
		variables = new VariableList();
		steps = new DefaultTreeModel( problemDescription);
		name = "NewProject";
		tree = new PseudoCodeStepTree( this);

		//		// TODO gerisi test için... ( silinecek sonra )
//		PCDTreeNode child = new PCDTreeNode( new Step( "testchild1"), this);
//		problemDescription.addChild( child );
//		PCDTreeNode child2 = new PCDTreeNode( new Step( "TestChild3"), this);
//		child.addChild( child2);
//		child2.addChild( new PCDTreeNode( new Step( "TestChild3"), this) );
//		problemDescription.addChild( new PCDTreeNode( new DecisionStep( "Decision", "Condition"), this));
//		problemDescription.addChild( new PCDTreeNode( new Step( "TestChild4"), this));
//		problemDescription.addChild( new PCDTreeNode( new Step( "TestChild5"), this));
		
		variables.addPropertyChangeListener( this);
		
		manageIDs();
		
	}
	
	public Project( DefaultTreeModel steps){
		problemDescription = new PCDTreeNode( new Step( "Problem Description"), this );
		variables = new VariableList();
		this.steps = steps;
		name = "NewProject";
		tree = new PseudoCodeStepTree( this);

		//		// TODO gerisi test için... ( silinecek sonra )
//		PCDTreeNode child = new PCDTreeNode( new Step( "testchild1"), this);
//		problemDescription.addChild( child );
//		PCDTreeNode child2 = new PCDTreeNode( new Step( "TestChild3"), this);
//		child.addChild( child2);
//		child2.addChild( new PCDTreeNode( new Step( "TestChild3"), this) );
//		problemDescription.addChild( new PCDTreeNode( new DecisionStep( "Decision", "Condition"), this));
//		problemDescription.addChild( new PCDTreeNode( new Step( "TestChild4"), this));
//		problemDescription.addChild( new PCDTreeNode( new Step( "TestChild5"), this));
		
		variables.addPropertyChangeListener( this);
		
		manageIDs();
	}
	
	public Project( String name ){
		problemDescription = new PCDTreeNode( new Step( "Problem Description"), this );
		variables = new VariableList();
		steps = new DefaultTreeModel( problemDescription);
		this.name = name;
		tree = new PseudoCodeStepTree( this);

		//		// TODO gerisi test için... ( silinecek sonra )
//		PCDTreeNode child = new PCDTreeNode( new Step( "testchild1"), this);
//		problemDescription.addChild( child );
//		PCDTreeNode child2 = new PCDTreeNode( new Step( "TestChild3"), this);
//		child.addChild( child2);
//		child2.addChild( new PCDTreeNode( new Step( "TestChild3"), this) );
//		problemDescription.addChild( new PCDTreeNode( new DecisionStep( "Decision", "Condition"), this));
//		problemDescription.addChild( new PCDTreeNode( new Step( "TestChild4"), this));
//		problemDescription.addChild( new PCDTreeNode( new Step( "TestChild5"), this));
		
		variables.addPropertyChangeListener( this);
		
		manageIDs();
		
	}
	
	//METHODS
	
	public JTree getTree(){
		return tree;
	}
	
	public void setTree( JTree tree ){
		this.tree = tree;
	}
	
	public TreeModel getSteps(){
		return steps;
	}
	
	public VariableList getVariableList(){
		return variables;
	}
	
	public void manageIDs(){
		PCDTreeNode root = (PCDTreeNode)steps.getRoot();
		
		if( root != problemDescription )
		{
			problemDescription = root;
		}
		
		byte[] id = new byte[1];
		id[0] = 0;
		((Step)(problemDescription.getUserObject())).setID( new StepID( id));
		manageIDs( problemDescription);	
	}
	
	public void manageIDs( PCDTreeNode node){
		Step step = (Step)(node.getUserObject());
		int childCount = node.getChildCount();
		byte[] parentID = step.getID().toArray();
		
		if( node.getUserObject() instanceof DecisionStep ){
			
			byte[] id = new byte[ parentID.length + 1];
			
			for( int j = 0; j < parentID.length; j++){
				id[j] = parentID[j];
			}
			
			//Handle if true part
			id[ id.length - 1 ] = StepID.IF_TRUE_PART_ID;
			PCDTreeNode child = (PCDTreeNode) node.getChildAt( 0);
			((Step) child.getUserObject() )  .setID( new StepID( id));
			manageIDs( (PCDTreeNode) node.getChildAt( 0));
			
			//Handle if false part
			id[ id.length - 1 ] = StepID.IF_FALSE_PART_ID;
			if( node.getChildCount() > 1 ){
				PCDTreeNode secondChild = ((PCDTreeNode)node.getChildAt( 1));
				((Step)secondChild.getUserObject()).setID( new StepID( id));
				manageIDs( (PCDTreeNode) node.getChildAt( 1));
			}
		}
		else if( !node.isRoot() )
		{
			for( int i = 0; i < childCount; i++)
			{
				PCDTreeNode currentNode = (PCDTreeNode)(node.getChildAt( i));
				Step current = (Step)currentNode.getUserObject();
				
				byte[] id = new byte[ parentID.length + 1];
				
				for( int j = 0; j < parentID.length; j++){
					id[j] = parentID[j];
				}
				
				id[ id.length - 1 ] = (byte) (i + 1);
				
				current.setID( new StepID( id));
				
				if( !currentNode.isLeaf() ){
					manageIDs( currentNode);
				}
			}
		}
		else
		{
			for( int i = 0; i < childCount; i++)
			{
				PCDTreeNode currentNode = (PCDTreeNode)(node.getChildAt( i));
				Step current = (Step)currentNode.getUserObject();
				
				byte[] id = new byte[ 1];
				
				id[ 0] = (byte) (i + 1);
				
				current.setID( new StepID( id));
				
				if( !currentNode.isLeaf() ){
					manageIDs( currentNode);
				}
			}
		}
	}
	
	private void addVariableToAllChildren( PCDTreeNode parent, Variable variable){
		//TODO öyle hepsini hoppadanak atma önce bir bak
		int childCount = parent.getChildCount();
		
		for( int i = 0; i < childCount; i++){
			Step childStep = (Step) ((PCDTreeNode) parent.getChildAt( i) ).getUserObject();
			
			childStep.addVariable( variable);
			
			if( parent.getChildAt(i).getChildCount() > 0 ){
				addVariableToAllChildren( (PCDTreeNode)parent.getChildAt(i), variable);
			}
		}
	}
	
	private void removeVariableFromAllChildren( PCDTreeNode parent, Variable variable){
		int childCount = parent.getChildCount();
		
		for( int i = 0; i < childCount; i++){
			Step childStep = (Step) ((PCDTreeNode) parent.getChildAt( i) ).getUserObject();
			
			childStep.removeVariable( variable);
			
			if( parent.getChildAt(i).getChildCount() > 0 ){
				removeVariableFromAllChildren( (PCDTreeNode)parent.getChildAt(i), variable);
			}
		}
	}
	
	public void setFontSize( int fontsize){
		
		fontSize = fontsize;
		((DefaultTreeModel)steps).reload();
	}

	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if( pce.getPropertyName().equals( PCDPropertyNames.VARIABLE_ADD )){
			
			PCDTreeNode root = (PCDTreeNode)steps.getRoot();
			((Step)root.getUserObject()).addVariable( (Variable) pce.getNewValue() );
			addVariableToAllChildren( root, (Variable) pce.getNewValue() );
			reloadModel( (DefaultTreeModel)steps, tree);
			
		}
		else if( pce.getPropertyName().equals( PCDPropertyNames.VARIABLE_REMOVE ) ){
			PCDTreeNode root = (PCDTreeNode)steps.getRoot();
			((Step)root.getUserObject()).removeVariable( (Variable) pce.getOldValue() );
			removeVariableFromAllChildren( root, (Variable) pce.getOldValue() );
			reloadModel( (DefaultTreeModel)steps, tree);
		}
		else if( pce.getPropertyName().equals( PCDPropertyNames.STEP_ADD )){
			PCDTreeNode root = (PCDTreeNode)steps.getRoot();
			for( int i = 0; i < variables.size(); i++)
			{
				addVariableToAllChildren( root, variables.get(i) );
			}
			reloadModel( (DefaultTreeModel)steps, tree);
			manageIDs();
		}
		else if( pce.getPropertyName().equals( PCDPropertyNames.VARIABLE_NAME)){
			PCDTreeNode root = (PCDTreeNode)steps.getRoot();
			((Step)root.getUserObject()).changeText( (String)pce.getOldValue(), (String)pce.getNewValue() );
			changeTextInAllChildren( root, (String)pce.getOldValue(), (String)pce.getNewValue() );
			
			reloadModel( (DefaultTreeModel)steps, tree);
		}
		else if( pce.getPropertyName().equals( PCDPropertyNames.STEP_CHANGE )){
			PCDTreeNode root = (PCDTreeNode)steps.getRoot();
			for( int i = 0; i < variables.size(); i++)
			{
				addVariableToAllChildren( root, variables.get(i) );
			}
			reloadModel( (DefaultTreeModel)steps, tree);
		}
		
		
	}

	private void changeTextInAllChildren(PCDTreeNode parent, String oldValue,
			String newValue) {
		
		int childCount = parent.getChildCount();
		
		for( int i = 0; i < childCount; i++){
			Step childStep = (Step) ((PCDTreeNode) parent.getChildAt( i) ).getUserObject();
			
			childStep.changeText( oldValue, newValue );
			
			if( parent.getChildAt(i).getChildCount() > 0 ){
				changeTextInAllChildren( (PCDTreeNode)parent.getChildAt(i), oldValue, newValue );
			}
		}
		
	}

	public PCDTreeNode findSelected(PCDTreeNode node) {
		PCDTreeNode nodeToReturn = null;
		
		if(((Step) node.getUserObject() ).isSelected() ){
			nodeToReturn = node;
		}
		else if( node.getChildCount() > 0 ){
			for( int i = 0; nodeToReturn == null &&  i < node.getChildCount(); i++ ){
				nodeToReturn = findSelected( (PCDTreeNode )node.getChildAt( i) );
			}
		}
		
		return nodeToReturn;
		
	}
	
	public static void reloadModel( DefaultTreeModel model, JTree tree){
		Enumeration<TreePath> enumaration = tree.
				getExpandedDescendants( new TreePath ( model.
						getRoot() ) );
		model.reload();
		if (enumaration != null) {

            while (enumaration.hasMoreElements()) {
                TreePath treePath = (TreePath) enumaration.nextElement();
                tree.expandPath(treePath);
            }
        }
		if( tree.getSelectionPath() != null) 
		{
			tree.expandPath( tree.getSelectionPath() );
		}
	}

	public String getName() {
		return name;
	}

	public void setSteps(TreeModel model) {
		steps = model;
		
	}

	public void setName(String name) {
		this.name = name;	
	}
}
