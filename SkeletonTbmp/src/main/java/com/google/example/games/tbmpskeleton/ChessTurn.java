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

    public ChessTurn(ChessBoard curBoard) {
        data = curBoard.getIntArray();
    }

    // This is the byte array we will write out to the TBMP API.
    public byte[] persist() {
        JSONArray retVal = null;

        try {
            //retVal.put("data", new JSONArray(data);
            retVal = new JSONArray(data);

        } catch (JSONException e) {
            Log.e("ChessTurn", "There was an issue writing JSON!", e);
        }

        String st = retVal.toString();

        Log.d(TAG, "==== PERSISTING\n" + st);

        return st.getBytes(Charset.forName("UTF-8"));
    }

    // Creates a new instance of ChessTurn.
    static public int[][] unpersist(byte[] byteArray) {

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

        int[][] retVal = new int[8][8];

        try {
            JSONArray obj = new JSONArray(st);

            /*if (obj.has("data")) {
                retVal = (int[][]) obj.get("data");
            }*/
            if (obj.get(0) != null) {
                //int test[] = (int []) obj.getInt(0);
                //int test2[] = obj.getInt(1);
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        retVal[i][j] = obj.getJSONArray(i).getInt(j);
                        int fgaewsr = 0;
                    }
                }
                //retVal = (int[][]) obj.get(0);
            }
        } catch (JSONException e) {
            Log.e("ChessTurn", "There was an issue parsing JSON!", e);
        }

        return retVal;
    }
}
