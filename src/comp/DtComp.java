/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright (C) 2019  Federico Ciuffardi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Please contact me (federico.ciuffardi@outlook.com) if you need 
 * additional information or have any questions.
 */


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
