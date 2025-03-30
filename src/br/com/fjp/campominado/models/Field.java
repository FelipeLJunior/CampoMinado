package br.com.fjp.campominado.models;

import java.util.ArrayList;
import java.util.List;

import br.com.fjp.campominado.exceptions.ExplosionException;

public class Field {
    private final int line;
    private final int column;

    private boolean mine;
    private boolean open;
    private boolean markup;

    private List<Field> neighbors = new ArrayList<>();

    Field(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public boolean isMarked() {
        return this.markup;
    }

    public boolean isOpen() {
        return this.open;
    }

    public boolean hasMine() {
        return this.mine;
    }

    public boolean addNeighbor(Field field) {
        int neighborLine = field.getLine();
        int neighborColumn = field.getColumn();

        int lineDistance = Math.abs(neighborLine - this.getLine());
        int columnsDistance = Math.abs(neighborColumn - this.getColumn());

        int totalLines = 100; // Linhas totais do tabuleiro
        int totalColumns = 100; // Colunas totais do tabuleiro

        if(lineDistance == 0 && columnsDistance == 0) {
            return false;// throw new RuntimeException("Campo com a mesma coordenada!");
        }

        if(this.getLine() == 0 && neighborLine < 0) {
            return false;// throw new RuntimeException("Campo em uma linha menor que permitida");
        }
        
        if(this.getLine() == totalLines && neighborLine > totalLines) {
            return false;// throw new RuntimeException("Campo em uma linha menor que permitida");
        }

        if(this.getColumn() == 0 && neighborColumn < 0) {
            return false;// throw new RuntimeException("Campo em uma linha menor que permitida");
        }

        if(this.getColumn() == totalColumns && neighborColumn > totalColumns) {
            return false;// throw new RuntimeException("Campo em uma linha menor que permitida");
        }

        if(columnsDistance > 1 || lineDistance > 1) {
            return false;// throw new RuntimeException("Campo muito distante!");
        }

        return this.neighbors.add(field);
    }

    void switchMarkup() {
        if(!this.open) {
            this.markup = !this.markup;
        }
    }

    void setMine() {
        this.mine = true;
    }

    boolean open() {
        if(open || markup) {
            return false;
        }

        if(mine) {
            throw new ExplosionException();
        }

        open = true;
        
        if(neighborhoodIsSafe()) {
            neighbors.forEach(neighbor -> neighbor.open());
        }

        return true;
    }

    boolean neighborhoodIsSafe() {
        return neighbors.stream().noneMatch(neighbor -> neighbor.mine);
    }

    boolean goalAchieved() {
        boolean discovered = !mine && isOpen();
        boolean protectedFromMine = mine && isMarked();

        return discovered || protectedFromMine;
    }

    long neighborsMines() {
        return neighbors.stream().filter(n -> n.hasMine()).count();
    }

    void reset() {
        mine = false;
        markup = false;
        open = false;
    }

    @Override
    public String toString() {
        if(isMarked()) {
            return "X";
        }

        if(isOpen() && hasMine()) {
            return "*";
        }

        if(isOpen() && neighborsMines() > 0L) {
            return Long.toString(neighborsMines());
        }
        
        return " ";
    }
}
