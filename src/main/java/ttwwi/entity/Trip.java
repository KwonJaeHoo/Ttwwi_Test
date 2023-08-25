package ttwwi.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "trips")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class Trip 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TripId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column
    private String tripImage;
    
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String oauth2Id;
}