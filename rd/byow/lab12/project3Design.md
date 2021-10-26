# Project3 Design

-----
## Procedure:
1. Randomly generate rooms.
2. Construct a graph that connects all room.
3. Construct hallways.

-----
## Randomly generates rooms

### Room.class:
* Room.class will be contained in a Set like structure (no overlap allowed)

#### parameters:
1. position : only record center ?
2. size: contains length and width

-----
### Solve Overlap
* Use set to record all occupied area. Only unoccupied unique value can be added to the set

_____
## Construct Hallways
### Method 1: No graph
Randomly connects rooms until all rooms are connected

-----
### Method 2: Rooms are only connected to their nearst neighbors.
We need to generate a MST that connects n points with their nearest neighbors. (KD tree)

-----
### Eligible Hallway: 
* The elegible Hallway that connects (x_1, y_1) and (x_2, y_2) <- (center of rooms) will be y = y_1 and x = x_2, or y = y_2 and x = x_1. 
* Randomly assign hallway width (1 ~ 2)

----

## Map.class
* Collection\<Room> Rooms
* Collection\<Position> Hallways
### Method:
#### public Room / void generateNewRoom();

#### public Position / void whereToPutHallway();

#### public void DrawWorld();



















