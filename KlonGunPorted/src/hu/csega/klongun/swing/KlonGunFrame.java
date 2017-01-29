package hu.csega.klongun.swing;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import hu.csega.klongun.KlonGun;

public class KlonGunFrame extends JFrame implements KeyListener {

	public KlonGunFrame(KlonGun kg) {
		super("KlonGun game");

		canvas = new KlonGunCanvas(kg);

		Container cp = getContentPane();
		cp.setLayout(new FlowLayout());
		cp.add(canvas);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
			}
		});

		addKeyListener(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public KlonGunControl getControl() {
		return klonGunControl;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT)
			klonGunControl.leftIsOn = true;
		if(keyCode == KeyEvent.VK_RIGHT)
			klonGunControl.rightIsOn = true;
		if(keyCode == KeyEvent.VK_UP)
			klonGunControl.upIsOn = true;
		if(keyCode == KeyEvent.VK_DOWN)
			klonGunControl.downIsOn = true;
		if(keyCode == KeyEvent.VK_CONTROL)
			klonGunControl.controlIsOn = true;
		if(keyCode == KeyEvent.VK_ALT)
			klonGunControl.altIsOn = true;
		if(keyCode == KeyEvent.VK_SHIFT)
			klonGunControl.shiftIsOn = true;
		if(keyCode == KeyEvent.VK_ESCAPE)
			klonGunControl.escapeIsOn = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT)
			klonGunControl.leftIsOn = false;
		if(keyCode == KeyEvent.VK_RIGHT)
			klonGunControl.rightIsOn = false;
		if(keyCode == KeyEvent.VK_UP)
			klonGunControl.upIsOn = false;
		if(keyCode == KeyEvent.VK_DOWN)
			klonGunControl.downIsOn = false;
		if(keyCode == KeyEvent.VK_CONTROL)
			klonGunControl.controlIsOn = false;
		if(keyCode == KeyEvent.VK_ALT)
			klonGunControl.altIsOn = false;
		if(keyCode == KeyEvent.VK_SHIFT)
			klonGunControl.shiftIsOn = false;
		if(keyCode == KeyEvent.VK_ESCAPE)
			klonGunControl.escapeIsOn = false;
	}

	private KlonGunCanvas canvas;
	private KlonGunControl klonGunControl;

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;

}
