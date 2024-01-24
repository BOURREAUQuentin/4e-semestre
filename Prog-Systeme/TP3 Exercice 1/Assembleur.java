public class Assembleur extends Thread {

    private Stock stock; 
    private int nbVoiture = 0;

    public Assembleur(Stock stock) {
        this.stock = stock;
    }

    public void run() {
        try {
            while (true) {
                stock.getMoteur();
                stock.getCarrosserie();
                stock.getRoues();
                try {
                    Thread.sleep(5000);
                } 
                catch (Exception e) {
                    e.printStackTrace();
                }
                nbVoiture++;
                System.out.println("\u001B[36;1m INFORMATION ASSEMBLAGE !!!!! VOITURE SORTIE DE L'USINE  !!!  \t Stock VOITURE = " + nbVoiture + "\u001B[0m \n");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}