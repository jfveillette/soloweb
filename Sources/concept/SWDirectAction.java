package concept;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.SWSettings;
import is.rebbi.wo.util.USArrayUtilities;
import is.rebbi.wo.util.USEOUtilities;
import is.rebbi.wo.util.USHTTPUtilities;
import is.rebbi.wo.util.USUtilities;

import java.util.Enumeration;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOCookie;
import com.webobjects.appserver.WORedirect;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSNumberFormatter;
import com.webobjects.foundation.NSTimestamp;

import concept.components.SWECard;
import concept.components.SWErrorMessage;
import concept.components.admin.SWLogin;
import concept.components.admin.SWManageSettings;
import concept.components.admin.SWNewsCategoriesSelectComponent;
import concept.components.client.SWNoPageFoundErrorComponent;
import concept.components.client.SWPasswordFieldComponent;
import concept.components.client.SWPrinterTemplate;
import concept.components.client.SWSearchResults;
import concept.components.client.SWStandardTemplate;
import concept.data.SWAssetFolder;
import concept.data.SWComponent;
import concept.data.SWGroup;
import concept.data.SWNewsItem;
import concept.data.SWPage;
import concept.data.SWPicture;
import concept.data.SWSite;
import concept.data.SWUser;
import concept.deprecated.SWMailSender;
import concept.deprecated.SWUtilities;
import concept.search.SWLuceneSearch;
import er.extensions.appserver.ERXDirectAction;

public class SWDirectAction extends ERXDirectAction {

	private static final Logger logger = LoggerFactory.getLogger( SWDirectAction.class );

	private static final String CRLF_CHARACTER = String.valueOf( (char)13 ) + String.valueOf( (char)10 );
	private static final String TAB_CHARACTER = String.valueOf( (char)9 );

	public SWDirectAction( WORequest aRequest ) {
		super( aRequest );
	}

	/**
	 * @return The login page.
	 */
	public WOActionResults loginAction() {
		if( SWSettings.stringForKey( "solowebResourcesURL" ) == null ) {
			((SWSession)session()).setIsLoggedIn( true );
			return pageWithName( SWManageSettings.class );
		}

		return pageWithName( SWLogin.class );
	}

	/**
	 * The default action is executed when the SoloWeb Application is entered.
	 * Checks the request for what site was requested, and returns that page.
	 */
	@Override
	public WOActionResults defaultAction() {
		SWSite requestedSite = requestedSite();

		if( requestedSite == null ) {
			return handleError();
		}

		SWPage thePage = requestedSite.alternateDefaultPage();

		if( thePage == null ) {
			thePage = requestedSite.frontpage();
		}

		return displayPageWithTemplate( thePage, SWStandardTemplate.class.getSimpleName() );
	}

	/**
	 * Checks the request for what site was requested, and returns it.
	 */
	private SWSite requestedSite() {
		return siteMatchingHostName( hostForRequest( request() ) );
	}

	/**
	 * Returns the site matching the host name specified.
	 */
	private SWSite siteMatchingHostName( String hostName ) {
		EOQualifier q = new EOKeyValueQualifier( SWSite.QUAL_KEY, EOQualifier.QualifierOperatorLike, "*" + hostName + SWSite.SITENAME_DELIMITER + "*" );
		EOFetchSpecification fs = new EOFetchSpecification( SWSite.ENTITY_NAME, q, null );

		NSArray<SWSite> sites = session().defaultEditingContext().objectsWithFetchSpecification( fs );

		if( !USArrayUtilities.hasObjects( sites ) ) {
			return null;
		}

		SWSite site = null;

		if( sites.count() == 1 ) {
			site = sites.objectAtIndex( 0 );
		}
		else {
			for( int i = 0; (i < sites.count()) && (site == null); i++ ) {
				site = sites.objectAtIndex( i );

				if( !site.hasHost( hostName.toLowerCase() ) ) {
					site = null;
				}
			}
		}

		return site;
	}

