package cs3500.view;

import cs3500.model.Player;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;


/**
 * This class is used for the pop up section of the game, a message will pop up
 * if the user doesn't take the card.
 */
public class PopUpWindow extends JFrame {

  /**
   * This is the constructor used for the popup window.
   *
   * @param player  takes in the enum of the player.
   * @param message takes in the message.
   */
  public PopUpWindow(Player player, String message) {
    initComponents(player, message);
    this.setLocationRelativeTo(null);
  }

  private void initComponents(Player player, String message) {
    JLabel players = new JLabel(player.name() + ":", JLabel.CENTER);
    JLabel messages = new JLabel(message + ":", JLabel.CENTER);


    JButton button = new JButton("Close");
    button.addActionListener((ActionEvent e) -> this.dispose());

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(players, BorderLayout.NORTH);
    panel.add(messages, BorderLayout.CENTER);
    panel.add(button, BorderLayout.SOUTH);

    this.add(panel);
    this.setSize(300, 150);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }
}