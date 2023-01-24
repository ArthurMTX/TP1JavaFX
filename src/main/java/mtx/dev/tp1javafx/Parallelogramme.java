package mtx.dev.tp1javafx;

public class Parallelogramme extends Quadrilatere {
    private final double longueurBase;
    private final double hauteur;
    private final InterPoint[] sommet;

    public Parallelogramme(InterPoint point1, InterPoint point2, InterPoint point3, InterPoint point4) {
        super(point1, point2, point3, point4);

        sommet = new InterPoint[4];
        sommet[0] = point1;
        sommet[1] = point2;
        sommet[2] = point3;
        sommet[3] = point4;

        // Calculer la longueur de la base
        longueurBase = point1.calculerDistance(point2);

        // Calculer la hauteur
        hauteur = point1.calculerDistance(point4);

        /*Vector2D a = new Vector2D(p1,p2);
        Vector2D b = new Vector2D(p3,p4);
        double area = a.cross(b);
        double height = area/a.magnitude();*/

        if (!isValidShape()) {
            //throw new IllegalArgumentException("Les points ne forment pas un parallélogramme.");
        }
    }

    public Double calculerAire() {
        return Math.abs((sommet[0].getX() - sommet[1].getX()) * (sommet[1].getY() - sommet[2].getY()));
    }

    private boolean isValidShape() {
        // Vérifier que les côtés opposés sont parallèles
        if (sommet[0].calculerDistance(sommet[2]) != sommet[1].calculerDistance(sommet[3])) {
            return false;
        }

        // Vérifier que les côtés opposés sont égaux
        return sommet[0].calculerDistance(sommet[1]) == sommet[2].calculerDistance(sommet[3]);
    }

    @Override
    public String type() {
        return "Parallélogramme";
    }

    @Override
    public String toString() {
        return "Je suis un " + type().toLowerCase() + ". J'ai une hauteur de " + hauteur +
                " et une longueur de base de " + longueurBase + ". Mes segments diagonaux se coupent en leurs milieux." +
                "Mes sommets ont pour coordonnées " + coordonnees();
    }
}

