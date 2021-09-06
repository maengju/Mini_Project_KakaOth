package inventory;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

//재고 조회 패널 
public class ViewPanel extends JPanel implements ActionListener {

	private JScrollPane viscrolledTable;
	private JScrollPane vcscrolledTable;
	private JTable vitable;
	private JTable vctable;
	private JComboBox<String> combo;
	private String[] category = { "전체", "코트류(61류6101)", "셔츠류(61류6103)", "언더팬츠(61류6107)", "티셔츠류(61류6109)",
			"저지,가디건류(61류6110)", "유아용 의류(61류6111)", "슈트류(61류6112)", "방수류(61류6113)", "제복류(61류6114)", "양말류(61류6115)",
			"장갑류(61류6116)", "의류부속품류(61류6117)" };
	private JButton checkBtn;
	private DefaultTableModel viewIpgoModel;
	private DefaultTableModel viewChulgoModel;
	private ViewDTO vdto;
	private ViewImpl view;

	public ViewPanel() {

		// 버튼 생성
		checkBtn = new JButton("조회");

		// 콤포박스 생성
		combo = new JComboBox<String>(category);

		// 콤보박스와 버튼 삽입할 패널 생성
		JPanel westP = new JPanel(new BorderLayout());
		westP.add("North", combo);
		westP.add("South", checkBtn);

		// JTable의 타이틀-chulgo
		Vector<String> vc = new Vector<String>();
		vc.add("번호");
		vc.add("날짜");
		vc.add("상품명");
		vc.add("카테고리");
		vc.add("수량");
		vc.add("단가");
		vc.add("총가격");

		// 테이블생성-chulgo
		viewChulgoModel = new DefaultTableModel(vc, 0);
		vctable = new JTable(viewChulgoModel);
		vctable.setDefaultEditor(Object.class, null);
		vcscrolledTable = new JScrollPane(vctable); // 스크롤 될 수 있도록 JScrollPane 적용

		// JTable의 타이틀-ipgo
		Vector<String> vi = new Vector<String>();
		vi.add("번호");
		vi.add("날짜");
		vi.add("상품명");
		vi.add("카테고리");
		vi.add("수량");
		vi.add("단가");
		vi.add("총가격");

		// 테이블생성-ipgo
		viewIpgoModel = new DefaultTableModel(vi, 0);
		vitable = new JTable(viewIpgoModel);
		vitable.setDefaultEditor(Object.class, null);
		viscrolledTable = new JScrollPane(vitable); // 스크롤 될 수 있도록 JScrollPane 적용

		// inventoryImpl 에 변수보내주기
		vdto = new ViewDTO();
		new InventoryImpl(vi, viewIpgoModel, vc, viewChulgoModel, vdto, vitable, vctable);

		// viewImpl에 보내주기
		view = new ViewImpl(vitable, viewIpgoModel, vctable, viewChulgoModel, vdto);

		// 패널에 넣기
		setLayout(new BorderLayout());
		add("West", westP); // 콤보박스랑 조회 버튼
		JPanel tableP = new JPanel(new GridLayout(2, 1));
		tableP.add(viscrolledTable);
		tableP.add(vcscrolledTable);
		add("Center", tableP);
//		add("Center", viscrolledTable);// 테이블
//		add("South", vcscrolledTable);// 테이블

		
		// event
		checkBtn.addActionListener(this);// 조회 버튼

		// 텍스트상자들 위에 엔터키적용
		checkBtn.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ENTER) {
					String selectedItem = combo.getSelectedItem().toString();
					if (selectedItem.equals("전체")) {
						selectedItem = null;
					}
					refresh(selectedItem);
				}
			}// keyReleased()
		});
		
		

	}// 생성자

	public void refresh(String searchCategory) {
		// 화면에 db생성
		viewIpgoModel.setRowCount(0); // 화면 초기화
		viewChulgoModel.setRowCount(0);
		view.selectRecordsIpgo(searchCategory);
		view.selectRecordsChulgo(searchCategory);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 콤보박스에서 무엇을 골랐는지에 따라 이벤트가 작동하게 생성
		if (e.getSource() == checkBtn) {
			String selectedItem = combo.getSelectedItem().toString();
			if (selectedItem.equals("전체")) {
				selectedItem = null;
			}
			refresh(selectedItem);
			checkBtn.setFocusable(true);
			checkBtn.requestFocus();
		} // if

	}// actionPerformed(ActionEvent e)

}
