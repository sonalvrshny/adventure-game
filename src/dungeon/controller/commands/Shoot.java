package dungeon.controller.commands;

import dungeon.controller.CommandController;
import dungeon.model.Dungeon;
import dungeon.view.DungeonView;

import java.io.IOException;


/**
 * The shoot command is used to shoot arrow from the player's current location in the dungeon.
 * Once a user types in the shoot command, the next prompt would ask in which direction they
 * want to shoot the arrow and for what distance, If an invalid value is entered, user should
 * re-enter the next command.
 */
public final class Shoot implements CommandController {
  private final String direction;
  private final String distance;

  /**
   * Shoot an arrow in the direction and distance specified by user.
   *
   * @param direction towards where the arrow is shot
   * @param distance how many caves the arrow should travel
   */
  public Shoot(String direction, String distance) {
    if (direction == null || distance == null) {
      throw new IllegalArgumentException("Items cannot be null");
    }
    this.direction = direction;
    this.distance = distance;
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
      int disNum;
      try {
        disNum = Integer.parseInt(distance);
      } catch (NumberFormatException e) {
        out.append("Only numeric values should be entered for distance").append("\n");
        return;
      }
      try {
        out.append(dungeon.shootArrow(direction, disNum)).append("\n");
      } catch (IllegalArgumentException | IllegalStateException e) {
        out.append(e.getMessage()).append("\n");
      }
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
    int disNum;
    try {
      disNum = Integer.parseInt(distance);
    } catch (NumberFormatException e) {
      view.displayStatus("Only numeric values should be entered for distance");
      return;
    }
    try {
      view.displayStatus(dungeon.shootArrow(direction, disNum));
    } catch (IllegalStateException | IllegalArgumentException ise) {
      view.displayStatus(ise.getMessage());
    }
  }
}
