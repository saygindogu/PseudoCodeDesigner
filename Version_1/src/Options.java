import java.awt.*;
import javax.swing.*;
import java.io.*;

/**
 * Options - Set of options that user can change
 *
 * @author Kerim Bartu Kömürcü
 */
 
public class Options
{		
		// VARIABLES
		private Project project;
		private StatementBox copiedBox;
		
		// CONSTRUCTOR
		public Options( Project project)
		{
			this.project = project;
		}
		
		/********************************************************************************************************
		************************************************METHODS**************************************************
		*********************************************************************************************************/
		// Returns the project
		public Project getProject()
		{
			return project;
		}
		
		// Creates a new project
		public void newProject()
		{
			project.getSteps().removeAll();
			project.getVariableList().removeAll();
		}
		
		// Opens a saved project
		public void saveProject( String name)
		{
			FileOutputStream saveFile;
			try{  // Catches errors in I/O
				
				// Opens a file named SavedProject.sav to write to
				saveFile = new FileOutputStream( name + ".r2d2" );
				
				// Creates an ObjectOutputStream to put objects into save file
				ObjectOutputStream save = new ObjectOutputStream(saveFile);
				
				// Saves the whole project
				save.writeObject( project);
				
				// Close the file.
				save.close(); 
			}
			catch( Exception e){
				// If error exists then prints the information
				e.printStackTrace(); 
			}
			//project.saveProject( name);
		}
		// Opens a saved project
		public Project openProject( File f)
		{
			Project pro;
			try {
			FileInputStream fis = new FileInputStream( f);
			ObjectInputStream ois = new ObjectInputStream( fis);
			// Typecast is needed because ObjectInputStream's readObject() method returns a generic Object
			pro = (Project) ois.readObject();
			project.getSteps().removeAll();
			project.getVariableList().removeAll();
			project.getSteps().addBox (pro.getSteps() );
			for ( int i = 0; i < pro.getVariableList().size(); i++ )
			{
				project.getVariableList().addVariable(pro.getVariableList().get(i) );
			}
			
			// Closes the file
			ois.close();
			}
			catch( Exception e){
				// If error exists then prints the information
				e.printStackTrace();
				pro = null;
			}
			return pro;
			
			
		}
		
		public void saveMethodList( MethodList methodList)
		{
			FileOutputStream saveFile;
			try {
				saveFile = new FileOutputStream( "methods.met");
				ObjectOutputStream save = new ObjectOutputStream(saveFile);
				save.writeObject( methodList);
				save.close();
			}
			catch( Exception e){
				e.printStackTrace();
			}
		}
		
		// Changes the font size of the statement box
		public void changeFontSize( int size)
		{
			project.changeFontSize( size);
		}
		
		// Changes the desired variable's name
		public void changeVariable( String oldName, String newName)
		{
			project.getVariableList().changeName(oldName, newName);
			project.changeWord( oldName, newName);
		}
		
		// Copies the selected box/boxes with all of its sub-boxes
		public void copyBox( int[] id)
		{	
			copiedBox = project.copyBox( id);
		}
		
		// Cuts the selected box/boxes with all of its sub-boxes
		public void cutBox( int[] id)
		{
			copiedBox = project.cutBox( id);
		}
		
		// Paste the cuted or copied box/boxes with all of its sub-boxes
		public void pasteBox( int[] newID)
		{
			if( copiedBox != null)
			{
				project.pasteBox( copiedBox, newID);
				copiedBox = null;
			}
		}
		
		// Adds a new box
		public void addBox( int[] id)
		{
			project.addNewBox( id);
		}
		
		// Adds a new variable to the variable list
		public void addVariable( Variable v)
		{
			project.addVariable( v);
		}
		
		public void insertMethod(MethodList methods) {
			for( int i = 0; i < methods.size(); i++)
			{
				methods.get(i).setID( project.getSteps().getLastStep().getNextID() );
				project.getSteps().addBox (methods.get( i) );
				project.getVariableList().addVariable( methods.get(i).getAllVariables() );
			}
		}
		// Removes a variable from the variable list
		public void removeVariable( String oldName)
		{
			project.getVariableList().remove( oldName);
		}
} // end of class Options
