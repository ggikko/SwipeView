package ggikko.me.swipviewapp.dto;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ggikko on 16. 8. 9..
 */

@Getter
@Setter
public class ImageInfo extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private String url;
}
