# Overview

Our codebase is trying to make a Triple Triad game. We do this by creating the model.  
This incorporates the GameGrid interface and GameGridModel class that implements the 
GameGrid interface. We have also created a Card interface which is extends the cards
into numerous interfaces. 


## Table of Contents

- GameGridModel
- NESWCard
- NESWCardFileReader
- ConFigurationFileReader
- Cell


## GameGridModel
- Implements the GameGrid interface.
- Is the model for the game.
- Lets user know when game starts and when game ends
- Incorporates a grid method that will play a card to the grid
- Checks to see a few things
    - if the game is over or not (checks if any cells are empty)
    - if a specific cell is playable (if the cell is empty or not)
    - lets the user know what the current state of the board looks like
    - returns whos turn it is (if it red's turn it will return red and if it is blue's turn
      will return blue)
    - Each players current hand can be returned as a list


## NESWCard
- Implements the Card interface
- This implements an enum that has the cards values which means those values can't change
- Has the North, East, South and West attack value and this represents the
  four values that are placed in the card


NESWCardFileReader
- 