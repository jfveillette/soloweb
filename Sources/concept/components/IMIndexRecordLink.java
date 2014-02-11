package concept.components;

import is.rebbi.wo.interfaces.PointsToEO;

import com.webobjects.appserver.WOContext;

import concept.urls.SWEOURLProvider;
import er.extensions.components.ERXStatelessComponent;

public class IMIndexRecordLink extends ERXStatelessComponent {

	public IMIndexRecordLink( WOContext context ) {
		super( context );
	}

	public PointsToEO object() {
		return (PointsToEO)valueForBinding( "object" );
	}

	public String href() {
		return SWEOURLProvider.urlForObjectInContext( object().targetEntityName(), object().targetID(), context() );
	}
}