package org.company.project.model.repository;

public interface GenericRepository extends AutoCloseable{
    void commit () throws Exception;
}
