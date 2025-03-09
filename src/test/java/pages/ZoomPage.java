package pages;

import org.openqa.selenium.WebDriver;
import java.awt.*;
import java.awt.event.KeyEvent;

public class ZoomPage {
    private WebDriver driver;
    private Robot robot;

    public ZoomPage(WebDriver driver) throws AWTException {
        this.driver = driver;
        this.robot = new Robot();
    }

    // Phương thức Zoom In (Ctrl +)
    public void zoomIn() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ADD);
        robot.keyRelease(KeyEvent.VK_ADD);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    // Phương thức Zoom Out (Ctrl -)
    public void zoomOut() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_SUBTRACT);
        robot.keyRelease(KeyEvent.VK_SUBTRACT);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    // Phương thức Reset Zoom (Ctrl 0)
    public void resetZoom() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_0);
        robot.keyRelease(KeyEvent.VK_0);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }
}
