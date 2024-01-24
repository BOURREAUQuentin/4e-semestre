public class Diner {
    public static void main(String[] args)
    {
        int dim = 5;
        Fourchettes fourchettes = new Fourchettes(dim);
        Philosophe[] mangeurs = new Philosophe[dim];

        for (int i =0; i<dim; i++)
            mangeurs[i] = new  Philosophe(i,fourchettes,4);

        for (int i =0; i<dim; i++)
            mangeurs[i].start();
    }
}