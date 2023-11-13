import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductMaker extends JFrame {
    private JTextField nameField, descriptionField, idField, recordCountField;
    private JButton addButton;
    private RandomAccessFile randomAccessFile;

    public RandProductMaker() {
        super("Random Product Maker");

        nameField = new JTextField(35);
        descriptionField = new JTextField(75);
        idField = new JTextField(6);
        recordCountField = new JTextField(10);
        recordCountField.setEditable(false);

        addButton = new JButton("Add Record");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                addRecord();
            }
        });

        setLayout(new GridLayout(5, 2, 5, 5));
        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Description:"));
        add(descriptionField);
        add(new JLabel("ID:"));
        add(idField);
        add(new JLabel("Record Count:"));
        add(recordCountField);
        add(addButton);

        try {
            randomAccessFile = new RandomAccessFile("product_data.dat", "rw");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addRecord() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String id = idField.getText();

        if (name.trim().isEmpty() || description.trim().isEmpty() || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Product product = new Product();
        product.formatName();
        product.formatDescription();
        product.formatID();

        try {
            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.writeUTF(product.formatName());
            randomAccessFile.writeUTF(product.formatDescription());
            randomAccessFile.writeUTF(product.formatID());
        } catch (IOException e) {
            e.printStackTrace();
        }

        nameField.setText("");
        descriptionField.setText("");
        idField.setText("");

        updateRecordCount();
    }

    private void updateRecordCount() {
        try {
            long recordCount = randomAccessFile.length() / (35 + 75 + 6); // Total length of one record
            recordCountField.setText(Long.toString(recordCount));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RandProductMaker randProductMaker = new RandProductMaker();
            randProductMaker.setSize(400, 200);
            randProductMaker.setVisible(true);
        });
    }
}

