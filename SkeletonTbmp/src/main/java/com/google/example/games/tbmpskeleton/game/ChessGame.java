package com.google.example.games.tbmpskeleton.game;

import com.google.example.games.tbmpskeleton.grid.ChessBoard;
import com.google.example.games.tbmpskeleton.grid.Location;
import com.google.example.games.tbmpskeleton.move.IllegalMoveException;
import com.google.example.games.tbmpskeleton.move.InCheckException;
import com.google.example.games.tbmpskeleton.move.InvalidLocationException;
import com.google.example.games.tbmpskeleton.move.Move;
import com.google.example.games.tbmpskeleton.pieces.Bishop;
import com.google.example.games.tbmpskeleton.pieces.ChessPiece;
import com.google.example.games.tbmpskeleton.pieces.King;
import com.google.example.games.tbmpskeleton.pieces.Knight;
import com.google.example.games.tbmpskeleton.pieces.Pawn;
import com.google.example.games.tbmpskeleton.pieces.Queen;
import com.google.example.games.tbmpskeleton.pieces.Rook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Manages the user interface and the back-end.
 * @author Kevin Minter
 */
public class ChessGame {
    // Pieces and grid information
    private ArrayList<ChessPiece> chessPieces;
    private ChessPiece captured;
    private ChessBoard grid;
    private HashSet<Location> white_locs, black_locs;

    // Move and turn information
    private boolean whiteTurn;
    private boolean playerWhite;

    // Castling information
    private King K, k;

    // Moves when in check
    private HashSet<Move> escape_moves;

    /**
     * Creates a new game on the given board.
     *
     * @param grid The board on which the game will be played.
     */
    public ChessGame(ChessBoard grid) {
        super();
        this.grid = grid;
        this.black_locs = new HashSet(16);
        this.white_locs = new HashSet(16);
        this.whiteTurn = true;
        defaultPosition();
        updateControlledLocations();
        for (ChessPiece p : chessPieces) {
            p.update();
        }
    }

    /**
     * @return The grid on which this game is played.
     */
    public ChessBoard getGrid() {
        return grid;
    }

    /**
     * @return The set of all locations that are attacked by a black piece.
     * Locations occupied by a black piece but not defended by another piece
     * are not included in this set.
     */
    public HashSet<Location> getLocsControlledByBlack() {
        return black_locs;
    }

    /**
     * @return The set of all locations that are attacked by a white piece.
     * Locations occupied by a white piece but not defended by another piece
     * are not included in this set.
     */
    public HashSet<Location> getLocsControlledByWhite() {
        return white_locs;
    }

    public boolean isCheckmate() {
        return isInCheck() && getEscapeMoves().isEmpty();
    }

    /**
     * @return True if it's white's turn; false for black.
     */
    public boolean isWhitesTurn() {
        return whiteTurn;
    }

    public boolean isPlayerWhite() { return playerWhite; }

    /**
     * Moves the piece at the source location to the specified destination. <br>
     * Precondition: There exists a piece at location <code>src</code>.
     *
     * @param src  The starting location of the piece.
     * @param dest The desired location to move the piece.
     * @return True if the enemy king is put in check; false otherwise.
     * @throws InvalidLocationException If either location is not in range.
     * @throws IllegalMoveException     If the attempted move is not valid.
     */
    public boolean move(Location src, Location dest) throws InvalidLocationException, IllegalMoveException {
        if (!grid.isValid(src))
            throw new InvalidLocationException(src);
        if (!grid.isValid(dest))
            throw new InvalidLocationException(dest);

        ChessPiece p = grid.get(src);

        if (!p.canMove(dest))
            throw new IllegalMoveException(p, dest);
        Move move = new Move(src, dest);


        if (isInCheck() && !escape_moves.contains(move)) {
            throw new InCheckException(escape_moves);
        }

        captured = grid.get(dest);

        // Time to actually move the piece.
        ChessPiece target = grid.put(dest, p);
        if (target != null) {
            chessPieces.remove(target);
        }
        updateControlledLocations();

        // Cannot make moves that put your own king in check.
        if (isInCheck()) {
            grid.put(src, p);
            if (target != null) {
                grid.put(dest, target);
                chessPieces.add(target);
            } else
                grid.remove(dest);
            updateControlledLocations();
            throw new IllegalMoveException("Moving " + p + " will place the king in check.", p, dest);
        }

        if(captured != null){
            chessPieces.remove(captured);
        }

        if((p.isWhite() && p.getLocation().getRow() >= 7) || (!p.isWhite() && p.getLocation().getRow() <= 0)){
            //Promote Pawns
        }
        // Switch turns.
        whiteTurn = !whiteTurn;

        boolean inCheck = isInCheck();
        for (ChessPiece chessPiece : chessPieces)
            chessPiece.update();

        if (inCheck) {
            escape_moves = getEscapeMoves();
        } else {
            escape_moves = null;
            updateAllowedMoves();
        }

        return inCheck;
    }

    /**
     * @param grid Swaps out this game's shogi for the given board.
     */
    public void setGrid(ChessBoard grid) {
        this.grid = grid;
        chessPieces.clear();
        for (Map.Entry<Location, ChessPiece> e : grid.getEntrySet()) {
            e.getValue().setLocation(e.getKey());
            chessPieces.add(e.getValue());
        }
    }

