import java.util.ArrayList;
import java.util.List;

public class Data {

    int size = 2;
    private List<String> messages = new ArrayList<String>();
   
    public synchronized String recuperer(){
        while (messages.size() == 0){
            try {
                    wait();
            } catch (InterruptedException e) {}
        }

        System.out.println("\u001B[33m Message recupéré \u001B[0m \n ");
        String message = messages.get(0);
        messages.remove(0);
        notify();
        return message;     
    }

    public synchronized void ajouter(String s){
        while (messages.size()==size){
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        System.out.println("\u001B[33m Message ajouté "+s + "\u001B[0m \n ");
        messages.add(s);
        notify();
    }
    
}
