package org.example.controller;

import org.example.exception.NoneListFind;
import org.example.model.Disability;
import org.example.model.FaccaoLeader;
import org.example.model.Cell;
import org.example.model.Prisoner;
import org.example.teclado.Teclado;

import java.util.ArrayList;
import java.util.List;

public class CellValidator {
  private Teclado teclado;



    /**
     *
     * Verifica se é possível alocar um novo prisioneiro lider de faccao em uma das celas fornecidas.
     * @param cells Uma lista de celas disponíveis onde o prisioneiro pode ser alocado.
     * @param newPrisoner O novo prisioneiro que está tentando ser alocado em uma das celas.
     * @return {@code true} se o novo prisioneiro pode ser alocado em alguma das celas,
     *         {@code false} caso contrário.
     */
    public boolean thereIsFaccaoLeader(List<Cell> cells, Prisoner newPrisoner) {
        for (Cell cell : cells) {
            if (cell.getPrisonersInCell().isEmpty()){
                return true; //Caso não tenha ninguém, deve poder colocar um lider de facção
            }
            for (Prisoner prisoner : cell.getPrisonersInCell()) {
                if (prisoner.getFaccaoLeader() == FaccaoLeader.SIM && newPrisoner.getFaccaoLeader() == FaccaoLeader.SIM) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Verifica se é possível alocar um novo prisioneiro comum em uma das celas fornecidas.
     * @param cells Uma lista de celas disponíveis onde o prisioneiro pode ser alocado.
     * @param newPrisoner O novo prisioneiro que está tentando ser alocado em uma das celas.
     * @return {@code true} se o novo prisioneiro pode ser alocado em alguma das celas,
     *         {@code false} caso contrário.
     */
    public boolean thereIsComumPrisoner(List<Cell> cells, Prisoner newPrisoner) {
        for (Cell cell : cells) {
            if (cell.getPrisonersInCell().isEmpty()) {
                return true; // Se a cela estiver vazia, pode alocar.
            }
            for (Prisoner prisoner : cell.getPrisonersInCell()) {
                if (prisoner.getFaccaoLeader() == FaccaoLeader.NAO && newPrisoner.getFaccaoLeader() == FaccaoLeader.NAO) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean disabilityCheckTrue(List<Cell> cells) {
        for (Cell cell : cells) {
            if (cell.getPrisonersInCell().isEmpty()){
                return true; //Caso não tenha ninguém, não deve ser possível alocar um prisioneiro disability.
            }
        }
        return false;
    }




    public boolean isEmptyCell(List<Cell> listHasSpace) {
        if (listHasSpace.isEmpty()) {
            System.out.println("Não há celas disponíveis para alocar o prisioneiro.");
            return true;
        }
     return false;
    }

    public List<Cell> cellHasSpace(Prisoner prisoner, List<Cell> cells) throws NoneListFind {
        ArrayList<Cell> listHasSpace = new ArrayList<>();

        for (Cell cell : cells) {
            if (cell.getFreeSpace() < prisoner.getDangerLevel()) {
                continue;
            }

            if (prisoner.getFaccaoLeader() == FaccaoLeader.SIM) {
                if (!thereIsFaccaoLeader(cells, prisoner)) {
                    continue;
                }
            }


            if (prisoner.getDisability() == Disability.SIM) {
                if (disabilityCheckTrue(cells)) {
                    continue;
                }
            }
            listHasSpace.add(cell);
        }


        if (!isEmptyCell(listHasSpace)){
            return listHasSpace;
        }
        throw new NoneListFind("Nenhuma cela encontrada para este prisioneiro");
    }
}
