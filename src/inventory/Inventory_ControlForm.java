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

		// 컨테이너 생성 카드레이아웃으로 화면전환 가능하게
		container = getContentPane();
		container.setLayout(card);
		container.add("재고관리", jaegoPanel); // JegoPanel
		container.add("재고조회", viewPanel); // ViewPanel
		container.add("거래처관리", clientPanel);// ClientPaner

		// 메뉴바 생성
		menu = new Inventory_MenuPane();
		this.setJMenuBar(menu);

		setTitle("의류재고관리프로그램");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000, 650);
		this.setLocationRelativeTo(null); // 창 가운데 위치
		this.setVisible(true);

		// event
		menu.getNewM().addActionListener(this);
		menu.getViewM().addActionListener(this);
		menu.getInventoryM().addActionListener(this);
	}// 생성자

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == menu.getNewM()) {
			card.show(container, "재고관리");
			jaegoPanel.refresh();
		} else if (e.getSource() == menu.getViewM()) {
			card.show(container, "재고조회");
			viewPanel.refresh(null);
		} else if (e.getSource() == menu.getInventoryM()) {
			card.show(container, "거래처관리");
//			clientPanel.refresh();
		}

	}

}
