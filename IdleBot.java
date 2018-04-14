import multipaint.Action;
import multipaint.Board;
import multipaint.Runner;

import java.util.Random;

public class IdleBot implements multipaint.Bot {
    private String playerId;
    public Random r;

    public static String[] ActionTypes = new String[]{"shoot", "walk"};
    public static int[][] ActionDirections = new int[][]{
            {-1, -1}, { 0, -1}, { 1, -1},
            {-1,  0},           { 1,  0},
            { 1,  1}, { 0,  1}, { 1,  1},
    };

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
        this.r = new Random();
    }

    public Action nextMove(Board state) {
        Action a = new Action();

        a.type = this.ActionTypes[0];
        a.direction = this.ActionDirections[0];

        return a;
    }

    public static void main(String[] args) {
        Runner.run(new IdleBot());
    }
}
