# 🚀 Proyecto: SILLENT HILLS

**Miembros del Equipo:**
* Manuel Pérez Rodríguez
* Yeray Durán Chaves

---

## 📖 Nuestra Historia

**Temática del Juego:** Silent Hill

**Premisa:**
> La última noche antes de llegar al pueblo, recibiste un mensaje sin remitente: "Vuelve". Pensaste que era una broma de mal gusto, pero algo dentro de ti te obligó a conducir hasta las afueras de Silent Hill.  
> La carretera estaba vacía. Solo niebla, asfalto mojado y el sonido lejano de una sirena imposible. Entonces viste una silueta cruzando la calzada. Giraste el volante, el coche derrapó... y todo se volvió negro.  
> Cuando despiertas, ya no estás del todo en el mundo real. Silent Hill ha abierto sus puertas para ti. Las calles del pueblo cambian a cada paso, como si alguien hubiese mezclado tus recuerdos con pesadillas ajenas.  
> Un tramo de carretera recuerda a una vieja huida entre crimen y culpa. Un hospital oxidado esconde informes clínicos, cadáveres inmóviles y puertas cerradas con combinaciones imposibles. Una mansión invadida por retratos y susurros parece arrancada de una pesadilla infantil. Más abajo, un refugio enterrado bajo la ciudad mezcla chatarra militar, tecnología rota, símbolos antiguos y terminales que todavía brillan en la oscuridad.  
> Cada lugar parece pertenecer a un mundo distinto, pero todos tienen algo en común: tú.  
> No has venido a Silent Hill por casualidad. El pueblo te ha llamado porque sabe lo que hiciste. Aquí los monstruos no siempre tienen colmillos; a veces tienen recuerdos, nombres y voces que conoces demasiado bien.  
> Si quieres escapar, tendrás que reunir fragmentos de verdad, leer lo que otros dejaron atrás y construir el artefacto que abre la Puerta del Juicio.  
> Pero recuerda: en Silent Hill, algunas puertas no se abren con llaves... se abren con culpa.

**Objetivo:**
Explorar las pistas, sobrevivir a las manifestaciones del pueblo y encontrar una forma de abrir la Puerta del Juicio para escapar.

---

## ⚙️ Estado del Proyecto (Fase 1: Motor Básico)

Esta primera versión del proyecto (Misión 1 / UD1-UD3) implementa el "núcleo" del motor de juego usando **programación procedural** (métodos estáticos) y **arrays**.

**Funcionalidad del Núcleo:**
* Bucle de juego principal (`while`).
* Mapa de habitaciones (Array `habitaciones[]`).
* Gestión de inventario (Array `inventario[]`).
* Gestión de objetos por sala (Matriz `objetosMapa[][]`).
* **Comandos implementados:** `ir derecha`, `ir izquierda`, `mirar`, `inventario`, `coger`, `ayuda` y `salir`.

**Tecnologías (Fase 1):**
* Java (JDK)
* Programación Procedural
* Arrays
* Git

---

## ⚙️ Estado del Proyecto (Fase 2: POO)

Esta fase refactoriza el motor básico (Fase 1) a Programación Orientada a Objetos.

El objetivo es convertir el diseño procedural en un diseño con clases claras (Habitacion, Jugador, Objeto), usar colecciones en lugar de arrays y mejorar la mantenibilidad y extensibilidad del juego.

**Funcionalidad de la Fase:**

* **Arquitectura OOP con clases principales**: (`Objeto`, `Entidad`, `Habitacion`, ...)
* **Bucle de juego principal** implementado en una clase Juego (main) que usa los objetos anteriores.
* **Inventario:** En la clase `Jugador.java`
* **Conexiones entre habitaciones**: Con los comandos `ir izquierda` / `derecha`
* **Mejora de la separación de responsabilidades y preparación para añadir**: persistencia (`guardar/cargar`), items con propiedades, ...

**Tecnologías (Fase 2):**

* Java (JDK)
* Programación Orientada a Objetos
* Arrays
* Interfaces
* Excepciones
* Git

---

## ⚙️ Estado del Proyecto (Fase 3: Colecciones, JSON y Persistencia)

Esta fase desacopla la historia del motor del juego, eliminando los datos hardcodeados en el código fuente y convirtiendo el motor en una estructura flexible capaz de cargar cualquier aventura desde archivos externos.

El objetivo es sustituir los arrays y referencias fijas por estructuras dinámicas del Java Collections Framework, incorporar persistencia con Java NIO.2 y externalizar toda la configuración del mundo usando archivos `.properties` y formato JSON.

**Funcionalidad de la Fase:**

* **Refactorización del modelo de datos**: el `Jugador` pasa a guardar la habitación actual mediante un ID de tipo `String` en lugar de un índice numérico, y el inventario se gestiona mediante `ArrayList`.
* **Conexiones entre habitaciones mediante identificadores**: la clase `Habitacion` sustituye las referencias directas por un `Map` donde cada dirección apunta al ID textual de la sala destino.
* **Serialización y deserialización polimórfica con Gson**: creación de un `ObjetoAdapter` para poder guardar y reconstruir correctamente objetos abstractos y sus subclases (`Llave`, `Mueble`, `Contenedor`, etc.).
* **Externalización del mundo del juego a JSON**: creación de una clase contenedora `AventuraConfig` y de una herramienta `Migrador` para exportar las habitaciones y objetos existentes a un archivo `aventura.json`.
* **Carga de configuración profesional mediante properties**: creación de un archivo `config.properties` para definir rutas base del proyecto y nombre del archivo principal de aventura.
* **Uso de Java NIO.2 para lectura de archivos**: implementación de la clase `CargadorAventura`, encargada de leer la configuración con `Properties`, `Path` y `Files.newBufferedReader`, y cargar el mundo base desde JSON.
* **Nuevo motor basado en datos externos**: eliminación de las instancias hardcodeadas de `Habitacion` en el arranque del juego y carga del mapa global desde el archivo `aventura.json`.
* **Actualización del comando `ir`**: el movimiento del jugador pasa a depender del ID de la sala actual y de las salidas definidas en el `Map` de cada habitación, controlando además direcciones escritas en mayúsculas o minúsculas.

**Tecnologías (Fase 3):**

* Java (JDK)
* Programación Orientada a Objetos
* Colecciones (List, Map, Set, ArrayList, HashMap)
* JSON
* Gson
* Serialización y Deserialización
* Java NIO.2
* Properties
* Paths
* Persistencia de datos
* Git
