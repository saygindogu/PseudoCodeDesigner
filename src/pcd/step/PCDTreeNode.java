package pcd.step;

import java.awt.datatransfer.Transferable;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.tree.DefaultMutableTreeNode;

import pcd.PCDPropertyNames;
import pcd.Project;

public class PCDTreeNode extends DefaultMutableTreeNode {
	
	private PropertyChangeSupport pcs;
	
	public PCDTreeNode( Step step, Project project){
		super(step);
		
		pcs = new PropertyChangeSupport( this);
		pcs.addPropertyChangeListener( project);
		
		if( step instanceof DecisionStep ){
			Step ifParent;
			Step elseParent;
			
			PCDTreeNode ifNode;
			PCDTreeNode elseNode;
			
			ifParent = new Step( "IF " + ((DecisionStep)step).getConditionText() + ";" );
			elseParent = new Step( "ELSE");
			
			ifNode = new PCDTreeNode( ifParent, project );
			elseNode = new PCDTreeNode( elseParent, project );
			
			add( ifNode);
			add( elseNode);	
		}
	}
	
	public void addPropertychangeListener( PropertyChangeListener listener){
		pcs.addPropertyChangeListener( listener);
	}
	
	public void addChild( PCDTreeNode node ){
		if( getUserObject() instanceof DecisionStep){
			
			byte[] parentID = ((Step)getUserObject()).getID().toArray();
			byte[] newID = new byte[ parentID.length + 1];
			
			for( int i = 0; i < parentID.length; i++){
				newID[i] = parentID[i];
			}
			
			newID[ newID.length - 1] = StepID.IF_TRUE_PART_ID;
			((Step)((PCDTreeNode)getChildAt( 0) ).getUserObject() ).setID( new StepID( newID ) );
			
			if( getChildCount() >= 2){
				newID[ newID.length - 1] = StepID.IF_FALSE_PART_ID;
				((Step)((PCDTreeNode)getChildAt( 1) ).getUserObject() ).setID( new StepID( newID ) );
			}
			
			((PCDTreeNode)getChildAt( 0)).addChild( node);
		}
		else
		{
			add( node);
		}
		
		pcs.firePropertyChange( new PropertyChangeEvent( this, PCDPropertyNames.STEP_ADD, null, null));
	}
	
	public void addChild( PCDTreeNode node, int index ){
		if( index >= getChildCount() || index < 0 ){
			addChild( node);
		}
		else if( getUserObject() instanceof DecisionStep){
			addChild( node);
		}
		else{			
			insert( node, index);
		}
		
		pcs.firePropertyChange( new PropertyChangeEvent( this, PCDPropertyNames.STEP_ADD , null, null));
	}

	public Project getProject(){
		return (Project) pcs.getPropertyChangeListeners()[0];
	}

	public PCDTreeNode copy() {
		PCDTreeNode newNode = new PCDTreeNode( ((Step)getUserObject()).copy() , getProject() );
		if( getUserObject() instanceof DecisionStep ){
			newNode.removeAllChildren();
		}
		if( getChildCount() > 0){
			for( int i = 0; i < getChildCount(); i++){
				if( !(getUserObject() instanceof DecisionStep) )
				{
					newNode.addChild( ((PCDTreeNode)getChildAt(i)).copy());
				}
				else{
					newNode.add( ((PCDTreeNode)getChildAt(i)).copy());
				}
			}
			
		}
		return newNode;
	}
	
	public void changeWord( String oldWord, String newWord){
		Step step = (Step) getUserObject();
		step.changeText( oldWord, newWord );
		if( !isLeaf() ){
			for( int i = 0; i < getChildCount(); i++){
				((PCDTreeNode)getChildAt(i)).changeWord( oldWord, newWord );
			}
		}
	}
	
		
	public void changeWord( String[] oldWords, String[] newWords ){
		
		for( int i = 0; i< oldWords.length; i++ ){
			if( oldWords[i] != null || !oldWords[i].equals( "") )
			{
				changeWord(oldWords[i], newWords[i] );
			}
		}
	}

	public void removePropertyChangeListeners() {
		pcs.removePropertyChangeListener( pcs.getPropertyChangeListeners()[0] );
		
	}
		
		
}
