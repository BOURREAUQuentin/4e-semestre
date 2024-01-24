import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Stock {
    private int carrosserie = 0;
    private int moteur = 0;
    private int roue = 0;

    final Lock lockCarroserie = new ReentrantLock();
    final Condition condFullCarrosserie = lockCarroserie.newCondition();
    final Condition condEmptyCarroseserie = lockCarroserie.newCondition();

    final Lock lockMoteur = new ReentrantLock();
    final Condition condFullMoteur = lockMoteur.newCondition();
    final Condition condEmptyMoteur = lockMoteur.newCondition();

    final Lock lockRoue = new ReentrantLock();
    final Condition condFullRoue = lockRoue.newCondition();
    final Condition condEmptyRoue = lockRoue.newCondition();

    public Stock(){
    }

    public void prendreCarrosserie() throws InterruptedException {
        lockCarroserie.lock();
        while (this.carrosserie == 0){
            condEmptyCarroseserie.await();
        }
        this.carrosserie -= 1;
        System.out.println("Carrosserie prise");
        condFullCarrosserie.signal();
        lockCarroserie.unlock();
    }

    public void prendreMoteur() throws InterruptedException {
        lockMoteur.lock();
        while (this.moteur == 0){
            condEmptyMoteur.await();
        }
        this.moteur -= 1;
        System.out.println("Moteur pris");
        condFullMoteur.signal();
        lockMoteur.unlock();
    }

    public void prendreRoue() throws InterruptedException {
        lockRoue.lock();
        while (this.roue == 0){
            condEmptyRoue.await();
        }
        this.roue -= 1;
        System.out.println("Roue pris");
        condFullRoue.signal();
        lockRoue.unlock();
    }

    public void ajouteRoue() throws InterruptedException {
        lockRoue.lock();
        while (this.roue >= 20){
            condFullRoue.await();
        }
        this.roue += 1;
        System.out.println("Roue ajouté");
        condEmptyRoue.signal();
        lockRoue.unlock();
    }

    public void ajouteCarrosserie() throws InterruptedException {
        lockCarroserie.lock();
        while (this.carrosserie >= 3){
            condFullCarrosserie.await();
        }
        this.carrosserie += 1;
        System.out.println("Carrosserie ajoutée");
        condEmptyCarroseserie.signal();
        lockCarroserie.unlock();
    }

    public void ajouteMoteur() throws InterruptedException {
        lockMoteur.lock();
        while (this.moteur >= 5){
            condFullMoteur.await();
        }
        this.moteur += 1;
        System.out.println("Moteur ajouté");
        condEmptyMoteur.signal();
        lockMoteur.unlock();
    }
}
