package comp;

import java.io.IOException;
import java.nio.file.Path;

public interface ICtrlComp {

	DtFullComp fullComp(Path currentPath, Path targetPath) throws IOException;

	DtFullComp fullComp(String currentConf, String targetConf);

	
}
