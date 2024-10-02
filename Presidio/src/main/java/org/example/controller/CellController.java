package org.example.controller;

import org.example.exception.NoneCellFind;
import org.example.model.Disability;
import org.example.model.FaccaoLeader;
import org.example.model.Cell;
import org.example.model.Prisoner;

import java.util.ArrayList;
import java.util.List;

public class CellController {

    /**
     * Verifica se é possível alocar um  prisioneiro líder de facção em uma das celas fornecidas.
     * @param cells lista de celas disponíveis
     * @param newPrisoner O novo prisioneiro
     * @return {@code true} se o novo prisioneiro pode ser alocado em alguma das celas,
     *         {@code false} se não tiver como alocar.
     */
    public boolean thereIsFaccaoLeader(List<Cell> cells, Prisoner newPrisoner) {
        for (Cell cell : cells) {
            if (cell.getPrisonersInCell().isEmpty()) {
                return true; // Se a cela estiver vazia, pode alocar o líder de facção
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
     * Verifica se é possível alocar um prisioneiro comum em uma das celas fornecidas.
     * @param cells lista de celas disponíveis .
     * @param newPrisoner O novo prisioneiro.
     * @return {@code true} se o novo prisioneiro pode ser alocado em alguma das celas,
     *         {@code false} se não tiver como alocar
     */
    public boolean thereIsComumPrisoner(List<Cell> cells, Prisoner newPrisoner) {
        for (Cell cell : cells) {
            if (cell.getPrisonersInCell().isEmpty()) {
                return true; // Se a cela estiver vazia, pode alocar prisioneiros comuns
            }
            for (Prisoner prisoner : cell.getPrisonersInCell()) {
                if (prisoner.getFaccaoLeader() == FaccaoLeader.NAO && newPrisoner.getFaccaoLeader() == FaccaoLeader.NAO) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retorna uma lista de celas que não estão vazias//verificação para os deficientes.
     * @param cells Uma lista de celas disponíveis.
     * @return Uma lista de celas não vazias.
     */
    public List<Cell> celasNaoVaziasParaDeficiente(List<Cell> cells) {
        List<Cell> nonEmptyCells = new ArrayList<>();

        for (Cell cell : cells) {
            if (!cell.getPrisonersInCell().isEmpty()) {
                nonEmptyCells.add(cell);
            }
        }
        return nonEmptyCells;
    }

    /**
     * Verifica se a lista de celas fornecida está vazia.
     * @param listHasSpace A lista de celas a ser verificada.
     * @return {@code false} se houver celas disponíveis.
     * @throws NoneCellFind Se não houver celas disponíveis.
     */
    public boolean isEmptyCell(List<Cell> listHasSpace) throws NoneCellFind {
        if (listHasSpace.isEmpty()) {
            throw new NoneCellFind("Não há celas disponíveis para alocar o prisioneiro.");
        }

        return false;
    }

    /**
     * Retorna uma lista de celas com espaço para o prisioneiro, considerando as definições do tab
     * @param prisoner O prisioneiro a ser alocado.
     * @param cells    A lista de celas disponíveis.
     * @return Uma lista de celas adequadas.
     * @throws Exception Se não houver celas disponíveis adequadas.
     */
    public List<Cell> cellHasSpace(Prisoner prisoner, List<Cell> cells) throws Exception {
        List<Cell> listHasSpace;
        if (prisoner.getFaccaoLeader() == FaccaoLeader.SIM){
         listHasSpace = verificacaoFaccaoLeader(prisoner, cells);
        } else {
            listHasSpace = cells;
        }
        listHasSpace.removeIf(cell -> !hasSpaceForPrisoner(cell, prisoner));

        return prisonerDisability(prisoner, listHasSpace);
    }

    /**
     * Aloca um prisioneiro em uma cela.
     * @param prisoner O prisioneiro a ser alocado.
     * @param cell  A cela onde o prisioneiro será alocado.
     * @param cellList A lista de celas disponíveis.
     * @return {@code true} se o prisioneiro foi alocado com sucesso, {@code false} se não tiver espaço.
     */
    public boolean allocatePrisonerToCell(Prisoner prisoner, Cell cell, List<Cell> cellList) {
        boolean alocado = cell.allocatePrisoner(prisoner);
        if (alocado && !hasSpaceForPrisoner(cell, prisoner)) {
            cellList.remove(cell);
        }

        return alocado;
    }

    /**
     * Verifica se há espaço na cela para o prisioneiro.
     * @param cell A cela a ser verificada.
     * @param prisoner O prisioneiro a ser alocado.
     * @return {@code true} se houver espaço suficiente na cela, {@code false} se não tiver espaçp.
     */
    private boolean hasSpaceForPrisoner(Cell cell, Prisoner prisoner) {
        return cell.getFreeSpace() >= prisoner.getDangerLevel();
    }

    /**
     * @param prisoner O prisioneiro a ser alocado.
     * @param cells A lista de celas disponíveis.
     * @return Uma lista de celas.
     */
    public List<Cell> verificacaoFaccaoLeader(Prisoner prisoner, List<Cell> cells) {
        ArrayList<Cell> listHasSpace = new ArrayList<>();

        for (Cell cell : cells) {
            if (prisoner.getFaccaoLeader() == FaccaoLeader.SIM) {
                if (thereIsFaccaoLeader(cells, prisoner)) {
                    listHasSpace.add(cell);
                }
            } else if (prisoner.getFaccaoLeader() == FaccaoLeader.NAO) {
                if (thereIsComumPrisoner(cells, prisoner)) {
                    listHasSpace.add(cell);
                }
            }
        }
        return listHasSpace;
    }

    /**
     * Trata a alocação de prisioneiros com deficiência.
     * @param prisoner
     * @param cells
     * @return Uma lista de celas.
     * @throws Exception Se não houver celas para deficientes.
     */
    private List<Cell> prisonerDisability(Prisoner prisoner, List<Cell> cells) throws Exception {
        if (prisoner.getDisability() == Disability.SIM) {
            List<Cell> nonEmptyCells = celasNaoVaziasParaDeficiente(cells);

            if (!nonEmptyCells.isEmpty()) {
                return nonEmptyCells;
            } else {
                throw new Exception("Não há celas adequadas (não vazias) para prisioneiros deficientes.");
            }
        }

        if (!isEmptyCell(cells)) {
            return cells;
        }
        throw new Exception("Erro na execução do método");
    }
}
