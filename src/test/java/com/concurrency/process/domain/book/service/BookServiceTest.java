package com.concurrency.process.domain.book.service;

import com.concurrency.process.domain.book.entity.Book;
import com.concurrency.process.domain.book.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    private final int TOTAL_BOOK_STOCK_COUNT = 10;
    private final int THREAD_COUNT = TOTAL_BOOK_STOCK_COUNT;
    private Book book;
    @BeforeEach
    void setUp() {
        book = Book.builder().name("nietzsche").stockCount(TOTAL_BOOK_STOCK_COUNT).build();
        bookRepository.save(book);
    }

    @Test
    void 동시주문_분산락_미적용() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    bookService.orderBook(book.getId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Book persistBook = bookRepository.findById(book.getId())
                .orElseThrow(IllegalArgumentException::new);

        assertNotEquals(0, persistBook.getStockCount());
        System.out.println("남은 재고 개수 = " + persistBook.getStockCount());
    }

    @Test
    void 동시주문_분산락_적용() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    bookService.orderBook(book.getName(), book.getId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Book persistBook = bookRepository.findById(book.getId())
                .orElseThrow(IllegalArgumentException::new);

        assertEquals(0, persistBook.getStockCount());
        System.out.println("남은 재고 개수 = " + persistBook.getStockCount());
    }

}