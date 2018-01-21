package hu.csega.games.library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.SAXException;

import hu.csega.games.library.migration.MigrationException;
import hu.csega.games.library.migration.SMigration;
import hu.csega.games.library.model.SMeshRef;
import hu.csega.games.library.model.mesh.v1.SMesh;
import hu.csega.games.library.util.FileUtil;
import hu.csega.games.library.xml.v1.XmlReader;

public class MeshLibrary {

	private FileUtil fileUtil;

	public MeshLibrary(FileUtil fileUtil) {
		String t3d_files = fileUtil.projectPath() + "t3d_files";
		List<String> ret = new ArrayList<>();
		FileUtil.collectFiles(t3d_files, ret);

		this.meshes = new HashMap<>();
		for(String fileName : ret) {
			String defaultName = FileUtil.cleanUpName(fileName);
			SMesh mesh = load(fileName, defaultName);
			SMeshRef key = new SMeshRef(mesh.getName());
			meshes.put(key, mesh);
		}
	}

	public SMesh resolve(SMeshRef ref) {
		return meshes.get(ref);
	}

	private static SMesh load(String fileName, String defaultName) {
		SMesh mesh;
		try {
			mesh = (SMesh) SMigration.migrate(XmlReader.read(fileName), defaultName);
		} catch (IOException | MigrationException | SAXException ex) {
			throw new RuntimeException(ex);
		}

		return mesh;
	}

	private Map<SMeshRef, SMesh> meshes;
}