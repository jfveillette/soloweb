package concept.data;

import is.rebbi.wo.interfaces.HumanReadable;
import is.rebbi.wo.interfaces.TimeStamped;

public class SWTag extends concept.data.auto._SWTag implements TimeStamped, HumanReadable {

	@Override
	public String toStringHuman() {
		return name();
	}
}