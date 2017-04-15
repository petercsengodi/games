package hu.csega.pixel.editor;

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

public class PixelEditor extends JFrame {

	public static final String PIX_FILE = "tmp.pix";
	public static final int MAXIMUM_NUMBER_OF_SHEETS = 100;

	public static void main(String[] args) {
		PixelEditor editor = new PixelEditor();

		File f = new File(PIX_FILE);
		if(f.exists()) {
			editor.library = PixelLibrary.load(f);
		} else {
			editor.library = new PixelLibrary(MAXIMUM_NUMBER_OF_SHEETS);
		}

		editor.setVisible(true);
	}

	public PixelEditor() {
		super("Pixel Editor");

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		canvas = new PixelCanvas(this);
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
				setCurrentSheet(MAXIMUM_NUMBER_OF_SHEETS - 1);
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

	public PixelSheet getCurrentSheet() {
		return library.get(currentSheet);
	}

	public void setUsedForCurrentSheet() {
		library.setUsed(currentSheet);
	}

	private void saveToFile() {
		File f = new File(PIX_FILE);
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
		if(i > MAXIMUM_NUMBER_OF_SHEETS - 1)
			i = MAXIMUM_NUMBER_OF_SHEETS - 1;
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

	private PixelLibrary library;
	private int currentSheet = 0;
	private PixelCanvas canvas;

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
