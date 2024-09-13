package librarysystem;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.io.Serial;
import java.util.Vector;

public class ImmutableTableModel extends DefaultTableModel {
    public ImmutableTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    @Serial
    private static final long serialVersionUID = -6341949921470886718L;

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
