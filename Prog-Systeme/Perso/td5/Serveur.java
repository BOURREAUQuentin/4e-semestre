import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Serveur{
    private ServerSocket socket;
    private List<ClientHandler> lesClients = new ArrayList<>();

    public Serveur(ServerSocket socket) {
        this.socket = socket;
    }

    public void candidats(){
        System.out.println("action candidats");
    }

    public void lancer(){
        try{
            while (true){
                Socket socketClient = this.socket.accept();
                ClientHandler clientHandler = new ClientHandler(socketClient);
                System.out.println("Nouveau client connecté au serveur !");
                clientHandler.start();
                lesClients.add(clientHandler);
                for (ClientHandler clientConnecte : lesClients){
                    PrintWriter reponseServeur = new PrintWriter(client.socket.getOutputStream(), true);
                    BufferedReader lecteurClient = new BufferedReader(new InputStreamReader(client.socket.getInputStream()));
                    String message;
                    while ((message = lecteurClient.readLine()) != null) {
                        System.out.println("Message reçu du client : " + message);
                        try{
                            if (message.equals("candidats")){
                                this.candidats();
                            }
                            else{
                                reponseServeur.println("Pas d'action associée !");
                            }
                        }
                        catch (Exception e){
                            reponseServeur.println("Erreur : " + e.getMessage());
                        }
                    }
                    reponseServeur.close();
                    lecteurClient.close();
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Serveur serveur = new Serveur(new ServerSocket(12345));
            System.out.println("Le serveur est à l'écoute sur le port 12345");
            serveur.lancer();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
