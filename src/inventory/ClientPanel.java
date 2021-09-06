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

//�ŷ�ó
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

		// Label����
		noL = new JLabel("����", SwingConstants.CENTER);
		clientL = new JLabel("�ŷ�ó", SwingConstants.CENTER);
		pursonL = new JLabel("�����", SwingConstants.CENTER);
		emailL = new JLabel("����� �̸���", SwingConstants.CENTER);
		phoneNumL = new JLabel("�ŷ�ó ��ȭ��ȣ", SwingConstants.CENTER);
		addressL = new JLabel("�ŷ�ó �ּ�", SwingConstants.CENTER);

		// TextField����
		noT = new JTextField("", 22);
		clientT = new JTextField("", 22);
		pursonT = new JTextField("", 22);
		emailT = new JTextField("", 22);
		phoneNumT = new JTextField("", 22);
		addressT = new JTextField("", 22);

		// Button����
		addBtn = new JButton("�߰�");
		updateBtn = new JButton("����");
		searchBtn = new JButton("�˻�");
		delBtn = new JButton("����");
		allBtn = new JButton("��ü����");
		// Label�� textfield �߰��� �г�
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

		// table Ÿ��Ʋ
		Vector<String> vector = new Vector<String>();
		vector.add("����");
		vector.add("�ŷ�ó");
		vector.add("�����");
		vector.add("����� �̸���");
		vector.add("��ȭ��ȣ");
		vector.add("�ּ�");

		// ���̺�
		model = new DefaultTableModel(vector, 0);

		table = new JTable(model);
		table.setDefaultEditor(Object.class, null);
		scrolledTable = new JScrollPane(table);// ��ũ�� �� �� �ֵ��� JScrollPane ����

		// ButtonP
		JPanel buttonP = new JPanel(new GridLayout(1, 3, 10, 10));
		buttonP.add(addBtn);
		buttonP.add(updateBtn);
		buttonP.add(searchBtn);
		buttonP.add(delBtn);
		buttonP.add(allBtn);

		// update��ư ��Ȱ��ȭ
		updateBtn.setEnabled(false);
		// ȭ�� ����
		setLayout(new BorderLayout());
		add("West", westP);
		add("Center", scrolledTable);
		add("South", buttonP);

		// �����ڷ� �����ֱ�
		inventory = new InventoryImpl(table, noT, clientT, pursonT, emailT, phoneNumT, addressT);

		// event�߰�
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

		// ȭ��DB�ҷ�����
		model.setRowCount(0);
		inventory.clientview();
		
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
		
		

	}// ������

	@Override // actionListener
	public void actionPerformed(ActionEvent e) {
		boolean indexcheck = inventory.indexCheck(noT.getText());

		if (e.getSource() == addBtn) {
			if (noT.getText().equals("") || clientT.getText().equals("") || pursonT.getText().equals("")
					|| emailT.getText().equals("") || phoneNumT.getText().equals("") || addressT.getText().equals("")) {

				JOptionPane.showMessageDialog(this, "��ĭ�� ä���ּ���");
				return;
			} // ��ĭ�� ������ �ٽ� �Է��ϰ� ����

			else if (indexcheck) {
				JOptionPane.showMessageDialog(this, "�̹� ������� ��ȣ �Դϴ�");
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
