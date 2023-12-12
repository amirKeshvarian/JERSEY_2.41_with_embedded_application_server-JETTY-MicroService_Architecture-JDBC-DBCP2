package org.company.project.model.repository;

import org.company.project.common.jdbc.JDBCProvider;
import org.company.project.model.domain.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PersonRepository implements GenericRepository{
    private Connection connection;
    private PreparedStatement preparedStatement;
    public PersonRepository () throws Exception {
        connection = JDBCProvider.getConnection(JDBCProvider.ORCLPDB1);
    }

    public void  insert (Person person) throws Exception {
        person.setRecordVersion(0l);
        preparedStatement =  connection.prepareStatement("insert into person (person_id,name,family,record_version) values (person_seq.nextVal,?,?,?)");
        preparedStatement.setString(1, person.getName());
        preparedStatement.setString(2, person.getFamily());
        preparedStatement.setLong(3, person.getRecordVersion());
        preparedStatement.executeUpdate();
    }

    @Override
    public void commit() throws Exception {
        connection.commit();
    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }
}
