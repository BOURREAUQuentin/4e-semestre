import java.util.Random;

class Lecteur extends Thread{

    private Ressource maRessource;
    private static int idC=0;
    private int id;

    public Lecteur(Ressource uneRessource){
	    maRessource =uneRessource;
	    id =idC++;
    }


    public void run(){
	    Random random = new Random();
	
        try{
            while(true){
                maRessource.lire(id);
                try{
                    Thread.sleep(random.nextInt(5)*5000);
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
