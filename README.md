# Custom-Entity-Bukkit(for 1.17.1)

![Maven Central](https://img.shields.io/maven-central/v/io.github.xenon245/customentity)
![GitHub](https://img.shields.io/github/license/xenon245/custom-entity)

### Paper용 CustomEntity 라이브러리

---

* #### Features
  * CustomPayLoad 패킷
  * 라이브러리 로더
* #### Environment
  * JDK 16
  * Kotlin 1.5.20
  * Paper & Spigot 1.17, 1.17.1

---

#### Gradle

```kotlin 
repositories {
    mavenCentral()
}
```

```kotlin
dependencies { 
    implementation("io.github.xenon245:customentity-api:<version>")
}
```

### plugins.yml

```yaml
name: ...
version: ...
main: ...
libraries:
  - io.github.xenon245:customentity:<version>
```

---

### NOTE

* 라이센스는 GPL-3.0이며 변경 혹은 삭제를 금합니다.
* `./gradlew setupWorkspace` 명령을 통해 작업환경을 구축 할 수 있습니다.

---