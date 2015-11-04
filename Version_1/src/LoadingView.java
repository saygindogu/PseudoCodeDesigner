//import java.awt.*;
//import javax.swing.*;
//
//class LoadingScreen extends Frame{
//	
//	LoadingScreen(){
//		setPreferredSize( new Dimension(300,200));
//		toFront();
//	}
//	void createGraphics(){
//		SplashScreen splashScreen = SplashScreen.getSplashScreen();
//		Graphics g = splashScreen.createGraphics();
//		//g.fillOval();
//	}
//	
//	public static void main( String[] args){
//		javax.swing.SwingUtilities.invokeLater( new Runnable(){
//			public void run(){
//				new LoadingScreen();
//			}
//		});
//	}
//}