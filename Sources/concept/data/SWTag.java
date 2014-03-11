package concept.data;

import is.rebbi.wo.interfaces.TimeStamped;
import is.rebbi.wo.util.HumanReadable;

public class SWTag extends concept.data.auto._SWTag implements TimeStamped, HumanReadable {

	@Override
	public String toStringHuman() {
		return name();
	}
}