package inventory;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ViewImpl implements view {
	private JTable vitable, vctable;
	private DefaultTableModel viewIpgoModel;
	private DefaultTableModel viewChulgoModel;
	private ViewDTO vdto;
	private InventoryDAO dao;

	public ViewImpl(JTable vitable, DefaultTableModel viewIpgoModel, JTable vctable, DefaultTableModel viewChulgoModel,
			ViewDTO vdto) {
		dao = new InventoryDAO();
		this.vitable = vitable;
		this.vctable = vctable;
		this.viewIpgoModel = viewIpgoModel;
		this.viewChulgoModel = viewChulgoModel;
		this.vdto = vdto;
	}

	@Override
	public void selectRecordsIpgo(String searchCategory) {
		DefaultTableModel viewIpgoModel = (DefaultTableModel) vitable.getModel();
		viewIpgoModel.setRowCount(0);

		List<ViewDTO> jaegoList = dao.selectIpgo(searchCategory);
		for (ViewDTO jaego : jaegoList) {
			String[] record = new String[viewIpgoModel.getColumnCount()];
			record[0] = jaego.getIndexT();
			record[1] = jaego.getDateT();
			record[2] = jaego.getProductNameIpgoT();
			record[3] = jaego.getComboIpgoCategory();
			record[4] = jaego.getAmountIpgoInt() + "";
			record[5] = jaego.getPriceIpgoT() + "";
			record[6] = jaego.getTotalIpgoPrice() + "";
			viewIpgoModel.addRow(record);
		}
	}

	@Override
	public void selectRecordsChulgo(String searchCategory) {
		DefaultTableModel viewChulgoModel = (DefaultTableModel) vctable.getModel();
		viewChulgoModel.setRowCount(0);

		List<ViewDTO> jaegoList = dao.selectChulgo(searchCategory);
		for (ViewDTO jaego : jaegoList) {
			String[] record = new String[viewChulgoModel.getColumnCount()];
			record[0] = jaego.getIndexT();
			record[1] = jaego.getDateT();
			record[2] = jaego.getProductNameChulgoT();
			record[3] = jaego.getComboChulgoCategory();
			record[4] = jaego.getAmountChulgoInt() + "";
			record[5] = jaego.getPriceChulgoT() + "";
			record[6] = jaego.getTotalChulgoPrice() + "";
			viewChulgoModel.addRow(record);
		}
	}

}
