package inventory;

import java.io.Serializable;

public class InventoryDTO implements Serializable {
	private String indexT;
	private String radioStatus;
	private String comboCategory;
	private String productNameT;
	private String amountT;
	private String priceT;
	private String dateT;

	// ==============================
	private String categoryCheck;

	// =====================================================
	public String getCategoryCheck() {
		return categoryCheck;
	}

	public void setCategoryCheck(String categoryCheck) {
		this.categoryCheck = categoryCheck;
	}

//===============================================================================
	//===============================clientPanelDTO
		private String no,clientName,purson,email,phoneNum,address;
		
		
		public String getNo() {
			return no;
		}

		public void setNo(String no) {
			this.no = no;
		}

		public String getClientName() {
			return clientName;
		}

		public void setClientName(String clientName) {
			this.clientName = clientName;
		}

		public String getPurson() {
			return purson;
		}

		public void setPurson(String purson) {
			this.purson = purson;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhoneNum() {
			return phoneNum;
		}

		public void setPhoneNum(String phoneNum) {
			this.phoneNum = phoneNum;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		//=====================================================

	public String getIndexT() {
		return String.valueOf(indexT);
	}

	public void setIndexT(String indexT) {
		this.indexT = indexT;
	}

	public String getRadioStatus() {
		return radioStatus;
	}

	public void setRadioStatus(String radioStatus) {
		this.radioStatus = radioStatus;
	}

	public String getComboCategory() {
		return comboCategory;
	}

	public void setComboCategory(String comboCategory) {
		this.comboCategory = comboCategory;
	}

	public String getProductNameT() {
		return productNameT;
	}

	public void setProductNameT(String productNameT) {
		this.productNameT = productNameT;
	}

	public String getAmountT() {
		return amountT;
	}

	public void setAmountT(String amountT) {
		this.amountT = amountT;
	}

	public String getPriceT() {
		return priceT;
	}

	public void setPriceT(String priceT) {
		this.priceT = priceT;
	}

	public String getDateT() {
		return dateT;
	}

	public void setDateT(String dateT) {
		this.dateT = dateT;
	}

}
