import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class NextCommandTest extends TestCase {
	
	private NextCommand nextCommand;
	
	private Model model;

	@Before
	public void setUp() throws Exception {
		model = new Model();
		nextCommand = new NextCommand(model);
	}

	@After
	public void tearDown() throws Exception {
		nextCommand = null;
		model = null;
	}
	
	@Test
	public void test() {
		
	}

}
