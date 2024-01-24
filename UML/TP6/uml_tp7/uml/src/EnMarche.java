
class EnMarche implements IEtat {
  @Override
  public void go(Automate a) {
      a.setEtatCourant(new EnMarche());
      a.getControle().lancerChrono();
  }

  @Override
  public void stop(Automate a) {
      a.setEtatCourant(new Arret());
      a.getControle().stopperChrono();
  }

}
