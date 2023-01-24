package mtx.dev.tp1javafx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Main extends Application{
    double width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth(); // Récupération de la taille de l'écran
    double height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight(); // Récupération de la taille de l'écran
    Pane root = new Pane(); // Création d'un Pane
    double squareSize = width/2; // Taille du carré
    double blockSize = squareSize/50; // Taille d'un bloc

    @Override
    public void start(Stage stage) throws Exception {
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
            shape = shape.replace("é", "e");
            shape = shape.replace("è", "e");
            shape = shape.replace("-", "");
            shape = shape.replace(" ", "");

            // Création de champs de saisie pour les coordonnées des points
            TextField textFieldA = new TextField();
            textFieldA.setLayoutX(10);
            textFieldA.setLayoutY(40);
            textFieldA.setPromptText("Point A");
            TextField textFieldB = new TextField();
            textFieldB.setLayoutX(10);
            textFieldB.setLayoutY(70);
            textFieldB.setPromptText("Point B");
            TextField textFieldC = new TextField();
            textFieldC.setLayoutX(10);
            textFieldC.setLayoutY(100);
            textFieldC.setPromptText("Point C");
            TextField textFieldD = new TextField();
            textFieldD.setLayoutX(10);
            textFieldD.setLayoutY(130);
            textFieldD.setPromptText("Point D");

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
                    String[] pointA = textFieldA.getText().split(",");
                    String[] pointB = textFieldB.getText().split(",");
                    String[] pointC = textFieldC.getText().split(",");
                    String[] pointD = textFieldD.getText().split(",");
                    InterPoint A = FabriquePoint.createPoint(Double.parseDouble(pointA[0]), Double.parseDouble(pointA[1]), point);
                    InterPoint B = FabriquePoint.createPoint(Double.parseDouble(pointB[0]), Double.parseDouble(pointB[1]), point);
                    InterPoint C = FabriquePoint.createPoint(Double.parseDouble(pointC[0]), Double.parseDouble(pointC[1]), point);
                    InterPoint D = FabriquePoint.createPoint(Double.parseDouble(pointD[0]), Double.parseDouble(pointD[1]), point);
                    Quadrilatere quadrilatere = FabriqueQuadrilatere.createQuadrilatere(A, B, C, D, finalShape);
                    displayShape(quadrilatere, Color.RED);
                });
                root.getChildren().add(buttonCreateQuadri);
            });
            root.getChildren().addAll(textFieldA, textFieldB, textFieldC, textFieldD, comboBoxPoint);
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