package mtx.dev.tp1javafx;

public class CerfVolant extends Quadrilatere {
    private final InterPoint[] sommet;

    public CerfVolant(InterPoint point1, InterPoint point2, InterPoint point3, InterPoint point4) {
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

        InterPoint diagonal1Start = sommet[0];
        InterPoint diagonal1End = sommet[2];
        InterPoint diagonal2Start = sommet[1];
        InterPoint diagonal2End = sommet[3];

        System.out.println(diagonal1Start.getY() + " " + diagonal1Start.getX());
        System.out.println(diagonal1End.getY() + " " + diagonal1End.getX());
        System.out.println(diagonal2Start.getY() + " " + diagonal2Start.getX());
        System.out.println(diagonal2End.getY() + " " + diagonal2End.getX());

        double angle1 = Math.atan2(diagonal1End.getY() - diagonal1Start.getY(), diagonal1End.getX() - diagonal1Start.getX());
        double angle2 = Math.atan2(diagonal2End.getY() - diagonal2Start.getY(), diagonal2End.getX() - diagonal2Start.getX());
        double angle = Math.abs(angle1 - angle2);

        System.out.println(angle);

        return side1 == side2 && side3 == side4 && angle == Math.PI/2;
    }

    @Override
    public String type() {
        return "Cerf-Volant";
    }

    @Override
    public String toString() {
        return "Je suis un " + type().toLowerCase() + ". Une de mes une des diagonales est mon axe de symétrie. J'ai une hauteur de " + sommet[0].calculerDistance(sommet[2]) +
                " et une largeur de " + sommet[1].calculerDistance(sommet[3]) + ". Mes sommets ont pour coordonnées " + coordonnees();
    }
}

