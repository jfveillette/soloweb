package concept;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.USEOUtilities;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCoding;

import concept.components.SWSimpleSwitch;
import concept.components.client.SWBreadcrumbTrail;
import concept.components.client.SWPictureInsert;
import concept.data.SWComponent;
import concept.data.SWPage;
import concept.data.SWPictureLink;
import er.extensions.appserver.ERXApplication;
import er.extensions.appserver.ERXWOContext;

public abstract class ButurTemplateBase extends SWGenericComponent {

	private static final Logger logger = LoggerFactory.getLogger( ButurTemplateBase.class );

	public ButurTemplateBase( WOContext context ) {
		super( context );
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return true;
	}

	@Override
	public boolean isStateless() {
		return true;
	}

	@Override
	public void reset() {}

	public boolean pictureHasCaption() {
		return (currentComponent().picture() != null) && StringUtilities.hasValue( currentComponent().picture().description() );
	}

	public String textTwoRendered() {
		String result = currentComponent().textTwo();

		if( !StringUtilities.hasValue( result ) ) {
			return "";
		}

		WOContext context = ERXWOContext.newContext();
		context._setSession( session() );

		for( SWPictureLink link : currentComponent().swPictureLinks() ) {
			SWPictureInsert insert = new SWPictureInsert( context );
			insert.link = link;
			String html = insert.generateResponse().contentString();
			result = result.replace( link.name(), html );
		}

		try {
			Pattern pat = Pattern.compile( "\\[([^\\]]+)\\]" );
			Matcher mat = pat.matcher( result );
			StringBuffer buffer = new StringBuffer();

			while( mat.find() ) {
				String match = mat.group( 1 );
				String[] part = match.split( " " );

				EOEditingContext ec = session().defaultEditingContext();

				if( "Butur".equals( part[0] ) ) {
					// [Butur integer]
					String id = "";
					if( part.length > 0 ) {
						id = part[1];
					}
					if( StringUtilities.hasValue( id ) ) {
						SWComponent compo = (SWComponent)USEOUtilities.objectWithPK( ec, SWComponent.ENTITY_NAME, new Integer( id ) );

						if( compo != null ) {
							SWSimpleSwitch ss = ERXApplication.erxApplication().pageWithName( SWSimpleSwitch.class, context );
							ss.setCurrentComponent( compo );
							ss.count = 0;
							String content = ss.generateResponse().contentString();
							mat.appendReplacement( buffer, content );
						}
						else {
							mat.appendReplacement( buffer, "[Butur " + id + "]" );
						}
					}
				}
				else if( "Sida".equals( part[0] ) ) {
					try {
						Integer id = new Integer( part[1] );

						SWPage page = SWPageUtilities.pageWithID( ec, id );
						if( page.equals( selectedPage() ) ) {
							throw new Exception();
						}

						NSArray<SWComponent> list = page.sortedAndApprovedComponents();
						Enumeration<SWComponent> e = list.objectEnumerator();
						String content = "";
						int index = 0;

						while( e.hasMoreElements() ) {
							SWComponent compo = e.nextElement();
							if( compo.page().equals( selectedPage() ) ) {
								throw new Exception();
							}
							SWSimpleSwitch ss = ERXApplication.erxApplication().pageWithName( SWSimpleSwitch.class, context );
							ss.setCurrentComponent( compo );
							ss.index = index;
							ss.count = list.count();
							content += ss.generateResponse().contentString();
							index++;
						}
						mat.appendReplacement( buffer, content );
					}
					catch( Exception e ) {
						logger.error( "Failed to render textTwo content ", e );
					}
				}
				else if( "Breadcrumbs".equals( part[0] ) ) {
					// [Breadcrumbs includeSelf]
					SWBreadcrumbTrail molar = ERXApplication.erxApplication().pageWithName( SWBreadcrumbTrail.class, context );
					// use key value since the parent object isn't valid until this butur is inside a look:
					molar.selectedPage = (SWPage)NSKeyValueCoding.Utility.valueForKey( parent(), "selectedPage" );
					if( part.length > 1 ) {
						molar.includeSelf = true;
					}
					String content = molar.generateResponse().contentString();
					mat.appendReplacement( buffer, content );
				}
				else if( StringUtilities.hasValue( part[0] ) ) {
					// dynamic component [ComponentName arg0 arg1...]
					try {
						WOComponent compo = WOApplication.application().pageWithName( part[0], context );
						if( compo instanceof IDynamicComponent ) {
							HashMap<String, String> params = new HashMap<>();
							Enumeration<String> ee = context().request().formValueKeys().objectEnumerator();
							while( ee.hasMoreElements() ) {
								String key = ee.nextElement();
								params.put( key, context().request().stringFormValueForKey( key ) );
							}
							Vector<String> args = new Vector<>();

							for( int i = 1; i < part.length; i++ ) { // skip first item 0
								args.add( part[i] );
							}

							((IDynamicComponent)compo).setCurrentComponent( currentComponent() );
							((IDynamicComponent)compo).parameters( params );
							((IDynamicComponent)compo).arguments( args );
							String content = compo.generateResponse().contentString();
							mat.appendReplacement( buffer, content );
						}
					}
					catch( Exception e ) {
						// TODO: handle exception
					}
				}
			}

			if( buffer.length() > 0 ) {
				mat.appendTail( buffer );
				result = buffer.toString();
			}

			// match key value coding for retrieving variables such as #currentComponent.page.name#
			pat = Pattern.compile( "#([\\w+.]+)#" );
			mat = pat.matcher( result );
			buffer = new StringBuffer();

			try {
				while( mat.find() ) {
					String keyValue = mat.group( 1 );
					String keys[] = keyValue.split( "\\." );
					Object obj = this;

					for( int i = 0; i < keys.length; i++ ) {
						String key = keys[i];
						obj = NSKeyValueCoding.Utility.valueForKey( obj, key );
					}

					mat.appendReplacement( buffer, obj.toString() );
				}
			}
			catch( Exception e ) {
				logger.error( "Error parsing textTwo", e );
			}

			if( buffer.length() > 0 ) {
				mat.appendTail( buffer );
				result = buffer.toString();
			}

		}
		catch( Error e ) {
			logger.error( "Error parsing textTwo", e );
		}

		return result;
	}
}