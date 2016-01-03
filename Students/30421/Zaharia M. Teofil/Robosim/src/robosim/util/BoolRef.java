package robosim.util;

public class BoolRef implements Bool_ROI {
	private boolean val;
	
	public BoolRef(boolean val) {
		this.val = val;
	}
	
	public BoolRef() {
		this(false);
	}
	
	public boolean getVal() {
		return val;
	}

	public void setVal(boolean val) {
		this.val = val;
	}
	
	public void copy(Bool_ROI rhs) {
		setVal(rhs.getVal());
	}
	
	
}
