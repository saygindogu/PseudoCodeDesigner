package pcd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
//TODO, bence bu kadar fazla actionListener olması ram için üzücü :D 
//Tek bir actionListener yapıp şu setActionCommand(str) methodunu kullanmak 
//suretiyle ayıralım olayları bakınız StepEditorPanel hem daha düzenli görünüyor benm gözüme
import javax.swing.JTree;

public class OptionsGUI
{
	public final static String NEW_PROJECT				= "New Project";
	public final static String CHANGE_PROJECT_NAME		= "Change Project Name";
	public final static String OPEN_PROJECT				= "Open Project";
	public final static String SAVE_PROJECT				= "Save Project";
	public final static String SAVE_PROJECT_AS			= "Save Project As";
	public final static String CUT_STEP					= "Cut Step";
	public final static String COPY_STEP				= "Copy Step";
	public final static String PASTE_STEP				= "Paste Step";
	public final static String CHANGE_VARIABLE			= "Change Variable";
	public final static String CHANGE_FONT_SIZE			= "Change Font Size";
	public final static String NEW_STEP					= "New Step";
	public final static String NEW_VARIABLE				= "New Variable";
	public final static String REMOVE_VARIABLE			= "Remove Variable";
	public final static String NEW_METHOD				= "New Method";
	public final static String INSERT_METHOD			= "Insert Method";
	public final static String ALGORITHM_VISUAL_DISPLAY	= "Algorithm Visual Display";
	public final static String RUN_THE_PCD 				= "Run the Pseudo Code";
	public final static String CHANGE_DIRECTORY 		= "Change Directory";
	//public final static String HELP 					= "HELP"; // HELP is coming soon in the very next version!!
	
	private OptionsGUIListener 	listener;
	protected JPanel 			panel;
	private JMenuBar 			menuBar;
	private JMenu 				file, edit, addRemove, view, preferences;
	private JToolBar 			fileToolBar, editToolBar, addRemoveToolBar, viewToolBar, preferencesToolBar;
	protected JTextField 		fontSizeTextField;
	private JButton 			newProject, changeProjectName, openProject, saveProject, changeFontSize, 
								changeVariable, copyTextBox, pasteTextBox;
	private JButton 			cutTextBox, newBox, newVariable, removeVariable, 
								insertMethod, addNewMethod;
	private JButton 			algorithmVisualDisplay, runThePseudoCode, help, saveProjectAs;
	private JButton				changeDirectory;
	private int 				selectedMethod;

	
	public OptionsGUI( Project project, JTree tree, PseudoCodeDesigner pcd, Color color)
	{
		listener = new OptionsGUIListener( project, tree, pcd );
		panel = new JPanel();
		panel.setLayout( new BorderLayout());
		panel.setBackground(color);
		menu(color);
	}
	
