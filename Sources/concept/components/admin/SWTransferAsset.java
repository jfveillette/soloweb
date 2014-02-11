package concept.components.admin;


import com.webobjects.appserver.WOContext;

import concept.SWGenericTransferComponent;

/**
 * A component for transferring of an SWAsset to a new folder
 */

public class SWTransferAsset extends SWGenericTransferComponent {

	public SWTransferAsset( WOContext context ) {
		super( context );
	}
}