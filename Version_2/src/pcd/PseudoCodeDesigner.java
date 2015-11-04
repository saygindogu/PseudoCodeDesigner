package pcd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class PseudoCodeDesigner extends JFrame {

	//Properties
	public static final String DEFAULT_SAVE_DIRECTORY = "PseudoCodeDesigner Saved Files";
	public static File userDefinedSaveDir = new File( DEFAULT_SAVE_DIRECTORY );
	private JPanel contentPane, splitPanePanel;
	private Project project;
    private Toolkit tk;
    private int xSize;
    private int ySize;
    private JSplitPane splitPane;
    private MainAreaPane main;
    private int xPartition;
    private int yPartition;
    private Color color;
    private ImageIcon collapsed;
	private ImageIcon expanded;
	
	//Constructors
	public PseudoCodeDesigner(){
		super( "PseudoCode Designer");
		
		expanded = new ImageIcon( "Images/expand2.png");
		collapsed = new ImageIcon( "Images/expand1.png");
		UIManager.put("Tree.collapsedIcon", collapsed);
	    UIManager.put("Tree.expandedIcon", expanded);
	    
		color = new Color(133, 133, 133);
	    
		project = new Project();
		contentPane = new JPanel();
		
		tk = Toolkit.getDefaultToolkit();  
		xSize = ((int) ( (tk.getScreenSize().getWidth()) * 0.990 ));  
		ySize = ((int) ( (tk.getScreenSize().getHeight() ) * 0.900 ) );
		
		xPartition = xSize / 7;
		yPartition = ySize / 8;
		
		main = new MainAreaPane( project, true);
		
		splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, new LeftAreaPanel(project, xPartition * 1, yPartition * 7, color ),  main);
		setExtendedState( getExtendedState() | JFrame.MAXIMIZED_BOTH);
		
		
		contentPane.setLayout( new BorderLayout() );
		contentPane.add( new OptionsPanel(project, main.getTree(), this, color ), BorderLayout.NORTH );
		contentPane.add( splitPane );
		
		setContentPane(contentPane);
		addWindowListener(new ExitListener());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible( true);
		setTitle( "Pseudo Code Designer - " + project.getName());
	}
	
	//Methods
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run(){
				new PseudoCodeDesigner();
			}
		});
	}
	
	public void setProject( Project p){
		project = p;
		
		contentPane.removeAll();
		
		main = new MainAreaPane( project, false);
		
		contentPane.add( new OptionsPanel(project, main.getTree(), this, color ), BorderLayout.NORTH );
		contentPane.add( new LeftAreaPanel(project, xPartition * 1, yPartition * 7, color ), BorderLayout.WEST );
		contentPane.add( main);
		contentPane.setBackground(color);
		revalidate();
	}
	
	@Override
	public void revalidate(){
		setTitle( "Pseudo Code Designer - " + project.getName());
		super.revalidate();
	}
	
	public class ExitListener extends WindowAdapter
	{	
		@Override
		public void windowClosing(WindowEvent e) {
			int choice = JOptionPane.showConfirmDialog(null, "Do you want to save existing project?", "Save Resource", JOptionPane.YES_NO_CANCEL_OPTION);
			if( choice == JOptionPane.YES_OPTION) {
				new OptionsGUIListener(project, project.getTree(), PseudoCodeDesigner.this).actionPerformed( new ActionEvent(this, 0, OptionsGUI.SAVE_PROJECT));
				System.exit(0);
			}
			else if ( choice != JOptionPane.CANCEL_OPTION && choice != JOptionPane.CLOSED_OPTION)
			{	
				System.exit(0);
			}
		}
	}

}
