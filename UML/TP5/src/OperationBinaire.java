
abstract class OperationBinaire extends Element {
  private Element lefElement;

  private Element rightElement;

  public abstract void addLeft(Element element) ;

  public abstract double evaluer() ;

  public abstract void addRight(Element element) ;
}
