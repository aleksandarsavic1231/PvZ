# PvZ
# SYSC 3110 - Milestone 4
# Deliverable Date: 07 Dec 2018

**Project Members:** Kyle Horne, Alexander Savic, Tashfiq Akhand, Kaj Hemmingsen-Beriault

+ **UML DIAGRAM** - UPDATED - Tashfiq Akhand
+ **SEQUENCE DIAGRAM** - UPDATED - Alexander Savic
+ **README** - UPDATED - Kaj Hemmingsen-Beriault
+ **CODE** - (Primary Contributor):
  + Action.java - UNCHANGED - Enum - Kyle Horne
  + Alive.java - UNCHANGED - Class - Kyle Horne
  + Board.java - UNCHANGED - Interface - Kyle Horne
  + Bullet.java - UNCHANGED - Class - Kyle Horne
  + CherryBomb.java - UNCHAGED - Class - Alexander Savic
  + Chomper.java - NEW - Class - Tashfiq Akhand
  + Command.java - UNCHANGED - Class - Kyle Horne
  + Controller.java - UNCHANGED - Class - Kyle Horne, Tashfiq Akhand, Alexander Savic
  + Entity.java - UNCHANGED - Class - Kyle Horne
  + EntityEvent - NEW - Class - Kyle Horne
  + Event.java - NEW - Class - Kyle Horne
  + Executable.java - NEW - Interface - Kyle Horne
  + GameBoard.java - REMOVED - Class - Kyle Horne
  + Listener.java - NEW - Interface - Kyle Horne
  + Moveable.java - UNCHANGED -Interface - Kyle Horne
  + Model.java - UPDATED - Class - Kyle Horne, Kaj Hemmingsen-Beriault, Tashfiq Akhand, Alexander Savic
  + NextAction - UNCHANGED - Class - Kyle Horne
  + NextCommand.java - NEW - Class - Kyle Horne
  + PeaShooter.java - UNCHANGED - Class - Kyle Horne
  + Plant.java - UPDATED - Enum - Kyle Horne
  + PylonZombie.java - UNCHANGED - Class - Kaj Hemmingsen-Beriault
  + RegularZombie.java - NEW - Class - Kyle Horne
  + Repeater.java - UNCAHNGED - Class - Kaj Hemmingsen-Beriault
  + RestartAction.java - NEW - Class - Kyle Horne
  + RestartCommand.java - NEW - Class - Kyle Horne
  + Shooter.java - UNCHANGED - Class - Kyle Horne
  + Sun.java - UNCHANGED - Class - Kyle Horne
  + Sunflower.java - UNCHANGED - Class - Kyle Horne
  + TileAction.java - UNCHANGED - Class - Kyle Horne
  + TileCommand.java - UNCHANGED- Class - Kyle Horne
  + TogglePlantAction.java - NEW - Class - Kyle Horne
  + TogglePlantCommand.java - NEW - Class - Kyle Horne
  + Tile.java - REMOVED - Interface - Kyle Horne
  + Undoable.java - NEW - Interface - Kyle Horne
  + UndoManager.java - NEW - Class - Kyle Horne
  + UnimplementedEntity.java - NEW - Class - Klye Horne
  + View.java - UPDATED - Class - Alexander Savic, Kaj Hemmingsen-Beriault, Tashfiq Akhand, Kyle Horne
  + Walnut.java - UNCHANGED - Class - Kaj Hemmingsen-Beriault
  + Zombie.java - UPDATED - Class - Kyle Horne, Kaj Hemmingsen-Beriault

**TESTS**
  + AllTests.java - UPDATED - JUnit - Kyle Horne, Tashfiq Akhand
  + AliveTest.java - UNCHANGED - JUnit - Kyle Horne, Tashfiq Akhand
  + BulletTest.java - UNCHANGED - JUnit - Kyle Horne, Tashfiq Akhand
  + CherryBombTest.java - UNCHANGED - JUnit - Kyle Horne
  + ChomperTest.java - NEW - JUnit - Tashfiq Akhand
  + CommandTest.java - UNCHANGED - JUnit - Kyle Horne
  + ControllerTest.java - UNCHANGED -JUnit - Kyle Horne
  + EntityEventTest.java - UNCHANGED - Junit - Kyle Horne
  + EntityTest.java - UNCHANGED - JUnit - Kyle Horne, Tashfiq Akhand
  + EventTest.java - UNCHANGED - JUnit - Kyle Horne, Tashfiq Akhand
  + ModelTest.java - UPDATED - JUnit - Kyle Horne
  + NextCommandTest - NEW - Junit - Kyle Horne
  + PeaShooterTest.java - UNCHANGED - JUnit - Kyle Horne, Tashfiq Akhand
  + RepeaterTest.java - UNCHANGED - JUnit - Kyle Horne
  + RestartCommandTest.java - NEW - Junit - Kyle Horne
  + SunflowerTest.java - UNCHANGED - JUnit - Kyle Horne, Tasfiq Akhand
  + TileCommandTest.java - NEW -JUnit - Kyle Horne
  + TogglePlantCommandTest.java - NEW - JUnit - Kyle Horne
  + UndoManagerTest.java - UNCHANGED - JUnit - Kyle Horne
  + WalnutTest.java - UNCHANGED - JUnit - Kyle Horne
  + ZombieTest.java - UNCHANGED - JUnit - Kyle Horne, Tashfiq Akhand
  
