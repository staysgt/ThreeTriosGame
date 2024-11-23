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
  private IPlayer<C> player;
  private Graphics2DView<C> view;

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
  }


  /**
   * This selects a cell on the game board.
   * <p>
   * //   * @param row the row of the selected cell
   * //   * @param col the column of the selected cell
   *
   * @throws IllegalArgumentException if it is an invalid card index
   */
//  @Override
//  public void selectCell(int row, int col) {
//
//    model.selectCell(row, col);
//
//    if (model.isCellHole(row, col) == false) {
//      throw new IllegalArgumentException("Invalid cell.");
//    }
//
//    if (model.isCellPlayable(row, col) == false) {
//      throw new IllegalArgumentException("Invalid cell.");
//    }
//
//  }
  @Override
  public void cardSelected(int cardIdx) {
    this.cardIdx = cardIdx;
  }

  @Override
  public void cellSelected(int row, int col) {
    this.row = row;
    this.col = col;
  }

  player.makeMove();

  view.setVisible(true);



  // need to display the winning score

}
