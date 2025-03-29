package br.com.fjp.campominado.models;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private final int line;
    private final int column;

    private boolean hasMine;
    private boolean isOpen;
    private boolean isMarked;

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

    public boolean addNeighbor(Field field) {
        int neighborLine = field.getLine();
        int neighborColumn = field.getColumn();

        int lineDistance = Math.abs(neighborLine - this.getLine());
        int columnsDistance = Math.abs(neighborColumn - this.getColumn());

        int totalLines = 100; // Linhas totais do tabuleiro
        int totalColumns = 100; // Colunas totais do tabuleiro

        if(lineDistance == 0 && columnsDistance == 0) {
            throw new RuntimeException("Campo com a mesma coordenada!");
        }

        if(this.getLine() == 0 && neighborLine < 0) {
            throw new RuntimeException("Campo em uma linha menor que permitida");
        }
        
        if(this.getLine() == totalLines && neighborLine > totalLines) {
            throw new RuntimeException("Campo em uma linha menor que permitida");
        }

        if(this.getColumn() == 0 && neighborColumn < 0) {
            throw new RuntimeException("Campo em uma linha menor que permitida");
        }

        if(this.getColumn() == totalColumns && neighborColumn > totalColumns) {
            throw new RuntimeException("Campo em uma linha menor que permitida");
        }

        if(columnsDistance > 1 || lineDistance > 1) {
            throw new RuntimeException("Campo muito distante!");
        }

        return this.neighbors.add(field);
    }
}
