# Computación Distribuida con Apache Spark

## Configuración del entorno y ejecución

**Requisitos previos**

Asegurarse de tener los siguientes componentes instalados: Java 8 o superior, Apache Spark, las dependencias necesarias y configurar la ruta en el archivo `Makefile` donde se especifica donde se encuentra localmente SPARK_FOLDER. Para obtener intente lanzar alguno de los siguientes comandos: 

`sudo find / -name "SPARK_FOLDER" 2>/dev/null`

`find / -type d -name "spark-3*" 2>/dev/null`

Y luego reemplazar en la línea `SPARK_FOLDER=`. Como ejemplo, debería tener: `SPARK_FOLDER=/home/tomi/spark`.

**Modos de ejecución:** 
Primero se debe correr el comando `make`. Luego:

1) Lectura simple de artículos:
- Comando: make run

    Compila el proyecto y ejecuta la clase principal FeedReaderMain sin argumentos. Esto activa el modo básico del programa: descarga e imprime todos los artículos obtenidos desde los feeds configurados, sin aplicar procesamiento adicional.
Se muestra por consola una lista con los artículos disponibles, incluyendo sus títulos, descripciones y URLs.
2) Cómputo de entidades nombradas:
- Comando: make run-ne

    Compila y ejecuta FeedReaderMain con el argumento -ne, lo que activa el cálculo de entidades nombradas (Named Entities). Durante la ejecución, se le pedirá al usuario que elija una heurística para el análisis. Luego, el sistema computará las entidades nombradas encontradas en los artículos. Se imprimirá una lista de entidades nombradas con su respectiva cantidad de apariciones en los feeds, con el formato: `<entidad nombrada>: <conteo>`.

## Decisiones de diseño

Realizamos las tareas propuestas basándonos en el trabajo realizado en el laboratorio anterior, donde desarrollamos un lector automático de feeds. En esta ocasión, implementamos un diseño capaz de soportar el procesamiento de grandes volúmenes de datos, tanto en la etapa de descarga como en el cálculo de entidades nombradas.

Para ello, utilizamos el framework Apache Spark, que nos permitió abordar la computación distribuida de manera eficiente. Tras explorar las herramientas que ofrece, decidimos levantar una sesión de Spark e instanciar un objeto de la clase SparkFeedFetcher, diseñada por nosotros, lo cual nos va a ayudar a procesar los feeds en paralelo.

Por otra parte, para imprimir por pantalla una lista con el formato `<entidad nombrada>: <conteo>`, fue necesario adaptar los contadores previamente utilizados. Los incorporamos a la lista de entidades nombradas calculadas, permitiendo así acceder directamente a sus valores al momento de mostrar los resultados.

Además, incorporamos mejoras al código existente, como el manejo de distintos tipos de contenido dentro de cada URL y el tratamiento de excepciones en la etapa de parseo, con el objetivo de hacer el sistema más robusto y tolerante a errores.Además, incorporamos mejoras al código existente, como el manejo de distintos tipos de contenido dentro de cada URL y el tratamiento de excepciones en la etapa de parseo, con el fin de hacer el sistema más robusto y tolerante a errores.

## Conceptos importantes
### 1. Describa el flujo de la aplicación ¿Qué pasos sigue la aplicación desde la lectura del archivo feeds.json hasta la obtención de las entidades nombradas? ¿Cómo se reparten las tareas entre los distintos componentes del programa?

1. **Lectura y parseo del archivo de suscripciones (subscription.json)**
    
    El proceso comienza con la lectura del archivo `subscrition.json`, el cual contiene una lista de subscripciones. Cada suscripción incluye una URL base y una lista de parámetros. A partir de esta información, se generan todas las combinaciones posibles de URLs, incorporando sus respectivos parámetros.

2. **Generación de URLs completas**
    
    Estas combinaciones se organizan en una lista que representa todas las direcciones URL que serán procesadas. Este paso permite tener una visión consolidada de todas las fuentes desde las cuales se obtendrán los artículos.

3. **Paralelización con Spark: descarga y parseo de feeds**
   
    Utilizando Apache Spark, se paraleliza la lista de URLs. Cada tarea en Spark se encarga de realizar la solicitud a una URL específica, recuperar su contenido (en formato RSS o Atom) y parsearlo para extraer los artículos.

4. **Unificación de artículos**

    Los artículos recuperados de todas las fuentes se combinan en una única lista global, que representa el corpus completo a analizar.

5. **Selección de la heurística de extracción**

    En esta etapa se selecciona la heurística que se utilizará para identificar entidades nombradas dentro del contenido de los artículos. Esta heurística puede estar basada en diccionarios, reglas, expresiones regulares u otras técnicas.

6. **Extracción de entidades nombradas (Spark)**

    Nuevamente, se utiliza Spark para paralelizar el análisis de los artículos. Cada tarea aplica la heurística a un artículo y extrae las entidades nombradas encontradas, junto con su frecuencia de aparición en dicho texto.

7. **Transformación a pares clave-valor (Spark)**

    Las entidades extraídas se transforman en pares del tipo (nombreEntidad, frecuencia), donde la clave es el nombre de la entidad y el valor indica cuántas veces aparece en el artículo correspondiente.

8. **Reducción por clave y suma de frecuencias (Spark)**

    A través de la operación reduceByKey, se agrupan todas las entidades que comparten el mismo nombre y se suman sus frecuencias, obteniendo así la cantidad total de apariciones de cada entidad en todo el conjunto de artículos.

9. **Recolección e impresión de resultados**

    Finalmente, los resultados se recolectan en una lista local que contiene los pares (entidad, frecuenciaTotal). Esta lista se imprime por pantalla, permitiendo visualizar las entidades nombradas más relevantes en el conjunto de datos analizado.


