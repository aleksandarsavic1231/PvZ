import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestartAction extends Controller implements ActionListener {

	public RestartAction(Model model) {
		super(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new RestartCommand(getModel()).execute();
	}

}
