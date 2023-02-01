package mtx.dev.tp1javafx;

public class Carre extends Quadrilatere {
    private final InterPoint[] sommet;

    public Carre(InterPoint point1, InterPoint point2, InterPoint point3, InterPoint point4) {
        super(point1, point2, point3, point4);

        sommet = new InterPoint[4];
        sommet[0] = point1;
        sommet[1] = point2;
        sommet[2] = point3;
        sommet[3] = point4;
    }

    public boolean isValidShape(){
        double side1 = sommet[0].calculerDistance(sommet[1]);
        double side2 = sommet[1].calculerDistance(sommet[2]);
        double side3 = sommet[2].calculerDistance(sommet[3]);
        double side4 = sommet[3].calculerDistance(sommet[0]);

        double angleA = InterPoint.angle(sommet[3], sommet[0], sommet[1]);
        double angleB = InterPoint.angle(sommet[0], sommet[1], sommet[2]);
        double angleC = InterPoint.angle(sommet[1], sommet[2], sommet[3]);
        double angleD = InterPoint.angle(sommet[2], sommet[3], sommet[0]);

        System.out.println(angleA);
        System.out.println(angleB);
        System.out.println(angleC);
        System.out.println(angleD);

        return side1 == side2
                && side2 == side3
                && side3 == side4
                && angleA == 90
                && angleB == 90
                && angleC == 90
                && angleD == 90;
    }

    @Override
    public String type() {
        return "Carré";
    }

    @Override
    public String toString() {
        return "Je suis un " + type().toLowerCase() + ". Mes quatre côtés ont tous la même longueur de " + sommet[0].calculerDistance(sommet[1]) +
                ". Mes sommets ont pour coordonnées " + coordonnees();
    }
}