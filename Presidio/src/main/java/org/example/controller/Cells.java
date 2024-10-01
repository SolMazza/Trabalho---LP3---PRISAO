package org.example.controller;

import org.example.model.Cell;

import java.util.ArrayList;

public class Cells {
    private ArrayList<Cell> cells;


    public Cells() {
        startList();
    }

    private void startList() {
        cells = new ArrayList<>();
        cells.add(new Cell("A", 10, 10));
        cells.add(new Cell("B", 20, 20));
        cells.add(new Cell("C", 5, 5));
        cells.add(new Cell("D", 7, 7));
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }


}