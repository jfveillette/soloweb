package concept.components.admin;

import is.rebbi.wo.util.HumanReadable;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USTimestampUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import concept.SWSessionHelper;
import concept.data.SWTransaction;
import concept.data.SWUser;
import er.extensions.components.ERXComponent;

public class SWTransactionLogViewer extends ERXComponent {

	/**
	 * The date from to search for.
	 */
	public NSTimestamp dateFrom = USTimestampUtilities.normalizeTimestampToMidnight( new NSTimestamp() );

	/**
	 * The date to to search for.
	 */
	public NSTimestamp dateTo = dateFrom.timestampByAddingGregorianUnits( 0, 0, 1, 0, 0, 0 );

	public SWUser currentUser;
	public SWUser selectedUser;

	public String targetEntityName;
	public String targetID;

	/**
	 * The transactions displayed on the page.
	 */
	public NSArray<SWTransaction> transactions;

	/**
	 * Transaction currently being iterated over.
	 */
	public SWTransaction currentTransaction;

	public SWTransactionLogViewer( WOContext context ) {
		super( context );
	}

	private EOEditingContext ec() {
		return session().defaultEditingContext();
	}

	/**
	 * @return All users in the DB.
	 */
	public NSArray<SWUser> users() {
		return SWUser.fetchAllSWUsers( ec(), SWUser.NAME.ascInsensitives() );
	}

	public WOActionResults search() {

		NSMutableArray<EOQualifier> a = new NSMutableArray<>();

		if( dateFrom != null ) {
			a.add( USEOUtilities.qualifierBetweenDates( SWTransaction.DATE_KEY, dateFrom, dateTo ) );
		}

		if( selectedUser != null ) {
			a.add( SWTransaction.USER.eq( selectedUser ) );
		}

		if( targetEntityName != null ) {
			a.add( SWTransaction.TARGET_ENTITY_NAME.eq( targetEntityName ) );
		}

		if( targetID != null ) {
			a.add( SWTransaction.TARGET_ID.eq( targetID ) );
		}

		EOQualifier q = new EOAndQualifier( a );
		EOFetchSpecification fs = new EOFetchSpecification( SWTransaction.ENTITY_NAME, q, SWTransaction.DATE.descs() );
		fs.setPrefetchingRelationshipKeyPaths( new NSArray<String>( "user" ) );
		transactions = ec().objectsWithFetchSpecification( fs );

		return null;
	}

	public String currentDisplayString() {
		return HumanReadable.DefaultImplementation.toStringHuman( currentTransaction.targetObject() );
	}

	public WOActionResults me() {
		selectedUser = SWSessionHelper.userInSession( session() );
		return null;
	}
}