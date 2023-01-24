package mtx.dev.tp1javafx;

public class Quadrilatere {
    private final InterPoint[] sommet;
    public Quadrilatere(InterPoint point1, InterPoint point2, InterPoint point3, InterPoint point4) {

        sommet = new InterPoint[4];
        sommet[0] = point1;
        sommet[1] = point2;
        sommet[2] = point3;
        sommet[3] = point4;
    }

    public String type() {
        return "Quadrilatère quelconque";
    }

    public void affiche() {
        System.out.println(this);
    }

    public String coordonnees() {
        StringBuilder sb = new StringBuilder();
        for (InterPoint point : sommet) {
            sb.append("(").append(point.getX()).append(",").append(point.getY()).append(") ");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Je suis un " + type().toLowerCase() + ". Mes sommets ont pour coordonnées " + coordonnees();
    }

    public InterPoint[] getPoints() {
        return sommet;
    }
}
