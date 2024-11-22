package cs3500.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public interface PlayerAction {

  void setListener(ActionListener e);

  void actionPerformed(ActionEvent e);

}
