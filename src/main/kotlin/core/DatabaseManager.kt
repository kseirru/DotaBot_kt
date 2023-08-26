package core

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

class DatabaseManager {
    private val connection: Connection = DriverManager.getConnection("jdbc:duckdb:database.duckdb")
    private val statement: Statement = connection.createStatement()

    fun getStatement() : Statement {
        return this.statement
    }

    fun getConnection() : Connection {
        return this.connection
    }

    fun closeConnection() {
        if(!statement.isClosed) {
            statement.close()
        }
        if(!connection.isClosed) {
            connection.close()
        }
    }
}