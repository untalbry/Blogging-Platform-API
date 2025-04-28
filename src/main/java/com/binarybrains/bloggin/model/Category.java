package com.binarybrains.bloggin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "b02_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    @Column(name = "id_category")
    private Long id;
    @Column(name = "tx_name")
    private String name;
}
