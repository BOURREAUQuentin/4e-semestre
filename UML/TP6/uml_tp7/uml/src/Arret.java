
class Arret implements IEtat {
  @Override
  public void go(Automate a) {
        a.setEtatCourant(new EnMarche());
        a.getControle().lancerChrono();
  }

  @Override
  public void stop(Automate a) {
      // pas d'evenement dans stop lorsque le chrono est arrêté
  }

}
