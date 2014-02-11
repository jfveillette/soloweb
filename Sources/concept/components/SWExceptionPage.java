package concept.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSPropertyListSerialization;
import com.webobjects.woextensions.WOExceptionParser;
import com.webobjects.woextensions.WOParsedErrorLine;

import concept.Concept;
import er.ajax.AjaxUtils;
import er.extensions.appserver.ERXApplication;
import er.extensions.components.ERXComponent;

/**
 * A little nicer version of WOExceptionPage.
 */

public class SWExceptionPage extends ERXComponent {

	private static final Logger logger = LoggerFactory.getLogger( SWExceptionPage.class );

	private Throwable _exception;
	public NSArray<String> _reasonLines;
	public String currentReasonLine;

	public transient WOExceptionParser error;
	public transient WOParsedErrorLine errorline;

	public SWExceptionPage( WOContext aContext ) {
		super( aContext );
	}

	/**
	 * Returns a response, based on if the user is logged in or not.
	 */
	@Override
	public void appendToResponse( WOResponse r, WOContext c ) {
		super.appendToResponse( r, c );
		AjaxUtils.addStylesheetResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "bootstrap-3/css/bootstrap.min.css" );
		AjaxUtils.addScriptResourceInHead( context(), r, Concept.sw().frameworkBundleName(), "bootstrap-3/js/bootstrap.min.js" );
	}

	@Override
	public boolean isEventLoggingEnabled() {
		return false;
	}

	public Throwable exception() {
		return _exception;
	}

	public void setException( Throwable value ) {
		error = new WOExceptionParser( value );
		_exception = value;
	}

	@Override
	public WOResponse generateResponse() {
		WOResponse response = super.generateResponse();
		error = null;
		errorline = null;
		return response;
	}

	public NSArray<String> reasonLines() {
		if( _reasonLines == null ) {
			String aMessage = _exception.getMessage();
			if( aMessage != null ) {
				_reasonLines = NSArray.componentsSeparatedByString( _exception.getMessage(), "\n" );
			}
			else {
				_reasonLines = new NSArray<String>();
			}
		}
		return _reasonLines;
	}

	public String errorMessage() {
		StringBuilder buffer = new StringBuilder( 128 );
		buffer.append( "Error : " );
		buffer.append( _exception.getClass().getName() );
		buffer.append( " - Reason :" );
		buffer.append( _exception.getMessage() );
		return new String( buffer );
	}

	public static WOResponse reportException( Throwable exception, WOContext context, NSDictionary extraInfo ) {
		logger.error( "Exception caught: " + exception.getMessage() + "\nExtra info: " + NSPropertyListSerialization.stringFromPropertyList( extraInfo ) + "\n", exception );
		SWExceptionPage nextPage = ERXApplication.erxApplication().pageWithName( SWExceptionPage.class, context );
		nextPage.setException( exception );
		return nextPage.generateResponse();
	}
}