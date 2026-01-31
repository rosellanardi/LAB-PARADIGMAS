module Dibujo where

-- Definir el lenguaje via constructores de tipo

data Dibujo a = Basica a
              | Rotar (Dibujo a)
              | Rotar45 (Dibujo a)
              | Espejar (Dibujo a)
              | Apilar Float Float (Dibujo a) (Dibujo a)
              | Juntar Float Float (Dibujo a) (Dibujo a)
              | Encimar (Dibujo a) (Dibujo a)
                deriving(Eq, Show)

-- Composición n-veces de una función con sí misma.
comp :: (a -> a) -> Int -> a -> a
comp f 0 a =  a
comp f n a = f (comp f (n-1) a)


-- Rotaciones de múltiplos de 90.
r180 :: Dibujo a -> Dibujo a
r180 a = comp Rotar 2 a

r270 :: Dibujo a -> Dibujo a
r270 a = comp Rotar 3 a


-- Pone una figura sobre la otra, ambas ocupan el mismo espacio.
(.-.) :: Dibujo a -> Dibujo a -> Dibujo a
(.-.) a b = Apilar 1 1 a b


-- Pone una figura al lado de la otra, ambas ocupan el mismo espacio.
(///) :: Dibujo a -> Dibujo a -> Dibujo a
(///) a b = Juntar 1 1 a b


-- Superpone una figura con otra.
(^^^) :: Dibujo a -> Dibujo a -> Dibujo a
(^^^) a b = Encimar a b


-- Dadas cuatro dibujos las ubica en los cuatro cuadrantes.
cuarteto :: Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a
cuarteto a b c d = (.-.) ((///) a b) ((///) c d) 


-- Una dibujo repetido con las cuatro rotaciones, superpuestas.
encimar4 :: Dibujo a -> Dibujo a
encimar4 a = (^^^) (r270 a) ((^^^) (r180 a) ((^^^) a (Rotar a)))


-- Cuadrado con la misma figura rotada i * 90, para i ∈ {0, ..., 3}.
-- No confundir con encimar4!
ciclar :: Dibujo a -> Dibujo a
ciclar a = (.-.) ((///) a (Rotar a)) ((///) (r180 a) (r270 a))


-- Transfomar un valor de tipo a como una Basica.
pureDib :: a -> Dibujo a
pureDib a = Basica a


-- map para nuestro lenguaje.
mapDib :: (a -> b) -> Dibujo a -> Dibujo b
mapDib f (Basica x) = Basica (f x)
mapDib f (Rotar d) = Rotar (mapDib f d)
mapDib f (Rotar45 d) = Rotar45 (mapDib f d)
mapDib f (Espejar d) = Espejar (mapDib f d)
mapDib f (Apilar f1 f2 d1 d2) = Apilar f1 f2 (mapDib f d1) (mapDib f d2)
mapDib f (Juntar f1 f2 d1 d2) = Juntar f1 f2 (mapDib f d1) (mapDib f d2)
mapDib f (Encimar d1 d2) = Encimar (mapDib f d1) (mapDib f d2)


-- Funcion de fold para Dibujos a
foldDib :: (a -> b) -> (b -> b) -> (b -> b) -> (b -> b) ->
       (Float -> Float -> b -> b -> b) -> 
       (Float -> Float -> b -> b -> b) -> 
       (b -> b -> b) ->
       Dibujo a -> b
foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar (Basica x) = 
    fBasica x
foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar (Rotar a) = 
    fRotar (foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar a) 
foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar (Rotar45 a) = 
    fRotar45 (foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar a) 
foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar (Espejar a) = 
    fEspejar (foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar a)  
foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar (Apilar f1 f2 a b) = 
    fApilar f1 f2 (foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar a) 
                  (foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar b)  
foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar (Juntar f1 f2 a b) = 
    fJuntar f1 f2 (foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar a) 
                  (foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar b)
foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar (Encimar a b) = 
    fEncimar (foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar a) 
             (foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar b)
