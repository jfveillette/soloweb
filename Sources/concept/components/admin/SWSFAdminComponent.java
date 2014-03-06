package concept.components.admin;

import java.util.Enumeration;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableDictionary;

import concept.SWGenericComponent;
import concept.data.SWDocument;
import concept.util.SWLoc;

public class SWSFAdminComponent extends SWGenericComponent {

	public SWSFAdminComponent( WOContext c ) {
		super( c );
	}

	public NSArray componentKeys() {
		return componentsAndKeys().allKeys();
	}

	public NSMutableDictionary componentsAndKeys() {
		NSMutableDictionary d = new NSMutableDictionary();

		d.setObjectForKey( "SWSFComponent", SWLoc.string( "docComponentSingleDocument", session() ) );
		d.setObjectForKey( "SWSFFileList", SWLoc.string( "docComponentListOfDocuments", session() ) );

		return d;
	}

	public String selectedComponentKey() {
		Enumeration e = componentsAndKeys().objectEnumerator();

		while( e.hasMoreElements() ) {
			String s = (String)e.nextElement();
			if( s.equals( currentComponent().templateName() ) ) {
				return (String)componentsAndKeys().allKeysForObject( s ).lastObject();
			}
		}

		return null;
	}

	public void setSelectedComponentKey( String newKey ) {
		currentComponent().setTemplateName( (String)componentsAndKeys().valueForKey( newKey ) );
	}

	public SWDocument selectedDocument() {
		return SWDocument.documentWithID( session().defaultEditingContext(), documentIDFromDictionary() );
	}

	public void setSelectedDocument( SWDocument document ) {
		if( document == null ) {
			currentComponent().customInfo().takeValueForKey( null, "documentID" );
		}
		else {
			currentComponent().customInfo().takeValueForKey( document.primaryKey().toString(), "documentID" );
		}
	}

	public Integer documentIDFromDictionary() {
		return currentComponent().customInfo().integerValueForKey( "documentID" );
	}

	public WOComponent self() {
		return this;
	}
}