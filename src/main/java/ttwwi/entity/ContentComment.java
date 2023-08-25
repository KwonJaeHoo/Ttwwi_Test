package ttwwi.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "contents_comments")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class ContentComment 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
}
