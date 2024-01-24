public class MainLivre {
    public static void main(String[] args) {
        Lecteur lecteur = new Lecteur();
        Ecrivain ecrivain = new Ecrivain();
        lecteur.start();
        ecrivain.start();
    }
}
