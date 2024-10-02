package org.example.model;

public class Prisoner {
    private String name;
    private int dangerLevel;
    private Disability disability;
    private FaccaoLeader faccaoLeader;

    public Prisoner(String name, int dangerLevel, Disability disability, FaccaoLeader faccaoLeader) {
        this.name = name;
        this.dangerLevel = dangerLevel;
        this.disability = disability;
        this.faccaoLeader = faccaoLeader;
    }

    @Override
    public String toString() {
        return "Prisoner{" +
                "name='" + name + '\'' +
                ", dangerLevel=" + dangerLevel +
                ", disability=" + disability +
                ", faccaoLeader=" + faccaoLeader +
                '}';
    }


    public String getName() {
        return name;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public Disability getDisability() {
        return disability;
    }

    public FaccaoLeader getFaccaoLeader() {
        return faccaoLeader;
    }
}
