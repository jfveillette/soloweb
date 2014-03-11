package concept.data;

import is.rebbi.wo.definitions.AttributeViewDefinition;
import is.rebbi.wo.util.HumanReadable;
import is.rebbi.wo.util.USUtilities;

/**
 * Further definition of an EOAttribute.
 */

public class SWAttributeMeta extends concept.data.auto._SWAttributeMeta implements HumanReadable {

	@Override
	public String toStringHuman() {
		return name();
	}

	public AttributeViewDefinition toAttributeViewDefinition() {
		AttributeViewDefinition a = new AttributeViewDefinition();
		a.setName( name() );
		a.setIcelandicName( icelandicName() );
		a.setText( text() );
		a.setShow( USUtilities.booleanFromObject( showNumeric() ) );
		a.setSortOrder( sortOrder() );
		return a;
	}
}