package concept;

import java.util.Random;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;

public class SWFCKTextEditing extends WOComponent {

	private String _content;
	private String _editorName;
	private static Random RANDOM = new Random();

	public SWFCKTextEditing( WOContext context ) {
		super( context );
	}

	public String content() {
		return _content;
	}

	public void setContent( String newContent ) {
		_content = newContent;
	}

	public String editorName() {
		if( _editorName == null ) {
			_editorName = "editor" + RANDOM.nextInt( 320000 );
		}

		return _editorName;
	}
}