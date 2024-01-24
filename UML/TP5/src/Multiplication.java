import java.util.ArrayList;
import java.util.List;

class Multiplication extends Operation {
    private List<Element> lesElements;

    public Multiplication(){
        this.lesElements = new ArrayList<Element>();
    }

    public void addElement(Element element){
        this.lesElements.add(element);
    }

    public double evaluer(){
        double valeurSomme = 0;
        for (Element element : this.lesElements){
            valeurSomme = valeurSomme * element.evaluer();
        }
        return valeurSomme;
    }
}
