import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;

/**
 * OptionsGUI - GUI of the Options class
 *
 * @author Kerim Bartu Kömürcü
 */
 
public class OptionsGUI
{
	private Options options;
	protected JPanel panel;
	private JMenuBar menuBar;
	private JMenu file, edit, addRemove, view;
	private JMenuItem newProject, openProject, saveProject, changeFontSize, changeVariable, copyTextBox, pasteTextBox;
	private JMenuItem cutTextBox, newBox, newVariable, removeVariable, insertMethod, addNewMethod;
	private JMenuItem algorithmVisualDisplay, runThePseudoCode, help;
	private ProblemDescriptionPanel problemDescriptionPanel;
	int selectedMethod;

	
	public OptionsGUI( Project project)
	{
		options = new Options( project);
		panel = new JPanel();
		panel.setLayout( new BorderLayout());
		problemDescriptionPanel = new ProblemDescriptionPanel( project );
		menu();
		panel.add( problemDescriptionPanel);
	}
	
	public void menu()
	{
		//MENUBAR
		menuBar = new JMenuBar();
		panel.add( menuBar, BorderLayout.NORTH);
		
		//MENU ELEMENTS
		file = new JMenu("File");
		file.setMnemonic('F');
		menuBar.add(file);
		
		edit = new JMenu("Edit");
		edit.setMnemonic('E');
		menuBar.add(edit);
		
		addRemove = new JMenu("Add / Remove");
		addRemove.setMnemonic('A');
		menuBar.add(addRemove);
		
		view = new JMenu("View");
		view.setMnemonic('V');
		menuBar.add(view);

		
		//MENU OPTÝONS
		
		////// File Options
		
		// newProject
		newProject = new JMenuItem("New Project");
		newProject.setToolTipText("Creat an empty project");
		newProject.addActionListener(new ActionListener() {public  void  actionPerformed(ActionEvent e) {options.newProject(); problemDescriptionPanel.clear(); }});
		file.add(newProject);
		
		// openProject
		openProject = new JMenuItem("Open Project");
		openProject.setToolTipText("Open an existing project");
		openProject.addActionListener(new ActionListener() {
			public  void  actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int fileChooser = chooser.showOpenDialog( null);
				if (fileChooser == JFileChooser.APPROVE_OPTION)
				{
					File file = chooser.getSelectedFile();
					problemDescriptionPanel.textArea.setText( options.openProject( file).getProblemDescription() );
				}
				
				}});
		file.add(openProject);
		
		// saveProject
		saveProject = new JMenuItem("Save Project");
		saveProject.setToolTipText("Save the active project");
		saveProject.addActionListener(new ActionListener() {
			public  void  actionPerformed(ActionEvent e) 
			{
				String saveName;
				saveName = JOptionPane.showInputDialog ("Enter the name of the project: ");
				if (saveName != null)
				{
					options.saveProject( saveName);
				}
			}});
		file.add(saveProject);
		
		////// Edit Options
		
		// CutTextBox
		cutTextBox = new JMenuItem("Cut Text Box");
		cutTextBox.setToolTipText("Cut the text box and put it on the Clipboard");
		cutTextBox.addActionListener(new ActionListener() {
			public  void  actionPerformed(ActionEvent e) {
				options.cutBox( options.getProject().getSteps().getSelectedBox().getID());
			}});
		edit.add(cutTextBox);
		
		// CopyTextBox
		copyTextBox = new JMenuItem("Copy Text Box");
		copyTextBox.setToolTipText("Copy the selection to the clipboard");
		copyTextBox.addActionListener(new ActionListener() {
			public  void  actionPerformed(ActionEvent e) {
				options.copyBox( options.getProject().getSteps().getSelectedBox().getID());
			}});
		edit.add(copyTextBox);
		
		// PasteTextBox
		pasteTextBox = new JMenuItem("Paste Text Box");
		pasteTextBox.setToolTipText("Insert clipboard contents");
		pasteTextBox.addActionListener(new ActionListener() {
			public  void  actionPerformed(ActionEvent e) {
				try
				{
					options.pasteBox( options.getProject().getSteps().getSelectedBox().getNextID());
				}
				catch( Exception exception)
				{
					options.pasteBox( options.getProject().getSteps().getLastStep().getNextID() );
				}
			}});
		edit.add(pasteTextBox);
		
		// ChangeFontSize
		changeFontSize = new JMenuItem("Change Font Size");
		changeFontSize.setToolTipText("Change the font size of the statement box");
		changeFontSize.addActionListener(new ActionListener() {
			public  void  actionPerformed(ActionEvent e) {
				String stringFontSize = JOptionPane.showInputDialog ("Enter the new size: ");
				if ( stringFontSize != null)
				{
					try{
					int fontSize = Integer.parseInt( stringFontSize);
					options.changeFontSize( fontSize);
					}
					catch (Exception exception)
					{
						JOptionPane.showMessageDialog(null, "Please enter an integer value");
					}
				}
			}});
		edit.add(changeFontSize);
		
		// ChangeVariable
		changeVariable = new JMenuItem("Change Variable");
		changeVariable.setToolTipText("Change the variable name");
		changeVariable.addActionListener(new ActionListener() {
			public  void  actionPerformed(ActionEvent e) {
				String oldName, newName;
				
				oldName = JOptionPane.showInputDialog ("Enter the name of the variable that you want to change: ", "Remove Variable");
				newName = JOptionPane.showInputDialog ("Enter the new name of it: ", "Remove Variable");
				
				options.changeVariable( oldName, newName);
				}});
		edit.add(changeVariable);
		
		////// Add / Remove Options
		
