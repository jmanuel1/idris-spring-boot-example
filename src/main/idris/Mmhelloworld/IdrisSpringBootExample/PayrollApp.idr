module Mmhelloworld.IdrisSpringBootExample.PayrollApp

import Control.App
import Org.Springframework.Context
import Mmhelloworld.IdrisSpringBootExample.PayrollStore
import System.FFI
import Java.Lang
import Java.Util

%export """
  jvm:import
  io/github/mmhelloworld/helloworld/EmployeeRepositoryBean
  io/github/mmhelloworld/helloworld/EmployeeService
"""
jvmImports : List String
jvmImports = []

namespace EmployeeService
  %export """
          jvm:public EmployeeService {}
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
        {"type": "EmployeeRepositoryBean"},
        {"type": "Employee"}
      ],
      "returnType": "Employee"
    }
  """
  saveEmployee : EmployeeRepositoryBean => Employee -> IO Employee
  saveEmployee employee = do
    _ <- entityManager
    save employee

  public export
  %export """
    jvm:public static getEmployees
    {
      "enclosingType": "EmployeeService",
      "arguments": [
        {"type": "EmployeeRepositoryBean"}
      ],
      "returnType": "List<Employee>"
    }
  """
  getEmployees : EmployeeRepositoryBean => IO (JList Employee)
  getEmployees = do
    _ <- entityManager
    findAll

  export
  %export """
    jvm:public static initDatabase
    {
      "enclosingType": "EmployeeService",
      "arguments": [
        {"type": "EmployeeRepositoryBean"}
      ],
      "returnType": "void"
    }
  """
  initDatabase : EmployeeRepositoryBean => IO ()
  initDatabase = do
      ignore $ saveEmployee $ Employee.new "Bilbo Baggins" "burglar"
      ignore $ saveEmployee $ Employee.new "Frodo Baggins" "thief"
