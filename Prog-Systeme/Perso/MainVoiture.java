public class MainVoiture {
    public static void main(String[] args) {
        Stock stock = new Stock();
        Assembleur assembleur = new Assembleur(stock);
        Fournisseur fournisseurCarroserie = new Fournisseur(1, stock);
        Fournisseur fournisseurMoteur = new Fournisseur(2, stock);
        Fournisseur fournisseurRoues = new Fournisseur(3, stock);
        fournisseurCarroserie.start();
        fournisseurMoteur.start();
        fournisseurRoues.start();
        assembleur.start();
    }
}
