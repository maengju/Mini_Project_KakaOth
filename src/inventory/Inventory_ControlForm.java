package inventory;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class Inventory_ControlForm extends JFrame implements ActionListener {
	private Inventory_MenuPane menu;
	private CardLayout card;
	private Container container;
	private JaegoPanel jaegoPanel;
	private ViewPanel viewPanel;
	private ClientPanel clientPanel;
	// private JPanel cardP;

	public Inventory_ControlForm() {

		card = new CardLayout();

		jaegoPanel = new JaegoPanel();
		viewPanel = new ViewPanel();
		clientPanel =new ClientPanel();

		// �����̳� ���� ī�巹�̾ƿ����� ȭ����ȯ �����ϰ�
		container = getContentPane();
		container.setLayout(card);
		container.add("������", jaegoPanel); // JegoPanel
		container.add("�����ȸ", viewPanel); // ViewPanel
		container.add("�ŷ�ó����", clientPanel);// ClientPaner

		// �޴��� ����
		menu = new Inventory_MenuPane();
		this.setJMenuBar(menu);

		setTitle("�Ƿ����������α׷�");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000, 650);
		this.setLocationRelativeTo(null); // â ��� ��ġ
		this.setVisible(true);

		// event
		menu.getNewM().addActionListener(this);
		menu.getViewM().addActionListener(this);
		menu.getInventoryM().addActionListener(this);
	}// ������

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == menu.getNewM()) {
			card.show(container, "������");
			jaegoPanel.refresh();
		} else if (e.getSource() == menu.getViewM()) {
			card.show(container, "�����ȸ");
			viewPanel.refresh(null);
		} else if (e.getSource() == menu.getInventoryM()) {
			card.show(container, "�ŷ�ó����");
//			clientPanel.refresh();
		}

	}

}
