package pl.kalisz.szpital.kancelaria.utils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("serial")
public class Broadcaster implements Serializable {
  static ExecutorService executorService = Executors.newSingleThreadExecutor();

  public interface BroadcastListener {
    void receiveBroadcast(TransactionMessage message);
  }

  private static LinkedList<BroadcastListener> listeners = new LinkedList<BroadcastListener>();

  public static synchronized void register(BroadcastListener listener) {
    listeners.add(listener);
  }

  public static synchronized void unregister(BroadcastListener listener) {
    listeners.remove(listener);
  }

  public static synchronized void broadcast(final TransactionMessage message) {
    for (final BroadcastListener listener : listeners)
      executorService.execute(() -> listener.receiveBroadcast(message));
  }

}
