package br.com.fjp.campominado.views;

import java.util.Arrays;
import java.util.Scanner;

import br.com.fjp.campominado.exceptions.ExplosionException;
import br.com.fjp.campominado.exceptions.QuitGameException;
import br.com.fjp.campominado.models.Board;

public class BoardTerminal {
    private Board board;
    private Scanner scanner = new Scanner(System.in);

    public BoardTerminal(Board board) {
        this.board = board;

        initGame();
    }
    
    private void initGame() {
        boolean keepGame = true;

        try {
            while(keepGame) {
                gameCicle();

                System.out.print("Jogar novamente (S/n): ");
                String answer = scanner.nextLine();

                if(answer.equalsIgnoreCase("n")) {
                    keepGame = false;
                } else {
                    board.reset();
                }
            }
        } catch (QuitGameException e) {
            System.out.println("Fim! Obrigado por jogar!");
        } finally {
            scanner.close();
        }
    }

    private void gameCicle() {
        try {
            int line, column;

            while(!board.isGoalAchieved()) {
                System.out.println(board);
                String ans = getUserAnswer("Digite (Linha, Coluna): ");
                
                if(ans == null || ans.length() < 2) {
                    System.out.println("Valor inválido, digite novamente!");
                    continue;
                }

                line = Integer.parseInt(ans.charAt(0)+"");
                column = Integer.parseInt(ans.charAt(2)+"");
                
                System.out.print("1 - Abrir ou 2 - (Des)marcar: ");
                ans = getUserAnswer("Digite (Linha, Coluna): ");
                
                if(ans.equals("1")) {
                    board.openField(line, column);
                } else if(ans.equals("2")) {
                    board.markField(line, column);
                }
            }

            System.out.println("Você ganhou!!!\nFim de Jogo!");
            System.out.println(board);
        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("\u001B[31m" + "Mina explodida!\nFim de jogo!" + "\u001B[0m");
        }
    }

    public String getUserAnswer(String text) {
        System.out.print(text);
        String ans = scanner.nextLine();

        if(ans.equalsIgnoreCase("sair")) {
            throw new QuitGameException();
        }

        String result;
        
        try {
            result = Arrays.stream(ans.split(","))
                .map(xy -> Integer.parseInt(xy.trim()))
                .toList()
                .toString()
                .replace(" ", "")
                .replace("[", "")
                .replace("]", "");
            
                if(result.length() > 3) {
                    result = null;
                }

        } catch (NumberFormatException e) {
            result = null;
        }

        return result;
    }
}
