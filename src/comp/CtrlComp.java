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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/*
 * Does the comparisons
 */

public class CtrlComp implements ICtrlComp {
	
	String lastConfMode;//stores the last conf mode
	String lastLine;//stores the last line 
	int lastLvl;//stores the last indentation level

	// does the full comp from paths to files
	public DtFullComp fullComp(Path currentPath, Path targetPath) throws IOException {
		return fullComp( new String(Files.readAllBytes(currentPath)), new String(Files.readAllBytes(targetPath)));
	}
	
	// does the full comp from Strings
	public DtFullComp fullComp(String currentConf, String targetConf){
		//float sTNS = System.nanoTime();
		
		String[] cLines = currentConf.split("\n");
		String[] tLines = targetConf.split("\n");
		
		DtComp[] cComp = new  DtComp[cLines.length];
		DtComp[] tComp = new  DtComp[tLines.length];
		
		preProcess(cLines, cComp);
		preProcess(tLines, tComp);
		
		for(int i=0;i<tLines.length;i++) {
			if(stateSet(tLines[i],tComp[i])) {
				continue;
			}
			tComp[i].setState(State.MISSING);
		}
		
		for(int i=0;i<cLines.length;i++) {
			cComp[i].setState(State.SURPLUS);
			if(stateSet(cLines[i],cComp[i])) {
				continue;
			}
			//
			for(int j=0;j<tLines.length;j++) {
				if(equivalent(cLines[i],tLines[j]) && equivalent(cComp[i].getConfMode(),tComp[j].getConfMode())){
					cComp[i].setState(State.OK);
					cComp[i].setMatchingLine(j);
					tComp[j].setState(State.OK);
					tComp[j].setMatchingLine(i);
					break;
				}
			}
		}
		 
		DtFullComp dtFullComp = new DtFullComp();
		dtFullComp.setCurrentComp(cComp);
		dtFullComp.setTargetComp(tComp);
		//float eTNS = System.nanoTime();
		//System.out.println("Time elapsed: "+(eTNS-sTNS)/1000000000+" secs");
		return dtFullComp;
	}
	//initializes the dtComps and normalizes the string to compare them properly 
	private void preProcess(String[] lines, DtComp[] dtComps) {
		lastConfMode="";
		lastLine="";
		lastLvl = 0;
		for(int i=0;i<lines.length;i++) {
			dtComps[i] = new DtComp();
			dtComps[i].setLine(lines[i]);
			lines[i] = normalize(lines[i]);
			proccessConfMode(dtComps[i]);
		}
	}
	//normalizes the string to compare them properly 
	private String normalize(String string) {
		string = (string).toLowerCase();
		string = string.replaceAll("\\s*$", "");
		string = string.replaceAll("\\s\\s+"," ");
		return string;
	}

	private boolean equivalent(String cmd1, String cmd2) {
		return cmd1.equals(cmd2);
	}	
	//handles the configuration modes | only one level of configuration modes supported 
	private void proccessConfMode(DtComp dtC) {
		dtC.setConfMode(normalize(lastConfMode));
		int currentLvl=-1;
		String currentLine = normalize(dtC.getLine());
		for(int i= 0;i<currentLine.length();i++) {
			if(currentLine.charAt(i)!=' ') {
				currentLvl = i;
				break;
			}
		}
		//special case (configuration mode that does not have the standard indentation
		if(currentLine.contains("address-family ")) {
			currentLvl--;
		}
		//if the current indentation is grater than the last set the last line as the configuration mode
		if(currentLvl>lastLvl) {
			dtC.setConfMode(lastLine);
		}
		//if the current indentation is less than the last set the configuration mode to global
		if(currentLvl<lastLvl) {
			dtC.setConfMode("");
		}
		lastConfMode = normalize(dtC.getConfMode());
		lastLvl = currentLvl;
		lastLine = normalize(dtC.getLine());
	}
	//Set the states returns true if state is already decided and false if needs further comparison
	private boolean stateSet(String line, DtComp comp){
		String mode = comp.getConfMode();
		if(line.contains("!")) {
			comp.setState(State.IGNORED);
			return true;//no comp
		}
		//add on the if below all the strings to be careful about (will never match because of encryption, etc)
		if(line.contains(" secret ") || line.contains(" password ") || line.contains("crypto pki") || line.contains(" pid ") || mode.contains("crypto pki") || mode.contains(" pid ")) {
			comp.setState(State.CAREFUL);
			return true;//no comp
		}
		return false;
	}

}
