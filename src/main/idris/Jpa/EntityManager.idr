module Jpa.EntityManager

import public Java.Lang
import public Jpa.TypedQuery

%export """
  jvm:import
  jakarta/persistence/EntityManager
  jakarta/persistence/EntityTransaction
"""
jvmImports : List String
jvmImports = []

export
data EntityManager : Type where [external]

-- export
-- data EntityTransaction : Type where [external]
--
-- %foreign "jvm:.getTransaction(EntityManager EntityTransaction),EntityManager"
-- transaction : EntityManager => IO EntityTransaction
--
-- %foreign "jvm:.begin(EntityTransaction void),EntityTransaction"
-- beginTransaction : EntityTransaction => IO ()
--
-- %foreign "jvm:.commit(EntityTransaction void),EntityTransaction"
-- commitTransaction : EntityTransaction => IO ()

export
%foreign "jvm:.persist(EntityManager java/lang/Object void),EntityManager"
persist : EntityManager => _ -> IO ()

%foreign "jvm:.createQuery(EntityManager java/lang/String java/lang/Class),TypedQuery"
prim__createQuery : EntityManager => String -> Class t -> IO (TypedQuery t)

export
createQuery : {t : Type} -> EntityManager => String -> IO (TypedQuery t)
createQuery query = prim__createQuery query classLiteral
