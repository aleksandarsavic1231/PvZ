import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class WalnutTest extends TestCase {
	
	private Walnut walnut;

	@Before
	public void setUp() throws Exception {
		walnut = new Walnut(new Point(0, 0));
	}

	@After
	public void tearDown() throws Exception {
		walnut = null;
	}
	
	@Test
	public void test() {
		// TODO
	}
}
