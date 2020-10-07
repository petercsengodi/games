package hu.csega.editors.ftm.layer1.presentation.swing.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import hu.csega.editors.FreeTriangleMeshToolStarter;
import hu.csega.editors.common.lens.EditorPoint;
import hu.csega.editors.ftm.model.FreeTriangleMeshModel;
import hu.csega.editors.ftm.model.FreeTriangleMeshTriangle;
import hu.csega.editors.ftm.model.FreeTriangleMeshVertex;
import hu.csega.games.engine.GameEngineFacade;

public class FreeTriangleMeshTexture extends FreeTriangleMeshCanvas {

	private int imageWidth = 300;
	private int imageHeight = 300;

	private String textureFilename = null;
	private BufferedImage textureImage = null;
	private boolean triedToLoadImage = false;

	public FreeTriangleMeshTexture(GameEngineFacade facade) {
		super(facade);
	}

	@Override
	protected void paint2d(Graphics2D g) {
		FreeTriangleMeshModel model = (FreeTriangleMeshModel) facade.model();
		String textureFilenameInModel = model.getTextureFilename();
		if(textureFilenameInModel == null || textureFilenameInModel.isEmpty())
			textureFilenameInModel = FreeTriangleMeshToolStarter.DEFAULT_TEXTURE_FILE;

		if(textureFilename == null || !textureFilename.equals(textureFilenameInModel)) {
			textureFilename = textureFilenameInModel;
			triedToLoadImage = false;
			textureImage = null;
		}

		if(textureImage == null) {
			if(triedToLoadImage) {
				// ...
			} else {
				triedToLoadImage = true;
				try {
					textureImage = ImageIO.read(new File(textureFilename));
				} catch(Exception ex) {
					textureImage = null;
				}
			}
		}

		if(textureImage != null) {
			g.drawImage(textureImage, 0, 0, imageWidth, imageHeight, null);
		}

		List<Object> selectedObjects = model.getSelectedObjects();

		List<FreeTriangleMeshVertex> vertices = model.getVertices();
		List<FreeTriangleMeshTriangle> triangles = model.getTriangles();

		g.setColor(Color.darkGray);
		for(FreeTriangleMeshTriangle triangle : triangles) {
			if(model.enabled(triangle)) {
				FreeTriangleMeshVertex v1 = vertices.get(triangle.getVertex1());
				FreeTriangleMeshVertex v2 = vertices.get(triangle.getVertex2());
				FreeTriangleMeshVertex v3 = vertices.get(triangle.getVertex3());

				if(selectedObjects.contains(v1) || selectedObjects.contains(v2) || selectedObjects.contains(v3)) {
					EditorPoint p1 = transformVertexToPoint(v1);
					EditorPoint p2 = transformVertexToPoint(v2);
					EditorPoint p3 = transformVertexToPoint(v3);
					g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
					g.drawLine((int)p2.getX(), (int)p2.getY(), (int)p3.getX(), (int)p3.getY());
					g.drawLine((int)p3.getX(), (int)p3.getY(), (int)p1.getX(), (int)p1.getY());
				}
			}
		}

		g.setColor(Color.red);
		for(Object object : selectedObjects) {

			if(object instanceof FreeTriangleMeshVertex) {
				FreeTriangleMeshVertex vertex = (FreeTriangleMeshVertex) object;
				if(model.enabled(vertex)) {
					EditorPoint p = transformVertexToPoint(vertex);
					g.drawRect((int)p.getX() - 2, (int)p.getY() - 2, 5, 5);
				}
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
	protected void selectAll(double x1, double y1, double x2, double y2, boolean add) {
		// not applicable
	}

	@Override
	protected void selectFirst(double x, double y, double radius, boolean add) {
		// not applicable
	}

	@Override
	protected void createVertexAt(double x, double y) {
		// not applicable
	}

	@Override
	protected void moveSelected(double x, double y) {
		FreeTriangleMeshModel model = (FreeTriangleMeshModel) facade.model();

		int width = (textureImage == null ? imageWidth : Math.min(imageWidth, textureImage.getWidth()));
		int height = (textureImage == null ? imageHeight : Math.min(imageHeight, textureImage.getHeight()));

		double horizontalStretch = 1.0 / width;
		double horizontalMove = x * horizontalStretch;

		double verticalStretch = 1.0 / height;
		double verticalMove = -y * verticalStretch;

		model.moveTexture(horizontalMove, verticalMove);
	}

	@Override
	protected EditorPoint transformVertexToPoint(FreeTriangleMeshVertex vertex) {
		int width = (textureImage == null ? imageWidth : Math.min(imageWidth, textureImage.getWidth()));
		int height = (textureImage == null ? imageHeight : Math.min(imageHeight, textureImage.getHeight()));

		double x = vertex.getTX() * width;
		double y = (1 - vertex.getTY()) * height;
		return new EditorPoint(x, y, 0.0, 1.0);
	}

	private static final long serialVersionUID = 1L;
}
