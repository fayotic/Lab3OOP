import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class JTableTest
{
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Percentages of Each Age Group Playing Video Games Per Genre ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,300);
        frame.setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        loadCSVData(model);

        // Set up a TableRowSorter for filtering
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Text field for filter input
        JTextField filterField = new JTextField();
        filterField.addActionListener(e -> applyFilter(sorter, filterField.getText()));

        // Set up the layout
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(filterField, BorderLayout.NORTH);

        JLabel myLabel = new JLabel("Enter genre in text field to filter and press 'ENTER'.");
        frame.add(myLabel, BorderLayout.SOUTH);

        frame.setVisible(true);


    }

    private static void loadCSVData(DefaultTableModel model) throws IOException {
        try  {
            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\fathi\\IdeaProjects\\Project3FR\\chart.csv"));
            if (!lines.isEmpty()) {
                // First line for column names
                String[] columnNames = lines.get(0).split(",");
                model.setColumnIdentifiers(columnNames);

                // Remaining lines for data
                lines.stream().skip(1) // Skip the header
                        .map(line -> line.split(","))
                        .forEach(model::addRow);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, STR."Error reading file: \{e.getMessage()}");
        }
    }

    private static void applyFilter(TableRowSorter<DefaultTableModel> sorter, String filterText) {
        if (filterText.trim().isEmpty()) {
            sorter.setRowFilter(null); // Clear the filter
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + filterText)); // Case insensitive filtering
        }
    }
}


