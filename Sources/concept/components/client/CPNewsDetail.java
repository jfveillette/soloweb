package concept.components.client;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.interfaces.HasFakeRelationship;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USHTTPUtilities;
import is.rebbi.wo.util.USMailSender;
import is.rebbi.wo.util.USUtilities;

import java.text.DateFormatSymbols;
import java.text.Format;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSMutableSet;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSTimestampFormatter;

import concept.ViewPage;
import concept.data.SWComment;
import concept.data.SWDocument;
import concept.data.SWNewsItem;
import concept.urls.SWURLProvider;
import concept.util.CPLoc;
import concept.util.Documents;
import er.ajax.AjaxHighlight;

/**
 * The default detail view for an SWNewsItem, including comment publishing.
 */

public class CPNewsDetail extends ViewPage<SWNewsItem> {

	public SWNewsItem currentNewsItem;

	/**
	 * Variables in the UI.
	 */
	public String userName;
	public String userUrl;
	public String userEmailAddress;
	public Integer userNotifyOnNewComments;
	public String text;
	public String hatesSpamString;
	public String expectedSpamAnswer = CPLoc.string( "commentsExpectedSpamAnswer", context() );
	public String errorMessage;

	/**
	 * The comment currently being iterated over in the list.
	 */
	public SWComment currentComment;

	public CPNewsDetail( WOContext context ) {
		super( context );
	}

	public String absoluteURL() {
		return SWURLProvider.urlForObjectInContext( selectedObject(), null );
	}

	public SWDocument document() {
		return Documents.relatedDocument( selectedObject() );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	/**
	 * Now let's publish that comment...
	 */
	public WOActionResults publishComment() {
		String referer = USHTTPUtilities.referer( context().request() );
		String userAgent = USHTTPUtilities.userAgent( context().request() );
		String ipAddress = USHTTPUtilities.ipAddressFromRequest( context().request() );

		if( !expectedSpamAnswer.equalsIgnoreCase( hatesSpamString ) ) {
			return error( "commentsWrongSpamAnswerError", expectedSpamAnswer );
		}

		if( !StringUtilities.stringHasValueTrimmed( text ) ) {
			return error( "commentsNoTextError", null );
		}

		SWComment c = HasFakeRelationship.Util.create( SWComment.class, selectedObject() );
		c.setDate( new NSTimestamp() );
		c.setName( userName );
		c.setUrl( userUrl );
		c.setEmailAddress( userEmailAddress );
		c.setNotifyOnNewComments( userNotifyOnNewComments );
		c.setText( text );
		c.setIpAddress( ipAddress );
		c.setUserAgent( userAgent );

		ec().saveChanges();

		AjaxHighlight.highlight( c );

		NSMutableSet<String> emailAddressesToNotify = new NSMutableSet<String>();

		if( StringUtilities.hasValue( SWSettings.webmasterEmail() ) ) {
			emailAddressesToNotify.addObject( SWSettings.webmasterEmail() );
		}

		for( SWComment nextComment : selectedObject().comments() ) {
			String nextEmailAddress = nextComment.emailAddress();

			if( USUtilities.booleanFromObject( nextComment.notifyOnNewComments() ) && StringUtilities.hasValue( nextEmailAddress ) && !nextEmailAddress.equals( userEmailAddress ) ) {
				emailAddressesToNotify.addObject( nextEmailAddress );
			}
		}

		String emailSubject = "[" + USHTTPUtilities.host( context().request() ) + "] - \"" + selectedObject().name() + "\"";
		String emailContent = c.commentFormattedForEmail( referer );

		USMailSender.sendInMultipleEmails( SWSettings.webmasterEmail(), emailAddressesToNotify.allObjects(), emailSubject, emailContent, null );

		text = null;
		errorMessage = null;

		return null;
	}

	/**
	 * This action is invoked if an error occurs during the publishing process.
	 */
	public WOActionResults error( String errorKey, Object... vars ) {
		errorMessage = StringUtilities.stringWithFormat( CPLoc.string( errorKey, context() ), vars );
		return null;
	}

	public Format dateFormatter() {
		return new NSTimestampFormatter( "%A %e. %B %Y", new DateFormatSymbols( locale() ) );
	}

	public Format timeFormatter() {
		return new NSTimestampFormatter( "%H:%M" );
	}
}