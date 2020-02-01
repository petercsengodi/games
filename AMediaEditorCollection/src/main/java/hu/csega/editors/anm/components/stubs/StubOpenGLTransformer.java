package hu.csega.editors.anm.components.stubs;

import java.util.ArrayList;
import java.util.List;

import hu.csega.editors.anm.components.ComponentOpenGLTransformer;
import hu.csega.editors.anm.model.AnimatorModel;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectVertex;

public class StubOpenGLTransformer implements ComponentOpenGLTransformer {

	private GameEngineFacade facade;
	private GameObjectHandler texture;

	@Override
	public GameModelBuilder transform(AnimatorModel model) {
		List<GameObjectVertex> vertices = new ArrayList<>();
		vertices.add(new GameObjectVertex(0, 0, 0, 0, 0, 0, 0, 0));
		vertices.add(new GameObjectVertex(100, 0, 0, 0, 0, 0, 1, 0));
		vertices.add(new GameObjectVertex(0, 100, 0, 0, 0, 0, 0, 1));

		List<Integer> indices = new ArrayList<Integer>();
		indices.add(0);
		indices.add(1);
		indices.add(2);

		GameModelBuilder result = new GameModelBuilder();
		result.setTextureHandler(texture);
		result.setVertices(vertices);
		result.setIndices(indices);
		return result;
	}

	@Override
	public void setGameEngineFacade(GameEngineFacade facade) {
		if(this.facade == null) {
			this.facade = facade;
			this.texture = facade.store().loadTexture("res/textures/ship1.jpg");
		}
	}

}
