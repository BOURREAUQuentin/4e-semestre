import java.util.Random;

public class Consommateur extends Thread {
    
    private Data myData;
    private int id;
    
    public Consommateur (Data d, int id){
        myData = d;   
        this.id = id;
	         
    }

    public void run() {
        
        Random random = new Random();
        while (true) {
            String message = myData.recuperer() ;
            System.out.format("\u001B[31m" + "Consommateur " + this.id + " a lu : %s \u001B[0m \n", message);
            try {
                Thread.sleep(random.nextInt(500));
            } catch (InterruptedException e) {}
        }
    }   

}
