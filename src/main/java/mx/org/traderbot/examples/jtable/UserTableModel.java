package mx.org.traderbot.examples.jtable;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class UserTableModel extends AbstractTableModel {

    private List<User> userData = new ArrayList<User>();
    private String[] columnNames =  {"First Name", "Last Name"};

    public UserTableModel() {}

    public UserTableModel(List<User> userData) {
        this.userData = userData;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return userData.size();
    }

    public Object getValueAt(int row, int column) {
        Object userAttribute = null;
        User userObject = userData.get(row);
        switch(column) {
            case 0: userAttribute = userObject.getFirstName(); break;
            case 1: userAttribute = userObject.getLastName(); break;
            default: break;
        }
        return userAttribute;
    }

    public void addUser(User user) {
        userData.add(user);
        fireTableDataChanged();
    }
}