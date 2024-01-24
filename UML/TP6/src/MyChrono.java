import javax.swing.JFrame;


public class MyChrono {
	  public static void main(String[] argv) {   
	      JFrame fenetre = new JFrame();
	      fenetre.setContentPane(new IHMChrono(20));
	      fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      fenetre.setLocation(100, 100);
	      fenetre.pack();
	      fenetre.setVisible(true);
	    }
	}

