package concept.components.tabs;

import com.webobjects.foundation.NSArray;

/**
 * A tab displayed on the website.
 */

public class IMTab {

	private String _name;
	private Integer _count;
	private boolean _disabled;

	public IMTab( String name ) {
		this( name, false );
	}

	public IMTab( String name, NSArray<?> objects ) {
		this( name, objects.count() );
	}

	public IMTab( String name, int count ) {
		setName( name );
		setCount( count );
		setDisabled( count == 0 );
	}

	public IMTab( String name, boolean disabled ) {
		setName( name );
		setDisabled( disabled );
	}

	public String nameWithCount() {
		if( count() != null ) {
			return name() + " (" + count() + ")";
		}

		return name();
	}

	public String name() {
		return _name;
	}

	public void setName( String value ) {
		_name = value;
	}

	public boolean disabled() {
		return _disabled;
	}

	public void setDisabled( boolean value ) {
		_disabled = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (_disabled ? 1231 : 1237);
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		return result;
	}

	public Integer count() {
		return _count;
	}

	public void setCount( Integer count ) {
		this._count = count;
	}

	@Override
	public boolean equals( Object obj ) {
		if( this == obj )
			return true;
		if( obj == null )
			return false;
		if( getClass() != obj.getClass() )
			return false;
		IMTab other = (IMTab)obj;
		if( _disabled != other._disabled )
			return false;
		if( _name == null ) {
			if( other._name != null )
				return false;
		}
		else if( !_name.equals( other._name ) )
			return false;
		return true;
	}
}