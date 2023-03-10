package mtx.dev.tp1javafx;

/**
 * @author Maxime MONET
 * @author Arthur PALY
 */

/**
 * Interface de point
 */
public interface InterPoint {
    double getX();
    double getY();
    String getName();
    void setName(String name);
    void setY(double y);
    void setX(double x);
    void afficher();
    double calculerDistance(InterPoint autrePoint);
    static double angle(InterPoint A, InterPoint B, InterPoint C) {
        double BAx = B.getX() - A.getX();
        double BAy = B.getY() - A.getY();
        double BCx = B.getX() - C.getX();
        double BCy = B.getY() - C.getY();
        double dotProduct = BAx * BCx + BAy * BCy;
        double BA = Math.sqrt(BAx * BAx + BAy * BAy);
        double BC = Math.sqrt(BCx * BCx + BCy * BCy);
        return Math.acos(dotProduct / (BA * BC)) * 180 / Math.PI;
    }
}
