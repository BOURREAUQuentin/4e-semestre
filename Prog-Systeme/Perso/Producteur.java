public class Producteur extends Thread {
    private Data data;
    private int id;

    public Producteur(Data data, int id){
        this.data = data;
        this.id = id;
    }

    public void run(){
        String messages[] = {"liam", "hamza", "koba", "zola", "favé", "damso", "ajna"};
        for (int i = 0; i < messages.length; i++) {
            this.data.ajouter(messages[i]);
            System.out.println("Producteur " + this.id + " à ajouté : " + messages[i]);
        }
    }
}
