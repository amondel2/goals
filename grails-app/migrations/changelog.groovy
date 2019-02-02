databaseChangeLog = {

    changeSet(author: "aaron (generated)", id: "1549077868479-1") {
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
        createTable(tableName: "employee_goal_type_employee_goal_comment") {
            column(name: "employee_goal_type_comments_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "employee_goal_comment_id", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-8") {
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
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "company")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-15") {
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "emp_location")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-16") {
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "employee_boss")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-17") {
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "employee_goal")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-18") {
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "employee_goal_comment")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-19") {
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "employee_goal_type")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-20") {
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "employees")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-21") {
        addPrimaryKey(columnNames: "id", constraintName: "PRIMARY", tableName: "goal_type")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-22") {
        addPrimaryKey(columnNames: "user_id, role_id", constraintName: "PRIMARY", tableName: "user_role")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-23") {
        addUniqueConstraint(columnNames: "company_id, employee_id", constraintName: "UK1a98cab54bdaff149e70fa37fba4", tableName: "employees")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-24") {
        addUniqueConstraint(columnNames: "boss_id, employee_id", constraintName: "UK1ef6c469bcf7b4ce5fa4c0f36556", tableName: "employee_boss")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-25") {
        addUniqueConstraint(columnNames: "employee_id, boss_id, default_boss", constraintName: "UK3137904eba91100d41b48c07dd17", tableName: "employee_boss")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-26") {
        addUniqueConstraint(columnNames: "employee_goal_id, type_id", constraintName: "UK4c3d1bf903bf988378d11d76ed57", tableName: "employee_goal_type")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-27") {
        addUniqueConstraint(columnNames: "location, company_id, geo", constraintName: "UK985c693291f854af421adbd1f83b", tableName: "emp_location")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-28") {
        addUniqueConstraint(columnNames: "authority", constraintName: "UK_irsamgnera6angm0prq1kemt2", tableName: "role")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-29") {
        addUniqueConstraint(columnNames: "name", constraintName: "UK_niu8sfil2gxywcru9ah3r4ec5", tableName: "company")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-30") {
        addUniqueConstraint(columnNames: "title", constraintName: "UK_pjhi5494kakw0o4beghsbfqs2", tableName: "goal_type")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-31") {
        addUniqueConstraint(columnNames: "username", constraintName: "UK_sb8bbouer5wak8vyiiy4pf2bx", tableName: "user")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-32") {
        addUniqueConstraint(columnNames: "employee_id, title", constraintName: "UKc28809272a51e237db4a6222e99f", tableName: "employee_goal")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-33") {
        createIndex(indexName: "FK5a3m3p9rcef22l4yskqsl4afr", tableName: "employee_goal_type_employee_goal_comment") {
            column(name: "employee_goal_type_comments_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-34") {
        createIndex(indexName: "FK8qo1kejmdd2v2m2megs2t0f2f", tableName: "employee_goal_type_employee_goal_comment") {
            column(name: "employee_goal_comment_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-35") {
        createIndex(indexName: "FKa68196081fvovjhkek5m97n3y", tableName: "user_role") {
            column(name: "role_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-36") {
        createIndex(indexName: "FKb3yrxtshdoq3bhspox4emk9w0", tableName: "employee_goal_type") {
            column(name: "type_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-37") {
        createIndex(indexName: "FKetyjmkcus5t0ktpyt7xuhkv25", tableName: "employees") {
            column(name: "location_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-38") {
        createIndex(indexName: "FKgl7ubhxtq9mnlwnmrn93baet8", tableName: "employee_goal_comment") {
            column(name: "employee_goal_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-39") {
        createIndex(indexName: "FKgph7ais5uxsg9qyv486wu989i", tableName: "employees") {
            column(name: "user_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-40") {
        createIndex(indexName: "FKsdu8o8m1mhusn7gtdgatdfdjh", tableName: "emp_location") {
            column(name: "company_id")
        }
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-41") {
        addForeignKeyConstraint(baseColumnNames: "company_id", baseTableName: "employees", constraintName: "FK3dtsl9h3lnbeeqds8rm23cwja", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-42") {
        addForeignKeyConstraint(baseColumnNames: "employee_id", baseTableName: "employee_boss", constraintName: "FK41el11lou0d2jb9hwwbyv7l64", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "employees")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-43") {
        addForeignKeyConstraint(baseColumnNames: "employee_goal_type_comments_id", baseTableName: "employee_goal_type_employee_goal_comment", constraintName: "FK5a3m3p9rcef22l4yskqsl4afr", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "employee_goal_type")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-44") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", constraintName: "FK859n2jvi8ivhui0rl0esws6o", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-45") {
        addForeignKeyConstraint(baseColumnNames: "employee_goal_comment_id", baseTableName: "employee_goal_type_employee_goal_comment", constraintName: "FK8qo1kejmdd2v2m2megs2t0f2f", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "employee_goal_comment")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-46") {
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FKa68196081fvovjhkek5m97n3y", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "role")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-47") {
        addForeignKeyConstraint(baseColumnNames: "boss_id", baseTableName: "employee_boss", constraintName: "FKacbejli1fsa5atad1m01hpw95", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "employees")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-48") {
        addForeignKeyConstraint(baseColumnNames: "type_id", baseTableName: "employee_goal_type", constraintName: "FKb3yrxtshdoq3bhspox4emk9w0", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "goal_type")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-49") {
        addForeignKeyConstraint(baseColumnNames: "location_id", baseTableName: "employees", constraintName: "FKetyjmkcus5t0ktpyt7xuhkv25", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "emp_location")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-50") {
        addForeignKeyConstraint(baseColumnNames: "employee_goal_id", baseTableName: "employee_goal_comment", constraintName: "FKgl7ubhxtq9mnlwnmrn93baet8", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "employee_goal")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-51") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "employees", constraintName: "FKgph7ais5uxsg9qyv486wu989i", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "user")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-52") {
        addForeignKeyConstraint(baseColumnNames: "employee_goal_id", baseTableName: "employee_goal_type", constraintName: "FKocffbrch7ag595us3h0vgh1v8", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "employee_goal")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-53") {
        addForeignKeyConstraint(baseColumnNames: "employee_id", baseTableName: "employee_goal", constraintName: "FKp9oxvu25dbop899yrsxdfckid", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "employees")
    }

    changeSet(author: "aaron (generated)", id: "1549077868479-54") {
        addForeignKeyConstraint(baseColumnNames: "company_id", baseTableName: "emp_location", constraintName: "FKsdu8o8m1mhusn7gtdgatdfdjh", deferrable: "false", initiallyDeferred: "false", onDelete: "NO ACTION", onUpdate: "NO ACTION", referencedColumnNames: "id", referencedTableName: "company")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-1") {
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
        addPrimaryKey(columnNames: "id", constraintName: "kpotypePK", tableName: "kpotype")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-3") {
        addUniqueConstraint(columnNames: "title", constraintName: "UC_KPOTYPETITLE_COL", tableName: "kpotype")
    }

    changeSet(author: "aaron (generated)", id: "1549107324208-4") {
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


}