	/**
	 * The search action performs a search of the SoloWeb site, and returns an
	 * SWSearchResults-page, displaying the results. You can pass in a branchID
	 * to narrow the search. Arguments:
	 * <ul>
	 * <li><b>searchString</b><br>
	 * The String to search for
	 * <li><b>branchID</b><br>
	 * The branch to search (see docs for SWContentSearch for further
	 * information)
	 * </ul>
	 */
	public WOActionResults searchAction() {
		String indexLocationOndisk = SWSettings.stringForKey( "indexLocationOndisk" );
		String searchString = request().stringFormValueForKey( "searchString" );
		String cidString = request().stringFormValueForKey( "cid" );
		String pidString = request().stringFormValueForKey( "pid" );
		String langString = request().stringFormValueForKey( "lang" );
		String branchIDString;
		String newsFolderIDsString;
		String newsDisplayPageSymbol = "";
		HashMap<String, Object> searchItemSettingsMap = null;
		SWPage thePage = null;

		searchItemSettingsMap = new HashMap<>();
		searchItemSettingsMap.put( "searchString", searchString );

		if( StringUtilities.hasValue( cidString ) ) {
			SWComponent comp = (SWComponent)USEOUtilities.objectWithPK( session().defaultEditingContext(), SWComponent.ENTITY_NAME, Integer.valueOf( cidString ) );
			thePage = SWPageUtilities.pageWithID( session().defaultEditingContext(), Integer.valueOf( pidString ) );
			branchIDString = comp.customInfo().stringValueForKey( "searchBranchID" );
			newsFolderIDsString = comp.customInfo().stringValueForKey( "searchNewsFolderIDs" );

			newsDisplayPageSymbol = comp.customInfo().stringValueForKey( "searchNewsDisplayPageSymbol" );
			searchItemSettingsMap.put( "newsDisplayPageSymbol", newsDisplayPageSymbol );
		}
		else {
			// Support this way of getting search settings because of links in
			// search engines (as of Aug/Sep 2012)
			branchIDString = request().stringFormValueForKey( "branchID" );
			newsFolderIDsString = request().stringFormValueForKey( "newsFolderIDs" );
			thePage = SWPageUtilities.pageFromRequest( session().defaultEditingContext(), request() );
		}

		Integer branchID = null;
		if( branchIDString != null ) {
			branchID = new Integer( branchIDString );
		}

		SWSearchResults nextPage = (SWSearchResults)displayPageWithTemplate( thePage, SWSearchResults.class.getSimpleName() );
		nextPage.language = langString;

		if( StringUtilities.hasValue( indexLocationOndisk ) ) {
			nextPage.results = new SWLuceneSearch( session().defaultEditingContext(), searchString, branchID, newsFolderIDsString ).search();
			nextPage.resultItemSettings = searchItemSettingsMap;
		}
		else {
			nextPage.results = new SWContentSearch( session().defaultEditingContext(), searchString, branchID ).search();
		}

		return nextPage;
	}

	/**
	 * Returns a most unfriendly error message to the user.
	 */
	public WOActionResults handleError() {
		String errorPageLinkingName = null;
		WOResponse errorResponse = null;
		SWSite requestedSite = requestedSite();

		if( requestedSite != null ) {
			errorPageLinkingName = requestedSite.noPageFoundErrorPageLinkingName();
		}

		if( errorPageLinkingName == null ) {
			errorPageLinkingName = SWSettings.stringForKey( "noPageFoundErrorPageLinkingName" );
		}

		if( errorPageLinkingName != null ) {
			SWPage thePage = SWPageUtilities.pageWithName( session().defaultEditingContext(), errorPageLinkingName );
			errorResponse = displayPageWithTemplate( thePage, "SWStandardTemplate" ).generateResponse();
		}
		else {
			String errorComponentName = SWNoPageFoundErrorComponent.class.getSimpleName();
			errorResponse = pageWithName( errorComponentName ).generateResponse();
		}

		errorResponse.setStatus( 404 );

		return errorResponse;
	}

	/**
	 * Retrieves the page specified by the arguments and displays it in the
	 * alternate (printing) template.
	 *
	 * Arguments:
	 * <ul>
	 * <li>Takes the same arguments as the action "dp" to determine the page to display
	 * </ul>
	 */
	public WOActionResults printAction() {
		SWPage thePage = SWPageUtilities.pageFromRequest( session().defaultEditingContext(), request() );
		return displayPageWithTemplate( thePage, SWPrinterTemplate.class.getSimpleName() );
	}

	/**
	 * When a user enters a password into the password field of a password
	 * public page, this method checks if he has entered the correct password
	 * and if so, stores a cookie that allows the user to browse the selected
	 * page and all subpages.
	 *
	 * If the crm url parameter has a value of true, then the user is a CRM user
	 * and the SWUser must then be part of the group assigned to the page he is
	 * trying to access.
	 *
	 * Arguments:
	 * <ul>
	 * <li>Takes the same arguments as the action "dp" to determine the page to display
	 * <li><b>pw</b><br>
	 * The password the user is trying
	 * </ul>
	 */
	public WOActionResults passwordAction() {
		String username = request().stringFormValueForKey( "username" );
		String password = request().stringFormValueForKey( "pw" );
		String crm = request().stringFormValueForKey( "crm" );

		// Look up username and password in users table
		NSMutableDictionary<String,String> lookup = new NSMutableDictionary<>();
		lookup.setObjectForKey( username, SWUser.USERNAME_KEY );
		lookup.setObjectForKey( password, SWUser.PASSWORD_KEY );

		SWUser user = null;

		try {
			NSArray<SWUser> users = EOUtilities.objectsMatchingValues( session().defaultEditingContext(), SWUser.ENTITY_NAME, lookup );

			if( users != null && users.count() > 0 ) {
				// One or more users found
				int userNo = 0;
				Number crmNafnId;
				while( user == null && userNo < users.count() ) {
					crmNafnId = users.objectAtIndex( userNo ).crmNafnId();
					if( crmNafnId != null && crm != null && crm.equalsIgnoreCase( "true" ) ) {
						// Want and found a crm user
						user = users.objectAtIndex( userNo );
					}
					else if( crmNafnId == null && (crm == null || !crm.equalsIgnoreCase( "true" )) ) {
						// Want and found a regular SW user
						user = users.objectAtIndex( userNo );
					}

					userNo++;
				}
			}
		}
		catch( Exception ex ) {
			logger.error( "Exception in SWDirectAction.passwordAction. username=" + username + ", password=", ex );
		}

		if( user != null ) {
			boolean okToLogin = false;

			// User found, check crm status if needed
			if( crm != null && crm.equals( "true" ) ) {
				SWPage thePage = SWPageUtilities.pageFromRequest( session().defaultEditingContext(), request() );
				int groupid = thePage.getUserGroupID();
				Enumeration iter = null;

				// See if the swuser belongs to the crm enabled swgroup that the
				// requested
				// page is public by
				SWGroup group = null;
				boolean found = false;
				if( user.groups() != null ) {
					iter = user.groups().objectEnumerator();
				}
				while( !found && iter != null && iter.hasMoreElements() ) {
					group = (SWGroup)iter.nextElement();
					found = (Integer.parseInt( group.primaryKey() ) == groupid);
				}
				if( found && group.crmGroup() != null && group.crmGroup().intValue() == 1 ) {
					if( user.crmNafnId() != null && user.crmNafnId().intValue() > 0 ) {
						okToLogin = true;
					}
				}
			}
			else {
				okToLogin = true;
			}

			if( okToLogin ) {
				okToLogin( user );
			}
		}

		return performActionNamed( "dp" );
	}

