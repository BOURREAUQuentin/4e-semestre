import java.util.Random;

public class Fournisseur extends Thread{
    private Stock stock;
    private int typeFournisseur;
    
    public Fournisseur(int typeFournisseur, Stock stock){
        this.stock = stock;
        this.typeFournisseur = typeFournisseur;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try{
                if (this.typeFournisseur == 1) {
                this.stock.ajouteCarrosserie();
                }
                else if (this.typeFournisseur == 2) {
                    this.stock.ajouteMoteur();
                }
                else{
                    this.stock.ajouteRoue();
                }
                Thread.sleep(random.nextInt(10) * 1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
