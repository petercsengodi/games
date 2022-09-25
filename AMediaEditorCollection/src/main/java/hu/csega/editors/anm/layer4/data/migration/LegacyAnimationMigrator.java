package hu.csega.editors.anm.layer4.data.migration;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import hu.csega.editors.anm.layer4.data.animation.Animation;
import hu.csega.editors.anm.layer4.data.animation.AnimationPart;
import hu.csega.editors.anm.layer4.data.animation.AnimationTransformation;
import hu.csega.games.library.legacy.animationdata.CConnection;
import hu.csega.games.library.legacy.animationdata.CModelData;
import hu.csega.games.library.legacy.animationdata.CPartData;

public class LegacyAnimationMigrator {

	public static Animation migrate(CModelData legacyModelData) {
		int max_scenes = legacyModelData.getMax_scenes();
		List<CPartData> parts = legacyModelData.getParts();
		int maxPartIndex = parts.size();

		Animation animation = new Animation();
		animation.setMaxPartIndex(maxPartIndex);

		int index = 0;
		Map<Integer, AnimationPart> partsMap = new TreeMap<>();
		for(CPartData part : parts) {
			AnimationPart migratedPart = migrate(part);
			partsMap.put(index, migratedPart);
		}

		animation.setParts(partsMap);
		return animation;
	}

	private static AnimationPart migrate(CPartData source) {
		AnimationPart part = new AnimationPart();
		String meshFilename = source.getMesh_file();
		part.setMesh(meshFilename);
		part.setName(meshFilename);

		Vector3f[] centerPoint = source.getCenter_point();
		AnimationTransformation basicTransformation = null; // FIXME: this should be calculated from centerPoints
		part.setBasicTransformation(basicTransformation);
		CConnection[] connections = source.getConnections();
		Matrix4f[] modelTransformByScenes = source.getModel_transform();

		return part;
	}
}
