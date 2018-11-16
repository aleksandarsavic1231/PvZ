# PvZ
# SYSC 3110 - Milestone 1
# Deliverable Date: 29 Oct 2018

**Project Members:** Kyle Horne, Alexander Savic, Tashfiq Akhand, Kaj Hemmingsen-Beriault

+ **UML DIAGRAM** - Tashfiq Akhand
+ **SEQUENCE DIAGRAM** - Alexander Savic
+ **README** - Kaj Hemmingsen-Beriault
+ **CODE** - (Primary Contributor):
  + Alive.java - NEW - Class - Kyle Horne
  + Board.java - NEW - Interface - Kyle Horne
  + Bullet.java - NEW - Class - Kyle Horne
  + Entity.java - NEW - Class - Kyle Horne
  + GameBoard.java - NEW - Class - Kyle Horne
  + Moveable.java - NEW -Interface - Kyle Horne
  + PeaShooter.java - NEW - Class - Kyle Horne
  + PvZModel.java - NEW - Class - Kyle Horne, Kaj Hemmingsen-Beriault, Tashfiq Akhand, Alexander Savic
  + Shooter.java - NEW - Class - Kyle Horne
  + Sun.java - NEW - Class - Kyle Horne
  + Sunflower.java - NEW - Class - Kyle Horne
  + Tile.java - NEW - Interface - Kyle Horne
  + Zombie.java - NEW - Class - Kyle Horne

**KNOWN BUGS** 
+ [See issues](https://github.com/aleksandarsavic1231/PvZ/issues) 

**DESIGN DECISIONS**
+ Abstract classes were used as much as possible when creating plants and zombies, as that cut down on copied code for shared properties between entities. We also used lambda functions to reduce repeated code. Coupling was kept low with the use of interfaces. We choose to use a linked list as our primary data structure for maintaining the state of multiple Entities since traversal is unavoidable for things like collision detection; however, once the index is known, insertion and deletions are trivial with linked list. We also used the Java standard libraries as much as possible. For example, we used the Point from the java.awt library to maintain the location of Entities. Moreover, we used the static keyword for read-only variables to increase efficiency.
 
**FUTURE GOALS**

**MILESTONE 1**
+ The next deliverable will include the controller and view portion of the MVC, transferring the display from the console to a Swing User Interface

**UPDATE: MILESTONE 2**
+ The next deliverable is set to include multiple plants with varying purposes, and different zombie types to provide additional challenges. An undo/redo button will be added to allow players to correct any mistakes they make while playing the game.
