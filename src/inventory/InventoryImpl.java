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
	// jaego에서 보내는거
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

	// client panel 생성자 시작

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
	// client panel 생성자

	// ===============================================================================================================================
//view에서보내는거
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
		// 레코드에 입력받을 데이터 임시저장배열 생성
		// 빈값이 있는지 확인 후 있을경우 다이얼로그 "빈칸을 채워주세요"
		// 빈값이 없을경우 레코드에 하나씩 담고 DTO세팅 , DAO세팅
		// 테이블에 보여주고 필드비워주기

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
			JOptionPane.showMessageDialog(null, "빈칸을 채워주세요.");// TODO: 알림 메시지 출력
		} else if (idexCheck(indexStr)) {
			JOptionPane.showMessageDialog(null, "index가 존재합니다.");
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

			dao.insert(dto);// 데이터베이스 삽입

			// insert
			model.addRow(record);// 테이블 삽입

			// viewpanel 리스트 계산 및 DB삽입, 테이블 추가

			if (radioStatusStr.equals("입고")) {
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

				// 모델안에 있는걸(기존꺼) 다 지워라. - 안쓰면 기존에 있는거까지 다 찍는다.

				for (ViewDTO vdto : viewIpgoList) {
					// 한줄당 백터에 넣어야 한다. list로는 못넣음
					vi.add(vdto.getIndexT());
					vi.add(vdto.getDateT());
					vi.add(vdto.getProductNameIpgoT());
					vi.add(vdto.getComboIpgoCategory());
					vi.add(vdto.getAmountIpgoInt() + "");
					vi.add(vdto.getPriceIpgoT() + "");
					vi.add(vdto.getTotalIpgoPrice() + ""); // String.format - 소수점 2째자리(문자열)

					viewIpgoModel.addRow(vi);// 한줄 붙음
				} // for

			} else if (radioStatusStr.equals("출고")) {
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

				// 모델안에 있는걸(기존꺼) 다 지워라. - 안쓰면 기존에 있는거까지 다 찍는다.

				for (ViewDTO vdto : viewChulgoList) {
					// 한줄당 백터에 넣어야 한다. list로는 못넣음
					vc.add(vdto.getIndexT());
					vc.add(vdto.getDateT());
					vc.add(vdto.getProductNameChulgoT());
					vc.add(vdto.getComboChulgoCategory());
					vc.add(vdto.getAmountChulgoInt() + "");
					vc.add(vdto.getPriceChulgoT() + "");
					vc.add(vdto.getTotalChulgoPrice() + ""); // String.format - 소수점
																// 2째자리(문자열)

					viewChulgoModel.addRow(vc);// 한줄 붙음
				} // for

			}
		}
	}

	private void addRecordList(List<InventoryDTO> loadlist) {
		// 레코드에 입력받을 데이터 임시저장배열 생성
		// 빈값이 있는지 확인 후 있을경우 다이얼로그 "빈칸을 채워주세요"
		// 빈값이 없을경우 레코드에 하나씩 담고 DTO세팅 , DAO세팅
		// 테이블에 보여주고 필드비워주기
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
			// viewpanel 리스트 계산 및 DB삽입, 테이블 추가

			if (dto.getRadioStatus().equals("입고")) {
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

				// 모델안에 있는걸(기존꺼) 다 지워라. - 안쓰면 기존에 있는거까지 다 찍는다.

				for (ViewDTO vdto : viewIpgoList) {
					// 한줄당 백터에 넣어야 한다. list로는 못넣음
					vi.add(vdto.getIndexT());
					vi.add(vdto.getDateT());
					vi.add(vdto.getProductNameIpgoT());
					vi.add(vdto.getComboIpgoCategory());
					vi.add(vdto.getAmountIpgoInt() + "");
					vi.add(vdto.getPriceIpgoT() + "");
					vi.add(vdto.getTotalIpgoPrice() + ""); // String.format - 소수점 2째자리(문자열)

					viewIpgoModel.addRow(vi);// 한줄 붙음
				} // for

			} else if (dto.getRadioStatus().equals("출고")) {
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

				// 모델안에 있는걸(기존꺼) 다 지워라. - 안쓰면 기존에 있는거까지 다 찍는다.

				for (ViewDTO vdto : viewChulgoList) {
					// 한줄당 백터에 넣어야 한다. list로는 못넣음
					vc.add(vdto.getIndexT());
					vc.add(vdto.getDateT());
					vc.add(vdto.getProductNameChulgoT());
					vc.add(vdto.getComboChulgoCategory());
					vc.add(vdto.getAmountChulgoInt() + "");
					vc.add(vdto.getPriceChulgoT() + "");
					vc.add(vdto.getTotalChulgoPrice() + ""); // String.format - 소수점
					// 2째자리(문자열)

					viewChulgoModel.addRow(vc);// 한줄 붙음
				} // for
			}
		} // for
	}

	public void updateRecord() {
		// 레코드에 입력받을 데이터 임시저장배열 생성
		// 빈값이 있는지 확인 후 있을경우 다이얼로그 "빈칸을 채워주세요"
		// 빈값이 없을경우 레코드에 하나씩 담고 DTO세팅 , DAO세팅
		// 테이블에 보여주고 필드비워주기

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
			JOptionPane.showMessageDialog(null, "빈칸을 채워주세요.");// TODO: 알림 메시지 출력
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
			dao.update(dto);// 데이터베이스 삽입

			// view
			if (radioStatusStr.equals("입고")) {

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

			} else if (radioStatusStr.equals("출고")) {

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

		// 리셋작업
		clearFields();
		indexT.setEditable(true);
		indexT.requestFocus();
	}

	public void deleteAllJaego(DefaultTableModel model) {
		model.setRowCount(0);
		dao.deleteData();

		// 리셋작업
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
			return; // 취소눌렀을때 그냥 나가기

		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));

			// 두번째 방법 - ArrayList 담겨있는 InvetoryDTO의 개수를 저장

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
		ObjectInputStream ois = null;// 파일 읽어와라

		JFileChooser chooser = new JFileChooser(); // 객체 생성, 클래스 생성
		int result = chooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile(); // 선택한 파일
			JOptionPane.showMessageDialog(null, file);
		}
		if (file == null)
			return; // java.lang.NullPointerException : code 소스로 고쳐주기 ==> 파일 열기 했을때 취소하면 오류가 뜬다.

		list.clear();

		try {
			ois = new ObjectInputStream(new FileInputStream(file));

			int size = ois.readInt();
			for (int i = 0; i < size; i++) {
				InventoryDTO dto = (InventoryDTO) ois.readObject(); // 파일 내용을 읽어와라 - 하나씩 읽어온다.
				// Object 자식 = (자식)부모 -- 형변환
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
		removeRecords(selected);// 해당 로우의 DB삭제
	}

	private boolean idexCheck(String indexStr) {
		index_check = dao.indexCheck(indexStr);
		return index_check;
	}

	private void clearFields() {
		// 필드 비워주는 메소드
		indexT.setText("");
		productNameT.setText("");
		amountT.setText("");
		priceT.setText("");
		dateT.setText("");
	}

	private String checkValidInput(String[] labels, String[] inputs) {
		// 입력받은 데이터들이 비어있을경우
		// Invalid Input: 해당라벨이름(들어온값) 출력
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
		// 비어있는값이 있는값 리턴
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
			if (table.getRowCount() == 0)// 비어있는 테이블이면
				return;
			selected = 0;
		}
		model.removeRow(selected);
		clearFields();
	}

	@Override
	public void removeRecords(int[] selectedRows) {// 선택된행렬 전체 삭제
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		for (int i = 0; i < selectedRows.length; i++) {
			Vector<String> rowData = (Vector<String>) model.getDataVector().elementAt(selectedRows[i]);
			int number = Integer.parseInt(rowData.get(0));

			System.out.println(number + "행 삭제");
			dao.delete(number);
		} // for문
		clearFields();
	}

	@Override
	public void selectRecord(int index) {// 마우스클릭으로 선택된 행값 얻어와서 텍스트상자에 넣는 함수
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
	public void selectRecords() {// 재고테이블 싹지우고 다시 DB에 접근해서 데이터 싹 긁어와서 테이블에 다시출력하기
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

	// client패널

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
		dao.clientInsert(dto);// 데이터베이스 삽입

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
		String clientName = JOptionPane.showInputDialog(null, "거래처명을 입력해주세요", "검색",
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
			JOptionPane.showMessageDialog(null, "찾고자 하는 거래처가 없습니다");

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

	}// 텍스트필드 비워주기

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
