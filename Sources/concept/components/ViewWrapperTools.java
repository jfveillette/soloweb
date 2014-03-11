package concept.components;

import is.rebbi.wo.urls.SWURLProvider;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USHTTPUtilities;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

import concept.Inspection;
import concept.ViewPage;
import concept.components.admin.SWDocumentComponent;
import concept.components.admin.SWMetaEdit;
import concept.components.admin.SWTransactionsForObject;
import concept.components.tagging.SWTaggingPage;
import concept.data.SWFavorite;
import concept.data.SWUser;
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

	public WOActionResults viewGenericVersion() {
		return Inspection.inspectObjectInContextUsingComponent( selectedObject(), context(), ViewPageGeneric.class );
	}

	public WOActionResults viewTransactions() {
		return Inspection.editObjectInContextUsingComponent( selectedObject(), context(), SWTransactionsForObject.class );
	}

	public WOActionResults viewDocuments() {
		return Inspection.editObjectInContextUsingComponent( selectedObject(), context(), SWDocumentComponent.class );
	}

	public WOActionResults viewMeta() {
		return Inspection.editObjectInContextUsingComponent( selectedObject(), context(), SWMetaEdit.class );
	}

	public WOActionResults viewTags() {
		return Inspection.editObjectInContextUsingComponent( selectedObject(), context(), SWTaggingPage.class );
	}
}