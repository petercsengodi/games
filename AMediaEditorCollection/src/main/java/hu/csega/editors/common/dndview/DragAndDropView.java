package hu.csega.editors.common.dndview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import hu.csega.games.common.CommonUIComponent;

public class DragAndDropView extends JPanel implements CommonUIComponent<JFrame, Void>, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

	public static final Dimension PREFERRED_SIZE = new Dimension(400, 300);

	private JFrame parent;
	private JPopupMenu popupMenu;

	private BufferedImage buffer = null;
	private Dimension lastSize = null;

	private boolean mouseLeftPressed = false;
	private boolean mouseRightPressed = false;
	private Point mouseLeftAt = new Point(0, 0);
	private Point mouseRightAt = new Point(0, 0);

	private boolean selectionClickEnabled = false;
	private boolean selectionBoxEnabled = false;
	private Point selectionStart = new Point();
	private Point selectionEnd = new Point();
	private Rectangle selectionBox = new Rectangle();

	private List<DragAndDropModelObject> orderedObjects = new ArrayList<>();
	private Set<DragAndDropModelObject> selectedObjects = new HashSet<>();
	private DragAndDropRenderContext renderContext = new DragAndDropRenderContext();
	private DragAndDropSelectionContext selectionContext = new DragAndDropSelectionContext(selectedObjects);

	public DragAndDropView() {
		setPreferredSize(PREFERRED_SIZE);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);

		this.popupMenu = new JPopupMenu("Context Menu");
		this.popupMenu.add(new JMenuItem("item 1"));
		this.popupMenu.add(new JMenuItem("item 2"));
		this.popupMenu.add(new JMenuItem("item 3"));

		setComponentPopupMenu(popupMenu);
	}

	public void addModelObject(DragAndDropModelObject object) {
		if(object != null) {
			orderedObjects.add(object);
			repaint();
		}
	}

	@Override
	public void paint(Graphics g) {
		Dimension size = getSize();
		if(buffer == null || lastSize == null || !lastSize.equals(size)) {
			buffer = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);

			if(lastSize == null)
				lastSize = new Dimension();

			lastSize.width = size.width;
			lastSize.height = size.height;
		}

		Graphics2D g2d = (Graphics2D)buffer.getGraphics();
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, lastSize.width, lastSize.height);
		g2d.setColor(Color.black);

		paint2d(g2d);

		g.drawImage(buffer, 0, 0, null);
	}

	public void repaintEverything() {
		parent.repaint();
	}

	@Override
	public Void provide() {
		return null;
	}

	@Override
	public void accept(JFrame parent) {
		this.parent = parent;
		this.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());

		if(mouseLeftPressed) {
			int dx = mouseLeftAt.x - p.x;
			int dy = mouseLeftAt.y - p.y;

			if(moved(dx, dy) && selectionClickEnabled) {
				selectionClickEnabled = false;
				selectionBoxEnabled = true;
			}

			if(selectionBoxEnabled) {
				selectionEnd.x -= dx;
				selectionEnd.y -= dy;
				repaint();
			} else {
				boolean changed = false;
				boolean handled = false;

				for(DragAndDropModelObject object : orderedObjects) {
					if(object.pointIsInsideOfHitShape(mouseLeftAt.x, mouseLeftAt.y)) {
						selectionContext.setSelected(selectedObjects.contains(object));
						changed = object.moved(selectionContext, dx, dy);
						handled = true;
						break;
					}
				}

				if(!handled && !selectedObjects.isEmpty()) {
					selectionContext.setSelected(true);
					for(DragAndDropModelObject object : selectedObjects) {
						if(object.pointIsInsideOfHitShape(mouseLeftAt.x, mouseLeftAt.y)) {
							changed = changed || object.moved(selectionContext, dx, dy);
						}
					}
				}

				if(changed)
					repaint();
			}

			mouseLeftAt.x = p.x;
			mouseLeftAt.y = p.y;
		}

		if(mouseRightPressed) {
			int dx = p.x - mouseRightAt.x;
			int dy = p.y - mouseRightAt.y;
			onTranslateView(dx, dy);
			repaint();
			mouseRightAt.x = p.x;
			mouseRightAt.y = p.y;
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());

		if(mouseLeftPressed) {
			int dx = p.x - mouseRightAt.x;
			int dy = p.y - mouseRightAt.y;
			if(moved(dx, dy) && selectionClickEnabled) {
				selectionClickEnabled = false;
				selectionBoxEnabled = true;
			}
		}

		if(mouseRightPressed) {
			onTranslateView(p.x - mouseRightAt.x, p.y - mouseRightAt.y);
			mouseRightAt.x = p.x;
			mouseRightAt.y = p.y;
			repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			mouseLeftPressed = true;
			mouseLeftAt = new Point(e.getX(), e.getY());
			if(!e.isControlDown()) {
				selectionClickEnabled = true;
				// selectionBoxEnabled = true;
				selectionStart.x = selectionEnd.x = mouseLeftAt.x;
				selectionStart.y = selectionEnd.y = mouseLeftAt.y;
			}
		}

		if(e.getButton() == 3) {
			mouseRightPressed = true;
			mouseRightAt = new Point(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == 1) {
			mouseLeftPressed = false;
			if(selectionBoxEnabled) {
				Rectangle box = calculateSelectionBox();

				selectedObjects.clear();
				for(DragAndDropModelObject object : orderedObjects) {
					double x1 = box.getMinX();
					double y1 = box.getMinY();
					double x2 = box.getMaxX();
					double y2 = box.getMaxY();
					if(object.hitShapeIsInsideOfBox(x1, y1, x2, y2)) {
						selectedObjects.add(object);
					}
				}

				selectionBoxEnabled = false;
				repaintEverything();
			} else if(selectionClickEnabled) {
				selectedObjects.clear();
				for(DragAndDropModelObject object : orderedObjects) {
					if(object.pointIsInsideOfHitShape(mouseLeftAt.x, mouseLeftAt.y)) {
						selectedObjects.add(object);
						break;
					}
				}

				repaint();
			}
		} else if(e.getButton() == 3) {
			mouseRightPressed = false;
			this.popupMenu.show(this, mouseRightAt.x, mouseRightAt.y);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(e.isControlDown()) {
				// ...
				repaintEverything();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	protected Rectangle calculateSelectionBox() {
		if(selectionBoxEnabled) {
			selectionBox.x = Math.min(selectionStart.x, selectionEnd.x);
			selectionBox.y = Math.min(selectionStart.y, selectionEnd.y);
			selectionBox.width = Math.abs(selectionStart.x - selectionEnd.x);
			selectionBox.height = Math.abs(selectionStart.y - selectionEnd.y);
			return selectionBox;
		} else {
			return null;
		}
	}

	protected double distance(double x1, double y1, double x2, double y2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		double ret = Math.sqrt(dx*dx + dy*dy);
		return ret;
	}

	private void paint2d(Graphics2D g) {
		renderContext.setGraphics(g);

		for(int index = orderedObjects.size()-1; index >= 0; index--) {
			DragAndDropModelObject object = orderedObjects.get(index);
			renderContext.setSelected(selectedObjects.contains(object));
			object.render(renderContext);
		}

		renderContext.setGraphics(null);
		renderContext.setSelected(false);

		if(selectionBoxEnabled) {
			g.setColor(Color.RED);
			int rx = Math.min(selectionStart.x, selectionEnd.x);
			int ry = Math.min(selectionStart.y, selectionEnd.y);
			int rwidth = Math.max(selectionStart.x, selectionEnd.x) - rx;
			int rheight = Math.max(selectionStart.y, selectionEnd.y) - ry;
			g.drawRect(rx, ry, rwidth, rheight);
		}
	}

	private void onTranslateView(int dx, int dy) {
		// TODO Auto-generated method stub

	}

	private boolean moved(int dx, int dy) {
		return dx < 0 || dx > 0 || dy < 0 || dy > 0;
	}

	public static synchronized String generateNewID() {
		long timestamp = System.currentTimeMillis();

		long counter;
		if(lastIDTimestamp == timestamp) {
			counter = ++lastIDCounter;
		} else {
			counter = lastIDCounter = 1L;
			lastIDTimestamp = timestamp;
		}

		return timestamp + "-" + counter;
	}

	private static long lastIDTimestamp = 0L;
	private static long lastIDCounter = 0L;

	private static final long serialVersionUID = 1L;
}
