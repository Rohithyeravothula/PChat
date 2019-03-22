package services;

import models.Message;

import java.util.concurrent.ExecutionException;

interface MessageListner {
    public void persist(Message message) throws ExecutionException, InterruptedException;
}
