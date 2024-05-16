package com.concurrency.process.domain.book.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer stockCount;

    public void increase() {
        validateStock();
        this.stockCount++;
    }

    public void increase(int count) {
        validateStock();
        this.stockCount += count;
    }

    public void decrease() {
        validateStock();
        this.stockCount--;
    }

    public void decrease(int count) {
        validateStock();
        this.stockCount -= count;
    }

    private void validateStock() {
        if (stockCount < 1) {
            throw new IllegalArgumentException();
        }
    }
}