	public void okToLogin( SWUser user ) {
		String cookieUserId = SWUtilities.scrambleCookieUserID( user.id().intValue() );
		request().addCookie( theUserCookie( cookieUserId ) );
	}

	/**
	 * Logs the user out of the password public area of the web, and destroys
	 * the SW_CONTENTUSER_ID cookie. The name or id of the page to be shown after
	 * logout is in the pg url parameter.
	 *
	 * @return the logout page
	 */
	public WOActionResults passwordLogoutAction() {
		SWPage resultPage = null;
		WOActionResults theResult;
		String pg = request().stringFormValueForKey( "pg" );
		WOCookie theCookie;

		// Make sure the cookie is destroyed
		theCookie = this.theUserCookie( "0" );
		theCookie.setExpires( new NSTimestamp( 1970, 1, 1, 0, 0, 0, null ) );
		request().addCookie( theCookie );

		// Get the result page
		resultPage = SWPageUtilities.pageWithName( session().defaultEditingContext(), pg );
		theResult = displayPageWithTemplate( resultPage, "SWStandardTemplate" );

		return theResult;
	}

	/**
	 * Creates and returns the user cookie
	 */
	public WOCookie theUserCookie( String id ) {
		int timeout = 0;

		String host = context().request().headerForKey( "Host" );
		String path = "/";

		if( host.indexOf( "localhost" ) == 0 ) {
			host = ""; // host should be empty for localhost
		}

		return new WOCookie( SWUtilities.USER_COOKIE_NAME, String.valueOf( id ), path, host, timeout, false );
	}

	/**
	 * Retrieves the page specified by the arguments and displays it to the user.
	 */
	public WOActionResults dpAction() {
		SWPage page = SWPageUtilities.pageFromRequest( session().defaultEditingContext(), request() );
		return displayPageWithTemplate( page, SWStandardTemplate.class.getSimpleName() );
	}

	/**
	 * Displays a page using the named template
	 */
	public WOActionResults displayPageWithTemplate( SWPage page, String templateName ) {

		if( page == null ) {
			return handleError();
		}

		SWGenericTemplate nextPage = (SWGenericTemplate)pageWithName( templateName );

		if( !page.isAccessible() && !isLoggedIn() ) {
			return SWErrorMessage.errorWithMessageAndStatusCode( "Public access to this page is not allowed", new WOContext( request() ), 403 );
		}

		if( !sessionUserHasAccessToPage( page, nextPage ) ) {
			String loginFormName = (String)page.valueForKey( "@valueInHierarchyForKeyPath.customInfo.loginFormName" );

			if( !StringUtilities.hasValue( loginFormName ) ) {
				loginFormName = SWPasswordFieldComponent.class.getSimpleName();
			}

			nextPage = (SWPasswordFieldComponent)pageWithName( loginFormName );
		}

		if( StringUtilities.hasValue( page.externalURL() ) ) {
			return USHTTPUtilities.redirectTemporary( page.externalURL() );
		}

		nextPage.setSelectedPage( page );

		String fontSetting = context().request().stringFormValueForKey( "fs" );

		if( StringUtilities.hasValue( fontSetting ) ) {
			if( fontSetting.equals( "toggletheme" ) ) {
				String selectedTheme = context().request().cookieValueForKey( SWDirectAction.COOKIE_NAME_THEME );

				if( selectedTheme == SWDirectAction.THEME_DEFAULT ) {
					selectedTheme = SWDirectAction.THEME_DYSLEX;
				}
				else {
					selectedTheme = SWDirectAction.THEME_DEFAULT;
				}
				WOCookie theCookie = fontCookie( selectedTheme );
				nextPage.setCookie( theCookie );
			}
			else {
				WOCookie theCookie = fontCookie( fontSetting );
				nextPage.setCookie( theCookie );
			}
		}

		return nextPage;
	}

	/**
	 * Indicates if the given user is logged in to SoloWeb.
	 */
	private boolean isLoggedIn() {
		SWSession s = (SWSession)existingSession();
		return (s != null) && s.isLoggedIn();
	}

