package com.binarybrains.bloggin.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bg01_blog")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_blog")
    private Long id;
    @Column(name = "tx_title")
    private String title;
    @Column(name = "tx_content")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_category")
    private Category category;
    @Column(name = "fh_created_at")
    private LocalDateTime createdAt;
    @Column(name = "fh_last_update")
    private LocalDateTime lastUpdate;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.lastUpdate = LocalDateTime.now();
    }

}
