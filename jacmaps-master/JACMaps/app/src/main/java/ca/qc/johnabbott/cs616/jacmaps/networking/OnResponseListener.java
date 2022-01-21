package ca.qc.johnabbott.cs616.jacmaps.networking;

public interface OnResponseListener<T> {
    void onResponse(T data);
}
