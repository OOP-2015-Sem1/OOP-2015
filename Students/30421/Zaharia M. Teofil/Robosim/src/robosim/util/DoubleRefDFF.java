package robosim.util;

public class DoubleRefDFF {
	private Double_ROI updateSource;
	private final DoubleRef actual;
	
	public DoubleRefDFF() {
		actual = new DoubleRef();
	}
	
	public Double_ROI getRef() {
		return actual;
	}
	
	public double getVal() {
		return actual.getVal();
	}
	
	public void coupleTo(Double_ROI updateSource) {
		this.updateSource = updateSource;
	}
	
	public void update() {
		actual.copy(updateSource);
	}
}
