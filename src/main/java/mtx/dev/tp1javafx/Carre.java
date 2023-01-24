package mtx.dev.tp1javafx;

public class Carre extends Quadrilatere {
    private final double longueurCote;
    private final double longueurCote2;
    private final double longueurCote3;
    private final double longueurCote4;
    private final InterPoint[] sommet;

    public Carre(InterPoint point1, InterPoint point2, InterPoint point3, InterPoint point4) {
        super(point1, point2, point3, point4);

        sommet = new InterPoint[4];
        sommet[0] = point1;
        sommet[1] = point2;
        sommet[2] = point3;
        sommet[3] = point4;

        // Calculer la longueur du côté
        longueurCote = point1.calculerDistance(point2);
        longueurCote2 = point2.calculerDistance(point3);
        longueurCote3 = point3.calculerDistance(point4);
        longueurCote4 = point4.calculerDistance(point1);

        if (!isValidShape()) {
            //throw new IllegalArgumentException("Les points ne forment pas un carré.");
        }
    }

    public Double calculerAire() {
        return longueurCote * longueurCote;
    }

    private boolean isValidShape(){
        // Vérifier les tout les côtés sont de même longueur
        return longueurCote == longueurCote2 && longueurCote2 == longueurCote3 && longueurCote3 == longueurCote4 && longueurCote4 == longueurCote;
    }

    @Override
    public String type() {
        return "Carré";
    }

    @Override
    public String toString() {
        return "Je suis un " + type().toLowerCase() + ". Mes quatre côtés ont tous la même longueur de " + longueurCote +
                ". Mes sommets ont pour coordonnées " + coordonnees();
    }
}