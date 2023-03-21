package com.mjc.school.repository.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tags")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TagModel implements BaseEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    private List<NewsModel> news;

    public TagModel(Long id) {
        this.id = id;
    }
}
