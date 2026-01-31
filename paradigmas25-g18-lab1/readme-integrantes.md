# LABORATORIO 1 - PARADIGMAS DE PROGRAMACIÓN

## Archivos principales:
- *Dibujo.hs*: Definimos nuestro lenguaje a partir de constructores. Aquí creamos la función foldDib que posee un fuerte uso en todo el laboratorio.
- *Pred.hs*: Implementación de funciones utilizando alto orden (sin pattern-matching)
- *Interp.hs*: Interpretación gráfica de nuestro lenguaje donde utilizamos la librería Gloss.
- *Main.hs*: Funciones que utilizan todo lo construido para mostrar por pantalla una grilla e imagenes elaboradas através de nuestro lenguaje. Por defecto utilizamos el ejemplo Escher, donde para cambiar sus figuras debemos modificar en
```
ej ancho alto = Conf {
                basic = E.interpBas
              , fig = E.escher 3 E.Fish
              , width = ancho
              , height = alto
              , r = id
              }
```
la línea fig, donde tenemos las siguientes opciones: `Fish | FishHD | Triangulo | Vacio`
En caso de utilizar otro ejemplo, se debe modificar la importación `import qualified Basica.Escher as E`
con el archivo en Basica correspondiente y luego utiliazr los métodos que el ejemplo nos proveerá.

## Mejora - Punto estrella:
En la rama `feature-rotarN` se agregó la siguiente funcionalidad: rotar α grados un Dibujo. Para esto se agregó un nuevo constructor `RotarN` que se extiende en la posterior utilización de `foldDilb` y `mapDib`, como también su interpretación en Interp.hs. En `Basica/Extra.hs` se encuentra un ejemplo de uso.



