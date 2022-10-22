# Battleship

Battleship is a two-player console application. 
Game sticks to the original rules written by Milton Bradley. 
The ships can be placed horizontally or vertically but not diagonally across the grid spaces. 
The ships should not cross or touch each other. 
The goal is to sink all the ships of the opponent before your opponent does this to you.


You should arrange your ships on the game field. Before you start, please read the conventions of the game:

On a 10x10 field, the first row contains numbers from 1 to 10 indicating the column, and the first column contains letters from A to J indicating the row.
The symbol ~ denotes the fog of war: the unknown area on the opponent's field and the yet untouched area on your field.
The symbol O denotes a cell with your ship, X denotes that the ship was hit, and M signifies a miss.

You have 5 ships: 
- Aircraft Carrier is 5 cells, 
- Battleship is 4 cells,
- Submarine is 3 cells, 
- Cruiser is 3 cells,
- Destroyer is 2 cells.

Start placing your ships with the largest one.
To place a ship, enter two coordinates: the beginning and the end of the ship.
If an error occurs in the input coordinates, your program reports it. The message contains the word Error.
