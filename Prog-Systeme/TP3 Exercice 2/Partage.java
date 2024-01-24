public class Partage {
    
    public static void main(String args[]){
    
        Ressource r = new Ressource();
        Ecrivain e1 = new Ecrivain(r);
        Ecrivain e2 = new Ecrivain(r);
        Ecrivain e3 = new Ecrivain(r);
        Lecteur l1 = new Lecteur(r);
        Lecteur l2 = new Lecteur(r);
    
        e1.start();
        e2.start();
        e3.start();
        l1.start();
        l2.start();   
    }
}
