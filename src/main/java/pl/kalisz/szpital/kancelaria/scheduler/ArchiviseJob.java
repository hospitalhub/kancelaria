package pl.kalisz.szpital.kancelaria.scheduler;

import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * The Class ArchiviseJob.
 */
public class ArchiviseJob implements Job {
//
//  /** The Constant CHECKPOINT. */
//  private static final String CHECKPOINT = "CHECKPOINT";
//
//  /** The Constant JDBC_DRIVER. */
//  private static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";
//
//  /** The Constant JDBC_HSQLDB_FILE. */
//  private static final String JDBC_HSQLDB_FILE = "jdbc:hsqldb:http://127.0.0.1:8080/wsz/hsql";
//
//  /** The Constant logger. */
//  public static final Logger LOGGER = LoggerFactory.getLogger(ArchiviseJob.class);
//
//  /** The connection pool. */
//  SimpleJDBCConnectionPool connectionPool;

  /**
   * Connect.
   * 
   * @throws SQLException the SQL exception
   * @throws ClassNotFoundException the class not found exception
   */
//  private void connect() throws SQLException, ClassNotFoundException {
//    Class.forName(JDBC_DRIVER);
//    if (connectionPool == null) {
//      connectionPool = new SimpleJDBCConnectionPool(JDBC_DRIVER, JDBC_HSQLDB_FILE, "SA", "", 2, 5);
//    }
//  }

  /*
   * (non-Javadoc)
   * 
   * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
   */
  public void execute(JobExecutionContext context) throws JobExecutionException {
//    LOGGER.info(Strings.ARCHIWIZUJEMY_TRANSAKCJE_NOWE_I_ZMIENIONE_ADRESY_D);
//    persistDBToDisk();
  }

  /**
   * Persist db to disk.
   */
//  private void persistDBToDisk() {
//    try {
//      connect();
//      Connection conn = null;
//      conn = connectionPool.reserveConnection();
//      StringBuffer query = new StringBuffer(CHECKPOINT);
//      Statement statement = conn.createStatement();
//      statement.execute(query.toString());
//      statement.close();
//      conn.commit();
//    } catch (Exception e) {
//      LOGGER.warn(e.getMessage());
//    }
//  }

}
