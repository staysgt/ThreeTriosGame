package cs3500.model.extracredit.levelone;

import java.util.Map;

import cs3500.model.Card;
import cs3500.model.Cell;
import cs3500.model.CellInterface;
import cs3500.model.CellState;
import cs3500.model.GameGrid;
import cs3500.model.NESWCard;
import cs3500.model.Player;


/**
 * Class for fallen ace stratetgy.
 *
 * @param <C>
 */
public class FallenAce2<C extends Card> extends AbstractLevelOne<C> {

  public FallenAce2(GameGrid<C> model, Map<Variant, Boolean> variants) {
    super(model, variants);
    variants.replace(Variant.FALLEN_ACE, true);
  }

  private void exceptionsPTG(int row, int col, CellInterface[][] grid, int handIdx) {
    if (isGameOver()) {
      throw new IllegalStateException("Game is over.");
    }
    if (row < 0 || row >= grid.length) {
      throw new IllegalArgumentException("Row index out of bounds");
    }
    if (col < 0 || col >= grid[0].length) {
      throw new IllegalArgumentException("Column index out of bounds");
    }
    if (handIdx > getHand(getTurn()).size() || handIdx < 0) {
      throw new IllegalArgumentException("Hand idx out of bounds");
    }
    if (grid[row][col].getCellState() == CellState.HOLE) {
      throw new IllegalArgumentException("Card cannot be played in a hole");
    }
    if (grid[row][col].getCard() != null) {
      throw new IllegalArgumentException("Location is already taken");
    }
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    exceptionsPTG(row, col, model.getBoard(), handIdx);
    grid[row][col].setCard(getHand(getTurn()).get(handIdx), getTurn());
    battle(grid, row, col, true, true, true, true, getTurn());
  }

  // Performs the battle stage.
  private void battle(CellInterface[][] grid, int rowIdx, int colIdx, boolean battleN,
                      boolean battleE, boolean battleS, boolean battleW, Player player) {
    // battles to the north & sets ownership + adds to map that tracks if its been visited
    battleNorth(grid, rowIdx, colIdx, battleN, player);
    //battles card to the south & sets ownership
    battleSouth(grid, rowIdx, colIdx, battleS, player);
    // battles card to the east & sets ownership
    battleEast(grid, rowIdx, colIdx, battleE, player);
    // battles card to the west & sets ownership
    battleWest(grid, rowIdx, colIdx, battleW, player);
  }

  private void battleWest(CellInterface[][] grid, int rowIdx, int colIdx, boolean battleW,
                          Player player) {
    int curW = 0;
    int adjE = 0;
    if (battleW && colIdx - 1 >= 0 && colIdx < grid[0].length
            && grid[rowIdx][colIdx - 1].getCard() != null) {
      Card currCard = grid[rowIdx][colIdx].getCard();
      Card adjCard = grid[rowIdx][colIdx - 1].getCard();
      if (adjCard != null && currCard != null) {
        if (variants.get(Variant.FALLEN_ACE)) {
          curW = currCard.getWestOurs().getValue() == 10 ? 1 : currCard.getWestOurs().getValue();
          adjE = adjCard.getEastOurs().getValue() == 10 ? 1 : currCard.getWestOurs().getValue();
        }

        if (variants.get(Variant.REVERSE)) {
          curW = 10 - curW;
          adjE = 10 - adjE;
        }
        if (grid[rowIdx][colIdx - 1].getOwner() != player && curW > adjE) {
          grid[rowIdx][colIdx - 1].setOwner(player);
          battle(grid, rowIdx, colIdx - 1, true, false, true,
                  true, player);
        }
      }
    }
  }

  private void battleEast(CellInterface[][] grid, int rowIdx, int colIdx, boolean battleE,
                          Player player) {
    int curE = 0;
    int adjW = 0;
    if (battleE && colIdx + 1 < grid[0].length && grid[rowIdx][colIdx + 1].getCard() != null) {
      Card currCard = grid[rowIdx][colIdx].getCard();
      Card adjCard = grid[rowIdx][colIdx + 1].getCard();
      if (adjCard != null && currCard != null) {
        if (variants.get(Variant.FALLEN_ACE)) {
          curE = currCard.getEastOurs().getValue() == 10 ? 1 : currCard.getEastOurs().getValue();
          adjW = adjCard.getWestOurs().getValue() == 10 ? 1 : adjCard.getWestOurs().getValue();
        }
        if (variants.get(Variant.REVERSE)) {
          curE = 10 - curE;
          adjW = 10 - adjW;
        }
      }
      if (grid[rowIdx][colIdx + 1].getOwner() != player && curE > adjW) {
        grid[rowIdx][colIdx + 1].setOwner(player);
        battle(grid, rowIdx, colIdx + 1, false, true, false,
                true, player);
      }
    }
  }

  private void battleSouth(CellInterface[][] grid, int rowIdx, int colIdx, boolean battleS,
                           Player player) {
    int curS = 0;
    int adjN = 0;
    if (battleS && rowIdx + 1 < grid.length && grid[rowIdx + 1][colIdx].getCard() != null) {
      Card currCard = grid[rowIdx][colIdx].getCard();
      Card adjCard = grid[rowIdx + 1][colIdx].getCard();
      if (adjCard != null && currCard != null) {
        if (variants.get(Variant.FALLEN_ACE)) {
          curS = currCard.getSouthOurs().getValue() == 10 ? 1 : currCard.getSouthOurs().getValue();
          adjN = adjCard.getNorthOurs().getValue() == 10 ? 1 : adjCard.getNorthOurs().getValue();
        }
        if (variants.get(Variant.REVERSE)) {
          curS = 10 - curS;
          adjN = 10 - adjN;
        }
      }
      if (grid[rowIdx + 1][colIdx].getOwner() != player && curS > adjN) {
        grid[rowIdx + 1][colIdx].setOwner(player);
        battle(grid, rowIdx + 1, colIdx, true, false, true,
                false, player);
      }
    }
  }

  private void battleNorth(CellInterface[][] grid, int rowIdx, int colIdx, boolean battleN,
                           Player player) {
    int curN = 0;
    int adjS = 0;
    if (battleN && rowIdx - 1 >= 0 && grid[rowIdx - 1][colIdx].getCard() != null) {
      Card currCard = grid[rowIdx][colIdx].getCard();
      Card adjCard = grid[rowIdx - 1][colIdx].getCard();
      if (adjCard != null && currCard != null) {
        if (variants.get(Variant.FALLEN_ACE)) {
          curN = currCard.getNorthOurs().getValue() == 10 ? 1 : currCard.getNorthOurs().getValue();
          adjS = adjCard.getSouthOurs().getValue() == 10 ? 1 : adjCard.getSouthOurs().getValue();
        }
        if (variants.get(Variant.REVERSE)) {
          curN = 10 - curN;
          adjS = 10 - adjS;
        }
      }
      if (grid[rowIdx - 1][colIdx].getOwner() != player && curN > adjS) {
        grid[rowIdx - 1][colIdx].setOwner(player);
        battle(grid, rowIdx - 1, colIdx, true, false, true,
                false, player);
      }
    }
  }


}
