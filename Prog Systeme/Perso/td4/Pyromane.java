public class Pyromane extends Thread{
    private int debut;
    private int fin;
    private Zone zone;

    public Pyromane(int debut, int fin, Zone zone) {
        this.debut = debut;
        this.fin = fin;
        this.zone = zone;
    }

    @Override
    public void run() {
        do{
            try{
                this.zone.getBarriere().await();
                for (int i = debut; i < fin; i++) {
                    for (int j = 0; j < zone.getCarte().length; j++) {
                        this.zone.propagation(i, j);
                    }
                }
                if (debut == 0){
                    this.zone.display();
                }
                Thread.sleep(1000);
                this.zone.getBarriere().await();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        while (this.zone.hasChanged());
    }
}
