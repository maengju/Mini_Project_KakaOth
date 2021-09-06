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

//��� ���� �г�
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
		// ------------------------------- ���� �г� -------------------------------
		// �󺧻���
		indexL = new JLabel("��ȣ", SwingConstants.CENTER);
		ipchulgoL = new JLabel("����� ����", SwingConstants.CENTER);
		categoryL = new JLabel("ī�װ�", SwingConstants.CENTER);
		userL = new JLabel("��ǰ��", SwingConstants.CENTER);
		amountL = new JLabel("����", SwingConstants.CENTER);
		priceL = new JLabel("�ܰ�", SwingConstants.CENTER);
		dateL = new JLabel("��¥", SwingConstants.CENTER);

		// �ؽ�Ʈ ����
		indexT = new JTextField("", 22);
		userT = new JTextField("", 22);
		amountT = new JTextField("", 22);
		priceT = new JTextField("", 22);
		dateT = new JTextField("", 22);
//		indexT.setEnabled(false);
		// ��ư ����
		// ���� ��ư ����
		ipgo = new JRadioButton("�԰�");
		chulgo = new JRadioButton("���");

		JPanel radioP = new JPanel();// radio �г� ����
		radioP.add(ipgo);
		radioP.add(chulgo);
		radioP.setLayout(new GridLayout(1, 2));
		ipgo.setSelected(true);// Ư�� ���� ��ư �������ֵ���

		ButtonGroup radioG = new ButtonGroup();// ���� ��ư�� �׷�ȭ �ϱ����� ��ü ����
		radioG.add(ipgo);// �׷쿡 ���� ��ư ���Խ�Ų��.
		radioG.add(chulgo);

		// �޺��ڽ� ���� JComboBox<String>
		category = new String[] { "��Ʈ��(61��6101)", "������(61��6103)", "�������(61��6107)", "Ƽ������(61��6109)", "����,����Ƿ�(61��6110)",
				"���ƿ� �Ƿ�(61��6111)", "��Ʈ��(61��6112)", "�����(61��6113)", "������(61��6114)", "�縻��(61��6115)", "�尩��(61��6116)",
				"�Ƿ��μ�ǰ��(61��6117)" };
		combo = new JComboBox<String>(category);
		JPanel comboP = new JPanel();// combo �г� ����
		comboP.add(combo);

		// ���гλ���
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

		// �����г� ����
		Panel westP = new Panel(new FlowLayout(FlowLayout.CENTER));
		westP.add(leftP);
		westP.add(radioP);
		westP.add(comboP);

		// ------------------------------- �Ʒ� ��ư -------------------------------
		addBtn = new JButton("�߰�");
		updateBtn = new JButton("����");
		delBtn = new JButton("����");
		saveBtn = new JButton("����");
		loadBtn = new JButton("�ҷ�����");

		// JTable�� Ÿ��Ʋ
		Vector<String> vector = new Vector<String>();
		vector.add("��ȣ");
		vector.add("����� ����");
		vector.add("ī�װ�");
		vector.add("��ǰ��");
		vector.add("����");
		vector.add("�ܰ�");
		vector.add("��¥");

		// ���̺�
		model = new DefaultTableModel(vector, 0); // header�߰�, ���� 0�� ����

		table = new JTable(model);
		table.setDefaultEditor(Object.class, null);
		scrolledTable = new JScrollPane(table); // ��ũ�� �� �� �ֵ��� JScrollPane ����

		// �Ʒ��� ��ư�г�
		JPanel bottomP = new JPanel(new GridLayout(1, 5, 10, 10));

		bottomP.add(addBtn);
		bottomP.add(updateBtn);
		bottomP.add(delBtn);
		bottomP.add(saveBtn);
		bottomP.add(loadBtn);

		// ������Ʈ��ư ��Ȱ��ȭ
		updateBtn.setEnabled(false);

		// ���� �����ڷ� �����ֱ�
		inventory = new InventoryImpl(table, labels, indexT, radioG, combo, userT, amountT, priceT, dateT, category,
				model);// JaegoImpl��

		// DefaultTableCellHeaderRenderer ���� (��� ������ ����)

		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		// DefaultTableCellHeaderRenderer�� ������ ��� ���ķ� ����

		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// ������ ���̺��� ColumnModel�� ������
		TableColumnModel tcmSchedule = table.getColumnModel();

		// �ݺ����� �̿��Ͽ� ���̺��� ��� ���ķ� ����
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}

		// ������

		setLayout(new BorderLayout());
		add("West", westP);
		add("Center", scrolledTable);
		add("South", bottomP);

		// Event�߰�
		events();
		// �ʱ�ȭ
		refresh();

	}// ������

	public void refresh() {
		// ȭ��DB�ҷ�����
		model.setRowCount(0);
		inventory.selectRecords();
	}

	private void events() {
		// ��ư�׼�����
		addBtn.addActionListener(this);// �Ϸ�
		updateBtn.addActionListener(this);
		delBtn.addActionListener(this);// �Ϸ�
		saveBtn.addActionListener(this);
		loadBtn.addActionListener(this);

		// �ؽ�Ʈ���ڵ� ���� ����Ű����
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

		// ���콺Ŭ���� ���̺�� �ؽ�Ʈ���ڿ� �ֱ�
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
			System.out.println("�߰���ư");
			inventory.addRecord();
			inventory.refresh(model);

		} else if (e.getSource() == updateBtn) {
			System.out.println("������ư");
			inventory.updateRecord();
			inventory.refresh(model);

		} else if (e.getSource() == delBtn) {
			System.out.println("������ư");
			int[] selected = table.getSelectedRows();
			inventory.deleteRecord(selected);
			inventory.refresh(model);

		} else if (e.getSource() == saveBtn) {
			System.out.println("�����ư");
			List<InventoryDTO> jaegoList = inventory.returnJaegoList();
			inventory.save(jaegoList);

		} else if (e.getSource() == loadBtn) {
			System.out.println("�ҷ������ư");

			inventory.load();
		}
	}

}
