package ttwwi.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "contents")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class Content 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentId;
}
