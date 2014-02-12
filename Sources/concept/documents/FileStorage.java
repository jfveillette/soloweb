package concept.documents;

import is.rebbi.core.util.StringUtilities;
import is.rebbi.wo.interfaces.UUIDStamped;
import is.rebbi.wo.util.SWSettings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import er.extensions.eof.ERXGenericRecord;

public class FileStorage extends Storage {

	private String _documentPath;

	private String documentPath() {
		if( _documentPath == null ) {
			_documentPath = SWSettings.documentPath();

			if( !StringUtilities.hasValue( _documentPath ) ) {
				_documentPath = SWSettings.stringForKey( "documentLocationOnDisk" );
			}

			if( !StringUtilities.hasValue( _documentPath ) ) {
				throw new RuntimeException( "You must define a document path to store documents using FileStorage." );
			}

			if( _documentPath.endsWith( "/" ) ) {
				_documentPath = _documentPath.substring( 0, _documentPath.length()-1 );
			}
		}


		return _documentPath;
	}

	/**
	 * @return The file on disk
	 */
	private File file( ERXGenericRecord document ) {
		String path = documentPath() + "/" + document.primaryKey();
		File file = new File( path );

		if( document instanceof UUIDStamped && ((UUIDStamped)document).uuid() != null && !file.exists() ) {
			path = documentPath() + "/" + ((UUIDStamped)document).uuid();
			file = new File( path );
		}

		return file;
	}

	@Override
	public void deleteData( ERXGenericRecord document ) {
		File f = file( document );

		if( f != null && f.exists() ) {
			f.delete();
		}
	}

	@Override
	public boolean hasData( ERXGenericRecord document ) {
		File f = file( document );
		return f != null && f.exists() && f.length() > 0;
	}

	@Override
	public long sizeOfData( ERXGenericRecord document ) {
		File f = file( document );

		if( f.exists() ) {
			return (int)f.length();
		}

		return 0;
	}

	@Override
	public InputStream in( ERXGenericRecord document ) {
		try {
			File f = file( document );

			if( !f.exists() ) {
				f.createNewFile();
			}

			return new FileInputStream( f );
		}
		catch( IOException e ) {
			throw new RuntimeException( e );
		}
	}

	@Override
	public OutputStream out( ERXGenericRecord document ) {
		try {
			File f = file( document );

			if( !f.exists() ) {
				f.createNewFile();
			}

			return new FileOutputStream( f );
		}
		catch( IOException e ) {
			throw new RuntimeException( e );
		}
	}
}