**ASSETS**
+ Entity Images - UNCHANGED - Kaj Hemmingsen-Beriault

**KNOWN BUGS** 
+ See issues

**DESIGN DECISIONS**

**MILESTONE 1**
+ Abstract classes were used as much as possible when creating plants and zombies, as that cut down on copied code for shared properties between entities. We also used lambda functions to reduce repeated code. Coupling was kept low with the use of interfaces. We choose to use a linked list as our primary data structure for maintaining the state of multiple Entities since traversal is unavoidable for things like collision detection; however, once the index is known, insertion and deletions are trivial with linked list. We also used the Java standard libraries as much as possible. For example, we used the Point from the java.awt library to maintain the location of Entities. Moreover, we used the static keyword for read-only variables to increase efficiency.

**MILESTONE 2**
+ Main method was transfered to View.java to adhere to MVC code.  Controller was implemented to communicate between View and Model. Sun was chosen as an interactible entity to engage users to collect the resource instead of automatically adding it to their account.  Action Enums got added to make Model more readable and reducing method complexity.  Additional JButtons for adding plants got added to the UI instead of window prompts, providing a smoother user experience and increasing clarity once in the application. A test suite was implemented so that tests can be conducted in a timely fashion and keeping results condensed and easy to view. A menu was implemented to give players the option to restart the game or exit without having to win/lose.


**MILESTONE 3**
+ Command was added to the MVC event pattern so that individual actions could be passed onto a stack so that a player could undo/redo actions if they so desired.  View listens to the UndoManager and Model for event handling and properly displays the desired output from each new Event.  Stacks were used due to their FILO data management style, making it easy to push and pop appropriate actions with minimal complexity.  A new Zombie type was added with more durability to provide the user with a more challenging experience.  The addition of new plants allows for the user to have a choice in how they wish to beat the level at hand.

**MILESTONE 4**
+ We are using five major software design patterns in order to achieve a loosely coupled system. Firstly, we are using the MVC design pattern. The View is able to communicate to the Model through the Command pattern and the Model is able to respond by Event driven programming since the View is a observer of the Model. The Controller is a singleton Object; thus, only allowing the insantiation of one Model. We decided to implement our Controller as a Singleton Object since the UndoManager, View, and various Commands (which are macros of the Model) need acess to the same Model instance. Therefore, these Objects need not to communicate the shared instance of the Model and may access and modify the state of the Model without knowledge of each other. This design allows for a simple implementation of various Commands as the Model can be accessed directely through the Controller. This is especially usefull since this Milestone ask for a implementation of a save/load feature using XML. Typically, the Model is passed as a parameter to the Commands on creation of a Action from the View. However, if loaded Commands need to be insantiated from XML there is no way of passing the Model instance from the View to the Command factory without tight coupling or by converting the Controller to a Singleton Object. 

+ The save/load feature was implemented through the use of a Encodable interface. Both Entities and Commands implemented the Encodable interface since their state must be persisted in order to have a successful load of a previous save. The Encodable interface provides the decleration of a toXMLString; thus, all implementations must provided an encoding to a XML String. The Model and UndoManager implement a XMLEncoderDecoder interface which provides implementation of a save/load method. The implementation of the save method converts all fields of the Model and UndoManager to XML format. The load method parses the XML and instantiates objects using the Factory design pattern. Both the Model and UndoManager are persited together using a LinkedList in the View since they may be polymorped into type XMLEncoderDecoder, allowing for the save and load method to be called on the occurence of a Action.

**FUTURE GOALS**

**MILESTONE 1**
+ The next deliverable will include the controller and view portion of the MVC, transferring the display from the console to a Swing User Interface

**UPDATE: MILESTONE 2**
+ The next deliverable is set to include multiple plants with varying purposes, and different zombie types to provide additional challenges. An undo/redo button will be added to allow players to correct any mistakes they make while playing the game.

**UPDATE: MILESTONE 3**
+ The final deliverable will require a save/load function so the user can resume their game at a later time. It will also include a level editor so that users may customize their experience and challenge themselves.  Finally, there is potential of altering the game from turn-based to real-time.