	/**
	 * Indicates if the current session has a password for the given page.
	 */
	private boolean sessionUserHasAccessToPage( SWPage aPage, SWGenericTemplate nextPage ) {
		// Get group that page is assigned to
		int pageGroupID = aPage.getUserGroupID();

		if( pageGroupID != -1 ) {
			// User group assigned; find user from user cookie
			String userId = "";

			NSArray<WOCookie> cookies = request().cookies();
			WOCookie cookie;

			for( int i = 0; i < cookies.count(); i++ ) {
				cookie = (context().request().cookies().objectAtIndex( i ));

				if( cookie.name().equals( "SW_CONTENTUSER_ID" ) ) {
					String cookieString = cookie.value();
					logger.debug( "1: cookie nr: " + i + ", cookieString: " + cookieString );

					// Fix the cookie string
					cookieString = cookieString.replaceAll( "\\(", "" );
					logger.debug( "2: cookie nr: " + i + ", cookieString: " + cookieString );
					cookieString = cookieString.replaceAll( "\\)", "" );
					logger.debug( "3: cookie nr: " + i + ", cookieString: " + cookieString );
					cookieString = cookieString.replaceAll( "\"", "" );
					logger.debug( "4: cookie nr: " + i + ", cookieString: " + cookieString );
					cookieString = cookieString.replaceAll( " ", "" );
					logger.debug( "5: cookie nr: " + i + ", cookieString: " + cookieString );
					cookie.setValue( cookieString );

					if( cookie.timeOut() == 0 ) {
						// Cookie was just created; carry it on
						int timeout = 864000; // 10 days
						WOCookie newCookie = new WOCookie( cookie.name(), cookie.value(), cookie.path(), cookie.domain(), timeout, false );
						nextPage.setCookie( newCookie );
					}

					// Set user id
					userId = cookieString;
					logger.debug( "6: userId: " + userId );

					break;
				}
			}

			if( StringUtilities.hasValue( userId ) ) {
				// User id found; do lookup
				// NOTE: We may have a comma separated list of userIds if there were
				// more than one SW_CONTENTUSER_ID cookies in the request

				String[] userIds = userId.split( "," );

				for( int idNo = 0; idNo < userIds.length; idNo++ ) {
					userId = userIds[idNo];
					logger.debug( "7: userId: " + userId );
					SWUser user = SWUtilities.userFromCookieUserID( session().defaultEditingContext(), userId );

					if( user != null ) {
						NSArray<SWGroup> userGroups = user.groups();

						// Iterate through the user's group to see if (s)he is in the
						// group

						for( int i = 0; i < userGroups.count(); i++ ) {
							if( Integer.parseInt( (userGroups.objectAtIndex( i )).primaryKey() ) == pageGroupID ) {
								// User is in group that page is assigned to; OK
								return true;
							}
						}
					}
				}
			}

			// User not found or page is not in user's assigned group
			return false;
		}
		else {
			// No user group for page set; OK
			return true;
		}
	}

	/**
	 * An adaptor-agnostic way of determining the requested host name.
	 */
	public static String hostForRequest( WORequest r ) {
		String domain = r.stringFormValueForKey( "host" );

		if( !StringUtilities.hasValue( domain ) ) {
			domain = USHTTPUtilities.host( r );
		}

		return domain;
	}

	/**
	 * List in xml the content of an image folder
	 */
	public WOActionResults swPictureFolderAction() {
		WOResponse r = new WOResponse();
		StringBuffer buffer = new StringBuffer();
		Integer folderID = new Integer( (String)request().formValueForKey( "folderID" ) );
		buffer.append( "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" );
		buffer.append( "<swFolder id=\"" + folderID + "\"> \n" );

		SWAssetFolder folder = (SWAssetFolder)USEOUtilities.objectWithPK( session().defaultEditingContext(), SWAssetFolder.ENTITY_NAME, folderID );
		NSArray pics = null;

		if( folder != null ) {
			if( "true".equals( request().formValueForKey( "sort" ) ) ) {
				pics = USEOUtilities.objectsForKeyWithValueSortedByKey( session().defaultEditingContext(), SWPicture.ENTITY_NAME, SWPicture.ASSET_FOLDER_ID_KEY, folderID, "name" );
			}
			else {
				pics = folder.pictures();
			}
		}

		if( pics != null ) {
			Enumeration e = pics.objectEnumerator();
			String s;
			boolean rkiFilter = SWSettings.booleanForKey( "RKI_FilterOutExtraECWebPictures" );
			while( e.hasMoreElements() ) {
				SWPicture pic = (SWPicture)e.nextElement();
				if( rkiFilter ) {
					s = pic.name();
					if( s != null && s.indexOf( '_' ) > 3 ) {
						s = s.substring( 0, s.indexOf( '_' ) );
						if( USUtilities.integerFromObject( s ) != null ) {
							continue;
						}
					}
				}

				buffer.append( "<swPicture id=\"" + pic.id() + "\"\n" );
				buffer.append( "   url=\"" + pic.pictureURL() + "\"\n" );
				buffer.append( "   picHeight=\"" + pic.height() + "\"\n" );
				buffer.append( "   picWidth=\"" + pic.width() + "\">\n" );
				if( pic.description() != null ) {
					buffer.append( "   <description>" + pic.description() + "</description>\n" );
				}
				if( pic.altText() != null ) {
					buffer.append( "   <altText>" + pic.altText() + "</altText>\n" );
				}
				buffer.append( "</swPicture>\n" );
			}

			buffer.append( "</swFolder>" );
		}
		else {
			buffer.append( "</swFolder>" );
		}

		r.setHeader( "content-type", "text/xml" );
		r.setContent( buffer.toString() );
		r.setContentEncoding( "UTF-8" );

		return r;
	}