		// NewBox
		newBox = new JMenuItem("New Box");
		newBox.setToolTipText("Creat a new box");
		newBox.addActionListener(new ActionListener() {
			public  void  actionPerformed(ActionEvent e) {
				options.addBox( options.getProject().getSteps().getLastStep().getNextID());
			}});
		addRemove.add(newBox);
		
		// NewVariable
		newVariable = new JMenuItem("New Variable");
		newVariable.setToolTipText("Creat a new variable");
		newVariable.addActionListener(new ActionListener() {
			public  void  actionPerformed(ActionEvent e) {
				String variableName;
				
				variableName = JOptionPane.showInputDialog ("Enter the name of the variable you want to add: ",  "Add Variable");
				options.addVariable( new Variable( variableName) );
			}});
		addRemove.add(newVariable);
		
		// RemoveVariable
		removeVariable = new JMenuItem("Remove Variable");
		removeVariable.setToolTipText("Remove a variable");
		removeVariable.addActionListener(new ActionListener() {
			public  void  actionPerformed(ActionEvent e) {
				String variableName;
			
				variableName = JOptionPane.showInputDialog ("Enter the id number (without periods) of the box you want to remove: ", "Remove Box" );
			
				options.removeVariable( variableName);
			}});
		addRemove.add(removeVariable);
		
		// addNewMethod
		addNewMethod = new JMenuItem("Add New Method");
		addNewMethod.setToolTipText("Add a new method");
		addRemove.add(addNewMethod);
		addNewMethod.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e) {
				
				PCDMethod newMethod;
				MethodList methodList;
				
				try {
					FileInputStream fis = new FileInputStream( "methods.met");
					ObjectInputStream ois = new ObjectInputStream( fis);
					
					// Typecast is needed because ObjectInputStream's readObject() method returns a generic Object
					methodList = (MethodList) ois.readObject();
					
					// Closes the file
					ois.close();
					
					NewMethodPanel newMethodPanel = new NewMethodPanel();
					
					selectedMethod = JOptionPane.showConfirmDialog(null, newMethodPanel, "New Method", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("add.png"));
					
					if( selectedMethod == JOptionPane.YES_OPTION)
					{	
						newMethodPanel.saveMethod();
						newMethod = newMethodPanel.getNewMethod() ;
						methodList.addMethod( newMethod);
					}
					
					FileOutputStream fos = new FileOutputStream( "methods.met" );
					ObjectOutputStream oos = new ObjectOutputStream ( fos);
					
					oos.writeObject( methodList);
					oos.close();
							
				}
				catch( FileNotFoundException exp) {
					methodList = new MethodList();
					
					NewMethodPanel newMethodPanel = new NewMethodPanel();
					
					
					int selectedMethod = JOptionPane.showConfirmDialog(null, newMethodPanel, "New Method", JOptionPane.YES_NO_OPTION);
					
					newMethodPanel.saveMethod();
					newMethod = newMethodPanel.getNewMethod() ;
					
					methodList.addMethod( newMethod);
					
					try
					{
						FileOutputStream fos = new FileOutputStream( "methods.met" );
						ObjectOutputStream oos = new ObjectOutputStream ( fos);
						
						oos.writeObject( methodList);
						oos.close();
					}
					catch( Exception exception){
					
					
					// If error exists then prints the information
					exception.printStackTrace();
					}
				}
				catch( Exception ex)
				{
					ex.printStackTrace();
				}
		}});
		
		// insertMethod ////////////////////////////////////////////////////
		insertMethod = new JMenuItem("Insert Method");
		insertMethod.setToolTipText("Insert an existing method");
		insertMethod.addActionListener(new ActionListener() {
			public  void  actionPerformed(ActionEvent e) {
				
				MethodList methods;
				
				try {
					FileInputStream fis = new FileInputStream( "methods.met");
					ObjectInputStream ois = new ObjectInputStream( fis);
					
					// Typecast is needed because ObjectInputStream's readObject() method returns a generic Object
					MethodList methodList = (MethodList) ois.readObject();
					
					// Closes the file
					ois.close();
					MethodListPanel mlp = new MethodListPanel( methodList);
					selectedMethod = JOptionPane.showConfirmDialog(null, mlp, "PCDMethod List", JOptionPane.YES_NO_OPTION);
					
					methods = mlp.getSelectedMethods();
					options.insertMethod( methods);
				}
				catch( FileNotFoundException fnfe)
				{
					JOptionPane.showMessageDialog(null, "Default file methods.met is not found in default directory");
				}
				catch( Exception m) {
					m.printStackTrace();
				}
			}});
		addRemove.add(insertMethod);
		

		
		////// View Options
		
		// AlgorithmVisualDisplay
		algorithmVisualDisplay = new JMenuItem("Algorithm Visual Display");
		algorithmVisualDisplay.setToolTipText("Visual Display of the algorithm");
		algorithmVisualDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showOptionDialog(null, new MainArea( options.getProject(), 500, 500), "Algorithm Visual Display", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
			}});
		view.add(algorithmVisualDisplay);
		
		// RunThePseudoCode
		runThePseudoCode = new JMenuItem("Run the Pseudo Code");
		runThePseudoCode.setToolTipText("Runs the psuedo code");
		runThePseudoCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				JOptionPane.showOptionDialog(null, new RPCPanel( options.getProject()),"Run the Pseudo Code", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
		}});
		view.add(runThePseudoCode);
		
		// Help
		help = new JMenuItem("Help");
		help.setToolTipText("Show help");
		help.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showOptionDialog(null, new HelpPanel(),"Help", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
			}});
		view.add(help);
	}
	
		// Get method for the selectedMethod
		public int getSelectedMethod()
		{
			if ( selectedMethod == JOptionPane.YES_OPTION)
				return 1;
			else
				return 0;
		}
}
