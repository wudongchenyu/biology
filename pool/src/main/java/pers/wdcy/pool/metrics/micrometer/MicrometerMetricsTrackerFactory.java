package pers.wdcy.pool.metrics.micrometer;

import pers.wdcy.pool.metrics.IMetricsTracker;
import pers.wdcy.pool.metrics.MetricsTrackerFactory;
import pers.wdcy.pool.metrics.PoolStats;
import io.micrometer.core.instrument.MeterRegistry;

public class MicrometerMetricsTrackerFactory implements MetricsTrackerFactory
{

   private final MeterRegistry registry;

   public MicrometerMetricsTrackerFactory(MeterRegistry registry)
   {
      this.registry = registry;
   }

   @Override
   public IMetricsTracker create(String poolName, PoolStats poolStats)
   {
      return new MicrometerMetricsTracker(poolName, poolStats, registry);
   }
}
