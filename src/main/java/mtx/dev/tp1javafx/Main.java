package mtx.dev.tp1javafx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale de l'application
 * @see Application
 */
public class Main extends Application{
    /**
     * Récupération de la largeur et de la hauteur de l'écran
     */
    private final double width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private final double height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    /**
     * Création du Pane principal, qui contiendra tous les éléments de l'application
     */
    private final static Pane root = new Pane();

    /**
     * Définition des constantes de l'application, en fonction de la taille de l'écran
     */
    private final double squareSize = width/2; // Taille du carré
    private final double blockSize = squareSize/50; // Taille d'un bloc
    private final double unit = squareSize/blockSize; // Unité de la grille

    /**
     * Initialisation de la liste des quadrilatères
     */
    private final ObservableList<Quadrilatere> shapeList = FXCollections.observableArrayList();
    private final ListView<String> shapeListView = new ListView<>();

    /**
     * Création des constantes des messages d'erreur
     */
    private final static String UNKNOWN_ERROR_MESSAGE = "Une erreur s'est produite lors de la création du quadrilatère";
    private final static String ERROR_MESSAGE_TEMPLATE = "Une erreur s'est produite lors de la création du point %s : %s";

    /**
     * Méthode principale de l'application
     * @param stage Le stage principal de l'application
     */
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(root, width, height);

        stage.setTitle("TPJavaFX");
        stage.setScene(scene);
        stage.show();
        displayGrid();

        // Création du champ qui permet de choisir le type de quadrilatère
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Carré", "Cerf-Volant", "Losange", "Parallèlogramme", "Quadrilatère", "Rectangle", "Trapèze");
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
        comboBoxPoint.getItems().addAll("Points cardinaux", "Points polaires");
        comboBoxPoint.setValue(comboBoxPoint.getItems().get(0));
        comboBoxPoint.setLayoutX(10);
        comboBoxPoint.setLayoutY(190);

        // Création du bouton qui permet de valide la création du quadrilatère
        Button buttonCreateQuadri = new Button("Créer le quadrilatère");
        buttonCreateQuadri.setLayoutX(10);
        buttonCreateQuadri.setLayoutY(220);

        // Création de la liste qui affiche les quadrilatères
        shapeListView.setPrefSize(200, 600);
        shapeListView.setTranslateX(width - 300);
        shapeListView.setTranslateY(75);

        /* /!\ Ne fonctionne pas /!\ Méthode qui permet de supprimer un quadrilatère de la liste /!\ Ne fonctionne pas /!\
        shapeListView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (shapeListView.getSelectionModel().getSelectedItem() != null) {
                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem delete = new MenuItem("Supprimer");
                    delete.setOnAction(event1 -> {
                        int index = shapeListView.getSelectionModel().getSelectedIndex();
                        Quadrilatere quadrilatereToRemove = shapeList.get(index);
                        shapeList.remove(quadrilatereToRemove);

                        System.out.println("Suppression du quadrilatère : " + quadrilatereToRemove.type() + " / " + quadrilatereToRemove.getColor());

                        // print shapeList
                        for (Quadrilatere quadrilatere : shapeList) {
                            System.out.println("ListeItem : " + quadrilatere.type() + " / " + quadrilatere.getColor());
                        }

                        gridRefresh();
                        shapeListRefresh();
                    });

                    contextMenu.getItems().add(delete);
                    contextMenu.show(shapeListView, event.getScreenX(), event.getScreenY());
                }
            }
        });
         */

        // Création du tableau qui contient les points du quadrilatère
        InterPoint[] points = new InterPoint[4];

