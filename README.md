# PvZ
# SYSC 3110 - Milestone 2
# Deliverable Date: 16 Nov 2018

**Project Members:** Kyle Horne, Alexander Savic, Tashfiq Akhand, Kaj Hemmingsen-Beriault

+ **UML DIAGRAM** - UPDATED - Tashfiq Akhand
+ **SEQUENCE DIAGRAM** - UPDATED - Alexander Savic
+ **README** - UPDATED - Kaj Hemmingsen-Beriault
+ **CODE** - (Primary Contributor):
  + Action.java - NEW - Enum - Kyle Horne
  + Alive.java - UNCHANGED - Class - Kyle Horne
  + Board.java - UNCHANGED - Interface - Kyle Horne
  + Bullet.java - UNCHANGED - Class - Kyle Horne
  + Controller.java - NEW - Class - Kyle Horne, Tashfiq Akhand, Alexander Savic
  + Entity.java - UNCHANGED - Class - Kyle Horne
  + Event.java - NEW - Class - Kyle Horne
  + GameBoard.java - REMOVED - Class - Kyle Horne
  + Listener.java - NEW - Interface - Kyle Horne
  + Moveable.java - UNCHANGED -Interface - Kyle Horne
  + Model.java - UPDATED - Class - Kyle Horne, Kaj Hemmingsen-Beriault, Tashfiq Akhand, Alexander Savic
  + PeaShooter.java - UNCHANGED - Class - Kyle Horne
  + Shooter.java - UNCHANGED - Class - Kyle Horne
  + Sun.java - NEW - Class - Kyle Horne
  + Sunflower.java - UNCHANGED - Class - Kyle Horne
  + Tile.java - REMOVED - Interface - Kyle Horne
  + View.java - NEW - Class - Alexander Savic, Kaj Hemmingsen-Beriault, Tashfiq Akhand, Kyle Horne
  + Zombie.java - UPDATED - Class - Kyle Horne, Kaj Hemmingsen-Beriault

**TESTS**
  + AllTests.java - NEW - JUnit - Kyle Horne, Tashfiq Akhand
  + AliveTest.java - NEW - JUnit - Kyle Horne, Tashfiq Akhand
  + BulletTest.java - NEW - JUnit - Kyle Horne, Tashfiq Akhand
  + EntityTest.java - NEW - JUnit - Kyle Horne, Tashfiq Akhand
  + EventTest.java - NEW - JUnit - Kyle Horne, Tashfiq Akhand
  + PeaShooterTest.java - NEW - JUnit - Kyle Horne, Tashfiq Akhand
  + SunflowerTest.java - NEW - JUnit - Kyle Horne, Tasfiq Akhand
  + ZombieTest.java - NEW - JUnit - Kyle Horne, Tashfiq Akhand
  
**ASSETS**
+ Entity Images - NEW - Kaj Hemmingsen-Beriault

**KNOWN BUGS** 
+ See issues

**DESIGN DECISIONS**

**MILESTONE 1**
+ Abstract classes were used as much as possible when creating plants and zombies, as that cut down on copied code for shared properties between entities. We also used lambda functions to reduce repeated code. Coupling was kept low with the use of interfaces. We choose to use a linked list as our primary data structure for maintaining the state of multiple Entities since traversal is unavoidable for things like collision detection; however, once the index is known, insertion and deletions are trivial with linked list. We also used the Java standard libraries as much as possible. For example, we used the Point from the java.awt library to maintain the location of Entities. Moreover, we used the static keyword for read-only variables to increase efficiency.

**MILESTONE 2**
+ Main method was transfered to View.java to adhere to MVC code.  Controller was implemented to communicate between View and Model. Sun was chosen as an interactible entity to engage users to collect the resource instead of automatically adding it to their account.  Action Enums got added to make Model more readable and reducing method complexity.  Additional JButtons for adding plants got added to the UI instead of window prompts, providing a smoother user experience and increasing clarity once in the application. A test suite was implemented so that tests can be conducted in a timely fashion and keeping results condensed and easy to view. A menu was implemented to give players the option to restart the game or exit without having to win/lose.

**FUTURE GOALS**

**MILESTONE 1**
+ The next deliverable will include the controller and view portion of the MVC, transferring the display from the console to a Swing User Interface

**UPDATE: MILESTONE 2**
+ The next deliverable is set to include multiple plants with varying purposes, and different zombie types to provide additional challenges. An undo/redo button will be added to allow players to correct any mistakes they make while playing the game.
