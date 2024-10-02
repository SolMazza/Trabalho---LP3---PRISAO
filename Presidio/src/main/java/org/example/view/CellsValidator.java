package org.example.view;

import org.example.controller.CellController;
import org.example.exception.NoneCellFind;
import org.example.exception.NonePrisoner;
import org.example.factory.PrisonerFactory;
import org.example.factory.CellFactory;
import org.example.model.Prisoner;
import org.example.model.Cell;
import org.example.teclado.Teclado;

import java.util.List;

public class CellsValidator {
    private CellController controller;
    private PrisonerFactory prisonerFactory;
    private CellFactory cellFactory;
    private Teclado teclado;

    public CellsValidator() {
        controller = new CellController();
        prisonerFactory = new PrisonerFactory();
        cellFactory = new CellFactory();
        teclado = new Teclado();
    }

    public Prisoner selectPrisoner(List<Prisoner> prisoners)  {
        System.out.println("Selecione um prisioneiro para alocar em uma cela:");
        printPrisoners(prisoners);
        String name = teclado.lerString("Digite o nome do prisioneiro que deseja alocar: ");
        Prisoner selectedPrisoner = prisionerByName(prisoners, name);
        return selectedPrisoner;
    }

    public Cell selectCell(List<Cell> cells)  {
        System.out.println("Selecione uma Cela para alocar o prisioneiro:");
        printCells(cells);
        String name = teclado.lerString("Digite o nome da cela que deseja alocar: ");
        Cell selectedCell = cellByName(cells, name);
        return selectedCell;
    }


    public void alocarPrisioneiro() throws Exception {
        List<Prisoner> prisonersTotal = prisonerFactory.getPrisoners();
        List<Cell> totalCelas = cellFactory.getCells();

        while (!prisonersTotal.isEmpty() && !totalCelas.isEmpty()) {
            Prisoner prisoner = selectPrisoner(prisonersTotal);
            List<Cell> cellsWithSpace = controller.cellHasSpace(prisoner, totalCelas);

            if (cellsWithSpace.isEmpty()) {
                System.out.println("Nenhuma cela disponível para o prisioneiro " + prisoner.getName());
                continue;
            }

            Cell cell = selectCell(cellsWithSpace);
            boolean alocado = controller.allocatePrisonerToCell(prisoner, cell, cellsWithSpace);
            if (alocado) {
                System.out.println("Prisioneiro " + prisoner.getName() + " foi alocado com sucesso.");
                prisonersTotal.remove(prisoner);
            } else {
                System.out.println("Não foi possível alocar o prisioneiro " + prisoner.getName() + ".");
            }
        }

        if (prisonersTotal.isEmpty()) {
            System.out.println("Todos os prisioneiros foram alocados.");
        } else {
            System.out.println("Não há mais celas disponíveis. Prisioneiros restantes não alocados.");
        }
    }





    public void printPrisoners(List<Prisoner> prisoners){
        for (Prisoner prisoner : prisoners) {
            System.out.println(" | "+ prisoner.toString() +"\n");
            System.out.println("--------------------");
        }
    }
    public Prisoner prisionerByName(List<Prisoner> prisoners, String prisonerName) throws NonePrisoner {

        for (Prisoner prisoner : prisoners) {

            if (prisoner.getName().equalsIgnoreCase(prisonerName)){
                return prisoner;
            }
        }
        throw new NonePrisoner("Nenhum prisioneiro com este nome encontrada");

    }
    public void printCells(List<Cell> cells) {
        for (Cell cell : cells) {
            System.out.println(" | " + cell.toString() + "\n");
            System.out.println("--------------------");
        }
    }

    public Cell cellByName(List<Cell> cells, String cellName) throws NoneCellFind {
        for (Cell cell : cells) {
            if (cell.getName().equalsIgnoreCase(cellName)) {
                return cell;
            }
        }
        throw new NoneCellFind("Nenhuma cela com este nome encontrada.");
    }
}