	/**
	 * Show a popup window for selecting news categories for a news item.
	 */
	public WOActionResults openNewsCategoriesSelectAction() {
		String extraCategories = this.context().request().stringFormValueForKey( "extraCategories" );

		SWNewsCategoriesSelectComponent newsCategoriesSelect = pageWithName( SWNewsCategoriesSelectComponent.class );
		newsCategoriesSelect.initialSelectedIds = extraCategories;

		return newsCategoriesSelect;
	}

	/**
	 * Export SoloForms registrations to Filemaker Pro 6 or later formatted XML.
	 *
	public WOActionResults soloFormsRegDataExportAction() {
		WOResponse resp = new WOResponse();
		String formId;

		try {
			// Get the form data xml
			formId = context().request().stringFormValueForKey( "formid" );
			// SWFFilemakerXmlExport hshandler = new SWFFilemakerXmlExport();
			String fmpXmlResult = SWFFilemakerXmlExport.exportFormContentToFmpXml( Integer.valueOf( formId ).intValue() );

			// Set up the response
			resp.setContentEncoding( "ISO-8859-1" );
			resp.setHeader( "content-type", "text/xml" );
			resp.setContent( fmpXmlResult );
		}
		catch( Exception ex ) {
			// Set up the response
			resp.setContentEncoding( "ISO-8859-1" );
			resp.setHeader( "content-type", "text/html" );
			resp.setContent( "<html><head></head><body>Villa</body></html>" );
		}

		return resp;
	}
	*/

