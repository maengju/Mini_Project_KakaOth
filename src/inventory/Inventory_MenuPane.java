package inventory;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Inventory_MenuPane extends JMenuBar{
	private JMenu jego,inandout;
	private JMenuItem newM,viewM,inventoryM;
	
	
	public Inventory_MenuPane() {
		jego = new JMenu("������");
		inandout = new JMenu("�ŷ�ó");
		
		newM = new JMenuItem("������");
		viewM = new JMenuItem("�����ȸ");
		inventoryM = new JMenuItem("�ŷ�ó����");
		
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
