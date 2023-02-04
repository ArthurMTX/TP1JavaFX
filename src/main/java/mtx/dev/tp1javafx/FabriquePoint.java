package mtx.dev.tp1javafx;

import java.lang.reflect.InvocationTargetException;

/**
 * Classe de fabrique de points
 */
public class FabriquePoint {
/**
     * Crée un point selon le type spécifié
     * @param x La coordonnée x du point
     * @param y La coordonnée y du point
     * @param type Le type de point à créer (cardinal ou polaire)
     * @param nom Le nom du point
     * @return Le point créé selon le type spécifié
     */
    public static InterPoint createPoint(double x, double y, String type, String nom) {
        try {
            // On utilise la réflexivité pour créer un point selon le type spécifié
             Class<?> classe = Class.forName("mtx.dev.tp1javafx." + type);
             return (InterPoint) classe.getConstructor(double.class, double.class, String.class).newInstance(x, y, nom);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
             e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        throw new IllegalArgumentException("Type de point inconnu. Veuillez choisir entre Point et Point2.");
    }
}
