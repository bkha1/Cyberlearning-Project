package cyber.learning.project.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cyber.learning.project.client.StoreService;
import cyber.learning.project.shared.WidgetInfo;


@SuppressWarnings("serial")
public final class StoreServiceImpl
  extends RemoteServiceServlet
  implements StoreService
{
  public StoreServiceImpl() throws ClassNotFoundException, SQLException
  {
    Class.forName("org.sqlite.JDBC");
    conn_ =
      DriverManager.getConnection(String.format("jdbc:sqlite:%s",
                                                "persistence.db"));
  }


  @Override
  public synchronized void record(boolean isPrimary,
                                  WidgetInfo[] infos)
    throws IllegalStateException
  {
    final String paramInsertQuery =
      "INSERT INTO \"main\".\"contents\" (\"cid\",\"type\",\"left\",\"top\",\"val\") " +
                                 "VALUES (?1,?2,?3,?4,?5)";
    try
    {
      final Statement deletion = conn_.createStatement();
      deletion.execute(
        String.format("DELETE FROM \"main\".\"contents\" WHERE \"cid\"=%s",
                      isPrimary ? 0 : 1));
      deletion.close();

      if (infos != null)
      {
        for (final WidgetInfo wi : infos)
        {
          final PreparedStatement ps = conn_.prepareStatement(paramInsertQuery);
          ps.setInt(1, isPrimary ? 0 : 1);
          ps.setString(2, wi.type_);
          ps.setInt(3, wi.absoluteFromLeft_);
          ps.setInt(4, wi.absoluteFromTop_);
          ps.setString(5, wi.value_);
          ps.executeUpdate();
          ps.close();
        }
      }
    }
    catch (Throwable t)
    {
      throw new IllegalStateException(t);
    }
  }


  @Override
  public synchronized WidgetInfo[] retrieve() throws IllegalStateException
  {
    final List<WidgetInfo> widgets = new LinkedList<WidgetInfo>();

    try
    {
      final Statement find = conn_.createStatement();
      final ResultSet results = find.executeQuery("SELECT * " +
        		                                      "FROM \"main\".\"contents\" " +
        		                                      "WHERE \"cid\" = 0");
      while (results.next())
      {
        widgets.add(new WidgetInfo(results.getString(2),
                                   results.getInt(3),
                                   results.getInt(4),
                                   results.getString(5)));
      }
      find.close();
    }
    catch (Throwable t) { /* swallow */ }

    return widgets.toArray(new WidgetInfo[widgets.size()]);
  }


  @Override
  public synchronized WidgetInfo[] retrieveAlt() throws IllegalStateException
  {
    final List<WidgetInfo> widgets = new LinkedList<WidgetInfo>();

    try
    {
      final Statement find = conn_.createStatement();
      final ResultSet results = find.executeQuery("SELECT * " +
                                                  "FROM \"main\".\"contents\" " +
                                                  "WHERE \"cid\" = 1");
      while (results.next())
      {
        widgets.add(new WidgetInfo(results.getString(2),
                                   results.getInt(3),
                                   results.getInt(4),
                                   results.getString(5)));
      }
      find.close();
    }
    catch (Throwable t) { /* swallow */ }

    return widgets.toArray(new WidgetInfo[widgets.size()]);
  }


  private final Connection conn_;
}