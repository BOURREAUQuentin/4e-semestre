import java.util.ArrayList;
import java.util.List;

public class Data{
    private List<String> messages;

    public Data() {
        this.messages = new ArrayList<>();
    }

    public synchronized void ajouter(String message) {
        while (this.messages.size() > 2){
            try{wait();}
            catch (InterruptedException e) {}
        }
        notify();
        this.messages.add(message);

    }

    public synchronized String recuperer() throws InterruptedException {
        while (this.messages.size() == 0) {
            try{wait();System.out.println("Aucun message en cours");}
            catch (InterruptedException e) {}
        }
        String message = this.messages.get(0) + "\n";
        this.messages.remove(0);
        notify();
        return message;
    }
}