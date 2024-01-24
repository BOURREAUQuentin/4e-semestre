
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
  public void goStop() {
    this.etatCourant.goStop(this);
  }

  @Override
  public void clear() {
    this.etatCourant.clear(this);
  }

  public Chrono getControle() {
    return controle;
  }

}
