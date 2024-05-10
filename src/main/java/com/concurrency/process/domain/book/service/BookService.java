package com.concurrency.process.domain.book.service;

import com.concurrency.process.aop.DistributedLock;
import com.concurrency.process.domain.book.entity.Book;
import com.concurrency.process.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.NoSuchElementException;

import static com.concurrency.process.constants.CommonConstants.NORMAL_FORMAT;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public void orderBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(NoSuchElementException::new);
        int beforeCount = book.getStockCount();
        book.decrease();
        int afterCount = book.getStockCount();
        log.info("{} - bookId: {}, stockCount: {} -> {}", LocalTime.now().format(NORMAL_FORMAT), book.getId(), beforeCount, afterCount);
    }

    @DistributedLock(key = "#lockName")
    public void orderBook(String lockName, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(NoSuchElementException::new);
        int beforeCount = book.getStockCount();
        book.decrease();
        int afterCount = book.getStockCount();
        log.info("{} - bookId: {}, stockCount: {} -> {}", LocalTime.now().format(NORMAL_FORMAT), book.getId(), beforeCount, afterCount);
    }
}
