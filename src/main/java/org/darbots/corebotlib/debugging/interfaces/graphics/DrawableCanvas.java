package org.darbots.corebotlib.debugging.interfaces.graphics;

public interface DrawableCanvas {
    public static final int DEFAULT_STROKE_WIDTH = 2;
    double getTotalWidth();
    double getTotalHeight();

    void clearCanvas();

    double getStrokeWidth();
    void setStrokeWidth(double width);
    Color getStrokeColor();
    void setStrokeColor(Color strokeColor);
    Color getFillColor();
    void setFillColor(Color fillColor);

    void strokeLine(double startX, double startY, double endX, double endY);
    void strokeRectangle(double startX, double startY, double endX, double endY);
    void strokePolygon(double[] xList, double[] yList);
    void strokeOval(double startX, double startY, double endX, double endY);
    void strokeCircle(double centerX, double centerY, double radius);

    void fillRectangle(double startX, double startY, double endX, double endY);
    void fillPolygon(double[] xList, double[] yList);
    void fillOval(double startX, double startY, double endX, double endY);
    void fillCircle(double centerX, double centerY, double radius);
}
