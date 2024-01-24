import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Ressource {
    
    int compteur=0;
    ReadWriteLock verrou = new ReentrantReadWriteLock();

    public void lire(int id) {
        verrou.readLock().lock();
        System.out.println("\u001B[32m Lecteur" + id +"  commence lecture \u001B[0m \n");
          try{
		Thread.sleep(1000);
	    }
	    catch(Exception e){
		e.printStackTrace();
	    }
        compteur--;
        System.out.println("\u001B[32;1m Lecteur" +id +" termine lecture \t" + compteur + "\u001B[0m \n");
        verrou.readLock().unlock();
    }

    public void ecrire(int id)  {
        verrou.writeLock().lock();
        System.out.println("\u001B[31m Ecrivain" + id + " commence ecriture \u001B[0m \n");
        try{
            Thread.sleep(5000);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        compteur++;
        System.out.println("\u001B[31;1m Ecrivain" + id + " termine ecriture \t "+ compteur + "\u001B[0m \n" );
        verrou.writeLock().unlock();
    }

}
