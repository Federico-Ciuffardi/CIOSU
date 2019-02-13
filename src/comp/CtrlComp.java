package comp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CtrlComp implements ICtrlComp {
	
	String lastConfMode;
	String lastLine;
	int lastLvl;
	public DtFullComp fullComp(Path currentPath, Path targetPath) throws IOException {
		return fullComp( new String(Files.readAllBytes(currentPath)), new String(Files.readAllBytes(targetPath)));
	}
	public DtFullComp fullComp(String currentConf, String targetConf){
		float sTNS = System.nanoTime();
		
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
		float eTNS = System.nanoTime();
		System.out.println("Time elapsed: "+(eTNS-sTNS)/1000000000+" secs");
		return dtFullComp;
	}
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
	private String normalize(String string) {
		string = (string).toLowerCase();
		string = string.replaceAll("\\s*$", "");
		string = string.replaceAll("\\s\\s+"," ");
		return string;
	}

	private boolean equivalent(String cmd1, String cmd2) {
		return cmd1.equals(cmd2);
	}	
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
		if(currentLine.contains("address-family ")) {
			currentLvl--;
		}
		if(currentLvl>lastLvl) {
			dtC.setConfMode(lastLine);
			
		}
		if(currentLvl<lastLvl) {
			dtC.setConfMode("");
		}
		lastConfMode = normalize(dtC.getConfMode());
		lastLvl = currentLvl;
		lastLine = normalize(dtC.getLine());
	}
	private boolean stateSet(String line, DtComp comp){
		String mode = comp.getConfMode();
		if(line.contains("!")) {
			comp.setState(State.IGNORED);
			return true;//no comp
		}
		if(line.contains(" secret ") || line.contains(" password ") || line.contains("crypto pki") || line.contains(" pid ") || mode.contains("crypto pki") || mode.contains(" pid ")) {
			comp.setState(State.CAREFUL);
			return true;//no comp
		}
		return false;
	}
}
