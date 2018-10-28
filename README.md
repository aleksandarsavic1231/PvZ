# PvZ
SYSC 3110 - Milestone 1
Deliverable Date: October 29, 2018
Project Members: Kyle Horne, Alexander Savic, Tashfiq Akhand, Kaj Hemmingsen-Beriault

UML DIAGRAM - Tashfiq Akhand

SEQUENCE DIAGRAM - Alexander Savic

README - Kaj Hemmingsen-Beriault

CODE - (Primary Contributor):
Alive.java - NEW - Class - Kyle Horne

Board.java - NEW - Interface - Kyle Horne
Bullet.java - NEW - Class - Kyle Horne
Entity.java - NEW - Class - Kyle Horne
GameBoard.java - NEW - Class - Kyle Horne
Moveable.java - NEW -Interface - Kyle Horne
PeaShooter.java - NEW - Class - Kyle Horne
PvZModel.java - NEW - Class - Kyle Horne, Kaj Hemmingsen-Beriault, Tashfiq 				Akhand, Alexander Savic
Shooter.java - NEW - Class - Kyle Horne
Sun.java - NEW - Class - Kyle Horne
Sunflower.java - NEW - Class - Kyle Horne
Tile.java - NEW - Interface - Kyle Horne
Zombie.java - NEW - Class - Kyle Horne

KNOWN BUGS -
None so far

DESIGN DECISIONS - 
Abstract classes were used as much as possible when creating plants and zombies, as that cut down on copied code for shared properties between entities. Coupling was kept low.  Unless something was fundamentally changed in another class such as method return type, issues were non-existent.  Many classes were implemented so that each class only required a minimum number of methods to execute properly.

FUTURE GOALS -
The next deliverable will include the controller and view portion of the MVC, transferring the display from the console to a Swing User Interface
