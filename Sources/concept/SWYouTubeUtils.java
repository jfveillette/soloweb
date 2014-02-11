package concept;

import is.rebbi.core.util.StringUtilities;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;

public class SWYouTubeUtils {

	private static final Logger logger = LoggerFactory.getLogger( SWYouTubeUtils.class );

	private static final String GOOGLE_YOUTUBE_CLIENT_ID = "Lausn-YouTube-1"; // Company-App-Version
	private static final String GOOGLE_YOUTUBE_API_KEY = "AI39si7BvvHXPnf77tcIPhJ4DHIYV5AazSisKRzmrkEjpJmiQBDsNZSdD5spa2vih3MSp0K6m4SmTPeWGtNL46Zyw7Ybz";

	private static final String YOUTUBE_GDATA_SERVER = "http://gdata.youtube.com";
	private static final String GET_VIDEO_ENTRY_URL = YOUTUBE_GDATA_SERVER + "/feeds/api/videos/";
	private static final String GET_USERS_UPLOADED_VIDEOS_FEED_URL = YOUTUBE_GDATA_SERVER + "/feeds/api/users/";
	private static final String GET_USERS_UPLOADED_VIDEOS_FEED_URL_SUFFIX = "/uploads";

	private static YouTubeService service;

	public static void init() {
		service = new YouTubeService( GOOGLE_YOUTUBE_API_KEY );
	}

	public static VideoEntry getVideoById( String videoId ) {
		VideoEntry video = null;

		try {
			if( StringUtilities.hasValue( videoId ) ) {
				video = service.getEntry( new URL( GET_VIDEO_ENTRY_URL + videoId ), VideoEntry.class );
			}
		}
		catch( Exception ex ) {
			logger.error( "Unable to get YouTube video with id=" + videoId + " - ", ex );
		}

		return video;
	}

	public static VideoFeed getUsersUploadedVideosFeed( String userId, int startIndex, int maxResults ) {
		String feedUrl = GET_USERS_UPLOADED_VIDEOS_FEED_URL + userId + GET_USERS_UPLOADED_VIDEOS_FEED_URL_SUFFIX + "?start-index=" + startIndex + "&max-results=" + maxResults;
		VideoFeed videoFeed = null;

		try {
			videoFeed = service.getFeed( new URL( feedUrl ), VideoFeed.class );
		}
		catch( Exception ex ) {
			logger.error( "Unable to get YouTube uploaded videos feed for userid=" + userId + " - ", ex );
		}

		return videoFeed;
	}
}