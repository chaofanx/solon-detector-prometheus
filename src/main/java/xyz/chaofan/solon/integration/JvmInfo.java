package xyz.chaofan.solon.integration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import xyz.chaofan.solon.Monitor;

public class JvmInfo implements Monitor {

  @Override
  public void scrape(MeterRegistry meterRegistry) {
    RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    Gauge.builder("jvm.info", () -> 1)
        .tag("name", runtimeMXBean.getVmName())
        .tag("vendor", runtimeMXBean.getVmVendor())
        .tag("version", runtimeMXBean.getVmVersion())
        .tag("spec.name", runtimeMXBean.getSpecName())
        .tag("java.version", System.getProperty("java.version"))
        .description("Jvm Basic Info")
        .register(meterRegistry);

    ThreadMXBean threads = ManagementFactory.getThreadMXBean();
    Gauge.builder("jvm.thread.count", threads::getThreadCount)
        .description("Jvm Thread Count. (Including daemon and non-daemon threads)")
        .register(meterRegistry);
    Gauge.builder("jvm.thread.daemon.count", threads::getDaemonThreadCount)
        .description("Jvm Daemon Thread Count").register(meterRegistry);
    Gauge.builder("jvm.thread.peek.count", threads::getPeakThreadCount)
        .description("Jvm Peak Thread Count").register(meterRegistry);

    Counter.builder("jvm.start.time").description("Jvm Start Time").register(meterRegistry)
        .increment(runtimeMXBean.getStartTime());

    MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
    Gauge.builder("jvm.memory.heap.init", heapMemoryUsage::getInit)
        .description("Jvm Heap Memory Init").register(meterRegistry);
    Gauge.builder("jvm.memory.heap.used", heapMemoryUsage::getUsed)
        .description("Jvm Heap Memory Used").register(meterRegistry);
    Gauge.builder("jvm.memory.heap.committed", heapMemoryUsage::getCommitted)
        .description("Jvm Heap Memory Committed").register(meterRegistry);
    Gauge.builder("jvm.memory.heap.max", heapMemoryUsage::getMax).description("Jvm Heap Memory Max")
        .register(meterRegistry);

    MemoryUsage nonHeapMemoryUsage = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
    Gauge.builder("jvm.memory.nonheap.init", nonHeapMemoryUsage::getInit)
        .description("Jvm NonHeap Memory Init").register(meterRegistry);
    Gauge.builder("jvm.memory.nonheap.used", nonHeapMemoryUsage::getUsed)
        .description("Jvm NonHeap Memory Used").register(meterRegistry);
    Gauge.builder("jvm.memory.nonheap.committed", nonHeapMemoryUsage::getCommitted)
        .description("Jvm NonHeap Memory Committed").register(meterRegistry);
    Gauge.builder("jvm.memory.nonheap.max", nonHeapMemoryUsage::getMax)
        .description("Jvm NonHeap Memory Max").register(meterRegistry);

    ClassLoadingMXBean cl = ManagementFactory.getClassLoadingMXBean();
    Gauge.builder("jvm.classloading.total", cl::getTotalLoadedClassCount)
        .description("Jvm ClassLoading Total").register(meterRegistry);
    Gauge.builder("jvm.classloading.loaded", cl::getLoadedClassCount)
        .description("Jvm ClassLoading Loaded").register(meterRegistry);
    Gauge.builder("jvm.classloading.unloaded", cl::getUnloadedClassCount)
        .description("Jvm ClassLoading Unloaded").register(meterRegistry);
  }
}
