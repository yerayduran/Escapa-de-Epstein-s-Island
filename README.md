# 🚀 Proyecto: NIGHT CLASS

**Miembros del Equipo:**
* Manuel Pérez Rodríguez
* Yeray Durán Chaves

---

## 📖 Nuestra Historia (HACENDADO OUT)

**Temática del Juego:** Escape Room en el Mercadona.

**Premisa:**
> Estabas cagando en el baño de un Mercadona random, tenías mucho sueño porque habías estado toda la noche jugando al call of duty, de repente notas cómo tus parpados empiezan a cerrarse. Al final te quedas dormido por viciar toda\n la noche y resulta que, cuando te despiertas, en vez de estar sentado en aquel váter estas en una especie de sillón en una sala totalmente a oscuras. Cuando te levantas, se te enciende la sala en la que estás. Ya no estás en aquel baño cutre del Mercadona, ahora estabas en un lugar totalmente desconocido.

**Objetivo:**
Explorar las pistas y encontrar una forma de salir del sitio misterioso.

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
* Colecciones (ArrayList, HashMap)
* Arrays
* Interfaces
* Records
* Excepciones
* Git

## 🔜 Próximas Fases

* **Fase 3 (Colecciones/Ficheros):** Cambiar los arrays del inventario por `ArrayLists` y añadir un sistema de **Guardar/Cargar Partida**.
* **Fase 4 (GUI/BBDD):** Crear una interfaz gráfica (GUI) y conectar a una base de datos para guardar progresos o logros.
