package concept;

import java.util.HashMap;
import java.util.Vector;

import concept.data.SWComponent;

public interface IDynamicComponent {

	public void parameters( HashMap<String, String> value );
	public void setCurrentComponent( SWComponent value );
	public void arguments( Vector<String> args );
}