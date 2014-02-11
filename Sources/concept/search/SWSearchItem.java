package concept.search;

import er.extensions.eof.ERXEnterpriseObject;

public interface SWSearchItem extends ERXEnterpriseObject {

	public String name();
	public String searchItemText();
	public String searchResultComponentName();
	public boolean isValidResult();
	public void setSearchItemContents( String contents );
}