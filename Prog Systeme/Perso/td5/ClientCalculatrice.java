import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientCalculatrice{

    public static void main(String[] args){
        try{
            Socket socketClient = new Socket("192.168.13.95", 12345);
            System.out.println("Connecté au serveur !");
            BufferedReader reponseServeur = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            PrintWriter ecrivainClient = new PrintWriter(socketClient.getOutputStream(), true);
            BufferedReader messageConsoleClient = new BufferedReader(new InputStreamReader(System.in));
            String clientInput;
            while ((clientInput = messageConsoleClient.readLine()) != null) {
                ecrivainClient.println(clientInput);
                System.out.println("Message reçu du serveur : " + reponseServeur.readLine());
            }
            reponseServeur.close();
            ecrivainClient.close();
            socketClient.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("En attente du serveur...");
        }
    }
}