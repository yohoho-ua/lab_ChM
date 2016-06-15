package lab1.graphic;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import lab1.math.Polinom;

/**
 * Created by zim on 23.04.2016.
 */
public class ViewForm extends Application {
    private TextField tfCoefs;
    private TextField tfParamA;
    private TextField tfParamK;
    private TextField tfPrecision;
    private Button btGo;
    private Label lblPolinom = new Label("Поліноміальне рівняння: a5(1+A) x5 + a4x4 + a3x3 + a2x2 + a1x1 + k a0 = 0");
    private TextArea taLog = new TextArea();
    private ComboBox<String> cbo;
    private GridPane vbIntervals = new GridPane();
    private VBox vbLeft = new VBox(5);


    private TextField[] intervalFields;
    private ResultPane bisectRP;
    private ResultPane hordRP;
    private ResultPane newtonRP;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Лаборотаорна №1");
        BorderPane pane = new BorderPane();
        Pane topPane = new Pane();

        tfCoefs = new TextField("1, 0, 0, 3, -2, -1");
        tfCoefs.setPrefColumnCount(10);
        tfParamA = new TextField("8");
        tfParamK = new TextField("1");
        tfPrecision = new TextField("0.00001");

        btGo = new Button("Ыч");
        btGo.setMaxWidth(Double.MAX_VALUE);

        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.setTop(lblPolinom);
        lblPolinom.setAlignment(Pos.CENTER);
        lblPolinom.setPadding(new Insets(10, 5, 10, 5));
        lblPolinom.setFont(new Font("Verdana", 18));

