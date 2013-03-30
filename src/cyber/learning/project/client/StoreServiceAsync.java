package cyber.learning.project.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cyber.learning.project.shared.WidgetInfo;

public interface StoreServiceAsync
{
  void record(boolean isPrimary,
              WidgetInfo[] infos,
              AsyncCallback<Void> callback);


  void retrieve(AsyncCallback<WidgetInfo[]> callback);


  void retrieveAlt(AsyncCallback<WidgetInfo[]> callback);
}