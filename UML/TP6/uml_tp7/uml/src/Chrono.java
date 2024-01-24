
class Chrono implements IEvenement {
  private Automate controleur;

  public Chrono(Automate controleur) {
    this.controleur = controleur;
  }

  public void lancerChrono() {
    System.out.println("Je lance le chrono");
  }

  public void stopperChrono() {
    System.out.println("J'arrete le chrono");
  }

  @Override
  public void go() {
    this.controleur.go();
  }

  @Override
  public void stop() {
    this.controleur.stop();
  }

}
