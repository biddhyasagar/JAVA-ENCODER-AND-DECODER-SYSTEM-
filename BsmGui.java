import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Dimension;

public class BsmGui extends JFrame implements ActionListener {

    private JTextField inputF;
    private JTextArea outputbsm;
    private JButton encoding, decoding, clearbsm, copybsm, pastebsm, helpbsm, checksumbsm;
    private JLabel statusbsm;


    public BsmGui() {
        setTitle("BiddhyaSagarMainali_Encoder/Decoder");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel topLabel = new JLabel("𝐖𝐄𝐋𝐂𝐎𝐌𝐄_𝐓𝐎_𝐁𝐒𝐌_𝐄𝐍𝐂𝐎𝐃𝐄𝐑&𝐃𝐄𝐂𝐎𝐃𝐄𝐑 𝐒𝐘𝐒𝐓𝐄𝐌");
        topLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topLabel.setFont(topLabel.getFont().deriveFont(18f));
        topLabel.setForeground(new Color(0, 0, 128));

        JPanel topLabelPanel = new JPanel(new BorderLayout());
        topLabelPanel.setBackground(Color.CYAN);
        topLabelPanel.add(topLabel, BorderLayout.CENTER);

        JLabel label = new JLabel("𝐄𝐧𝐭𝐞𝐫 𝐭𝐡𝐞 𝐓𝐞𝐱𝐭:");
        inputF = new JTextField(50);
        inputF.setHorizontalAlignment(JTextField.CENTER);
        inputF.addActionListener(this);
        inputF.setPreferredSize(new Dimension(inputF.getPreferredSize().width, 40));

        outputbsm = new JTextArea();
        outputbsm.setRows(10);
        outputbsm.setColumns(30);
        outputbsm.setEditable(false);
        outputbsm.setBackground(Color.LIGHT_GRAY);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        outputPanel.setBackground(Color.CYAN);
        outputPanel.add(new JScrollPane(outputbsm), BorderLayout.CENTER);

        encoding = new JButton("𝙴𝚗𝚌𝚘𝚍𝚎");
        decoding = new JButton("𝙳𝚎𝚌𝚘𝚍𝚎");
        clearbsm = new JButton("𝙲𝚕𝚎𝚊𝚛");
        copybsm = new JButton("𝙲𝚘𝚙𝚢");
        pastebsm = new JButton("𝙿𝚊𝚜𝚝𝚎");
        helpbsm = new JButton("𝙷𝚎𝚕𝚙");
        checksumbsm = new JButton("𝙲𝚑𝚎𝚌𝚔𝚜𝚞𝚖");

        encoding.addActionListener(this);
        decoding.addActionListener(this);
        clearbsm.addActionListener(this);
        copybsm.addActionListener(this);
        pastebsm.addActionListener(this);
        helpbsm.addActionListener(this);
        checksumbsm.addActionListener(this);

        encoding.setBackground(Color.ORANGE);
        decoding.setBackground(Color.ORANGE);
        clearbsm.setBackground(Color.ORANGE);
        copybsm.setBackground(Color.ORANGE);
        pastebsm.setBackground(Color.ORANGE);
        helpbsm.setBackground(Color.ORANGE);
        checksumbsm.setBackground(Color.ORANGE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        panel.add(topLabelPanel, gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(label, gbc);
        gbc.gridy++;
        panel.add(inputF, gbc);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(encoding);
        buttonPanel.add(decoding);
        buttonPanel.add(clearbsm);
        buttonPanel.add(copybsm);
        buttonPanel.add(pastebsm);
        buttonPanel.add(helpbsm);
        buttonPanel.add(checksumbsm);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        centerPanel.add(outputPanel, BorderLayout.CENTER);

        statusbsm = new JLabel("Status: Ready to encode/decode.");
        JPanel statusPanel = new JPanel();
        statusPanel.add(statusbsm);

        
        add(panel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == inputF || e.getSource() == encoding) {
            String inputText = inputF.getText();
            if (inputText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Some Input.", "Status", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String encodedText = encodeBase64(inputText);
            outputbsm.setText("Encoded message: " + encodedText);
            statusbsm.setText("Status: Encoded.");
        } else if (e.getSource() == decoding) {
            String inputText = inputF.getText();
            if (inputText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter some Input.", "Status", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                String decodedText = decodeBase64(inputText);
                outputbsm.setText("Decoded message: " + decodedText);
                statusbsm.setText("Status: Decoded.");
            } catch (IllegalArgumentException ex) {
                outputbsm.setText("Error: Invalid input.");
                statusbsm.setText("Status: Invalid input.");
            }
        } else if (e.getSource() == clearbsm) {
            inputF.setText("");
            outputbsm.setText("");
            statusbsm.setText("Status: Ready to encode/decode.");
        } else if (e.getSource() == copybsm) {
            String outputText = outputbsm.getText();
            if (outputText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No output to copy.", "Status", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            StringSelection stringSelection = new StringSelection(outputText.substring(outputText.indexOf(":") + 2));
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);
            statusbsm.setText("Status: Copied to clipboard.");
        } else if (e.getSource() == pastebsm) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable contents = clipboard.getContents(null);
            if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    String pastedText = (String) contents.getTransferData(DataFlavor.stringFlavor);
                    inputF.setText(pastedText);
                    statusbsm.setText("Status: Text pasted from clipboard.");
                } catch (Exception ex) {
                    statusbsm.setText("Status: Error pasting text from clipboard.");
                }
            } else {
                statusbsm.setText("Status: No text found in clipboard.");
            }
        } else if (e.getSource() == helpbsm) {
            JOptionPane.showMessageDialog(this,
                    "This is a BiddhyaSagarMainali Encoder/Decoder program.\n"
                            + "Enter the text you want to encode or decode in the input field.\n"
                            + "Press the Encode button to encode the input text.\n"
                            + "Press the Decode button to decode the input text.\n"
                            + "Press the Clear button to clear the input and output fields.\n"
                            + "Press the Copy button to copy the output text to the clipboard.\n"
                            + "Press the Paste button to paste text from the clipboard.\n"
                            + "Press the Help button to show this message.\n"
                            + "Press the Checksum button to calculate the checksum of the input text.",
                    "Help",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == checksumbsm) {
            String inputText = inputF.getText();
            if (inputText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter some Input.", "Status", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] inputBytes = inputText.getBytes(StandardCharsets.UTF_8);
                md.update(inputBytes);
                byte[] checksumBytes = md.digest();
                String checksumText = bytesToHex(checksumBytes);
                outputbsm.setText("Checksum: " + checksumText);
                statusbsm.setText("Status: Checksum calculated.");
            } catch (Exception ex) {
                outputbsm.setText("Error: Invalid input.");
                statusbsm.setText("Status: Invalid input.");
            }
        }
    }

    public String encodeBase64(String plainText) {
        StringBuilder binaryRepresentation = new StringBuilder();
        for (char c : plainText.toCharArray()) {
            
            String binary = Integer.toBinaryString(c);
          
            binary = String.format("%8s", binary).replace(' ', '0');
            binaryRepresentation.append(binary);
        }

        StringBuilder encodedResult = new StringBuilder();
        int index = 0;
        while (index < binaryRepresentation.length()) {
            
            String group = binaryRepresentation.substring(index, Math.min(index + 6, binaryRepresentation.length()));
          
            while (group.length() < 6) {
                group += "00"; 
            }
          
            int decimalValue = Integer.parseInt(group, 2);
            
            encodedResult.append(Base64EncodeChars.charAt(decimalValue));
            index += 6;
        }
       
        while (encodedResult.length() % 4 != 0) {
            encodedResult.append('=');
        }
        return encodedResult.toString();
    }

    public String decodeBase64(String cipherText) throws IllegalArgumentException {
        StringBuilder binaryRepresentation = new StringBuilder();
        for (char c : cipherText.toCharArray()) {
          
            if (c == '=') {
                continue;
            }
            
            int index = Base64DecodeChars.indexOf(c);
            if (index == -1) {
                throw new IllegalArgumentException("Invalid character in Base64 input");
            }
           
            String binary = Integer.toBinaryString(index);
            
            binary = String.format("%6s", binary).replace(' ', '0');
            binaryRepresentation.append(binary);
        }

        StringBuilder decodedResult = new StringBuilder();
        int index = 0;
        while (index < binaryRepresentation.length()) {
         
            String group = binaryRepresentation.substring(index, Math.min(index + 8, binaryRepresentation.length()));
            
            int decimalValue = Integer.parseInt(group, 2);
           
            decodedResult.append((char) decimalValue);
            index += 8;
        }
        return decodedResult.toString();
    }

    public String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static final String Base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static final String Base64DecodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BsmGui().setVisible(true);
            }
        });
    }
}
