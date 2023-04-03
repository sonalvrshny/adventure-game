package dungeon.view;

import dungeon.controller.MvcController;

/**
 * The visual representation of the dungeon game is set here.
 * The game frame consists of a description panel, status panel,
 * dungeon panel and instructions. The model interacts with this view
 * through the controller based on mouse clicks and keyboard presses.
 */
public interface DungeonView {

  /**
   * Method for setting the listeners for the settings option on the frame.
   * The settings item displays the dungeon's current specifications.
   *
   * @param controller controller for connecting view to model
   */
  void setSettingListeners(MvcController controller);

  /**
   * Method for setting the listeners for the restart option on the frame.
   * Restart has two options - to start with the same specs or to update the
   * specs.
   *
   * @param controller controller for connecting view to model
   */
  void setRestartListeners(MvcController controller);

  /**
   * Method for setting the listeners for clicking on the grid to
   * move the player around. Any invalid move will prompt the user
   * about illegal move.
   *
   * @param controller controller for connecting view to model
   */
  void moveClickListener(MvcController controller);

  /**
   * Method for setting the listeners for keyboard presses to move the player,
   * pick up items or shoot arrow. Any invalid action will prompt the user
   * about illegal action.
   *
   * @param controller controller for connecting view to model
   */
  void setKeyListeners(MvcController controller);

  /**
   * Make the view visible. This is usually called after the view is constructed.
   */
  void makeVisible();

  /**
   * Signal the view to draw itself.
   */
  void refresh();

  /**
   * Displays the outcome of each action the player does.
   *
   * @param status string representing what player did and what should be next
   */
  void displayStatus(String status);


  /**
   * Disposes the current frame being use.
   */
  void removeFrame();

  /**
   * Used to display any error that is caught in the model and display
   * in a popup on the view.
   *
   * @param error message of the error
   */
  void showErrorMessage(String error);
}
