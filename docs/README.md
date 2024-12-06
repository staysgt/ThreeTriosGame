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

## Changes for part 3:

In our model we realized that there was an uneccsary method we had called isCellPlayable, so we
got rid of this (we already had legalPlay method which had the same functinoality). We updated the
MouseClicked method to be able to include a pop-up window. This window will let the player know if
they are making mistakes such as placing a card in a non-empty cell or a hole cell. It also lets the
player know if they clicked on a cell without selecting a card.

## New classes in the controller

In the controller we added a few new packages. First we created the IPlayer interface that contains
methods containing the actions that a player can do (ie makeMove). This has two subclasses for the
two different types of players, human and machine. Based on the type of player that is making the
moves, they will behave differently, and these subclasses describe those differences. We also made
test classes for both of these classes in the controller subpackage in the test directory. And then
we also added the ThreeTriosController, which is the overall controller for the game. This class
implements the features interface from the view class in attempts to be able to keep up in real time
when the player makes a click on the board. However, we had some issues when trying to actually do
this, so currently, our controller does not support human players. But, we were able to get it to
work using the AI strategies, and had the view update accordingly to this. Because our controller is
not fully working as intended, we did not make the proper tests for the human aspects in the actual
controller. Had we gotten this far, we would've created mocks of the controller for ease of testing.

## New classes in the view

In the view we created a popUpWindow class which creates a new JPanel that will be called into the
MouseClick class. This includes a small panel with a close statement at the bottom so the user can
exit out of it. Overall, this helps the game function more thoroughly because the player is informed
when they make a mistake. We also created a features interface which helps the controller. This
interface is used for the board. In the GameGridFrameView we incoorporated this interface and used
action listeners to help the board respond to specific event such as the user placing a card onto a
cell.

## Command Line Arguments

When running the program, the first entered string will be associated with the first player, and the
second entered string will be associated with teh second player. If a human player is desired, enter
'human', otherwise, enter the desired strategy for the machine player ('flipmost', 'corners',
'minimax', or 'cardlesslikely'). Example of running the jar file with two human players:

java -jar ThreeTriosGame.jar human human

## Changes from HW7

When working on this assignment, we made some changes to our HW7 view, in order to make it more
organized. Our view used the concrete implementation of a card (NESWCard) rather than the interface.
We updated it to make sure that it was reliant on our interface rather than a class. We also added
in command line arguments, because we realized this was missing from the last assignment that we had
turned in.

## What we were able to get working:

We were able to get everything working between our code and our providers code. However, we were
never able to get our model to work for human players from the last homework assignment, so this
still does not work, but our views work with handling clicks, just not representing a card played
to the grid. But, our controller does work between two machine players, and we were able to get the
views to represent this, in the main method.