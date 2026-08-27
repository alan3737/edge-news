package com.edge_news.event_polling_service.publisher;

import com.edge_news.event_polling_service.message.EventMessage;

public interface Publisher {
    void publish(EventMessage message);
}
