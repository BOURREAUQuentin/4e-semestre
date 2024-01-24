import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Stock {

    private final int NBROUES = 20;
    private final int NBMOTEUR = 5;
    private final int NBCARROSSERIE = 3;

    private int carrosserie;
    private int roues;
    private int moteur;

    private Lock lockMoteur;
    private Lock lockCarro;
    private Lock lockRoues;

    private Condition condMoteurFull;
    private Condition condMoteurEmpty;

    private Condition condRouesFull;
    private Condition condRouesEmpty;

    private Condition condCarroFull;
    private Condition condCarroEmpty;

    public Stock() {
        lockMoteur = new ReentrantLock();
        lockCarro = new ReentrantLock();
        lockRoues = new ReentrantLock();
        
        condMoteurFull = lockMoteur.newCondition();
        condMoteurEmpty = lockMoteur.newCondition();

        condRouesFull = lockRoues.newCondition();
        condRouesEmpty = lockRoues.newCondition();

        condCarroFull = lockCarro.newCondition();
        condCarroEmpty = lockCarro.newCondition();
    }

    public void addMoteur() throws InterruptedException {
        lockMoteur.lock();
        while (moteur >= NBMOTEUR) {
        condMoteurFull.await();
        }
        moteur += 1;
        System.out.println("\u001B[32;1m MOTEUR ajouté \t Stock MOTEUR = " + moteur + "\u001B[0m \n");
        condMoteurEmpty.signal();
        lockMoteur.unlock();
    }

    public void getMoteur() throws InterruptedException {
        lockMoteur.lock();
        while (moteur == 0) {
        condMoteurEmpty.await();
        }
        moteur -= 1;
        System.out.println("\u001B[32;1m MOTEUR retiré \t Stock MOTEUR = " + moteur + "\u001B[0m \n");
        condMoteurFull.signal();
        lockMoteur.unlock();
    }

    public void addCarrosserie() throws InterruptedException {
        lockCarro.lock();
        while (carrosserie >= NBCARROSSERIE) {
        condCarroFull.await();
        }
        carrosserie += 1;
        System.out.println("\u001B[31;1m CARROSSERIE ajoutée \t Stock CARROSSERIE = " + carrosserie + "\u001B[0m \n");
        condCarroEmpty.signal();
        lockCarro.unlock();
    }

    public void getCarrosserie() throws InterruptedException {
        lockCarro.lock();
        while (carrosserie == 0) {
        condCarroEmpty.await();
        }
        carrosserie -= 1;
        System.out.println("\u001B[31;1m CARROSSERIE retiré \t Stock CARROSSERIE = " + carrosserie  + "\u001B[0m \n");
        condCarroFull.signal();
        lockCarro.unlock();
    }

    public void addRoues() throws InterruptedException {
        lockRoues.lock();
        while (roues >= NBROUES) {
        condRouesFull.await();
        }
        roues += 1;
        System.out.println("\u001B[30;1m ROUE ajoutée \t Stock ROUE = " + roues + "\u001B[0m \n");
        if (roues >= 4) condRouesEmpty.signal();
        lockRoues.unlock();
    }

    public void getRoues() throws InterruptedException {
        /*lockRoues.lock();
            while(roues<4){
                condRouesEmpty.await();
            }
            System.out.println("4 ROUES retirées \t Stock ROUE \t="+roues);
            roues -=4;
            condRouesFull.signal();
            lockRoues.unlock();
            */
        getRoues(4);
    }

    public void getRoues(int nbR) throws InterruptedException {
        lockRoues.lock();
        while (roues < nbR) {
        condRouesEmpty.await();
        }
        System.out.println("\u001B[30;1m " + nbR + " ROUES retirées \t Stock ROUE = " + roues + "\u001B[0m \n");
        roues -= nbR;
        condRouesFull.signal();
        lockRoues.unlock();
    }
}

