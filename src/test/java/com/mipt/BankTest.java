package com.mipt;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
  @FunctionalInterface
  interface BankTransferFunction {
    void run(BankAccount firstAccount, BankAccount secondAccount, int amount);
  }

  @Test
  public void successfulSending() {
    BankAccount sender = new BankAccount(100);
    BankAccount receiver = new BankAccount(0);
    Bank bank = new Bank();
    assertEquals(100, sender.getBalance());
    bank.sendToAccount(sender, receiver, 30);
    assertEquals(30, receiver.getBalance());
    assertEquals(70, sender.getBalance());
  }

  @Test
  public void failedSending() {
    BankAccount sender = new BankAccount(100);
    BankAccount receiver = new BankAccount(0);
    Bank bank = new Bank();
    assertThrows(IllegalArgumentException.class, () -> bank.sendToAccount(sender, receiver, 9999));
  }

  @Test
  public void illegalArguments() {
    BankAccount sender = new BankAccount(100);
    BankAccount receiver = new BankAccount(0);
    Bank bank = new Bank();
    assertThrows(IllegalArgumentException.class, () -> bank.sendToAccount(sender, receiver, -1));
    assertThrows(IllegalArgumentException.class, () -> bank.sendToAccount(null, receiver, 5));
    assertThrows(IllegalArgumentException.class, () -> bank.sendToAccount(sender, null, 5));
    assertThrows(IllegalArgumentException.class, () -> bank.sendToAccount(null, null, 5));
  }

  @Test
  public void sendWithDeadlock() throws InterruptedException {
    Bank bank = new Bank();
    assertTrue(deadlock(bank::sendToAccountDeadlock));
    assertFalse(deadlock(bank::sendToAccount));
  }

  private boolean deadlock(BankTransferFunction transferFunction) throws InterruptedException {
    final int MAX_AWAIT_TIME = 1000;
    final int ITERATIONS_NUM = 1000;
    final int THREADS_NUM = 100;

    AtomicInteger completedCounter = new AtomicInteger(0);

    BankAccount firstAccount = new BankAccount(THREADS_NUM * ITERATIONS_NUM);
    BankAccount secondAccount = new BankAccount(THREADS_NUM * ITERATIONS_NUM);

    List<Thread> threads = new ArrayList<>(THREADS_NUM);

    for (int i = 0; i < THREADS_NUM; i++) {
      Thread thread;

      if (i % 2 == 0) {
        thread = new Thread(
            () -> {
              for (int j = 0; j < ITERATIONS_NUM; ++j) {
                transferFunction.run(firstAccount, secondAccount, 1);
                completedCounter.incrementAndGet();
              }
            });
      } else {
        thread = new Thread(
            () -> {
              for (int j = 0; j < ITERATIONS_NUM; ++j) {
                transferFunction.run(secondAccount, firstAccount, 1);
                completedCounter.incrementAndGet();
              }
            });
      }

      threads.add(thread);
      thread.start();
    }

    Thread.sleep(MAX_AWAIT_TIME);

    for (Thread thread : threads) {
      thread.interrupt();
    }

    return completedCounter.get() < THREADS_NUM * ITERATIONS_NUM;
  }
}
