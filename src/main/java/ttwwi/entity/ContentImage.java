package ttwwi.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "contents_images")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class ContentImage 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentimageId;
}
