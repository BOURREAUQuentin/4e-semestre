
class Main {
  public static void main(String[] args)
  {
        // Somme somme1 = new Somme();
        // Somme somme2 = new Somme();
        // Somme somme3 = new Somme();
        // Somme somme4 = new Somme();
        // Nombre nb1 = new Nombre(-2);
        // Nombre nb2 = new Nombre(4);
        // Nombre nb3 = new Nombre(8);
        // Nombre nb4 = new Nombre(1);
        // Nombre nb5 = new Nombre(7);
        // Nombre nb6 = new Nombre(6);
        // Nombre nb7 = new Nombre(2);
        // somme1.addElement(somme2);
        // somme1.addElement(nb5);
        // somme1.addElement(somme4);
        // somme2.addElement(nb1);
        // somme2.addElement(nb2);
        // somme2.addElement(somme3);
        // somme3.addElement(nb3);
        // somme3.addElement(nb4);
        // somme4.addElement(nb6);
        // somme4.addElement(nb7);
        // System.out.println(somme1.evaluer());

        Somme somme1 = new Somme();
        Multiplication mult1 = new Multiplication();
        Soustraction soustr1 = new Soustraction();
        Division div1 = new Division();
        Nombre nb1 = new Nombre(-2);
        Nombre nb2 = new Nombre(4);
        Nombre nb3 = new Nombre(8);
        Nombre nb4 = new Nombre(1);
        Nombre nb5 = new Nombre(7);
        Nombre nb6 = new Nombre(6);
        Nombre nb7 = new Nombre(2);
        somme1.addElement(mult1);
        somme1.addElement(nb5);
        somme1.addElement(div1);
        mult1.addElement(nb1);
        mult1.addElement(nb2);
        mult1.addElement(soustr1);
        soustr1.addLeft(nb3);
        soustr1.addRight(nb4);
        div1.addLeft(nb6);
        div1.addRight(nb7);
        System.out.println(somme1.evaluer());
  }
}
