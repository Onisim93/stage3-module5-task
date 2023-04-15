package com.mjc.school.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "news")
@EntityListeners(value = AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsModel implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;

    @CreatedDate
    private LocalDateTime created;
    @LastModifiedDate
    private LocalDateTime modified;

    @ManyToOne(fetch = FetchType.EAGER)
    private AuthorModel author;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TagModel> tags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "news")
    private List<CommentModel> comments;

    public NewsModel(Long id) {
        this.id = id;
    }

}
