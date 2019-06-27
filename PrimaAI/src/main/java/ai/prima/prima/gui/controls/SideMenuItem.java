package ai.prima.prima.gui.controls;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;

public class SideMenuItem extends GridPane {

    private StringProperty text = new SimpleStringProperty();
    private ObjectProperty<Image> image = new SimpleObjectProperty<>();

    private ImageView imageView;
    private Label label;

    public SideMenuItem(){
        setHgap(20);
        setMaxHeight(10);
        setPadding(new Insets(15, 15, 15, 15));
        getStyleClass().add("hover");
        label = new Label();
        label.getStyleClass().add("h2");
        imageView = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        Bindings.bindBidirectional(imageView.imageProperty(), image);
        Bindings.bindBidirectional(label.textProperty(), text);
        add(imageView, 0, 0);
        add(label, 1, 0);
    }



    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public Image getImage() {
        return image.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public void setImage(Image image) {
        this.image.set(image);
    }
}
