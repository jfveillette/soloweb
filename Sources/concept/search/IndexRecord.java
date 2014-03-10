package concept.search;

import is.rebbi.wo.definitions.EntityViewDefinition;
import is.rebbi.wo.interfaces.PointsToEO;
import is.rebbi.wo.util.SWPKSerialization;
import er.extensions.eof.ERXGenericRecord;

/**
 * A document in the Lucene index.
 */

public class IndexRecord implements PointsToEO {

	/**
	 * Descriptive name.
	 */
	private String _name;

	/**
	 * Descriptive text.
	 */
	private String _text;

	/**
	 * Hidden text used in the index.
	 */
	private String _hiddenText;

	/**
	 * Name of the entity of this record.
	 */
	private String _targetEntityName;

	/**
	 * Unique ID of the record.
	 */
	private String _targetID;

	private EntityViewDefinition _viewDefinition;

	public IndexRecord() {}

	public static IndexRecord create( ERXGenericRecord eo ) {
		String entityName = eo.entityName();
		String targetID = SWPKSerialization.serialize( eo );
		return create( entityName, targetID );
	}

	public static IndexRecord create( String entityName, String targetID ) {
		IndexRecord record = new IndexRecord();
		record.setTargetEntityName( entityName );
		record.setTargetID( targetID );
		return record;
	}

	public String name() {
		return _name;
	}

	public void setName( String name ) {
		_name = name;
	}

	public String text() {
		return _text;
	}

	public void setText( String value ) {
		_text = value;
	}

	public String hiddenText() {
		return _hiddenText;
	}

	public void setHiddenText( String value ) {
		_hiddenText = value;
	}

	@Override
	public String targetEntityName() {
		return _targetEntityName;
	}

	@Override
	public void setTargetEntityName( String value ) {
		_targetEntityName = value;
	}

	@Override
	public String targetID() {
		return _targetID;
	}

	@Override
	public void setTargetID( String value ) {
		_targetID = value;
	}

	/**
	 * @return A unique ID for this record in the index.
	 */
	public String uniqueID() {
		return targetEntityName() + targetID();
	}

	public EntityViewDefinition viewDefinition() {
		if( _viewDefinition == null ) {
			_viewDefinition = EntityViewDefinition.get( targetEntityName() );
		}

		return _viewDefinition;
	}

	/**
	 * @return Icelandic name of the object type associated with this entity.
	 */
	public String icelandicName() {
		if( viewDefinition() != null ) {
			return viewDefinition().icelandicName();
		}

		return null;
	}

	/**
	 * @return The path of an icon for this record.
	 */
	public String iconFileName() {
		if( viewDefinition() != null ) {
			String iconFileName = viewDefinition().iconFileName();

			if( iconFileName != null ) {
				return "icons/" + iconFileName;
			}
		}

		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_targetEntityName == null) ? 0 : _targetEntityName.hashCode());
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		result = prime * result + ((_targetID == null) ? 0 : _targetID.hashCode());
		result = prime * result + ((_text == null) ? 0 : _text.hashCode());
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if( this == obj ) {
			return true;
		}
		if( obj == null ) {
			return false;
		}
		if( getClass() != obj.getClass() ) {
			return false;
		}
		IndexRecord other = (IndexRecord)obj;
		if( _targetEntityName == null ) {
			if( other._targetEntityName != null ) {
				return false;
			}
		}
		else if( !_targetEntityName.equals( other._targetEntityName ) ) {
			return false;
		}
		if( _name == null ) {
			if( other._name != null ) {
				return false;
			}
		}
		else if( !_name.equals( other._name ) ) {
			return false;
		}
		if( _targetID == null ) {
			if( other._targetID != null ) {
				return false;
			}
		}
		else if( !_targetID.equals( other._targetID ) ) {
			return false;
		}
		if( _text == null ) {
			if( other._text != null ) {
				return false;
			}
		}
		else if( !_text.equals( other._text ) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "IMIndexRecord [_name=" + _name + ", _text=" + _text + ", _hiddenText=" + _hiddenText + ", _entityName=" + _targetEntityName + ", _targetID=" + _targetID + "]";
	}
}