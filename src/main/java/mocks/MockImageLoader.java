package software.ulpgc.imageviewer.swing;

import software.ulpgc.imageviewer.Image;
import software.ulpgc.imageviewer.ImageLoader;

public class MockImageLoader implements ImageLoader {
    private final String[] images = new String[] {"RED", "GREEN", "BLUE"};
    @Override
    public Image load() {
        return imageAt(0);
    }

    private Image imageAt(int i) {
        return new Image() {
            @Override
            public String name() {
                return images[i];
            }

            @Override
            public Image prev() {
                return imageAt((i + 1) % images.length);
            }

            @Override
            public Image next() {
                return imageAt(i > 0 ? i - 1 : images.length - 1);
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof Image && ((Image) obj).name().equals(this.name());
            }
        };
    }
}
