package mtx.dev.tp1javafx;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Classe de fabrique de quadrilatères
 */
public class FabriqueQuadrilatere {
    /**
     * Crée un quadrilatère selon le type spécifié
     * @param A Le point A
     * @param B Le point B
     * @param C Le point C
     * @param D Le point D
     * @param type Le type de quadrilatère à créer (carré, cerf-volant, losange, quadrilatère quelconque, parallèlogramme, rectangle ou trapèze)
     * @return Le quadrilatère créé selon le type spécifié
     */
    public static Quadrilatere createQuadrilatere(InterPoint A, InterPoint B, InterPoint C, InterPoint D, String type) {
        try {
            // On utilise la réflexivité pour créer un quadrilatère selon le type spécifié
            String packageName = Quadrilatere.class.getPackage().getName();
            Class<?> classe = Class.forName(packageName + "." + type);
            return (Quadrilatere) classe.getConstructor(InterPoint.class, InterPoint.class, InterPoint.class, InterPoint.class).newInstance(A, B, C, D);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Type de quadrilatère inconnu. Veuillez choisir entre Carré, Cerf-Volant, Losange, Parallèlogramme, Rectangle ou Trapèze.");
    }

    /**
     * Vérifie si un quadrilatère est valide selon le type spécifié
     * @param A Le point A
     * @param B Le point B
     * @param C Le point C
     * @param D Le point D
     * @param type Le type de quadrilatère à vérifier (carré, cerf-volant, losange, quadrilatère quelconque, parallèlogramme, rectangle ou trapèze)
     * @return true si le quadrilatère est valide selon le type spécifié, false sinon
     */
    public static boolean isShapeValid(InterPoint A, InterPoint B, InterPoint C, InterPoint D, String type) {
        try {
            String packageName = Quadrilatere.class.getPackage().getName();
            Class<?> classe = Class.forName(packageName + "." + type);
            Method isValidShape = classe.getMethod("isValidShape");
            return (boolean) isValidShape.invoke(createQuadrilatere(A, B, C, D, type));
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }
}