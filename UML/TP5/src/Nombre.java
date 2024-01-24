
class Nombre extends Element {
  private double valeur;

  public Nombre(double valeur) {
    this.valeur = valeur;
  }

  @Override
  public double evaluer() {
    return this.valeur;
  }

}
