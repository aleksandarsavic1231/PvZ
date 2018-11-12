import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

public class PeaShooterTest extends TestCase{
	
	private PeaShooter ps;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Point p = new Point(2,3);
		this.ps = new PeaShooter(p);
	}

	@After
	public void tearDown() throws Exception {
		this.ps = null;
	}

	@Test
	public void test() {
		ps.setNextDeployable(2);
		assertTrue(ps.isDeployable(6));
		assertTrue(ps.isDeployable(5));
		assertFalse(ps.isDeployable(2));
	}

}
