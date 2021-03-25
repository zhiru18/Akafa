package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
/**
 * This class is used to print swing components.
 * @author Slye and user1401250 on stackoverflow.
 * {@link}https://stackoverflow.com/questions/12764634/printing-a-jframe-and-its-components
 *
 */
public class Printer implements Printable {
    final Component comp;

    public Printer(Component comp){
        this.comp = comp;
    }
    /**
     * This method changes the components to graphics and prints them.
     */
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex > 0) {
            return Printable.NO_SUCH_PAGE;
        }

        // get the bounds of the component
        Dimension dim = comp.getSize();
        double cHeight = dim.getHeight();
        double cWidth = dim.getWidth();

        // get the bounds of the printable area
        double pHeight = pageFormat.getImageableHeight();
        double pWidth = pageFormat.getImageableWidth();

        double pXStart = pageFormat.getImageableX();
        double pYStart = pageFormat.getImageableY();

        double xRatio = pWidth / cWidth;
        double yRatio = pHeight / cHeight;


        Graphics2D graphics2 = (Graphics2D) graphics;
        graphics2.translate(pXStart, pYStart);
        graphics2.scale(xRatio, yRatio);
        comp.printAll(graphics2);

        return Printable.PAGE_EXISTS;
	}
}