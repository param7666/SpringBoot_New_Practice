# 🌱 SpringBoot New Practice

> One repo. Every Spring topic. From the ground up.

I'm working through Spring — properly, one concept at a time — starting with Core Spring, moving into Spring Boot, and eventually building out full Microservices examples. No shortcuts, no skipped chapters. Each folder here is a small, focused project built to nail down exactly one idea before moving to the next.

---

## 📖 About this repo

This isn't a single application — it's a **collection of independent mini-projects**, each one built to demonstrate exactly one Spring concept in isolation. Instead of learning Dependency Injection, AOP, and REST controllers all tangled together in one messy app, every idea gets its own clean folder, its own code, and (eventually) its own notes.

The goal: by the time this repo is "done," it should double as a personal reference guide — a place I can come back to and instantly find a working example of any Spring concept, from the most basic IoC container setup to a fully working microservices architecture.

---

## 🧠 Why I'm doing it this way

- **Isolation over integration** — mixing 5 concepts in one project makes it hard to tell which part is doing what. One topic per folder keeps things crystal clear.
- **Progressive difficulty** — the repo is ordered so each new project builds on the understanding from the last.
- **Practice over theory** — every folder is runnable code, not just notes. I learn by breaking things and fixing them.
- **Long-term reference** — future me (or anyone else browsing this) should be able to open any folder and understand the concept in under 5 minutes.

---

## 🗂️ Current Projects

| # | Project Folder | Topic | Concept Covered |
|---|-----------------|-------|------------------|
| 01 | `IOCProj01DI` | Setter Injection (XML) | Basic IoC container, bean wiring via `<property>` tags |
| 02 | `IOCProj02DI` | Setter Injection (XML) — Variant 2 | Reinforcing setter DI with a different bean structure |
| 03 | `IOCProj03ConstructorInjection` | Constructor Injection | Wiring dependencies via constructor args instead of setters |
| 04 | `IOCProj04XMLAndAnotationDI` | Autowired + XML Hybrid | Mixing `@Autowired` annotation-based DI with XML bean definitions |

Each folder follows this internal structure:

```
IOCProjXX<TopicName>/
 └── src/
     └── com/
         └── nt/
             ├── *.java          → Bean classes, main runner class
             └── *.xml           → Spring bean configuration (where applicable)
```

---

## 🛣️ Full Roadmap

### 🔹 Core Spring
| Status | Topic |
|--------|-------|
| ✅ | Setter Injection (XML) |
| ✅ | Constructor Injection |
| ✅ | Autowiring + XML hybrid config |
| ⬜ | Pure Java Configuration (`@Configuration`, `@Bean`) |
| ⬜ | Component Scanning (`@Component`, `@Service`, `@Repository`) |
| ⬜ | Bean Scopes — Singleton vs Prototype |
| ⬜ | Bean Lifecycle — `@PostConstruct`, `@PreDestroy`, `InitializingBean`, `DisposableBean` |
| ⬜ | Spring AOP — Aspects, Advice types, Pointcuts |
| ⬜ | Spring Expression Language (SpEL) |
| ⬜ | ApplicationContext vs BeanFactory |

### 🔹 Spring Boot
| Status | Topic |
|--------|-------|
| ⬜ | Spring Boot Starter Basics & Project Structure |
| ⬜ | Auto-Configuration Deep Dive |
| ⬜ | Building REST APIs (`@RestController`, `@RequestMapping`) |
| ⬜ | Spring Data JPA + Repository Pattern |
| ⬜ | DTOs & Entity Mapping |
| ⬜ |Spring MVC/TheamLeaf |
| ⬜ | Global Exception Handling (`@ControllerAdvice`, `@ExceptionHandler`) |
| ⬜ | Request Validation (`@Valid`, custom validators) |
| ⬜ | Spring Boot Profiles (dev/test/prod configs) |
| ⬜ | Spring Security Basics — Authentication & Authorization |
| ⬜ | JWT-based Security |
| ⬜ | Logging & Actuator |

### 🔹 Microservices
| Status | Topic |
|--------|-------|
| ⬜ | Microservices Architecture Fundamentals |
| ⬜ | Spring Cloud Config Server |
| ⬜ | Service Discovery with Eureka |
| ⬜ | API Gateway (Spring Cloud Gateway) |
| ⬜ | Inter-service Communication — REST Template / Feign / WebClient |
| ⬜ | Circuit Breaker Pattern (Resilience4j) |
| ⬜ | Centralized Logging & Distributed Tracing |
| ⬜ | Event-driven Communication (Kafka / RabbitMQ) |
| ⬜ | Containerizing Services with Docker |
| ⬜ | Orchestration Basics (Docker Compose / intro to Kubernetes) |

---

## 🛠️ Tech Stack

| Category | Tools |
|----------|-------|
| Language | Java |
| Core Framework | Spring Framework, Spring Boot |
| Build Tool | Maven |
| Config Style | XML + Annotation-based (mixed, by design) |
| Future additions | Spring Data JPA, Spring Security, Spring Cloud, Docker, Kafka |

---

## ▶️ How to Run a Project

1. Open the specific `IOCProjXX...` folder in your IDE (IntelliJ IDEA / Eclipse / STS).
2. Locate the main runner class inside `src/com/nt`.
3. If the project uses XML config, make sure the `.xml` file path in the main class matches its actual location.
4. Run the main class directly — each project is self-contained and has no dependency on other folders in this repo.

---

## 📝 Learning Notes

As I go through each topic, I try to capture the "why," not just the "how":

- **Setter vs Constructor Injection** — setter injection is optional/mutable dependencies, constructor injection is for mandatory/immutable dependencies. Spring recommends constructor injection for required fields.
- **XML vs Annotation config** — XML gives explicit, centralized control (useful for legacy systems); annotations are faster to write and closer to the code they configure.
- *(More notes will be added here as new topics are completed — things that confused me, "aha" moments, and gotchas worth remembering.)*

---

## 📌 Conventions Used in This Repo

- **Folder naming:** `IOCProj<Number><ShortTopicName>/src/com/nt`
- **Commit messages:** describe exactly what concept/change was added (no vague "update" commits)
- **One concept per project:** no folder mixes more than one core idea
- **Package base:** `com.nt` used consistently across all projects for simplicity

---


## 👤 Author

**Parmeshwar Gurlewad**
GitHub: [@param7666](https://github.com/param7666)

Learning Java Full Stack Development, one topic — and one commit — at a time.

---

*⭐ If you're following a similar learning path, feel free to fork this repo and adapt the structure for your own practice.*
