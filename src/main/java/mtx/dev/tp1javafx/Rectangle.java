package mtx.dev.tp1javafx;

public class Rectangle extends Quadrilatere {
    private final double longueur;
    private final double longueur2;
    private final double largeur;
    private final double largeur2;
    private final InterPoint[] sommet;

    public Rectangle(InterPoint point1, InterPoint point2, InterPoint point3, InterPoint point4) {
        super(point1, point2, point3, point4);

        sommet = new InterPoint[4];
        sommet[0] = point1;
        sommet[1] = point2;
        sommet[2] = point3;
        sommet[3] = point4;

        // Calculer la longueur
        longueur = point1.calculerDistance(point2);
        longueur2 = point3.calculerDistance(point4);

        // Calculer la largeur
        largeur = point1.calculerDistance(point4);
        largeur2 = point2.calculerDistance(point3);

        if (!isValidShape()) {
            throw new IllegalArgumentException("Les points ne forment pas un rectangle.");
        }
    }

    public Double calculerAire() {
        return longueur * largeur;
    }

    public boolean isValidShape(){
        // Vérifier si les longueurs sont égales et si les largeurs sont égales
        return longueur == longueur2 && largeur == largeur2;
    }

    @Override
    public String type() {
        return "Rectangle";
    }

    @Override
    public String toString() {
        return "Je suis un " + type().toLowerCase() + ". Mes quatre angles sont droits. J'ai une longueur de " + longueur +
                " et une largeur de " + largeur + ". Mes sommets ont pour coordonnées " + coordonnees();
    }
}
