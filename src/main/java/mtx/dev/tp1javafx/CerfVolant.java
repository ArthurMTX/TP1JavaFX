package mtx.dev.tp1javafx;

public class CerfVolant extends Quadrilatere {
    private final double largeur;
    private final double hauteur;
    private final double longueurCote;
    private final double longueurCote2;
    private final double longueurCote3;
    private final double longueurCote4;
    private final InterPoint pointMilieuLargeur;
    private final InterPoint[] sommet;

    public CerfVolant(InterPoint point1, InterPoint point2, InterPoint point3, InterPoint point4) {
        super(point1, point2, point3, point4);

        sommet = new InterPoint[4];
        sommet[0] = point1;
        sommet[1] = point2;
        sommet[2] = point3;
        sommet[3] = point4;

        // Calculer la larger
        largeur = point2.calculerDistance(point4);

        // Calculer la largeur
        hauteur = point1.calculerDistance(point3);

        // Calculer la longueur du côté
        longueurCote = point1.calculerDistance(point2);
        longueurCote2 = point2.calculerDistance(point3);
        longueurCote3 = point3.calculerDistance(point4);
        longueurCote4 = point4.calculerDistance(point1);

        // Crée un point au milieu de la largeur
        pointMilieuLargeur = new Point(point1.getX() + (largeur / 2), point1.getY());
        //pointMilieuLargeurX = ((point2.getX() - point4.getX()) / 2) + point2.getX();
        //pointMilieuLargeurY = point4.getY();
        System.out.println(pointMilieuLargeur.getX() + " " + pointMilieuLargeur.getY());

        if (!isValidShape()) {
           // throw new IllegalArgumentException("Les points ne forment pas un cerf-volant.");
        }
    }

    public InterPoint getPointMilieuLargeur(){
        return pointMilieuLargeur;
    }

    public Double calculerAire() {
        return (largeur * hauteur) / 2;
    }

    public boolean isValidShape(){
        // Vérifier les cotés adjacent sont de même longueur
        if (longueurCote != longueurCote3 || longueurCote2 != longueurCote4){
            //return false;
        }
        System.out.println(InterPoint.angle(sommet[0], pointMilieuLargeur, sommet[3]));
        System.out.println(InterPoint.angle(pointMilieuLargeur, sommet[0] , sommet[3]));
        System.out.println(InterPoint.angle(sommet[0] , sommet[3], pointMilieuLargeur));
        System.out.println(InterPoint.angle(sommet[3], sommet[0], pointMilieuLargeur));
        System.out.println(InterPoint.angle(sommet[3], pointMilieuLargeur, sommet[0]));
        System.out.println(InterPoint.angle(pointMilieuLargeur, sommet[3], sommet[0]));
        if (InterPoint.angle(sommet[0], pointMilieuLargeur, sommet[3]) != 90){
            return false;
        }
        // Vérifier la hauteur est n'est pas égale à la largeur
        return hauteur != largeur;
    }

    @Override
    public String type() {
        return "Cerf-Volant";
    }

    @Override
    public String toString() {
        return "Je suis un " + type().toLowerCase() + ". Une de mes une des diagonales est mon axe de symétrie. J'ai une hauteur de " + hauteur +
                " et une largeur de " + largeur + ". Mes sommets ont pour coordonnées " + coordonnees();
    }
}

