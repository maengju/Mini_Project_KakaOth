package inventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "c##java";
	private String password = "bit";

	public InventoryDAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}// 积己磊

	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// 立练

	// ==========================================================================
	public void insert(InventoryDTO dto) {
		String sql = "insert into t_jaego_control(jaego_index, jaego_status, jaego_category, jaego_name, jaego_amount, jaego_price, jaego_date) values(?, ?, ?, ?, ?, ?, ?)";
		getConnection();
//			long key = -1L;

		try {
//				pstmt = conn.prepareStatement(sql, new String[] { "ID" }); //矫牧胶 积己
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getIndexT());
			pstmt.setString(2, dto.getRadioStatus());
			pstmt.setString(3, dto.getComboCategory());
			pstmt.setString(4, dto.getProductNameT());
			pstmt.setString(5, dto.getAmountT());
			pstmt.setString(6, dto.getPriceT());
			pstmt.setString(7, dto.getDateT());
			pstmt.executeUpdate();

//				pstmt.executeUpdate();
//				if (rs.next()) {
//					key = rs.getLong(1);
//				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public int update(InventoryDTO dto) {
		String sql = "merge into t_jaego_control a\n" + "using dual\n" + "on (a.jaego_index=?)\n"
				+ "when matched then\n"
				+ "update set a.jaego_status = ?, a.jaego_category = ?, a.jaego_name = ?, a.jaego_amount = ?, a.jaego_price = ?, a.jaego_date = ?\n"
				+ "when not matched then\n"
				+ "insert (a.jaego_index, a.jaego_status, a.jaego_category, a.jaego_name, a.jaego_amount, a.jaego_price, a.jaego_date)\n"
				+ "values (?,?,?,?,?,?,?)";
		this.getConnection();
		int su = 0;

		try {
			pstmt = conn.prepareStatement(sql); // 积己
			pstmt.setString(1, dto.getIndexT());
			pstmt.setString(2, dto.getRadioStatus());
			pstmt.setString(3, dto.getComboCategory());
			pstmt.setString(4, dto.getProductNameT());
			pstmt.setString(5, dto.getAmountT());
			pstmt.setString(6, dto.getPriceT());
			pstmt.setString(7, dto.getDateT());
			pstmt.setString(8, dto.getIndexT());
			pstmt.setString(9, dto.getRadioStatus());
			pstmt.setString(10, dto.getComboCategory());
			pstmt.setString(11, dto.getProductNameT());
			pstmt.setString(12, dto.getAmountT());
			pstmt.setString(13, dto.getPriceT());
			pstmt.setString(14, dto.getDateT());
			pstmt.executeUpdate();

			su = pstmt.getUpdateCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return su;
	}

	public int delete(int selectedRowIndex) {
		String sql = "delete from t_jaego_control where jaego_index = ?";
		this.getConnection();
		int su = 0;

		try {
			pstmt = conn.prepareStatement(sql); // 积己
			pstmt.setLong(1, selectedRowIndex);
			pstmt.executeUpdate();

			su = pstmt.getUpdateCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return su;
	}

	// jaegopanel
	public List<InventoryDTO> select() {
		List<InventoryDTO> records = new ArrayList<>();
		String sql = "select jaego_index, jaego_status, jaego_category, jaego_name, jaego_amount, jaego_price, jaego_date from t_jaego_control order by jaego_index";
		this.getConnection();

		try {
			pstmt = conn.prepareStatement(sql); // 积己
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				InventoryDTO dto = new InventoryDTO();
				dto.setIndexT(rs.getString("jaego_index"));
				dto.setRadioStatus(rs.getString("jaego_status"));
				dto.setComboCategory(rs.getString("jaego_category"));
				dto.setProductNameT(rs.getString("jaego_name"));
				dto.setAmountT(rs.getString("jaego_amount"));
				dto.setPriceT(rs.getString("jaego_price"));
				dto.setDateT(rs.getString("jaego_date"));

				records.add(dto);
			} // while

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return records;
	}

	// jaegopanel
	public boolean indexCheck(String indexStr) {
		boolean index_check = false;
		String sql = "select * from t_jaego_control where jaego_index=?";
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, indexStr);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				indexStr = rs.getString("JAEGO_INDEX");
				index_check = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return index_check;

	}

	// viewpanel ipgo单捞磐 叼厚俊 火涝
	public void insertIpgo(ViewDTO vdto) {
		String sql = "insert into t_jaego_ipgo values(?, ?, ?, ?, ?, ?, ?)";
		getConnection();
//			long key = -1L;

		try {
//				pstmt = conn.prepareStatement(sql, new String[] { "ID" }); //矫牧胶 积己
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vdto.getIndexT());
			pstmt.setString(2, vdto.getDateT());
			pstmt.setString(3, vdto.getProductNameIpgoT());
			pstmt.setString(4, vdto.getComboIpgoCategory());
			pstmt.setInt(5, vdto.getAmountIpgoInt());
			pstmt.setDouble(6, vdto.getPriceIpgoT());
			pstmt.setDouble(7, vdto.getTotalIpgoPrice());
			pstmt.executeUpdate();

//				pstmt.executeUpdate();
//				if (rs.next()) {
//					key = rs.getLong(1);
//				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void insertChulgo(ViewDTO vdto) {
		String sql = "insert into t_jaego_chulgo values(?, ?, ?, ?, ?, ?, ?)";
		getConnection();
//			long key = -1L;

		try {
//				pstmt = conn.prepareStatement(sql, new String[] { "ID" }); //矫牧胶 积己
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vdto.getIndexT());
			pstmt.setString(2, vdto.getDateT());
			pstmt.setString(3, vdto.getProductNameChulgoT());
			pstmt.setString(4, vdto.getComboChulgoCategory());
			pstmt.setInt(5, vdto.getAmountChulgoInt());
			pstmt.setDouble(6, vdto.getPriceChulgoT());
			pstmt.setDouble(7, vdto.getTotalChulgoPrice());
			pstmt.executeUpdate();

//				pstmt.executeUpdate();
//				if (rs.next()) {
//					key = rs.getLong(1);
//				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public int updateIpgo(ViewDTO vdto) {
		String sql = "merge into t_jaego_ipgo a\n" + "using dual\n" + "on (a.jaego_index=?)\n" + "when matched then\n"
				+ "update set a.jaego_date = ?, a.jaego_product = ?, a.jaego_category = ?, a.jaego_amount = ?, a.jaego_price = ?, a.jaego_total_price = ?\n"
				+ "when not matched then\n"
				+ "insert (a.jaego_index, a.jaego_date, a.jaego_product, a.jaego_category, a.jaego_amount, a.jaego_price, a.jaego_total_price)\n"
				+ "values (?,?,?,?,?,?,?)";
		this.getConnection();
		int su = 0;

		try {
			pstmt = conn.prepareStatement(sql); // 积己
			pstmt.setString(1, vdto.getIndexT());
			pstmt.setString(2, vdto.getDateT());
			pstmt.setString(3, vdto.getProductNameIpgoT());
			pstmt.setString(4, vdto.getComboIpgoCategory());
			pstmt.setInt(5, vdto.getAmountIpgoInt());
			pstmt.setDouble(6, vdto.getPriceIpgoT());
			pstmt.setDouble(7, vdto.getTotalIpgoPrice());
			pstmt.setString(8, vdto.getIndexT());
			pstmt.setString(9, vdto.getDateT());
			pstmt.setString(10, vdto.getProductNameIpgoT());
			pstmt.setString(11, vdto.getComboIpgoCategory());
			pstmt.setInt(12, vdto.getAmountIpgoInt());
			pstmt.setDouble(13, vdto.getPriceIpgoT());
			pstmt.setDouble(14, vdto.getTotalIpgoPrice());
			pstmt.executeUpdate();

			su = pstmt.getUpdateCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return su;
	}

	public int updateChulgo(ViewDTO vdto) {
		String sql = "merge into t_jaego_chulgo a\n" + "using dual\n" + "on (a.jaego_index=?)\n" + "when matched then\n"
				+ "update set a.jaego_date = ?, a.jaego_product = ?, a.jaego_category = ?, a.jaego_amount = ?, a.jaego_price = ?, a.jaego_total_price = ?\n"
				+ "when not matched then\n"
				+ "insert (a.jaego_index, a.jaego_date, a.jaego_product, a.jaego_category, a.jaego_amount, a.jaego_price, a.jaego_total_price)\n"
				+ "values (?,?,?,?,?,?,?)";
		this.getConnection();
		int su = 0;

		try {
			pstmt = conn.prepareStatement(sql); // 积己
			pstmt.setString(1, vdto.getIndexT());
			pstmt.setString(2, vdto.getDateT());
			pstmt.setString(3, vdto.getProductNameChulgoT());
			pstmt.setString(4, vdto.getComboChulgoCategory());
			pstmt.setInt(5, vdto.getAmountChulgoInt());
			pstmt.setDouble(6, vdto.getPriceChulgoT());
			pstmt.setDouble(7, vdto.getTotalChulgoPrice());
			pstmt.setString(8, vdto.getIndexT());
			pstmt.setString(9, vdto.getDateT());
			pstmt.setString(10, vdto.getProductNameChulgoT());
			pstmt.setString(11, vdto.getComboChulgoCategory());
			pstmt.setInt(12, vdto.getAmountChulgoInt());
			pstmt.setDouble(13, vdto.getPriceChulgoT());
			pstmt.setDouble(14, vdto.getTotalChulgoPrice());
			pstmt.executeUpdate();

			su = pstmt.getUpdateCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return su;
	}

	// 惑前疙 府胶飘 惶酒坷扁
	public List<InventoryDTO> selectProduct() {
		List<InventoryDTO> records = new ArrayList<>();
		String sql = "select jaego_index, jaego_status, jaego_category, jaego_name, jaego_amount, jaego_price, jaego_date from t_jaego_control";
		this.getConnection();

		try {
			pstmt = conn.prepareStatement(sql); // 积己
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				InventoryDTO dto = new InventoryDTO();
				dto.setIndexT(rs.getString("jaego_index"));
				dto.setRadioStatus(rs.getString("jaego_status"));
				dto.setComboCategory(rs.getString("jaego_category"));
				dto.setProductNameT(rs.getString("jaego_name"));
				dto.setAmountT(rs.getString("jaego_amount"));
				dto.setPriceT(rs.getString("jaego_price"));
				dto.setDateT(rs.getString("jaego_date"));

				records.add(dto);
			} // while

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return records;
	}

	public List<ViewDTO> selectIpgo(String searchCategory) {
		List<ViewDTO> records = new ArrayList<>();
		String sql = "select * from t_jaego_ipgo";
		this.getConnection();

		try {
			if (searchCategory != null) {
				sql += " where jaego_category = '" + searchCategory + "'";
			}
			pstmt = conn.prepareStatement(sql); // 积己
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ViewDTO dto = new ViewDTO();
				dto.setIndexT(rs.getString("jaego_index"));
				dto.setDateT(rs.getString("jaego_date"));
				dto.setProductNameIpgoT(rs.getString("jaego_product"));
				dto.setComboIpgoCategory(rs.getString("jaego_category"));
				dto.setAmountIpgoInt(rs.getInt("jaego_amount"));
				dto.setPriceIpgoT(rs.getDouble("jaego_price"));
				dto.setTotalIpgoPrice(rs.getDouble("jaego_total_price"));

				records.add(dto);
			} // while

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return records;
	}

	public List<ViewDTO> selectChulgo(String searchCategory) {
		List<ViewDTO> records = new ArrayList<>();
		String sql = "select * from t_jaego_chulgo";
		this.getConnection();

		try {
			if (searchCategory != null) {
				sql += " where jaego_category = '" + searchCategory + "'";
			}
			pstmt = conn.prepareStatement(sql); // 积己
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ViewDTO dto = new ViewDTO();
				dto.setIndexT(rs.getString("jaego_index"));
				dto.setDateT(rs.getString("jaego_date"));
				dto.setProductNameChulgoT(rs.getString("jaego_product"));
				dto.setComboChulgoCategory(rs.getString("jaego_category"));
				dto.setAmountChulgoInt(rs.getInt("jaego_amount"));
				dto.setPriceChulgoT(rs.getDouble("jaego_price"));
				dto.setTotalChulgoPrice(rs.getDouble("jaego_total_price"));

				records.add(dto);
			} // while

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return records;
	}

	// =================================================================================
	// clientPanel

	public void clientInsert(InventoryDTO dto) {

		String sql = "insert into t_client values(?,?,?,?,?,?)";
		getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getNo());
			pstmt.setString(2, dto.getClientName());
			pstmt.setString(3, dto.getPurson());
			pstmt.setString(4, dto.getEmail());
			pstmt.setString(5, dto.getPhoneNum());
			pstmt.setString(6, dto.getAddress());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}// clientInsert(InventoryDTO dto)

	public List<InventoryDTO> clientview() {
		// TODO Auto-generated method stub
		List<InventoryDTO> records = new ArrayList<>();
		String sql = "select * from t_client order by client_index";
		this.getConnection();

		try {
			pstmt = conn.prepareStatement(sql); // 积己
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				InventoryDTO dto = new InventoryDTO();
				dto.setNo(rs.getString("client_index"));
				dto.setClientName(rs.getString("client_name"));
				dto.setPurson(rs.getString("client_purson"));
				dto.setEmail(rs.getString("client_email"));
				dto.setPhoneNum(rs.getString("client_phonenum"));
				dto.setAddress(rs.getString("client_address"));

				records.add(dto);
			} // while

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return records;

	}// clientview()

	public void clientupdate(InventoryDTO dto) {
		// TODO Auto-generated method stub
		String sql = "merge into t_client a using dual on (a.client_index=?) when "
				+ "matched then update set a.client_name = ?, a.client_purson = ?, "
				+ "a.client_email = ?, a.client_phonenum = ?, a.client_address = ? " + "when not matched then insert "
				+ "(a.client_index,a.client_name,a.client_purson,"
				+ "a.client_email,a.client_phonenum,a.client_address)values (?,?,?,?,?,?)";

		this.getConnection();

		try {
			pstmt = conn.prepareStatement(sql); // 积己
			pstmt.setString(1, dto.getNo());
			pstmt.setString(2, dto.getClientName());
			pstmt.setString(3, dto.getPurson());
			pstmt.setString(4, dto.getEmail());
			pstmt.setString(5, dto.getPhoneNum());
			pstmt.setString(6, dto.getAddress());

			pstmt.setString(7, dto.getNo());
			pstmt.setString(8, dto.getClientName());
			pstmt.setString(9, dto.getPurson());
			pstmt.setString(10, dto.getEmail());
			pstmt.setString(11, dto.getPhoneNum());
			pstmt.setString(12, dto.getAddress());

			pstmt.executeUpdate();

			pstmt.getUpdateCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}// clientupdate(InventoryDTO dto)

	public String clientindexCheck(String no) {
		String idcheck = null;
		String sql = "select * from t_client where client_index=?";
		this.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, no);

			rs = pstmt.executeQuery();

			if (rs.next())
				idcheck = rs.getString("client_index");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return idcheck;

	}

	public void clientdelete(String no) {
		String sql = "delete t_client where client_index = ?";
		this.getConnection();

		try {
			pstmt = conn.prepareStatement(sql); // 积己
			pstmt.setString(1, no);
			pstmt.executeUpdate();

			pstmt.getUpdateCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}// clientdelete(String no)

	public List<InventoryDTO> searchClient(String clientName) {
		List<InventoryDTO> records = new ArrayList<>();
		String name = "%" + clientName + "%";
		String sql = "select * from t_client where client_name like ? ";
		this.getConnection();

		try {
			pstmt = conn.prepareStatement(sql); // 积己
			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				InventoryDTO dto = new InventoryDTO();
				dto.setNo(rs.getString("client_index"));
				dto.setClientName(rs.getString("client_name"));
				dto.setPurson(rs.getString("client_purson"));
				dto.setEmail(rs.getString("client_email"));
				dto.setPhoneNum(rs.getString("client_phonenum"));
				dto.setAddress(rs.getString("client_address"));

				records.add(dto);
			} // while

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return records;
	}

	public void deleteData() {

		String sql = "delete from t_jaego_control";
		this.getConnection();

		try {
			pstmt = conn.prepareStatement(sql); // 积己
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// =================================================================================

}