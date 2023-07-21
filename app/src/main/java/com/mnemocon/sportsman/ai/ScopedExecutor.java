package com.mnemocon.sportsman.ai;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Обертывает существующий исполнитель, чтобы предоставить метод {@link #shutdown},
 * который позволяет последующую отмену отправленных задач.
 */
public class ScopedExecutor implements Executor {

  private final Executor executor; // Оригинальный исполнитель
  private final AtomicBoolean shutdown = new AtomicBoolean(); // Флаг для отслеживания состояния "завершено"

  public ScopedExecutor(@NonNull Executor executor) {
    this.executor = executor;
  }

  @Override
  public void execute(@NonNull Runnable command) {
    // Ранний выход, если этот объект был завершен
    if (shutdown.get()) {
      return;
    }
    executor.execute(
            () -> {
              // Повторная проверка на случай, если он был завершен между временем
              if (shutdown.get()) {
                return;
              }
              command.run();
            });
  }

  /**
   * После вызова этого метода ни одни из отправленных или последующих задач не начнут выполняться,
   * превращая этот исполнитель в неработающий.
   *
   * <p>Задачи, которые уже начали выполняться, будут продолжены.
   */
  public void shutdown() {
    shutdown.set(true);
  }
}
