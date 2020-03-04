package org.darbots.corebotlib.debugging.interfaces.graphics.canvas_packet;

import org.darbots.corebotlib.debugging.interfaces.graphics.DrawableCanvas;

import java.io.Serializable;

public interface CanvasPacketSegment extends Serializable {
    public static final long serialVersionUID = 0L;
    void drawOnCanvas(DrawableCanvas canvas);
}
