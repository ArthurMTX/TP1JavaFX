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
        int i = 1;
        for (InterPoint point : sommet) {
            sb.append((char) ('A' + i - 1)).append(" : ").append("(x : ").append(point.getX()).append(" ; ").append("y : ").append(point.getY()).append(") ");
            if (i != sommet.length) {
                sb.append("/ ");
            }
            i++;
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
