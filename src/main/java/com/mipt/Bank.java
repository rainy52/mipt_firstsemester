package com.mipt;

public class Bank {
  public void sendToAccountDeadlock(BankAccount sender, BankAccount receiver, int amount) {
    if (sender == null || receiver == null || amount <= 0 || sender.getId() == receiver.getId()) {
      throw new IllegalArgumentException("Неверно введены данные транзакции");
    }

    synchronized (sender) {
      synchronized (receiver) {
        sender.pay(amount);
        receiver.deposit(amount);
      }
    }
  }

  public void sendToAccount(BankAccount sender, BankAccount receiver, int amount) {
    if (sender == null || receiver == null || amount <= 0 || sender.getId() == receiver.getId()) {
      throw new IllegalArgumentException("Неверно введены параметры транзакции");
    }

    BankAccount first;
    BankAccount second;

    if (sender.getId().compareTo(receiver.getId()) < 0) {
      first = sender;
      second = receiver;
    } else {
      first = receiver;
      second = sender;
    }

    synchronized (first) {
      synchronized (second) {
        sender.pay(amount);
        receiver.deposit(amount);
      }
    }
  }
}
