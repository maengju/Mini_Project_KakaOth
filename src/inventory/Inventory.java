package inventory;

import java.util.List;

public interface Inventory {

	// ===============================================================================================================

	public void addRecord();

	public void removeRecord(int selected);

	public void removeRecords(int[] selected);

	public void selectRecords();

	public void selectRecord(int row);

	void save(List<InventoryDTO> list);

	public void load();

	// =================================================================================
	// client 사용 메소드

	public void clientadd();

	public void clearText();

	public void clientupdate();

	public void clientview();

	public void clientclickview(int index);

	public void clientDel(String no);

	public void clientSearch();

}
