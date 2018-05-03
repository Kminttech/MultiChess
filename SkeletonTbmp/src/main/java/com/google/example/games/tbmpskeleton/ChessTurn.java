package com.google.example.games.tbmpskeleton;

import android.util.Log;

import com.google.example.games.tbmpskeleton.grid.ChessBoard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class ChessTurn {
    public static final String TAG = "EBTurn";
    public int[][] data;
    public boolean whiteTurn;

    public ChessTurn(){
    }

    public ChessTurn(ChessBoard curBoard, boolean whiteTurn) {
        data = curBoard.getIntArray();
        this.whiteTurn = whiteTurn;
    }

    // This is the byte array we will write out to the TBMP API.
    public byte[] persist() {
        JSONArray retVal = null;

        try {
            //retVal.put("data", new JSONArray(data);
            retVal = new JSONArray(data);
            retVal.put(whiteTurn);

        } catch (JSONException e) {
            Log.e("ChessTurn", "There was an issue writing JSON!", e);
        }

        String st = retVal.toString();

        Log.d(TAG, "==== PERSISTING\n" + st);

        return st.getBytes(Charset.forName("UTF-8"));
    }

    // Creates a new instance of ChessTurn.
    static public ChessTurn unpersist(byte[] byteArray) {

        if (byteArray == null) {
            Log.d(TAG, "Empty array---possible bug.");
            return null;
        }

        String st = null;
        try {
            st = new String(byteArray, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            return null;
        }

        Log.d(TAG, "====UNPERSIST \n" + st);

        ChessTurn retVal = new ChessTurn();
        retVal.data = new int[8][8];

        try {
            JSONArray obj = new JSONArray(st);

            if (obj.get(0) != null) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        retVal.data[i][j] = obj.getJSONArray(i).getInt(j);
                    }
                }
            }
            retVal.whiteTurn = obj.getBoolean(8);
        } catch (JSONException e) {
            Log.e("ChessTurn", "There was an issue parsing JSON!", e);
        }

        return retVal;
    }
}
