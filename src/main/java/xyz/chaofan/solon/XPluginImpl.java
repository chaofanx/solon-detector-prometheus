package xyz.chaofan.solon;

import io.micrometer.core.instrument.MeterRegistry;
import org.noear.solon.Solon;
import org.noear.solon.core.AppContext;
import org.noear.solon.core.BeanWrap;
import org.noear.solon.core.Plugin;
import org.noear.solon.core.event.AppLoadEndEvent;
import org.noear.solon.core.util.LogUtil;

public class XPluginImpl implements Plugin {

  @Override
  public void start(AppContext context) {
    if (!Solon.app().enableHttp()) {
      LogUtil.global().error("solon-detector-prometheus plugin start failed, because http server is not enabled");
      return;
    }
    LogUtil.global().info("solon-detector-prometheus plugin start");
    BeanWrap registryWrap = Solon.context().wrap(Constants.METER_REGISTRY_BEAN_NAME, SolonMeterRegistry.GLOBAL_METER_REGISTRY);
    context.putWrap(Constants.METER_REGISTRY_BEAN_NAME, registryWrap);
    context.putWrap(MeterRegistry.class, registryWrap);
    String path = context.cfg().getOrDefault(Constants.PATH_CONFIG, MetricHandler.DEFAULT_PATH).toString();
    LogUtil.global().info("application prometheus endpoint: " + path);
    Solon.app().get(path, MetricHandler.getInstance());
    Solon.app().onEvent(AppLoadEndEvent.class, event -> {
      SolonMeterRegistry.applicationStartTime = System.currentTimeMillis();
      SolonMeterRegistry.init();
    });
  }
}
