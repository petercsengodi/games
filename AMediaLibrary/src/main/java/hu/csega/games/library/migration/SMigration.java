package hu.csega.games.library.migration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import hu.csega.games.libary.legacy.modeldata.CEdge;
import hu.csega.games.libary.legacy.modeldata.CFigure;
import hu.csega.games.libary.legacy.modeldata.CModel;
import hu.csega.games.libary.legacy.modeldata.CTexID;
import hu.csega.games.libary.legacy.modeldata.CTriangle;
import hu.csega.games.libary.legacy.modeldata.CVertex;
import hu.csega.games.library.animation.SAnimation;
import hu.csega.games.library.animation.SBodyPart;
import hu.csega.games.library.animation.SConnection;
import hu.csega.games.library.legacy.animationdata.CConnection;
import hu.csega.games.library.legacy.animationdata.CModelData;
import hu.csega.games.library.legacy.animationdata.CPartData;
import hu.csega.games.library.model.SMeshRef;
import hu.csega.games.library.model.SObject;
import hu.csega.games.library.model.STextureRef;
import hu.csega.games.library.model.mesh.v1.SEdge;
import hu.csega.games.library.model.mesh.v1.SMesh;
import hu.csega.games.library.model.mesh.v1.SShape;
import hu.csega.games.library.model.mesh.v1.STriangle;
import hu.csega.games.library.model.mesh.v1.SVertex;

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

		if(name.endsWith(".x"))
			name = name.substring(0, name.length() - 2);

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

		List<SBodyPart> bodyParts = new ArrayList<>();
		for(CPartData p: obj.getParts()) {
			bodyParts.add(migrateCPartData(p));
		}

		SAnimation animation = new SAnimation();
		animation.setName(name);
		animation.setMaxScenes(obj.getMax_scenes());
		animation.setBodyParts(bodyParts);
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

		CTexID textureRef = input.getTexID();
		STextureRef texture = (textureRef != null ? migrateCTexID(textureRef) : null);

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

	private SBodyPart migrateCPartData(CPartData input) {
		SBodyPart converted = (SBodyPart)alreadyConverted.get(input);
		if(converted != null)
			return converted;

		converted = new SBodyPart();
		alreadyConverted.put(input, converted);

		String meshName = input.getMesh_file();
		if(meshName != null) {
			int index = meshName.lastIndexOf('/');
			if(index > -1)
				meshName = meshName.substring(index + 1);

			if(meshName.endsWith(".t3d"))
				meshName = meshName.substring(0, meshName.length() - 4);

			if(meshName.endsWith(".x"))
				meshName = meshName.substring(0, meshName.length() - 2);
		}

		SMeshRef meshRef = new SMeshRef();
		meshRef.setName(meshName);

		Vector3f[] oldCenterPoints = input.getCenter_point();
		Vector3f[] centerPoints = new Vector3f[oldCenterPoints.length];
		for(int i = 0; i < oldCenterPoints.length; i++) {
			centerPoints[i] = new Vector3f(oldCenterPoints[i]);
		}

		Matrix4f[] oldMatrixTransformations = input.getModel_transform();
		Matrix4f[] modelTransformations = new Matrix4f[oldMatrixTransformations.length];
		for(int i = 0; i < oldMatrixTransformations.length; i++) {
			modelTransformations[i] = new Matrix4f(oldMatrixTransformations[i]);
		}

		CConnection[] oldConnections = input.getConnections();
		SConnection[] connections = new SConnection[oldConnections.length];
		for(int i = 0; i < oldConnections.length; i++) {
			connections[i] = migrateCConnection(oldConnections[i]);
		}

		converted.setCenterPoints(centerPoints);
		converted.setConnections(connections);
		converted.setMesh(meshRef);
		converted.setModelTransformations(modelTransformations);

		return converted;
	}


	private SConnection migrateCConnection(CConnection input) {
		SConnection converted = (SConnection)alreadyConverted.get(input);
		if(converted != null)
			return converted;

		converted = new SConnection();
		alreadyConverted.put(input, converted);

		converted.setConnectionIndex(input.getConnection_index());
		converted.setName(input.getName());
		converted.setObjectIndex(input.getObject_index());
		converted.setPoint(input.getPoint());

		return converted;
	}

	private STextureRef migrateCTexID(CTexID input) {
		if(input == null)
			return null;

		STextureRef converted = (STextureRef)alreadyConverted.get(input);
		if(converted != null)
			return converted;

		converted = new STextureRef();
		alreadyConverted.put(input, converted);

		String name = input.name;

		if(name != null) {
			int index = name.lastIndexOf('/');
			if(index > -1)
				name = name.substring(index + 1);

			if(name.endsWith(".bmp") || name.endsWith(".jpg") || name.endsWith(".png"))
				name = name.substring(0, name.length() - 4);

			if(name.endsWith(".jpeg"))
				name = name.substring(0, name.length() - 5);
		}

		converted.setName(name);

		return converted;
	}

	private Map<Object, Object> alreadyConverted = new HashMap<>();
}