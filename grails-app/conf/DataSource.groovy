dataSource {
  pooled = true
  //loggingSql = true
  driverClassName = "org.hsqldb.jdbcDriver"
  username = "sa"
  password = ""
}
hibernate {
  cache.use_second_level_cache = true
  cache.use_query_cache = true
  cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
  development {
    dataSource {
//      dbCreate = "create-drop" // one of 'create', 'create-drop','update'
//      url = "jdbc:hsqldb:mem:devDB"
      dbCreate = "update"
//      url = "jdbc:hsqldb:file:devDb;shutdown=true"

      driverClassName = "org.postgresql.Driver"
      url = "jdbc:postgresql://localhost:5432/trl"
      username = "postgres"
      password = "postgres"
    }
  }
  test {
    dataSource {
      dbCreate = "create-drop" // one of 'create', 'create-drop','update'
      url = "jdbc:hsqldb:mem:testDB"
      //dbCreate = "update"
      //url = "jdbc:hsqldb:file:testDb;shutdown=true"
    }
  }
  production {
    dataSource {
      dbCreate = "update"
      url = "jdbc:hsqldb:file:prodDb;shutdown=true"
    }
  }
}
