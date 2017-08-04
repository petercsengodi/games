package hu.csega.superstition.ftm.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.FreeTriangleMeshToolStarter;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;
import hu.csega.superstition.ftm.model.FreeTriangleMeshTriangle;
import hu.csega.superstition.ftm.model.FreeTriangleMeshVertex;

public class FreeTriangleMeshTexture extends FreeTriangleMeshCanvas {

	private int imageWidth = 400;
	private int imageHeight = 400;

	private BufferedImage textureImage = null;
	private boolean triedToLoadImage = false;

	public FreeTriangleMeshTexture(GameEngineFacade facade) {
		super(facade);
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void paint2d(Graphics2D g) {

		if(textureImage == null) {
			if(triedToLoadImage) {
				// ...
			} else {
				triedToLoadImage = true;
				try {
					textureImage = ImageIO.read(new File(FreeTriangleMeshToolStarter.TEXTURE_FILE));
				} catch(Exception ex) {
					textureImage = null;
				}
			}
		}

		if(textureImage != null) {
			g.drawImage(textureImage, 0, 0, null);
		}

		FreeTriangleMeshModel model = (FreeTriangleMeshModel) facade.model();
		List<Object> selectedObjects = model.getSelectedObjects();

		List<FreeTriangleMeshVertex> vertices = model.getVertices();
		List<FreeTriangleMeshTriangle> triangles = model.getTriangles();

		g.setColor(Color.darkGray);
		for(FreeTriangleMeshTriangle triangle : triangles) {
			FreeTriangleMeshVertex v1 = vertices.get(triangle.getVertex1());
			FreeTriangleMeshVertex v2 = vertices.get(triangle.getVertex2());
			FreeTriangleMeshVertex v3 = vertices.get(triangle.getVertex3());

			if(selectedObjects.contains(v1) || selectedObjects.contains(v2) || selectedObjects.contains(v3)) {
				Point p1 = transformVertexToPoint(v1);
				Point p2 = transformVertexToPoint(v2);
				Point p3 = transformVertexToPoint(v3);
				g.drawLine(p1.x, p1.y, p2.x, p2.y);
				g.drawLine(p2.x, p2.y, p3.x, p3.y);
				g.drawLine(p3.x, p3.y, p1.x, p1.y);
			}
		}

		g.setColor(Color.red);
		for(Object object : selectedObjects) {

			if(object instanceof FreeTriangleMeshVertex) {
				FreeTriangleMeshVertex vertex = (FreeTriangleMeshVertex) object;
				Point p = transformVertexToPoint(vertex);
				g.drawRect(p.x - 2, p.y - 2, 5, 5);
			}
		}
	}

	@Override
	protected void translate(double x, double y) {
		// not applicable
	}

	@Override
	protected void zoom(double delta) {
		// not applicable
	}

	@Override
	protected void selectAll(int x1, int y1, int x2, int y2, boolean add) {
		// not applicable
	}

	@Override
	protected void selectFirst(int x, int y, int radius, boolean add) {
		// not applicable
	}

	@Override
	protected void createVertexAt(int x, int y) {
		// not applicable
	}

	@Override
	protected void moveSelected(int x, int y) {
		FreeTriangleMeshModel model = (FreeTriangleMeshModel) facade.model();

		int width = (textureImage == null ? imageWidth : Math.min(imageWidth, textureImage.getWidth()));
		int height = (textureImage == null ? imageHeight : Math.min(imageHeight, textureImage.getHeight()));

		double horizontalStretch = 1.0 / width;
		double horizontalMove = x * horizontalStretch;

		double verticalStretch = 1.0 / height;
		double verticalMove = y * verticalStretch;

		model.moveTexture(horizontalMove, verticalMove);
	}

	@Override
	protected Point transformVertexToPoint(FreeTriangleMeshVertex vertex) {
		int width = (textureImage == null ? imageWidth : Math.min(imageWidth, textureImage.getWidth()));
		int height = (textureImage == null ? imageHeight : Math.min(imageHeight, textureImage.getHeight()));

		double x = vertex.getTX() * width;
		double y = vertex.getTY() * height;
		return new Point((int)x, (int)y);
	}
}
