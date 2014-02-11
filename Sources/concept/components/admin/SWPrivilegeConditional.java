package concept.components.admin;

import is.rebbi.wo.util.SWSettings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation._NSStringUtilities;

import concept.SWAdminComponent;

public class SWPrivilegeConditional extends SWAdminComponent {

	private static final Logger logger = LoggerFactory.getLogger( SWPrivilegeConditional.class );

	public SWPrivilegeConditional( WOContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	public String identifier() {
		return (String)valueForBinding( "identifier" );
	}

	public EOEnterpriseObject record() {
		return (EOEnterpriseObject)valueForBinding( "record" );
	}

	public boolean negate() {
		Object o = valueForBinding( "negate" );

		if( o != null ) {
			return ((Boolean)o).booleanValue();
		}

		return false;
	}

	public Object condition() {
		return valueForBinding( "condition" );
	}

	public boolean hasPrivilege() {

		if( !SWSettings.booleanForKey( "enablePrivileges" ) ) {
			return true;
		}

		if( record() != null && identifier() != null ) {
			return user().hasPrivilegeFor( record(), identifier() );
		}

		if( condition() != null ) {
			return extractBooleanValue( condition() );
		}

		return false;
	}

	private boolean extractBooleanValue( Object obj ) {
		boolean flag = true;
		if( obj != null ) {
			if( obj instanceof Number ) {
				if( ((Number)obj).intValue() == 0 ) {
					flag = false;
				}
			}
			else if( obj instanceof String ) {
				String s = (String)obj;
				int i = s.length();
				if( i >= 2 && i <= 5 ) {
					String s1 = s.toLowerCase();
					if( s1.equals( "no" ) || s1.equals( "false" ) || s1.equals( "nil" ) || s1.equals( "null" ) ) {
						flag = false;
					}
				}
				if( flag && _NSStringUtilities.isNumber( s ) ) {
					try {
						if( Integer.parseInt( s ) == 0 ) {
							flag = false;
						}
					}
					catch( NumberFormatException numberformatexception ) {
						logger.debug( "<WOAssociation> Exception while evaluating value in component: ", numberformatexception );
					}
				}
			}
			else if( obj instanceof Boolean ) {
				flag = ((Boolean)obj).booleanValue();
			}
		}
		else {
			flag = false;
		}
		return flag;
	}
}