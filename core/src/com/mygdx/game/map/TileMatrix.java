package com.mygdx.game.map;

import java.util.ArrayList;

public class TileMatrix {

    private static int MAX_ENTITIES = 15;
    private int id;
    private int width;
    private int height;

    private ArrayList<Tile> tiles;

    public TileMatrix(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
        tiles = new ArrayList<Tile>();
        createTileMap();
    }

    public void createTileMap () {
        int [][] matrix = generateLogicMatrix();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                switch (matrix[i][j]) {
                    case 0:
                        tiles.add(new Tile(0, i, j, 0, 0));
                        break;
                    case 1:
                        int entityX = (int) Math.floor(Math.random() * (2 - 0 + 1)) + 0;
                        int entityWidth = (int) Math.floor(Math.random() * ((5 - entityX) - 3 + 1)) + 3;
                        tiles.add(new Tile(1, i, j, entityX, entityWidth));
                        break;
                    case 2:
                        tiles.add(new Tile(2, i, j, 0, 0));
                        break;
                    case 3:
                        tiles.add(new Tile(3, i, j, 0, 0));
                        break;
                }
            }
        }
    }

    public int[][] generateLogicMatrix () {
        int [][] matrix = new int [width][height];
        int numEntities = 0;
        int rand;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == matrix.length - 1) matrix[i][j] = 0;
                else if (i == 0) {
                    int groundType = (int) Math.floor(Math.floor(Math.random() * (5 - 0 + 1)) + 0);
                    if (groundType == 0 && j > 2 && matrix[i][j - 2] != 0) matrix[i][j] = 0;
                    else matrix[i][j] = 3;
                }
                else if (numEntities < MAX_ENTITIES){
                    // Condicional que comprueba si se está justo arriba del suelo, donde solo peuden ir bloques o aire.
                    if (matrix[i - 1][j] == 3) {
                        if (j > 0 && matrix[i][j - 1] != 2) {
                            rand = (int) Math.floor(Math.random() * (3 - 0) + 0); // Random 1 / 4 para bloque
                            if (rand <= 2) matrix[i][j] = rand;
                            //else matrix[i][j] = 0;
                        }
                    }
                    else if (i == 1 && matrix[i -1][j] == 0) {
                        matrix[i][j] = 1;
                    }
                    //Resto de bloques
                    else {
                        // SI hay un bloque en diagonal inferior que es un bloque, se pone una plataforma o no.   1 / 2
                        if (j > 0 && j < matrix[i].length - 1 && (matrix[i - 1][j - 1] == 2 || matrix[i - 1][j + 1] == 2) && (matrix[i][j - 1] != 1 && matrix[i][j + 1] != 1)) {
                            rand = (int) Math.floor(Math.floor(Math.random() * (1 - 0 + 1)) + 0); //
                            matrix[i][j] = rand;
                        }
                        // SI hay una plataforma en diagonal inferior que es un bloque, se pone una plataforma o no.   1 / 2
                        else if (j > 0 && j < matrix[i].length - 1 && (matrix[i - 1][j - 1] == 1 || matrix[i - 1][j + 1] == 1)&& (matrix[i][j - 1] != 1 && matrix[i][j + 1] != 1)) {
                            rand = (int) Math.floor(Math.floor(Math.random() * (2 - 0 + 1)) + 0); //
                            if(!(rand == 2 && matrix[i - 1][j] == 1))
                                matrix[i][j] = rand;
                        }
                    }
                }
                else {
                    // Añadir mas condiciones quí
                    matrix[i][j] = 0;
                }
            }
        }
        return matrix;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    @Override
    public String toString() {
        String temp = "";
        int oldX = 0;

        for(Tile tile : tiles) {
            if(tile.getX() != oldX)
                temp = temp + "\n";
            temp = temp + tile.getId() + " ";
            oldX = tile.getX();
        }

        return temp;
    }
}