	/**
	 * Send a form via email. The following hidden fields must be in the form:
	 * fromAddress: The address to send the form from toAddress: The address to
	 * send the form to subject: The email subject resultPageUrl: The url for the
	 * thank-you page
	 */
	public WOActionResults sendFormAction() {
		String fromAddress, toAddress, subject, thankYouPage, fieldNames, fieldTitles, fieldValue, fieldName, fieldTitle;
		NSArray aFieldNames, aFieldTitles, toAddresses;
		String emailContent;

		// Get the form parameters
		fromAddress = request().stringFormValueForKey( "fromAddress" );
		toAddress = request().stringFormValueForKey( "toAddress" );
		subject = request().stringFormValueForKey( "subject" );
		thankYouPage = request().stringFormValueForKey( "resultPageUrl" );

		// Get the field names and titles
		fieldNames = request().stringFormValueForKey( "fieldNames" );
		fieldTitles = request().stringFormValueForKey( "fieldTitles" );

		// Get the values and build the email content string
		aFieldNames = NSArray.componentsSeparatedByString( fieldNames, "," );
		aFieldTitles = NSArray.componentsSeparatedByString( fieldTitles, "," );
		emailContent = "";
		for( int i = 0; i < aFieldNames.count(); i++ ) {
			fieldName = (String)aFieldNames.objectAtIndex( i );
			fieldValue = this.request().stringFormValueForKey( fieldName );
			fieldTitle = (String)aFieldTitles.objectAtIndex( i );
			emailContent += fieldTitle + " : " + fieldValue + "\n";
		}

		// Send the form
		toAddresses = NSArray.componentsSeparatedByString( toAddress, "," );
		is.rebbi.wo.util.USMailSender.composeEmail( fromAddress, toAddresses, null, null, subject, emailContent, null );

		// Check mailing lists
//		this.checkMailingLists();

		// Redirect to thank you page
		WORedirect redirect = new WORedirect( context() );
		redirect.setUrl( thankYouPage );
		return redirect;
	}
/*
	private void checkMailingLists() {
		String mailingListParams;
		boolean mailingListSet;
		StringTokenizer mailingListParamsSplitter;
		String mailingListCheckboxName;
		boolean mailingListChecked;
		int mailinglistId;
		String email;
		SWMMailinglist mailinglist;
		int count = 1;

		do {
			mailingListParams = this.request().stringFormValueForKey( "mailinglist" + count );
			mailingListSet = (mailingListParams != null && mailingListParams.length() > 0);

			if( mailingListSet ) {
				// Parse mailing list params
				mailingListParamsSplitter = new StringTokenizer( mailingListParams, ";" );

				if( mailingListParamsSplitter.hasMoreTokens() ) {
					mailingListCheckboxName = mailingListParamsSplitter.nextToken();

					if( mailingListCheckboxName != null && mailingListCheckboxName.length() > 0 ) {
						mailingListChecked = (this.request().formValueForKey( mailingListCheckboxName ) != null);

						if( mailingListChecked ) {
							// Mailing list checkbox checked; proceed

							if( mailingListParamsSplitter.hasMoreTokens() ) {
								mailinglistId = Integer.parseInt( mailingListParamsSplitter.nextToken() );
								mailinglist = SWMMailinglist.listWithID( session().defaultEditingContext(), new Integer( mailinglistId ) );

								if( mailinglist != null ) {
									// Add registration with given email address
									email = this.request().stringFormValueForKey( "email" );
									SWMUtil.registerEmailToMailinglist( session().defaultEditingContext(), context(), mailinglistId, email );
								}
							}
						}
					}
				}

				count++;
			}
		}
		while( mailingListSet );
	}
*/
	/**
	 * send email in more generic form than sendFormAction it is wise to include
	 * fromName and toName to decrease the change of this message being sorted as
	 * spam
	 *
	public WOActionResults emailAction() {
		class SMTPAuthenticator extends javax.mail.Authenticator {

			private String username;
			private String password;

			public SMTPAuthenticator( String username, String password ) {
				this.username = username;
				this.password = password;
			}

			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication( username, password );
			}
		}

		String fromAddress = (String)request().formValueForKey( "fromAddress" );
		String fromName = (String)request().formValueForKey( "fromName" );
		String toAddress = (String)request().formValueForKey( "toAddress" );
		String toName = (String)request().formValueForKey( "toName" );
		String cc = (String)request().formValueForKey( "cc" );
		String bcc = (String)request().formValueForKey( "bcc" );
		String subject = (String)request().formValueForKey( "subject" );
		String text = (String)request().formValueForKey( "text" );
		String htmlText = (String)request().formValueForKey( "htmlText" );
		String returnURL = (String)request().formValueForKey( "returnURL" );

		try {
			Properties defaultProperties = new Properties();
			Authenticator auth = null;
			String host = SWSettings.stringForKey( "defaultMailServer" );
			String port = SWSettings.stringForKey( "defaultMailServerSMTPPort" );

			defaultProperties.put( "mail.smtp.host", host );
			if( StringUtilities.hasValue( port ) ) {
				defaultProperties.put( "mail.smtp.port", port );
			}
			if( "postur.lausn.is".equals( host ) ) {
				defaultProperties.put( "mail.smtp.auth", "true" );
				auth = new SMTPAuthenticator( "soloweb@lausn.is", "hth1010" );
			}
			javax.mail.Session defaultMailSession = javax.mail.Session.getDefaultInstance( defaultProperties, auth );
			MimeMessage msg = new MimeMessage( defaultMailSession );
			msg.setSentDate( new Date() );

			if( fromAddress != null && fromName != null ) {
				msg.setFrom( new InternetAddress( fromAddress, fromName, "utf-8" ) );
			}

			if( toAddress != null && toName != null ) {
				msg.setRecipient( Message.RecipientType.TO, new InternetAddress( toAddress, toName, "utf-8" ) );
			}

			if( cc != null ) {
				msg.setRecipients( Message.RecipientType.CC, InternetAddress.parse( cc ) );
			}

			if( bcc != null ) {
				msg.setRecipients( Message.RecipientType.BCC, InternetAddress.parse( bcc ) );
			}

			if( subject != null ) {
				msg.setSubject( subject, "iso-8859-1" );
			}

			// Create the message
			MimeMultipart mp = new MimeMultipart();

			// Insert HTML content

			if( text != null && text != "" ) {
				MimeBodyPart mbp1 = new MimeBodyPart();
				// mbp1.setContent( text, "text/plain; charset=\"iso-8859-1\"");
				mbp1.setContent( text, "text/plain; charset=\"utf-8\"" );
				mp.addBodyPart( mbp1 );
			}

			if( htmlText != null && htmlText != "" ) {
				MimeBodyPart mbp2 = new MimeBodyPart();
				mbp2.setContent( htmlText, "text/html; charset=\"utf-8\"" );
				mp.addBodyPart( mbp2 );
			}

			mp.setSubType( "alternative" );
			msg.setContent( mp );

			Transport.send( msg );

		}
		catch( Exception e ) {
			logger.debug( "error while sending email", e );
		}

		WORedirect redirect = new WORedirect( context() );
		redirect.setUrl( returnURL );
		return redirect;
	}
	*/

	/**
	 * Removed by Hugi 2013-11-21
	 *
	 * Dynamic Component Action :
	 *
	public WOActionResults dcAction() {
		// WOResponse resp = new WOResponse();

		// here the idea was also to include id (for page) parameter in order to
		// know where to
		// display this dynamic component (we need a page for that right?)
		// SWPage page =
		// SWPageUtilities.pageFromRequest(session().defaultEditingContext(),
		// this.request());
		// but the page referes to itself and keeps calling this Action

		SWDynamicComponent compo = (SWDynamicComponent)pageWithName( "SWDynamicComponent" );
		compo.componentName = (String)request().formValueForKey( "name" );
		NSMutableDictionary attributes = new NSMutableDictionary();
		NSArray keys = request().formValueKeys();
		Enumeration e = keys.objectEnumerator();
		while( e.hasMoreElements() ) {
			String key = (String)e.nextElement();
			if( !"name".equals( key ) ) {
				Object value = request().formValueForKey( key );
				attributes.setObjectForKey( value, key );
			}
		}
		compo.setComponentAttributes( attributes );

		return compo;

		// this will create an endless loop since the page keeps referring to
		// itself
		// return displayPageWithTemplate( page, "SWStandardTemplate" );
	}
	*/

