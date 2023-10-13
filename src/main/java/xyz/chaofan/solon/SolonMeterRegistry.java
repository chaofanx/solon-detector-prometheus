package xyz.chaofan.solon;

import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class SolonMeterRegistry {
  public static final PrometheusMeterRegistry GLOBAL_METER_REGISTRY = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

  public static long applicationStartTime = 0;

  public static void init() {
    Counter.builder("application.start.time").description("Application start time").register(GLOBAL_METER_REGISTRY).increment(applicationStartTime);
  }
}
