package entity;

import java.util.ArrayList;
import java.util.List;

public class AbstractMediaCollection<T> {

    String name;
    Enum collectionType;
    List<T> media;

    public AbstractMediaCollection(String name, Enum collectionType, List<T> media) {
        this.name = name;
        this.collectionType = collectionType;
        this.media = media;
    }

    public String getName() {
        return name;
    }

    public Enum getCollectionType() {
        return collectionType;
    }

    public List<T> getMedia() {
        return new ArrayList<>(media);
    }
}
