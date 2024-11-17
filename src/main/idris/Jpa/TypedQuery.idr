module Jpa.TypedQuery

import public Java.Util

%export """
  jvm:import
  jakarta/persistence/TypedQuery
"""
jvmImports : List String
jvmImports = []

export
data TypedQuery : Type -> Type where [external]

export
%foreign "jvm:.getResultList(TypedQuery java/util/List),TypedQuery"
(.getResultList) : TypedQuery x -> IO (JList x)
