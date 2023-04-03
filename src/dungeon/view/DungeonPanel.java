package dungeon.view;

import dungeon.model.Directions;
import dungeon.model.ReadOnlyDungeon;
import dungeon.model.Treasure;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Package private class to make it accessible within the package.
 * This panel is used to display the grid of the dungeon with the current
 * state of the game. This includes the outcome of every move the
 * player takes and what is present in the locations the player has visited.
 */
final class DungeonPanel extends JPanel {
  private final ReadOnlyDungeon dungeonModel;
  private BufferedImage black;
  private BufferedImage location;
  private BufferedImage ruby;
  private BufferedImage sapphire;
  private BufferedImage diamond;
  private BufferedImage arrow;
  private BufferedImage monster;
  private BufferedImage thief;
  private BufferedImage lessSmell;
  private BufferedImage moreSmell;
  private BufferedImage player;

  /**
   * The panel consists of images representing locations
   * and items of the dungeon.
   *
   * @param d read only version of the model
   */
  public DungeonPanel(ReadOnlyDungeon d) {
    if (d == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.dungeonModel = d;
    try {
      black = ImageIO.read(getClass().getResource("/black.png"));
      ruby = ImageIO.read(getClass().getResource("/ruby.png"));
      sapphire = ImageIO.read(getClass().getResource("/emerald.png"));
      diamond = ImageIO.read(getClass().getResource("/diamond.png"));
      arrow = ImageIO.read(getClass().getResource("/arrow-white.png"));
      monster = ImageIO.read(getClass().getResource("/otyugh.png"));
      thief = ImageIO.read( getClass().getResource("/thief.png"));
      lessSmell = ImageIO.read(getClass().getResource("/stench01.png"));
      moreSmell = ImageIO.read(getClass().getResource("/stench02.png"));
      player = ImageIO.read(getClass().getResource("/player.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // method to grab paths of the required images
  private void setPathImage(int c) throws IOException {
    if (dungeonModel.locationMoves(c).size() == 1) {
      if (dungeonModel.locationMoves(c).contains(Directions.NORTH)) {
        location = ImageIO.read(getClass().getResource("/N.png"));
      }
      if (dungeonModel.locationMoves(c).contains(Directions.SOUTH)) {
        location = ImageIO.read(getClass().getResource("/S.png"));
      }
      if (dungeonModel.locationMoves(c).contains(Directions.EAST)) {
        location = ImageIO.read(getClass().getResource("/E.png"));
      }
      if (dungeonModel.locationMoves(c).contains(Directions.WEST)) {
        location = ImageIO.read(getClass().getResource("/W.png"));
      }
    } else if (dungeonModel.locationMoves(c).size() == 2) {
      if (dungeonModel.locationMoves(c).contains(Directions.NORTH)
              && dungeonModel.locationMoves(c).contains(Directions.EAST)) {
        location = ImageIO.read(getClass().getResource("/NE.png"));
      }
      if (dungeonModel.locationMoves(c).contains(Directions.NORTH)
              && dungeonModel.locationMoves(c).contains(Directions.WEST)) {
        location = ImageIO.read(getClass().getResource("/WN.png"));
      }
      if (dungeonModel.locationMoves(c).contains(Directions.NORTH)
              && dungeonModel.locationMoves(c).contains(Directions.SOUTH)) {
        location = ImageIO.read(getClass().getResource("/NS.png"));
      }
      if (dungeonModel.locationMoves(c).contains(Directions.EAST)
              && dungeonModel.locationMoves(c).contains(Directions.SOUTH)) {
        location = ImageIO.read(getClass().getResource("/ES.png"));
      }
      if (dungeonModel.locationMoves(c).contains(Directions.EAST)
              && dungeonModel.locationMoves(c).contains(Directions.WEST)) {
        location = ImageIO.read(getClass().getResource("/EW.png"));
      }
      if (dungeonModel.locationMoves(c).contains(Directions.SOUTH)
              && dungeonModel.locationMoves(c).contains(Directions.WEST)) {
        location = ImageIO.read(getClass().getResource("/SW.png"));
      }
    } else if (dungeonModel.locationMoves(c).size() == 3) {
      if (dungeonModel.locationMoves(c).contains(Directions.EAST)
              && dungeonModel.locationMoves(c).contains(Directions.SOUTH)
              && dungeonModel.locationMoves(c).contains(Directions.WEST)) {
        location = ImageIO.read(getClass().getResource("/ESW.png"));
      }
      if (dungeonModel.locationMoves(c).contains(Directions.NORTH)
              && dungeonModel.locationMoves(c).contains(Directions.EAST)
              && dungeonModel.locationMoves(c).contains(Directions.SOUTH)) {
        location = ImageIO.read(getClass().getResource("/NES.png"));
      }
      if (dungeonModel.locationMoves(c).contains(Directions.NORTH)
              && dungeonModel.locationMoves(c).contains(Directions.EAST)
              && dungeonModel.locationMoves(c).contains(Directions.WEST)) {
        location = ImageIO.read(getClass().getResource("/NEW.png"));
      }
      if (dungeonModel.locationMoves(c).contains(Directions.SOUTH)
              && dungeonModel.locationMoves(c).contains(Directions.WEST)
              && dungeonModel.locationMoves(c).contains(Directions.NORTH)) {
        location = ImageIO.read(getClass().getResource("/SWN.png"));
      }
    } else if (dungeonModel.locationMoves(c).size() == 4) {
      location = ImageIO.read(getClass().getResource("/NESW.png"));
    }
  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (int i = 0; i < dungeonModel.getSettings().get(1); i++) {
      for (int j = 0; j < dungeonModel.getSettings().get(0); j++) {
        try {
          BufferedImage cellLocation;

          // unvisited locations of dungeon
          if (dungeonModel.gameState()[j][i] == 0) {
            cellLocation = black;
            g2d.drawImage(cellLocation, (i * 100) + 50, (j * 100) + 50, 100, 100, null);
          }
          // visited locations of dungeon
          else {
            int current = j * dungeonModel.getSettings().get(1) + i;
            setPathImage(current);
            cellLocation = location;
            g2d.drawImage(cellLocation, (i * 100) + 50, (j * 100) + 50, 100, 100, null);
            // if location contains ruby
            if (dungeonModel.locationTreasure(current).contains(Treasure.RUBY)) {
              g2d.drawImage(ruby, (i * 100) + 70, (j * 100) + 60, 20, 20, null);
            }
            // if location contains sapphire
            if (dungeonModel.locationTreasure(current).contains(Treasure.SAPPHIRE)) {
              g2d.drawImage(sapphire, (i * 100) + 95, (j * 100) + 60, 20, 20, null);
            }
            // if location contains diamond
            if (dungeonModel.locationTreasure(current).contains(Treasure.DIAMOND)) {
              g2d.drawImage(diamond, (i * 100) + 120, (j * 100) + 60, 20, 20, null);
            }
            // if location contains arrow
            if (dungeonModel.locationArrows(current) > 0) {
              g2d.drawImage(arrow, (i * 100) + 80, (j * 100) + 140, 50, 5, null);
              g2d.setFont(new Font("Helvetica", Font.BOLD, 14));
              g2d.setColor(Color.WHITE);
              g2d.drawString(Integer.toString(dungeonModel.locationArrows(current)),
                      (i * 100) + 80, (j * 100) + 130);
            }
            // if location contains Otyugh
            if (dungeonModel.locationMonster(current) != null) {
              g2d.drawImage(monster, (i * 100) + 60, (j * 100) + 80, 40, 40, null);
            }
            // if location contains thief
            if (dungeonModel.locationThief(current)) {
              g2d.drawImage(thief, (i * 100) + 110, (j * 100) + 80, 35, 35, null);
            }
          }

          g2d.drawImage(player, (dungeonModel.getCurrentLocationY() * 100) + 80,
                  (dungeonModel.getCurrentLocationX() * 100) + 80, 35, 35, null);
          // if player location contains low smell
          if (dungeonModel.isMonsterClose().contains("bad")) {
            g2d.drawImage(lessSmell, (dungeonModel.getCurrentLocationY() * 100) + 85,
                    (dungeonModel.getCurrentLocationX() * 100) + 85, 30, 30, null);
          }
          // if player location contains high smell
          if (dungeonModel.isMonsterClose().contains("worse")) {
            g2d.drawImage(moreSmell, (dungeonModel.getCurrentLocationY() * 100) + 85,
                    (dungeonModel.getCurrentLocationX() * 100) + 85, 30, 30, null);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
