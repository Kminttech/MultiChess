package com.google.example.games.tbmpskeleton.grid;


import com.google.example.games.tbmpskeleton.game.ChessGame;
import com.google.example.games.tbmpskeleton.pieces.Bishop;
import com.google.example.games.tbmpskeleton.pieces.ChessPiece;
import com.google.example.games.tbmpskeleton.pieces.King;
import com.google.example.games.tbmpskeleton.pieces.Knight;
import com.google.example.games.tbmpskeleton.pieces.Pawn;
import com.google.example.games.tbmpskeleton.pieces.Queen;
import com.google.example.games.tbmpskeleton.pieces.Rook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Represents an 8x8 chessboard, made of locations that hold Chess pieces.
 * @author Kevin
 */
public class ChessBoard implements Grid<ChessPiece> {
    private HashMap<Location, ChessPiece> map;
    private ChessGame game;
    private boolean inverted;
    /**
     * Create a new empty Chessoard.
     */
    public ChessBoard(ChessGame game) {
        super();
        this.game = game;
        map = new HashMap(32);
    }

    public ChessBoard(Set<Map.Entry<Location, ChessPiece>> entrySet) {
        super();
        map = new HashMap(32);
        for(Map.Entry<Location, ChessPiece> e : entrySet) {
            map.put(e.getKey().copy(), e.getValue().copy());
        }
    }

    public Set<Map.Entry<Location, ChessPiece>> getEntrySet() { return map.entrySet(); }

    @Override
    public void invert() {
        inverted = !inverted;
    }

    @Override
    public boolean isInverted() {
        return inverted;
    }

    @Override
    public ChessPiece get(Location loc) {
        return map.get(loc);
    }

    @Override
    public int getCols() {
        return 8;
    }

    @Override
    public int getRows() {
        return 8;
    }

    @Override
    public boolean isOccupied(Location loc) {
        return map.containsKey(loc);
    }

    @Override
    public boolean isValid(Location loc) {
        return loc != null && (loc.getCol() >= 0 && loc.getCol() < 8) && (loc.getRow() >= 0 && loc.getRow() < 8);
    }

    @Override
    public ChessPiece put(Location loc, ChessPiece chessPiece) {
        if(chessPiece == null) {
            throw new IllegalArgumentException("ChessPiece cannot be null.");
        }
        if(chessPiece.getLocation() != null && !chessPiece.getLocation().equals(loc)) {
            Location prev = chessPiece.getLocation();
            map.remove(prev);
        }

        ChessPiece other = map.get(loc);
        chessPiece.setLocation(loc);
        if(other != null)
            other.setLocation(null);
        map.put(loc, chessPiece);

        return other;
    }

    @Override
    public void remove(Location loc) {
        map.remove(loc);
    }

    public int[][] getIntArray(){
        int[][] board = new int[8][8];
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<Location, ChessPiece> pair = (Map.Entry) it.next();
            int x = pair.getKey().getCol();
            int y = pair.getKey().getRow();
            int rep = pair.getValue().representation();
            board[x][y] = rep;
        }
        return board;
    }

    public ArrayList<ChessPiece> setFromIntArray(int[][] board){
        ArrayList<ChessPiece> arr = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[j][i] != 0){
                    if(board[j][i] == 1){
                        ChessPiece p = new King(true, game, new Location(i, j));
                        map.put(new Location(i, j), p);
                        arr.add(p);
                    }else if(board[j][i] == 2){
                        ChessPiece p = new Queen(true, game, new Location(i, j));
                        map.put(new Location(i, j), p);
                        arr.add(p);
                    }else if(board[j][i] == 3){
                        ChessPiece p = new Bishop(true, game, new Location(i, j));
                        map.put(new Location(i, j), p);
                        arr.add(p);
                    }else if(board[j][i] == 4){
                        ChessPiece p = new Knight(true, game, new Location(i, j));
                        map.put(new Location(i, j), p);
                        arr.add(p);
                    }else if(board[j][i] == 5){
                        ChessPiece p = new Rook(true, game, new Location(i, j));
                        map.put(new Location(i, j), p);
                        arr.add(p);
                    }else if(board[j][i] == 6){
                        ChessPiece p = new Pawn(true, game, new Location(i, j));
                        map.put(new Location(i, j), p);
                        arr.add(p);
                    }else if(board[j][i] == -1){
                        ChessPiece p = new King(false, game, new Location(i, j));
                        map.put(new Location(i, j), p);
                        arr.add(p);
                    }else if(board[j][i] == -2){
                        ChessPiece p = new Queen(false, game, new Location(i, j));
                        map.put(new Location(i, j), p);
                        arr.add(p);
                    }else if(board[j][i] == -3){
                        ChessPiece p = new Bishop(false, game, new Location(i, j));
                        map.put(new Location(i, j), p);
                        arr.add(p);
                    }else if(board[j][i] == -4){
                        ChessPiece p = new Knight(false, game, new Location(i, j));
                        map.put(new Location(i, j), p);
                        arr.add(p);
                    }else if(board[j][i] == -5){
                        ChessPiece p = new Rook(false, game, new Location(i, j));
                        map.put(new Location(i, j), p);
                        arr.add(p);
                    }else if(board[j][i] == -6){
                        ChessPiece p = new Pawn(false, game, new Location(i, j));
                        map.put(new Location(i, j), p);
                        arr.add(p);
                    }
                }
            }
        }
        return arr;
    }
}
