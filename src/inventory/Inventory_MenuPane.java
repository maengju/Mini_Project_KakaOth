package inventory;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Inventory_MenuPane extends JMenuBar{
	private JMenu jego,inandout;
	private JMenuItem newM,viewM,inventoryM;
	
	
	public Inventory_MenuPane() {
		jego = new JMenu("재고관리");
		inandout = new JMenu("거래처");
		
		newM = new JMenuItem("재고관리");
		viewM = new JMenuItem("재고조회");
		inventoryM = new JMenuItem("거래처관리");
		
		jego.add(newM);
		jego.add(viewM);
		inandout.add(inventoryM);
		
		add(jego);
		add(inandout);
	}

	public JMenuItem getNewM() {
		return newM;
	}
	public JMenuItem getViewM() {
		return viewM;
	}
	public JMenuItem getInventoryM() {
		return inventoryM;
	}


	
}
