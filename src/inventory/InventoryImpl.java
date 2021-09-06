package inventory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class InventoryImpl implements Inventory, Serializable {
	private JTable table;
	private String[] labels;
	private JTextField indexT, productNameT, amountT, priceT, dateT;
	private ButtonGroup radioG;
	private JComboBox combo;
	private InventoryDAO dao;
	boolean index_check = false;
	private List<InventoryDTO> list;
	private List<ViewDTO> viewIpgoList;
	private List<ViewDTO> viewChulgoList;
	private String[] category;
	private Vector<String> vi;
	private DefaultTableModel viewIpgoModel;
	private Vector<String> vc;
	private DefaultTableModel viewChulgoModel;
	private ViewDTO vdto;
	private JTable vitable, vctable;
	private DefaultTableModel model;
	DecimalFormat df = new DecimalFormat("###,###.##");

	// =========================================================================================
	// clientPanel
	JTable clientTable;
	JTextField noT, clientT, pursonT, emailT, phoneNumT, addressT;

	// =========================================================================================
	// jaego���� �����°�
	public InventoryImpl(JTable table, String[] labels, JTextField indexT, ButtonGroup radioG, JComboBox combo,
			JTextField productNameT, JTextField amountT, JTextField priceT, JTextField dateT, String[] category,
			DefaultTableModel model) {
		this.table = table;
		this.labels = labels;
		this.indexT = indexT;
		this.radioG = radioG;
		this.combo = combo;
		this.productNameT = productNameT;
		this.amountT = amountT;
		this.priceT = priceT;
		this.dateT = dateT;
		this.category = category;
		this.viewIpgoModel = new DefaultTableModel(vi, 0);
		this.viewChulgoModel = new DefaultTableModel(vc, 0);
		this.model = model;
		vdto = new ViewDTO();
		vi = new Vector<String>();
		vc = new Vector<String>();
		dao = new InventoryDAO();
		list = new ArrayList<InventoryDTO>();

	}

//========================================================================================================

	// client panel ������ ����

	public InventoryImpl(JTable clientTable, JTextField noT, JTextField clientT, JTextField pursonT, JTextField emailT,
			JTextField phoneNumT, JTextField addressT) {
		dao = new InventoryDAO();

		this.clientTable = clientTable;
		this.noT = noT;
		this.clientT = clientT;
		this.pursonT = pursonT;
		this.emailT = emailT;
		this.phoneNumT = phoneNumT;
		this.addressT = addressT;
	}
	// client panel ������

	// ===============================================================================================================================
//view���������°�
	public InventoryImpl(Vector<String> vi, DefaultTableModel viewIpgoModel, Vector<String> vc,
			DefaultTableModel viewChulgoModel, ViewDTO vdto, JTable vitable, JTable vctable) {
		this.vi = vi;
		this.viewIpgoModel = viewIpgoModel;
		this.vc = vc;
		this.viewChulgoModel = viewChulgoModel;
		this.vdto = vdto;
		this.vitable = vitable;
		this.vctable = vctable;

	}

	@Override
	public void addRecord() {
		// ���ڵ忡 �Է¹��� ������ �ӽ�����迭 ����
		// ���� �ִ��� Ȯ�� �� ������� ���̾�α� "��ĭ�� ä���ּ���"
		// ���� ������� ���ڵ忡 �ϳ��� ��� DTO���� , DAO����
		// ���̺� �����ְ� �ʵ����ֱ�

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		String[] record = new String[model.getColumnCount()];
		// jaegopanel
		String indexStr = indexT.getText();
		String radioStatusStr = getSelectedButtonFromRadio(radioG);
		String comboCategoryStr = combo.getSelectedItem().toString();
		String productNameStr = productNameT.getText();
		String amountStr = amountT.getText();
		String priceStr = priceT.getText();
		String dateStr = dateT.getText();

		String invalidInput = checkValidInput(labels, new String[] { indexStr, radioStatusStr, comboCategoryStr,
				productNameStr, amountStr, priceStr, dateStr });
		if (invalidInput != null) {
			JOptionPane.showMessageDialog(null, "��ĭ�� ä���ּ���.");// TODO: �˸� �޽��� ���
		} else if (idexCheck(indexStr)) {
			JOptionPane.showMessageDialog(null, "index�� �����մϴ�.");
		} else {
			record[0] = indexStr;
			record[1] = radioStatusStr;
			record[2] = comboCategoryStr;
			record[3] = productNameStr;
			record[4] = amountStr;
			record[5] = priceStr;
			record[6] = dateStr;

			InventoryDTO dto = new InventoryDTO();
			dto.setIndexT(indexStr);
			dto.setRadioStatus(radioStatusStr);
			dto.setComboCategory(comboCategoryStr);
			dto.setProductNameT(productNameStr);
			dto.setAmountT(amountStr);
			dto.setPriceT(priceStr);
			dto.setDateT(dateStr);

			dao.insert(dto);// �����ͺ��̽� ����

			// insert
			model.addRow(record);// ���̺� ����

			// viewpanel ����Ʈ ��� �� DB����, ���̺� �߰�

			if (radioStatusStr.equals("�԰�")) {
				int amountIpgoInt = Integer.parseInt(amountStr);
				double priceIpgoDouble = Double.parseDouble(priceStr);
				double totalIpgoPrice = priceIpgoDouble * amountIpgoInt;

				vdto.setIndexT(indexStr);
				vdto.setDateT(dateStr);
				vdto.setProductNameIpgoT(productNameStr);
				vdto.setComboIpgoCategory(comboCategoryStr);
				vdto.setAmountIpgoInt(amountIpgoInt);
				vdto.setPriceIpgoT(priceIpgoDouble);
				vdto.setTotalIpgoPrice(totalIpgoPrice);

				viewIpgoList = new ArrayList<ViewDTO>();
				viewIpgoList.add(vdto);
				dao.insertIpgo(vdto);

				// �𵨾ȿ� �ִ°�(������) �� ������. - �Ⱦ��� ������ �ִ°ű��� �� ��´�.

				for (ViewDTO vdto : viewIpgoList) {
					// ���ٴ� ���Ϳ� �־�� �Ѵ�. list�δ� ������
					vi.add(vdto.getIndexT());
					vi.add(vdto.getDateT());
					vi.add(vdto.getProductNameIpgoT());
					vi.add(vdto.getComboIpgoCategory());
					vi.add(vdto.getAmountIpgoInt() + "");
					vi.add(vdto.getPriceIpgoT() + "");
					vi.add(vdto.getTotalIpgoPrice() + ""); // String.format - �Ҽ��� 2°�ڸ�(���ڿ�)

					viewIpgoModel.addRow(vi);// ���� ����
				} // for

			} else if (radioStatusStr.equals("���")) {
				int amountChulgoInt = Integer.parseInt(amountStr);
				double priceChulgoDouble = Double.parseDouble(priceStr);
				double totalChulgoPrice = priceChulgoDouble * amountChulgoInt;

				vdto.setIndexT(indexStr);
				vdto.setDateT(dateStr);
				vdto.setProductNameChulgoT(productNameStr);
				vdto.setComboChulgoCategory(comboCategoryStr);
				vdto.setAmountChulgoInt(amountChulgoInt);
				vdto.setPriceChulgoT(priceChulgoDouble);
				vdto.setTotalChulgoPrice(totalChulgoPrice);

				viewChulgoList = new ArrayList<ViewDTO>();
				viewChulgoList.add(vdto);
				dao.insertChulgo(vdto);

				// �𵨾ȿ� �ִ°�(������) �� ������. - �Ⱦ��� ������ �ִ°ű��� �� ��´�.

				for (ViewDTO vdto : viewChulgoList) {
					// ���ٴ� ���Ϳ� �־�� �Ѵ�. list�δ� ������
					vc.add(vdto.getIndexT());
					vc.add(vdto.getDateT());
					vc.add(vdto.getProductNameChulgoT());
					vc.add(vdto.getComboChulgoCategory());
					vc.add(vdto.getAmountChulgoInt() + "");
					vc.add(vdto.getPriceChulgoT() + "");
					vc.add(vdto.getTotalChulgoPrice() + ""); // String.format - �Ҽ���
																// 2°�ڸ�(���ڿ�)

					viewChulgoModel.addRow(vc);// ���� ����
				} // for

			}
		}
	}

	private void addRecordList(List<InventoryDTO> loadlist) {
		// ���ڵ忡 �Է¹��� ������ �ӽ�����迭 ����
		// ���� �ִ��� Ȯ�� �� ������� ���̾�α� "��ĭ�� ä���ּ���"
		// ���� ������� ���ڵ忡 �ϳ��� ��� DTO���� , DAO����
		// ���̺� �����ְ� �ʵ����ֱ�
		deleteAllJaego(model);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		String[] record = new String[model.getColumnCount()];
		// jaegopanel
		for (InventoryDTO dto : loadlist) {
			Vector<String> v = new Vector<>();
			v.add(dto.getIndexT());
			v.add(dto.getRadioStatus());
			v.add(dto.getComboCategory());
			v.add(dto.getProductNameT());
			v.add(dto.getAmountT());
			v.add(dto.getPriceT());
			v.add(dto.getDateT());

			model.addRow(v);

			dto.setIndexT(dto.getIndexT());
			dto.setRadioStatus(dto.getRadioStatus());
			dto.setComboCategory(dto.getComboCategory());
			dto.setProductNameT(dto.getProductNameT());
			dto.setAmountT(dto.getAmountT());
			dto.setPriceT(dto.getPriceT());
			dto.setDateT(dto.getDateT());

			dao.insert(dto);
			// viewpanel ����Ʈ ��� �� DB����, ���̺� �߰�

			if (dto.getRadioStatus().equals("�԰�")) {
				int amountIpgoInt = Integer.parseInt(dto.getAmountT());
				double priceIpgoDouble = Double.parseDouble(dto.getPriceT());
				double totalIpgoPrice = priceIpgoDouble * amountIpgoInt;

				vdto.setIndexT(dto.getIndexT());
				vdto.setDateT(dto.getDateT());
				vdto.setProductNameIpgoT(dto.getProductNameT());
				vdto.setComboIpgoCategory(dto.getComboCategory());
				vdto.setAmountIpgoInt(amountIpgoInt);
				vdto.setPriceIpgoT(priceIpgoDouble);
				vdto.setTotalIpgoPrice(totalIpgoPrice);

				viewIpgoList = new ArrayList<ViewDTO>();
				viewIpgoList.add(vdto);
				dao.insertIpgo(vdto);

				// �𵨾ȿ� �ִ°�(������) �� ������. - �Ⱦ��� ������ �ִ°ű��� �� ��´�.

				for (ViewDTO vdto : viewIpgoList) {
					// ���ٴ� ���Ϳ� �־�� �Ѵ�. list�δ� ������
					vi.add(vdto.getIndexT());
					vi.add(vdto.getDateT());
					vi.add(vdto.getProductNameIpgoT());
					vi.add(vdto.getComboIpgoCategory());
					vi.add(vdto.getAmountIpgoInt() + "");
					vi.add(vdto.getPriceIpgoT() + "");
					vi.add(vdto.getTotalIpgoPrice() + ""); // String.format - �Ҽ��� 2°�ڸ�(���ڿ�)

					viewIpgoModel.addRow(vi);// ���� ����
				} // for

			} else if (dto.getRadioStatus().equals("���")) {
				int amountChulgoInt = Integer.parseInt(dto.getAmountT());
				double priceChulgoDouble = Double.parseDouble(dto.getPriceT());
				double totalChulgoPrice = priceChulgoDouble * amountChulgoInt;

				vdto.setIndexT(dto.getIndexT());
				vdto.setDateT(dto.getDateT());
				vdto.setProductNameChulgoT(dto.getProductNameT());
				vdto.setComboChulgoCategory(dto.getComboCategory());
				vdto.setAmountChulgoInt(amountChulgoInt);
				vdto.setPriceChulgoT(priceChulgoDouble);
				vdto.setTotalChulgoPrice(totalChulgoPrice);

				viewChulgoList = new ArrayList<ViewDTO>();
				viewChulgoList.add(vdto);
				dao.insertChulgo(vdto);

				// �𵨾ȿ� �ִ°�(������) �� ������. - �Ⱦ��� ������ �ִ°ű��� �� ��´�.

				for (ViewDTO vdto : viewChulgoList) {
					// ���ٴ� ���Ϳ� �־�� �Ѵ�. list�δ� ������
					vc.add(vdto.getIndexT());
					vc.add(vdto.getDateT());
					vc.add(vdto.getProductNameChulgoT());
					vc.add(vdto.getComboChulgoCategory());
					vc.add(vdto.getAmountChulgoInt() + "");
					vc.add(vdto.getPriceChulgoT() + "");
					vc.add(vdto.getTotalChulgoPrice() + ""); // String.format - �Ҽ���
					// 2°�ڸ�(���ڿ�)

					viewChulgoModel.addRow(vc);// ���� ����
				} // for
			}
		} // for
	}

	public void updateRecord() {
		// ���ڵ忡 �Է¹��� ������ �ӽ�����迭 ����
		// ���� �ִ��� Ȯ�� �� ������� ���̾�α� "��ĭ�� ä���ּ���"
		// ���� ������� ���ڵ忡 �ϳ��� ��� DTO���� , DAO����
		// ���̺� �����ְ� �ʵ����ֱ�

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		String[] record = new String[model.getColumnCount()];

		String indexStr = indexT.getText();
		String radioStatusStr = getSelectedButtonFromRadio(radioG);
		String comboCategoryStr = combo.getSelectedItem().toString();
		String productNameStr = productNameT.getText();
		String amountStr = amountT.getText();
		String priceStr = priceT.getText();
		String dateStr = dateT.getText();

		String invalidInput = checkValidInput(labels, new String[] { indexStr, radioStatusStr, comboCategoryStr,
				productNameStr, amountStr, priceStr, dateStr });
		if (invalidInput != null) {
			JOptionPane.showMessageDialog(null, "��ĭ�� ä���ּ���.");// TODO: �˸� �޽��� ���
		} else {
			record[0] = indexStr;
			record[1] = radioStatusStr;
			record[2] = comboCategoryStr;
			record[3] = productNameStr;
			record[4] = amountStr;
			record[5] = priceStr;
			record[6] = dateStr;

			InventoryDTO dto = new InventoryDTO();
			dto.setIndexT(indexStr);
			dto.setRadioStatus(radioStatusStr);
			dto.setComboCategory(comboCategoryStr);
			dto.setProductNameT(productNameStr);
			dto.setAmountT(amountStr);
			dto.setPriceT(priceStr);
			dto.setDateT(dateStr);
			// update
			dao.update(dto);// �����ͺ��̽� ����

			// view
			if (radioStatusStr.equals("�԰�")) {

				int amountIpgoInt = Integer.parseInt(amountStr);
				double priceIpgoDouble = Double.parseDouble(priceStr);
				double totalIpgoPrice = priceIpgoDouble * amountIpgoInt;

				vdto.setIndexT(indexStr);
				vdto.setDateT(dateStr);
				vdto.setProductNameIpgoT(productNameStr);
				vdto.setComboIpgoCategory(comboCategoryStr);
				vdto.setAmountIpgoInt(amountIpgoInt);
				vdto.setPriceIpgoT(priceIpgoDouble);
				vdto.setTotalIpgoPrice(totalIpgoPrice);

				viewIpgoList = new ArrayList<ViewDTO>();
				viewIpgoList.add(vdto);
				dao.updateIpgo(vdto);

			} else if (radioStatusStr.equals("���")) {

				int amountChulgoInt = Integer.parseInt(amountStr);
				double priceChulgoDouble = Double.parseDouble(priceStr);
				double totalChulgoPrice = priceChulgoDouble * amountChulgoInt;

				vdto.setIndexT(indexStr);
				vdto.setDateT(dateStr);
				vdto.setProductNameChulgoT(productNameStr);
				vdto.setComboChulgoCategory(comboCategoryStr);
				vdto.setAmountChulgoInt(amountChulgoInt);
				vdto.setPriceChulgoT(priceChulgoDouble);
				vdto.setTotalChulgoPrice(totalChulgoPrice);

				viewChulgoList = new ArrayList<ViewDTO>();
				viewChulgoList.add(vdto);
				dao.updateChulgo(vdto);

			}
		}

	}

	public void refresh(DefaultTableModel model) {
		model.setRowCount(0);
		selectRecords();

		// �����۾�
		clearFields();
		indexT.setEditable(true);
		indexT.requestFocus();
	}

	public void deleteAllJaego(DefaultTableModel model) {
		model.setRowCount(0);
		dao.deleteData();

		// �����۾�
		clearFields();
		indexT.setEditable(true);
		indexT.requestFocus();
	}

	@Override
	public void save(List<InventoryDTO> list) {
		JFileChooser chooser = new JFileChooser();
		int result = chooser.showSaveDialog(null);

		File file = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
		}

		// ----------
		if (file == null)
			return; // ��Ҵ������� �׳� ������

		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));

			// �ι�° ��� - ArrayList ����ִ� InvetoryDTO�� ������ ����

			oos.writeInt(list.size());

			for (InventoryDTO dto : list) {
				oos.writeObject(dto);
			}
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void load() {
		File file = null;
		ObjectInputStream ois = null;// ���� �о�Ͷ�

		JFileChooser chooser = new JFileChooser(); // ��ü ����, Ŭ���� ����
		int result = chooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile(); // ������ ����
			JOptionPane.showMessageDialog(null, file);
		}
		if (file == null)
			return; // java.lang.NullPointerException : code �ҽ��� �����ֱ� ==> ���� ���� ������ ����ϸ� ������ ���.

		list.clear();

		try {
			ois = new ObjectInputStream(new FileInputStream(file));

			int size = ois.readInt();
			for (int i = 0; i < size; i++) {
				InventoryDTO dto = (InventoryDTO) ois.readObject(); // ���� ������ �о�Ͷ� - �ϳ��� �о�´�.
				// Object �ڽ� = (�ڽ�)�θ� -- ����ȯ
				list.add(dto);
			}

			ois.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		addRecordList(list);
	}

	public List<InventoryDTO> listReturnRecords() {

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		List<InventoryDTO> jaegoList = dao.select();

		for (InventoryDTO jaego : jaegoList) {
			String[] record = new String[model.getColumnCount()];
			record[0] = jaego.getIndexT().toString();
			record[1] = jaego.getRadioStatus().toString();
			record[2] = jaego.getComboCategory().toString();
			record[3] = jaego.getProductNameT().toString();
			record[4] = jaego.getAmountT().toString();
			record[5] = jaego.getPriceT().toString();
			record[6] = jaego.getDateT().toString();
		}

		return jaegoList;
	}

	public void deleteRecord(int[] selected) {
		removeRecords(selected);// �ش� �ο��� DB����
	}

	private boolean idexCheck(String indexStr) {
		index_check = dao.indexCheck(indexStr);
		return index_check;
	}

	private void clearFields() {
		// �ʵ� ����ִ� �޼ҵ�
		indexT.setText("");
		productNameT.setText("");
		amountT.setText("");
		priceT.setText("");
		dateT.setText("");
	}

	private String checkValidInput(String[] labels, String[] inputs) {
		// �Է¹��� �����͵��� ����������
		// Invalid Input: �ش���̸�(���°�) ���
		for (int i = 0; i < inputs.length; i++) {
			if (isInvalidInput(inputs[i])) {
				String text = labels[i] + " (" + inputs[i] + ")";
				System.out.println("Invalid Input: " + text);
				return text;
			}
		}
		return null;
	}

	private boolean isInvalidInput(String input) {
		// ����ִ°��� �ִ°� ����
		return input == null || input.length() == 0;
	}

	private String getSelectedButtonFromRadio(ButtonGroup buttonGroup) {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				return button.getText();
			}
		}
		return null;
	}

	private void setSelectedButtonFromRadio(ButtonGroup buttonGroup, String selectedText) {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.getText().equals(selectedText)) {
				button.setSelected(true);
			}
		}
	}

	@Override
	public void removeRecord(int selected) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if (selected < 0) {
			if (table.getRowCount() == 0)// ����ִ� ���̺��̸�
				return;
			selected = 0;
		}
		model.removeRow(selected);
		clearFields();
	}

	@Override
	public void removeRecords(int[] selectedRows) {// ���õ���� ��ü ����
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		for (int i = 0; i < selectedRows.length; i++) {
			Vector<String> rowData = (Vector<String>) model.getDataVector().elementAt(selectedRows[i]);
			int number = Integer.parseInt(rowData.get(0));

			System.out.println(number + "�� ����");
			dao.delete(number);
		} // for��
		clearFields();
	}

	@Override
	public void selectRecord(int index) {// ���콺Ŭ������ ���õ� �ప ���ͼ� �ؽ�Ʈ���ڿ� �ִ� �Լ�
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Vector<String> rowData = (Vector<String>) model.getDataVector().elementAt(index);

		indexT.setText(rowData.get(0));
		setSelectedButtonFromRadio(radioG, rowData.get(1));
		combo.setSelectedItem(rowData.get(2));
		productNameT.setText(rowData.get(3));
		amountT.setText(rowData.get(4));
		priceT.setText(rowData.get(5));
		dateT.setText(rowData.get(6));
	}

	@Override
	public void selectRecords() {// ������̺� ������� �ٽ� DB�� �����ؼ� ������ �� �ܾ�ͼ� ���̺� �ٽ�����ϱ�
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		List<InventoryDTO> jaegoList = dao.select();
		for (InventoryDTO jaego : jaegoList) {
			String[] record = new String[model.getColumnCount()];
			record[0] = jaego.getIndexT();
			record[1] = jaego.getRadioStatus();
			record[2] = jaego.getComboCategory();
			record[3] = jaego.getProductNameT();
			record[4] = jaego.getAmountT();
			record[5] = jaego.getPriceT();
			record[6] = jaego.getDateT();
			model.addRow(record);
		}
	}

	// ===============================================================================================================================

	// client�г�

	public void clientadd() {

		DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
		String[] record = new String[model.getColumnCount()];

		String nostr = noT.getText();
		String clientstr = clientT.getText();
		String pusonstr = pursonT.getText();
		String emailstr = emailT.getText();
		String phoneNumstr = phoneNumT.getText();
		String addressstr = addressT.getText();

		record[0] = nostr;
		record[1] = clientstr;
		record[2] = pusonstr;
		record[3] = emailstr;
		record[4] = phoneNumstr;
		record[5] = addressstr;

		InventoryDTO dto = new InventoryDTO();
		dto.setNo(nostr);
		dto.setClientName(clientstr);
		dto.setPurson(pusonstr);
		dto.setEmail(emailstr);
		dto.setPhoneNum(phoneNumstr);
		dto.setAddress(addressstr);

		// insert
		dao.clientInsert(dto);// �����ͺ��̽� ����

		clearText();
		model.addRow(record);
	}// clientadd()

	@Override
	public void clientupdate() {

		DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
		String[] record = new String[model.getColumnCount()];

		String nostr = noT.getText();
		String clientstr = clientT.getText();
		String pusonstr = pursonT.getText();
		String emailstr = emailT.getText();
		String phoneNumstr = phoneNumT.getText();
		String addressstr = addressT.getText();

		record[0] = nostr;
		record[1] = clientstr;
		record[2] = pusonstr;
		record[3] = emailstr;
		record[4] = phoneNumstr;
		record[5] = addressstr;

		InventoryDTO dto = new InventoryDTO();
		dto.setNo(nostr);
		dto.setClientName(clientstr);
		dto.setPurson(pusonstr);
		dto.setEmail(emailstr);
		dto.setPhoneNum(phoneNumstr);
		dto.setAddress(addressstr);

		// update
		dao.clientupdate(dto);

		clientview();
		noT.setEditable(true);
		clearText();

		noT.requestFocus();

	}// clientupdate()

	@Override
	public void clientview() {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
		model.setRowCount(0);

		List<InventoryDTO> clientlist = dao.clientview();
		for (InventoryDTO client : clientlist) {
			String[] record = new String[model.getColumnCount()];
			record[0] = client.getNo();
			record[1] = client.getClientName();
			record[2] = client.getPurson();
			record[3] = client.getEmail();
			record[4] = client.getPhoneNum();
			record[5] = client.getAddress();
			model.addRow(record);
		}

	}// clientview()

	@Override
	public void clientclickview(int index) {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
		Vector<String> rowData = (Vector<String>) model.getDataVector().elementAt(index);

		noT.setText(rowData.get(0));
		clientT.setText(rowData.get(1));
		pursonT.setText(rowData.get(2));
		emailT.setText(rowData.get(3));
		phoneNumT.setText(rowData.get(4));
		addressT.setText(rowData.get(5));

	}

	@Override
	public void clientSearch() {
		String clientName = JOptionPane.showInputDialog(null, "�ŷ�ó���� �Է����ּ���", "�˻�",
				JOptionPane.QUESTION_MESSAGE);

		if (clientName == null || clientName.length() == 0)
			return;

		DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
		model.setRowCount(0);

		List<InventoryDTO> clientlist = dao.searchClient(clientName);
		for (InventoryDTO client : clientlist) {
			String[] record = new String[model.getColumnCount()];
			record[0] = client.getNo();
			record[1] = client.getClientName();
			record[2] = client.getPurson();
			record[3] = client.getEmail();
			record[4] = client.getPhoneNum();
			record[5] = client.getAddress();
			model.addRow(record);
		}
		int su = model.getRowCount();

		if (su == 0)
			JOptionPane.showMessageDialog(null, "ã���� �ϴ� �ŷ�ó�� �����ϴ�");

	}// clientSearch()

	@Override
	public void clientDel(String no) {
		dao.clientdelete(no);

		clientview();

		clearText();
		noT.setEditable(true);
		noT.requestFocus();

	}

	@Override
	public void clearText() {
		noT.setText("");
		clientT.setText("");
		pursonT.setText("");
		emailT.setText("");
		phoneNumT.setText("");
		addressT.setText("");

	}// �ؽ�Ʈ�ʵ� ����ֱ�

	public boolean indexCheck(String no) {

		String indexCheck = null;

		indexCheck = dao.clientindexCheck(no);

		boolean clientCheck = false;
		if (indexCheck != null) {
			clientCheck = true;
		}

		return clientCheck;
	}

	public List<InventoryDTO> returnJaegoList() {
		List<InventoryDTO> jaegoList = dao.select();
		return jaegoList;
	}

	// ===============================================================================

}
