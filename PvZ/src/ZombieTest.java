import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ZombieTest {
	
	private Zombie z;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Point p = new Point(2,3);
		this.z = new Zombie(p);
	}

	@After
	public void tearDown() throws Exception {
		this.z = null;
	}

	@Test
	public void test() {
		z.unlock();
		Point pTest = new Point(1, 3);
		assertEquals(z.nextPosition(), pTest);
	}

}
