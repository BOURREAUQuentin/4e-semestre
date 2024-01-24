
class Suspendu implements IEtat {
    private static Suspendu instance = null;

    public static Suspendu getInstance(){
        if (instance == null){
            instance = new Suspendu();
        }
        return instance;
    }

    @Override
    public void goStop(Automate a) {
        a.setEtatCourant(EnMarche.getInstance());
        a.getControle().reprendreChrono();
    }
  
    @Override
    public void clear(Automate a) {
        a.setEtatCourant(Arret.getInstance());
        a.getControle().stopperChrono();
        a.getControle().initChrono();
    }
  
  }
  