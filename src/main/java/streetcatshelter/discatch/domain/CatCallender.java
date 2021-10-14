package streetcatshelter.discatch.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CatCallender {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;



}
