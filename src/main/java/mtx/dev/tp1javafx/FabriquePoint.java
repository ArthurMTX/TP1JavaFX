package mtx.dev.tp1javafx;

import java.lang.reflect.InvocationTargetException;

public class FabriquePoint {
    public static InterPoint createPoint(double x, double y, String type) {
        try {
             Class<?> classe = Class.forName("mtx.dev.tp1javafx." + type);
             return (InterPoint) classe.getConstructor(double.class, double.class).newInstance(x, y);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
             e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        throw new IllegalArgumentException("Type de point inconnu. Veuillez choisir entre Point et Point2.");
    }
}
