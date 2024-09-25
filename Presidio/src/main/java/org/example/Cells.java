package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Cells {
    Teclado teclado = new Teclado();
    private ArrayList<Cell> cells;
    private CellValidator validator;

    public Cells() {
        startList();
    }

    private void startList() {
        cells = new ArrayList<>();
        this.validator = new CellValidator();
        cells.add(new Cell("A", 10, 10));
        cells.add(new Cell("B", 20, 20));
        cells.add(new Cell("C", 5, 5));
        cells.add(new Cell("D", 7, 7));
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public boolean alocarPrisonerNaCela(Prisoner prisoner) {
        ArrayList<Cell> listHasSpace = new ArrayList<>();

        for (Cell cell : cells) {
            if (cell.getFreeSpace() < prisoner.getDangerLevel()) {
                continue;
            }

            if (prisoner.getFaccaoLeader() == FaccaoLeader.SIM) {
                if (!validator.thereAreFaccaoLeader(cells, prisoner)) {
                    continue;
                }
            }


            if (prisoner.getDisability() == Disability.SIM) {
                if (validator.disabilityCheckTrue(cells)) {
                    continue;
                }
            }

            listHasSpace.add(cell);
        }

        if (listHasSpace.isEmpty()) {
            System.out.println("Não há celas disponíveis para alocar o prisioneiro.");
            return false;
        }

        System.out.println("----ESTES SÃO OS NOMES DAS CELAS COM ESPAÇOS E SEUS ESPAÇOS----");
        for (Cell cell : listHasSpace) {
            System.out.println("Nome: " + cell.getName() + " || Espaço disponível: " + cell.getFreeSpace());
        }

        Cell celaSelecionada = null;
        while (celaSelecionada == null) {
            String selecionado = teclado.lerString("----Digite o nome da cela que deseja alocar----");

            for (Cell cell : listHasSpace) {
                if (cell.getName().equalsIgnoreCase(selecionado)) {
                    celaSelecionada = cell;
                    break;
                }
            }

            if (celaSelecionada == null) {
                System.out.println("Cela não encontrada ou não disponível. Tente novamente.");
            }
        }

        boolean sucesso = celaSelecionada.allocatePrisoner(prisoner);
        if (sucesso) {
            System.out.println("Prisioneiro " + prisoner.getName() + " alocado com sucesso na cela " + celaSelecionada.getName());
        } else {
            System.out.println("Falha ao alocar o prisioneiro na cela " + celaSelecionada.getName());
        }

        return sucesso;
    }
}