module Mmhelloworld.IdrisSpringBootExample.Main

import Java.Lang
import Java.Util
import Org.Springframework.Context
import Org.Springframework.Boot
import System.FFI
import System
import Control.App

import Mmhelloworld.IdrisSpringBootExample.PayrollStore
import Mmhelloworld.IdrisSpringBootExample.PayrollApp

%export
    """
    jvm:import
    io/github/mmhelloworld/helloworld/Employee
    io/github/mmhelloworld/helloworld/EmployeeController
    io/github/mmhelloworld/helloworld/EmployeeRepository
    io/github/mmhelloworld/helloworld/PayrollConfiguration
    io/github/mmhelloworld/helloworld/PayrollApplication
    org/springframework/boot/SpringApplication
    org/springframework/boot/autoconfigure/SpringBootApplication
    org/springframework/context/annotation/Bean
    org/springframework/stereotype/Component
    java/util/List
    """
jvmImports : List String
jvmImports = []

-- Spring boot main class
namespace PayrollApplication
    %export """
            jvm:public PayrollApplication
            {
                "annotations": [
                    {"NoArgsConstructor": {}},
                    {
                        "SpringBootApplication": {
                            "scanBasePackages": [
                                "io.github.mmhelloworld.idrisspringboot",
                                "io.github.mmhelloworld.helloworld"
                            ]
                        }
                    }
                ]
            }
            """
    public export
    PayrollApplication : Type
    PayrollApplication = Struct "io/github/mmhelloworld/helloworld/PayrollApplication" []

main : IO ()
main = do
    args <- Arrays.fromList String !getArgs
    ignore $ fromPrim $ run PayrollApplication args