        //combo box for intervals
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "1",
                        "2",
                        "3",
                        "4",
                        "5",
                        "6"
                );
        cbo = new ComboBox(options);
        cbo.setValue("3");


        //grid pane for controls
        GridPane gpControls = new GridPane();
        gpControls.setVgap(5);
        gpControls.setHgap(5);
        gpControls.setPadding(new Insets(10, 10, 10, 10));
        gpControls.add(new Label("коефіцієнти: "), 0, 0);
        gpControls.add(new Label("параметр а: "), 0, 1);
        gpControls.add(new Label("параметр k: "), 0, 2);
        gpControls.add(new Label("точність: "), 0, 3);
        gpControls.add(new Label("к-сть коренів: "), 0, 4);

        gpControls.add(tfCoefs, 1, 0);
        gpControls.add(tfParamA, 1, 1);
        gpControls.add(tfParamK, 1, 2);
        gpControls.add(tfPrecision, 1, 3);
        gpControls.add(cbo, 1, 4);


        intervalFields = new TextField[Integer.parseInt(cbo.getValue())];

        //Intervals pane
        vbIntervals.setPadding(new Insets(10, 10, 10, 10));
        vbIntervals.setVgap(5);
        vbIntervals.setHgap(5);
        vbIntervals.add(new Label("інтервал №1"), 0, 0);
        vbIntervals.add(new Label("інтервал №2"), 0, 1);
        vbIntervals.add(new Label("інтервал №3"), 0, 2);
        vbIntervals.add(intervalFields[0] = new TextField("-1,-0.5"), 1, 0);
        vbIntervals.add(intervalFields[1] = new TextField("-0.5,0"), 1, 1);
        vbIntervals.add(intervalFields[2] = new TextField("0.5,1"), 1, 2);

        //Results panel
        VBox vbResuls = new VBox(10);
        bisectRP = new ResultPane("Метод бісекції");
        hordRP = new ResultPane("Метод хорд");
        newtonRP = new ResultPane("Метод дотичних");
        vbResuls.getChildren().addAll(bisectRP, hordRP, newtonRP);


        BorderPane.setAlignment(topPane, Pos.CENTER);

        tfPrecision.setPrefColumnCount(3);

        vbLeft.getChildren().addAll(gpControls, vbIntervals, btGo, vbResuls);
        pane.setLeft(vbLeft);
        pane.setCenter(taLog);


        Scene scene = new Scene(pane, 1024, 900);
        scene.getStylesheets().add("css/stylesheet.css");
        stage.setScene(scene);
        stage.show();


        btGo.setOnAction(e -> {
            proceed();
        });

        cbo.setOnAction(e -> {
            vbIntervals.getChildren().clear();
            int value = Integer.parseInt(cbo.getValue());
            intervalFields = new TextField[value];
            for (int i = 0; i < value; i++) {
                vbIntervals.add(new Label("інтервал №" + (i + 1)), 0, i);
                vbIntervals.add(intervalFields[i] = new TextField(), 1, i);
            }
        });


    }

    private void proceed() {
        taLog.clear();
        String s_coefs[] = tfCoefs.getText().split(",");
        int coefs[] = new int[s_coefs.length];
        for (int i = 0; i < s_coefs.length; i++) {
            coefs[i] = Integer.parseInt(s_coefs[i].trim());
        }
        double precision = Double.parseDouble(tfPrecision.getText());
        int paramA = Integer.parseInt(tfParamA.getText());
        int paramK = Integer.parseInt(tfParamK.getText());

        Polinom polinom = new Polinom(precision, coefs, paramA, paramK);

        double results1[][] = new double[intervalFields.length][];
        double results2[][] = new double[intervalFields.length][];
        double results3[][] = new double[intervalFields.length][];
        for (int i = 0; i < intervalFields.length; i++) {
            String[] temp = intervalFields[i].getText().split(",");
            double a = Double.parseDouble(temp[0]);
            double b = Double.parseDouble(temp[1]);
            results1[i] = polinom.getSolutionBy(1, a, b);
            results2[i] = polinom.getSolutionBy(2, a, b);
            results3[i] = polinom.getSolutionBy(3, a, b);
        }

        //status
        lblPolinom.setText(String.format("Поліноміальне рівняння:" +
                        " %d(1+%d) x^5 + %dx^4 + %dx^3 + %dx^2 + %dx + %d * %d = 0",
                coefs[0], paramA, coefs[1], coefs[2], coefs[3], coefs[4], paramK, coefs[5]));

        //set Results
        StringBuilder tempRoots = new StringBuilder();
        StringBuilder tempIters = new StringBuilder();
        for (double[] doubles : results1) {
            String temp = String.format("%.5f", doubles[doubles.length - 4]);
            tempRoots.append(temp + ",  ");
            tempIters.append(doubles.length / 6 + ",  ");
        }

        bisectRP.initPane(tempRoots.substring(0, tempRoots.length() - 3), tempIters.substring(0, tempIters.length() - 3));

        tempRoots = new StringBuilder();
        tempIters = new StringBuilder();
        for (double[] doubles : results2) {
            String temp = String.format("%.5f", doubles[doubles.length - 3]);
            tempRoots.append(temp + ",  ");
            tempIters.append(doubles.length / 3 + ",  ");
        }

        hordRP.initPane(tempRoots.substring(0, tempRoots.length() - 3), tempIters.substring(0, tempIters.length() - 3));

        tempRoots = new StringBuilder();
        tempIters = new StringBuilder();
        for (double[] doubles : results3) {
            String temp = String.format("%.5f", doubles[doubles.length - 3]);
            tempRoots.append(temp + ",  ");
            tempIters.append(doubles.length / 3 + ",  ");
        }
        newtonRP.initPane(tempRoots.substring(0, tempRoots.length() - 3), tempIters.substring(0, tempIters.length() - 3));

        //Log bisect
        taLog.appendText("************************************************************************************************************\n");
        taLog.appendText("************************************************************************************************************");
        for (int i = 0; i < intervalFields.length; i++) {
            String header = String.format("\nМетод Бісекції для інтервалу [%s]:" +
                            "\n_______________________________________________________________________________________________________________" +
                            "\n%10s %8s %18s %20s %24s %25s %26s" +
                            "\n_______________________________________________________________________________________________________________",
                    intervalFields[i].getText(), "iteration", "a", "b", "c", "f(a)", "f(c)", "precision");
            taLog.appendText(header);
            for (int j = 0, count = 1; j < results1[i].length; j++, count++) {
                String body = String.format("\n%5d %15.5f %15.5f %15.5f %20.5f %20.5f %20.8f", count,
                        results1[i][j++], results1[i][j++], results1[i][j++],
                        results1[i][j++], results1[i][j++], results1[i][j]);
                taLog.appendText(body);
            }
            taLog.appendText("\n\n");
        }

        //Log hord
        taLog.appendText("************************************************************************************************************\n");
        taLog.appendText("************************************************************************************************************");
        for (int i = 0; i < intervalFields.length; i++) {
            String header = String.format("\nМетод хорд для інтервалу [%s]:" +
                            "\n_______________________________________________________________________________________________________________" +
                            "\n%10s %8s  %25s %26s" +
                            "\n_______________________________________________________________________________________________________________",
                    intervalFields[i].getText(), "iteration", "x", "F(x)", "precision");
            taLog.appendText(header);
            for (int j = 0, count = 1; j < results2[i].length; j++, count++) {
                String body = String.format("\n%5d  %20.5f  %20.5f %20.8f", count,
                        results2[i][j++], results2[i][j++], results2[i][j]);
                taLog.appendText(body);
            }
            taLog.appendText("\n\n");
        }

        //Log newton
        taLog.appendText("************************************************************************************************************\n");
        taLog.appendText("************************************************************************************************************");
        for (int i = 0; i < intervalFields.length; i++) {
            String header = String.format("\nМетод дотичних (Н'ютона) для інтервалу [%s]:" +
                            "\n_______________________________________________________________________________________________________________" +
                            "\n%10s %8s  %25s %26s" +
                            "\n_______________________________________________________________________________________________________________",
                    intervalFields[i].getText(), "iteration", "x", "F(x)", "precision");
            taLog.appendText(header);
            for (int j = 0, count = 1; j < results3[i].length; j++, count++) {
                String body = String.format("\n%5d  %20.5f  %20.5f %20.8f", count,
                        results3[i][j++], results3[i][j++], results3[i][j]);
                taLog.appendText(body);
            }
            taLog.appendText("\n\n");
        }


    }

