public class Usine {

    public static void main(String args[]) {
        Stock stock = new Stock();
        Assembleur assembleur = new Assembleur(stock);
        Fournisseur f1 = new Fournisseur(1, stock);
        Fournisseur f2 = new Fournisseur(2, stock);
        Fournisseur f3 = new Fournisseur(3, stock);
        
        assembleur.start();
        f1.start();
        f2.start();
        f3.start();
    }
}
