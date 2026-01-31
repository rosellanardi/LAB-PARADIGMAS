import Dibujo

type Pred a = a -> Bool

--Para la definiciones de la funciones de este modulo, no pueden utilizar
--pattern-matching, sino alto orden a traves de la funcion foldDib, mapDib 

-- Dado un predicado sobre básicas, cambiar todas las que satisfacen
-- el predicado por el resultado de llamar a la función indicada por el
-- segundo argumento con dicha figura.
-- Por ejemplo, cambiar (== Triangulo) (\x -> Rotar (Basica x)) rota
-- todos los triángulos.
cambiar :: Pred a -> (a -> Dibujo a) -> Dibujo a -> Dibujo a
cambiar  p f d = foldDib (\x -> if p x then f x else (pureDib x))
                         (\x -> Rotar x)
                         (\x -> Rotar45 x)
                         (\x -> Espejar x)
                         (\f1 f2 x y -> Apilar f1 f2 x y)
                         (\f1 f2 x y -> Juntar f1 f2 x y)
                         (\x y -> Encimar x y) d


-- Alguna básica satisface el predicado.
anyDib :: Pred a -> Dibujo a -> Bool
anyDib p d = foldDib (\x -> p x)
                     (\x -> x)
                     (\x -> x)
                     (\x -> x)
                     (\_ _ x y -> x || y)
                     (\_ _ x y -> x || y)
                     (\x y -> x || y)
                     d


-- Todas las básicas satisfacen el predicado.
allDib :: Pred a -> Dibujo a -> Bool
allDib p d = foldDib (\x -> p x)
                     (\x -> x)
                     (\x -> x)
                     (\x -> x)
                     (\_ _ x y -> x && y)
                     (\_ _ x y -> x && y)
                     (\x y -> x && y)
                     d


-- Hay 4 rotaciones seguidas.
esRot360 :: Pred (Dibujo a)
esRot360 d = snd (foldDib (\_ -> (0, False))
                          (\(acc, res)-> if (acc + 1) == 4 then (acc + 1, True) else (acc + 1, res))
                          (\(acc, res) -> (0, res))
                          (\(acc, res) -> (0, res))
                          (\_ _ (acc1, res1) (acc2, res2) -> (0, res1 || res2))
                          (\_ _ (acc1, res1) (acc2, res2) -> (0, res1 || res2))
                          (\(acc1, res1) (acc2, res2) -> (0, res1 || res2)) d)


-- Hay 2 espejados seguidos.
esFlip2 :: Pred (Dibujo a)
esFlip2 d = snd (foldDib (\_ -> (0, False))
                         (\(acc, res)-> (0, res))
                         (\(acc, res) -> (0, res))
                         (\(acc, res) -> if (acc + 1) == 2 then (acc + 1, True) else (acc + 1, res))
                         (\_ _ (acc1, res1) (acc2, res2) -> (0, res1 || res2))
                         (\_ _ (acc1, res1) (acc2, res2) -> (0, res1 || res2))
                         (\(acc1, res1) (acc2, res2) -> (0, res1 || res2)) d) 


data Superfluo = RotacionSuperflua | FlipSuperfluo deriving (Show, Eq)


---- Chequea si el dibujo tiene una rotacion superflua
errorRotacion :: Dibujo a -> [Superfluo]
errorRotacion d | esRot360 d = [RotacionSuperflua]
                | otherwise = []


-- Chequea si el dibujo tiene un flip superfluo
errorFlip :: Dibujo a -> [Superfluo]
errorFlip d | esFlip2 d = [FlipSuperfluo]
            | otherwise = []


-- Aplica todos los chequeos y acumula todos los errores, y
-- sólo devuelve la figura si no hubo ningún error.
checkSuperfluo :: Dibujo a -> Either [Superfluo] (Dibujo a)
checkSuperfluo d | ((errorRotacion d) ++ (errorFlip d) == []) = Right d
                 | otherwise = Left ((errorRotacion d) ++ (errorFlip d))
