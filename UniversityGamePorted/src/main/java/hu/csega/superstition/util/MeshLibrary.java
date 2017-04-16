package hu.csega.superstition.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.SAXException;

import hu.csega.superstition.gamelib.legacy.migration.MigrationException;
import hu.csega.superstition.gamelib.legacy.migration.SMigration;
import hu.csega.superstition.gamelib.model.SMeshRef;
import hu.csega.superstition.gamelib.model.mesh.SMesh;
import hu.csega.superstition.xml.XmlReader;

public class MeshLibrary {

	private static MeshLibrary instance;

	static {
		instance = new MeshLibrary();

		String root = FileUtil.workspaceRootOrTmp();
		List<String> ret = new ArrayList<>();
		FileUtil.collectFiles(root + File.separator + "UniversityGamePorted" +
				File.separator + "res" + File.separator + "t3d_files", ret);

		for(String fileName : ret) {
			String defaultName = FileUtil.cleanUpName(fileName);
			SMesh mesh = load(fileName, defaultName);
			SMeshRef key = new SMeshRef(mesh.getName());
			instance.meshes.put(key, mesh);
		}
	}

	public static MeshLibrary instance() {
		return instance;
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

	private Map<SMeshRef, SMesh> meshes = new HashMap<>();
}