	public void menu(Color color)
	{
		//MENUBAR
		menuBar = new JMenuBar();
		menuBar.setPreferredSize(new Dimension(1800, 50));
		//menuBar.setBackground( new Color( 190, 158, 156));
		menuBar.setBackground( new Color( 159, 176, 255) );
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
		
		preferences = new JMenu( "Preferences");
		preferences.setMnemonic( 'P');
		menuBar.add( preferences );
		
		//TOOLBARS
		fileToolBar = new JToolBar();
		fileToolBar.setFloatable(false);
		file.add(fileToolBar);

		
		editToolBar = new JToolBar();
		editToolBar.setFloatable(false);
		edit.add(editToolBar);
		
		addRemoveToolBar = new JToolBar();
		addRemoveToolBar.setFloatable(false);
		addRemove.add(addRemoveToolBar);
		
		viewToolBar = new JToolBar();
		viewToolBar.setFloatable(false);
		view.add(viewToolBar);
		
		preferencesToolBar = new JToolBar();
		preferencesToolBar.setFloatable( false);
		preferences.add( preferencesToolBar);
		
		////// File Options
		
		// newProject
		newProject = new JButton(NEW_PROJECT);
		newProject.setToolTipText("Create an empty project");
		newProject.setActionCommand(NEW_PROJECT);
		newProject.addActionListener( listener );
		fileToolBar.add(newProject);
		
		//changeProjectName
		changeProjectName = new JButton( CHANGE_PROJECT_NAME );
		changeProjectName.setToolTipText("Change the current project's name");
		changeProjectName.setActionCommand(CHANGE_PROJECT_NAME);
		changeProjectName.addActionListener( listener );
		fileToolBar.add(changeProjectName);
		
		// openProject
		openProject = new JButton(OPEN_PROJECT);
		openProject.setToolTipText("Open an existing project");
		openProject.setActionCommand(OPEN_PROJECT);
		openProject.addActionListener( listener );
		fileToolBar.add(openProject);
		
		// saveProject
		saveProject = new JButton(SAVE_PROJECT);
		saveProject.setToolTipText("Save the active project to the default directory with it's own name");
		saveProject.setActionCommand(SAVE_PROJECT);
		saveProject.addActionListener( listener );
		fileToolBar.add(saveProject);
		
		//Save Project As
		saveProjectAs = new JButton(SAVE_PROJECT_AS);
		saveProjectAs.setToolTipText("Save the active project");
		saveProjectAs.setActionCommand(SAVE_PROJECT_AS);
		saveProjectAs.addActionListener( listener );
		fileToolBar.add(saveProjectAs);
		
		////// Edit Options
		
		// CutTextBox
		cutTextBox = new JButton(CUT_STEP);
		cutTextBox.setToolTipText("Cut the text box and put it on the Clipboard");
		cutTextBox.setActionCommand(CUT_STEP);
		cutTextBox.addActionListener( listener );
		editToolBar.add(cutTextBox);
		
		// CopyTextBox
		copyTextBox = new JButton(COPY_STEP);
		copyTextBox.setToolTipText("Copy the selection to the clipboard");
		copyTextBox.setActionCommand(COPY_STEP);
		copyTextBox.addActionListener( listener );
		editToolBar.add(copyTextBox);
		
		// PasteTextBox
		pasteTextBox = new JButton(PASTE_STEP);
		pasteTextBox.setToolTipText("Insert clipboard contents");
		pasteTextBox.setActionCommand(PASTE_STEP);
		pasteTextBox.addActionListener( listener );
		editToolBar.add(pasteTextBox);
		
		// ChangeVariable
		changeVariable = new JButton(CHANGE_VARIABLE);
		changeVariable.setToolTipText("Change the variable name");
		changeVariable.setActionCommand(CHANGE_VARIABLE);
		changeVariable.addActionListener( listener );
		editToolBar.add(changeVariable);
		
		editToolBar.add(Box.createRigidArea(new Dimension(15, 10)));
		
		// ChangeFontSize
		changeFontSize = new JButton(CHANGE_FONT_SIZE);
		changeFontSize.setToolTipText("Change the font size of the statement box");
		changeFontSize.setActionCommand(CHANGE_FONT_SIZE);
		changeFontSize.addActionListener( listener );
		editToolBar.add(changeFontSize);
		
		// FontSizeTextField
		fontSizeTextField = new JTextField();
		fontSizeTextField.setPreferredSize(new Dimension(40, 25));
		fontSizeTextField.setActionCommand(CHANGE_FONT_SIZE);
		fontSizeTextField.addActionListener(listener);
		editToolBar.add(fontSizeTextField);
		
		
		
		////// Add / Remove Options
		
		// NewBox
		newBox = new JButton(NEW_STEP);
		newBox.setToolTipText("Creat a new box");
		newBox.setActionCommand(NEW_STEP);
		newBox.addActionListener( listener );
		addRemoveToolBar.add(newBox);
		
		// NewVariable
		newVariable = new JButton(NEW_VARIABLE);
		newVariable.setToolTipText("Creat a new variable");
		newVariable.setActionCommand(NEW_VARIABLE);
		newVariable.addActionListener( listener );
		addRemoveToolBar.add(newVariable);
		
		// RemoveVariable
		removeVariable = new JButton(REMOVE_VARIABLE);
		removeVariable.setToolTipText("Remove a variable");
		removeVariable.setActionCommand(REMOVE_VARIABLE);
		removeVariable.addActionListener( listener );
		addRemoveToolBar.add(removeVariable);
		
		// addNewMethod

		addNewMethod = new JButton(NEW_METHOD);
		addNewMethod.setToolTipText("Add a new method");
		addNewMethod.setActionCommand(NEW_METHOD);
		addNewMethod.addActionListener( listener );
		addRemoveToolBar.add(addNewMethod);
		
		
		// insertMethod ////////////////////////////////////////////////////
		insertMethod = new JButton(INSERT_METHOD);
		insertMethod.setToolTipText("Insert an existing method");
		insertMethod.setActionCommand(INSERT_METHOD);
		insertMethod.addActionListener( listener );
		addRemoveToolBar.add(insertMethod);
		

		
		////// View Options
		
		// AlgorithmVisualDisplay
		algorithmVisualDisplay = new JButton(ALGORITHM_VISUAL_DISPLAY);
		algorithmVisualDisplay.setToolTipText("Visual Display of the algorithm");
		algorithmVisualDisplay.setActionCommand(ALGORITHM_VISUAL_DISPLAY);
		algorithmVisualDisplay.addActionListener( listener );
		viewToolBar.add(algorithmVisualDisplay);
		
		// RunThePseudoCode
		runThePseudoCode = new JButton(RUN_THE_PCD);
		runThePseudoCode.setToolTipText("Runs the psuedo code");
		runThePseudoCode.setActionCommand(RUN_THE_PCD);
		runThePseudoCode.addActionListener( listener );
		viewToolBar.add(runThePseudoCode);
		
		//ChangeDirectory
		changeDirectory = new JButton(CHANGE_DIRECTORY);
		changeDirectory.setToolTipText("Changes the default save directory");
		changeDirectory.setActionCommand(CHANGE_DIRECTORY);
		changeDirectory.addActionListener( listener );
		preferencesToolBar.add(changeDirectory);
		
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

