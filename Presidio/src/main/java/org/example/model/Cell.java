package org.example.model;

import java.util.ArrayList;

public class Cell {
    private String name;
    private int capacity;
    private int freeSpace;
    private ArrayList<Prisoner> prisonersInCell;




    public Cell(String name, int capacity, int freeSpace) {
        this.name = name;
        this.capacity = capacity;
        this.freeSpace = freeSpace;
        this.prisonersInCell = new ArrayList<>();

    }

    public ArrayList<Prisoner> getPrisonersInCell() {
        return prisonersInCell;
    }


    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFreeSpace() {
        return freeSpace;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        this.freeSpace = capacity;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                ", freeSpace=" + freeSpace +
                ", prisonersInCell=" + prisonersInCell +
                '}';
    }

    public void setFreeSpace(int freeSpace) {
        this.freeSpace = freeSpace;
    }

    public boolean allocatePrisoner(Prisoner prisoner) {
        if (prisoner.getDangerLevel() <= freeSpace) {
            prisonersInCell.add(prisoner);
            freeSpace -= prisoner.getDangerLevel();
            return true;
        } else {
            System.out.println("Não há espaço suficiente na cela " + name);
            return false;
        }
    }
}