	/**
	 * Check all email addresses in the SoloMail user table and report on invalid ones.
	 *
	public WOActionResults checkSoloMailAddressesAction() {
		NSArray emails = EOUtilities.objectsForEntityNamed( session().defaultEditingContext(), "SWMUser" );
		Enumeration iter = emails.objectEnumerator();
		SWMUser email;

		while( iter.hasMoreElements() ) {
			email = (SWMUser)iter.nextElement();
			if( USUtilities.validateEmailAddress( email.emailAddress() ) == false ) {
				log.info( "Address: " + email.emailAddress() + ", name: " + email.name() + ", userid: " + email.userID() );
			}
		}

		return new WOResponse();
	}
	*/

	/**
	 * subPageCountAction returns a response with the number of pages below and
	 * including the page id specified with the URL parameter parentPageID.
	 */
	public WOActionResults subPageCountAction() {
		WOResponse resp = new WOResponse();
		Integer topPageId = new Integer( request().stringFormValueForKey( "parentPageID" ) );

		if( topPageId != null && topPageId.intValue() > 0 ) {
			SWPage topPage = SWPageUtilities.pageWithID( session().defaultEditingContext(), topPageId );
			NSArray<SWPage> thePages = topPage.everySubPage( true );

			if( thePages != null ) {
				String s ="Pages under and including page id " + topPageId + ": ";
				s += thePages.count();
				resp.setContent( s );
			}
		}

		return resp;
	}

	/**
	 * Returns a plain text response with UTF8 encoding that lists all the
	 * SoloWeb user groups that have the CRM group attribute turned on. The
	 * response looks like this:
	 *
	 * groupId <TAB> group name <CRLF>
	 *
	 * The action does not take any input parameters.
	 */
	public WOActionResults getCrmUserGroupsAction() {
		NSArray groupList = USEOUtilities.objectsForKeyWithValueSortedByKey( session().defaultEditingContext(), "SWGroup", "crmGroup", new Integer( 1 ), "name" );

		// Build the group list string
		String groupStr = "";
		if( groupList != null ) {
			Enumeration iter = groupList.objectEnumerator();
			SWGroup aGroup;
			while( iter.hasMoreElements() ) {
				aGroup = (SWGroup)iter.nextElement();
				groupStr += aGroup.primaryKey().toString() + SWDirectAction.TAB_CHARACTER + aGroup.name();
				if( iter.hasMoreElements() ) {
					groupStr += SWDirectAction.CRLF_CHARACTER;
				}
			}
		}

		// Build the response
		WOResponse resp = new WOResponse();
		if( groupStr.length() > 0 ) {
			resp.setContent( groupStr );
		}

		// Return it
		return resp;
	}

	/**
	 * Returns a plain text response with UTF8 encoding that lists all the
	 * SoloWeb user groups that have the CRM group attribute turned on and the
	 * specified crm user is a member of. The response is a TAB separated list of
	 * group ids.
	 *
	 * The action takes 1 input parameter: crmuserid The crm user id for which to
	 * return group membership
	 */
	public WOActionResults getUsersEnabledCrmUserGroupsAction() {
		String groupStr = "";
		Integer crmNafnId = USUtilities.integerFromObject( context().request().formValueForKey( "crmuserid" ) );
		if( crmNafnId != null && crmNafnId.intValue() > 0 ) {
			SWUser user = (SWUser)USEOUtilities.objectMatchingKeyAndValue( session().defaultEditingContext(), SWUser.ENTITY_NAME, SWUser.CRM_NAFN_ID_KEY, crmNafnId );
			if( user != null ) {
				// Build the group list string
				Enumeration iter = user.groups().objectEnumerator();
				SWGroup aGroup;
				while( iter.hasMoreElements() ) {
					aGroup = (SWGroup)iter.nextElement();
					if( aGroup.crmGroup() != null && aGroup.crmGroup().intValue() > 0 ) {
						if( groupStr.length() > 0 ) {
							groupStr += SWDirectAction.TAB_CHARACTER;
						}
						groupStr += aGroup.primaryKey().toString();
					}
				}
			}
		}

		// Build the response
		WOResponse resp = new WOResponse();
		if( groupStr.length() > 0 ) {
			resp.setContent( groupStr );
		}

		// Return it
		return resp;
	}

	public static final String FONT_NORMAL = "normal";
	public static final String FONT_LARGE = "large";
	public static final String FONT_XLARGE = "xlarge";
	public static final String THEME_DEFAULT = "default";
	public static final String THEME_DYSLEX = "dyslex";
	public static final String COOKIE_NAME_FONT = "SW_FONT";
	public static final String COOKIE_NAME_THEME = "SW_THEME";

	private WOCookie fontCookie( String selectedFont ) {
		String host = context().request().headerForKey( "Host" );

		if( host.indexOf( ':' ) > -1 ) {
			host = host.substring( 0, host.indexOf( ':' ) );
		}

		return new WOCookie( SWDirectAction.COOKIE_NAME_FONT, selectedFont, "/", host, 2592000, false );
	}

	public WOActionResults setFontNormalAction() {
		WOActionResults page = this.dpAction();
		WOCookie theCookie = fontCookie( SWDirectAction.FONT_NORMAL );
		WOResponse resp = page.generateResponse();
		resp.addCookie( theCookie );
		return resp;
	}

	public WOActionResults setFontLargeAction() {
		WOActionResults page = this.dpAction();
		WOCookie theCookie = fontCookie( SWDirectAction.FONT_LARGE );
		WOResponse resp = page.generateResponse();
		resp.addCookie( theCookie );
		return resp;
	}

