package hu.csega.superstition.gamelib.legacy.migration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.csega.superstition.gamelib.legacy.animationdata.CModelData;
import hu.csega.superstition.gamelib.legacy.modeldata.CEdge;
import hu.csega.superstition.gamelib.legacy.modeldata.CFigure;
import hu.csega.superstition.gamelib.legacy.modeldata.CModel;
import hu.csega.superstition.gamelib.legacy.modeldata.CTexID;
import hu.csega.superstition.gamelib.legacy.modeldata.CTriangle;
import hu.csega.superstition.gamelib.legacy.modeldata.CVertex;
import hu.csega.superstition.gamelib.model.SAnimation;
import hu.csega.superstition.gamelib.model.SEdge;
import hu.csega.superstition.gamelib.model.SMesh;
import hu.csega.superstition.gamelib.model.SObject;
import hu.csega.superstition.gamelib.model.SShape;
import hu.csega.superstition.gamelib.model.STextureRef;
import hu.csega.superstition.gamelib.model.STriangle;
import hu.csega.superstition.gamelib.model.SVertex;

public class SMigration {

	public static SObject migrate(Object obj, String name) throws MigrationException {
		if(obj == null || obj instanceof SObject)
			return (SObject)obj;

		if(name == null || name.length() == 0)
			throw new MigrationException("Object name is missing!");

		int index = name.lastIndexOf('/');
		if(index > -1)
			name = name.substring(index + 1);

		if(name.endsWith(".t3d") || name.endsWith(".anm"))
			name = name.substring(0, name.length() - 4);

		SMigration migration = new SMigration();

		Class<?> c = obj.getClass();
		if(c == CModelData.class) {
			return migration.migrateAnimationData((CModelData)obj, name);
		} else if(c == CModel.class) {
			return migration.migrateMeshData((CModel)obj, name);
		} else {
			throw new MigrationException("Don't know, how to convert object!");
		}
	}

	private SObject migrateAnimationData(CModelData obj, String name) {
		SAnimation converted = (SAnimation)alreadyConverted.get(obj);
		if(converted != null)
			return converted;

		converted = new SAnimation();
		alreadyConverted.put(obj, converted);

		SAnimation animation = new SAnimation();
		animation.setName(name);
		return animation;
	}

	private SObject migrateMeshData(CModel model, String name) {
		SMesh converted = (SMesh)alreadyConverted.get(model);
		if(converted != null)
			return converted;

		converted = new SMesh();
		alreadyConverted.put(model, converted);

		List<SShape> figures = new ArrayList<>();
		for(CFigure f : model.figures) {
			figures.add(migrateCFigure(f));
		}

		SMesh mesh = new SMesh();
		mesh.setName(name);
		mesh.setFigures(figures);
		return mesh;
	}

	private SShape migrateCFigure(CFigure input) {
		SShape converted = (SShape)alreadyConverted.get(input);
		if(converted != null)
			return converted;

		converted = new SShape();
		alreadyConverted.put(input, converted);

		STextureRef texture = migrateCTexID(input.getTexID());

		List<SVertex> vertices = new ArrayList<>();
		for(CVertex v : input.getVertices()) {
			vertices.add(migrateCVertex(v));
		}

		List<STriangle> triangles = new ArrayList<>();
		for(CTriangle t : input.getTriangles()) {
			triangles.add(migrateCTriangle(t));
		}

		converted.setAmbientColor(input.getAmbient_color());
		converted.setDiffuseColor(input.getDiffuse_color());
		converted.setEmissiveColor(input.getEmissive_color());
		converted.setTexture(texture);
		converted.setTriangles(triangles);
		converted.setVertices(vertices);

		return converted;
	}

	private SVertex migrateCVertex(CVertex input) {
		SVertex converted = (SVertex)alreadyConverted.get(input);
		if(converted != null)
			return converted;

		converted = new SVertex();
		alreadyConverted.put(input, converted);

		List<SEdge> edges = new ArrayList<>();
		for(CEdge e : input.getEdges()) {
			edges.add(migrateCEdge(e));
		}

		List<SVertex> neighbours = new ArrayList<>();
		for(CVertex v : input.getNeighbours()) {
			neighbours.add(migrateCVertex(v));
		}

		List<STriangle> triangles = new ArrayList<>();
		for(CTriangle t : input.getTriangles()) {
			triangles.add(migrateCTriangle(t));
		}

		converted.setEdges(edges);
		converted.setNeighbours(neighbours);
		converted.setTriangles(triangles);
		converted.setPosition(input.getPosition());
		converted.setTextureCoordinates(input.getTexture_coordinates());

		return converted;
	}

	private SEdge migrateCEdge(CEdge input) {
		SEdge converted = (SEdge)alreadyConverted.get(input);
		if(converted != null)
			return converted;

		converted = new SEdge();
		alreadyConverted.put(input, converted);

		converted.setFrom(migrateCVertex(input.getFrom()));
		converted.setTo(migrateCVertex(input.getTo()));
		converted.setTriangle(migrateCTriangle(input.getTriangle()));

		return converted;
	}

	private STriangle migrateCTriangle(CTriangle input) {
		STriangle converted = (STriangle)alreadyConverted.get(input);
		if(converted != null)
			return converted;

		converted = new STriangle();
		alreadyConverted.put(input, converted);

		List<SVertex> vertices = new ArrayList<>();
		for(CVertex v : input.getVertices()) {
			vertices.add(migrateCVertex(v));
		}

		List<SEdge> edges = new ArrayList<>();
		for(CEdge e : input.getEdges()) {
			edges.add(migrateCEdge(e));
		}

		List<STriangle> neighbours = new ArrayList<>();
		for(CTriangle t : input.getNeighbours()) {
			neighbours.add(migrateCTriangle(t));
		}

		converted.setEdges(edges);
		converted.setNeighbours(neighbours);
		converted.setVertices(vertices);
		converted.setCount(input.getCount());

		return converted;
	}

	private STextureRef migrateCTexID(CTexID input) {
		STextureRef converted = (STextureRef)alreadyConverted.get(input);
		if(converted != null)
			return converted;

		converted = new STextureRef();
		alreadyConverted.put(input, converted);

		converted.setName(input.name);

		return converted;
	}

	private Map<Object, Object> alreadyConverted = new HashMap<>();
}
