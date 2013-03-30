package cyber.learning.project.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cyber.learning.project.shared.WidgetInfo;


@RemoteServiceRelativePath("store")
public interface StoreService extends RemoteService
{
  void record(boolean isPrimary, WidgetInfo[] infos)
    throws IllegalStateException;


  WidgetInfo[] retrieve() throws IllegalStateException;


  WidgetInfo[] retrieveAlt() throws IllegalStateException;
}