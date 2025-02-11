# Desert Planet - Un juego desarrollado con LibGDX

## Descripción
**Desert Planet** es un juego desarrollado en Java utilizando el framework **LibGDX**. En este juego, el jugador explora un planeta desértico mientras enfrenta desafíos y supera obstáculos en su camino.

## Características
- Jugabilidad en 2D con gráficos retro.
- Exploración y combate en un entorno desértico.
- Controles simples e intuitivos.
- Sistema de puntuación y progreso.

## Requisitos del sistema
Para ejecutar el juego, necesitas:
- **Java 8 o superior** instalado.
- **LibGDX** y sus dependencias.
- **Gradle**.

## Instalación y ejecución
### 1. Clonar el repositorio
```bash
git clone https://github.com/tu_usuario/desert_planet.git
cd desert_planet
```

### 2. Compilar y ejecutar con Gradle
Si tienes Gradle instalado, puedes ejecutar el juego con:
```bash
./gradlew desktop:run  # En Linux/macOS
# o
gradlew.bat desktop:run  # En Windows
```

### 3. Ejecutar manualmente con Java
Si prefieres compilarlo manualmente:
```bash
javac -cp libgdx.jar -d bin src/com/tu_usuario/desertplanet/*.java
java -cp bin:libgdx.jar com.tu_usuario.desertplanet.Main
```

### 4. Ejecutar archivo DesertPlanetv2.jar
Se puede probar el juego haciendo doble click en el archivo .jar.

## Controles
- **Flechas**: Mover al personaje.
- **Espacio**: Disparar.
- **ESC**: Salir del juego.

## Licencia
Este proyecto está bajo la licencia **MIT**, por lo que puedes modificarlo y distribuirlo libremente respetando los términos de la licencia.

