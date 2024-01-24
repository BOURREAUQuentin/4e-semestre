
class EnMarche implements IEtat {
  private static EnMarche instance = null;

  public static EnMarche getInstance(){
    if (instance == null){
      instance = new EnMarche();
    }
    return instance;
  }

  @Override
  public void goStop(Automate a) {
      a.setEtatCourant(Suspendu.getInstance());
      a.getControle().suspendreChrono();
  }

  @Override
  public void clear(Automate a) {
      // n'existe pas d'evenement associé
  }

}
