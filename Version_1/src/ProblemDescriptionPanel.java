/**
 * @(#)ProblemDescriptionPanel.java
 *
 *
 * @author R2D2
 * @version 1.00 2013/5/10
 */
import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;

public class ProblemDescriptionPanel extends JPanel {
	
	private JLabel label;
	private JButton edit;
	protected JTextArea textArea;
	private JPanel textPanel;
	private Font font;
	private Project project;
	private ImageIcon editIcon;
	
    public ProblemDescriptionPanel( Project pro) {
    	
    	font = new Font( "Angsana New", Font.PLAIN, 28 );
    	
    	project = pro;
    	setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
    	textPanel = new JPanel();
    	//textPanel.setBackground(  Color.BLACK);
    	
    	textArea = new JTextArea( pro.getProblemDescription() , 3, 103);
    	textArea.setEditable( false);
    	textArea.addMouseListener( new ProblemDescriptionMouseListener() );
    	textArea.setFont( font);
    	textArea.setEditable( false);
    	
    	label = new JLabel( "      Problem Description:");
    	label.setFont( font);
    	
    	editIcon = new ImageIcon( "edit.png ");
    	edit = new JButton( editIcon);
    	edit.addActionListener( new EditButtonListener() );
    	
    	textPanel.setPreferredSize( new Dimension ( 50, 50));
    	edit.setSize( 45, 45);
    	add( label);
    	textPanel.add( textArea);
    	add( textPanel);
    	add( edit);
    }
    
    public void clear()
    {
    	textArea.setText("");
    }
    
    private class ProblemDescriptionMouseListener extends MouseAdapter
    {
    	public void mousePressed( MouseEvent e)
    	{
    		if( e.getClickCount() == 2)
    		{
    			if( textArea.isEditable() )
    			{
    					textArea.setEditable( false);
    					textArea.grabFocus();
    					edit.setIcon( editIcon);
    			}
    			else
    			{
    				textArea.setEditable( true);
    				edit.setIcon( new ImageIcon( "done.png") );
    			}
    		}
    	}
    }
    
    private class EditButtonListener implements ActionListener{
    	
    	public void actionPerformed( ActionEvent e)
    	{
    		if( textArea.isEditable() )
    		{
    			textArea.setEditable( false);
    			project.setProblemDescription( textArea.getText() );
    			edit.setIcon( editIcon);
    		}
    		else
    		{
    			edit.setIcon( new ImageIcon( "done.png") );
    			textArea.grabFocus();
    			textArea.setEditable( true);
    		}
    	}
    }
    
    
}