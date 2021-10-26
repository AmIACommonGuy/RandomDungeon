# Project 3 Prep

**For tessellating hexagons, one of the hardest parts is figuring out where to place each hexagon/how to easily place hexagons on screen in an algorithmic way.
After looking at your own implementation, consider the implementation provided near the end of the lab.
How did your implementation differ from the given one? What lessons can be learned from it?**

Answer: Pretty similar. It is important to figure out the relative position among hexagons.

-----

**Can you think of an analogy between the process of tessellating hexagons and randomly generating a world using rooms and hallways?
What is the hexagon and what is the tesselation on the Project 3 side?**

Answer: rooms and hallways are gonna be the hexagons. tesselation will be the arrangement.

-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating hexagons.**

Answer: 1. generating rooms; 2. generating random positions; 3. generating graph; 4. find MST; 5. Add bridges;
6. generating hallways; 7. Draw Hallways

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: Rooms are vertex, while hallways are edges. They are similar in the way that their shape are random.
