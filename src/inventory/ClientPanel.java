package inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

//거래처
public class ClientPanel extends JPanel implements ActionListener {
	private JLabel noL, clientL, pursonL, emailL, phoneNumL, addressL;
	private JTextField noT, clientT, pursonT, emailT, phoneNumT, addressT;
	private JButton addBtn, updateBtn, searchBtn, delBtn , allBtn;

	private JScrollPane scrolledTable;
	private JTable table;

	private DefaultTableModel model;
	private InventoryImpl inventory;

	public ClientPanel() {
		this.setLayout(new BorderLayout(10, 10));

		// Label생성
		noL = new JLabel("순서", SwingConstants.CENTER);
		clientL = new JLabel("거래처", SwingConstants.CENTER);
		pursonL = new JLabel("담당자", SwingConstants.CENTER);
		emailL = new JLabel("담당자 이메일", SwingConstants.CENTER);
		phoneNumL = new JLabel("거래처 전화번호", SwingConstants.CENTER);
		addressL = new JLabel("거래처 주소", SwingConstants.CENTER);

		// TextField생성
		noT = new JTextField("", 22);
		clientT = new JTextField("", 22);
		pursonT = new JTextField("", 22);
		emailT = new JTextField("", 22);
		phoneNumT = new JTextField("", 22);
		addressT = new JTextField("", 22);

		// Button생성
		addBtn = new JButton("추가");
		updateBtn = new JButton("수정");
		searchBtn = new JButton("검색");
		delBtn = new JButton("삭제");
		allBtn = new JButton("전체보기");
		// Label과 textfield 추가할 패널
		JPanel westP = new JPanel(new GridLayout(16, 1, 5, 5));
		westP.add(noL);
		westP.add(noT);

		westP.add(clientL);
		westP.add(clientT);

		westP.add(pursonL);
		westP.add(pursonT);

		westP.add(emailL);
		westP.add(emailT);

		westP.add(phoneNumL);
		westP.add(phoneNumT);

		westP.add(addressL);
		westP.add(addressT);

		// table 타이틀
		Vector<String> vector = new Vector<String>();
		vector.add("순서");
		vector.add("거래처");
		vector.add("담당자");
		vector.add("담당자 이메일");
		vector.add("전화번호");
		vector.add("주소");

		// 테이블
		model = new DefaultTableModel(vector, 0);

		table = new JTable(model);
		table.setDefaultEditor(Object.class, null);
		scrolledTable = new JScrollPane(table);// 스크롤 될 수 있도록 JScrollPane 적용

		// ButtonP
		JPanel buttonP = new JPanel(new GridLayout(1, 3, 10, 10));
		buttonP.add(addBtn);
		buttonP.add(updateBtn);
		buttonP.add(searchBtn);
		buttonP.add(delBtn);
		buttonP.add(allBtn);

		// update버튼 비활성화
		updateBtn.setEnabled(false);
		// 화면 구성
		setLayout(new BorderLayout());
		add("West", westP);
		add("Center", scrolledTable);
		add("South", buttonP);

		// 생성자로 보내주기
		inventory = new InventoryImpl(table, noT, clientT, pursonT, emailT, phoneNumT, addressT);

		// event추가
		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		searchBtn.addActionListener(this);
		delBtn.addActionListener(this);
		allBtn.addActionListener(this);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object src = e.getSource();
				if (src == table) {
					int row = table.getSelectedRow();
					inventory.clientclickview(row);

					noT.setEditable(false);
					updateBtn.setEnabled(true);
				}
			}// mouseClicked()
		});

		// 화면DB불러오기
		model.setRowCount(0);
		inventory.clientview();
		
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)

		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정

		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmSchedule = table.getColumnModel();

		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
		tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
		
		

	}// 생성자

	@Override // actionListener
	public void actionPerformed(ActionEvent e) {
		boolean indexcheck = inventory.indexCheck(noT.getText());

		if (e.getSource() == addBtn) {
			if (noT.getText().equals("") || clientT.getText().equals("") || pursonT.getText().equals("")
					|| emailT.getText().equals("") || phoneNumT.getText().equals("") || addressT.getText().equals("")) {

				JOptionPane.showMessageDialog(this, "빈칸을 채워주세요");
				return;
			} // 빈칸이 있을때 다시 입력하게 보냄

			else if (indexcheck) {
				JOptionPane.showMessageDialog(this, "이미 사용중인 번호 입니다");
				noT.setText("");
				return;
			}
			inventory.clientadd();
			model.setRowCount(0);
			inventory.clientview();
			
		} else if (e.getSource() == updateBtn) {

			inventory.clientupdate();
			updateBtn.setEnabled(false);
			model.setRowCount(0);
			inventory.clientview();

		}else if ( e.getSource() == searchBtn) {
			
			inventory.clientSearch();
			
		} else if (e.getSource() == delBtn) {
			inventory.clientDel(noT.getText());
			model.setRowCount(0);
			inventory.clientview();

		} else if (e.getSource() == allBtn) {
			inventory.clientview();
		}

	}// actionPerformed(ActionEvent e)

}
