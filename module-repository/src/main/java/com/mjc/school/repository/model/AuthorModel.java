package com.mjc.school.repository.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EqualsAndHashCode
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "author")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorModel implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime lastUpdateDate;


}
