package silaris_client.ReaderConfig;

public interface TagListener {
    void onTag(String epc, int rssi);
}