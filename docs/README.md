# Overview

In this project, a model for the game called Three Trios was created. Three Trios is a two
player game where a person is either blue or red. This model implemented
interfaces, classes and enums all incorporated into two major classes; the GameGridModel and
the NESWCard. On top of that there are reader files that demonstrate examples of what the
grid should look like. There is also a view and controller package. The view has a textual view
that renders the cards. The controller package currently has nothing but later on will be
implemented. The main focus for this project is the model interface. Below, there is a layout of
what it looks like. Since
we used an interface to implement the two player game and the cards, it allows for more flexibility
later on when potentially creating different versions of the game.

/
| +- README
+- src/
| +- cs3500/
| | +- controller/
| | | +- ConfigurationFileReader
| | | +- NESWCardFileReader
| | | +- CardFileReader
| | +- model/
| | | +- Card
| | | +- Cell
| | | +- CellState
| | | +- GameGrid
| | | +- GameGridModel
| | | +- NESWCard
| | | +- Player
| | | +- ThreeTriosFileReader
| | +- view/
| | | +- GameGridTextView
| | | +- TextView
| +- cardsexample
| +- Main
| +- noholes
| +- TextView
| +- unreachableholes
| +- walkableholes
+- test/
| +- cs3500/
| | +- controller/
| | | +- ConfigFileReaderTests
| | | +- NESWCardFileReaderTests
| | +- model/
| | | +- CardTests
| | | +- GameGridModelTests

## Controller Explanation

Although there is not a controller implemented yet, the controller package contains classes that
will prove useful for the later creation of a controller.
Current classes within the controller:

- CardFileReader takes in a cardfile txt file and will be used in a controller to create a game.
  Since the controller is not yet implmemented, this file is only used for testing purposes.
- ConfigurationFileReader takes in a txt file and is useful for assembling the board for a three
  trios game.
- We chose for these classes to be in the controller folder because it is the controller that will
  ultimately end up using the behaviors created by these classes.

## Model Explanation

- The Card Interface is implemented into many different classes. It serves as a model for the
  behaviors of a card.
- The Cell class represents what the grid is made up of. Each cell represents a coordinate of the
  grid. If the cell has a hole in it which means that it is a card can't be placed then an
  IllegalException will be thrown
- CellState is an enum which is just if the cell is a hole or not
- Game grid is the interface that runs the game model. This interface lets us know how the game
  works and what would happen if the game ends and how the winner will be determined. This is
  implemented
  into a class called GameGridModel
- In a card, there are the four directional values. There is a (public enum AttVal) in it that lets
  the player know what the valid numbers on a NESW card are.
- We also have a player enum that represents the two players of a game.

## View Explanation

For the view, we created a GUI. This GUI uses a 2D graphics class we created as the view.
This makes it so that the grid is able to be visualized. We also used the java MouseListener
interface and implemented it into a mouseClick class. With this, either player is able to
highlight the card they want to place and put it on the grid. This is changes from the original
view that renders a textual view of the grid.

## Invariances in the code

There are a few invariances in the code. There are attack values created for each of the four
directions. These attack values
are final and do not change. With these invariances created, the numbers on the card are placed in a
specific order.

## How the user gets started with the game

For a user to get started with the game, they must create an instance of the model. In order to
start the game,
they must provide the start game with the necessary parameters (list of cards, # of rows, # of cols,
config file).
This is easily extracted from a configuration file using the ConfigurationFileReader class. To make
moves, they can call
the playToGrid method on the previously created/started model.

## Changes made to the model from HW5

We had a few necessary changes to make from our original model in HW5 in order to make the
strategies possible. First, we started by making a ReadOnlyGameGridModel interface, that contains
the methods that cannot alter the grid. Our original interface extends the read only interface. We
also added in a few new methods, cardsFlipped (necessary for flip most strategy), legalPlay
(needed for strategies), and getGameStatuses (needed for minimax strategy). A few other
miscellaneous changes were made, including ensuring the getBoard was returning a deep copy of the
board, so that the user could not use this to cheat. As well, we added another constructor that
constructs an in progress game, which was needed for getting the possible strategies used on
previous moves.

## New Strategies Package

For HW6, we created a package within the controller to hold all of the strategies, and files
related to the strategy. Each of the four strategies and their decorators have their own class that
contain the logic behind running the strategies.

## Extra Credit Strategies Implemented

In order to implement all 4 of the strategies we first used the strategy design pattern to make sure
that the code stayed
clean. After implemented the strategies, we used the decorator design pattern to allow the user to
create more complex
versions of the strategies. The usage of these strategies and their implementations can be found in
the test/cs3500/controller
package.