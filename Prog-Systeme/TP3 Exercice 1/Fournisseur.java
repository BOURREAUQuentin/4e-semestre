import java.util.Random;

class Fournisseur extends Thread {

    private int type;
    private Stock stock;

    public Fournisseur(int type, Stock stock) {
        this.type = type;
        this.stock = stock;
    }

    public void run() {
        Random rand = new Random();
        while (true) {
            try 
            {
                if (type == 1) {
                    stock.addRoues();
                    System.out.println("\u001B[30m f1 : roue ajoutée au stock \u001B[0m \n"); // noir
                } 
                else if (type == 2) {
                    stock.addMoteur();
                    System.out.println("\u001B[32m f2 : moteur ajouté au stock \u001B[0m \n"); //vert
                } 
                else {
                    stock.addCarrosserie();
                    System.out.println("\u001B[31m f3: carroserie ajoutée au stock \u001B[0m \n"); //rouge
                }

                Thread.sleep(rand.nextInt(10) * 1000);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
