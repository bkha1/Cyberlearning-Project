package cyber.learning.project.client;

import gwtquery.plugins.draggable.client.gwt.DraggableWidget; 

import com.google.gwt.core.client.EntryPoint; 
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.youtube.client.YouTubeEmbeddedPlayer;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CyberLearning implements EntryPoint {//test comment for test commit
	
	/**
	 * Declare variables
	 */
	AbsolutePanel contentPanel = new AbsolutePanel();
	String selectedText; //Brian added -bkha1
	boolean sndOn = false;
	boolean uploadVisible = false;
	SoundController sController = new SoundController(); //sound stuff - brian - bkha1
	Sound sound; //= sController.createSound(Sound.MIME_TYPE_AUDIO_OGG,"https://dl.dropbox.com/u/22130680/testfolder/air.ogg");
	String sndLink;//will contain the url to the sound
	TextArea sndArea = new TextArea();
	TextArea someText = new TextArea();//moved this from inside onModuleLoad - Brian - bkha1
	Image img;
	
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private boolean toolbarVisible = false;
	private boolean resizeEnabled = false;
	private String uploadFileName = "";
	
	public enum fileType {
		IMAGE,
		SOUND
	};
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Label errorLabel = new Label();
		final HorizontalPanel editHorizontalPanel_1 = new HorizontalPanel();

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel rootPanel = RootPanel.get("bookContainer");
		RootPanel.get("errorLabelContainer").add(errorLabel);
	
		/* Example code for embedding a YouTube clip.
		 
		   // String corresponds to the alphanumeric ID at the end of YouTube URL. //
		   YouTubeEmbeddedPlayer ytPlayer = new YouTubeEmbeddedPlayer("zn7-fVtT16k");
		   ytPlayer.setWidth("300px");
		   ytPlayer.setHeight("300px");
		   RootPanel.get().add(ytPlayer);
		 */
				
		VerticalPanel flowPanel = new VerticalPanel();
		rootPanel.add(flowPanel, 100, 100);
		flowPanel.setSize("1000px", "600px");
		
		final MenuBar menuBar = new MenuBar(false);
		flowPanel.add(menuBar);
		
		MenuItem mntmSearchtext = new MenuItem("my freBook", false, (Command) null);
		menuBar.addItem(mntmSearchtext);
		menuBar.setSize("965px", "100%");
		
		HorizontalPanel contentHorizontalPanel = new HorizontalPanel();
		flowPanel.add(contentHorizontalPanel);
		contentHorizontalPanel.setSize("100%", "500px");
		
		final VerticalPanel toolbarPanel = new VerticalPanel();
		contentHorizontalPanel.add(toolbarPanel);
		toolbarPanel.setSize("80px", "144px");
		
		contentHorizontalPanel.add(contentPanel);
		contentPanel.setSize("885px", "600px");
		
		/*
		 * Toolbar items for editing
		 */	
		//create "Text" item
		final PushButton newTextArea = new PushButton(new Image("cyberlearning/gwt/clean/images/text_icon.png"));
		newTextArea.setSize("90px", "80px");
		toolbarPanel.add(newTextArea);
		//configure as draggable and add to content area
		newTextArea.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event) {
				//create the new draggable object
				final Widget draggableText = createDraggableText();
				RichTextToolbar toolBar = new RichTextToolbar(((DraggableWidget<RichTextArea>) draggableText).getOriginalWidget());
				contentPanel.add(toolBar, 0, 0);
				contentPanel.add(draggableText, 0, 50);
				draggableText.setSize("90%", "75px");
				
				//use "CTRL" key to alternate between drag and resize mode
				newTextArea.addMouseDownHandler(new MouseDownHandler()
				{

					@Override
					public void onMouseDown(MouseDownEvent event) {
						//if "CTRL" key pressed, user resizing widget
						if(event.isControlKeyDown())
						{
							((DraggableWidget<TextArea>) draggableText).setDisabledDrag(true);
						}
					}
				});
				newTextArea.addMouseUpHandler(new MouseUpHandler()
				{
					@Override
					public void onMouseUp(MouseUpEvent event) {
						((DraggableWidget<TextArea>) draggableText).setDisabledDrag(false);
					}
					
				});				
			}	
		});
		
		//create "Image" item
		PushButton newImage = new PushButton(new Image("cyberlearning/gwt/clean/images/image_icon.PNG"));
		newImage.setSize("90px", "90px");
		toolbarPanel.add(newImage);
		newImage.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//allow user to upload sound file
				if(!uploadVisible)
				{	
					editHorizontalPanel_1.add(uploadNewFile(fileType.IMAGE));
					uploadVisible = true;
				}
				//configure as draggable and add to toolbar
				Widget draggableImage = createDraggableImage();
				contentPanel.add(draggableImage);	
				
			}		
		});

				
		//create "Video" item
		PushButton newVideo = new PushButton(new Image("cyberlearning/gwt/clean/images/video_icon.png"));
		newVideo.setSize("90px", "70px");
		toolbarPanel.add(newVideo);
		newVideo.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//configure as draggable and add to toolbar
				Widget draggableVideo = createDraggableVideo();
				contentPanel.add(draggableVideo);				
			}	
		});
		
		//Sound Wrapper Stuff - Brian - bkha1
		PushButton sndButton = new PushButton(new Image("cyberlearning/gwt/clean/images/sound_icon.png"));
		sndButton.setSize("90px", "90px");
		toolbarPanel.add(sndButton);
		sndButton.addClickHandler(new ClickHandler() 
		{
			@Override
			public void onClick(ClickEvent event) {
				//allow user to upload sound file
				if(!uploadVisible)
				{
					editHorizontalPanel_1.add(uploadNewFile(fileType.SOUND));
					uploadVisible = true;
				}
				//insert button to play user's sound
				Widget draggableSound = createDraggableSound();
				contentPanel.add(draggableSound);
			}			
		});
		
		Button sndOffButton = new Button("Sound Off");//button for sound off
		//toolbarPanel.add(sndOffButton);
		sndOffButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				if(sndOn == true)
				{
				sound.stop();//turns off song if its playing
				sndOn = false;
				}
			}
		});
		
		sndArea.setText("http://www.public.asu.edu/~bkha1/air.ogg");//default link
		
		//end sound test stuff
        
		//after all elements added, hide toolbar - items start out in a weird place if it starts visible
		DOM.setStyleAttribute(toolbarPanel.getElement(), "visibility", "hidden");
		contentPanel.setSize("885px", "100%");					

		
		//create "Table" item
		//CellTable<Object> cellTable = new CellTable<Object>();
		//Column<Object, ?> col = null;
		//cellTable.addColumn(col);
		//configure as draggable and add to toolbar		
		//Widget draggableTable = createDraggableTable(cellTable);
		//toolbarPanel.add(draggableTable);
		//draggableTable.setSize("90%", "141px");
		
		/*
		 * Content area for reading
		 */
		
		//Highlighting Stuff - Brian
		//Button highlightBtn = new Button ("Highlight Selection");//, new ClickListener()
		/*{
			public void onClick(Widget sender)
			{
				Window.alert("HOW HIGH?");
			}
		});*/
		//toolbarPanel.add(highlightBtn);
		//highlightBtn.setWidth("101px");
		//String testText = someText.getSelectedText();
		//highlightBtn.addClickHandler(new ClickHandler()
		//{
		//public void onClick(ClickEvent event)
		//{
			//someText.getSelectedText().toUpperCase();
			//someText.
			//someText.setReadOnly(true);
			//Window.alert(selectedText);
		//}
		//});
		
		flowPanel.add(editHorizontalPanel_1);
		editHorizontalPanel_1.setWidth("100%");
		
		Button viewToolbarBtn = new Button("New button");
		editHorizontalPanel_1.add(viewToolbarBtn);
		
		editHorizontalPanel_1.add(sndArea);//added these for convenience and sound link testing -bkha
		editHorizontalPanel_1.add(sndOffButton);//bkha1
		
		viewToolbarBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(toolbarVisible)
				{
					DOM.setStyleAttribute(toolbarPanel.getElement(), "visibility", "hidden");
					toolbarPanel.setSize("0px", "100%");
					contentPanel.setSize("885px", "100%");
				}
				else
				{
					DOM.setStyleAttribute(toolbarPanel.getElement(), "visibility", "");
					toolbarPanel.setSize("80px", "100%");
					contentPanel.setSize("965px", "100%");					
				}
				toolbarVisible = !toolbarVisible;				
			}
		});
		viewToolbarBtn.setText("View Toolbar");
	}
	
	Widget createDraggableText()
	{
		//Rich Text Editor - Brian - bkha1
		RichTextArea textArea = new RichTextArea();
		textArea.setText("\tLorem ipsum dolor sit amet, consectetur adipiscing elit. Ut viverra pulvinar tellus et cursus. Nam viverra dapibus libero et consequat. Aenean imperdiet erat nec risus cursus ac pretium mauris ultrices. Aliquam mollis tellus at nibh bibendum fringilla. Quisque ac purus quis lectus auctor placerat. Maecenas eu lorem sed ligula consequat sagittis. Mauris pulvinar nulla a dolor lobortis vehicula. Quisque in elit vel felis aliquam pulvinar eu et nulla. Phasellus eu nibh velit. Pellentesque porttitor eros id arcu placerat aliquam. Duis eget arcu non magna pretium cursus in et nisl. Praesent sollicitudin pharetra risus hendrerit convallis. Vestibulum bibendum lobortis quam in faucibus. Morbi quis leo nibh. Quisque cursus euismod augue, eu malesuada libero interdum in. Quisque volutpat gravida ligula eget blandit.");		//contentPanel.setWidget(25,0,textArea);
		textArea.setSize("200px","150px");
		//end rich text editor stuff
		
		//configure as draggable
		DraggableWidget<RichTextArea> draggableText = new DraggableWidget<RichTextArea>(textArea);
		draggableText.setDraggingCursor(Cursor.MOVE);
		
		return draggableText;
	}
	
	Widget createDraggableImage()
	{		
		//configure as draggable
		Image newImg = new Image("cyberlearning/gwt/clean/images/image_icon.PNG");
		DOM.setStyleAttribute(newImg.getElement(),  "border",  "2px solid blue");
		DraggableWidget<Image> draggableImage = new DraggableWidget<Image>(newImg);
		draggableImage.setDraggingCursor(Cursor.MOVE);
		img = null;
		
		return draggableImage;
	}
	
	Widget createDraggableVideo()
	{	
		AbsolutePanel videoContainer = new AbsolutePanel();
		videoContainer.setSize("300px", "300px");
		DOM.setStyleAttribute(videoContainer.getElement(), "border", "2px solid blue");
	
		//Prompt the user for a specific YouTube video
		String alphaId = Window.prompt("Enter alphanumeric ID at the end of YouTube URL:", "");

		// String corresponds to the alphanumeric ID at the end of YouTube URL. //
		//YouTubeEmbeddedPlayer ytPlayer = new YouTubeEmbeddedPlayer("zn7-fVtT16k");
		YouTubeEmbeddedPlayer ytPlayer = new YouTubeEmbeddedPlayer(alphaId);
		ytPlayer.setWidth("300px");
		ytPlayer.setHeight("300px");
		videoContainer.add(ytPlayer);
		DraggableWidget<AbsolutePanel> draggableVideoContainer = new DraggableWidget<AbsolutePanel>(videoContainer);
		draggableVideoContainer.setDraggingCursor(Cursor.MOVE);
		
		return draggableVideoContainer;
	}
	
	//NOTE: dragging sound button seems to be buggy, cant get it to let go once i've clicked on the widget, always follows the cursor - bkha1
	Widget createDraggableSound()
	{		
		final Image playImg = new Image("cyberlearning/gwt/clean/images/play_icon.png");
		final Image stopImg = new Image("cyberlearning/gwt/clean/images/stop_icon.png");
		AbsolutePanel soundContainer = new AbsolutePanel();
		soundContainer.setSize("52px", "52px");
		DOM.setStyleAttribute(soundContainer.getElement(), "border", "2px solid blue");
		final PushButton newSound = new PushButton(playImg);
		newSound.setSize("50px", "50px");
		soundContainer.add(newSound);
		final DraggableWidget<AbsolutePanel> draggableButton = new DraggableWidget<AbsolutePanel>(soundContainer);
		draggableButton.setDraggingCursor(Cursor.MOVE);
		newSound.addDoubleClickHandler(new DoubleClickHandler(){
			@Override
			public void onDoubleClick(DoubleClickEvent event)
			{
				draggableButton.setDisabledDrag(true);
				
				//changed sound on logic cus toggling doesnt seem to work well enough - bkha1
				if(!sndOn)
				{
					sndLink = sndArea.getText();//grabs link from textbox
					sound = sController.createSound(Sound.MIME_TYPE_AUDIO_OGG,sndLink);//sets up the sound
					sound.setLooping(true);
					//sound.play();
					if(sound.play() == true)//plays sound
					{
					sndOn = true;
					}
				}
				/*
				if(!sndOn)
				{
					sound.play();
					newSound.getUpFace().setImage(stopImg);
				}
				else
				{
					sound.stop();
					newSound.getUpFace().setImage(playImg);
				}
				sndOn=!sndOn;
				*/
				
				draggableButton.setDisabledDrag(false);
			}
			
		});
		
		return draggableButton;
	}
	Widget createDraggableTable(CellTable<Object> table)
	{
		//configure as draggable
		DraggableWidget<CellTable<Object>> draggableTable = new DraggableWidget<CellTable<Object>>(table);
		draggableTable.setDraggingCursor(Cursor.MOVE);
		draggableTable.setDisabledDrag(true);
		
		return draggableTable;		
	}
	
	@SuppressWarnings("deprecation")
	public FormPanel uploadNewFile(final fileType f)
	{		
		//Prompt the user for a specific sound
		final FormPanel uploadForm = new FormPanel();
		uploadForm.setAction(GWT.getModuleBaseURL() + "upload");
		// Because we're going to add a FileUpload widget, we'll need to set the
	    // form to use the POST method, and multipart MIME encoding.
		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		uploadForm.setMethod(FormPanel.METHOD_POST);

	    // Create a panel to hold all of the form widgets.
	    VerticalPanel panel = new VerticalPanel();
	    uploadForm.setWidget(panel);

	    // Create a TextBox, giving it a name so that it will be submitted.
	    //final TextBox tb = new TextBox();
	    //tb.setName("textBoxFormElement");
	    //panel.add(tb);

	    // Create a FileUpload widget.
	    final FileUpload upload = new FileUpload();
	    upload.setName("uploadFormElement");
	    panel.add(upload);

	    // Add a 'submit' button.
	    panel.add(new Button("Submit", new ClickListener() {
	      public void onClick(Widget sender) {
	    	  uploadForm.submit();
	      }
	    }));

	    // Add an event handler to the form.
	    uploadForm.addFormHandler(new FormHandler() {
	      public void onSubmit(FormSubmitEvent event) {
	        // This event is fired just before the form is submitted. We can take
	        // this opportunity to perform validation.
	        //if (tb.getText().length() == 0) {
	        //  Window.alert("The text box must not be empty");
	        //  event.setCancelled(true);
	        //}
	        //else
	        {
	        	System.out.println(upload.getFilename());
	        	uploadFileName = "cyberlearning/gwt/clean/uploads/" + upload.getFilename();
	        	if(f == fileType.IMAGE)
	        	{
	        		img = new Image(uploadFileName);
	        	}
	        	else
	        	{
	        		//sound = sController.createSound(Sound.MIME_TYPE_AUDIO_MP4, uploadFileName);
	        	}
				uploadFileName.equals("");
	        }
	      }
	      
	      @Override
	      public void onSubmitComplete(FormSubmitCompleteEvent event) {
	        // When the form submission is successfully completed, this event is
	        // fired. Assuming the service returned a response of type text/html,
	        // we can get the result text here (see the FormPanel documentation for
	        // further explanation).
	        Window.alert(event.getResults());
	      }
	    });
	    
	    return uploadForm;
	    
	}
}
