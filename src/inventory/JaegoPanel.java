package inventory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

//재고 관리 패널
public class JaegoPanel extends JPanel implements ActionListener {
	private JLabel indexL, ipchulgoL, categoryL, userL, amountL, priceL, dateL;
	private JTextField indexT, userT, amountT, priceT, dateT;
	private String[] labels = { "indexL", "ipchulgoL", "categoryL", "userL", "amountL", "priceL", "dateL" };

	private JScrollPane scrolledTable;
	private JTable table;

	private JButton addBtn;
	private JButton updateBtn;
	private JButton delBtn;
	private JButton saveBtn;
	private JButton loadBtn;

	String[] category;

	JComboBox<String> combo;

	JRadioButton ipgo, chulgo;

	private DefaultTableModel model;
	private InventoryImpl inventory;

	public JaegoPanel() {

		this.setLayout(new BorderLayout(10, 10));
		// ------------------------------- 서쪽 패널 -------------------------------
		// 라벨생성
		indexL = new JLabel("번호", SwingConstants.CENTER);
		ipchulgoL = new JLabel("입출고 상태", SwingConstants.CENTER);
		categoryL = new JLabel("카테고리", SwingConstants.CENTER);
		userL = new JLabel("상품명", SwingConstants.CENTER);
		amountL = new JLabel("수량", SwingConstants.CENTER);
		priceL = new JLabel("단가", SwingConstants.CENTER);
		dateL = new JLabel("날짜", SwingConstants.CENTER);

		// 텍스트 생성
		indexT = new JTextField("", 22);
		userT = new JTextField("", 22);
		amountT = new JTextField("", 22);
		priceT = new JTextField("", 22);
		dateT = new JTextField("", 22);
//		indexT.setEnabled(false);
		// 버튼 생성
		// 라디오 버튼 생성
		ipgo = new JRadioButton("입고");
		chulgo = new JRadioButton("출고");

		JPanel radioP = new JPanel();// radio 패널 생성
		radioP.add(ipgo);
		radioP.add(chulgo);
		radioP.setLayout(new GridLayout(1, 2));
		ipgo.setSelected(true);// 특정 라디오 버튼 눌러져있도록

		ButtonGroup radioG = new ButtonGroup();// 라디오 버튼을 그룹화 하기위한 객체 생성
		radioG.add(ipgo);// 그룹에 라디오 버튼 포함시킨다.
		radioG.add(chulgo);

		// 콤보박스 생성 JComboBox<String>
		category = new String[] { "코트류(61류6101)", "셔츠류(61류6103)", "언더팬츠(61류6107)", "티셔츠류(61류6109)", "저지,가디건류(61류6110)",
				"유아용 의류(61류6111)", "슈트류(61류6112)", "방수류(61류6113)", "제복류(61류6114)", "양말류(61류6115)", "장갑류(61류6116)",
				"의류부속품류(61류6117)" };
		combo = new JComboBox<String>(category);
		JPanel comboP = new JPanel();// combo 패널 생성
		comboP.add(combo);

		// 라벨패널생성
		JPanel leftP = new JPanel(new GridLayout(15, 1, 5, 5));
		leftP.add(indexL);
		leftP.add(indexT);
		leftP.add(ipchulgoL);
		leftP.add(ipgo);
		leftP.add(chulgo);
		leftP.add(categoryL);
		leftP.add(combo);
		leftP.add(userL);
		leftP.add(userT);
		leftP.add(amountL);
		leftP.add(amountT);
		leftP.add(priceL);
		leftP.add(priceT);
		leftP.add(dateL);
		leftP.add(dateT);

		// 왼쪽패널 모음
		Panel westP = new Panel(new FlowLayout(FlowLayout.CENTER));
		westP.add(leftP);
		westP.add(radioP);
		westP.add(comboP);

		// ------------------------------- 아래 버튼 -------------------------------
		addBtn = new JButton("추가");
		updateBtn = new JButton("수정");
		delBtn = new JButton("삭제");
		saveBtn = new JButton("저장");
		loadBtn = new JButton("불러오기");

		// JTable의 타이틀
		Vector<String> vector = new Vector<String>();
		vector.add("번호");
		vector.add("입출고 상태");
		vector.add("카테고리");
		vector.add("상품명");
		vector.add("수량");
		vector.add("단가");
		vector.add("날짜");

		// 테이블
		model = new DefaultTableModel(vector, 0); // header추가, 행은 0개 지정

		table = new JTable(model);
		table.setDefaultEditor(Object.class, null);
		scrolledTable = new JScrollPane(table); // 스크롤 될 수 있도록 JScrollPane 적용

		// 아래쪽 버튼패널
		JPanel bottomP = new JPanel(new GridLayout(1, 5, 10, 10));

		bottomP.add(addBtn);
		bottomP.add(updateBtn);
		bottomP.add(delBtn);
		bottomP.add(saveBtn);
		bottomP.add(loadBtn);

		// 업데이트버튼 비활성화
		updateBtn.setEnabled(false);

		// 변수 생성자로 보내주기
		inventory = new InventoryImpl(table, labels, indexT, radioG, combo, userT, amountT, priceT, dateT, category,
				model);// JaegoImpl에

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

		// 데이터

		setLayout(new BorderLayout());
		add("West", westP);
		add("Center", scrolledTable);
		add("South", bottomP);

		// Event추가
		events();
		// 초기화
		refresh();

	}// 생성자

	public void refresh() {
		// 화면DB불러오기
		model.setRowCount(0);
		inventory.selectRecords();
	}

	private void events() {
		// 버튼액션정리
		addBtn.addActionListener(this);// 완료
		updateBtn.addActionListener(this);
		delBtn.addActionListener(this);// 완료
		saveBtn.addActionListener(this);
		loadBtn.addActionListener(this);

		// 텍스트상자들 위에 엔터키적용
		indexT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ENTER) {
					inventory.addRecord();
				}
			}// keyReleased()
		});
		userT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ENTER) {
					inventory.addRecord();
				}
			}// keyReleased()
		});
		amountT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ENTER) {
					inventory.addRecord();
				}
			}// keyReleased()
		});
		priceT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ENTER) {
					inventory.addRecord();
				}
			}// keyReleased()
		});
		dateT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ENTER) {
					inventory.addRecord();
				}
			}// keyReleased()
		});

		// 마우스클릭시 테이블모델 텍스트상자에 넣기
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object src = e.getSource();
				if (src == table) {
					int row = table.getSelectedRow();
					inventory.selectRecord(row);

					indexT.setEditable(false);
					updateBtn.setEnabled(true);
				}
			}// mouseClicked()
		});

	}// events();

	// ActionListener Overrides
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addBtn) {
			System.out.println("추가버튼");
			inventory.addRecord();
			inventory.refresh(model);

		} else if (e.getSource() == updateBtn) {
			System.out.println("수정버튼");
			inventory.updateRecord();
			inventory.refresh(model);

		} else if (e.getSource() == delBtn) {
			System.out.println("삭제버튼");
			int[] selected = table.getSelectedRows();
			inventory.deleteRecord(selected);
			inventory.refresh(model);

		} else if (e.getSource() == saveBtn) {
			System.out.println("저장버튼");
			List<InventoryDTO> jaegoList = inventory.returnJaegoList();
			inventory.save(jaegoList);

		} else if (e.getSource() == loadBtn) {
			System.out.println("불러오기버튼");

			inventory.load();
		}
	}

}
