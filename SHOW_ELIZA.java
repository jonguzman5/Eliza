package Eliza;

public class SHOW_ELIZA {
	public static void main(String[]args){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				ELIZA_GUI ELIZA_GUI = new ELIZA_GUI();
			}
		});
	}
}
