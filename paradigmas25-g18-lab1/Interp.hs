-- Sacar del esqueleto final!
module Interp where
import Graphics.Gloss
import Graphics.Gloss.Data.Vector
import qualified Graphics.Gloss.Data.Point.Arithmetic as V

import Dibujo

-- Gloss provee el tipo Vector y Picture.
type ImagenFlotante = Vector -> Vector -> Vector -> Picture
type Interpretacion a = a -> ImagenFlotante

mitad :: Vector -> Vector
mitad = (0.5 V.*)

-- Interpretaciones de los constructores de Dibujo

--interpreta el operador de rotacion
interp_rotar :: ImagenFlotante -> ImagenFlotante
interp_rotar f d w h = f (d V.+ w) (h) (V.negate w)


--interpreta el operador de espejar
interp_espejar :: ImagenFlotante -> ImagenFlotante
interp_espejar f d w h = f (d V.+ w) (V.negate w) (h)


--interpreta el operador de rotacion 45
interp_rotar45 :: ImagenFlotante -> ImagenFlotante
interp_rotar45 f d w h = f (d V.+ (mitad(w V.+ h))) (mitad(w V.+ h)) (mitad(h V.- w))

r'' :: Float -> Float -> Float
r'' m n = n/(m + n) 

r' :: Float -> Float -> Float
r' m n = m/(m + n) 

v' :: Float -> Vector -> Vector
v' f v = mulSV f v


--interpreta el operador de apilar
interp_apilar :: Float -> Float -> ImagenFlotante -> ImagenFlotante -> ImagenFlotante
interp_apilar m n f g d w h = Pictures[f (d V.+ (v' (r'' m n) h)) (w) (v' (r' m n) h), 
                                       g d w (v' (r'' m n) h)]


--interpreta el operador de juntar
interp_juntar :: Float -> Float -> ImagenFlotante -> ImagenFlotante -> ImagenFlotante
interp_juntar m n f g d w h = Pictures[f d (v' (r' m n) w) h, 
                                       g (d V.+ (v' (r' m n) w)) (v' (r'' m n) w) h]


--interpreta el operador de encimar
interp_encimar :: ImagenFlotante -> ImagenFlotante -> ImagenFlotante
interp_encimar f g d w h = Pictures[f d w h, g d w h]


--interpreta cualquier expresion del tipo Dibujo a
--utilizar foldDib 
interp :: Interpretacion a -> Dibujo a -> ImagenFlotante
interp i d = foldDib (\x -> (i x)) 
                     interp_rotar 
                     interp_rotar45 
                     interp_espejar 
                     interp_apilar 
                     interp_juntar 
                     interp_encimar d

