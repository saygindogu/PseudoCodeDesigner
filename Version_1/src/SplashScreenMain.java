import javax.swing.UIManager;
import javax.swing.ImageIcon;

/**
 * SplashScreenMain - Main class for SplashScreen with main method.
 * @author G1B R2D2 - Can Mergenci
 * @version 1.00
 */
public class SplashScreenMain {

	SplashScreen screen;

	public SplashScreenMain() {
		// initialize the splash screen
		splashScreenInit();
		// do something here to simulate the program doing something that
		// is time consuming

		for ( int i = 0; i <= 1000; i++ )
		{
			for ( long j = 0; j < 25000; ++j )
			{
				String str = " " + (j + i);
			}
			// run either of these two -- not both
			screen.setProgress("Loading... " + i/10+"%", i/10);  // progress bar with a message
			//screen.setProgress(i);           // progress bar with no message
		}
		splashScreenDestruct();
	}

	private void splashScreenDestruct() {
		screen.setScreenVisible(false);
	}

	private void splashScreenInit() {
		ImageIcon myImage = new ImageIcon(SplashScreenMain.class.getResource("r2d2.png"));
		screen = new SplashScreen(myImage);
		screen.setLocationRelativeTo(null);
		screen.setProgressMax(100);
		screen.setScreenVisible(true);
	}

}
