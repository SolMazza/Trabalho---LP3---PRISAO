package org.example;

public class Main {
    public static void main(String[] args) {
        Cells cells = new Cells();

        Prisoners prisonersList = new Prisoners();

        for (Prisoner prisoner : prisonersList.getPrisoners()) {
            System.out.println("\n ||Tentando alocar prisioneiro: " + prisoner.getName() +
                    "| |Periculosidade: " + prisoner.getDangerLevel() +
                    "| |Lider de Facção: " + prisoner.getFaccaoLeader() +
                    "| |Deficiencia: " + prisoner.getDisability() + "||");

            boolean alocado = cells.alocarPrisonerNaCela(prisoner);
            if (alocado) {
                System.out.println("Prisioneiro " + prisoner.getName() + " foi alocado com sucesso.");
            } else {
                if (prisoner.getDisability() == Disability.SIM) {
                    System.out.println("Não foi possível alocar o prisioneiro " + prisoner.getName() +
                            " porque ele tem deficiência e não pode ficar sozinho.");
                } else {
                    System.out.println("Não foi possível alocar o prisioneiro " + prisoner.getName() + ".");
                }
            }
        }

        cells.teclado.fechar();
    }
}