	public WOActionResults setFontXLargeAction() {
		WOActionResults page = this.dpAction();
		WOCookie theCookie = fontCookie( SWDirectAction.FONT_XLARGE );
		WOResponse resp = page.generateResponse();
		resp.addCookie( theCookie );
		return resp;
	}

	public WOActionResults toggleThemeAction() {
		String selectedTheme = context().request().cookieValueForKey( SWDirectAction.COOKIE_NAME_THEME );
		if( selectedTheme == SWDirectAction.THEME_DEFAULT ) {
			selectedTheme = SWDirectAction.THEME_DYSLEX;
		}
		else {
			selectedTheme = SWDirectAction.THEME_DEFAULT;
		}
		WOActionResults page = this.dpAction();
		WOCookie theCookie = fontCookie( selectedTheme );
		WOResponse resp = page.generateResponse();
		resp.addCookie( theCookie );
		return resp;
	}

	public WOActionResults sendNewsItemAction() {
		Number itemId = context().request().numericFormValueForKey( "itemId", new NSNumberFormatter( "0" ) );
		Number pageId = context().request().numericFormValueForKey( "pageId", new NSNumberFormatter( "0" ) );
		String netfang = context().request().stringFormValueForKey( "netfang" );
		String skilabod = context().request().stringFormValueForKey( "skilabod" );
		String fromAddress = SWSettings.stringForKey( "rkiSenderEmail" );

		SWNewsItem item = (SWNewsItem)USEOUtilities.objectWithPK( session().defaultEditingContext(), SWNewsItem.ENTITY_NAME, itemId );

		NSMutableDictionary<String,Object> d = new NSMutableDictionary<>();
		d.setObjectForKey( pageId, "id" );
		d.setObjectForKey( Integer.valueOf( item.primaryKey() ), "detail" );
		String url = context().directActionURLForActionNamed( "dp", d );
		String host = SWDirectAction.hostForRequest( context().request() );

		String html = "";
		html += "<link rel='stylesheet' type='text/css' href='http://rki.is/ytri/css_new/sw32_default.css' />\n";
		html += "<p>&THORN;&eacute;r hefur veri&eth; send fr&eacute;tt fr&aacute; " + host + "</p>\n";
		html += "<p>Skilabo&eth; sendanda:</p>\n";
		html += "<p>" + skilabod.replaceAll( "\n", "<br/>" ) + "</p><hr/>\n";
		html += "<div class='newsItem'>\n";
		html += "<h4>" + item.heading() + "</h4>";
		html += "<div class='excerpt'>";

		if( item.hasPicture2() ) {
			html += "<img src='" + item.picture2().pictureURL() + "'/>";
		}
		else if( item.hasPicture() ) {
			html += "<img src='" + item.picture().pictureURL() + "'/>";
		}

		html += item.excerpt() + "</div>\n";
		html += "<div class='fleira'>";
		html += "<a href='http://" + host + url + "'>Lesa meira...</a>\n";
		html += "</div>";
		html += "</div>";
		String subject = item.heading();
		is.rebbi.wo.util.USMailSender.composeEmail( fromAddress, new NSArray( netfang ), null, null, subject, null, html );

		WORedirect redirect = new WORedirect( context() );
		redirect.setUrl( url );
		return redirect;
	}

	public WOActionResults sendECardAction() {
		String result = "ok";
		SWECard ecard = new SWECard( context(), request() );
		if( ecard != null && !ecard.error ) {
			String senderEmail = request().stringFormValueForKey( "senderEmail" );
			String receiverEmail = request().stringFormValueForKey( "receiverEmail" );
			String subject = request().stringFormValueForKey( "subject" );
			;
			String text = ecard.receiverName + "\r\n\r\n";
			text += request().stringFormValueForKey( "text" ) + "\r\n\r\n";
			text += request().stringFormValueForKey( "kvedja" ) + "\r\n";
			text += ecard.senderName;

			NSArray receiverEmails = NSArray.componentsSeparatedByString( receiverEmail, "," );
			// SWMailSender.composeEmail( senderEmail, receiverEmails, null, null,
			// subject, null, html );
			NSMutableArray images = new NSMutableArray();
			addPic( images, 1016020 ); // top left
			addPic( images, 1016024 ); // top center
			addPic( images, 1016027 ); // top right
			addPic( images, 1016009 ); // middle left
			addPic( images, 1016019 ); // middle center (back)
			addPic( images, 1016021 ); // middle right
			addPic( images, 1016017 ); // bottom left
			addPic( images, 1016013 ); // bottom center
			addPic( images, 1016028 ); // bottom right
			images.addObject( ecard.selectedPicture );
			Enumeration e = receiverEmails.objectEnumerator();
			ecard.setImages( images );
			String html = ecard.generateResponse().contentString();
			while( e.hasMoreElements() ) {
				SWMailSender.composeEmail( senderEmail, new NSArray<>( (String)e.nextElement() ), null, null, subject, text, html, images );
			}

		}
		else {
			result = "error";
		}
		((SWSession)session()).objectStore().takeValueForKey( result, "ecard" );
		return dpAction();
	}

	private void addPic( NSMutableArray images, int id ) {
		SWPicture pic = SWPicture.pictureWithID( session().defaultEditingContext(), id );
		if( pic != null ) {
			images.addObject( pic );
		}
	}
}