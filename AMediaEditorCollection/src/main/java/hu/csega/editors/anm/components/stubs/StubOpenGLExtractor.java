package hu.csega.editors.anm.components.stubs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import hu.csega.editors.anm.components.ComponentOpenGLExtractor;
import hu.csega.editors.anm.layer1.view3d.AnimatorSet;
import hu.csega.editors.anm.layer1.view3d.AnimatorSetPart;
import hu.csega.editors.anm.layer4.data.model.AnimatorModel;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.games.engine.g3d.GameObjectVertex;
import hu.csega.games.engine.g3d.GameTransformation;
import hu.csega.games.units.UnitStore;

public class StubOpenGLExtractor implements ComponentOpenGLExtractor {

	private AnimatorSet set;

	@Override
	public void accept(AnimatorModel model) {
		createTestData();
	}

	private void createTestData() {
		if (set == null) {
			set = new AnimatorSet();

			Matrix4f basicLookAt = new Matrix4f();
			Vector4f tmpEye = new Vector4f();
			Vector4f tmpCenter = new Vector4f();
			Vector4f tmpUp = new Vector4f();
			Matrix4f inverseLookAt = new Matrix4f();
			Matrix4f basicScale = new Matrix4f();
			Matrix4f result = new Matrix4f();

			GameObjectPlacement cameraPlacement = new GameObjectPlacement();
			cameraPlacement.position.x = 0;
			cameraPlacement.position.y = 0;
			cameraPlacement.position.z = 100;
			cameraPlacement.target.x = 0;
			cameraPlacement.target.y = 0;
			cameraPlacement.target.z = 0;
			cameraPlacement.up.x = 0;
			cameraPlacement.up.y = -1;
			cameraPlacement.up.z = 0;

			List<GameObjectVertex> vertices = new ArrayList<>();
			vertices.add(new GameObjectVertex(0, 0, 0, 0, 0, 0, 0, 0));
			vertices.add(new GameObjectVertex(100, 0, 0, 0, 0, 0, 1, 0));
			vertices.add(new GameObjectVertex(0, 100, 0, 0, 0, 0, 0, 1));

			List<Integer> indices = new ArrayList<Integer>();
			indices.add(0);
			indices.add(1);
			indices.add(2);

			GameEngineFacade facade = UnitStore.instance(GameEngineFacade.class);
			GameObjectHandler texture = facade.store().loadTexture("res/textures/wood-texture.jpg");

			GameModelBuilder builder = new GameModelBuilder();
			builder.setTextureHandler(texture);
			builder.setVertices(vertices);
			builder.setIndices(indices);

			GameObjectHandler model = facade.store().buildModel(builder);

			GameTransformation transformation = new GameTransformation();
			GameObjectPlacement placement = new GameObjectPlacement();
			placement.target.set(0f, 0f, 1f);
			placement.up.set(0f, 1f, 0f);
			placement.calculateBasicLookAt(basicLookAt);
			placement.calculateInverseLookAt(basicLookAt, tmpEye, tmpCenter, tmpUp, inverseLookAt);
			placement.calculateBasicScaleMatrix(basicScale);
			inverseLookAt.mul(basicScale, result);
			transformation.importFrom(result);

			AnimatorSetPart part = new AnimatorSetPart();
			part.setModel(model);
			part.setTransformation(transformation);

			set.setCamera(cameraPlacement);
			set.setParts(Arrays.asList(part));
		}
	}

}
