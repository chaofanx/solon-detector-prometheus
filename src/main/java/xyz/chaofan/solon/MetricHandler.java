package xyz.chaofan.solon;

import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;

public class MetricHandler implements Handler {

  public static final String DEFAULT_PATH = "/metrics";

  private static final MetricHandler instance = new MetricHandler();

  public static MetricHandler getInstance() {
    return instance;
  }

  @Override
  public void handle(Context ctx) {

    ctx.output(SolonMeterRegistry.GLOBAL_METER_REGISTRY.scrape());
  }
}
