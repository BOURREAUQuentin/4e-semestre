public class Main {
    public static void main(String[] args) {
        Data data = new Data();
        Producteur producteur1 = new Producteur(data, 1);
        Consommateur consommateur1 = new Consommateur(data, 1);
        Producteur producteur2 = new Producteur(data, 2);
        Consommateur consommateur2 = new Consommateur(data, 2);

        producteur1.start();
        consommateur1.start();
        producteur2.start();
        consommateur2.start();
    }
}
