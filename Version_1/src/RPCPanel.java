import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class RPCPanel extends JPanel {
	
	MainArea mainArea;
	Project project;
	JButton run, stop;
	Timer timer;
	ArrayList<StatementBox> allBoxes;
	boolean processingConditional;
	int subBoxCount, currentIndex, anchorValue, anchorValue2, ifPartCount, elsePartCount, conditionalCount;
	
	public RPCPanel( Project p){

		project = p;
		mainArea = new MainArea(p, 800, 800);
		
		timer = new Timer( 1000, new MyActionListener() );
		processingConditional = false;
		allBoxes = p.getSteps().getAllBoxes();
		run = new JButton( "Run");
		for( StatementBox box : allBoxes )
		{
			box.setExpanded(true);
		}
		stop = new JButton( "Stop");
		
		currentIndex = -1;
		anchorValue = -1;
		anchorValue2 = -1;
		subBoxCount = 0;
		ifPartCount = 0;
		elsePartCount = 0;
		conditionalCount = 0;
		
		add( mainArea);
		run.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				if ( timer.isRunning())
				{
					timer.stop();
				}
				else
					timer.start();
		}});
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				timer.stop();
				currentIndex = 0;
				anchorValue = 0;
				subBoxCount = 0;
		}});
		add( run);
		add( stop);
	}
	
	private class MyActionListener implements ActionListener
	{
		
		public void actionPerformed( ActionEvent e)
		{
			currentIndex++;
			if ( currentIndex < allBoxes.size() )
			{
				StatementBox current = allBoxes.get( currentIndex);
				current.setSelected( true);
			
				if ( processingConditional)
				{
					if ( anchorValue >= 0 && allBoxes.get( anchorValue ) instanceof RepetitionBox)
					{
						if (currentIndex - anchorValue >= subBoxCount)
						{
								currentIndex = anchorValue - 1;
								anchorValue = -1;
								conditionalCount--;
								if( conditionalCount == 0)
								{
									processingConditional = false;
								}
								
						}
					}
					else
					{
						if (currentIndex - anchorValue >= ifPartCount)
						{
							currentIndex = currentIndex + elsePartCount;
							anchorValue = -1;
							conditionalCount--;
							if( conditionalCount == 0)
							{
								processingConditional = false;
							}
						}
					}
				}
				
				if( current instanceof ConditionalBox )
				{
					int decision = JOptionPane.showConfirmDialog( null, new JLabel( ( (ConditionalBox) current ).getCondition() ), "Is Condition True?", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE);
					
					if( conditionalCount % 2 == 0)
						anchorValue2 = currentIndex;
					else
						anchorValue = currentIndex;
					
					if( current instanceof RepetitionBox )
					{
						if ( decision == JOptionPane.NO_OPTION)
						{
							subBoxCount = current.getSubBoxes().getAllBoxes().size();
							currentIndex = currentIndex + subBoxCount;
						}
						else
						{
							subBoxCount = current.getSubBoxes().getAllBoxes().size();
							processingConditional = true;
							conditionalCount++;
						}
					}
					else
					{
						if ( decision == JOptionPane.NO_OPTION)
						{
							ifPartCount = ( (DecisionBox) current ).getIfPart().getAllBoxes().size();
							currentIndex = currentIndex + ifPartCount;
							processingConditional = true;
							conditionalCount++;
						}
						else
						{
							ifPartCount =  ( (DecisionBox) current ).getIfPart().getAllBoxes().size();
							elsePartCount = ( (DecisionBox) current ).getElsePart().getAllBoxes().size();
							processingConditional = true;
							conditionalCount++;
						}
					}
				}
				
				
			}
			else
			{
				timer.stop();
				currentIndex = 0;
				anchorValue = 0;
				subBoxCount = 0;
			}
		}
	}
	
	
}