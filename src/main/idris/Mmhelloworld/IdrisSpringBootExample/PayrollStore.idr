module Mmhelloworld.IdrisSpringBootExample.PayrollStore

import System.FFI
import Java.Lang
import Java.Util
import public Jpa.EntityManager

%export
    """
    jvm:import
    org/springframework/web/bind/annotation/GetMapping Get
    io/github/mmhelloworld/helloworld/Employee
    io/github/mmhelloworld/helloworld/EmployeeRepository
    io/github/mmhelloworld/helloworld/EmployeeRepositoryBean
    io/micronaut/serde/annotation/Serdeable
    com/fasterxml/jackson/annotation/JsonIgnore
    com/fasterxml/jackson/annotation/JsonCreator
    com/fasterxml/jackson/annotation/JsonProperty
    java/util/List
    java/lang/Long
    jakarta/persistence/Column
    jakarta/persistence/Entity
    jakarta/persistence/EntityManager
    jakarta/persistence/Id
    jakarta/persistence/GeneratedValue
    jakarta/persistence/Table
    jakarta/validation/constraints/NotNull
    """
jvmImports : List String
jvmImports = []

%export """
    jvm:public EmployeeRepositoryBean
    {}
    """
public export
EmployeeRepositoryBean : Type
EmployeeRepositoryBean = Struct "io/github/mmhelloworld/helloworld/EmployeeRepositoryBean" []

export
%foreign "jvm:#entityManager(EmployeeRepositoryBean EntityManager),EmployeeRepositoryBean"
entityManager : EmployeeRepositoryBean => IO EntityManager

namespace Employee

    {-
     - Employee database entity with primary key "id" and fields name and role
     -}
    %export """
            jvm:public Employee
            {
                "annotations": [
                    {"Data": {}},
                    {"AllArgsConstructor": {
                        "annotations": [
                            {"JsonCreator": {}}
                        ],
                        "parameterAnnotations": [
                            [{"JsonProperty": "name"}],
                            [{"JsonProperty": "role"}]
                        ],
                        "exclude": ["id"]}},
                    {"NoArgsConstructor": {}},
                    {"Entity": {}}
                ],
                "fields": {
                    "id": {
                        "type": "java/lang/Long",
                        "annotations": [
                            {"Id": {}},
                            {"GeneratedValue": {}}
                        ]
                    },
                    "name": {
                        "type": "String"
                    },
                    "role": {
                        "type": "String"
                    }
                }
            }
            """
    public export
    Employee : Type
    Employee = Struct "io/github/mmhelloworld/helloworld/Employee" []

    export
    %foreign "jvm:<init>"
    new : String -> String -> Employee

namespace EmployeeRepository

    {-
     - Repository to manage employees in database
     -}
    %export """
        jvm:public EmployeeRepository
        {}
        """
    public export
    EmployeeRepository : Type
    EmployeeRepository = Struct "io/github/mmhelloworld/helloworld/EmployeeRepository" []

    export
    %export """
      jvm:public static findAll
      {
        "enclosingType": "EmployeeRepository",
        "arguments": [
          {"type": "EntityManager"}
        ],
        "returnType": "List<Employee>"
      }
    """
    findAll : EntityManager => IO (JList Employee)
    findAll = do
      let qlString = "SELECT * FROM employee"
      query <- the (IO (TypedQuery Employee)) $ createQuery qlString
      query.getResultList

    export
    %export """
      jvm:public static save
      {
        "enclosingType": "EmployeeRepository",
        "arguments": [
          {"type": "EntityManager"},
          {"type": "Employee"}
        ],
        "returnType": "Employee"
      }
    """
    save : EntityManager => Employee -> IO Employee
    save employee = do
      persist employee
      pure employee
