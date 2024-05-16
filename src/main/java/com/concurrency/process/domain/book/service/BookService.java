package com.concurrency.process.domain.book.service;

import com.concurrency.process.aop.DistributedLock;
import com.concurrency.process.domain.book.entity.Book;
import com.concurrency.process.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com.concurrency.process.constants.CommonConstants.NORMAL_FORMAT;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private static int count = 0;

    @Transactional
    public void orderBook(Long bookId, int orderCount) {
        Book book = bookRepository.findById(bookId).orElseThrow(NoSuchElementException::new);
        int beforeCount = book.getStockCount();
        try {
            book.decrease(orderCount);
        } catch (IllegalArgumentException e1) {
            throw new RuntimeException("재고가 부족합니다.");
        }
        log.info("{}. 도서명: {}, 재고현황: {} -> {}", ++count, book.getName(), beforeCount, book.getStockCount());
    }

    @DistributedLock(key = "#lockName")
    public void orderBook(String lockName, Long bookId, int orderCount) {
        Book book = bookRepository.findById(bookId).orElseThrow(NoSuchElementException::new);
        int beforeCount = book.getStockCount();
        try {
            book.decrease(orderCount);
        } catch (IllegalArgumentException e1) {
            throw new RuntimeException("재고가 부족합니다.");
        }

        log.info("{}. 도서명: {}, 재고현황: {} -> {}", ++count, book.getName(), beforeCount, book.getStockCount());
    }
}
