import java.util.List;

abstract class Operation extends Element {
  private List<Element> lesElements;

  public abstract void addElement(Element element) ;

  @Override
  public abstract double evaluer() ;

}
