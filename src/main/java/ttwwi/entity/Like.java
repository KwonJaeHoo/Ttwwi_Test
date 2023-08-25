package ttwwi.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "likes")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class Like 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long LikeId;
}
