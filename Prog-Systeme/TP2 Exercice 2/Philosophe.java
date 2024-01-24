import java.util.Random;

public class Philosophe extends Thread{

    private Fourchettes mesFourchettes;
    private int id;
    private int nb_bouchees;
        
    public Philosophe (int id, Fourchettes lesFourchettes,int coup_de_fourchettes){
        mesFourchettes = lesFourchettes;   
        this.id = id;
        nb_bouchees = coup_de_fourchettes;
    }

    public void run (){

        Random random = new Random();
        long debutRepas = System.currentTimeMillis();

	    while (nb_bouchees > 0){
            System.out.println("\u001B[31m" + "Philosophe " + this.id + " : je pense en attendant les fourchettes" + "\u001B[0m \n");
            mesFourchettes.prendre(this.id);  
            System.out.println("\u001B[33m" + "Philosophe " + this.id + " : je mange..." + "\u001B[0m \n"); 
            nb_bouchees = nb_bouchees - 1;       
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}

            mesFourchettes.deposer(this.id);
            System.out.println("\u001B[32m" + "Philosophe " + this.id + " : j'ai depose mes fourchettes et je pense un peu" + "\u001B[0m \n");
            try { 
                Thread.sleep(random.nextInt(5000));}  
            catch (InterruptedException e) {}
               
        }
        long finRepas = System.currentTimeMillis();
        System.out.println("Philosophe " + this.id + " : j'ai fini en " + (float)((float)(finRepas-debutRepas)/1000) + "s...\n");
        
    }
    
}