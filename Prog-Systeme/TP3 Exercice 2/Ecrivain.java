import java.util.Random;


class Ecrivain extends Thread{

    Ressource maRessource;
    private static int idC=0;
    private int id;

    public Ecrivain(Ressource uneRessource){
	    maRessource = uneRessource;
        id =idC++;
    }


    public void run(){
	    Random random = new Random();

        try{
            while(true){
                maRessource.ecrire(id);
                try{
                    Thread.sleep(random.nextInt(5)*10000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }       
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}