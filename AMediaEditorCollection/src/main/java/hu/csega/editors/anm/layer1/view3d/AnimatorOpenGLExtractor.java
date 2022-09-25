package hu.csega.editors.anm.layer1.view3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;

import hu.csega.editors.anm.components.ComponentOpenGLExtractor;
import hu.csega.editors.anm.components.ComponentOpenGLTransformer;
import hu.csega.editors.anm.layer4.data.animation.Animation;
import hu.csega.editors.anm.layer4.data.animation.AnimationMisc;
import hu.csega.editors.anm.layer4.data.animation.AnimationPart;
import hu.csega.editors.anm.layer4.data.animation.AnimationPlacement;
import hu.csega.editors.anm.layer4.data.animation.AnimationScene;
import hu.csega.editors.anm.layer4.data.animation.AnimationScenePart;
import hu.csega.editors.anm.layer4.data.animation.AnimationVector;
import hu.csega.editors.anm.layer4.data.model.AnimationPersistent;
import hu.csega.editors.anm.layer4.data.model.AnimatorModel;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.games.engine.g3d.GameTransformation;
import hu.csega.games.library.util.FileUtil;
import hu.csega.games.units.UnitStore;

public class AnimatorOpenGLExtractor implements ComponentOpenGLExtractor {

	private AnimatorSet set;
	private ComponentOpenGLTransformer transformer;
	private GameEngineFacade facade;
	private GameModelStore store;
	private FileUtil files;

	private Matrix4f m1 = new Matrix4f();
	private Matrix4f m2 = new Matrix4f();
	private Matrix4f m3 = new Matrix4f();
	private Matrix4f m4 = new Matrix4f();
	private Matrix4f m5 = new Matrix4f();

	@Override
	public void accept(AnimatorModel model) {
		if(model == null) {
			return;
		}

		if(transformer == null) {
			transformer = UnitStore.instance(ComponentOpenGLTransformer.class);
		}

		if(facade == null || store == null) {
			facade = UnitStore.instance(GameEngineFacade.class);
			store = facade.store();
		}

		if(files == null) {
			files = UnitStore.instance(FileUtil.class);
		}

		synchronized (model) {
			generateSet(model.getPersistent());
		}

		transformer.accept(set);
		return;
	}

	private void generateSet(AnimationPersistent persistent) {
		if(this.set == null) {
			this.set = new AnimatorSet();
		}

		int currentScene = 0;
		GameObjectPlacement camera = new GameObjectPlacement();
		List<AnimatorSetPart> parts = new ArrayList<>();

		if(persistent != null) {
			AnimationMisc misc = persistent.getMisc();
			if(misc != null) {
				currentScene = misc.getCurrentScene();

				AnimationPlacement cam = misc.getCamera();
				if(cam != null) {
					AnimationVector pos = cam.getPosition();
					if(pos != null && pos.getV() != null) {
						float[] v = pos.getV();
						camera.position.set(v[0], v[1], v[2]);
					} else {
						camera.position.set(0f, 0f, -100f);
					}

					AnimationVector tar = cam.getTarget();
					if(tar != null && tar.getV() != null) {
						float[] v = tar.getV();
						camera.target.set(v[0], v[1], v[2]);
					} else {
						camera.target.set(0f, 0f, 0f);
					}

					AnimationVector up = cam.getTarget();
					if(up != null && up.getV() != null) {
						float[] v = up.getV();
						camera.up.set(v[0], v[1], v[2]);
					} else {
						camera.up.set(0f, 1f, 0f);
					}
				} else {
					camera.position.set(0f, 400f, -400f);
					camera.target.set(0f, 0f, 0f);
					camera.up.set(0f, 1f, 0f);
				}
			} else {
				camera.position.set(0f, 400f, -400f);
				camera.target.set(0f, 0f, 0f);
				camera.up.set(0f, 1f, 0f);
			}

			Animation animation = persistent.getAnimation();
			if(animation != null) {

				Map<Integer, AnimationPart> map = animation.getParts();
				if(map != null && map.size() > 0) {

					List<AnimationScene> scenes = animation.getScenes();
					if(scenes != null && scenes.size() > 0) {
						if(currentScene < 0 || currentScene >= scenes.size()) {
							currentScene = 0;
						}

						AnimationScene scene = scenes.get(currentScene);
						Map<Integer, AnimationScenePart> sceneParts = scene.getSceneParts();
						if(sceneParts != null && sceneParts.size() > 0) {
							generateParts(parts, sceneParts, map);
						}
					}

				} // end map check
			}

		}

		set.setCamera(camera);
		set.setParts(parts);
	}

	private void generateParts(List<AnimatorSetPart> parts,
			Map<Integer, AnimationScenePart> sceneParts, Map<Integer, AnimationPart> map) {


		for(Map.Entry<Integer, AnimationScenePart> entry : sceneParts.entrySet()) {
			Integer partKey = entry.getKey();
			AnimationScenePart modifiers = entry.getValue();
			if(!modifiers.isVisible()) {
				continue;
			}

			AnimationPart animationPart = map.get(partKey);
			if(animationPart != null) {

				String filename = animationPart.getMesh();
				if(filename == null || filename.length() == 0) {
					continue;
				}

				if(filename.charAt(0) != '/') {
					filename = files.projectPath() + filename;
				}

				GameObjectHandler model = store.loadModel(filename);
				if(model == null) {
					throw new RuntimeException("Couldn't load game model: " + filename);
				}

				m1.set(animationPart.getBasicTransformation().getM());
				m2.set(modifiers.getModelTransformation().getM());
				m3.set(modifiers.getPartTransformation().getM());
				m1.mul(m2, m4);
				m4.mul(m3, m5);

				GameTransformation transformation = new GameTransformation();
				transformation.importFrom(m5);

				AnimatorSetPart setPart = new AnimatorSetPart();
				setPart.setTransformation(transformation);
				setPart.setModel(model);
				parts.add(setPart);
			}
		}

	}

}
