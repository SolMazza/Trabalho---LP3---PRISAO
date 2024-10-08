package org.example.factory;

import org.example.model.Disability;
import org.example.model.FaccaoLeader;
import org.example.model.Prisoner;

import java.util.ArrayList;

public class PrisonerFactory {
    private ArrayList<Prisoner> prisoners;

    public PrisonerFactory() {
        startList();
    }
    private void startList(){
        prisoners = new ArrayList<>();
        prisoners.add(new Prisoner("Z", 5, Disability.NAO, FaccaoLeader.NAO));
        prisoners.add(new Prisoner("Y", 10, Disability.NAO, FaccaoLeader.NAO));
        prisoners.add(new Prisoner("K", 5, Disability.NAO, FaccaoLeader.NAO));
        prisoners.add(new Prisoner("L", 10, Disability.NAO, FaccaoLeader.SIM));
        prisoners.add(new Prisoner("O", 5, Disability.NAO, FaccaoLeader.SIM));
        prisoners.add(new Prisoner("X", 3, Disability.SIM, FaccaoLeader.NAO));
        prisoners.add(new Prisoner("M", 1, Disability.SIM, FaccaoLeader.NAO));
        }

    public ArrayList<Prisoner> getPrisoners() {
        return prisoners;
    }
}
