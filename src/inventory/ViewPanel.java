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

//��� ��ȸ �г� 
public class ViewPanel extends JPanel implements ActionListener {

	private JScrollPane viscrolledTable;
	private JScrollPane vcscrolledTable;
	private JTable vitable;
	private JTable vctable;
	private JComboBox<String> combo;
	private String[] category = { "��ü", "��Ʈ��(61��6101)", "������(61��6103)", "�������(61��6107)", "Ƽ������(61��6109)",
			"����,����Ƿ�(61��6110)", "���ƿ� �Ƿ�(61��6111)", "��Ʈ��(61��6112)", "�����(61��6113)", "������(61��6114)", "�縻��(61��6115)",
			"�尩��(61��6116)", "�Ƿ��μ�ǰ��(61��6117)" };
	private JButton checkBtn;
	private DefaultTableModel viewIpgoModel;
	private DefaultTableModel viewChulgoModel;
	private ViewDTO vdto;
	private ViewImpl view;

	public ViewPanel() {

		// ��ư ����
		checkBtn = new JButton("��ȸ");

		// �����ڽ� ����
		combo = new JComboBox<String>(category);

		// �޺��ڽ��� ��ư ������ �г� ����
		JPanel westP = new JPanel(new BorderLayout());
		westP.add("North", combo);
		westP.add("South", checkBtn);

		// JTable�� Ÿ��Ʋ-chulgo
		Vector<String> vc = new Vector<String>();
		vc.add("��ȣ");
		vc.add("��¥");
		vc.add("��ǰ��");
		vc.add("ī�װ�");
		vc.add("����");
		vc.add("�ܰ�");
		vc.add("�Ѱ���");

		// ���̺����-chulgo
		viewChulgoModel = new DefaultTableModel(vc, 0);
		vctable = new JTable(viewChulgoModel);
		vctable.setDefaultEditor(Object.class, null);
		vcscrolledTable = new JScrollPane(vctable); // ��ũ�� �� �� �ֵ��� JScrollPane ����

		// JTable�� Ÿ��Ʋ-ipgo
		Vector<String> vi = new Vector<String>();
		vi.add("��ȣ");
		vi.add("��¥");
		vi.add("��ǰ��");
		vi.add("ī�װ�");
		vi.add("����");
		vi.add("�ܰ�");
		vi.add("�Ѱ���");

		// ���̺����-ipgo
		viewIpgoModel = new DefaultTableModel(vi, 0);
		vitable = new JTable(viewIpgoModel);
		vitable.setDefaultEditor(Object.class, null);
		viscrolledTable = new JScrollPane(vitable); // ��ũ�� �� �� �ֵ��� JScrollPane ����

		// inventoryImpl �� ���������ֱ�
		vdto = new ViewDTO();
		new InventoryImpl(vi, viewIpgoModel, vc, viewChulgoModel, vdto, vitable, vctable);

		// viewImpl�� �����ֱ�
		view = new ViewImpl(vitable, viewIpgoModel, vctable, viewChulgoModel, vdto);

		// �гο� �ֱ�
		setLayout(new BorderLayout());
		add("West", westP); // �޺��ڽ��� ��ȸ ��ư
		JPanel tableP = new JPanel(new GridLayout(2, 1));
		tableP.add(viscrolledTable);
		tableP.add(vcscrolledTable);
		add("Center", tableP);
//		add("Center", viscrolledTable);// ���̺�
//		add("South", vcscrolledTable);// ���̺�

		
		// event
		checkBtn.addActionListener(this);// ��ȸ ��ư

		// �ؽ�Ʈ���ڵ� ���� ����Ű����
		checkBtn.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ENTER) {
					String selectedItem = combo.getSelectedItem().toString();
					if (selectedItem.equals("��ü")) {
						selectedItem = null;
					}
					refresh(selectedItem);
				}
			}// keyReleased()
		});
		
		

	}// ������

	public void refresh(String searchCategory) {
		// ȭ�鿡 db����
		viewIpgoModel.setRowCount(0); // ȭ�� �ʱ�ȭ
		viewChulgoModel.setRowCount(0);
		view.selectRecordsIpgo(searchCategory);
		view.selectRecordsChulgo(searchCategory);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// �޺��ڽ����� ������ ��������� ���� �̺�Ʈ�� �۵��ϰ� ����
		if (e.getSource() == checkBtn) {
			String selectedItem = combo.getSelectedItem().toString();
			if (selectedItem.equals("��ü")) {
				selectedItem = null;
			}
			refresh(selectedItem);
			checkBtn.setFocusable(true);
			checkBtn.requestFocus();
		} // if

	}// actionPerformed(ActionEvent e)

}
