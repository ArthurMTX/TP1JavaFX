package mtx.dev.tp1javafx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.Normalizer;

public class Main extends Application{
    double width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth(); // Récupération de la taille de l'écran
    double height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight(); // Récupération de la taille de l'écran
    Pane root = new Pane(); // Création d'un Pane
    double squareSize = width/2; // Taille du carré
    double blockSize = squareSize/50; // Taille d'un bloc

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(root, width, height);

        stage.setTitle("TPJavaFX");
        stage.setScene(scene);
        stage.show();

        displayGrid();

        // Champ select qui permet de choisir le type de quadrilatère
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Carré", "Cerf-Volant", "Losange", "Parallèlogramme", "Rectangle", "Trapèze");
        comboBox.setLayoutX(10);
        comboBox.setLayoutY(10);
        comboBox.setOnAction(e -> {
            String shape = comboBox.getValue();
            shape = Normalizer.normalize(shape, Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "")
                    .replaceAll("-", "")
                    .replaceAll("\\s", "");

            TextField[] textFields = new TextField[4];

            for (int i = 0; i < textFields.length; i++) {
                textFields[i] = new TextField();
                textFields[i].setLayoutX(10);
                textFields[i].setLayoutY(40 + 30 * i);
                textFields[i].setPromptText("Point " + (char)('A' + i));
            }

            // Champ select qui permet de choisir le type de point
            ComboBox<String> comboBoxPoint = new ComboBox<>();
            comboBoxPoint.getItems().addAll("Point", "Point2");
            comboBoxPoint.setLayoutX(10);
            comboBoxPoint.setLayoutY(160);
            String finalShape = shape;
            comboBoxPoint.setOnAction(e2 -> {
                String point = comboBoxPoint.getValue();

                // Création du bouton qui permet de créer le quadrilatère
                Button buttonCreateQuadri = new Button("Créer le quadrilatère");
                buttonCreateQuadri.setLayoutX(10);
                buttonCreateQuadri.setLayoutY(190);
                buttonCreateQuadri.setOnAction(e3 -> {
                    try {
                        InterPoint A = FabriquePoint.createPoint(Double.parseDouble(textFields[0].getText().split(",")[0]),
                                Double.parseDouble(textFields[0].getText().split(",")[1]), point);
                        InterPoint B = FabriquePoint.createPoint(Double.parseDouble(textFields[1].getText().split(",")[0]),
                                Double.parseDouble(textFields[1].getText().split(",")[1]), point);
                        InterPoint C = FabriquePoint.createPoint(Double.parseDouble(textFields[2].getText().split(",")[0]),
                                Double.parseDouble(textFields[2].getText().split(",")[1]), point);
                        InterPoint D = FabriquePoint.createPoint(Double.parseDouble(textFields[3].getText().split(",")[0]),
                                Double.parseDouble(textFields[3].getText().split(",")[1]), point);
                        Quadrilatere quadrilatere = FabriqueQuadrilatere.createQuadrilatere(A, B, C, D, finalShape);
                        displayShape(quadrilatere, Color.RED);
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
                        Text errorTextField = new Text(errorMessage);
                        errorTextField.setText(errorMessage);
                        errorTextField.setStyle("-fx-text-fill: red;");
                        errorTextField.setLayoutX(10);
                        errorTextField.setLayoutY(250);
                        root.getChildren().add(errorTextField);
                        System.out.println(errorMessage);
                    }
                });
                root.getChildren().add(buttonCreateQuadri);
            });
            root.getChildren().addAll(textFields);
            root.getChildren().add(comboBoxPoint);
        });
        root.getChildren().add(comboBox);
    }

    public static void main(String[] args) {
        launch();
    }

    // fonction pour afficher une grille orthonormée
    public void displayGrid() {
        // affichage du carré
        Line line1 = new Line(0, 0, squareSize, 0);
        Line line2 = new Line(squareSize, 0, squareSize, squareSize);
        Line line3 = new Line(squareSize, squareSize, 0, squareSize);
        Line line4 = new Line(0, squareSize, 0, 0);
        line1.setStroke(Color.BLACK);
        line2.setStroke(Color.BLACK);
        line3.setStroke(Color.BLACK);
        line4.setStroke(Color.BLACK);
        Group grid = new Group(line1, line2, line3, line4);

        // affichage de la grille dans le carré
        for (int i = 1; i < 10; i++) {
            Line line = new Line(0, blockSize*5*i, squareSize, blockSize*5*i);
            line.setStroke(Color.GRAY);
            line.opacityProperty().set(0.5);
            grid.getChildren().add(line);
        }
        for (int i = 1; i < 10; i++) {
            Line line = new Line(blockSize*5*i, 0, blockSize*5*i, squareSize);
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

    private void displayShape(Quadrilatere shape, Color color) {
        InterPoint[] points = shape.getPoints();
        int shapeSize = 19;

        // place un cercle sur chaque point
        for (InterPoint point : points) {
            Circle circle = new Circle();
            circle.setCenterX(point.getX()*shapeSize + width/2);
            circle.setCenterY(point.getY()*shapeSize + height/2);
            circle.setRadius(2);
            circle.setFill(color);
            root.getChildren().add(circle);
        }

        // affiche les lignes entre les points
        for (int i = 0; i < points.length; i++) {
            Line line = new Line(points[i].getX()*shapeSize + width/2,
                    points[i].getY()*shapeSize + height/2,
                    points[(i+1)%points.length].getX()*shapeSize + width/2,
                    points[(i+1)%points.length].getY()*shapeSize + height/2);
            line.setStroke(color);
            root.getChildren().add(line);
        }

        System.out.println("Point 1 : " + points[0].getX() + " " + points[0].getY());
        System.out.println("Point 2 : " + points[1].getX() + " " + points[1].getY());
        System.out.println("Point 3 : " + points[2].getX() + " " + points[2].getY());
        System.out.println("Point 4 : " + points[3].getX() + " " + points[3].getY());
    }

    private void testShapes() {
        Quadrilatere carre = FabriqueQuadrilatere.createQuadrilatere(
                FabriquePoint.createPoint(0,0, "Point"),
                FabriquePoint.createPoint(4,0, "Point"),
                FabriquePoint.createPoint(4,4, "Point"),
                FabriquePoint.createPoint(0,4, "Point"), "Carre");
        carre.affiche();
        displayShape(carre, Color.BLUE);

        Quadrilatere cerfVolant = FabriqueQuadrilatere.createQuadrilatere(
                FabriquePoint.createPoint(0,3, "Point"),
                FabriquePoint.createPoint(1,2, "Point"),
                FabriquePoint.createPoint(0,0, "Point"),
                FabriquePoint.createPoint(-1,2, "Point"), "CerfVolant");
        cerfVolant.affiche();
        displayShape(cerfVolant, Color.GREEN);

        Quadrilatere losange = FabriqueQuadrilatere.createQuadrilatere(
                FabriquePoint.createPoint(0,0, "Point"),
                FabriquePoint.createPoint(0,2, "Point"),
                FabriquePoint.createPoint(4,2, "Point"),
                FabriquePoint.createPoint(2,4, "Point"), "Losange");
        losange.affiche();
        displayShape(losange, Color.YELLOW);

        Quadrilatere parallelogramme = FabriqueQuadrilatere.createQuadrilatere(
                FabriquePoint.createPoint(0,0, "Point"),
                FabriquePoint.createPoint(4,0, "Point"),
                FabriquePoint.createPoint(6,4, "Point"),
                FabriquePoint.createPoint(2,4, "Point"), "Parallelogramme");
        parallelogramme.affiche();
        displayShape(parallelogramme, Color.ORANGE);

        Quadrilatere rectangle = FabriqueQuadrilatere.createQuadrilatere(
                FabriquePoint.createPoint(2,2, "Point"),
                FabriquePoint.createPoint(6,2, "Point"),
                FabriquePoint.createPoint(6,4, "Point"),
                FabriquePoint.createPoint(2,4, "Point"), "Rectangle");
        rectangle.affiche();
        displayShape(rectangle, Color.RED);

        Quadrilatere trapeze = FabriqueQuadrilatere.createQuadrilatere(
                FabriquePoint.createPoint(0,0, "Point"),
                FabriquePoint.createPoint(4,0, "Point"),
                FabriquePoint.createPoint(6,4, "Point"),
                FabriquePoint.createPoint(2,4, "Point"), "Trapeze");
        trapeze.affiche();
        displayShape(trapeze, Color.PURPLE);
    }
}