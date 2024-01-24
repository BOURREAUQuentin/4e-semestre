import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) {
        // déterminer le nb de thread possible
        // déclarer une barriere
        // declarer une zone (carte) : taille N de la matrice
        // determiner une taille de zone par thread, size = N / nb
        // list de threads (new Thread(debut, fin, zone))

        int nbThreads = Runtime.getRuntime().availableProcessors();
        System.out.println("Nombre de threads possibles : "+nbThreads);
        CyclicBarrier barriere = new CyclicBarrier(nbThreads);
        Zone zone = new Zone(new int[24][24], barriere);
        int sizeThread = zone.getCarte().length / nbThreads;
        System.out.println("Nombre de lignes gérées par thread : " + sizeThread);

        Pyromane[] pyromanes = new Pyromane[nbThreads];

        for (int i = 0; i < nbThreads; i++) {
            int debut = i * sizeThread;
            int fin = (i + 1) * sizeThread;
            if (i == nbThreads - 1){
                fin = zone.getCarte().length;
            }

            pyromanes[i] = new Pyromane(debut, fin, zone);
            pyromanes[i].start();
        }
    }
}
