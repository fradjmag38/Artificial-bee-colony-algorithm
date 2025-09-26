package projmod;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.util.*;
public class Plot2D extends Application {
	private static List<Double> globalMinsList;
	
	public static void setGlobalMins(List<Double> minsList) {
        globalMinsList = minsList;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Graphique de la fonction Rastrigin");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X");
        yAxis.setLabel("Valeur de la fonction Rastrigin");

        final LineChart<Number, Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Fonction Rastrigin");

        XYChart.Series series = new XYChart.Series();
        series.setName(" optimisation de Rastrigin");
     
        // Ajoutez les valeurs minimales obtenues à partir de l'algorithme Bee Colony à la série
        for (int i = 0; i < globalMinsList.size(); i++) {
            series.getData().add(new XYChart.Data<>(i + 1, globalMinsList.get(i)));    
        }
       // Ajoutez la valeur minimale avec une autre couleur
        double minGlobal = Collections.min(globalMinsList);
        XYChart.Series minSeries = new XYChart.Series();
        minSeries.getData().add(new XYChart.Data<>(globalMinsList.indexOf(minGlobal) + 1, minGlobal));
    
        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }

    private double rastriginFunction(double x) {
        // Formule de la fonction de Rastrigin
        return 10 * 2 + Math.pow(x, 2) - 10 * Math.cos(2 * Math.PI * x);
    }
}
