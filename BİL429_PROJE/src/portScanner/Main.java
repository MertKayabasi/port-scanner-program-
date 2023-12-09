package portScanner;
import javax.swing.SwingUtilities;
public class Main {
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PortScannerGUI portScannerGUI = new PortScannerGUI();
                portScannerGUI.setVisible(true);
            }
        });
	}
}
