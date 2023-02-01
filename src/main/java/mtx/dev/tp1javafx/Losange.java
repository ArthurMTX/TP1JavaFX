package mtx.dev.tp1javafx;

public class Losange extends Quadrilatere {
    private final double longueurCote;
    private final InterPoint[] sommet;
    private final double largeur;
    private final double hauteur;

    public Losange(InterPoint point1, InterPoint point2, InterPoint point3, InterPoint point4) {
        super(point1, point2, point3, point4);

        sommet = new InterPoint[4];
        sommet[0] = point1;
        sommet[1] = point2;
        sommet[2] = point3;
        sommet[3] = point4;

        // Calculer la longueur du côté
        longueurCote = point1.calculerDistance(point2);

        hauteur = point1.calculerDistance(point3);

        largeur = point2.calculerDistance(point4);

        if (!isValidShape()) {
           throw new IllegalArgumentException("Les points ne forment pas un losange.");
        }
    }

    public Double calculerAire() {
        return (largeur * hauteur) / 2;
    }

    private boolean isValidShape(){
        double side1 = sommet[0].calculerDistance(sommet[1]);
        double side2 = sommet[1].calculerDistance(sommet[2]);
        double side3 = sommet[2].calculerDistance(sommet[3]);
        double side4 = sommet[3].calculerDistance(sommet[0]);

        double angleA = InterPoint.angle(sommet[3], sommet[0], sommet[1]);
        double angleB = InterPoint.angle(sommet[0], sommet[1], sommet[2]);
        double angleC = InterPoint.angle(sommet[1], sommet[2], sommet[3]);
        double angleD = InterPoint.angle(sommet[2], sommet[3], sommet[0]);

        System.out.println("angleA: " + angleA);
        System.out.println("angleB: " + angleB);
        System.out.println("angleC: " + angleC);
        System.out.println("angleD: " + angleD);

        return side1 == side2
                && side2 == side3
                && side3 == side4
                && angleA == angleC
                && angleB == angleD;
    }

    @Override
    public String type() {
        return "Losange";
    }

    @Override
    public String toString() {
        return "Je suis un " + type().toLowerCase() + ". Mes les côtés ont tous la même longueur de " + longueurCote +
                ". Mes sommets ont pour coordonnées " + coordonnees();
    }
}
