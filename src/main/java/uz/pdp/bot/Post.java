package uz.pdp.bot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private Integer id;
    private Integer userId;
    private String title;
    private String body;

}
