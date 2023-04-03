package dungeon;

import dungeon.controller.DungeonConsoleController;
import dungeon.controller.DungeonController;
import dungeon.controller.MvcCommandController;
import dungeon.controller.MvcController;
import dungeon.model.Dungeon;
import dungeon.model.DungeonClass;
import dungeon.model.RandomClass;
import dungeon.model.RandomGenerator;
import dungeon.model.RandomSeedClass;
import dungeon.view.DungeonFrameView;
import dungeon.view.DungeonView;

import java.io.InputStreamReader;

/**
 * Run an interactive adventure game using GUI or a text based adventure
 * game on the console. Text based game is played by providing inputs
 * in the console. The GUI can be played with mouse clicks and keyboard
 * presses according to the instructions provided on the view which is launched
 * when the program is run.
 *
 */
public class Main {

  /**
   * The program can be run without using CLI arguments to launch the
   * GUI. If CLI is provided, the game will go into the text based
   * console mode.
   *
   * @param args none for GUI or list of specs for text based game
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      Dungeon model = new DungeonClass(6, 6, 4, 65, false, 2, new RandomSeedClass(0));
      DungeonView view = new DungeonFrameView(model);
      MvcController controller = new MvcCommandController();
      controller.startDungeon(model, view);
    }
    else {
      int r;
      int c;
      int i;
      int p;
      int m;
      boolean b = false;

      try {
        r = Integer.parseInt(args[0]);
      } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
        System.out.println("Not a number! Enter valid row size");
        return;
      }
      try {
        c = Integer.parseInt(args[1]);
      } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
        System.out.println("Not a number! Enter valid column size");
        return;
      }
      try {
        i = Integer.parseInt(args[2]);
      } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
        System.out.println("Not a number! Enter valid interconnectivity");
        return;
      }
      try {
        p = Integer.parseInt(args[3]);
      } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
        System.out.println("Not a number! Enter valid percentage");
        return;
      }
      try {
        m = Integer.parseInt(args[4]);
      } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
        System.out.println("Not a number! Enter valid difficulty");
        return;
      }
      String w = args[5];
      if (w.equalsIgnoreCase("y") || w.equalsIgnoreCase("Yes")) {
        b = true;
      } else if (w.equalsIgnoreCase("n") || w.equalsIgnoreCase("No")) {
        b = false;
      }

      RandomGenerator rand = new RandomClass();

      Readable input = new InputStreamReader(System.in);
      Appendable output = System.out;
      DungeonController textController = new DungeonConsoleController(input, output);
      Dungeon dungeon = new DungeonClass(r, c, i, p, b, m, rand);
      textController.startDungeon(dungeon);
    }
  }
}