        // Si le bouton de création du quadrilatère est cliqué
        buttonCreateQuadri.setOnAction(e -> {

            // Récupération du type de quadrilatère, enlève les accents et les espaces pour éviter les erreurs
            String shape = comboBox.getValue();
            shape = Normalizer.normalize(shape, Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "")
                    .replaceAll("-", "")
                    .replaceAll("\\s", "");
            String typeShape = shape;

            // Récupération du type de point (cardinal ou polaire)
            String typePoint = comboBoxPoint.getValue();
            if (typePoint.equals("Points cardinaux")) {
                typePoint = "Point";
            } else {
                typePoint = "Point2";
            }

            // En fonction du type de quadrilatère, on crée un quadrilatère différent
            try {
                // Création des points
                for (int i = 0; i < 4; i++) {
                    // Si le champ est vide, on affiche une erreur
                    if (textFields[i].getText().isEmpty()) {
                        String errorMessage = "Une erreur s'est produite lors de la création du point " + (char) ('A' + i) + " : les coordonnées ne sont pas au format : x;y";
                        printErrorMessage(errorMessage);
                        return;
                    }
                    // Si le champ n'est pas au bon format (x;y), on affiche une erreur
                    if (!textFields[i].getText().matches("(-?\\d+(\\.\\d+)?);(-?\\d+(\\.\\d+)?)")) {
                        String errorMessage = "Une erreur s'est produite lors de la création du point " + (char) ('A' + i) + " : les coordonnées ne sont pas au format : x;y";
                        printErrorMessage(errorMessage);
                        return;
                    }
                    // Création du point, on inverse y pour que le point soit bien affiché
                    String[] coord = textFields[i].getText().split(";");
                    double x = Double.parseDouble(coord[0]);
                    double y = -Double.parseDouble(coord[1]);
                    String name = String.valueOf((char) ('A' + i));
                    points[i] = FabriquePoint.createPoint(x, y, typePoint, name);
                }

                // Si le quadrilatère n'est pas valide, on affiche une erreur
                if (!FabriqueQuadrilatere.isShapeValid(points[0], points[1], points[2], points[3], typeShape)) {
                    String errorMessage = "Une erreur s'est produite lors de la création du quadrilatère : le quadrilatère n'est pas valide";
                    printErrorMessage(errorMessage);
                } else {
                    Quadrilatere quadrilatere = FabriqueQuadrilatere.createQuadrilatere(points[0], points[1], points[2], points[3], typeShape);
                    displayShape(quadrilatere, colorPicker.getValue());
                }

            } catch (NumberFormatException eNumber) {
                // Récupération du nom de la variable qui a causé l'erreur
                String methodName = eNumber.getStackTrace()[0].getMethodName();
                String variableName = switch (methodName) {
                    case "createPointA" -> "A";
                    case "createPointB" -> "B";
                    case "createPointC" -> "C";
                    case "createPointD" -> "D";
                    default -> "inconnu";
                };
                String errorMessage = generateErrorMessage(variableName, eNumber.getMessage());
                printErrorMessage(errorMessage);
            }
        });