    public boolean addPiece(ChessPiece p, Location loc){
        if(!chessPieces.contains(p)) {
            chessPieces.add(p);
            grid.put(loc, p);
            whiteTurn = !whiteTurn;
            return true;
        }
        return false;
    }

    /**
     * Sets up a brand new game of Shogi. Creates a new chessPieces and sets up the
     * chessPieces on the grid.
     */
    private void defaultPosition() {
        chessPieces = new ArrayList<ChessPiece>(32);
        // Kings
        K = new King(true, this, new Location(0, 4));
        k = new King(false, this, new Location(7, 4));
        chessPieces.add(k);
        chessPieces.add(K);

        Queen q1, q2;
        q1 = new Queen(true, this, new Location(0, 3));
        q2 = new Queen(false, this, new Location(0, 3));
        chessPieces.add(q1);
        chessPieces.add(q2);

        // Pawns
        for (int i = 0; i < 7; i++) {
            Pawn p1 = new Pawn(true, this, new Location(1, i));
            Pawn p2 = new Pawn(false, this, new Location(6, i));
            chessPieces.add(p1);
            chessPieces.add(p2);
        }

        // Knights
        Knight n1, n2, n3, n4;
        n1 = new Knight(true, this, new Location(0, 1));
        n2 = new Knight(true, this, new Location(0, 7));
        n3 = new Knight(false, this, new Location(7, 1));
        n4 = new Knight(false, this, new Location(7, 6));
        chessPieces.add(n1);
        chessPieces.add(n2);
        chessPieces.add(n3);
        chessPieces.add(n4);

        // Bishops
        Bishop b1, b2, b3, b4;
        b1 = new Bishop(true, this, new Location(0, 2));
        b2 = new Bishop(true, this, new Location(0, 5));
        b3 = new Bishop(false, this, new Location(7, 2));
        b4 = new Bishop(false, this, new Location(7, 5));
        chessPieces.add(b1);
        chessPieces.add(b2);
        chessPieces.add(b3);
        chessPieces.add(b4);

        // Rooks
        Rook r1, r2, r3, r4;
        r1 = new Rook(true, this, new Location(0, 0));
        r2 = new Rook(true, this, new Location(0, 7));
        r3 = new Rook(false, this, new Location(7, 0));
        r4 = new Rook(false, this, new Location(7, 7));
        chessPieces.add(r1);
        chessPieces.add(r2);
        chessPieces.add(r3);
        chessPieces.add(r4);

        for (ChessPiece p : chessPieces) {
            grid.put(p.getLocation(), p);
        }
    }

    /**
     * @return A set of valid moves that takes the king out of check.
     */
    private HashSet<Move> getEscapeMoves(){
        HashSet<Move> result = new HashSet<Move>();
        ArrayList<ChessPiece> all_Chess_pieces = new ArrayList<ChessPiece>(chessPieces.size());
        for (ChessPiece p : chessPieces)
            all_Chess_pieces.add(p);

        for (ChessPiece p : all_Chess_pieces){
            if (p.isWhite() != isWhitesTurn())
                continue;
            Location prev = p.getLocation();
            for(Location loc : p.getMoves()){
                ChessPiece captured = grid.put(loc, p);
                if (captured != null)
                    chessPieces.remove(captured);
                updateControlledLocations();
                if(!isInCheck())
                    result.add(new Move(prev, loc));
                grid.put(prev, p);
                if(captured != null){
                    grid.put(loc, captured);
                    chessPieces.add(captured);
                }else
                    grid.remove(loc);
                updateControlledLocations();
            }
        }
        return result;
    }

    /**
     * @return True if the current player moving is in check; false otherwise.
     */
    private boolean isInCheck() {
        return isWhitesTurn() ? getLocsControlledByBlack().contains(K.getLocation()) : getLocsControlledByWhite().contains(k.getLocation());
    }

    private void updateAllowedMoves() {
        HashSet<Location> enemy = isWhitesTurn() ? black_locs : white_locs;
        King our_king = isWhitesTurn() ? K : k;
        ArrayList<ChessPiece> all_Chess_pieces = new ArrayList<ChessPiece>(chessPieces.size());
        for (ChessPiece p : chessPieces){
            all_Chess_pieces.add(p);
        }

        for (ChessPiece p : all_Chess_pieces){
            if ((isWhitesTurn() != p.isWhite()) || p instanceof King)
                continue;

            Location prev = p.getLocation();
            Iterator<Location> i = p.getMoves().iterator();
            while (i.hasNext()) {
                Location loc = i.next();
                ChessPiece captured = grid.put(loc, p);
                if (captured != null)
                    chessPieces.remove(captured);
                updateControlledLocations();
                if (enemy.contains(our_king.getLocation())) {
                    i.remove();
                }
                grid.put(prev, p);
                if (captured != null) {
                    grid.put(loc, captured);
                    chessPieces.add(captured);
                } else
                    grid.remove(loc);
                updateControlledLocations();
            }
        }
    }

    private void updateControlledLocations(){
        white_locs.clear();
        black_locs.clear();
        for (ChessPiece p : chessPieces) {
            if (p.isWhite())
                white_locs.addAll(p.getAttackedLocations());
            else
                black_locs.addAll(p.getAttackedLocations());
        }
    }
}
