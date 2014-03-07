package concept.components.user;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;

import concept.SWBaseComponent;
import concept.SWSessionHelper;
import concept.data.SWUser;
import concept.urls.SWURLProvider;
import er.extensions.eof.ERXEC;

public class SWCreateUser extends SWBaseComponent {

	private SWUser _newUser;
	public String errorMessage;
	public String passwordForComparison;

	public SWCreateUser( WOContext context ) {
		super( context );
	}

	public SWUser newUser() {
		if( _newUser == null ) {
			EOEditingContext ec = ERXEC.newEditingContext( session().defaultEditingContext() );
			_newUser = new SWUser();
			ec.insertObject( _newUser );
		}

		return _newUser;
	}

	public WOActionResults saveChanges() {

		EOEditingContext ec = session().defaultEditingContext();

		if( !StringUtilities.hasValue( newUser().name() ) ) {
			return error( "Vinsamlegast sláið inn nafn" );
		}

		if( !StringUtilities.hasValue( newUser().emailAddress() ) ) {
			return error( "Vinsamlegast sláið inn netfang" );
		}

		if( SWUser.emailAddressExists( ec, newUser().emailAddress() ) ) {
			return error( "Þetta netfang er þegar skráð" );
		}

		if( !StringUtilities.hasValue( newUser().password() ) ) {
			return error( "Vinsamlegast sláið inn aðgangsorð" );
		}

		if( !newUser().password().equals( passwordForComparison ) ) {
			return error( "Aðgangsorð er ekki eins í báðum reitum" );
		}

		newUser().loginInContext( context() );
		ec.saveChanges();

		SWSessionHelper.setUserInSession( session(), newUser().localInstanceIn( ec ) );

		errorMessage = null;
		return USHTTPUtilities.redirectTemporary( SWURLProvider.urlForObjectInContext( newUser(), context() ) );
	}

	public WOActionResults error( String message ) {
		errorMessage = message;
		return null;
	}
}