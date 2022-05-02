package mx.org.traderbot.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import mx.org.traderbot.util.Util;
import mx.org.traderbot.chart.candles.model.CandleModel;
import mx.org.traderbot.orderbook.vo.OrderResume;
import mx.org.traderbot.trade.vo.Trade;



public class DataTableModel<T> extends AbstractTableModel {

	final static Logger logger = Logger.getLogger(DataTableModel.class);
	
	private List<T> data = new ArrayList<T>();
    private String[] columnNames;

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public DataTableModel() {}

    public DataTableModel(List<T> data) {
        this.data = data;
    }

    
    public DataTableModel(T[] trade) {
		this.data =   (List<T>) Arrays.asList(trade);
	}

	@Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public Object getValueAt(int row, int column) {
        Object dataAttribute = null;
        T object = data.get(row);
        
        dataAttribute = Util.valor(object, column);
        
        return dataAttribute;
    }

    public void addUser(T order) {
        data.add(order);
        fireTableDataChanged();
    }
    
    public void updateTable(List<T> data)
    {
    	this.data = data;
    	fireTableDataChanged();    	
    	logger.info("Actualizanda");
    }
    
    public void updateTable(T[] data)
    {
    	this.data = (List<T>) Arrays.asList(data);;
    	fireTableDataChanged();    	
    	logger.info("Actualizanda");
    }
}