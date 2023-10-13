package xyz.chaofan.solon;


import io.micrometer.core.instrument.MeterRegistry;

/**
 * 集成监控的基类
 */
public interface Monitor {
  void scrape(MeterRegistry meterRegistry);
}
