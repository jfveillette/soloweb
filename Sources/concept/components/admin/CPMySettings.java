package concept.components.admin;

import is.rebbi.core.util.StringUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.CPAdminComponent;
import concept.data.SWSite;

/**
 * User settings.
 */

public class CPMySettings extends CPAdminComponent {

	public String password;
	public String passwordAgain;

	public SWSite currentSite;
	public String message;

	public CPMySettings( WOContext context ) {
		super( context );
	}

	public NSArray<SWSite> allSites() {
		return SWSite.allSites( ec() );
	}

	@Override
	public WOActionResults saveChanges() {
		if( StringUtilities.hasValue( password ) ) {
			if( !StringUtilities.hasValue( passwordAgain ) ) {
				return message( "Þú verður að slá aðgangsorðið inn í báða reitina." );
			}

			if( !password.equals( passwordAgain ) ) {
				return message( "Aðgangsorðið er ekki eins í báðum reitum. Reyndu aftur" );
			}

			conceptUser().setPasswordHash( password );
		}

		return super.saveChanges();
	}

	private WOActionResults message( String string ) {
		message = string;
		return null;
	}
}