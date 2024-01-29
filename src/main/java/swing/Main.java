package software.ulpgc.imageviewer.swing;

import software.ulpgc.imageviewer.Image;
import software.ulpgc.imageviewer.ImageDisplay;
import software.ulpgc.imageviewer.ImagePresenter;

public class Main {
    public static void main(String[] args) {
        Image image = new MockImageLoader().load();
        MainFrame mainFrame = new MainFrame();
        ImageDisplay imageDisplay = mainFrame.imageDisplay();
        new ImagePresenter(image, imageDisplay);
        mainFrame.setVisible(true);

    }
}
