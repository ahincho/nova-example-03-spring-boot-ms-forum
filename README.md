# ms-forum

Segundo microservicio de ejemplo en Spring Boot sobre Nova Platform, con el mismo patrón que
`ms-course`: hereda `nova-spring-boot-parent`, usa el starter de observabilidad e instrumenta sus
endpoints con anotaciones.

Existe para que haya dos servicios distintos reportando al mismo stack de observabilidad: con uno
solo no se ve si los tableros distinguen servicios, ni cómo se reparte el tráfico entre ellos.

## Qué muestra

- `@Traced` y `@Metered` sobre los endpoints de `ForumController`, combinados en el mismo método
  cuando hacen falta los dos.
- Un error provocado: `GET /api/forum/topics/999` lanza una excepción, así la tasa de errores de
  los Four Golden Signals tiene un caso real que contar.
- Toda la configuración propia son dos valores: el puerto (`8082`) y
  `nova.observability.otlp.endpoint`.

## Cómo correrlo

```bash
mvn spring-boot:run
```

Los artefactos `pe.edu.nova.java` se leen de GitHub Packages, así que Maven necesita
credenciales para `maven.pkg.github.com`. Para ver las trazas, `nova-shared-03-infrastructure`
levanta el collector en `localhost:4318`.

Los temas y respuestas del foro son datos de muestra escritos en el controlador.

## License

Eclipse Public License 2.0 — see [LICENSE](LICENSE).

Copyright © 2026 Angel Hincho.
