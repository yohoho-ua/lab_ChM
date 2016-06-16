package lab2;/**
 * Created by john on 12.06.2016.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MatrixForm extends Application {
    private int width = 1280;
    private int height = 800;
    private Button btnLoad = new Button("Відкрити");
    private Button btnSolve = new Button("Рішення");
    private File file;
    private Label lblStatus = new Label("Оберіть файл з данимм СЛАУ");
    private Label lblRoots = new Label("Корені : ");
    private Label lblDetRes = new Label();
    private GridPane rootsPane = new GridPane();
    private TextArea taLog = new TextArea();
    /*private TableView table = new TableView();
    private TableColumn tbcMatrix;
    private TableColumn tbcFree;
    private TableColumn tbcAnti;*/


    @Override
    public void start(Stage stage) {
        Label lblDet = new Label("Визначник :");
        BorderPane pane = new BorderPane();
        BorderPane controlsPane = new BorderPane();
        pane.setTop(lblStatus);
        pane.setLeft(controlsPane);
        taLog.setFont(Font.font("Ariel", FontWeight.NORMAL, 15));

        VBox results = new VBox(10);
        results.getChildren().addAll(lblDet,lblDetRes, lblRoots, rootsPane);
        results.setPadding(new Insets(10, 10,10,20));
        controlsPane.setCenter(results);
        lblDet.setFont(Font.font(null, FontWeight.BOLD, 15));
        lblRoots.setFont(Font.font(null, FontWeight.BOLD, 15));
        lblDetRes.setFont(Font.font(null, FontWeight.BOLD, 20));




        VBox buttons = new VBox(10);
        buttons.getChildren().addAll(btnLoad, btnSolve);
        buttons.setPadding(new Insets(10, 20,10,10));
        lblStatus.setPadding(new Insets(10, 5,10,5));

        controlsPane.setTop(buttons);

        pane.setCenter(taLog);

        Scene scene = new Scene(pane, width, height);
        stage.setScene(scene);
        stage.show();

        //table



        btnLoad.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            //file = chooser.showOpenDialog(null);
            file = new File("matrix5Main.txt");
            if (file != null) {
                initInput(file);
            }

        });
    }


    private void initInput(File file) {
        double[][] matrix = null;
        double[] free = null;


        try {
            lblStatus.setText("Файл СЛАУ: " + file.getCanonicalPath().toString());
            ArrayList<String[]> strings = new ArrayList<>();

            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                strings.add(input.nextLine().split(","));
            }
            int matrixRang = strings.size();

            matrix = new double[strings.size()][];
            free = new double[strings.size()];

            //fill matrix
            int startRow = 0;
            for (String[] string : strings) {
                double[] temp = new double[matrixRang];
                int cureentLength = string.length;
                if (matrixRang == cureentLength - 1) {
                    for (int i = 0; i < matrixRang; i++) {
                        temp[i] = Double.parseDouble(string[i]);
                    }
                    free[startRow] = Double.parseDouble(string[cureentLength - 1]);
                    matrix[startRow++] = temp;
                } else {
                    throw new Exception("wrong input");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

       /* table.getColumns().clear();
        tbcMatrix = new TableColumn("Коефіцієнти");
        tbcFree = new TableColumn("Вільні\nчлени");
        tbcAnti = new TableColumn("Обернена матриця");
        table.getColumns().addAll(tbcMatrix, tbcFree, tbcAnti);

        TableColumn matrixColumns [] = new TableColumn[matrix.length];
        for (int i = 0; i < matrixColumns.length; i++) {
            matrixColumns[i] = new TableColumn(i+1+"");
            tbcMatrix.getColumns().add(matrixColumns[i]);
        }
        TableColumn antiColumns [] = new TableColumn[matrix.length];
        for (int i = 0; i < matrixColumns.length; i++) {
            antiColumns[i] = new TableColumn(i+1+"");
            tbcAnti.getColumns().add(antiColumns[i]);
        }*/

        Matrix mat = new Matrix(matrix, free);
        mat.solve();
        taLog.clear();
        //System.out.println(mat.getLog());
        //System.out.println("\n Det = " + mat.getDet());
        String[] roots = mat.getRoots();
        /*for (String root : roots) {
            System.out.print(root + "  ");
        }*/

        for (int i = 0; i < roots.length; i++) {
            Label temp = new Label("x"+(i+1) + " = ");
            temp.setFont(Font.font(null, FontWeight.BOLD, 20));
            rootsPane.add(temp, 0,i);
            Label temp1 = new Label(roots[i]);
            temp1.setFont(Font.font(null, FontWeight.BOLD, 20));
            rootsPane.add(temp1, 1,i);

        }

        lblDetRes.setText(mat.getDet());
        taLog.appendText(mat.getLog());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
