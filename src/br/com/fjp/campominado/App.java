package br.com.fjp.campominado;

import br.com.fjp.campominado.models.Board;
import br.com.fjp.campominado.views.BoardTerminal;

public class App {
    public static void main(String[] args) {
        Board board = new Board(6, 6, 6);
        new BoardTerminal(board);
    }
}
