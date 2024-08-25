module Mmhelloworld.IdrisSpringBootExample.PayrollApp

import Control.App
import Org.Springframework.Context
import Org.Springframework.Data.Jpa.Repository
import Mmhelloworld.IdrisSpringBootExample.PayrollStore
import System.FFI
import Java.Lang
import Java.Util

%export """
  jvm:import
  io/github/mmhelloworld/helloworld/EmployeeService
"""
jvmImports : List String
jvmImports = []

%export """
        jvm:public EmployeeService
        """
public export
EmployeeService : Type
EmployeeService = Struct "io/github/mmhelloworld/helloworld/EmployeeService" []

public export
%export """
  jvm:public static saveEmployee
  {
    "enclosingType": "EmployeeService",
    "arguments": [
      {"type": "EmployeeRepository"},
      {"type": "Employee"}
    ],
    "returnType": "Employee"
  }
"""
saveEmployee : EmployeeRepository => Employee -> IO Employee
saveEmployee employee =
  save {entity=Employee} {id=Int64} (subtyping (the EmployeeRepository %search)) employee

public export
%export """
  jvm:public static getEmployees
  {
    "enclosingType": "EmployeeService",
    "arguments": [
      {"type": "EmployeeRepository"}
    ],
    "returnType": "List<Employee>"
  }
"""
getEmployees : EmployeeRepository => IO (JList Employee)
getEmployees =
  findAll {id=Int64} (subtyping (the EmployeeRepository %search))

export
%export """
  jvm:public static initDatabase
  {
    "enclosingType": "EmployeeService",
    "arguments": [
      {"type": "EmployeeRepository"}
    ],
    "returnType": "void"
  }
"""
initDatabase : EmployeeRepository => IO ()
initDatabase = do
    ignore $ saveEmployee $ Employee.new "Bilbo Baggins" "burglar"
    ignore $ saveEmployee $ Employee.new "Frodo Baggins" "thief"
