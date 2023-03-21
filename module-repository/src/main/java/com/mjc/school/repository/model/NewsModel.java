package com.mjc.school.repository.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "news")
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class NewsModel implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;

    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime lastUpdateDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AuthorModel author;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<TagModel> tags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "news")
    private List<CommentModel> comments;

}
