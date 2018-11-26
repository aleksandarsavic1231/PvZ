import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class TileCommandTest extends TestCase {
	
	private Sunflower sunflower;

	@Before
	public void setUp() throws Exception {
		sunflower = new Sunflower(new Point(0, 0));
	}

	@After
	public void tearDown() throws Exception {
		sunflower = null;
	}
	
	@Test
	public void test() {
		
	}

}
