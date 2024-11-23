package cs3500.controller;


import cs3500.model.Player;

public interface PlayerAction {

  /**
   * This selects a card from the user's hand.
   *
   * @param cardIndex the index of the card in the user's hand
   */
  void selectCard(Player player, int cardIndex);

  /**
   * This selects a cell on the game board.
   *
   * @param row the row of the selected cell
   * @param col the column of the selected cell
   */
  void selectCell(int row, int col);

}
