package com.mipt;

import java.util.UUID;

public class BankAccount {
  private final UUID id;
  private int balance;

  public BankAccount(int balance) {
    this.id = UUID.randomUUID();
    this.balance = balance;
  }

  public UUID getId() {
    return id;
  }
  public int getBalance() {
    return balance;
  }

  public void deposit(int amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Неверная сумма");
    }
    this.balance += amount;
  }

  public void pay(int amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Неверная сумма");
    }
    if (this.balance < amount) {
      throw new IllegalArgumentException("Недостаточно средств");
    }
    this.balance -= amount;
  }
}
