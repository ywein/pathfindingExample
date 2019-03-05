package com.webmind28.pathfindingApp;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import com.webmind28.pathfinding.Pathfinding;

public class Main {

    public static void main(String[] args) {
        Gson gson = new Gson();
        try {
            ClassLoader classLoader = Main.class.getClassLoader();
            List<String> jsonData = Collections.emptyList();
            JsonReader reader = new JsonReader(new FileReader(classLoader.getResource("matrix.json").getFile()));
            boolean[][] matrix = gson.fromJson(reader, boolean[][].class);

            // Bottom left of the grid
            double gridStartX = 32.1054513823108;
            double gridStartY = 34.8094213183362;

            // grid step
            double step = ((0.5 * 0.00001) / 1.11);


            // Example path points
            double startX = 32.10630983027194;
            double startY = 34.81064468622208;
            double endX = 32.10627234224652;
            double endY = 34.810412675142295;

            Pathfinding pathfinding = new Pathfinding(matrix);

            long[] startCell = Pathfinding.convertToGrid(startX, startY, gridStartX, gridStartY, step);
            long[] endCell = Pathfinding.convertToGrid(endX, endY, gridStartX, gridStartY, step);

            long[] test = Pathfinding.convertToGrid(startX, startY, gridStartX, gridStartY, step);
            System.out.println(test);


            List<int[]> path = pathfinding.getPath(startCell, endCell);
            List<double[]> pathCoord = Pathfinding.convertFromGrid(path, gridStartX, gridStartY, step);

            System.out.println(gson.toJson(path));
            System.out.println(gson.toJson(pathCoord));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
