
class Automate implements IEvenement {
  private IEtat etatCourant;

  private Chrono controle;

  public Automate(IEtat etat) {
    this.etatCourant = etat;
    this.controle = null;
  }

  public void setControle(Chrono controle) {
    this.controle = controle;
  }

  public void setEtatCourant(IEtat etat) {
    this.etatCourant = etat;
  }

  @Override
  public void go() {
    this.etatCourant.go(this);
  }

  @Override
  public void stop() {
    this.etatCourant.stop(this);
  }

  public Chrono getControle() {
    return controle;
  }

}
