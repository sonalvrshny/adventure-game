package dungeon.controller;

import dungeon.controller.commands.Move;
import dungeon.controller.commands.Pick;
import dungeon.controller.commands.Shoot;
import dungeon.model.Dungeon;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Starting point of the control for the dungeon game. Here, the inputs taken from
 * user are passed into the model for respective functionalities.
 */
public class DungeonConsoleController implements DungeonController {

  private final Appendable out;
  private final Scanner scan;

  /**
   * The controller is responsible for taking input and sending it
   * to the model for its respective functions. The resulting output
   * is appended to the output log.
   *
   * @param in  input taken from user to pass to model
   * @param out output messages to be displayed to user
   */
  public DungeonConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  private void appendLog(String s) {
    try {
      out.append(s);
    } catch (IOException e) {
      throw new IllegalStateException("Cannot append!");
    }
  }


  @Override
  public void startDungeon(Dungeon dungeon) {
    if (dungeon == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    Map<String, Function<Scanner, CommandController>> knownCommands = new HashMap<>();


    knownCommands.put("MOVE", s -> {
      appendLog("Where?\n");
      return new Move(s.next());
    });
    knownCommands.put("PICK", s -> {
      appendLog("What? Treasure (t) or Arrow (a)\n");
      return new Pick(s.next());
    });
    knownCommands.put("SHOOT", s -> {
      appendLog("Where?\n");
      String dir = s.next();
      appendLog("How many caves? (1-5)\n");
      String dis = s.next();
      return new Shoot(dir, dis);
    });


    try {
      appendLog("----------You have entered the dungeon----------\n");
      appendLog(dungeon.displayLocationDescription() + "\n");
      if (!dungeon.isMonsterClose().equals("")) {
        appendLog(dungeon.isMonsterClose() + "\n");
      }
      while (!dungeon.gameOver()) {
        appendLog("\n");
        appendLog("What do you want to do next?: move, pick or shoot? (q to quit)\n");
        CommandController control;
        String in = scan.next();
        if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
          appendLog("Come back soon!\n");
          return;
        }
        if (!in.equalsIgnoreCase("move")
                && !in.equalsIgnoreCase("shoot")
                && !in.equalsIgnoreCase("pick")) {
          appendLog(in + " is not a valid command!\n");
          continue;
        }

        Function<Scanner, CommandController> cmd =
                knownCommands.getOrDefault(in.toUpperCase(Locale.ROOT), null);
        if (cmd == null) {
          throw new IllegalArgumentException();
        } else {
          control = cmd.apply(scan);
          control.commandGo(dungeon, out);
        }
      }
    } catch (NoSuchElementException e) {
      e.getMessage();
    }

  }
}
