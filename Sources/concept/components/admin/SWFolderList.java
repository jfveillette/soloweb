package concept.components.admin;

import is.rebbi.wo.interfaces.SWFolderInterface;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USHierarchyUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import concept.SWAccessPrivilegeUtilities;
import concept.SWAdminComponent;
import concept.SWApplication;
import concept.SWSession;

public class SWFolderList extends SWAdminComponent {

	private static final Logger logger = LoggerFactory.getLogger( SWFolderList.class );

	private static final String PLUS_GIF = "plus.gif";
	private static final String MINUS_GIF = "minus.gif";
	private static final String SPACER_GIF = "spacer.gif";

	public SWFolderInterface selectedFolder, currentFolder;
	public int currentIndex;

	public SWFolderList( WOContext context ) {
		super( context );
	}

	public NSArray rootFolders() {
		NSArray a = null;

		try {
			String eName = entityName();
			SWFolderInterface folder = (SWFolderInterface)USEOUtilities.classForEntityNamed( session().defaultEditingContext(), eName ).newInstance();
			a = folder.sortedRootFolders( session().defaultEditingContext() );
			a = SWAccessPrivilegeUtilities.filteredArrayWithUserAndPrivilege( a, user(), "allowToSee" );
		}
		catch( Exception e ) {
			logger.debug( "Error fetching root folders", e );
		}

		return a;
	}

	public boolean isDisabled() {

		if( disabledFolder() != null ) {
			if( currentFolder.equals( disabledFolder() ) ) {
				return true;
			}
		}

		if( disabledBranch() != null ) {
			if( USHierarchyUtilities.isChildOfNode( currentFolder, disabledBranch(), false ) ) {
				return true;
			}
		}

		return false;
	}

	public WOActionResults selectFolder() {
		setSelectedFolder( currentFolder );

		if( actionName() == null ) {
			return null;
		}

		return performParentAction( actionName() );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	public String entityName() {
		return (String)valueForBinding( "entityName" );
	}

	public String actionName() {
		return (String)valueForBinding( "actionName" );
	}

	public SWFolderInterface disabledFolder() {
		return (SWFolderInterface)valueForBinding( "disabledFolder" );
	}

	public SWFolderInterface disabledBranch() {
		return (SWFolderInterface)valueForBinding( "disabledBranch" );
	}

	public SWFolderInterface selectedFolder() {
		return (SWFolderInterface)valueForBinding( "selectedFolder" );
	}

	public void setSelectedFolder( SWFolderInterface newSelectedFolder ) {
		selectedFolder = newSelectedFolder;
		setValueForBinding( selectedFolder, "selectedFolder" );
	}

	public NSArray subFolders() {
		if( isExpanded( currentFolder ) ) {
			return SWAccessPrivilegeUtilities.filteredArrayWithUserAndPrivilege( currentFolder.sortedSubFolders(), user(), "allowToSee" );
		}
		else {
			return NSArray.EmptyArray;
		}
	}

	/**
	    * Determines if the current branch should be expanded (if subpages should be displayed)
	    */
	public boolean isExpanded( SWFolderInterface anObject ) {
		return ((SWSession)session()).arrayWithKeyContainsObject( entityName(), anObject );
	}

	/**
	    * Expands the current branch.
	    */
	public void expandBranch( SWFolderInterface anObject ) {
		((SWSession)session()).addObjectToArrayWithKey( anObject, entityName() );
	}

	/**
	    * Collapses the current branch.
	    */
	public void collapseBranch( SWFolderInterface anObject ) {
		((SWSession)session()).removeObjectFromArrayWithKey( anObject, entityName() );
	}

	/**
	    * Determines if the plus sign or the minus sign should be displayed in
	    * front of the current page, based on if it's branch is expanded or not.
	    * Returns the name of the picture to display.
	    */
	public String toggleString() {
		String imageName = null;

		if( hasNoSubFolders() ) {
			imageName = SPACER_GIF;
		}
		else if( isExpanded( currentFolder ) ) {
			imageName = MINUS_GIF;
		}
		else {
			imageName = PLUS_GIF;
		}

		return WOApplication.application().resourceManager().urlForResourceNamed( "sw32/img/" + imageName, SWApplication.swapplication().frameworkBundleName(), null, context().request() );
	}

	public WOComponent toggleDisplay() {
		if( isExpanded( currentFolder ) ) {
			collapseBranch( currentFolder );
		}
		else {
			expandBranch( currentFolder );
		}

		return null;
	}

	public boolean hasNoSubFolders() {
		if( currentFolder != null ) {
			return !USArrayUtilities.hasObjects( currentFolder.sortedSubFolders() );
		}
		return true;
	}

	public String title() {
		String t = "M&ouml;ppur";
		try {
			SWFolderInterface folder = (SWFolderInterface)USEOUtilities.classForEntityNamed( session().defaultEditingContext(), entityName() ).newInstance();
			t = folder.title();
		}
		catch( Exception e ) {}

		return t;
	}
}