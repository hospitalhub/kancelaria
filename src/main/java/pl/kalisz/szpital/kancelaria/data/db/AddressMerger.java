package pl.kalisz.szpital.kancelaria.data.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AddressMerger.
 */
public class AddressMerger {

	static final String JDBC_HSQLDB = "jdbc:mysql://127.0.0.1:3306/wordpress?user=root&password=nA8Wedeg&useUnicode=yes&amp;characterEncoding=UTF-8";

	static final Logger LOGGER = LoggerFactory.getLogger(DbHelper.class.toString());

	/**
	 * Merge adresy.
	 * 
	 * @param id1
	 *            the id1
	 * @param id
	 *            the id
	 * @return the int
	 */
	public static int mergeAdresy(final int id1, final int id) {
		LOGGER.info("mergeuje " + id + " do " + id1);
		Connection conn = null;
		int updatedRows = 0;
		try {
			conn = DriverManager.getConnection(JDBC_HSQLDB);
			PreparedStatement statement = conn.prepareStatement("UPDATE TRANSACTION SET adresid = ? WHERE adresid = ?");
			statement.setInt(1, id1);
			statement.setInt(2, id);
			try {
				updatedRows = statement.executeUpdate();
			} catch (Exception e) {
				LOGGER.error(e.getMessage() + " " + id1);
			}
			statement.close();
			conn.commit();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NullPointerException e2) {
				e2.printStackTrace();
			}
		}
		return updatedRows;
	}

}
