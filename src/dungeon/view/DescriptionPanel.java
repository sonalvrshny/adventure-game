package dungeon.view;

import dungeon.model.ReadOnlyDungeon;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Package private class to make it accessible within the package.
 * This panel is used to display the description of the game at
 * the current moment. This includes the outcome of every move the
 * player takes and what items the player has collected till now.
 */
final class DescriptionPanel extends JPanel {

  private final ReadOnlyDungeon dungeonModel;
  private BufferedImage arrow = null;
  private BufferedImage ruby = null;
  private BufferedImage sapphire = null;
  private BufferedImage diamond = null;

  /**
   * The panel consists of a labels with the outcome of each move
   * of the player. It also displays the item list of the player.
   *
   * @param dungeon read only version of the model
   */
  public DescriptionPanel(ReadOnlyDungeon dungeon) {
    super();
    // defining images of items present in dungeon
    try {
      arrow = ImageIO.read(getClass().getResource("/arrow-black.png"));
      ruby = ImageIO.read(getClass().getResource("/ruby.png"));
      sapphire = ImageIO.read(getClass().getResource("/emerald.png"));
      diamond = ImageIO.read(getClass().getResource("/diamond.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (dungeon == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.dungeonModel = dungeon;
    setLayout(new GridBagLayout());

    // setting the labels for description of each move
    JLabel locationDesc = new JLabel();
    JLabel smellDesc = new JLabel();
    locationDesc.setPreferredSize(new Dimension(100, 50));
    smellDesc.setPreferredSize(new Dimension(100, 50));

    // placement of labels on the panel
    GridBagConstraints c = new GridBagConstraints();
    locationDesc.setPreferredSize(new Dimension(500, 5));
    c.gridx = 0;
    c.gridy = 0;
    add(locationDesc, c);

    smellDesc.setPreferredSize(new Dimension(500, 5));
    c.gridx = 0;
    c.gridy = 1;
    add(smellDesc, c);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    Font labelFont = new Font("Comic Sans", Font.BOLD, 14);
    g2d.setFont(labelFont);

    // description of player's current location
    String[] description = dungeonModel.displayLocationDescription().split("\n");
    g2d.drawString(description[0], 30, 150);
    if (description.length > 1) {
      g2d.drawString(description[1], 30, 175);
    }
    if (!dungeonModel.gameOver()) {
      g2d.drawString(dungeonModel.isMonsterClose(), 30, 200);
    }

    // description of what the player has collected till now
    g2d.drawString("Player details:", 60, 50);
    g2d.drawImage(arrow, 60, 70, 50, 10, null);
    g2d.drawString("x" + dungeonModel.playerArrows(), 117, 80);
    g2d.drawImage(ruby, 200, 65, 20, 20, null);
    g2d.drawString("x" + dungeonModel.playerRuby(), 225, 80);
    g2d.drawImage(sapphire, 300, 65, 20, 20, null);
    g2d.drawString("x" + dungeonModel.playerSapphire(), 325, 80);
    g2d.drawImage(diamond, 400, 65, 20, 20, null);
    g2d.drawString("x" + dungeonModel.playerDiamond(), 425, 80);
  }
}
