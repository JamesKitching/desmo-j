package desmoj.extensions.space2D.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * <p>
 * �berschrift:
 * </p>
 * <p>
 * Beschreibung:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Organisation:
 * </p>
 * 
 * @author unbekannt
 * @version 1.0
 */

public abstract class JBufferedPanel extends JPanel {

	private BufferedImage buffer;

	private Graphics2D bufferG;

	private boolean repaint = true;

	private boolean buffered = true;

	public JBufferedPanel(boolean buffered) {
		super();
		this.buffered = buffered;
	}

	private void checkBufferState() {
		if (buffer == null || getBounds().width != buffer.getWidth(null)
				|| getBounds().height != buffer.getHeight(null)) {
			createBuffer();
			requestRepaint();
		}
	}

	private void createBuffer() {
		buffer = new BufferedImage(getBounds().width, getBounds().height,
				BufferedImage.TYPE_INT_RGB);
		bufferG = buffer.createGraphics();
	}

	public synchronized void requestRepaint() {
		repaint = true;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (buffered) {
			checkBufferState();
			if (repaint) {
				bufferG.setBackground(Color.white);
				bufferG.clearRect(0, 0, buffer.getWidth(null), buffer
						.getHeight(null));
				bufferG.setColor(Color.black);
				paintMe(bufferG);
				repaint = false;
			}
			g.drawImage(buffer, 0, 0, null);
		} else
			paintMe(g);
	}

	protected abstract void paintMe(Graphics g);

}