package database;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import beans.PersonDaily;

public class Database {

	private String username = "sa";

	private String password = "qwe123!@#";

	// private String url =
	// "jdbc:sqlserver://222.195.150.215:1433; DatabaseName=ReportDatabase; instance=SQL2008R2";
	private String url = "jdbc:jtds:sqlserver://222.195.150.215:1433;DatabaseName=ReportDatabase;instance=SQL2008R2";

	// private static String driverClass =
	// "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String driverClass = "net.sourceforge.jtds.jdbc.Driver";

	static {
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getProjectDaily(String projectName)
			throws Exception {

		String contents = "";
		String sql = "select Contents from ProjectReports where ProjectReportID = ( select max(ProjectReportID) from ProjectReports where ProjectID in (select ProjectID from Projects where ProjectName=?))";
		try (Connection c = DriverManager
				.getConnection(url, username, password);
				PreparedStatement s = c.prepareStatement(sql);) {
			s.setString(1, projectName);
			ResultSet r = s.executeQuery();
			if (null != r && r.next()) {
				 contents = r.getString(1);
			}else{
				throw new Exception("Project name " + " is not exist");
			}
			r.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return contents;
	}

	public List<PersonDaily> getProjectPerson(String projectName)
			throws Exception {
		List<PersonDaily> values = new ArrayList<PersonDaily>();
		String sql = "select PersonID, PersonName, EMail from People where PersonID in ( select PersonID from PersonProjects where ProjectID in ( select ProjectID from Projects where ProjectName = ?))";
		try(Connection c = DriverManager.getConnection(url, username, password);
			PreparedStatement s = c.prepareStatement(sql);) {
			s.setString(1, projectName);
			ResultSet r = s.executeQuery();
			if( null == r ){
				throw new Exception("Project name " + " is not exist");
			}
			while( r.next() ){
				PersonDaily daily = new PersonDaily();
				daily.setPersonID(r.getInt(1));
				daily.setPersonName(r.getString(2));
				daily.setEmail( r.getString(3));
				values.add(daily);
			}
			r.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return values;
	}

}
