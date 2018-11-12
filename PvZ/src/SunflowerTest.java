import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

import java.awt.Point;


public class SunflowerTest extends TestCase{
	
	private Sunflower s;
	private Point p;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.p = new Point(2,3);
		this.s = new Sunflower(p);
	}

	@After
	public void tearDown() throws Exception {
		this.s = null;
	}

	@Test
	public void test() {
		s.setNextDeployable(2);
		assertTrue(s.isDeployable(6));
		assertTrue(s.isDeployable(5));
		assertFalse(s.isDeployable(2));
		
	}
	


}
