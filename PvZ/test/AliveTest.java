import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

public class AliveTest extends TestCase{
	
	private Alive a;
	 

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
		this.a = new Alive(p, Health);
		
	}

	@After
	public void tearDown() throws Exception {
		this.a = null;
	}

	@Test
	public void test() {
		assertEquals(a.getHealth(), 5);
		//assertEquals(a.setHealth(1),1);
	}

}
