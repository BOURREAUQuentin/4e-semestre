import java.util.Random;

public class Producteur extends Thread{

    private Data myData;
    private int id;
        
    public Producteur (Data d,int id){
        myData = d;   
        this.id = id;

    }

    public void run (){

        String importantInfo[] = {"AB", "CD", "EF", "GH", "IJ", "KL", "MN", "OP", "QR", "ST", "UV", "WX", "YZ"};

        Random random = new Random();

	    for (int i=0; i<13;i++){
            myData.ajouter(importantInfo[i]);
	        System.out.println("\u001B[32m" + "Producteur " + this.id + " a ajoute " + importantInfo[i] + "\u001B[0m \n");
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}
        }
        
    }
    
}
