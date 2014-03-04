package concept.data;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.interfaces.SWHasCustomInfo;
import is.rebbi.wo.interfaces.SWTimedContent;
import is.rebbi.wo.interfaces.SWTransferable;
import is.rebbi.wo.util.SWCustomInfo;
import is.rebbi.wo.util.SWTimedContentUtilities;
import is.rebbi.wo.util.USSortable;
import is.rebbi.wo.util.USSortableUtilities;
import is.rebbi.wo.util.USUtilities;

import java.util.Enumeration;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.data.auto._SWComponent;
import concept.util.HumanReadable;

/**
 * An SWComponent represents a part of a page
 */

public class SWComponent extends _SWComponent implements SWTimedContent, SWTransferable, USSortable, SWHasCustomInfo, HumanReadable {

	private SWCustomInfo _customInfo;

	public SWCustomInfo customInfo() {
		if( _customInfo == null ) {
			_customInfo = new SWCustomInfo( this );
		}

		return _customInfo;
	}

	public void changeSortOrder( int offset ) {
		NSMutableArray<SWComponent> components = (NSMutableArray<SWComponent>)page().sortedComponents();

		int i = components.indexOfObject( this );
		components.removeObjectAtIndex( i );
		components.insertObjectAtIndex( this, i + offset );

		Enumeration<SWComponent> e = components.objectEnumerator();

		while( e.hasMoreElements() ) {
			SWComponent aComponent = e.nextElement();
			aComponent.setSortNumber( new Integer( components.indexOfObject( aComponent ) ) );
		}
	}

	public boolean hasPicture() {
		return picture() != null;
	}

	public boolean isFirst() {
		return USSortableUtilities.isFirst( this );
	}

	public boolean isLast() {
		return USSortableUtilities.isLast( this, page().components() );
	}

	public String textTwoWithBreaks() {
		return StringUtilities.convertBreakString( textTwo() );
	}

	public boolean isPublished() {
		return USUtilities.numberIsTrue( published() ) && isTimeValid();
	}

	@Override
	public void transferOwnership( EOEnterpriseObject newOwner ) {
		this.page().removeComponent( this );
		((SWPage)newOwner).insertComponentAtIndex( this, 0 );
	}

	public SWComponent createCopy() {
		EOEditingContext ec = editingContext();
		SWComponent nc = new SWComponent();

		nc.setTextOne( textOne() );
		nc.setTextTwo( textTwo() );
		nc.setPicture( picture() );
		nc.setCustomInfoData( customInfoData() );
		nc.setTemplateName( templateName() );
		nc.setEncodeBreaks( encodeBreaks() );
		nc.setPublished( published() );
		nc.setSortNumber( sortNumber() );
		nc.setTimeIn( timeIn() );
		nc.setTimeOut( timeOut() );

		Enumeration<SWPictureLink> e = swPictureLinks().objectEnumerator();
		while( e.hasMoreElements() ) {
			SWPictureLink link = e.nextElement();
			SWPictureLink newLink = new SWPictureLink();
			newLink.setAlign( link.align() );
			newLink.setPicture( link.picture() );
			newLink.setLinkToLarge( link.linkToLarge() );
			newLink.setName( link.name() );
			newLink.setShowCaption( link.showCaption() );
			newLink.setSize( link.size() );
			//newLink.setComponent( nc );
			newLink.addObjectToBothSidesOfRelationshipWithKey( nc, "component" );
			ec.insertObject( newLink );
		}

		ec.insertObject( nc );
		return nc;
	}

	@Override
	public boolean isTimeValid() {
		return SWTimedContentUtilities.validateDisplayTime( this );
	}

	/**
	 * Added for backwards compatibility for sw28 webs that were using their own ButurTemplate wods.
	 */
	public String buturID() {
		return "but_" + primaryKey();
	}

	public void addTextToIndex( StringBuilder b ) {
		if( templateName().startsWith( "ButurTemplate" ) ) {
			String s = textOne();
			if( StringUtilities.hasValue( s ) ) {
				b.append( s );
				b.append( " " );
			}
			s = textTwo();
			if( StringUtilities.hasValue( s ) ) {
				b.append( s );
				b.append( " " );
			}
		}
	}

	public NSArray<SWPicture> pictures() {
		NSMutableArray<SWPicture> results = new NSMutableArray<>();

		for( SWPictureLink link : swPictureLinks() ) {
			results.addObject( link.picture() );
		}

		return results;
	}

	@Override
	public String toStringHuman() {
		if( page() != null ) {
			return page().toStringHuman() + " - " + sortNumber();
		}

		return "Ótengt efni";
	}
}