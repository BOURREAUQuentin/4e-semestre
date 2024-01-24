import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    public Socket socketClient;
    private BufferedReader reponseServeur;
    private PrintWriter ecrivainClient;
    private BufferedReader messageConsoleClient;

    public ClientHandler(Socket socketClient){
        this.socketClient = socketClient;
        try{
            this.reponseServeur = new BufferedReader(new InputStreamReader(this.socketClient.getInputStream()));
            this.ecrivainClient = new PrintWriter(this.socketClient.getOutputStream(), true);
            this.messageConsoleClient = new BufferedReader(new InputStreamReader(System.in));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            String clientInput;
            while ((clientInput = this.messageConsoleClient.readLine()) != null) {
                ecrivainClient.println(clientInput);
                System.out.println(reponseServeur.readLine());
            }
            this.reponseServeur.close();
            this.ecrivainClient.close();
            this.socketClient.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }  
    }
}
