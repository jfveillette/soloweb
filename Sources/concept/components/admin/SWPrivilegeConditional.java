package concept.components.admin;

import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USUtilities;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEnterpriseObject;

import concept.CPAdminComponent;

/**
 * Conditional granting access to the current user based on a named privilege.
 */

public class SWPrivilegeConditional extends CPAdminComponent {

	public SWPrivilegeConditional( WOContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	@Override
	public boolean isStateless() {
		return true;
	}

	public String identifier() {
		return stringValueForBinding( "identifier" );
	}

	public EOEnterpriseObject record() {
		return (EOEnterpriseObject)valueForBinding( "record" );
	}

	public Object condition() {
		return valueForBinding( "condition" );
	}

	public boolean hasPrivilege() {

		if( !SWSettings.privilegesEnabled() ) {
			return true;
		}

		if( record() != null && identifier() != null ) {
			return conceptUser().hasPrivilegeFor( record(), identifier() );
		}

		if( condition() != null ) {
			return USUtilities.booleanFromObject( condition() );
		}

		return false;
	}
}