module Org.Springframework.Boot

import System.FFI

import Org.Springframework.Context
import Java.Lang

%export
    """
    jvm:import
    io/micronaut/runtime/Micronaut
    """
jvmImports : List String
jvmImports = []

%foreign "jvm:run,io/micronaut/runtime/Micronaut"
jrun : Class Object -> Array String -> PrimIO ApplicationContext

export %inline
run : Type -> Array String -> PrimIO ApplicationContext
run ty args = jrun (believe_me $ classLiteral {ty=ty}) args


namespace CommandLineRunner

    public export
    CommandLineRunner : Type
    CommandLineRunner = (Struct "org/springframework/boot/CommandLineRunner run" [], Array String -> PrimIO ())
