/*
 * Copyright 2006 Pavel Jbanov.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gc.gwt.wysiwyg.client;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Image;

public class EditorToolbarButton extends Image {
  
  private static final String SPACER_IMAGE_URL = "spacer.gif";

  private String buttonId;

  public EditorToolbarButton(String buttonId) {
    super(SPACER_IMAGE_URL);

    this.setTitle(buttonId);
    this.buttonId = buttonId;

    OnMouseOverDecorateButtonListener listener = new OnMouseOverDecorateButtonListener(this);
    this.addMouseOverHandler(listener);
    this.addMouseOutHandler(listener);
    this.setStyleName("Editor-Toolbar-Button Editor-Toolbar-Button-" + buttonId);
  }

  public String getButtonId() {
    return buttonId;
  }

  public void setButtonId(String buttonId) {
    this.buttonId = buttonId;
  }

  private class OnMouseOverDecorateButtonListener implements MouseOverHandler, MouseOutHandler {

    private EditorToolbarButton button;

    public OnMouseOverDecorateButtonListener(EditorToolbarButton button) {
      this.button = button;
    }

    public void onMouseOver(MouseOverEvent event) {
      button.setStyleName("Editor-Toolbar-Button Editor-Toolbar-Button-hover Editor-Toolbar-Button-"
          + button.getButtonId());
    }

    public void onMouseOut(MouseOutEvent event) {
      button.setStyleName("Editor-Toolbar-Button Editor-Toolbar-Button-" + button.getButtonId());
    }
  }

  /**
   * Overriden to block the browser's default behaviour.
   */
  public void onBrowserEvent(Event event) {
    super.onBrowserEvent(event);
    // This is required to prevent a Drag & Drop of the Image in the edit text.
    DOM.eventPreventDefault(event);
  }

}
