package com.webmind28.pathfinding;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

public class Pathfinding {
    GridCell[][] cells;
    NavigationGrid<GridCell> navGrid;
    AStarGridFinder<GridCell> finder;

    public Pathfinding(boolean[][] matrix) {
        int xLines = matrix.length;
        int yLines = matrix[0].length;
        this.cells = new GridCell[xLines][yLines];
        for (int x = 0; x < xLines; x++) {
            for (int y = 0; y < yLines; y++) {
                this.cells[x][y] = new GridCell(!matrix[x][y]);
            }
        }

        this.navGrid = new NavigationGrid(cells, true);

        GridFinderOptions opt = new GridFinderOptions();
        opt.allowDiagonal = true;
        opt.dontCrossCorners = false;
        this.finder = new AStarGridFinder(GridCell.class, opt);
    }

    public List<int[]> getPath(long[] startCell, long[] endCell) {
        List<GridCell> pathToEnd = this.finder.findPath(toIntExact(startCell[1]), toIntExact(startCell[0]), toIntExact(endCell[1]), toIntExact(endCell[0]), this.navGrid);
        return pathToEnd.stream().map(cell -> new int[]{cell.getY(), cell.getX()}).collect(Collectors.toList());
    }

    public static double[] convertFromGrid(int x, int y, double startX, double startY, double step) {
        return new double[]{startX + x * step + step / 2, startY + y * step + step / 2};
    }

    public static long[] convertToGrid(double x, double y, double startX, double startY, double step) {
        return new long[]{Math.round(Math.abs(x - startX) / step), Math.round(Math.abs(y - startY) / step)};
    }

    public static List<double[]> convertFromGrid(List<int[]> coordinates, double startX, double startY, double step) {
        return coordinates.stream().map(coord -> convertFromGrid(coord[0], coord[1], startX, startY, step)).collect(Collectors.toList());
    }

    public static List<long[]> convertToGrid(List<double[]> coordinates, double startX, double startY, double step) {
        return coordinates.stream().map(coord -> convertToGrid(coord[0], coord[1], startX, startY, step)).collect(Collectors.toList());
    }
}
