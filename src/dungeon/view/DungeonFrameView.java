package dungeon.view;

import dungeon.controller.MvcController;
import dungeon.model.ReadOnlyDungeon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;


/**
 * This is the view of the dungeon game. When a game is launched this
 * frame is displayed. Here, the description, status, instructions and
 * dungeon are represented. There is a menu bar to access the settings,
 * for restarting and for quitting the game. The arrow keys and mouse clicks
 * are used to play the game.
 *
 */
public final class DungeonFrameView extends JFrame implements DungeonView {

  private final ReadOnlyDungeon dungeon;
  private final DungeonPanel dungeonPanel;
  private JTextField xField;
  private JTextField yField;
  private JTextField interconnectivity;
  private JTextField percentage;
  private JComboBox<String> wrapping;
  private JComboBox<String> difficulty;
  private final JButton okay;
  private final JLabel status;
  private final JMenuBar menuBar;
  private final JMenuItem sameRestart;
  private final JMenuItem newRestart;

  /**
   * The frame consists of all the panels for the dungeon game.
   * The frame displays description based on the dungeon's current
   * state via the controller.
   *
   * @param dungeon read only version of the model
   */
  public DungeonFrameView(ReadOnlyDungeon dungeon) {
    super();
    if (dungeon == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    // defining properties of the frame
    this.dungeon = dungeon;
    this.setBounds(50,50,1400,750);
    this.setSize(1400, 800);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setLayout(new GridLayout());
    this.requestFocus();

    // declaring components of frame
    okay = new JButton("Confirm");
    status = new JLabel();
    menuBar = new JMenuBar();
    sameRestart = new JMenuItem("Same game");
    newRestart = new JMenuItem("New game");

    // dungeon panel
    dungeonPanel = new DungeonPanel(dungeon);

    dungeonPanel.setPreferredSize(new Dimension(dungeon.getSettings().get(1) * 100 + 75,
            dungeon.getSettings().get(0) * 100 + 75));

    // container
    JPanel container = new JPanel();
    container.setPreferredSize(new Dimension(800, 800));

    // description panel
    DescriptionPanel descriptionPanel = new DescriptionPanel(dungeon);
    descriptionPanel.setPreferredSize(new Dimension(800, 250));

    // status panel
    JPanel statusPanel = new JPanel();
    statusPanel.setPreferredSize(new Dimension(800, 75));
    statusPanel.add(status, BorderLayout.WEST);

    // instruction panel
    InstructionsPanel instructionsPanel = new InstructionsPanel();
    instructionsPanel.setPreferredSize(new Dimension(800, 500));

    container.add(descriptionPanel);
    container.add(statusPanel);
    container.add(instructionsPanel);

    // scroll label
    JScrollPane scrollDungeon = new JScrollPane(dungeonPanel);
    JScrollPane scrollDungeon1 = new JScrollPane(container);
    add(scrollDungeon);
    add(scrollDungeon1);

    // menu
    createMenu();
    this.setLocationRelativeTo(dungeonPanel);
  }

  private void createMenu() {
    // defining fields for settings of the dungeon
    xField = new JTextField(dungeon.getSettings().get(0).toString(), 8);
    yField = new JTextField(dungeon.getSettings().get(1).toString(), 5);
    interconnectivity = new JTextField(dungeon.getSettings().get(2).toString(), 5);
    percentage = new JTextField(dungeon.getSettings().get(3).toString(), 5);
    String[] s = {"Yes", "No"};
    wrapping = new JComboBox<>(s);
    String[] s2 = {"1", "2", "3"};
    difficulty = new JComboBox<>(s2);

    // preferences of menu
    okay.setSize(new Dimension(30, 30));
    Font menuFont = new Font("Times New Roman", Font.PLAIN, 24);
    JMenu menu = new JMenu("Menu");
    menu.setFont(menuFont);

    // displaying dungeon specs
    JMenuItem menuItemSettings = new JMenuItem("Settings");
    menuItemSettings.setFont(menuFont);
    menu.add(menuItemSettings);
    menu.addSeparator();

    menuItemSettings.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        JPanel myPanel = new JPanel(new GridLayout(7, 2, 10, 25));
        Font f = new Font("Arial", Font.PLAIN, 20);
        myPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        myPanel.add(new JLabel("Number of rows:    " + dungeon.getSettings().get(0))).setFont(f);
        myPanel.add(new JLabel("Number of columns: " + dungeon.getSettings().get(1))).setFont(f);
        myPanel.add(new JLabel("Interconnectivity: " + dungeon.getSettings().get(2))).setFont(f);
        myPanel.add(new JLabel("Percentage:        " + dungeon.getSettings().get(3))).setFont(f);
        String w = dungeon.getSettings().get(4) == 1 ? "No" : "Yes";
        myPanel.add(new JLabel("Wrapping:          " + w)).setFont(f);
        myPanel.add(new JLabel("Difficulty:        " + dungeon.getSettings().get(5))).setFont(f);

        UIManager.put("OptionPane.minimumSize", new Dimension(750, 500));
        JOptionPane.showMessageDialog(null, myPanel);
      }
    });

    // restart game options
    JMenu menuItemRestart = new JMenu("Restart");
    menuItemRestart.setFont(menuFont);
    menuItemRestart.add(sameRestart).setFont(menuFont);
    menuItemRestart.add(newRestart).setFont(menuFont);

    menu.add(menuItemRestart);

    // restarting game with new specifications
    newRestart.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        JPanel myPanel = new JPanel(new GridLayout(7, 2, 10, 25));
        Font f = new Font("Arial", Font.PLAIN, 20);
        myPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        myPanel.add(new JLabel("Enter rows (>6):")).setFont(f);
        myPanel.add(xField).setFont(f);
        myPanel.add(new JLabel("Enter columns (>6):")).setFont(f);
        myPanel.add(yField).setFont(f);
        myPanel.add(new JLabel("Enter interconnectivity:")).setFont(f);
        myPanel.add(interconnectivity).setFont(f);
        myPanel.add(new JLabel("Enter treasure percentage (0 -100):")).setFont(f);
        myPanel.add(percentage).setFont(f);
        myPanel.add(new JLabel("Wrapping?")).setFont(f);
        myPanel.add(wrapping).setFont(f);
        myPanel.add(new JLabel("Select difficulty:")).setFont(f);
        myPanel.add(difficulty).setFont(f);
        okay.setFont(f);
        myPanel.add(okay, BorderLayout.PAGE_END);

        UIManager.put("OptionPane.minimumSize", new Dimension(750, 500));
        JOptionPane.showMessageDialog(null, myPanel);
      }
    });

    // quit game
    JMenuItem menuItemQuit = new JMenuItem("Quit");
    menuItemQuit.setFont(menuFont);
    menu.add(menuItemQuit);
    menuItemQuit.addActionListener((event) -> System.exit(0));

    // add items to menu bar
    menuBar.add(menu);

    // set the menu bar
    this.setJMenuBar(menuBar);
  }

  /**
   * Adds a mouse listener to the confirm button on
   * the restart game with new specifications item.
   *
   * @param controller processes the clicks on the view
   */
  @Override
  public void setSettingListeners(MvcController controller) {
    if (controller == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }
    okay.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        controller.processCommand("restart", xField.getText(), yField.getText(),
                interconnectivity.getText(), percentage.getText(),
                difficulty.getSelectedItem().toString(), wrapping.getSelectedItem().toString());
      }
    });
  }

  /**
   * Adds a mouse listener to the confirm button on
   * the restart game with same specifications item.
   *
   * @param controller processes the clicks on the view
   */
  @Override
  public void setRestartListeners(MvcController controller) {
    if (controller == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }
    sameRestart.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        controller.processCommand("restart");
      }
    });
  }

  /**
   * Adds a mouse listener to the panel consisting of the dungeon
   * grid. Allows the player to move based on the clicks made on
   * the grid.
   *
   * @param controller processes the clicks on the view
   */
  @Override
  public void moveClickListener(MvcController controller) {
    if (controller == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }
    // create the MouseAdapter
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        // arithmetic to convert panel coords to grid coords
        int xOnBoard = (e.getX() - 50) >= 0 ? ((e.getX() - 50) / 100) : -1;
        int yOnBoard = (e.getY() - 50) >= 0 ? ((e.getY() - 50) / 100) : -1;
        controller.clickListener(xOnBoard, yOnBoard);
      }
    };
    dungeonPanel.addMouseListener(clickAdapter);
  }


  /**
   * Adds a keyboard listener to the panel consisting of the dungeon
   * grid. Allows the player to move, pick or shoot based on the arrow
   * keys pressed on the keyboard.
   *
   * @param controller processes the clicks on the view
   */
  @Override
  public void setKeyListeners(MvcController controller) {
    if (controller == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }

    KeyAdapter keyAdapter = new KeyAdapter() {
      List<Integer> list = new ArrayList<>();

      private void processKey(String direction) {
        String[] values = {"1", "2", "3", "4", "5"};
        if (list.contains(KeyEvent.VK_S)) {
          String s = (String) JOptionPane.showInputDialog(null,
                  "What distance?", "Shoot", JOptionPane.INFORMATION_MESSAGE,
                  null, values, "1");
          if (s == null) {
            list = new ArrayList<>();
            displayStatus("Did not shoot");
            return;
          }
          controller.processCommand("shoot", direction, s);
        }
        else {
          controller.processCommand("move", direction);
        }
        list = new ArrayList<>();
      }

      @Override
      public void keyPressed(KeyEvent keyEvent) {
        UIManager.put("OptionPane.minimumSize", new Dimension(250, 250));
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 18));
        list.add(keyEvent.getKeyCode());
        if (!dungeon.gameOver()) {
          if (keyEvent.getKeyCode() == KeyEvent.VK_P) {
            displayStatus("What do you want to pick up? (a or t)");
          }
          if (keyEvent.getKeyCode() == KeyEvent.VK_S) {
            displayStatus("Press direction to shoot and enter number of caves");
          }
        }
        if (list.contains(KeyEvent.VK_P) && list.contains(KeyEvent.VK_T)) {
          controller.processCommand("pick", "t");
          list = new ArrayList<>();
        }

        if (list.contains(KeyEvent.VK_P) && list.contains(KeyEvent.VK_A)) {
          controller.processCommand("pick", "a");
          list = new ArrayList<>();
        }

        if (list.contains(KeyEvent.VK_UP)) {
          processKey("north");
        }
        else if (list.contains(KeyEvent.VK_DOWN)) {
          processKey("south");
        }
        else if (list.contains(KeyEvent.VK_LEFT)) {
          processKey("west");
        }
        else if (list.contains(KeyEvent.VK_RIGHT)) {
          processKey("east");
        }
      }
    };
    this.addKeyListener(keyAdapter);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void displayStatus(String s) {
    status.setVerticalAlignment(3);
    status.setText(s);
    status.setFont(new Font("Arial", Font.BOLD, 16));
    status.setForeground(Color.BLACK);
    if (s.equals("You have reached the end and won the game!")) {
      status.setFont(new Font("Arial", Font.BOLD, 18));
      status.setForeground(new Color(0, 130, 0));
    }
    if (s.equals("Oh no! You have been eaten by the Otyugh. Game Over!")) {
      status.setFont(new Font("Arial", Font.BOLD, 18));
      status.setForeground(Color.RED);
    }
  }

  @Override
  public void removeFrame() {
    dispose();
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }
}