        // Affichage des éléments
        root.getChildren().addAll(textFields);
        root.getChildren().addAll(buttonCreateQuadri, comboBoxPoint, colorPicker, comboBox, shapeListView);
    }

    /**
     * Méthode principale de l'application
     * @param args arguments généraux
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Méthode qui affiche une grille orthonormée en deux dimensions
     */
    public void displayGrid() {
        // Affichage du container de la grille
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

        // Affichage des lignes de la grille en abscisses
        for (int i = 1; i < squareSize/blockSize; i++) {
            Line line = new Line(0, blockSize*i, squareSize, blockSize*i);
            line.setStroke(Color.GRAY);
            line.opacityProperty().set(0.5);
            grid.getChildren().add(line);
        }

        // Affichage des lignes de la grille en ordonnées
        for (int i = 1; i < squareSize/blockSize; i++) {
            Line line = new Line(blockSize*i, 0, blockSize*i, squareSize);
            line.setStroke(Color.GRAY);
            line.opacityProperty().set(0.5);
            grid.getChildren().add(line);
        }

        // Affichage de la ligne des abscisses
        Line lineY = new Line(0, squareSize/2, squareSize, squareSize/2);
        lineY.setStroke(Color.RED);
        lineY.setStrokeWidth(2);
        grid.getChildren().add(lineY);

        // Affichage de la ligne des ordonnées
        Line lineX = new Line(squareSize/2, 0, squareSize/2, squareSize);
        lineX.setStroke(Color.RED);
        lineX.setStrokeWidth(2);
        grid.getChildren().add(lineX);

        // Centrage de la grille
        grid.setTranslateX(width/2 - squareSize/2);
        grid.setTranslateY(height/2 - squareSize/2);

        // Ajout de la grille a la fenêtre
        root.getChildren().add(grid);
    }

    /**
     * Méthode qui affiche une popup d'erreur
     * @param errorMessage Le message d'erreur
     */
    private void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur s'est produite");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    /**
     * Méthode qui génère un message d'erreur à partir du nom de la variable et du message d'erreur
     * @param variableName Le nom de la variable qui a causé l'erreur
     * @param errorMessage Le message d'erreur
     */
    private static String generateErrorMessage(String variableName, String errorMessage) {
        if ("inconnu".equals(variableName)) {
            return UNKNOWN_ERROR_MESSAGE + " : " + errorMessage;
        } else {
            return String.format(ERROR_MESSAGE_TEMPLATE, variableName, errorMessage);
        }
    }

    /**
     * Méthode qui affiche un quadrilatère sur la grille
     * @param shape Le quadrilatère à afficher
     * @param color La couleur du quadrilatère
     */
    private void displayShape(Quadrilatere shape, Color color) {
        // Récupère les points du quadrilatère
        InterPoint[] points = shape.getPoints();

        // Place un cercle sur chaque point
        for (InterPoint point : points) {
            Circle circle = new Circle();
            circle.setCenterX(point.getX()*unit + width/2);
            circle.setCenterY(point.getY()*unit + height/2);
            circle.setRadius(2);
            circle.setFill(color.darker());
            root.getChildren().add(circle);

            // Affiche le nom du point et ses coordonnées
            Text text = new Text(point.getX()*unit + width/2, point.getY()*unit + height/2, point.getName( )+ " (" + point.getX() + ", " + (-point.getY()) + ")");
            text.fontProperty().set(Font.font("Arial", FontWeight.BOLD, 16));
            text.setFill(color);
            root.getChildren().add(text);
        }

        // Affiche les lignes entre les points
        for (int i = 0; i < points.length; i++) {
            Line line = new Line(points[i].getX()*unit + width/2,
                    points[i].getY()*unit + height/2,
                    points[(i+1)%points.length].getX()*unit + width/2,
                    points[(i+1)%points.length].getY()*unit + height/2);
            line.setStroke(color);
            line.setStrokeWidth(2);
            root.getChildren().add(line);
        }

        // Applique la couleur au quadrilatère
        shape.setColor(color);

        shapeList.add(shape);
        shapeListRefresh();
    }

    /**
     * Méthode qui rafraichit la liste des quadrilatères
     */
    private void shapeListRefresh(){
        // On vide la ListView
        shapeListView.getItems().clear();

        // Création d'une nouvelle liste d'éléments pour la ListView
        ObservableList<String> items = FXCollections.observableArrayList();

        List<Quadrilatere> shapeListCopy = new ArrayList<>(shapeList);

        // Pour chaque quadrilatère, l'ajouter à liste d'éléments
        for (Quadrilatere shape : shapeListCopy) {
            String shapeColor = shape.getColor().toString().substring(2, shape.getColor().toString().length() - 2);
            String shapeType = shape.type();
            items.add(shapeType + " / " + shapeColor);
        }

        // Affecter la nouvelle liste d'éléments à la ListView
        shapeListView.getItems().setAll(items);
    }

    /**
     * Méthode qui rafraichit la grille
     */
    private void gridRefresh() {
        // On supprime tous les éléments de la grille (sauf la grille elle-même)
        List<Node> nodesToRemove = new ArrayList<>();
        for (Node node : root.getChildren()) {
            if (node instanceof Line || node instanceof Circle || node instanceof Text) {
                nodesToRemove.add(node);
            }
        }

        root.getChildren().removeAll(nodesToRemove);

        // On affiche tous les quadrilatères de la liste
        List<Quadrilatere> shapeListCopy = new ArrayList<>(shapeList);
        for (Quadrilatere shape : shapeListCopy) {
            displayShape(shape, shape.getColor());
        }
    }
}