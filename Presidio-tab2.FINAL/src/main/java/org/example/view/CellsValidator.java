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
    private final CellController controller = new CellController();
    private final PrisonerFactory prisonerFactory = new PrisonerFactory();
    private final CellFactory cellFactory = new CellFactory();
    private final Teclado teclado = new Teclado();

    public void alocarPrisioneiro() {
        List<Prisoner> prisonersTotal = prisonerFactory.getPrisoners();
        List<Cell> totalCelas = cellFactory.getCells();

        while (!prisonersTotal.isEmpty() && !totalCelas.isEmpty()) {
            try {
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

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (prisonersTotal.isEmpty()) {
            System.out.println("Todos os prisioneiros foram alocados.");
        } else {
            System.out.println("Não há mais celas disponíveis. Prisioneiros restantes não alocados.");
        }
    }

    public Prisoner selectPrisoner(List<Prisoner> prisoners) throws NonePrisoner {
        try {
            printPrisoners(prisoners);
            String name = teclado.lerString("Digite o nome do prisioneiro que deseja alocar: ");
            return prisionerByName(prisoners, name);
        } catch (NonePrisoner e) {
            throw new NonePrisoner("Prisioneiro não encontrado. Por favor, tente novamente.");
        }
    }

    public Cell selectCell(List<Cell> cells) throws NoneCellFind {
        try {
            printCells(cells);
            String name = teclado.lerString("Digite o nome da cela que deseja alocar: ");
            return cellByName(cells, name);
        } catch (NoneCellFind e) {
            throw new NoneCellFind("Cela não encontrada. Por favor, tente novamente.");
        }
    }



    public void printPrisoners(List<Prisoner> prisoners){
        for (Prisoner prisoner : prisoners) {
            System.out.println(" | "+ prisoner.toString() +"\n");
            System.out.println("--------------------");
        }
    }
    public Prisoner prisionerByName(List<Prisoner> prisoners, String prisonerName) throws NonePrisoner {
        return prisoners.stream()
                .filter(prisoner -> prisoner.getName().equalsIgnoreCase(prisonerName))
                .findFirst()
                .orElseThrow(() -> new NonePrisoner("Nenhum prisioneiro com este nome encontrada"));
    }
    public void printCells(List<Cell> cells) {
        for (Cell cell : cells) {
            System.out.println(" | " + cell.toString() + "\n");
            System.out.println("--------------------");
        }
    }

    public Cell cellByName(List<Cell> cells, String cellName) throws NoneCellFind {
        return cells.stream()
                .filter(cell -> cell.getName().equalsIgnoreCase(cellName))
                .findFirst()
                .orElseThrow(() -> new NoneCellFind("Nenhuma cela com este nome encontrada."));
    }
}
