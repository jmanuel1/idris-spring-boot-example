module Micronaut.Jpa.EntityManager

import Jpa.EntityManager

%export """
  jvm:import
  jakarta/persistence/EntityManager
"""
jvmImports : List String
jvmImports = []

export
data EntityManagerWrapper : Type where [external]

export
data EntityManager : Type where [external]

export
%hint
%foreign "jvm:#entityManager(EntityManagerWrapper, EntityManager),EntityManagerWrapper"
entityManager : EntityManagerWrapper => EntityManager
