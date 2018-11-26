import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@SuiteClasses({ 
	AliveTest.class, 
	BombTest.class,
	BulletTest.class, 
	CommandTest.class,
	ControllerTest.class,
	EntityTest.class, 
	EventTest.class,
	PeaShooterTest.class, 
	SunflowerTest.class, 
	ZombieTest.class 
})
public class AllTests extends TestCase {
	
	public static void main(String[] args) { 
		junit.textui.TestRunner.run(AllTests.class);
	} 
	
	public static Test suite() {
		TestSuite suite = new TestSuite(); 
		suite.addTest(new TestSuite(AliveTest.class)); 	
		suite.addTest(new TestSuite(Bomb.class)); 
		suite.addTest(new TestSuite(BulletTest.class)); 
		suite.addTest(new TestSuite(CommandTest.class));
		suite.addTest(new TestSuite(Controller.class)); 
		suite.addTest(new TestSuite(EntityTest.class));
		suite.addTest(new TestSuite(EventTest.class));
		suite.addTest(new TestSuite(PeaShooterTest.class)); 
		suite.addTest(new TestSuite(SunflowerTest.class)); 	
		suite.addTest(new TestSuite(ZombieTest.class)); 
		return suite;
	} 

}
