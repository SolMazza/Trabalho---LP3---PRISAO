package org.example.controller;

import org.example.exception.NoneCellFind;
import org.example.model.Cell;
import org.example.model.Disability;
import org.example.model.FaccaoLeader;
import org.example.model.Prisoner;

import java.util.ArrayList;
import java.util.List;

public class CellController {

    public List<Cell> cellHasSpace(Prisoner prisoner, List<Cell> cells) throws NoneCellFind {
        List<Cell> validCells = filterCellsForPrisonerType(prisoner, cells);
        validCells.removeIf(cell -> !hasSpaceForPrisoner(cell, prisoner));

        if (prisoner.getDisability() == Disability.SIM) {
            List<Cell> nonEmptyCells = celasNaoVaziasParaDeficiente(validCells);
            if (!nonEmptyCells.isEmpty()) {
                return nonEmptyCells;
            }
            throw new NoneCellFind("Não há celas com outros prisioneiros para prisioneiros deficientes.");
        }

        if (validCells.isEmpty()) {
            throw new NoneCellFind("Não há celas disponíveis.");
        }

        return validCells;
    }

    private List<Cell> filterCellsForPrisonerType(Prisoner prisoner, List<Cell> cells) {
        List<Cell> filteredCells;
        if (prisoner.getFaccaoLeader() == FaccaoLeader.SIM) {
            filteredCells = filterCellsForFaccaoLeader(cells);
        } else {
            filteredCells = filterCellsForCommonPrisoner(cells);
        }
        return filteredCells;
    }

    private List<Cell> filterCellsForFaccaoLeader(List<Cell> cells) {
        List<Cell> result = new ArrayList<>();
        for (Cell cell : cells) {
            if (cell.getPrisonersInCell().isEmpty() || cellContainsLeader(cell)) {
                result.add(cell);
            }
        }
        return result;
    }

    private boolean cellContainsLeader(Cell cell) {
        for (Prisoner prisoner : cell.getPrisonersInCell()) {
            if (prisoner.getFaccaoLeader() == FaccaoLeader.SIM) {
                return true;
            }
        }
        return false;
    }

    private List<Cell> filterCellsForCommonPrisoner(List<Cell> cells) {
        List<Cell> result = new ArrayList<>();
        for (Cell cell : cells) {
            if (cell.getPrisonersInCell().isEmpty() || !cellContainsLeader(cell)) {
                result.add(cell);
            }
        }
        return result;
    }

    private boolean hasSpaceForPrisoner(Cell cell, Prisoner prisoner) {
        return cell.getFreeSpace() >= prisoner.getDangerLevel();
    }

    private List<Cell> celasNaoVaziasParaDeficiente(List<Cell> cells) {
        List<Cell> nonEmptyCells = new ArrayList<>();
        for (Cell cell : cells) {
            if (!cell.getPrisonersInCell().isEmpty()) {
                nonEmptyCells.add(cell);
            }
        }
        return nonEmptyCells;
    }

    public boolean allocatePrisonerToCell(Prisoner prisoner, Cell cell, List<Cell> cellList) {
        boolean allocated = cell.allocatePrisoner(prisoner);
        if (allocated && !hasSpaceForPrisoner(cell, prisoner)) {
            cellList.remove(cell);
        }
        return allocated;
    }
}
