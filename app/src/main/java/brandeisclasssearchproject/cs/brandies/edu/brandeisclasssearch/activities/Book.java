package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities;

/**
 * Created by lijiewen on 11/28/16.
 */

public class Book {
    private String imageURL;
    private String text;

    public Book (String imageURL, String text) {
        this.imageURL = imageURL;
        this.text = text;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public String getText() {
        return this.text;
    }
}
