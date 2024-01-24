import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class Chrono implements Runnable {
	private int x, y, diametre;
	private JComponent proprietaire;
	private Thread deroulement;
	private long tempsEcoule = 0; // exprime en millisecondes
	private long duree; // nombre de millisecondes pour un tour complet
	private long momentDebut = 0;
	private long momentSuspension;
    private boolean finir;
    private boolean suspendu=false;

	/* - proprietaire donne le composant devant contenir l'image du chronometre.
	 * - duree donne le temps en secondes mis pour que le chronometre fasse un tour complet,
	 * apres ce temps, le chronometre s'arrete.
	 * - x et y indiquent  les coordonnees du coin superieur gauche du carre 
	 * circonscrit au chronometre
	 *- diametre indique le diametre du chronometre*/
	public Chrono(JComponent proprietaire, int duree, int x, int y, int diametre) {
		this.duree = duree * 1000;
		this.x = x;
		this.y = y;
		this.diametre = diametre;
		this.proprietaire = proprietaire;

	}

    //=========OPERATIONS SUR CHRONO===================
    
	/* Demarre le chronometre */
	public void lancerChrono()  {
		deroulement = new Thread(this);
		deroulement.start();
	}

    public synchronized void stopperChrono() {
        suspendu=false;
        finir = true;
        notifyAll();
    }
    
	/* Réinitialisation */
	public void initChrono(){
		tempsEcoule = 0;
		proprietaire.repaint(new Rectangle(x, y, diametre, diametre));
	}


	/* Suspend le deroulement du temps ; ce deroulement pourra etre repris 
	 * dans l'etat ou il se trouvait par la methode reprendre */
	public void suspendreChrono() {
        suspendu=true;
        momentSuspension = System.currentTimeMillis();
	}

		/* Si le chronometre est en fonctionnment mais a ete suspendu, 
	 * il recommence a tourne r*/ 
	public synchronized  void reprendreChrono() {
        suspendu=false;
        momentDebut +=  System.currentTimeMillis() - momentSuspension;
        notifyAll();
	}

    
	/* Fait tourner le chronometre */
	public void run() {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		finir = false;
		momentDebut = System.currentTimeMillis();
		while((tempsEcoule < duree) && (!finir))
		{
			tempsEcoule = System.currentTimeMillis() - 
			momentDebut;
			proprietaire.repaint(new Rectangle(x, y, diametre, diametre));
			try {
				Thread.sleep(200);
				synchronized(this) {
					while (suspendu && !finir) wait();
				}
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	/* Dessine le chronometre selon le temps pendant lequel il a tourne  depuis qu'il a ete mis en fonctionnement */
	public void dessine(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillArc(x, y, diametre, diametre, 90,
				(int)(360 - tempsEcoule * 360 / duree));
		g.setColor(Color.RED);
		g.fillArc(x, y, diametre, diametre,90,
				(int)(-tempsEcoule * 360 / duree));
	}
}