// inner clas for result viewing
    private class ResultPane extends VBox {
        private Label lblName = new Label();
        private Label lblRoots = new Label();
        private Label lblIterations = new Label();

        public ResultPane(String name) {
            HBox contentHB = new HBox(5);
            Label lblKoreni = new Label("Корені: ");
            lblKoreni.setFont(Font.font(name, FontWeight.NORMAL, 16));
            Label lblIteratii = new Label("Ітерації: ");
            lblIteratii.setFont(Font.font(name, FontWeight.NORMAL, 16));
            this.getChildren().addAll(lblName, contentHB);
            VBox resLabelsVB1 = new VBox(5);
            resLabelsVB1.getChildren().addAll(lblKoreni, lblIteratii);
            VBox resLabelsVB2 = new VBox(5);
            resLabelsVB2.getChildren().addAll(lblRoots, lblIterations);
            contentHB.getChildren().addAll(resLabelsVB1, resLabelsVB2);
            lblName.setText(name);
            lblName.setFont(Font.font(null, FontWeight.BOLD, 20));
            lblRoots.setFont(Font.font(name, FontWeight.BOLD, 16));
            lblIterations.setFont(Font.font(name, FontWeight.BOLD, 16));

            this.setStyle("-fx-padding: 10;" +
                    "-fx-border-style: solid inside;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-insets: 5;" +
                    "-fx-border-radius: 5;" +
                    "-fx-border-color: blue;");
        }

        public void setLblName(String text) {
            lblName.setText(text);
        }

        public void setLblRoots(String text) {
            lblRoots.setText(text);
        }

        public void setLblIterations(String text) {
            lblIterations.setText(text);
        }

        public void initPane(String roots, String iterations) {
            //setLblName(name);
            setLblRoots(roots);
            setLblIterations(iterations);
        }
    }

    public static void main(String[] args) {
        System.setProperty("glass.accessible.force", "false");
        launch(args);
    }
}