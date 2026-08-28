package com.edge_news.event_polling_service.source;

import com.edge_news.event_polling_service.message.EventMessage;
import java.io.IOException;
import java.util.List;

public interface NewsSource {
    List<EventMessage> fetchAndNormalize() throws IOException;
}