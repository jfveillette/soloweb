package concept.components.client;

import is.rebbi.core.util.StringUtilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.google.gdata.data.DateTime;
import com.google.gdata.data.media.mediarss.MediaThumbnail;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import concept.SWGenericComponent;
import concept.SWPageUtilities;
import concept.SWYouTubeUtils;
import concept.data.SWComponent;

public class SWYouTubeList extends SWGenericComponent {

	public VideoFeed videoFeed;
	public int currentVideoIndex;
	public VideoEntry currentVideo;
	public String currentVideoPreviewImageUrl;
	public int currentVideoPreviewImageWidth;
	public int currentVideoPreviewImageHeight;
	public String pageItem;
	private int startIndex;

	public SWYouTubeList( WOContext context ) {
		super( context );
	}

	@Override
	public void setCurrentComponent( SWComponent theComponent ) {
		super.setCurrentComponent( theComponent );
		init();
	}

	private void init() {
		String userId = customInfoString( "swyoutubelist_userid", null );
		int maxVideosInList = customInfoInteger( "swyoutubelist_maxvideosinlist", 0 );

		if( maxVideosInList == 0 ) {
			maxVideosInList = 25;
		}
		startIndex = ((getPg() - 1) * maxVideosInList) + 1;

		videoFeed = null;
		if( StringUtilities.hasValue( userId ) ) {
			videoFeed = SWYouTubeUtils.getUsersUploadedVideosFeed( userId, startIndex, maxVideosInList );
		}
	}

	private int getPg() {
		String pg = (String)context().request().formValueForKey( "pg" );
		if( !StringUtilities.hasValue( pg ) ) {
			return 1;
		}
		else {
			return Integer.valueOf( pg );
		}
	}

	public boolean hasVideos() {
		return (videoFeed != null && videoFeed.getTotalResults() > 0);
	}

	public NSArray videos() {
		NSMutableArray list = null;

		if( hasVideos() ) {
			list = new NSMutableArray( videoFeed.getEntries().toArray() );
		}

		return list;
	}

	public void setCurrentVideo( VideoEntry theVideo ) {
		currentVideo = theVideo;

		currentVideoPreviewImageUrl = null;
		currentVideoPreviewImageWidth = 0;
		currentVideoPreviewImageHeight = 0;

		if( currentVideo != null ) {
			getThumbnailInfo();
		}
	}

	public void getThumbnailInfo() {
		List<MediaThumbnail> thumbnails = currentVideo.getMediaGroup().getThumbnails();
		MediaThumbnail curNail;
		int maxThumbnailWidth = customInfoInteger( "swyoutubelist_maxpreviewimagewidth", -1 );
		boolean scalePreviewImage = customInfoBoolean( "swyoutubelist_scalepreviewimage" );

		if( thumbnails != null && thumbnails.size() > 0 ) {
			if( maxThumbnailWidth == -1 ) {
				// No width specified, use first thumbnail
				curNail = thumbnails.get( 0 );
				currentVideoPreviewImageUrl = curNail.getUrl();
				currentVideoPreviewImageWidth = curNail.getWidth();
				currentVideoPreviewImageHeight = curNail.getHeight();
			}
			else {
				// Width specified
				MediaThumbnail nail = null;

				for( int tnno = 0; tnno < thumbnails.size(); tnno++ ) {
					curNail = thumbnails.get( tnno );
					if( scalePreviewImage && (nail == null || curNail.getWidth() > nail.getWidth()) ) {
						// Find the biggest thumbnail, it will then be scaled to the requested width
						nail = curNail;
					}
					else if( !scalePreviewImage && (nail == null || ((curNail.getWidth() > nail.getWidth() && curNail.getWidth() <= maxThumbnailWidth))) ) {
						// Find the biggest thumbnail that is smaller than the specified width and use that
						nail = curNail;
					}
				}

				currentVideoPreviewImageUrl = nail.getUrl();
				currentVideoPreviewImageWidth = nail.getWidth();
				currentVideoPreviewImageHeight = nail.getHeight();
				if( scalePreviewImage && (maxThumbnailWidth != -1) ) {
					// Scale to requested size
					double ratio = (double)maxThumbnailWidth / (double)currentVideoPreviewImageWidth;
					currentVideoPreviewImageWidth = (int)(currentVideoPreviewImageWidth * ratio);
					currentVideoPreviewImageHeight = (int)(currentVideoPreviewImageHeight * ratio);
				}
			}
		}
	}

	public String videoClass() {
		int videosPerLine = customInfoInteger( "swyoutubelist_videosperline", -1 );
		String klass = "swyoutubelist_video";

		if( videosPerLine > 0 ) {
			if( currentVideoIndex % videosPerLine == 0 ) {
				klass += " first";
			}
			else if( currentVideoIndex % videosPerLine == 2 ) {
				klass += " last";
			}
		}

		return klass;
	}

	public String videoLength() {
		String vlen = "";
		Long lengthInSeconds;
		String minutes, seconds;

		lengthInSeconds = currentVideo.getMediaGroup().getDuration();
		minutes = String.valueOf( lengthInSeconds / 60 );
		if( minutes.length() == 1 ) {
			minutes = "0" + minutes;
		}
		seconds = String.valueOf( lengthInSeconds % 60 );
		if( seconds.length() == 1 ) {
			seconds = "0" + seconds;
		}
		vlen = minutes + ":" + seconds;

		return vlen;
	}

	public String videoUploadDate() {
		String date = "";
		DateTime uploadDate;

		uploadDate = currentVideo.getMediaGroup().getUploaded();
		date = new SimpleDateFormat( "dd.MM.yyyy" ).format( new Date( uploadDate.getValue() ) );

		return date;
	}

	public boolean shouldAddLinebreak() {
		int videosPerLine = customInfoInteger( "swyoutubelist_videosperline", -1 );
		boolean addBreak = false;

		if( currentVideoIndex % videosPerLine == 2 ) {
			addBreak = true;
		}

		return addBreak;
	}

	public String playbackUrl() {
		String url = "";
		String playbackPageName = customInfoString( "swyoutubelist_playbackpagename", null );

		if( StringUtilities.hasValue( playbackPageName ) ) {
			url = "/page/" + playbackPageName + "&videoid=" + currentVideo.getMediaGroup().getVideoId();
		}

		return url;
	}

	public boolean hasPrevPage() {
		return videoFeed.getPreviousLink() != null;
	}

	public String prevPageUrl() {
		String pageUrl = SWPageUtilities.urlForPage( currentComponent().page(), context() );
		int newPage = getPg() - 1;
		pageUrl += "&pg=" + newPage;
		return pageUrl;
	}

	public boolean hasNextPage() {
		return videoFeed.getNextLink() != null;
	}

	public String nextPageUrl() {
		String pageUrl = SWPageUtilities.urlForPage( currentComponent().page(), context() );
		int newPage = getPg() + 1;
		pageUrl += "&pg=" + newPage;
		return pageUrl;
	}
}