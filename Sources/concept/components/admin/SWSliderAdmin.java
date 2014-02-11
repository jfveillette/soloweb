package concept.components.admin;


import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.SWGenericComponent;
import concept.data.SWComponent;

public class SWSliderAdmin extends SWGenericComponent {

	public String currentDisplayType, currentSize, currentTransitionType;
	private NSMutableDictionary _displayTypes = null;
	private NSMutableDictionary _transitionTypes = null;

	public SWSliderAdmin( WOContext context ) {
		super( context );

		// Build the displayTypes dictionary
		_displayTypes = new NSMutableDictionary();
		_displayTypes.setObjectForKey( "Myndir úr möppu myndar", "imagesfromfolder" );
		_displayTypes.setObjectForKey( "Fréttir úr fréttamöppu", "newsfromfolder" );
		_displayTypes.setObjectForKey( "Bútar af síðu með hlekkjunarheiti", "componentsfrompage" );

		// Build the transitionTypes dictionary
		_transitionTypes = new NSMutableDictionary();
		_transitionTypes.setObjectForKey( "Lárétt", "horizontal" );
		_transitionTypes.setObjectForKey( "Lóðrétt", "vertical" );
		_transitionTypes.setObjectForKey( "Fade", "fade" );
	}

	public NSArray displayTypes() {
		return _displayTypes.allKeys();
	}

	public NSArray transitionTypes() {
		return _transitionTypes.allKeys();
	}

	public String currentDisplayTypeDescription() {
		return (String)_displayTypes.valueForKey( currentDisplayType );
	}

	public String currentTransitionTypeDescription() {
		return (String)_transitionTypes.valueForKey( currentTransitionType );
	}

	@Override
	public void setCurrentComponent( SWComponent newOne ) {
		super.setCurrentComponent( newOne );
		currentComponent().setTemplateName( "SWSlider" );
	}
}