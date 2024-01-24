import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurCalcultatrice {

    public static void addition(PrintWriter serveurReponse, int nb1, int nb2){
        int somme = (nb1 + nb2);
        serveurReponse.println(somme);
    }

    public static void soustraction(PrintWriter serveurReponse, int nb1, int nb2){
        int somme = (nb1 - nb2);
        serveurReponse.println(somme);
    }

    public static void multiplication(PrintWriter serveurReponse, int nb1, int nb2){
        int somme = (nb1 * nb2);
        serveurReponse.println(somme);
    }

    public static void division(PrintWriter serveurReponse, int nb1, int nb2){
        if (nb2 != 0){
            float somme = (nb1 / nb2);
            serveurReponse.println(somme);
        }
        serveurReponse.println("Erreur division par zéro impossible !");
    }
    public static void main(String[] args) {
        try {
            ServerSocket serveurSocket = new ServerSocket(12345);
            System.out.println("Le serveur est à l'écoute sur le port 12345");
            Socket clientSocket = serveurSocket.accept();
            System.out.println("Client connecté au serveur !");
            PrintWriter reponseServeur = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader lecteurClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message;
            while ((message = lecteurClient.readLine()) != null) {
                System.out.println("Message reçu du client : " + message);
                String[] calcul = message.split(" ");
                try{
                    int nb1 = Integer.parseInt(calcul[0]);
                    int nb2 = Integer.parseInt(calcul[2]);
                    if (message.contains("+")){
                        ServeurCalcultatrice.addition(reponseServeur, nb1, nb2);
                    }
                    else if (message.contains("*")){
                        ServeurCalcultatrice.multiplication(reponseServeur, nb1, nb2);
                    }
                    else if (message.contains("-")){
                        ServeurCalcultatrice.soustraction(reponseServeur, nb1, nb2);
                    }
                    else if (message.contains("/")){
                        ServeurCalcultatrice.division(reponseServeur, nb1, nb2);
                    }
                    else{
                        reponseServeur.println("Pas de calcul possible !");
                    }
                }
                catch (Exception e){
                    reponseServeur.println("Pas de calcul possible !");
                }
            }
            lecteurClient.close();
            reponseServeur.close();
            clientSocket.close();
            serveurSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}