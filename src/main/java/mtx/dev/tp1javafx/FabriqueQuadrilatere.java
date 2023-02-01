package mtx.dev.tp1javafx;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FabriqueQuadrilatere {
    public static Quadrilatere createQuadrilatere(InterPoint A, InterPoint B, InterPoint C, InterPoint D, String type) {
        try {
            Class<?> classe = Class.forName("mtx.dev.tp1javafx." + type);
            return (Quadrilatere) classe.getConstructor(InterPoint.class, InterPoint.class, InterPoint.class, InterPoint.class).newInstance(A, B, C, D);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Type de quadrilatère inconnu. Veuillez choisir entre Carré, Cerf-Volant, Losange, Parallèlogramme, Rectangle ou Trapèze.");
    }

    public static boolean isShapeValid(InterPoint A, InterPoint B, InterPoint C, InterPoint D, String type) {
        try {
            Class<?> classe = Class.forName("mtx.dev.tp1javafx." + type);
            Method isValidShape = classe.getMethod("isValidShape");
            return (boolean) isValidShape.invoke(createQuadrilatere(A, B, C, D, type));
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }
}