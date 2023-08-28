package com.example.shoppinglist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "images")
public class Image extends BaseEntity {

    @Column
    private String title;

    @Column
    private String url;

    @Column(name = "public_id")
    private String publicId;
}
