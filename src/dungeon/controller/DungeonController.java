package dungeon.controller;

import dungeon.model.Dungeon;

/**
 * Represents a Controller for Dungeon: handle user inputs by executing them using the model;
 * convey move outcomes to the user in the form of messages indicating what state the player
 * is in inside the dungeon.
 */
public interface DungeonController {

  /**
   * Execute a single game of text-based dungeon game given a dungeon Model.
   * When the game is over or player quits, the startDungeon method ends.
   *
   * @param dungeon a non-null dungeon Model
   * @throws IllegalArgumentException if dungeon is null
   */
  void startDungeon(Dungeon dungeon);
}
