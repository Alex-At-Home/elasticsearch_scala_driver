## Running the shell (example)

```
#bash> java -jar elasticsearch_shell.jar
@ latest.`/`().read()
@ implicit val driver = (new ElasticsearchDriver()).withNewHostPorts(List("localhost:9200")).withBasicAuth("user", "password").start()
@ res0.exec()
@ res2.value.get
res3: util.Try[ElasticsearchInfo] = Success(
  ElasticsearchInfo(
    "host",
    "cluster_name",
    VersionInfo("2.3.4", "e455fd0c13dceca8dbbdbb1665d068ae55dabe3f", "2016-06-30T11:24:31Z", false, "5.5.0"),
    "You Know, for Search"
  )
)
```

## Building the shell

In sbt simply run:

```
elasticsearch_scala_shell/assembly
```

This will generate `<PROJECT_HOME>/elasticsearch_scala_shell/target/scala-2.11/elasticsearch_shell.jar`
