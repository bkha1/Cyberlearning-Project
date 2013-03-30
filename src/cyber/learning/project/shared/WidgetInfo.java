package cyber.learning.project.shared;

import java.io.Serializable;



@SuppressWarnings("serial")
public final class WidgetInfo implements Serializable
{
  public WidgetInfo() {}


  public WidgetInfo(String type,
                    Integer fromLeft,
                    Integer fromTop,
                    String value)
  {
    type_ = type;
    absoluteFromLeft_ = fromLeft;
    absoluteFromTop_ = fromTop;
    value_ = value;
  }


  public String type_;
  public int absoluteFromLeft_;
  public int absoluteFromTop_;
  public String value_;
}