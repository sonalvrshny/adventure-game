package dungeon.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * Panel displaying the instructions to play the game.
 */
final class InstructionsPanel extends JPanel {

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    Font labelFont = new Font("Comic Sans", Font.BOLD, 13);
    g2d.setFont(labelFont);

    g2d.drawString("Welcome to the dungeon! Try reaching the end cave without "
            + "getting eaten to win!", 25, 30);
    g2d.drawString("How to play: ", 25, 60);
    g2d.drawString("1. Press the arrow keys to move the player. "
            + "Invalid tries will not move the player", 30, 90);
    g2d.drawString("2. Alternatively, click on adjacent cells to move the player."
            + " Invalid tries will not move the player", 30, 120);
    g2d.drawString("3. Press p to start picking up an item. "
            + "Press a (arrow) or t (treasure) to pick item", 30, 150);
    g2d.drawString("4. Press s to shoot followed by direction to shoot in. "
            + "The next prompt will ask for distance to shoot", 30, 180);
    g2d.drawString("5. Arriving in a cave with a healthy Otyugh means that you lost", 30, 210);
    g2d.drawString("6. Every 3 invalid moves makes a thief appear "
            + "to steal your treasure", 30, 240);
    g2d.drawString("7. Click on menu for settings and restarting the game", 30, 270);
  }
}
