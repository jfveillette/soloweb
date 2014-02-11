package concept.deprecated;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.util.FileType;
import is.rebbi.wo.util.USMailSender;

import java.net.URLEncoder;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;


import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;

import concept.data.SWPicture;

public class SWMailSender {

	private SWMailSender() {}

	public static void composeEmail( String fromEmailAddress, NSArray<String> to, NSArray<String> cc, NSArray<String> bcc, String subject, String plaintextBody, String htmlBody, NSArray images ) {

		try {
			MimeMultipart mp = new MimeMultipart( "alternative" );

			if( StringUtilities.hasValue( plaintextBody ) ) {
				MimeBodyPart bp = new MimeBodyPart();
				bp.setContent( plaintextBody, "text/plain; charset=\"UTF-8\"" );
				mp.addBodyPart( bp );
			}

			if( StringUtilities.hasValue( htmlBody ) ) {
				MimeMultipart bp = new MimeMultipart( "related" );

				MimeBodyPart htmlPart = new MimeBodyPart();
				htmlPart.setContent( htmlBody, "text/html; charset=\"UTF-8\"" );
				bp.addBodyPart( htmlPart );

				for( Object next : images ) {
					BodyPart image = SWMailSender.createEmbeddedImage( next );

					if( image != null ) {
						bp.addBodyPart( image );
					}
				}

				BodyPart altBP = new MimeBodyPart();
				altBP.setContent( bp );
				mp.addBodyPart( altBP );
			}

			USMailSender._sendMessage( fromEmailAddress, to, cc, bcc, subject, mp );
		}
		catch( Exception e ) {
			throw new RuntimeException( "Error sending e-mail", e );
		}
	}

	private static MimeBodyPart createEmbeddedImage( String cid, byte[] data, String mimeType ) {
		try {
			MimeBodyPart bp = new MimeBodyPart();
			ByteArrayDataSource ds = new ByteArrayDataSource( data, mimeType );
			DataHandler dh = new DataHandler( ds );
			bp.setDataHandler( dh );
			bp.setHeader( "Content-ID", "<" + URLEncoder.encode( cid, "UTF-8" ) + ">" );
			bp.setDisposition( "inline" );
			return bp;
		}
		catch( Exception e ) {
			throw new RuntimeException( "An error occurred while creating an embedded image" );
		}
	}

	private static MimeBodyPart createEmbeddedImage( Object pictureParameter ) {

		SWPicture picture;
		String size = "original";

		if( pictureParameter instanceof SWPicture ) {
			picture = (SWPicture)pictureParameter;
		}
		else {
			Object[] o = (Object[])pictureParameter;
			picture = (SWPicture)o[0];
			size = (String)o[1];
		}

		String cid = picture.emailEmbedURLWithoutCID();
		FileType docType = picture.documentType();
		String mimeType = "image/jpeg";

		if( docType != null ) {
			mimeType = docType.mimeType();
		}

		NSData data = picture.dataForSize( size );

		if( data == null ) {
			data = picture.dataForSize( "original" );
		}

		return createEmbeddedImage( cid, data.bytes(), mimeType );
	}
}