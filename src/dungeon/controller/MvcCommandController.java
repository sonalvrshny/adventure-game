package dungeon.controller;

import dungeon.controller.commands.Move;
import dungeon.controller.commands.Pick;
import dungeon.controller.commands.Shoot;
import dungeon.model.Dungeon;
import dungeon.model.DungeonClass;
import dungeon.model.RandomSeedClass;
import dungeon.view.DungeonFrameView;
import dungeon.view.DungeonView;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The controller handles user inputs from the view by listening to
 * mouse clicks and keyboard inputs and passes the inputs to the model
 * to implement the functionalities. The dungeon game starts with a preset
 * dungeon.
 */
public final class MvcCommandController implements MvcController {
  private Dungeon model;
  private DungeonView view;
  private int rows;
  private int columns;
  private int interconnectivity;
  private int percentage;
  private int difficulty;
  private boolean wrapping;
  private final Map<String, Function<String, CommandController>> knownCommands;
  private int seed;


  /**
   * A dungeon is first created with default specifications which are used
   * when the game is launched. The actions taken by the player are then
   * controlled by listening to the inputs from the view.
   */
  public MvcCommandController() {
    seed = 0;
    knownCommands = new HashMap<>();
    rows = 6;
    columns = 6;
    interconnectivity = 4;
    percentage = 65;
    difficulty = 2;
    wrapping = false;
  }


  @Override
  public void startDungeon(Dungeon model, DungeonView view) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    this.model = model;
    this.view = view;
    view.setKeyListeners(this);
    view.setSettingListeners(this);
    view.setRestartListeners(this);
    view.moveClickListener(this);
    view.makeVisible();
  }

  private void processSettings(String r, String c, String i, String p, String d, String w) {
    if (r == null || c == null || i == null || p == null || d == null || w == null) {
      throw new IllegalArgumentException("Setting values cannot be null");
    }
    try {
      rows = Integer.parseInt(r);
      columns = Integer.parseInt(c);
      interconnectivity = Integer.parseInt(i);
      percentage = Integer.parseInt(p);
      difficulty = Integer.parseInt(d);
      if (w.equalsIgnoreCase("No")) {
        wrapping = false;
      }
      else if (w.equalsIgnoreCase("Yes")) {
        wrapping = true;
      }
      try {
        seed++;
        if (seed == 100) {
          seed = 0;
        }
        model = new DungeonClass(rows, columns, interconnectivity, percentage,
                wrapping, difficulty, new RandomSeedClass(seed));
        view.removeFrame();
        view = new DungeonFrameView(model);
        startDungeon(model, view);
      } catch (IllegalArgumentException | IllegalStateException e) {
        view.showErrorMessage(e.getMessage());
      }
    } catch (NumberFormatException e) {
      view.showErrorMessage("Please enter numbers in the text fields");
    }
  }

  @Override
  public void clickListener(int x, int y) {
    String direction = "";
    if (model.getCurrentLocationX() == y && model.getCurrentLocationY() - 1 == x) {
      direction = "w";
    }
    else if (model.getCurrentLocationX() == y && model.getCurrentLocationY() + 1 == x) {
      direction = "e";
    }
    else if (model.getCurrentLocationX() - 1 == y && model.getCurrentLocationY() == x) {
      direction = "n";
    }
    else if (model.getCurrentLocationX() + 1 == y && model.getCurrentLocationY() == x) {
      direction = "s";
    }
    processCommand("move", direction);
  }


  @Override
  public void processCommand(String command, String... s) {
    if (command == null) {
      throw new IllegalArgumentException("Command cannot be null");
    }
    if (s == null) {
      throw new IllegalArgumentException("Action cannot be null");
    }
    knownCommands.put("move", a -> new Move(s[0]));
    knownCommands.put("pick", a -> new Pick(s[0]));
    knownCommands.put("shoot", a -> new Shoot(s[0], s[1]));

    Function<String, CommandController> cmd =
            knownCommands.getOrDefault(command, null);
    CommandController control;


    if (command.equals("restart")) {
      if (s.length == 0) {
        view.removeFrame();
        try {
          model = new DungeonClass(rows, columns, interconnectivity, percentage,
                  wrapping, difficulty, new RandomSeedClass(seed));
          view = new DungeonFrameView(model);
          startDungeon(model, view);
        } catch (IllegalArgumentException | IllegalStateException e) {
          view.displayStatus(e.getMessage());
        }
      }
      else {
        processSettings(s[0], s[1], s[2], s[3], s[4], s[5]);
      }
    }
    if (model.gameOver()) {
      view.displayStatus("Game is over! Start a new game");
    }
    if (!model.gameOver() && cmd != null) {
      control = cmd.apply(command);
      control.mvcGo(model, view);
      view.refresh();
    }
  }
}
