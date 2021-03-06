import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@SuiteClasses({ 
	AliveTest.class, 
	BulletTest.class, 
	CherryBombTest.class,
	CommandTest.class,
	EntityEventTest.class,
	EntityTest.class,
	EventTest.class,
	ModelTest.class,
	NextCommandTest.class,
	PeaShooterTest.class, 
	RepeaterTest.class, 
	ChomperTest.class,
	RestartCommandTest.class, 
	SunflowerTest.class, 
	TileCommandTest.class, 
	TogglePlantCommandTest.class, 
	UndoManagerTest.class,
	WalnutTest.class,
	ZombieTest.class 
})
public class AllTests extends TestCase {
	
	public static void main(String[] args) { 
		junit.textui.TestRunner.run(AllTests.class);
	} 

	public static Test suite() {
		TestSuite suite = new TestSuite(); 
		suite.addTest(new TestSuite(AliveTest.class)); 	
		suite.addTest(new TestSuite(BulletTest.class)); 
		suite.addTest(new TestSuite(CommandTest.class));
		suite.addTest(new TestSuite(CherryBombTest.class)); 
		suite.addTest(new TestSuite(ChomperTest.class));
		suite.addTest(new TestSuite(EntityEventTest.class));
		suite.addTest(new TestSuite(EntityTest.class));
		suite.addTest(new TestSuite(EventTest.class));
		suite.addTest(new TestSuite(ModelTest.class));
		suite.addTest(new TestSuite(NextCommandTest.class));
		suite.addTest(new TestSuite(PeaShooterTest.class)); 
		suite.addTest(new TestSuite(RepeaterTest.class)); 
		suite.addTest(new TestSuite(ChomperTest.class));
		suite.addTest(new TestSuite(RestartCommandTest.class)); 
		suite.addTest(new TestSuite(SunflowerTest.class)); 
		suite.addTest(new TestSuite(UndoManagerTest.class)); 	
		suite.addTest(new TestSuite(TileCommandTest.class)); 	
		suite.addTest(new TestSuite(TogglePlantCommandTest.class)); 	
		suite.addTest(new TestSuite(WalnutTest.class)); 	
		suite.addTest(new TestSuite(ZombieTest.class)); 
		return suite;
	} 

}
