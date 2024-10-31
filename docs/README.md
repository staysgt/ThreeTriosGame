

In this project, a model for the game called Triple Triad was created. Triple Triad is a two
player game where a person is either blue or red. This model implemented
interfaces, classes and enums all incorporated into two major classes; the GameGridModel and 
the NESWCardModel. On top of that there are reader files that demonstrate examples of what the 
grid should look like. There is also a view and controller package. The view has a textual view
that renders the cards. The controller package currently has nothing but later on will be
implemented later on. This project doesn't contain a GUI as of now. The main 
focus for this project is the model interface. Below, there is a layout of what it looks like.

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
- In our src we have created a cs3500 package that takes in a controller package.
- CardFileReader is currently not implemented yet but will be used to read
the users card and let them keep track of their cards
- ConfigurationFileReader is used for implementations 


## Model Explanation
- In our src we have created a cs3500 package that takes in classes and creates a model package.
- The Card Interface is potentially one of the most important interfaces as it is implemented
into many different classes. It keeps track of the different types of cards in the game
- The Cell class is the grid used for the game. This is where the 
cards are placed. If the cell has a hole in it which means that it is a card can't be placed then an
IllegalException will be thrown
- CellState is an enum which is just if the cell is a hole or not
- Game grid is the interface that runs the game model. This interface lets us know how the game 
works and what would happen if the game ends and how the winner will be determined. This is implemented
into a class called GameGridModel
- In the game, there are the four directions. This determines where the four numbers on the Card will be. There
is a (public enum AttVal) in it that lets the player know what all of the valid numbers are.
- The two players are placed in an enum since their values never change.

## View Explanation 
- In our src we have created a cs3500 package that takes in classes and creates a view package.
- The view incorporates just a text view which renders the grid and lets the user pick whos turn it is in the game.
This will change later on. 

## Invariances in the code
There are a few invariances in the code. There are attack values created for each of the four directions. These attack values
are final and do not change. With these invariances created, the numbers on the card are placed in a specific order. 


## How the user gets started with the game
public NESWCardFileReader(String path) throws FileNotFoundException {
Scanner scan = new Scanner(new File(path));
int i = 1;
while (scan.hasNextLine()) {
String currLine = scan.nextLine();
String[] split = currLine.split("\\s+");
String name = split[0];

This is used for the controller of the game and it uses a scanner to abstract the 
values of the card into the user interface.

/**
* Constructor for a ConfigurationFileReader.
* @param filePath the filepath for the configuration file.
* @throws FileNotFoundException if a file does not exist at the given filepath.
  */
  public ConfigurationFileReader(String filePath) throws FileNotFoundException {
  Scanner scan = new Scanner(new File(filePath));
  String firstLine = scan.nextLine();
  this.cols = Integer.parseInt(firstLine.split(" ")[0]);
  this.rows = Integer.parseInt(firstLine.split(" ")[1]);
  while (scan.hasNextLine()) {
  rowConfig.add(scan.nextLine());
  }
  }
This implements a grid and uses a scanner to allow the user to read their cards.
