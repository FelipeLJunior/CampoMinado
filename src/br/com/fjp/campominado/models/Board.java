package br.com.fjp.campominado.models;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.fjp.campominado.exceptions.ExplosionException;

public class Board {
    private int linesQuantity;
    private int columnsQuantity;
    private int minesQuantity;

    private final List<Field> fields = new ArrayList<>();

    public Board(int linesQuantity, int columnsQuantity, int minesQuantity) {
        this.linesQuantity = linesQuantity;
        this.columnsQuantity = columnsQuantity;
        this.minesQuantity = minesQuantity;

        fieldsGenerate();
        associateNeighbors();
        shuffleMines();
    }

    public void openField(int line, int column) {
        try {
            fields.stream()
                .filter(f -> f.getLine() == line && f.getColumn() == column)
                .findFirst()
                .ifPresent(f -> f.open());
        } catch (ExplosionException e) {
            fields.stream().filter(f -> f.hasMine()).forEach(f -> f.open(true));
            throw e;
        }
    }

    public void markField(int line, int column) {
        fields.stream()
            .filter(f -> f.getLine() == line && f.getColumn() == column)
            .findFirst()
            .ifPresent(f -> f.switchMarkup());
    }

    private void fieldsGenerate() {
        for(int line = 0; line < linesQuantity; line++) {
            for(int column = 0; column < columnsQuantity; column++) {
                fields.add(new Field(line, column));
            }
        }
    }

    private void associateNeighbors() {
        for(Field f1: fields) {
            for(Field f2: fields) {
                f1.addNeighbor(f2);
            }
        }
    }

    private void shuffleMines() {
        long armedMines = 0;
        Predicate<Field> hasMine = f -> f.hasMine();
        int totalFields = fields.size();

        while(armedMines < minesQuantity) {
            int randomIndex = (int) (Math.random() * totalFields);
            fields.get(randomIndex).setMine();
            
            armedMines = fields.stream().filter(hasMine).count();
        }
    }

    public boolean isGoalAchieved() {
        return fields.stream().allMatch(f -> f.isGoalAchieved());
    }

    public void reset() {
        fields.forEach(f -> f.reset());
        shuffleMines();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder lastLine = new StringBuilder(" ");

        int i = 0;
        for(int l = 0; l < linesQuantity; l++) {
            sb.append(l + "\u001B[36m");
            for (int c = 0; c < columnsQuantity; c++) {
                sb.append(" ");
                sb.append(fields.get(i++));
            }
            lastLine.append(" ");
            lastLine.append((i / columnsQuantity)-1);
            sb.append("\u001B[0m\n");
        }

        sb.append(lastLine);

        return sb.toString();
    }
}
