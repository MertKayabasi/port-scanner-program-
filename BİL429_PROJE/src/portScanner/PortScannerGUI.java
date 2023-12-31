package portScanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.List;

public class PortScannerGUI extends JFrame {

    private JTextArea resultTextArea;
    private JButton scanButton;
    private JTextField hostTextField;
    private JTextField startPortTextField;
    private JTextField endPortTextField;

    public PortScannerGUI() {
        setTitle("Port Scanner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        scanButton = new JButton("Scan Ports");
        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scanPorts();
            }
        });

        hostTextField = new JTextField();
        startPortTextField = new JTextField();
        endPortTextField = new JTextField();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Host Address:"));
        inputPanel.add(hostTextField);
        inputPanel.add(new JLabel("Start Port:"));
        inputPanel.add(startPortTextField);
        inputPanel.add(new JLabel("End Port:"));
        inputPanel.add(endPortTextField);
        inputPanel.add(new JLabel()); // Empty label for spacing
        inputPanel.add(scanButton);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    private void scanPorts() {
        String hostAddress = hostTextField.getText();

        try {
            int startPort = Integer.parseInt(startPortTextField.getText());
            int endPort = Integer.parseInt(endPortTextField.getText());

            InetAddress inetAddress = InetAddress.getByName(hostAddress);
            Host host = new Host(inetAddress);

            List<Integer> openPorts = host.openPortNumbersBetween(startPort, endPort);

            // JTextArea'ya sonuçları ekle
            resultTextArea.setText(""); // Mevcut metni temizle

            if (openPorts.isEmpty()) {
                resultTextArea.append("Belirtilen aralıkta açık port bulunamadı.");
            } else {
                for (int port : openPorts) {
                    String portDescription = host.portDescription(port);
                    String result = "Port " + port + ": " + portDescription + "\n";
                    resultTextArea.append(result);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lütfen geçerli bir sayı girin.", "Hata", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
}
