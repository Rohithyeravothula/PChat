package services;

import models.Message;

interface MessageSender {
    public boolean send(Message message);
}
