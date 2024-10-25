package xyz.chaofan.solon;

import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import xyz.chaofan.solon.integration.JvmInfo;

public class SolonMeterRegistry {
  public static final PrometheusMeterRegistry GLOBAL_METER_REGISTRY = new PrometheusMeterRegistry(
      PrometheusConfig.DEFAULT);

  public static long applicationStartTime = 0;

  public static void init() {
    Counter.builder("application.start.time").description("Application start time").register(GLOBAL_METER_REGISTRY).increment(applicationStartTime);
    doScrape(new JvmInfo());
  }

  private static void doScrape(Monitor monitor) {
    monitor.scrape(GLOBAL_METER_REGISTRY);
  }

}
