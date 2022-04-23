package Views;

import Models.Tiles.TileGrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileGridPrinter {
    public static String print(TileGrid tileGrid) {
        List<List<Character>> screen = new ArrayList<>();
        for (int i = 0; i < 50; ++i) {
            screen.add(new ArrayList<>(50));
            for (int j = 0; j < 50; ++j) {
                screen.get(i).add('.');
            }
        }



        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 50; ++i) {
            for (int j = 0; j < 50; ++j) {
                output.append(screen.get(i).get(j));
            }
            output.append('\n');
        }
        return output.toString();
    }
}
