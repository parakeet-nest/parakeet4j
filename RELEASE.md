> - change the version number into `pom.xml`
> - change the version number into `README.md`
> - commit
> - create a tag on GitHub
> - pull

### What's new (v0.0.7)

#### Protected endpoint

If your Ollama endpoint is protected with a header token, you can specify the token like this:

```java
query.setTokenHeaderName("X-TOKEN").setTokenHeaderValue("john doe");
```
