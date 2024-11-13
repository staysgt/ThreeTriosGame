package cs3500.controller;

import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;

public class MiniController {
  GameGrid model = new GameGridModel();

  public MiniController(GameGrid model) {
    this.model = model;
  }

  public void playToGrid(int row, int col, int handIdx) {
    model.playToGrid(row, col, handIdx);
  }
}
