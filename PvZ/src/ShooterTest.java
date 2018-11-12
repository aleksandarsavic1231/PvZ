import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

public class ShooterTest extends TestCase{
	
	private Shooter s;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Point p = new Point(2,3);
		int Health = 5;
		
	}

	@After
	public void tearDown() throws Exception {
		s = null;
	}

	@Test
	public void test() {
		s.setFireRate(4);
		assertTrue(s.canShoot());
		s.setFireRate(0);
		assertFalse(s.canShoot());
		fail("Not yet implemented");
	}

}
