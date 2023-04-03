package dungeon.controller;

import dungeon.model.Dungeon;
import dungeon.view.DungeonView;

/**
 * To play the game, there are 3 possible actions a user can do - move, pick or shoot.
 * User can quit/restart the game as well through different actions.
 */
public interface CommandController {

  /**
   * Execute a command of dungeon game given a dungeon Model. The command
   * is typed into the console. This command would execute this method
   * and append to the output the state of the dungeon.
   *
   * @param dungeon a non-null dungeon Model
   * @param out logging string output
   * @throws IllegalArgumentException if model or appendable is null
   */
  void commandGo(Dungeon dungeon, Appendable out);

  /**
   * Execute a command of dungeon game given a dungeon Model. Each command is defined
   * by mouse clicks/keyboard inputs. This command would execute this method
   * and the view renders to it and displays the outcome.
   *
   * @param dungeon a non-null dungeon Model
   * @param view display for the outcome of action
   * @throws IllegalArgumentException if model or view is null
   */
  void mvcGo(Dungeon dungeon, DungeonView view);
}

