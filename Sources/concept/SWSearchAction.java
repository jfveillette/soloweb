package concept;

import is.rebbi.wo.util.USHTTPUtilities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.lucene.queryparser.flexible.standard.QueryParserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;

import concept.components.SWSearchPage;
import concept.data.SWSystemEvent;
import concept.search.Indexer;
import concept.search.SearchTermConstructor;
import er.extensions.appserver.ERXDirectAction;

public class SWSearchAction extends ERXDirectAction {

	private static final Logger logger = LoggerFactory.getLogger( SWSearchAction.class );

	public SWSearchAction( WORequest r ) {
		super( r );
	}

	public WOActionResults searchAction() {
		String searchString = request().stringFormValueForKey( "searchString_field" );
		boolean useInflection = true;

		if( isQuoted( searchString ) ) {
			searchString = QueryParserUtil.escape( searchString.substring( 1, searchString.length()-1 ) );
			searchString = "\"" + searchString + "\"";
		}
		else {
			searchString = QueryParserUtil.escape( searchString );
		}

		String queryString = SearchTermConstructor.constructQueryString( searchString, null, useInflection );

		SWSearchPage nextPage = pageWithName( SWSearchPage.class );
		nextPage.setSearchString( searchString );
		nextPage.setQueryString( queryString );
		nextPage.setUseInflection( useInflection );
		nextPage.setResults( Indexer.results( queryString ) );
		SWSystemEvent.logEvent( "Leit", searchString );

		return nextPage;
	}

	public boolean isQuoted( String s ) {
		return s != null && s.length() > 1 && s.startsWith("\"" ) && s.endsWith( "\"" );
	}

	public WOActionResults searchRedirectionAction() {
		String searchString = request().stringFormValueForKey( "searchString_field" );

		if( searchString != null ) {
			try {
				searchString = URLEncoder.encode( searchString, "UTF-8" );
			}
			catch( UnsupportedEncodingException e ) {
				logger.error( "Failed to encode searchString: " + searchString, e );
			}
		}

		StringBuilder b = new StringBuilder();
		b.append( "http://" );
		b.append( USHTTPUtilities.host( request() ) );
		b.append( "/search/" + searchString );
		String url = b.toString();
		return USHTTPUtilities.redirectTemporary( url );
	}
}