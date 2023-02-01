package mtx.dev.tp1javafx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import java.text.Normalizer;

public class Main extends Application{
    double width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth(); // Récupération de la taille de l'écran
    double height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight(); // Récupération de la taille de l'écran
    static Pane root = new Pane(); // Création d'un Pane
    double squareSize = width/2; // Taille du carré
    double blockSize = squareSize/50; // Taille d'un bloc
    double unit = squareSize/blockSize; // Unité de la grille

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(root, width, height);

        stage.setTitle("TPJavaFX");
        stage.setScene(scene);
        stage.show();
        displayGrid();

        // Création du champ qui permet de choisir le type de quadrilatère
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Carré", "Cerf-Volant", "Losange", "Parallèlogramme", "Rectangle", "Trapèze");
        comboBox.setValue(comboBox.getItems().get(0));
        comboBox.setLayoutX(10);
        comboBox.setLayoutY(10);

        // Création des champs qui permettent de rentrer les coordonnées des points
        TextField[] textFields = new TextField[4];
        for (int i = 0; i < textFields.length; i++) {
            textFields[i] = new TextField();
            textFields[i].setLayoutX(10);
            textFields[i].setLayoutY(40 + 30 * i);
            textFields[i].setPromptText("Point " + (char)('A' + i));
        }

        // Création du bouton qui permet de choisir la couleur du quadrilatère
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        colorPicker.setLayoutX(10);
        colorPicker.setLayoutY(160);

        // Création du champ qui permet de choisir le type des points
        ComboBox<String> comboBoxPoint = new ComboBox<>();
        comboBoxPoint.getItems().addAll("Point", "Point2");
        comboBoxPoint.setValue(comboBoxPoint.getItems().get(0));
        comboBoxPoint.setLayoutX(10);
        comboBoxPoint.setLayoutY(190);

        // Création du bouton qui permet de valide la création du quadrilatère
        Button buttonCreateQuadri = new Button("Créer le quadrilatère");
        buttonCreateQuadri.setLayoutX(10);
        buttonCreateQuadri.setLayoutY(220);

        // Création du tableau qui contient les points du quadrilatère
        InterPoint[] points = new InterPoint[4];

        buttonCreateQuadri.setOnAction(e -> {

            String shape = comboBox.getValue();
            shape = Normalizer.normalize(shape, Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "")
                    .replaceAll("-", "")
                    .replaceAll("\\s", "");
            String typeShape = shape;

            String typePoint = comboBoxPoint.getValue();

            try {
                for (int i = 0; i < 4; i++) {
                    // detect if one of the textFields is empty
                    System.out.println(textFields[i].getText());
                    if (textFields[i].getText().isEmpty()) {
                        String errorMessage = "Une erreur s'est produite lors de la création du point " + (char) ('A' + i) + " : les coordonnées ne sont pas au format : x;y";
                        errorMessage(errorMessage);
                        return;
                    }
                    if (!textFields[i].getText().matches("(-?\\d+(\\.\\d+)?);(-?\\d+(\\.\\d+)?)")) {
                        String errorMessage = "Une erreur s'est produite lors de la création du point " + (char) ('A' + i) + " : les coordonnées ne sont pas au format : x;y";
                        errorMessage(errorMessage);
                        return;
                    }
                    String[] coord = textFields[i].getText().split(";");
                    double x = Double.parseDouble(coord[0]);
                    double y = -Double.parseDouble(coord[1]);
                    points[i] = FabriquePoint.createPoint(x, y, typePoint);
                }

                if (!FabriqueQuadrilatere.isShapeValid(points[0], points[1], points[2], points[3], typeShape)) {
                    String errorMessage = "Une erreur s'est produite lors de la création du quadrilatère : le quadrilatère n'est pas valide";
                    errorMessage(errorMessage);
                    return;
                }

                Quadrilatere quadrilatere = FabriqueQuadrilatere.createQuadrilatere(points[0], points[1], points[2], points[3], typeShape);
                displayShape(quadrilatere, colorPicker.getValue());

            } catch (NumberFormatException eNumber) {
                String variableName = switch (eNumber.getStackTrace()[0].getMethodName()) {
                    case "createPointA" -> "A";
                    case "createPointB" -> "B";
                    case "createPointC" -> "C";
                    case "createPointD" -> "D";
                    default -> "inconnu";
                };
                String errorException = eNumber.getMessage();
                String errorMessage;
                if (variableName.equals("inconnu")) {
                    errorMessage = "Une erreur s'est produite lors de la création du quadrilatère : " + errorException;
                } else {
                    errorMessage = "Une erreur s'est produite lors de la création du point " + variableName + " : " + errorException;
                }
                errorMessage(errorMessage);
            }
        });

