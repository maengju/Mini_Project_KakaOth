package inventory;

import java.io.Serializable;

public class ViewDTO implements Serializable {
	private String indexT;
	private String radioIpgoStatus;
	private String dateT;
	// 입고
	private String comboIpgoCategory;
	private String productNameIpgoT;
	private int amountIpgoInt;
	private Double priceIpgoT;
	private Double totalIpgoPrice;
	// 출고
	private String radioChulgoStatus;
	private String comboChulgoCategory;
	private String productNameChulgoT;
	private int amountChulgoInt;
	private Double priceChulgoT;
	private Double totalChulgoPrice;

	// 공통

	public String getIndexT() {
		return indexT;
	}

	public String getDateT() {
		return dateT;
	}

	public void setDateT(String dateT) {
		this.dateT = dateT;
	}

	public void setIndexT(String indexT) {
		this.indexT = indexT;
	}

	public String getRadioIpgoStatus() {
		return radioIpgoStatus;
	}

	public void setRadioIpgoStatus(String radioIpgoStatus) {
		this.radioIpgoStatus = radioIpgoStatus;
	}

	// chulgo
	public String getRadioChulgoStatus() {
		return radioChulgoStatus;
	}

	public void setRadioChulgoStatus(String radioChulgoStatus) {
		this.radioChulgoStatus = radioChulgoStatus;
	}

	public String getComboChulgoCategory() {
		return comboChulgoCategory;
	}

	public void setComboChulgoCategory(String comboChulgoCategory) {
		this.comboChulgoCategory = comboChulgoCategory;
	}

	public String getProductNameChulgoT() {
		return productNameChulgoT;
	}

	public void setProductNameChulgoT(String productNameChulgoT) {
		this.productNameChulgoT = productNameChulgoT;
	}

	public int getAmountChulgoInt() {
		return amountChulgoInt;
	}

	public void setAmountChulgoInt(int amountChulgoInt) {
		this.amountChulgoInt = amountChulgoInt;
	}

	public Double getPriceChulgoT() {
		return priceChulgoT;
	}

	public void setPriceChulgoT(Double priceChulgoT) {
		this.priceChulgoT = priceChulgoT;
	}

	public Double getTotalChulgoPrice() {
		return totalChulgoPrice;
	}

	public void setTotalChulgoPrice(Double totalChulgoPrice) {
		this.totalChulgoPrice = totalChulgoPrice;
	}

	// ipgo
	public Double getTotalIpgoPrice() {
		return totalIpgoPrice;
	}

	public void setTotalIpgoPrice(Double totalIpgoPrice) {
		this.totalIpgoPrice = totalIpgoPrice;
	}

	public String getComboIpgoCategory() {
		return comboIpgoCategory;
	}

	public void setComboIpgoCategory(String comboIpgoCategory) {
		this.comboIpgoCategory = comboIpgoCategory;
	}

	public String getProductNameIpgoT() {
		return productNameIpgoT;
	}

	public void setProductNameIpgoT(String productNameIpgoT) {
		this.productNameIpgoT = productNameIpgoT;
	}

	public int getAmountIpgoInt() {
		return amountIpgoInt;
	}

	public void setAmountIpgoInt(int amountIpgoInt) {
		this.amountIpgoInt = amountIpgoInt;
	}

	public Double getPriceIpgoT() {
		return priceIpgoT;
	}

	public void setPriceIpgoT(Double priceIpgoT) {
		this.priceIpgoT = priceIpgoT;
	}

}
