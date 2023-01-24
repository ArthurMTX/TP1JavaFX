package mtx.dev.tp1javafx;

public class Point implements InterPoint {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void afficher() {
        System.out.println("Coordonnées x: " + x + " Coordonnées y: " + y);
    }

    public double calculerDistance(InterPoint autrePoint) {
        double distanceX = this.x - autrePoint.getX();
        double distanceY = this.y - autrePoint.getY();
        return Math.sqrt(distanceX*distanceX + distanceY*distanceY);
    }

    public static double angle(Point A, Point B, Point C) {
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
