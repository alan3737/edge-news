interface NewsSource {
    List<EventMessage> fetchAndNormalize() throws IOException;
}