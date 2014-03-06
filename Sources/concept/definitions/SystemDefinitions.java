package concept.definitions;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.components.SWDocumentDetailPage;
import concept.components.admin.SWEditComponent;
import concept.components.admin.SWEditPage;
import concept.components.admin.SWEditSite;
import concept.components.admin.SWEditTransaction;
import concept.components.admin.edit.CPEditGroup;
import concept.components.admin.edit.CPEditNewsItem;
import concept.components.admin.edit.CPEditUser;
import concept.components.admin.edit.SWEditDocument;
import concept.components.admin.edit.SWEditEntityMeta;
import concept.components.client.CPNewsDetail;
import concept.components.client.SWPageDetailPage;
import concept.components.client.SWUserDetailPage;
import concept.components.tagging.SWTagDetailPage;
import concept.data.SWAccessPrivilege;
import concept.data.SWAccessPrivilegeValue;
import concept.data.SWAttributeMeta;
import concept.data.SWComment;
import concept.data.SWComponent;
import concept.data.SWDocument;
import concept.data.SWDocumentLink;
import concept.data.SWEntityMeta;
import concept.data.SWFavorite;
import concept.data.SWFolder;
import concept.data.SWFolderLink;
import concept.data.SWGroup;
import concept.data.SWMeta;
import concept.data.SWNewsItem;
import concept.data.SWPage;
import concept.data.SWSite;
import concept.data.SWSystemEvent;
import concept.data.SWTag;
import concept.data.SWTransaction;
import concept.data.SWUser;

public class SystemDefinitions implements ProvidesEntityViewDefinitions{

	@Override
	public NSArray<EntityViewDefinition> entityViewDefinitions() {
		NSMutableArray<EntityViewDefinition> a = new NSMutableArray<>();
		a.add( EntityViewDefinition.create( SWAccessPrivilege.ENTITY_NAME, "Aðgangsheimild", "Aðgangsheimildir", "Kerfi", null, null, null, false, null, null ) );
		a.add( EntityViewDefinition.create( SWAccessPrivilegeValue.ENTITY_NAME, "Aðgangsgildi", "Aðgangsgildi", "Kerfi", null, null, null, false, null, null ) );
		a.add( EntityViewDefinition.create( SWAttributeMeta.ENTITY_NAME, "Reitaskilgreining", "Reitaskilgreiningar", "Kerfi", null, null, null, false, null, null ) );
		a.add( EntityViewDefinition.create( SWComment.ENTITY_NAME, "Athugasemd", "Athugasemdir", "Kerfi", null, null, null, false, null, null ) );
		a.add( EntityViewDefinition.create( SWComponent.ENTITY_NAME, "Efni", "Efni", "Kerfi", null, null, null, false, null, SWEditComponent.class ) );
		a.add( EntityViewDefinition.create( SWDocument.ENTITY_NAME, "Skjal", "Skjöl", "Kerfi", null, "document", null, false, SWDocumentDetailPage.class, SWEditDocument.class ) );
		a.add( EntityViewDefinition.create( SWDocumentLink.ENTITY_NAME, "Skjalatenging", "Skjalatengingar", "Kerfi", null, null, null, false, null, null ) );
		a.add( EntityViewDefinition.create( SWEntityMeta.ENTITY_NAME, "Töfluskilgreining", "Töfluskilgreiningar", "Kerfi", null, null, null, false, null, SWEditEntityMeta.class ) );
		a.add( EntityViewDefinition.create( SWFavorite.ENTITY_NAME, "Eftirlæti", "Eftirlæti", "Kerfi", null, null, null, false, null, null ) );
		a.add( EntityViewDefinition.create( SWFolder.ENTITY_NAME, "Mappa", "Möppur", "Kerfi", null, null, null, false, null, null ) );
		a.add( EntityViewDefinition.create( SWFolderLink.ENTITY_NAME, "Möpputenging", "Möpputengingar", "Kerfi", null, null, null, false, null, null ) );
		a.add( EntityViewDefinition.create( SWGroup.ENTITY_NAME, "Hópur", "Hópar", "Kerfi", null, null, null, false, null, CPEditGroup.class ) );
		a.add( EntityViewDefinition.create( SWMeta.ENTITY_NAME, "Eigindi", "Eigindi", "Kerfi", null, null, null, false, null, null ) );
		a.add( EntityViewDefinition.create( SWNewsItem.ENTITY_NAME, "Frétt", "Fréttir", "Kerfi", null, "newsitem", null, false, CPNewsDetail.class, CPEditNewsItem.class ) );
		a.add( EntityViewDefinition.create( SWUser.ENTITY_NAME, "Notandi", "Notendur", "Kerfi", null, null, null, false, SWUserDetailPage.class, CPEditUser.class ) );
		a.add( EntityViewDefinition.create( SWPage.ENTITY_NAME, "Vefsíða", "Vefsíður", "Kerfi", null, "page", null, false, SWPageDetailPage.class, SWEditPage.class ) );
		a.add( EntityViewDefinition.create( SWSite.ENTITY_NAME, "Vefur", "Vefir", "Kerfi", null, null, null, false, null, SWEditSite.class ) );
		a.add( EntityViewDefinition.create( SWSystemEvent.ENTITY_NAME, "Kerfisviðburður", "Kerfisviðburðir", "Kerfi", null, null, null, false, null, null ) );
		a.add( EntityViewDefinition.create( SWTag.ENTITY_NAME, "Merki", "Merki", "Kerfi", null, "tag", null, false, SWTagDetailPage.class, null ) );
		a.add( EntityViewDefinition.create( SWTransaction.ENTITY_NAME, "Hreyfing", "Hreyfingar", "Kerfi", null, null, null, false, null, SWEditTransaction.class ) );
		return a.immutableClone();
	}
}