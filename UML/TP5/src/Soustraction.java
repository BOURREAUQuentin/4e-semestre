
class Soustraction extends OperationBinaire {
    private Element lefElement;

    private Element rightElement;

    public Soustraction(){
        this.lefElement = null;
        this.rightElement = null;
    }

    public void addLeft(Element element){
        this.lefElement = element;
    }

    public void addRight(Element element){
        this.rightElement = element;
    }

    public double evaluer(){
        return this.lefElement.evaluer() - this.rightElement.evaluer();
    }
}
