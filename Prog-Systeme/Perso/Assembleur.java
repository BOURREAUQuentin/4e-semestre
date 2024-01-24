public class Assembleur extends Thread {
    private Stock stock;
    
    public Assembleur(Stock stock){   
        this.stock = stock;
    }

    @Override
    public void run() {
        while (true) {
            try{
                this.stock.prendreCarrosserie();
                this.stock.prendreMoteur();
                for (int i = 0; i < 4; i++) {
                    this.stock.prendreRoue();
                }
                System.out.println("Voiture crée !");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}