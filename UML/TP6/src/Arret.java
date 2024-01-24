
class Arret implements IEtat {
  private static Arret instance = null;

  public static Arret getInstance() {
    if (instance == null) {
      instance = new Arret();
    }
    return instance;
  }

  @Override
  public void goStop(Automate a) {
    a.setEtatCourant(EnMarche.getInstance());
    a.getControle().lancerChrono();
  }

  @Override
  public void clear(Automate a) {
      // pas d'evenement dans clear lorsque le chrono est arrêté
  }

}
