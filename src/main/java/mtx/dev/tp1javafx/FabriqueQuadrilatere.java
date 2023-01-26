package mtx.dev.tp1javafx;

import java.lang.reflect.InvocationTargetException;

public class FabriqueQuadrilatere {
    public static Quadrilatere createQuadrilatere(InterPoint A, InterPoint B, InterPoint C, InterPoint D, String type) {
        try {
            Class<?> classe = Class.forName("mtx.dev.tp1javafx." + type);
            return (Quadrilatere) classe.getConstructor(
                    InterPoint.class, InterPoint.class,
                    InterPoint.class, InterPoint.class)
                    .newInstance(A, B, C, D);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        throw new IllegalArgumentException("Type de quadrilatère inconnu. Veuillez choisir entre Carré, Cerf-Volant, Losange, Parallèlogramme, Rectangle ou Trapèze.");
    }
}
