import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

public class BulletTest extends TestCase{
	private Bullet b;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Point p = new Point(2,3);
		this.b = new Bullet(p, 2);
		
	}

	@After
	public void tearDown() throws Exception {
		this.b = null;
	}

	@Test
	public void test() {
		assertEquals(b.getDamage(),2);
		Point pTest = new Point(3, 3);
		assertEquals(b.nextPosition(), pTest);
	}

}
