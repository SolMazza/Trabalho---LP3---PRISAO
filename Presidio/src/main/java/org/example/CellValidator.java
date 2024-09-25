package org.example;

import java.util.List;

public class CellValidator {


    public boolean thereAreFaccaoLeader(List<Cell> cells, Prisoner newPrisoner) {
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

    public boolean disabilityCheckTrue(List<Cell> cells) {
        for (Cell cell : cells) {
            if (cell.getPrisonersInCell().isEmpty()){
                return true; //Caso não tenha ninguém, não deve ser possível alocar um prisioneiro disability.
            }
        }
        return false;
    }

}
