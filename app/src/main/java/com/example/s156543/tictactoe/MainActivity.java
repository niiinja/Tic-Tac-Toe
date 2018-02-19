// Tic Tac Toe game on which two human players can play against eachother
// Unit 2 Minor Programmeren Nina Boelsums 10742670

package com.example.s156543.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class MainActivity extends AppCompatActivity {
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game();
    }

    @Override
    // Save Class game in an stream as an array of bytes
    // To do this I looked at code found at the following link:
    // https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bs);
            oos.writeObject(game);
            outState.putByteArray("game", bs.toByteArray());
            bs.close();
        } catch (IOException e) {
            return;
        }

    }

    // Turn the byte array stream into game class again
    @Override
    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        byte[] ba = inState.getByteArray("game");
        ByteArrayInputStream is = new ByteArrayInputStream(ba);
        try {
            ObjectInputStream ois = new ObjectInputStream(is);
            game = (Game) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return;
        }

        restoreUI();
    }

    // Restores crosses and circles in view to the corresponding coordinates in game
    private void restoreUI() {
        for (int i = 0; i < game.getBoardSize(); i++) {
            for (int j = 0; j < game.getBoardSize(); j++) {
                setTile(findViewById(coordsToId(i, j)), game.getTile(i, j));
            }
        }
        View win = findViewById(R.id.win);
        if (game.getGameOver())
            win.setVisibility(View.VISIBLE);
    }

    // Returns id of Tile corresponding to coordinates
    private int coordsToId(int x, int y) {
        switch (x) {
            case 0:
                switch (y) {
                    case 0:
                        return R.id.Tile1;
                    case 1:
                        return R.id.Tile2;
                    case 2:
                        return R.id.Tile3;
                }
                break;
            case 1:
                switch (y) {
                    case 0:
                        return R.id.Tile4;
                    case 1:
                        return R.id.Tile5;
                    case 2:
                        return R.id.Tile6;
                }
                break;
            case 2:
                switch (y) {
                    case 0:
                        return R.id.Tile7;
                    case 1:
                        return R.id.Tile8;
                    case 2:
                        return R.id.Tile9;
                }
                break;
        }
        return 0;
    }



    // Process tile clicks
    public void tileClicked(View view) {
        if (game.getGameOver())
            return;

        // Translate the button into the right coordinates.
        int id = view.getId();

        int row = 0;
        int column= 0;

        switch(id){
            case R.id.Tile1:
                row = 1;
                column  = 1;
                break;
            case R.id.Tile2:
                row = 1;
                column = 2;
                break;
            case R.id.Tile3:
                row = 1;
                column = 3;
                break;
            case R.id.Tile4:
                row = 2;
                column = 1;
                break;
            case R.id.Tile5:
                row = 2;
                column = 2;
                break;
            case R.id.Tile6:
                row = 2;
                column = 3;
                break;
            case R.id.Tile7:
                row = 3;
                column = 1;
                break;
            case R.id.Tile8:
                row = 3;
                column = 2;
                break;
            case R.id.Tile9:
                row = 3;
                column = 3;
                break;
            }

    // Feed those coordinates to the Games draw method:
        com.example.s156543.tictactoe.Tile tile;
        tile = game.draw(row - 1, column - 1);
        setTile(view, tile);
        if (game.getGameOver()){
            View win = findViewById(R.id.win);
            win.setVisibility(View.VISIBLE);
        }

    }

    // Set a visible cross or circle according to the tile's enum value
    public void setTile(View view, com.example.s156543.tictactoe.Tile tile) {
        String symbol = "";

        switch(tile) {
            case CROSS:
                symbol = "X";
                break;
            case CIRCLE:
                symbol = "O";
                break;
            case INVALID:
                return;
            case BLANK:
                symbol = "";
                break;
        }

        TextView t = (TextView) view;
        t.setText(symbol);
    }

    // Process click on 'reset', resets the game
    public void resetClicked(View view){
        game = new Game();

    // resets user interface
        restoreUI();
        View win = findViewById(R.id.win);
        win.setVisibility(View.INVISIBLE);
    }
}
