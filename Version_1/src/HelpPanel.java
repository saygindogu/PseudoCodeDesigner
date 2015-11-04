import java.awt.Dimension;
import java.io.*;


import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;




public class HelpPanel extends JPanel 
{
	JScrollPane scrollPane;
	JEditorPane editorPane;
	StringBuffer content;
	
	public HelpPanel()
	{
		try {
			editorPane = new JEditorPane( "http://ug.bcc.bilkent.edu.tr/~kerim.komurcu/Help.htm" );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		scrollPane = new JScrollPane( editorPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		scrollPane.setPreferredSize( new Dimension( 800, 800) );
		scrollPane.getVerticalScrollBar().setUnitIncrement( 13);
		editorPane.setEditable( false);
		add( scrollPane);
	}
}
