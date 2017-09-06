package brown.prediction.goodprice;

import java.util.Set;

public class Bundle implements IGood {
	
	public Set<Good> bundle; 
	
	public Bundle (Set<Good> goods) {
		this.bundle = goods; 
	}

	@Override
	public String toString() {
		return "Bundle [bundle=" + bundle + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bundle == null) ? 0 : bundle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bundle other = (Bundle) obj;
		if (bundle == null) {
			if (other.bundle != null)
				return false;
		} else if (!bundle.equals(other.bundle))
			return false;
		return true;
	}
	
	
}