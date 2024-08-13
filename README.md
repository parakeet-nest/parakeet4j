# 🦜🪺☕️ Parakeet4J

Parakeet4J is the simplest Java library to create **GenAI apps** with **[Ollama](https://ollama.com/)**.

> A GenAI app is an application that uses generative AI technology. Generative AI can create new text, images, or other content based on what it's been trained on. So a GenAI app could help you write a poem, design a logo, or even compose a song! These are still under development, but they have the potential to be creative tools for many purposes. - [Gemini](https://gemini.google.com)

> ✋ Parakeet4J is only for creating GenAI apps generating **text** (not image, music,...).

## Hello World!

```java
public class Hello
{
    public static void main( String[] args )
    {
        Options options = new Options()
                .setTemperature(0.0)
                .setRepeatLastN(2);

        var systemContent = "You are a useful AI agent, expert with programming";
        var userContent = "Generate a Hello World program in GoLang.";

        List<Message> messages = List.of(
                new Message("system", systemContent),
                new Message("user", userContent)
        );

        Query queryChat = new Query("deepseek-coder", options).setMessages(messages);

        var resultAnswer = ChatStream("http://0.0.0.0:11434", queryChat,
                chunk -> {
                    System.out.print(chunk.getMessage().getContent());
                    return null;
                });

        if(resultAnswer.exception().isEmpty()) {
            System.out.println("🙂: " + 
                    resultAnswer.getAnswer().getMessage().getContent()
            );
        } else {
            System.out.println("😡: " + 
                    resultAnswer.exception().toString()
            );
        }
    }
}
```

## Install

This library project use the [GitHub Maven Registry](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry). So, to use it in your projects, use the following steps.

### First

Create (or update) a file: `~/.m2/settings.xml` with the following content

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <activeProfiles>
    <activeProfile>github</activeProfile>
  </activeProfiles>

  <profiles>
    <profile>
      <id>github</id>
      <repositories>
        <repository>
          <id>central</id>
          <url>https://repo1.maven.org/maven2</url>
        </repository>
        <repository>
          <id>github</id>
          <url>https://maven.pkg.github.com/parakeet-nest/parakeet4j</url>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
      </repositories>
    </profile>
  </profiles>

  <servers>
    <server>
      <id>github</id>
      <username>your GitHub handle</username>
      <password>your GitHub token</password>
    </server>
  </servers>
</settings>
```

### Then

Add this section to your `pom.xml` file:

```xml
<dependencies>
    <dependency>
        <groupId>org.parakeetnest.parakeet4j</groupId>
        <artifactId>parakeet4j</artifactId>
        <version>0.0.5</version>
    </dependency>
</dependencies>
```
