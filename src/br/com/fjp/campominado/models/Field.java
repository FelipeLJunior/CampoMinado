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
        int columnDistance = Math.abs(neighborColumn - this.getColumn());

        if(lineDistance == 0 && columnDistance == 0) {
            return false;
        }

        if(columnDistance > 1 || lineDistance > 1) {
            return false;
        }

        this.neighbors.add(field);
        
        return true;
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
        if(isOpen() || isMarked()) {
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

    void open(boolean open) {
        this.open = open;
    }

    boolean neighborhoodIsSafe() {
        return neighbors.stream().noneMatch(neighbor -> neighbor.hasMine());
    }

    boolean isGoalAchieved() {
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

        if(isOpen()) {
            return " ";
        }
        
        return "?";
    }
}
