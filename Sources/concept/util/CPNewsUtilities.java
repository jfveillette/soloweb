package concept.util;

import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USEOUtilities;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;

import concept.data.SWFolder;
import concept.data.SWNewsItem;

/**
 * SWNewsUtilities contains some convenience methods to fetch news
 */

public class CPNewsUtilities extends Object {

	private static final NSArray<EOSortOrdering> NEWS_DEFAULT_SORT_ORDERINGS = SWNewsItem.DATE.descs();
	private static final EOQualifier PUBLISHED_QUALIFIER = SWNewsItem.PUBLISHED.eq( 1 );

	/**
	 * Constructs a list of news matching the given parameters.
	 */
	public static NSArray<SWNewsItem> news( EOEditingContext ec, Integer folderID, Integer daysToInclude, Integer daysToExclude, NSArray<EOSortOrdering> sortOrderings, Integer itemsToShow, Integer itemsToSkip, boolean randomSort ) {

		if( sortOrderings == null ) {
			sortOrderings = NEWS_DEFAULT_SORT_ORDERINGS;
		}

		SWFolder folder = USEOUtilities.objectWithPK( ec, SWFolder.ENTITY_NAME, folderID );
		NSArray<SWNewsItem> items = folder.itemsOfType( SWNewsItem.class );
		items = EOQualifier.filteredArrayWithQualifier( items, SWNewsItem.PUBLISHED.eq( 1 ) );
		items = EOSortOrdering.sortedArrayUsingKeyOrderArray( items, sortOrderings );
		return USArrayUtilities.maxObjectsFromArray( items, itemsToShow );

		/*
		EOQualifier qPublished = SWNewsItem.PUBLISHED.eq( 1 );

		EOQualifier qTimeInOK = SWNewsItem.TIME_IN.lt( new NSTimestamp() );
		EOQualifier qTimeInIsNull = SWNewsItem.TIME_IN.isNull();

		EOQualifier qTimeOutOK = SWNewsItem.TIME_OUT.gt( new NSTimestamp() );
		EOQualifier qTimeOutIsNull = SWNewsItem.TIME_OUT.isNull();

		EOQualifier qTimeIn = new EOOrQualifier( new NSArray<EOQualifier>( qTimeInOK, qTimeInIsNull ) );
		EOQualifier qTimeOut = new EOOrQualifier( new NSArray<EOQualifier>( qTimeOutOK, qTimeOutIsNull ) );

		NSMutableArray<EOQualifier> qualArr = new NSMutableArray<EOQualifier>();

		qualArr.addObject( qPublished );
		qualArr.addObject( qTimeIn );
		qualArr.addObject( qTimeOut );

		if( daysToInclude != null ) {
			NSTimestamp now = new NSTimestamp();
			NSTimestamp targetDate = now.timestampByAddingGregorianUnits( 0, 0, -daysToInclude, 0, 0, 0 );
			EOQualifier q3 = new EOKeyValueQualifier( SWNewsItem.DATE_KEY, EOQualifier.QualifierOperatorGreaterThan, targetDate );
			qualArr.addObject( q3 );
		}

		if( daysToExclude != null ) {
			NSTimestamp now = new NSTimestamp();
			NSTimestamp targetDate = now.timestampByAddingGregorianUnits( 0, 0, -daysToExclude, 0, 0, 0 );
			EOQualifier q4 = new EOKeyValueQualifier( SWNewsItem.DATE_KEY, EOQualifier.QualifierOperatorLessThan, targetDate );
			qualArr.addObject( q4 );
		}

		EOAndQualifier qual = new EOAndQualifier( qualArr );
		EOFetchSpecification fs = new EOFetchSpecification( SWNewsItem.ENTITY_NAME, qual, sortOrderings );

		int itemsToFetch = 0;

		if( itemsToShow != null ) {
			itemsToFetch += itemsToShow;
		}

		if( itemsToSkip != null ) {
			itemsToFetch += itemsToSkip;
		}

		fs.setFetchLimit( itemsToFetch );

		NSArray<SWNewsItem> fetchedNewsItems = ec.objectsWithFetchSpecification( fs ).mutableClone();

		if( itemsToSkip != null ) {
			NSMutableArray<SWNewsItem> skippedItemArray = fetchedNewsItems.mutableClone();
			skippedItemArray.removeObjectsInRange( new NSRange( 0, itemsToSkip ) );
			fetchedNewsItems = skippedItemArray;
		}

		if( randomSort ) {
			return USArrayUtilities.arrayByRandomizingArray( fetchedNewsItems );
		}

		return fetchedNewsItems;
		*/
	}
}