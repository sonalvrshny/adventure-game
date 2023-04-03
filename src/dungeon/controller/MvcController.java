package dungeon.controller;

import dungeon.model.Dungeon;
import dungeon.view.DungeonView;

/**
 * Represents a Controller for Dungeon: handle user inputs through mouse clicks
 * and keyboard presses on the view by executing them using the model;
 * convey move outcomes to the user through visual representation indicating what state the player
 * is in inside the dungeon.
 */
public interface MvcController {

  /**
   * Execute a single game of dungeon game with a GUI given a dungeon Model.
   * When the game is over or player quits, the startDungeon method ends.
   * @param model the model of the dungeon game
   * @param view visual representation of the dungeon game
   * @throws IllegalArgumentException if model or view is null
   */
  void startDungeon(Dungeon model, DungeonView view);

  /**
   * Handles the clicks on the dungeon grid to move thr player.
   *
   * @param x direction to move horizontally
   * @param y direction to move vertically
   */
  void clickListener(int x, int y);

  /**
   * Handles the actions performed on the view and
   * tells the model what to do. Once the model executes the
   * function, the view updates with the present state of the model.
   *
   * @param command command string to identify the action performed on the view
   * @param s next set of strings to be processed by the controller
   * @throws IllegalArgumentException if command passed is null
   */
  void processCommand(String command, String...s);
}
