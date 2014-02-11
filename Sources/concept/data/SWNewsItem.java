package concept.data;

import is.rebbi.wo.interfaces.HasFakeRelationship;
import is.rebbi.wo.interfaces.HumanReadable;
import is.rebbi.wo.interfaces.SWHasCustomInfo;
import is.rebbi.wo.interfaces.SWTimedContent;
import is.rebbi.wo.interfaces.SWUserInterface;
import is.rebbi.wo.interfaces.TimeStamped;
import is.rebbi.wo.interfaces.UserStamped;
import is.rebbi.wo.util.SWCustomInfo;
import is.rebbi.wo.util.SWTimedContentUtilities;
import is.rebbi.wo.util.USUtilities;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;

import concept.data.auto._SWNewsItem;
import concept.util.Documents;

public class SWNewsItem extends _SWNewsItem implements SWTimedContent, SWHasCustomInfo, TimeStamped, UserStamped, HumanReadable {

	private SWCustomInfo _customInfo;

	public SWCustomInfo customInfo() {
		if( _customInfo == null ) {
			_customInfo = new SWCustomInfo( this );
		}

		return _customInfo;
	}

	@Override
	public void awakeFromInsertion( EOEditingContext anEC ) {
		super.awakeFromInsertion( anEC );
		setDate( new NSTimestamp() );
	}

	public String name() {
		return heading();
	}

	public void setName( String value ) {
		setHeading( value );
	}

	/**
	 * Indicates if this object has been published, and if it's display time has come
	 */
	public boolean isPublished() {
		return USUtilities.numberIsTrue( published() ) && isTimeValid();
	}

	/**
	 * Indicates if this object's display time has come and has not expired
	 */
	@Override
	public boolean isTimeValid() {
		return SWTimedContentUtilities.validateDisplayTime( this );
	}

	/**
	 * @return Related documents
	 */
	public NSArray<SWDocument> documents() {
		return Documents.relatedDocuments( this );
	}

	public NSArray<SWComment> comments() {
		return HasFakeRelationship.Util.relatedObjects( SWComment.class, this, SWComment.DATE.descs() );
	}

	@Override
	public String toStringHuman() {
		return name();
	}

	@Override
	public void setCreatedBy( SWUserInterface user ) {
		super.setCreatedBy( (SWUser)user );

	}

	@Override
	public void setModifiedBy( SWUserInterface user ) {
		super.setModifiedBy( (SWUser)user );
	}
}