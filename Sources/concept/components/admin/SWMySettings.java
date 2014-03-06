package concept.components.admin;

import is.rebbi.core.util.StringUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;

import concept.CPAdminComponent;
import concept.SWSessionHelper;
import concept.data.SWSite;

public class SWMySettings extends CPAdminComponent {

	public String password;
	public String passwordAgain;

	public SWSite currentSite;
	public String message;

	public SWMySettings( WOContext context ) {
		super( context );
	}

	public NSArray<SWSite> sites() {
		return SWSessionHelper.userInSession( session() ).sites();
	}

	@Override
	public void appendToResponse( WOResponse response, WOContext context ) {
		super.appendToResponse( response, context );
	}

	@Override
	public WOActionResults saveChanges() {
		message = null;

		if( StringUtilities.hasValue( password ) || StringUtilities.hasValue( passwordAgain ) ) {
			if( !StringUtilities.hasValue( password ) || !StringUtilities.hasValue( passwordAgain ) ) {
				return message( "Þú verður að slá aðgangsorðið eins inn í báða reitina til að breyta því." );
			}

			if( !java.util.Objects.equals( password, passwordAgain ) ) {
				return message( "Aðgangsorðið er ekki eins í báðum reitum. Reyndu aftur" );
			}

			user().setPasswordHash( password );
			password = null;
			passwordAgain = null;
		}

		user().editingContext().saveChanges();

		return null;
	}

	private WOActionResults message( String string ) {
		message = string;
		return null;
	}
}