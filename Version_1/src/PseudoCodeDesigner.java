/**
 * @(#)PseudoCodeDesigner.java
 *
 * PseudoCodeDesigner application
 *
 * @author R2D2 - Z. Saygýn Doðu
 * @version 1.00 2013/4/18
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class PseudoCodeDesigner {
    
	protected Project project;
    private MainArea mainArea;
    protected JFrame frame;
    private JPanel leftArea;
    private JPanel options;
    private JPanel bottom;
    JPanel panel7;
    Toolkit tk;
    int xSize;
    int ySize;
    
    int xPartition;
	int yPartition;
    
    public PseudoCodeDesigner()
    {
    	project = new Project();

    	tk = Toolkit.getDefaultToolkit();  
		xSize = ((int) ( (tk.getScreenSize().getWidth()) * 0.990 ));  
		ySize = ((int) ( (tk.getScreenSize().getHeight() ) * 0.900 ) );
		
		int xPartition = xSize / 7;
		int yPartition = ySize / 8;

    	leftArea = new LeftAreaPanel( project, xPartition * 1, yPartition * 7 );
    	mainArea = new MainArea( project, xPartition * 6, yPartition * 7 );
    	
    	frame = new JFrame( "Gui test");
    	frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    	
    	OptionsGUI optionsGui = new OptionsGUI( project);
    	options = optionsGui.panel;
    	
    	bottom = new JPanel() ; // bottom panel
		bottom.add( leftArea);
		bottom.add( mainArea);
		
		panel7 = new JPanel() ; // most general Panel
		panel7.setLayout( new BoxLayout( panel7, BoxLayout.Y_AXIS));
		panel7.add( options );
		panel7.add( bottom );
		
		frame.add( panel7 );
    	frame.setVisible( true);
    	frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
    	
    	frame.setLayout( new FlowLayout() );
    	
		options.setPreferredSize( new Dimension( xPartition * 7, yPartition * 1 ));
    	
    }
    

    
    public static void main(String[] args) {

    try {
//		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new SplashScreenMain();
		new PseudoCodeDesigner();

	}
	catch (Exception e) {
		e.printStackTrace();
	}
	
		


	
		
    	
	}
}
