package concept.components.admin;

import is.rebbi.wo.interfaces.SWFolderInterface;

import com.webobjects.appserver.WOContext;

import concept.SWGenericTransferComponent;

public class SWTransferFolder extends SWGenericTransferComponent {

	public SWTransferFolder( WOContext context ) {
		super( context );
	}

	public boolean isFolder() {
		return record instanceof SWFolderInterface;
	}
}