        root.getChildren().addAll(textFields);
        root.getChildren().addAll(buttonCreateQuadri, comboBoxPoint, colorPicker, comboBox);
        //testShapes();
    }

    public static void main(String[] args) {
        launch();
    }

    // fonction pour afficher une grille orthonormée
    public void displayGrid() {
        // affichage du carré
        Line[] lines = new Line[] {
                new Line(0, 0, squareSize, 0),
                new Line(squareSize, 0, squareSize, squareSize),
                new Line(squareSize, squareSize, 0, squareSize),
                new Line(0, squareSize, 0, 0),
        };
        for (Line line : lines) {
            line.setStroke(Color.BLACK);
        }
        Group grid = new Group(lines);

        // lignes de la grille en abscisses
        for (int i = 1; i < squareSize/blockSize; i++) {
            Line line = new Line(0, blockSize*i, squareSize, blockSize*i);
            line.setStroke(Color.GRAY);
            line.opacityProperty().set(0.5);
            grid.getChildren().add(line);
        }

        // lignes de la grille en ordonnées
        for (int i = 1; i < squareSize/blockSize; i++) {
            Line line = new Line(blockSize*i, 0, blockSize*i, squareSize);
            line.setStroke(Color.GRAY);
            line.opacityProperty().set(0.5);
            grid.getChildren().add(line);
        }

        // affichage de la ligne des abscisses
        Line lineY = new Line(0, squareSize/2, squareSize, squareSize/2);
        lineY.setStroke(Color.RED);
        lineY.setStrokeWidth(2);
        grid.getChildren().add(lineY);

        // affichage de la ligne des ordonnées
        Line lineX = new Line(squareSize/2, 0, squareSize/2, squareSize);
        lineX.setStroke(Color.RED);
        lineX.setStrokeWidth(2);
        grid.getChildren().add(lineX);

        // centre le carré dans la fenêtre
        grid.setTranslateX(width/2 - squareSize/2);
        grid.setTranslateY(height/2 - squareSize/2);

        root.getChildren().add(grid);
    }

    private void errorMessage(String errorMessage) {
        System.out.println(errorMessage);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    private void displayShape(Quadrilatere shape, Color color) {
        InterPoint[] points = shape.getPoints();

        // place un cercle sur chaque point
        for (InterPoint point : points) {
            Circle circle = new Circle();
            circle.setCenterX(point.getX()*unit + width/2);
            circle.setCenterY(point.getY()*unit + height/2);
            circle.setRadius(2);
            circle.setFill(color);
            root.getChildren().add(circle);
        }

        // affiche les lignes entre les points
        for (int i = 0; i < points.length; i++) {
            Line line = new Line(points[i].getX()*unit + width/2,
                    points[i].getY()*unit + height/2,
                    points[(i+1)%points.length].getX()*unit + width/2,
                    points[(i+1)%points.length].getY()*unit + height/2);
            line.setStroke(color);
            root.getChildren().add(line);
        }

        System.out.println("Point 1 : " + points[0].getX() + " " + points[0].getY());
        System.out.println("Point 2 : " + points[1].getX() + " " + points[1].getY());
        System.out.println("Point 3 : " + points[2].getX() + " " + points[2].getY());
        System.out.println("Point 4 : " + points[3].getX() + " " + points[3].getY());
    }

    private void testShapes() {
        String separator = "##############################################################################################";
        System.out.println(separator);

        Quadrilatere carre = FabriqueQuadrilatere.createQuadrilatere(
                FabriquePoint.createPoint(0,0, "Point"),
                FabriquePoint.createPoint(4,0, "Point"),
                FabriquePoint.createPoint(4,4, "Point"),
                FabriquePoint.createPoint(0,4, "Point"), "Carre");
        carre.affiche();
        displayShape(carre, Color.BLUE);

        System.out.println(separator);

        Quadrilatere cerfVolant = FabriqueQuadrilatere.createQuadrilatere(
                FabriquePoint.createPoint(0,3, "Point"),
                FabriquePoint.createPoint(1,2, "Point"),
                FabriquePoint.createPoint(0,0, "Point"),
                FabriquePoint.createPoint(-1,2, "Point"), "CerfVolant");
        cerfVolant.affiche();
        displayShape(cerfVolant, Color.GREEN);

        System.out.println(separator);

        Quadrilatere losange = FabriqueQuadrilatere.createQuadrilatere(
                FabriquePoint.createPoint(0,0, "Point"),
                FabriquePoint.createPoint(0,2, "Point"),
                FabriquePoint.createPoint(4,2, "Point"),
                FabriquePoint.createPoint(2,4, "Point"), "Losange");
        losange.affiche();
        displayShape(losange, Color.YELLOW);

        System.out.println(separator);

        Quadrilatere parallelogramme = FabriqueQuadrilatere.createQuadrilatere(
                FabriquePoint.createPoint(0,0, "Point"),
                FabriquePoint.createPoint(4,0, "Point"),
                FabriquePoint.createPoint(6,4, "Point"),
                FabriquePoint.createPoint(2,4, "Point"), "Parallelogramme");
        parallelogramme.affiche();
        displayShape(parallelogramme, Color.ORANGE);

        System.out.println(separator);

        Quadrilatere rectangle = FabriqueQuadrilatere.createQuadrilatere(
                FabriquePoint.createPoint(2,2, "Point"),
                FabriquePoint.createPoint(6,2, "Point"),
                FabriquePoint.createPoint(6,4, "Point"),
                FabriquePoint.createPoint(2,4, "Point"), "Rectangle");
        rectangle.affiche();
        displayShape(rectangle, Color.RED);

        System.out.println(separator);

        Quadrilatere trapeze = FabriqueQuadrilatere.createQuadrilatere(
                FabriquePoint.createPoint(0,0, "Point"),
                FabriquePoint.createPoint(4,0, "Point"),
                FabriquePoint.createPoint(6,4, "Point"),
                FabriquePoint.createPoint(2,4, "Point"), "Trapeze");
        trapeze.affiche();
        displayShape(trapeze, Color.PURPLE);

        System.out.println(separator);
    }
}