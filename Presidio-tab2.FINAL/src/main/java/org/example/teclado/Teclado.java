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
   }
