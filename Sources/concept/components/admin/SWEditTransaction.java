package concept.components.admin;


import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

import concept.ViewPage;
import concept.data.SWTransaction;
import er.extensions.components.ERXComponent;

/**
 * @author Hugi Þórðarson
 */

public class SWEditTransaction extends ViewPage<SWTransaction> {

	private static final String KVC_NULL = "<com.webobjects.foundation.NSKeyValueCoding$Null>";

	public String currentKey;
	public ERXComponent callingComponent;

	public SWEditTransaction( WOContext context ) {
		super( context );
	}

	public Object currentBeforeValue() {
		Object o = selectedObject().beforeDictionary().objectForKey( currentKey );

		if( KVC_NULL.equals( o ) ) {
			o = null;
		}

		return o;
	}

	public Object currentAfterValue() {
		Object o = selectedObject().afterDictionary().objectForKey( currentKey );

		if( o == null ) {
			o = currentBeforeValue();
		}

		if( KVC_NULL.equals( o ) ) {
			o = null;
		}

		return o;
	}

	public String trClass() {
		Object o = currentAfterValue();

		if( o == null || o.equals( currentBeforeValue() ) ) {
			return null;
		}

		return "sw-modified";
	}

	public WOActionResults returnToCallingcomponent() {
		callingComponent.ensureAwakeInContext( context() );
		return callingComponent;
	}
}