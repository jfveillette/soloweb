package concept.components;

import is.rebbi.wo.interfaces.PointsToEO;
import is.rebbi.wo.urls.SWEOURLProvider;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXStatelessComponent;

public class PointsToEOLink extends ERXStatelessComponent {

	public PointsToEOLink( WOContext context ) {
		super( context );
	}

	public PointsToEO object() {
		return (PointsToEO)valueForBinding( "object" );
	}

	public String href() {
		return SWEOURLProvider.urlForObjectInContext( object().targetEntityName(), object().targetID(), context() );
	}
}