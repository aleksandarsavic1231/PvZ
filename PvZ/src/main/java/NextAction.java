import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextAction extends Controller implements ActionListener {

	public NextAction(Model model) {
		super(model);
	} 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new NextCommand(getModel()).execute();
	}
	
}
