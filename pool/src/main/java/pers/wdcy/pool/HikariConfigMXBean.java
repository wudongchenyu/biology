/*
 * Copyright (C) 2013 Brett Wooldridge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pers.wdcy.pool;

/**
 * The javax.management MBean for a Hikari pool configuration.
 *
 * @author Brett Wooldridge
 */
public interface HikariConfigMXBean
{
   /**
    * Get the maximum number of milliseconds that a client will wait for a connection from the pool. If this
    * time is exceeded without a connection becoming available, a SQLException will be thrown from
    * {@link javax.sql.DataSource#getConnection()}.
    *
    * @return the connection timeout in milliseconds
    */
   long getConnectionTimeout();

   /**
    * Set the maximum number of milliseconds that a client will wait for a connection from the pool. If this
    * time is exceeded without a connection becoming available, a SQLException will be thrown from
    * {@link javax.sql.DataSource#getConnection()}.
    *
    * @param connectionTimeoutMs the connection timeout in milliseconds
    */
   void setConnectionTimeout(long connectionTimeoutMs);

   /**
    * This property controls the maximum lifetime of a connection in the pool. When a connection reaches this
    * timeout, even if recently used, it will be retired from the pool. An in-use connection will never be
    * retired, only when it is idle will it be removed.
    *
    * @return the maximum connection lifetime in milliseconds
    */
   long getMaxLifetime();

   /**
    * This property controls the maximum lifetime of a connection in the pool. When a connection reaches this
    * timeout, even if recently used, it will be retired from the pool. An in-use connection will never be
    * retired, only when it is idle will it be removed.
    *
    * @param maxLifetimeMs the maximum connection lifetime in milliseconds
    */
   void setMaxLifetime(long maxLifetimeMs);

   /**
    * The property controls the minimum number of idle connections that HikariCP tries to maintain in the pool,
    * including both idle and in-use connections. If the idle connections dip below this value, HikariCP will
    * make a best effort to restore them quickly and efficiently.
    *
    * @return the minimum number of connections in the pool
    */
   int getMinimumIdle();

   /**
    * The property controls the minimum number of idle connections that HikariCP tries to maintain in the pool,
    * including both idle and in-use connections. If the idle connections dip below this value, HikariCP will
    * make a best effort to restore them quickly and efficiently.
    *
    * @param minIdle the minimum number of idle connections in the pool to maintain
    */
   void setMinimumIdle(int minIdle);

   /**
    * The property controls the maximum number of connections that HikariCP will keep in the pool,
    * including both idle and in-use connections.
    *
    * @return the maximum number of connections in the pool
    */
   int getMaximumPoolSize();

   /**
    * The property controls the maximum size that the pool is allowed to reach, including both idle and in-use
    * connections. Basically this value will determine the maximum number of actual connections to the database
    * backend.
    * <p>
    * When the pool reaches this size, and no idle connections are available, calls to getConnection() will
    * block for up to connectionTimeout milliseconds before timing out.
    *
    * @param maxPoolSize the maximum number of connections in the pool
    */
   void setMaximumPoolSize(int maxPoolSize);

   /**
    * The name of the connection pool.
    *
    * @return the name of the connection pool
    */
   String getPoolName();

   /**
    * Get the default catalog name to be set on connections.
    *
    * @return the default catalog name
    */
   String getCatalog();

   /**
    * Set the default catalog name to be set on connections.
    * <p>
    * WARNING: THIS VALUE SHOULD ONLY BE CHANGED WHILE THE POOL IS SUSPENDED, AFTER CONNECTIONS HAVE BEEN EVICTED.
    *
    * @param catalog the catalog name, or null
    */
   void setCatalog(String catalog);
}
