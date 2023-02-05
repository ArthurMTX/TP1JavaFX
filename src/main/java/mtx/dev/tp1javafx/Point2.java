package mtx.dev.tp1javafx;

/**
 * @author Maxime MONET
 * @author Arthur PALY
 */

/**
 * Classe point représentant un point polaire dans un espace à 2 dimensions
 * @see InterPoint
 */
public class Point2 implements InterPoint {
    /**
     * Coordonnées x du point
     */
    private double x;

    /**
     * Coordonnées y du point
     */
    private double y;

    /**
     * Nom du point
     */
    private String name;

    /**
     * Constructeur de la classe Point
     * @param radius Rayon du point par rapport à l'origine
     * @param angle Angle du point par rapport à l'origine en radians
     * @param name Nom du point
     */
    public Point2(double radius, double angle, String name) {
        this.x = radius * Math.cos(angle);
        this.y = radius * Math.sin(angle);
        this.name = name;

        System.out.println("Point2: " + name + " x: " + x + " y: " + y);
    }

    /**
     * Retourne la coordonnée x du point
     * @return La coordonnée x du point
     */
    public double getX() {
        return x;
    }

    /**
     * Retourne la coordonnée y du point
     * @return La coordonnée y du point
     */
    public double getY() {
        return y;
    }

    /**
     * Retourne le nom du point
     * @return Le nom du point
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie la coordonnée x du point
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Modifie la coordonnée y du point
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Modifie le nom du point
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Affiche les coordonnées x et y du point
     */
    public void afficher() {
        System.out.println("Coordonnées x: " + x + " Coordonnées y: " + y);
    }

    /**
     * Calcule la distance entre le point et un autre point
     * @param autrePoint Le point avec lequel on veut calculer la distance
     * @return La distance entre les deux points
     */
    public double calculerDistance(InterPoint autrePoint) {
        double distanceX = this.x - autrePoint.getX();
        double distanceY = this.y - autrePoint.getY();
        return Math.sqrt(distanceX*distanceX + distanceY*distanceY);
    }

    /**
     * Calcule l'angle entre le point et deux autres points
     * @param A Le premier point
     * @param B Le deuxième point
     * @param C Le troisième point
     * @return L'angle entre les trois points
     */
    public static double angle(Point2 A, Point2 B, Point2 C) {
        double BAx = B.x - A.x;
        double BAy = B.y - A.y;
        double BCx = B.x - C.x;
        double BCy = B.y - C.y;
        double dotProduct = BAx * BCx + BAy * BCy;
        double BA = Math.sqrt(BAx * BAx + BAy * BAy);
        double BC = Math.sqrt(BCx * BCx + BCy * BCy);
        return Math.acos(dotProduct / (BA * BC)) * 180 / Math.PI;
    }
}
