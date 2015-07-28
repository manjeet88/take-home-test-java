package net.qays.maana.TakeHomeTest;

import java.util.Map;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class BarChartSample extends Application {
    private static Map<Integer, Integer> counts;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Bar Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Country Summary");
        xAxis.setLabel("Word Length (Letters)");
        yAxis.setLabel("Count");
        Scene scene = new Scene(bc, 800, 600);
        Integer minKey = counts.keySet().stream().min(Integer::compare).orElse(0);
        Integer maxKey = counts.keySet().stream().max(Integer::compare).orElse(minKey);
        Integer minVal = counts.values().stream().min(Integer::compare).orElse(0);
        Integer maxVal = counts.values().stream().max(Integer::compare).orElse(minVal);
        log.debug("key min: {}, max: {}\nval min: {}, max: {}", minKey, maxKey, minVal, maxVal);
        IntStream.range(minKey, maxKey + 1).forEach(slot -> {
            log.debug("Adding bar for word length: {}", slot);
            XYChart.Series series1 = new XYChart.Series();
            series1.setName(Integer.toString(slot));
            series1.getData().add(new XYChart.Data(Integer.toString(slot), counts.getOrDefault(slot, 0)));
            bc.getData().add(series1);
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void graph(Map<Integer, Integer> in) {
        counts = in;
        launch(null);
    }
}