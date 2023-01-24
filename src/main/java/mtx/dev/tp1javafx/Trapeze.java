package mtx.dev.tp1javafx;

public class Trapeze extends Quadrilatere {
    private final double longueurBase1;
    private final double longueurBase2;
    private final double hauteur;
    private final InterPoint[] sommet;

    public Trapeze(InterPoint point1, InterPoint point2, InterPoint point3, InterPoint point4) {
        super(point1, point2, point3, point4);

        sommet = new InterPoint[4];
        sommet[0] = point1;
        sommet[1] = point2;
        sommet[2] = point3;
        sommet[3] = point4;

        // Calculer la longueur des bases
        longueurBase1 = point1.calculerDistance(point2);
        longueurBase2 = point3.calculerDistance(point4);

        // Calculer la hauteur
        hauteur = point1.calculerDistance(point4);

        if (!isValidShape()) {
            //throw new IllegalArgumentException("Les points ne forment pas un trapèze.");
        }
    }

    private boolean isValidShape(){
        // Vérifier les côtés sont de même longueur
        if (sommet[0].calculerDistance(sommet[1]) != sommet[1].calculerDistance(sommet[2]) ||
                sommet[1].calculerDistance(sommet[2]) != sommet[2].calculerDistance(sommet[3]) ||
                sommet[2].calculerDistance(sommet[3]) != sommet[3].calculerDistance(sommet[0])) {
            return false;
        }
        // Vérifier les angles sont droits
        return InterPoint.angle(sommet[0], sommet[1], sommet[2]) == 90 &&
                InterPoint.angle(sommet[1], sommet[2], sommet[3]) == 90 &&
                InterPoint.angle(sommet[2], sommet[3], sommet[0]) == 90 &&
                InterPoint.angle(sommet[3], sommet[0], sommet[1]) == 90;
    }

    @Override
    public String type() {
        return "Trapèze";
    }

    @Override
    public String toString() {
        return "Je suis un " + type().toLowerCase() + ". J'ai une hauteur de " + hauteur +
                " et une longueur de base de " + longueurBase1 + " et " + longueurBase2 + ". Ces bases sont deux côtés opposés parallèles." +
                "Mes sommets ont pour coordonnées " + coordonnees();
    }
}

