package org.example.teclado;

import java.util.Scanner;

public class Teclado {
    private Scanner scanner;

    public Teclado() {
        scanner = new Scanner(System.in);
    }

    public String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    public int lerInt(String mensagem) {
        System.out.print(mensagem);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            }
        }
    }

    public void fechar() {
        scanner.close();
    }
}
