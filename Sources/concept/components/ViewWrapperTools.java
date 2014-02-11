package concept.components;

import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

import concept.ViewPage;
import concept.data.SWFavorite;
import concept.data.SWUser;
import concept.urls.SWURLProvider;
import er.extensions.eof.ERXGenericRecord;

public class ViewWrapperTools extends ViewPage<ERXGenericRecord> {

	public ViewWrapperTools( WOContext context ) {
		super( context );
	}

	public boolean allowFavorites() {
		return !(selectedObject() instanceof SWUser);
	}

	public WOActionResults createFavorite() {
		SWFavorite.createFavorite( selectedObject(), conceptUser() );
		selectedObject().editingContext().saveChanges();
		return null;
	}

	public WOActionResults deleteFavorite() {
		SWFavorite.deleteFavorite( ec(), selectedObject(), conceptUser() );
		ec().saveChanges();
		return null;
	}

	public boolean isFavorite() {
		return conceptUser() != null && SWFavorite.isFavorite( conceptUser(), selectedObject() );
	}

	public String urlOfSelectedObject() {
		String domain = SWSettings.defaultDomainName();

		if( domain == null ) {
			domain = USHTTPUtilities.host( context().request() );
		}

		return "http://" + domain + SWURLProvider.urlForObjectInContext( selectedObject(), context() );
	}
}