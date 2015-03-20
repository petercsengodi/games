package hu.csega.alpoc.frame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class AlpocCanvas extends Canvas implements MouseListener, MouseMotionListener {

	private static final Dimension PREFERRED_SIZE = new Dimension(800, 600);
	private static final long serialVersionUID = 1L;

	private BufferedImage buffer = new BufferedImage(PREFERRED_SIZE.width, PREFERRED_SIZE.height, BufferedImage.TYPE_INT_RGB);
	private boolean mouseLeftPressed = false;
	private boolean mouseRightPressed = false;
	private Point mouseLeftAt = new Point(0, 0);
	private Point mouseRightAt = new Point(0, 0);
	private Point translate = new Point(0, 0);
	
	public AlpocCanvas() {
		setPreferredSize(PREFERRED_SIZE);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}	
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)buffer.getGraphics();
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, PREFERRED_SIZE.width, PREFERRED_SIZE.height);
		g2d.setColor(Color.black);
		
		paint2d(g2d);
		
		g.drawImage(buffer, 0, 0, null);
	}
	
	private void paint2d(Graphics2D g) {
		g.translate(PREFERRED_SIZE.width / 2, PREFERRED_SIZE.height / 2);
		g.translate(-translate.x, -translate.y);
		g.drawLine(0, -100, 0, 100);
		g.drawLine(-100, 0, 100, 0);
		g.translate(translate.x, translate.y);
		g.translate(-PREFERRED_SIZE.width / 2, -PREFERRED_SIZE.height / 2);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());
			
		if(mouseRightPressed) {
			translate.x += mouseRightAt.x - p.x;
			translate.y += mouseRightAt.y - p.y;
			
			mouseRightAt.x = p.x;
			mouseRightAt.y = p.y;
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());
		
		if(mouseRightPressed) {
			translate.x += mouseRightAt.x - p.x;
			translate.y += mouseRightAt.y - p.y;
			
			mouseRightAt.x = p.x;
			mouseRightAt.y = p.y;
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			mouseLeftPressed = true;
			mouseLeftAt = new Point(e.getX(), e.getY());
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
		}
		if(e.getButton() == 3) {
			mouseRightPressed = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
