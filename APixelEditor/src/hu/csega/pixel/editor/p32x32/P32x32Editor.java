package hu.csega.pixel.editor.p32x32;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hu.csega.pixel.PixelLibrary;
import hu.csega.pixel.PixelSheet;

public class P32x32Editor extends JFrame {

	public P32x32Editor(String filename, int maximumNumberOfSheets, int spwidth, int spheight) {
		super("Pixel Editor 32x32");

		this.maximumNumberOfSheets = maximumNumberOfSheets;
		this.filename = filename;
		this.spwidth = spwidth;
		this.spheight = spheight;

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		canvas = new P32x32Canvas(this);
		contentPane.add(canvas, BorderLayout.NORTH);

		board = new JPanel();
		board.setLayout(new FlowLayout());
		contentPane.add(board, BorderLayout.SOUTH);

		first = new JButton("|<<");
		board.add(first);
		first.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCurrentSheet(0);
			}

		});

		previous = new JButton("<");
		board.add(previous);
		previous.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCurrentSheet(currentSheet - 1);
			}

		});

		numberOfSheet = new JTextField();
		numberOfSheet.setPreferredSize(new Dimension(60, 32));
		numberOfSheet.setText("0");
		board.add(numberOfSheet);
		numberOfSheet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCurrentSheet();
			}
		});

		next = new JButton(">");
		board.add(next);
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCurrentSheet(currentSheet + 1);
			}

		});

		last = new JButton(">>|");
		board.add(last);
		last.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCurrentSheet(P32x32Editor.this.maximumNumberOfSheets - 1);
			}

		});

		copyLabel = new JLabel("Copy this sheet to: ");
		board.add(copyLabel);

		copyInput = new JTextField();
		copyInput.setPreferredSize(new Dimension(60, 32));
		board.add(copyInput);

		copyOk = new JButton("Copy!");
		board.add(copyOk);
		copyOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				copySheetTo();
			}
		});

		save = new JButton("SAVE ALL!");
		board.add(save);
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveToFile();
			}
		});

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
	}

	public void startEditor() {
		File f = new File(filename);
		if(f.exists()) {
			library = PixelLibrary.load(f);
		} else {
			library = new PixelLibrary(maximumNumberOfSheets);
		}

		setVisible(true);
	}

	public int getSpWidth() {
		return spwidth;
	}

	public int getSpHeigth() {
		return spheight;
	}

	public PixelSheet getCurrentSheet() {
		return library.get(currentSheet);
	}

	public void setUsedForCurrentSheet() {
		library.setUsed(currentSheet);
	}

	private void saveToFile() {
		File f = new File(filename);
		if(f.exists())
			f.delete();
		library.save(f);
	}

	private void setCurrentSheet() {
		setCurrentSheet(convertToIndex(numberOfSheet));
	}

	private int convertToIndex(JTextField field) {
		int i;
		try {
			i = Integer.parseInt(field.getText());
		} catch(Exception ex) {
			i = 0;
		}

		return checkIndex(i);
	}

	private int checkIndex(int i) {
		if(i < 0)
			i = 0;
		if(i > maximumNumberOfSheets - 1)
			i = maximumNumberOfSheets - 1;
		return i;
	}

	private void setCurrentSheet(int i) {
		currentSheet = checkIndex(i);
		numberOfSheet.setText(String.valueOf(currentSheet));
		canvas.repaint();
	}

	private void copySheetTo() {
		int i1 = convertToIndex(numberOfSheet);
		int i2 = convertToIndex(copyInput);
		if(i1 != i2) {
			PixelSheet sheetFrom = library.get(i1);
			PixelSheet sheetTo = library.get(i2);
			sheetFrom.copyValuesInto(sheetTo);
		}
	}

	private final String filename;
	private final int maximumNumberOfSheets;

	private PixelLibrary library;
	private int currentSheet = 0;
	private P32x32Canvas canvas;
	private int spwidth;
	private int spheight;

	private JPanel board;
	private JButton first;
	private JButton previous;
	private JTextField numberOfSheet;
	private JButton next;
	private JButton last;
	private JLabel copyLabel;
	private JTextField copyInput;
	private JButton copyOk;
	private JButton save;

	private static final long serialVersionUID = 1L;
}
