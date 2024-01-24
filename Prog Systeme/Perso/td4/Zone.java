import java.util.concurrent.CyclicBarrier;

public class Zone {
    private int[][] carte;
    private CyclicBarrier barriere;
    private boolean hasChanged;

    public Zone(int[][] carte, CyclicBarrier barriere) {
        this.carte = carte;
        this.barriere = barriere;
        this.carte[0][1] = 2;
        this.carte[1][0] = 3;
        this.hasChanged = false;
    }

    public int[][] getCarte() {
        return carte;
    }

    public CyclicBarrier getBarriere() {
        return barriere;
    }

    public void setChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }

    public boolean hasChanged() {
        return hasChanged;
    }

    public void propagation(int ligne, int colonne){
        int etat = carte[ligne][colonne];
        if (etat == 0){
            int sumEtatsVoisins = 0;
            for (int i=ligne-1; i <= ligne+1; i++) {
                for (int j=colonne-1; j <= colonne+1; j++) {
                    if (i >= 0 && i < carte.length && j >= 0 && j < carte[0].length) {
                        sumEtatsVoisins += carte[i][j];
                    }
                }
            }
            if (sumEtatsVoisins >= 6){
                carte[ligne][colonne] = 1;
                this.hasChanged = true;
            }
        }
        else if (etat != 4){
            carte[ligne][colonne] = etat+1;
            this.hasChanged = true;
        }
        else{
            this.hasChanged = false;
        }
    }

    public void display() {
        for (int i = 0; i < carte.length; i++) {
            for (int j = 0; j < carte[i].length; j++) {
                int etat = carte[i][j];
                String couleur = "\u001B[32m";
                if (etat == 1){
                    couleur = "\u001B[33m";
                }
                else if (etat == 2){
                    couleur = "\u001B[34m";
                }
                else if (etat == 3){
                    couleur = "\u001B[35m";
                }
                else if (etat == 4){
                    couleur = "\u001B[36m";
                }
                System.out.print(couleur + etat + "\u001B[0m|");
            }
            System.out.println();
        }
        System.out.println("\n");
    }
}