### 2.¿Por qué se decide usar Apache Spark para este proyecto? ¿Qué necesidad concreta del problema resuelve?

Se decide utilizar Apache Spark ya que este nos ayuda a reducir significativamente los tiempos de cómputo al dejarnos distribuir, mediante el uso de hilos, tanto las tareas de procesamiento de URL y feeds, como la de computar y extraer las heuristicas de cada articulo.
    
### 3.Liste las principales ventajas y desventajas que encontró al utilizar Spark.

En primer lugar, en relación a las ventajas, nos permitió adaptarlo facilmente a nuestro código anterior haciendo solo unas pequeñas modificaciones. Además, logramos modularizarlo de gran manera, adaptando nuestras anteriores funcionalidades a un enfoque distribuido y más eficiente. Una vez aprendida la secuencia general (sobretodo los conceptos de Map y Reduce) el desarrollo se volvió más sencillo.

Sin embargo, si consideramos como desventaja una cierta complejidad inicial, especialmente en base al tipo de estructura que se usa (principalmente el tipo RDD). Además, tuvimos dificulades con el tema de la serialización de objetos. Esto fue corrigido implementando este concepto en las clases generadas por nosotros.

### 4.¿Cómo se aplica el concepto de inversión de control en este laboratorio? Explique cómo y dónde se delega el control del flujo de ejecución. ¿Qué componentes deja de controlar el desarrollador directamente?

En este laboratorio, la inversión de control se manifiesta principalmente en las etapas en que se delega trabajo a los workers, tanto al momento de realizar solicitudes HTTP y parsear artículos, como en la fase posterior de análisis de entidades nombradas mediante una heurística. 
En ambos casos, el flujo de ejecución deja de estar controlado directamente por el desarrollador, ya que Spark se encarga de distribuir las tareas, ejecutarlas y coordinar la recolección de resultados. Esta dinámica obliga a seguir una estructura de procesamiento definida por Spark, donde se abren y cierran etapas de ejecución, es decir, el control del flujo no está en el código imperativo del usuario, sino en el motor de ejecución distribuida que maneja Spark internamente.
Los componentes que el desarrollador deja de controlar directamente son, principalmente, cómo y cuándo se ejecutan las tareas que se reparten entre los workers. Por ejemplo, no se decide en qué orden exacto se hacen las solicitudes o el análisis, ni en qué máquina se ejecuta cada parte. Todo ese control lo toma Spark, y uno como desarrollador solo define las operaciones que se quieren hacer, pero no el detalle de su ejecución.

### 5. **¿Considera que Spark requiere que el codigo original tenga una integración tight vs loose coupling?**

Spark permite modularizar distintas partes del procesamiento, lo que contribuye tanto a una mejor organización del código como a un uso más eficiente de los recursos. Al mismo tiempo, ofrece una alta flexibilidad en la forma de definir y encadenar operaciones, sin requerir un flujo rígido o fuertemente estructurado. Estas características se corresponden con un enfoque de loose coupling, donde los componentes del sistema pueden desarrollarse e interactuar de forma más desacoplada.

En este modelo, cada etapa del procesamiento (como la obtención de datos, el parseo, o el análisis de entidades) puede implementarse como una unidad relativamente independiente. Esto no solo facilita la reutilización de funciones o módulos, sino que también permite adaptar partes del sistema sin tener que modificar el resto. Además, Spark abstrae muchos aspectos del manejo de datos distribuidos, por lo que el desarrollador puede centrarse en la lógica de cada etapa sin acoplarse al detalle de la infraestructura subyacente. Todo esto refuerza la idea de que Spark favorece un diseño desacoplado y modular, más cercano al loose coupling.

### 6.¿El uso de Spark afectó la estructura de su código original? ¿Tuvieron que modificar significativamente clases, métodos o lógica de ejecución del laboratorio 2?

El uso de Spark no afectó como tal la estructura de nuestro código original, pero sí se realizaron modificaciones significativas. Estas ultimas fueron tanto de diseño como correcciones, y se detallan a continuación:

- **RssParser**: detectamos que en el nuevo archivo `config.js` se encuentran urls RSS con formatos distintos a los que se trabajaron en el laboratorio 2. Por esto, se dividió el parseo de RSS en dos métodos:  `parseRss` y  `parseFeed`. La diferencia de ello radica en el nombre del rootElement como también de algunas otras etiquetas. Por ejemplo, las etiquetas `<item>` ahora son `<entry>`.

- **GeneralParser**: a diferencia del laboratorio 2, se cambió el tipo que maneja esta clase, como su método `parse`, que implementan las clases heredadas de ella. Ya no se trabaja más con  `List<HashMap<String, Object>>`, si no que ahora se utiliza un tipo genérico `T`, el cual será definido por cada Parser en particular. Por ejemplo, `parserRss` toma `T=Feed`.

- **FeedReaderMain**: se implementó en base a la nueva clase llamada **SparkFeedFetcher**. Esta ultima presenta tres métodos que utilizan el método `parallelize` de Spark para distribuir el trabajo: 
    - parallelizeFeeds --> dada una lista de urls, hace un request HTTP por cada una de ellas, parsea y luego colecciona todos los articulos de cada `Feed`.
    - parallelizeComputedEntities --> dada una lista de artículos y una heurística, computa esta última a cada artículo. Finalmente, recolecta y devuelve una lista de NamedEntities.
    - parallelizeCountedEntities --> dada una lista de entidades nombradas, se encarga de juntar todas aquellas que son iguales sumando sus frecuencias.

    Finalmente, se utiliza la clase **SparkFeedFetcher** en el **FeedReaderMain**. En este último se crea la sesión Spark y se detiene al final, y cada método que utiliza esta sesión esta implementado en  **SparkFeedFetcher**.