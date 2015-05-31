package magna.vlt.ui.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;

import magna.vlt.util.Sorter;
import magna.vlt.util.Tokenizer;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TableColumn;

public class UIHelper {

	public void initCombo(Combo combo, Hashtable<?, String> table){
		combo.removeAll();
		combo.setEnabled(true);
		
		int index = table.keySet().size();
		String[] items = new String[table.keySet().size()+1];
		items[0] = "";
		for(Object key: table.keySet()){
			String value = "";
			
			if(key instanceof Integer){
				int castedKey = (Integer) key;
				value = table.get(castedKey);
			}else if (key instanceof String){
				String castedKey = (String) key;
				value = table.get(castedKey);
			}
			
			String text = Tokenizer.concatWithDelim("-", String.valueOf(key), value);
			items[index--] = text;
		}
		combo.setItems(items);
		combo.select(0);
	}
	
	public String getCurrentDate()
	{
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
	    String strdate = dateFormat.format(date);
	    return strdate;
	}
	

	public String convertDateToString(int day, int month, int year){
		String strDate = String.valueOf(year) +"-";
	    strDate += (month < 10) ? "0" + month + "-" : month + "-";
	    strDate += (day < 10) ? "0" + day + "-" : day;
	    return strDate;
	}
	public void initList(List list, Hashtable<Integer, String> table, boolean asRemove){
		if(asRemove) list.removeAll();
		String[] currentItems = list.getItems();
		 
		int index = table.keySet().size()-1;
		String[] items = new String[table.keySet().size() + currentItems.length];
		Set<Integer> keySet = table.keySet();
		
		ArrayList<Integer> keyList = (ArrayList<Integer>) Sorter.asSortedList(keySet);
		
		for(Integer key: keyList){
			String value = table.get(key);
			String text = Tokenizer.concatWithDelim("-", String.valueOf(key), value);
			items[index--] = text;
		}
		
		int currentIndex = keyList.size();
		for(String item: currentItems) items[currentIndex++] = item;
		list.setItems(items);
		list.select(0);
	}
	
	
	
	public void initList(List list, Hashtable<String, String> table){
		list.removeAll();
		String[] currentItems = list.getItems();
		 
		int index = table.keySet().size()-1;
		String[] items = new String[table.keySet().size() + currentItems.length];
		Set<String> keySet = table.keySet();
		
		ArrayList<String> keyList = (ArrayList<String>) Sorter.asSortedList(keySet);
		
		for(String key: keyList){
			String value = table.get(key);
			String text = Tokenizer.concatWithDelim("-", String.valueOf(key), value);
			items[index--] = text;
		}
		
		int currentIndex = keyList.size();
		for(String item: currentItems) items[currentIndex++] = item;
		list.setItems(items);
		list.select(0);
	}
	
	
	public boolean isExistItem(List list, String text){
		for(String item: list.getItems()){
			if(item.equals(text)) return true;
		}
		return false;
	}
	
	public TableViewerColumn createTableViewerColumn(TableViewer tableViewer, String title, int bound, final int colNumber) {
	    final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
	    final TableColumn column = viewerColumn.getColumn();
	    column.setText(title);
	    column.setWidth(bound);
	    column.setResizable(true);
	    column.setMoveable(true);
	    return viewerColumn;
	}
	
	public void addPopupMenu(final Composite composite, String[] menuItems, SelectionListener[] listener) {
		Menu menu = new Menu(composite);
	    MenuItem[] items = new MenuItem[menuItems.length];
	    for(int i = 0; i < menuItems.length ; i++){
	    	items[i] = new MenuItem(menu, SWT.PUSH);
		    items[i].setText(menuItems[i]);	
		    items[i].addSelectionListener(listener[i]);
	    }
	    
	    composite.setMenu(menu);
	}
	
	public void addPopupMenu(final Control control, String[] menuItems, SelectionListener[] listener) {
		Menu menu = new Menu(control);
	    MenuItem[] items = new MenuItem[menuItems.length];
	    for(int i = 0; i < menuItems.length ; i++){
	    	items[i] = new MenuItem(menu, SWT.PUSH);
		    items[i].setText(menuItems[i]);	
		    items[i].addSelectionListener(listener[i]);
	    }
	    
	    control.setMenu(menu);
	}
	
	public String getToken(String str, String delim, int loc){
		StringTokenizer stk = new StringTokenizer(str, delim);
		String result = "";
		ArrayList<String> tokens = new ArrayList<String>(); 
		while(stk.hasMoreTokens()) tokens.add(stk.nextToken());
		
		if(tokens.size()>loc) result = tokens.get(loc);
		return result;
	}
	
	public String[] getToken(String str, String delim){
		String[] tokens = str.split(delim);
		
		return tokens;
	}
	
}
