> - change the version number into `pom.xml`
> - change the version number into `README.md`
> - commit
> - create a tag on GitHub
> - pull

### What's new (v0.0.6)

#### Completion 

Verbose mode:
```java
Options options = new Options()
        .setTemperature(0.0)
        .setRepeatLastN(2)
        .setVerbose(true);
```

#### Memory Persistent Vector Store

Add additional data to a vector record:
```java
vectorRecord.setText("THIS IS THE CHUNK NUM "+Integer.toString(index.get()));
vectorRecord.setReference("THIS IS THE CHUNK DOC REF");
vectorRecord.setMetaData("THIS IS THE CHUNK META DATA");
```

