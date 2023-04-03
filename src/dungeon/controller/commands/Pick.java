package dungeon.controller.commands;

import dungeon.controller.CommandController;
import dungeon.model.Dungeon;
import dungeon.view.DungeonView;

import java.io.IOException;


/**
 * The pick command is used to pick either treasure or arrow from the player's current
 * location in the dungeon. Once a user types in the pick command, the next prompt would
 * ask what the player wants to pick up. If an invalid value is entered, user should
 * re-enter the next command.
 */
public final class Pick implements CommandController {
  private final String item;

  /**
   * Pick the item the player wants from the present location.
   *
   * @param item what the player wants to pick up
   */
  public Pick(String item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null");
    }
    this.item = item;
  }

  @Override
  public void commandGo(Dungeon dungeon, Appendable out) {
    if (dungeon == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (out == null) {
      throw new IllegalArgumentException("Log cannot be null");
    }
    try {
      if (item.equalsIgnoreCase("treasure") || item.equalsIgnoreCase("t")) {
        try {
          dungeon.pickUpTreasure();
        } catch (IllegalStateException e) {
          out.append(e.getMessage()).append("\n");
          return;
        }
      } else if (item.equalsIgnoreCase("arrow") || item.equalsIgnoreCase("a")) {
        try {
          dungeon.pickUpArrow();
        } catch (IllegalStateException e) {
          out.append(e.getMessage()).append("\n");
          return;
        }
      } else {
        out.append(item).append(" is not an existing item in the dungeon!").append("\n");
        return;
      }
      out.append(dungeon.displayPlayerDescription()).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Cannot append!");
    }
  }

  @Override
  public void mvcGo(Dungeon dungeon, DungeonView view) {
    if (dungeon == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    if (item.equals("t")) {
      try {
        dungeon.pickUpTreasure();
        view.displayStatus("You picked up treasure");
      } catch (IllegalStateException ise) {
        view.displayStatus(ise.getMessage());
      }
    }
    else if (item.equals("a")) {
      try {
        dungeon.pickUpArrow();
        view.displayStatus("You picked up 1 arrow");
      } catch (IllegalStateException ise) {
        view.displayStatus(ise.getMessage());
      }
    }
  }
}