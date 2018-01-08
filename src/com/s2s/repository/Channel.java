package com.s2s.repository;

import com.s2s.models.Message;

import java.sql.Timestamp;
import java.util.HashMap;

public class Channel extends Repository<Timestamp, Message> {
    Channel() {
        super();
    }

    Channel(HashMap<Timestamp, Message> messages) {
        super(messages);
    }
}
