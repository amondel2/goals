databaseChangeLog = {

    changeSet(author: "aaron (generated)", id: "1549077868479-1") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                tableExists(tableName: "company")
            }
        }
        createTable(tableName: "company") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-2") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                tableExists(tableName: "emp_location")
            }
        }
        createTable(tableName: "emp_location") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "company_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "location", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "geo", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-3") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                tableExists(tableName: "employee_boss")
            }
        }
        createTable(tableName: "employee_boss") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "boss_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "default_boss", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "employee_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-4") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                tableExists(tableName: "employee_goal")
            }
        }
        createTable(tableName: "employee_goal") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "created_date", type: "datetime(6)") {
                constraints(nullable: "false")
            }

            column(name: "modified_date", type: "datetime(6)") {
                constraints(nullable: "false")
            }

            column(name: "title", type: "VARCHAR(100)") {
                constraints(nullable: "false")
            }

            column(name: "target_complet_date", type: "datetime(6)") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "actual_completed_date", type: "datetime(6)")

            column(name: "employee_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "orgin_target_date", type: "datetime(6)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-5") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                tableExists(tableName: "employee_goal_comment")
            }
        }
        createTable(tableName: "employee_goal_comment") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "created_date", type: "datetime(6)") {
                constraints(nullable: "false")
            }

            column(name: "comment_str", type: "VARCHAR(500)") {
                constraints(nullable: "false")
            }

            column(name: "employee_goal_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-6") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                tableExists(tableName: "employee_goal_type")
            }
        }
        createTable(tableName: "employee_goal_type") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "employee_goal_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "type_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-7") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                tableExists(tableName: "employee_goal_type_employee_goal_comment")
            }
        }
        createTable(tableName: "employee_goal_type_employee_goal_comment") {
            column(name: "employee_goal_type_comments_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "employee_goal_comment_id", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-8") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                tableExists(tableName: "employees")
            }
        }
        createTable(tableName: "employees") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "first_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "rest_token", type: "VARCHAR(255)")

            column(name: "location_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "employee_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "hire_date", type: "datetime(6)")

            column(name: "end_date", type: "datetime(6)")

            column(name: "manager", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "company_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "BIGINT")

            column(name: "last_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "email", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-9") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                tableExists(tableName: "goal_type")
            }
        }
        createTable(tableName: "goal_type") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "title", type: "VARCHAR(100)") {
                constraints(nullable: "false")
            }

            column(name: "is_active", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column( name: "end_date", type: "datetime(6)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-10") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                tableExists(tableName: "registration_code")
            }
        }
        createTable(tableName: "registration_code") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true")
            }

            column(name: "date_created", type: "datetime(6)") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "token", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-11") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                tableExists(tableName: "role")
            }
        }
        createTable(tableName: "role") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "authority", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-12") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                tableExists(tableName: "user")
            }
        }
        createTable(tableName: "user") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "password_expired", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "account_locked", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "password", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "account_expired", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "enabled", type: "BIT(1)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-13") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                tableExists(tableName: "user_role")
            }
        }
        createTable(tableName: "user_role") {
            column(name: "user_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "role_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-14") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                primaryKeyExists(tableName: "company")
            }
        }
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "company")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-15") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                primaryKeyExists(tableName: "emp_location")
            }
        }
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "emp_location")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-16") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                primaryKeyExists(tableName: "employee_boss")
            }
        }
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "employee_boss")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-17") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                primaryKeyExists(tableName: "employee_goal")
            }
        }
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "employee_goal")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-18") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                primaryKeyExists(tableName: "employee_goal_comment")
            }
        }
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "employee_goal_comment")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-19") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                primaryKeyExists(tableName: "employee_goal_type")
            }
        }
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "employee_goal_type")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-20") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                primaryKeyExists(tableName: "employees")
            }
        }
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "employees")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-21") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                primaryKeyExists(tableName: "goal_type")
            }
        }
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "goal_type")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-22") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                primaryKeyExists(tableName: "user_role")
            }
        }
        addPrimaryKey(columnNames: "user_id, role_id", constraintName: "PRIMARY", tableName: "user_role")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-23") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                uniqueConstraintExists(tableName:"employees", columnNames:"company_id, employee_id", constraintName: "UK1a98cab54bdaff149e70fa37fba4")
            }
        }
        addUniqueConstraint(columnNames: "company_id, employee_id", constraintName: "UK1a98cab54bdaff149e70fa37fba4", tableName: "employees")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-24") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                uniqueConstraintExists(tableName:"employee_boss", constraintName: "UK1ef6c469bcf7b4ce5fa4c0f36556")
            }
        }
        addUniqueConstraint(columnNames: "boss_id, employee_id", constraintName: "UK1ef6c469bcf7b4ce5fa4c0f36556", tableName: "employee_boss")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-25") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                uniqueConstraintExists(tableName:"employee_boss", constraintName: "UK3137904eba91100d41b48c07dd17")
            }
        }
        addUniqueConstraint(columnNames: "employee_id, boss_id, default_boss", constraintName: "UK3137904eba91100d41b48c07dd17", tableName: "employee_boss")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-26") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                uniqueConstraintExists(tableName:"employee_goal_type", constraintName: "UK4c3d1bf903bf988378d11d76ed57")
            }
        }
        addUniqueConstraint(columnNames: "employee_goal_id, type_id", constraintName: "UK4c3d1bf903bf988378d11d76ed57", tableName: "employee_goal_type")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-27") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                uniqueConstraintExists(tableName:"emp_location", constraintName: "UK985c693291f854af421adbd1f83b")
            }
        }
        addUniqueConstraint(columnNames: "location, company_id, geo", constraintName: "UK985c693291f854af421adbd1f83b", tableName: "emp_location")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-28") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                uniqueConstraintExists(tableName:"role", constraintName: "UK_irsamgnera6angm0prq1kemt2")
            }
        }
        addUniqueConstraint(columnNames: "authority", constraintName: "UK_irsamgnera6angm0prq1kemt2", tableName: "role")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-29") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                uniqueConstraintExists(tableName:"company", constraintName: "UK_niu8sfil2gxywcru9ah3r4ec5")
            }
        }
        addUniqueConstraint(columnNames: "name", constraintName: "UK_niu8sfil2gxywcru9ah3r4ec5", tableName: "company")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-30") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                uniqueConstraintExists(tableName:"goal_type", constraintName: "UK_pjhi5494kakw0o4beghsbfqs2")
            }
        }
        addUniqueConstraint(columnNames: "title", constraintName: "UK_pjhi5494kakw0o4beghsbfqs2", tableName: "goal_type")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-31") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                uniqueConstraintExists(tableName:"user", constraintName: "UK_sb8bbouer5wak8vyiiy4pf2bx")
            }
        }
        addUniqueConstraint(columnNames: "username", constraintName: "UK_sb8bbouer5wak8vyiiy4pf2bx", tableName: "user")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-32") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                uniqueConstraintExists(tableName:"employee_goal", constraintName: "UKc28809272a51e237db4a6222e99f")
            }
        }
        addUniqueConstraint(columnNames: "employee_id, title", constraintName: "UKc28809272a51e237db4a6222e99f", tableName: "employee_goal")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-33") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                indexExists(tableName:"employee_goal_type_employee_goal_comment", indexName: "FK5a3m3p9rcef22l4yskqsl4afr")
            }
        }
        createIndex(indexName: "FK5a3m3p9rcef22l4yskqsl4afr", tableName: "employee_goal_type_employee_goal_comment") {
            column(name: "employee_goal_type_comments_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-34") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                indexExists(tableName:"employee_goal_type_employee_goal_comment", indexName: "FK8qo1kejmdd2v2m2megs2t0f2f")
            }
        }
        createIndex(indexName: "FK8qo1kejmdd2v2m2megs2t0f2f", tableName: "employee_goal_type_employee_goal_comment") {
            column(name: "employee_goal_comment_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-35") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                indexExists(tableName:"user_role", indexName: "FKa68196081fvovjhkek5m97n3y")
            }
        }
        createIndex(indexName: "FKa68196081fvovjhkek5m97n3y", tableName: "user_role") {
            column(name: "role_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-36") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                indexExists(tableName:"employee_goal_type", indexName: "FKb3yrxtshdoq3bhspox4emk9w0")
            }
        }
        createIndex(indexName: "FKb3yrxtshdoq3bhspox4emk9w0", tableName: "employee_goal_type") {
            column(name: "type_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-37") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                indexExists(tableName:"employees", indexName: "FKetyjmkcus5t0ktpyt7xuhkv25")
            }
        }
        createIndex(indexName: "FKetyjmkcus5t0ktpyt7xuhkv25", tableName: "employees") {
            column(name: "location_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-38") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                indexExists(tableName:"employee_goal_comment", indexName: "FKgl7ubhxtq9mnlwnmrn93baet8")
            }
        }
        createIndex(indexName: "FKgl7ubhxtq9mnlwnmrn93baet8", tableName: "employee_goal_comment") {
            column(name: "employee_goal_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-39") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                indexExists(tableName:"employees", indexName: "FKgph7ais5uxsg9qyv486wu989i")
            }
        }
        createIndex(indexName: "FKgph7ais5uxsg9qyv486wu989i", tableName: "employees") {
            column(name: "user_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-40") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                indexExists(tableName:"emp_location", indexName: "FKsdu8o8m1mhusn7gtdgatdfdjh")
            }
        }
        createIndex(indexName: "FKsdu8o8m1mhusn7gtdgatdfdjh", tableName: "emp_location") {
            column(name: "company_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-41") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(foreignKeyName: "FK3dtsl9h3lnbeeqds8rm23cwja")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "company_id", baseTableName: "employees", constraintName: "FK3dtsl9h3lnbeeqds8rm23cwja", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-42") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(foreignKeyName: "FK41el11lou0d2jb9hwwbyv7l64")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "employee_id", baseTableName: "employee_boss", constraintName: "FK41el11lou0d2jb9hwwbyv7l64", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "employees")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-43") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(foreignKeyName: "FK5a3m3p9rcef22l4yskqsl4afr")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "employee_goal_type_comments_id", baseTableName: "employee_goal_type_employee_goal_comment", constraintName: "FK5a3m3p9rcef22l4yskqsl4afr", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "employee_goal_type")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-44") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(foreignKeyName: "FK859n2jvi8ivhui0rl0esws6o")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", constraintName: "FK859n2jvi8ivhui0rl0esws6o", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-45") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(foreignKeyName: "FK8qo1kejmdd2v2m2megs2t0f2f")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "employee_goal_comment_id", baseTableName: "employee_goal_type_employee_goal_comment", constraintName: "FK8qo1kejmdd2v2m2megs2t0f2f", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "employee_goal_comment")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-46") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(foreignKeyName: "FKa68196081fvovjhkek5m97n3y")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FKa68196081fvovjhkek5m97n3y", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "role")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-47") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(foreignKeyName: "FKacbejli1fsa5atad1m01hpw95")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "boss_id", baseTableName: "employee_boss", constraintName: "FKacbejli1fsa5atad1m01hpw95", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "employees")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-48") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(foreignKeyName: "FKb3yrxtshdoq3bhspox4emk9w0")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "type_id", baseTableName: "employee_goal_type", constraintName: "FKb3yrxtshdoq3bhspox4emk9w0", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "goal_type")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-49") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(foreignKeyName: "FKetyjmkcus5t0ktpyt7xuhkv25")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "location_id", baseTableName: "employees", constraintName: "FKetyjmkcus5t0ktpyt7xuhkv25", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "emp_location")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-50") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(foreignKeyName: "FKgl7ubhxtq9mnlwnmrn93baet8")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "employee_goal_id", baseTableName: "employee_goal_comment", constraintName: "FKgl7ubhxtq9mnlwnmrn93baet8", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "employee_goal")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-51") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(foreignKeyName: "FKgph7ais5uxsg9qyv486wu989i")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "employees", constraintName: "FKgph7ais5uxsg9qyv486wu989i", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-52") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(foreignKeyName: "FKocffbrch7ag595us3h0vgh1v8")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "employee_goal_id", baseTableName: "employee_goal_type", constraintName: "FKocffbrch7ag595us3h0vgh1v8", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "employee_goal")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-53") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(foreignKeyName: "FKp9oxvu25dbop899yrsxdfckid")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "employee_id", baseTableName: "employee_goal", constraintName: "FKp9oxvu25dbop899yrsxdfckid", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "employees")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-54") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(foreignKeyName: "FKsdu8o8m1mhusn7gtdgatdfdjh")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "company_id", baseTableName: "emp_location", constraintName: "FKsdu8o8m1mhusn7gtdgatdfdjh", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-1") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                tableExists(tableName: "kpotype")
            }
        }
        createTable(tableName: "kpotype") {
            column(name: "id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "title", type: "VARCHAR(100)") {
                constraints(nullable: "false")
            }

            column(name: "is_active", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "end_date", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-2") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                primaryKeyExists(tableName: "kpotype")
            }
        }
        addPrimaryKey(columnNames: "id", constraintName: "kpotypePK", tableName: "kpotype")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-3") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                uniqueConstraintExists(tableName: "kpotype", constraintName: "UC_KPOTYPETITLE_COL")
            }
        }
        addUniqueConstraint(columnNames: "title", constraintName: "UC_KPOTYPETITLE_COL", tableName: "kpotype")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-4") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(foreignKeyName: "FK3rnd22pmcvdk8mnmpv2mapfky")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "type_id", baseTableName: "employee_goal_type", constraintName: "FK3rnd22pmcvdk8mnmpv2mapfky", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "kpotype")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-5") {
        dropForeignKeyConstraint(baseTableName: "employee_goal_type_employee_goal_comment", constraintName: "FK5a3m3p9rcef22l4yskqsl4afr")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-6") {
        dropForeignKeyConstraint(baseTableName: "employee_goal_type_employee_goal_comment", constraintName: "FK8qo1kejmdd2v2m2megs2t0f2f")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-7") {
        dropForeignKeyConstraint(baseTableName: "employee_goal_type", constraintName: "FKb3yrxtshdoq3bhspox4emk9w0")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-8") {
        dropForeignKeyConstraint(baseTableName: "employees", constraintName: "FKetyjmkcus5t0ktpyt7xuhkv25")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-9") {
        dropForeignKeyConstraint(baseTableName: "emp_location", constraintName: "FKsdu8o8m1mhusn7gtdgatdfdjh")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-10") {
        dropUniqueConstraint(constraintName: "UK985c693291f854af421adbd1f83b", tableName: "emp_location")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-11") {
        dropUniqueConstraint(constraintName: "UK_pjhi5494kakw0o4beghsbfqs2", tableName: "goal_type")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-12") {
        dropTable(tableName: "emp_location")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-13") {
        dropTable(tableName: "employee_goal_type_employee_goal_comment")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-14") {
        dropTable(tableName: "goal_type")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-15") {
        dropColumn(columnName: "location_id", tableName: "employees")
    }

    changeSet(author: "aaron (generated)", id: "1549936586973-1") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                columnExists(tableName: "employee_goal_comment", columnName: "modified_date")
            }
        }
        addColumn(tableName: "employee_goal_comment") {
            column(name: "modified_date", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1549936586973-2") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                columnExists(tableName: "employee_goal_comment", columnName: "modified_user_id")
            }
        }
        addColumn(tableName: "employee_goal_comment") {
            column(name: "modified_user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1549936586973-3") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                foreignKeyConstraintExists(constraintName: "FKgs1bnqhy41wgeqfxhdpmyp6m9")
            }
        }
        addForeignKeyConstraint(baseColumnNames: "modified_user_id", baseTableName: "employee_goal_comment", constraintName: "FKgs1bnqhy41wgeqfxhdpmyp6m9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user")
    }

    changeSet(author: "aaron (generated)", id: "1550022766413-1") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                columnExists(tableName: "employees", columnName: "show_hidden")
            }
        }
        addColumn(tableName: "employees") {
            column(name: "show_hidden", type: "bit") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "aaron (generated)", id: "1550975129359-1") {
        preConditions(onFail: "MARK_RAN", onError: "WARN") {
            not {
                columnExists(tableName: "kpotype", columnName: "active_start_date")
            }
        }
        addColumn(tableName: "kpotype") {
            column(name: "active_start_date", type: "datetime")
        }
    }

}
