public class Main {

    public static void main(String[] args) {
        Data la_data = new Data();
        Producteur prod = new Producteur(la_data,0);
        Consommateur conso = new Consommateur(la_data,0);
        (new Thread(new Consommateur(la_data,1))).start();
	    (new Thread(new Consommateur(la_data,2))).start(); 
         (new Thread(new Producteur(la_data,1))).start();
	    (new Thread(new Producteur(la_data,2))).start();
        prod.start();
        conso.start();  

        try {
            Thread.sleep(30000);    // s'execute pendant 30 secondes
        } catch (InterruptedException e) {}

       

        
    } 

}
