package concept.data;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.interfaces.HasFakeRelationship;
import concept.data.auto._SWComment;
import er.extensions.eof.ERXGenericRecord;

/**
 * A comment.
 */

public class SWComment extends _SWComment implements HasFakeRelationship {

	private ERXGenericRecord _targetObject;

	/**
	 * @return a usable version of the URL entered.
	 */
	public String fixedUrl() {
		return StringUtilities.fixUrl( url() );
	}

	/**
	 * @return true if this record has no URL.
	 */
	public boolean noUrl() {
		return !StringUtilities.hasValue( url() );
	}

	/**
	 * @return The text as displayed to the user.
	 */
	public String textForDisplay() {
		String s = text();
		s = StringUtilities.convertBreakString( s );
		return s;
	}

	/**
	 * @return The comment formatted for notification e-mails.
	 */
	public String commentFormattedForEmail( String refererURL ) {
		StringBuilder b = new StringBuilder();
		b.append( name() );
		b.append( " skrifar:" );
		b.append( "\n" );
		b.append( "\n" );
		b.append( text() );
		b.append( "\n" );
		b.append( "\n" );
		b.append( refererURL );
		return b.toString();
	}

	/**
	 * @return A unique identified for this comment, used in comment lists.
	 */
	public String anchor() {
		return "swcomment_" + primaryKey();
	}

	public ERXGenericRecord targetObject() {
		if( _targetObject == null) {
			_targetObject = HasFakeRelationship.Util.targetObject( this );
		}

		return _targetObject;
	}
}