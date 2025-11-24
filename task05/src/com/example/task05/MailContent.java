// MailContent.java
package com.example.task05;

public interface MailContent<T> {
    String getFrom();
    String getTo();
    T getContent();
}