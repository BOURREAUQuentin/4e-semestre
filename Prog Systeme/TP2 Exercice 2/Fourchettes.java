import java.util.Arrays;

public class Fourchettes {
    
    /** tableau d'occupation des fourchettes false = occupee, true = libre*/
    boolean[] lesFourchettes;
    /** nb de fourchettes*/
    int taille;
   
    /** constructeur initialisant la taille et le tableau des fourchettes a true*/
    public Fourchettes(int nb) {
        lesFourchettes = new boolean[nb];
        Arrays.fill(lesFourchettes, true);
        taille = nb;
    }

    /** fonction appelee par un processus philosophe i. <br>
     * Si la fourchette de droite (i) et de gauche (i+1) est libre
    * alors le philosophe les prend,
    * sinon, il est mis en attente*/
    public synchronized void prendre(int no) {  
        int droite = no;
        int gauche = (no+1)%taille;          
        while (lesFourchettes[gauche]==false || lesFourchettes[droite]==false) {            
            try   {  
                wait();  
            }
            catch (InterruptedException e) {}
        }
        lesFourchettes[gauche] = false;
        lesFourchettes[droite] = false;
    }

    /** fonction appelee par un processus philosophe i. <br>
    * libere la fourchette de droite (i) et de gauche (i+1) <br>
    * et reveille les processus en attente sur les fourchettes*/
    public synchronized void deposer(int no) {
        int droite = no;
        int gauche = (no+1)%taille;          
        lesFourchettes[gauche] = true;
        lesFourchettes[droite] = true;
        notifyAll(); // reveille les processus en attente de fourchettes
    }      
    
}
