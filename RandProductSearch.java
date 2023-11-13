import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductSearch extends JFrame {
    private JTextField partialNameField;
    private JButton searchButton;
    private JTextArea resultArea;
    private RandomAccessFile randomAccessFile;

    public RandProductSearch() {
        super("Random Product Search");

        partialNameField = new JTextField(35);
        searchButton = new JButton("Search");
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                searchProducts();
            }
        });

        setLayout(new FlowLayout());
        add(new JLabel("Partial Name:"));
        add(partialNameField);
        add(searchButton);
        add(new JScrollPane(resultArea));

        try {
            randomAccessFile = new RandomAccessFile("product_data.dat", "r");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void searchProducts() {
        resultArea.setText("");

        String partialName = partialNameField.getText().trim().toLowerCase();

        try {
            randomAccessFile.seek(0);

            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                String name = randomAccessFile.readUTF().trim().toLowerCase();
                String description = randomAccessFile.readUTF().trim();
                String id = randomAccessFile.readUTF().trim();

                if (name.contains(partialName)) {
                    resultArea.append("Name: " + name + "\n");
                    resultArea.append("Description: " + description + "\n");
                    resultArea.append("ID: " + id + "\n");
                    resultArea.append("----------------------------\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RandProductSearch randProductSearch = new RandProductSearch();
            randProductSearch.setSize(500, 300);
            randProductSearch.setVisible(true);
        });
    }
}
