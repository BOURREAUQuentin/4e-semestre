public class Consommateur extends Thread {
    private Data data;
    private int id;

    public Consommateur(Data data, int id){
        this.data = data;
        this.id = id;
    }

    public void run(){
        while (true){
            try {
                String message = this.data.recuperer();
                System.out.println("Consommateur " + this.id + " à utilisé : " + message);
            }
            catch (InterruptedException e) {}
        }
    }
}
