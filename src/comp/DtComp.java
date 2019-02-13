package comp;

public class DtComp {
	private String line=null;
	private State state=null;
	private String confMode="";
	private int matchingLine=-1;

	public int getMatchingLine() {
		return matchingLine;
	}
	public void setMatchingLine(int matchingLine) {
		this.matchingLine = matchingLine;
	}
	public String getConfMode() {
		return confMode;
	}
	public void setConfMode(String confMode) {
		this.confMode = confMode;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	public String toString() {
		String out = line +" [State: " + state;
		if(confMode != "") {
			out = out + "|ConfMode: " + confMode;
		}
		if(matchingLine >-1) {
			out = out  + "|Maches with: " + matchingLine;
		}
		out = out + "]";
		return out;
	}
}
