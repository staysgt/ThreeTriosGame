package cs3500.controller;

import java.util.Objects;

import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.view.Features;
import cs3500.view.Graphics2DView;

/**
 * Class for a three trios controller.
 *
 * @param <C>
 */
public class ThreeTriosController<C extends Card> implements Features {

  private final GameGrid<C> model;
  private final IPlayer<C> player;
  private final Graphics2DView<C> view;

  private int cardIdx;
  private int col;
  private int row;


  /**
   * Constructor for a three trioes controller.
   *
   * @param model  model the game is being played on.
   * @param player player who is making hte moves.
   * @param view   view for the specified player.
   * @throws NullPointerException if given arguments are null
   */
  public ThreeTriosController(GameGrid<C> model, IPlayer<C> player,
                              Graphics2DView<C> view) {
    this.model = Objects.requireNonNull(model);
    this.view = Objects.requireNonNull(view);
    this.player = Objects.requireNonNull(player);
    view.setVisible(true);
  }

  @Override
  public void cardSelected(int cardIdx) {
    this.cardIdx = cardIdx;
  }

  @Override
  public void cellSelected(int row, int col) {
    this.row = row;
    this.col = col;
    player.makeMove(row, col, cardIdx);
  }




  // need to display the winning score

}
