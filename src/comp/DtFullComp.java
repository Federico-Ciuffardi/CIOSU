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

public class DtFullComp {
	private DtComp[] currentComp =null;
	private DtComp[] targetComp =null;
	
	public DtComp[] getCurrentComp() {
		return currentComp;
	}
	public void setCurrentComp(DtComp[] currentComp) {
		this.currentComp = currentComp;
	}
	public DtComp[] getTargetComp() {
		return targetComp;
	}
	public void setTargetComp(DtComp[] targetComp) {
		this.targetComp = targetComp;
	}


}
