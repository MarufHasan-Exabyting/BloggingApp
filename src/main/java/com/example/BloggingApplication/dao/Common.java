package com.example.BloggingApplication.dao;

import com.example.BloggingApplication.model.EntityMetadata;

import java.util.Date;

public class Common {
    public static <Property, Parameter> String getDynamicQuery(Class<?> table, Property property, Parameter parameter)
    {
        System.out.println(table.getSimpleName());
        String query = "From " + table.getSimpleName() + " where " + property + " = " + parameter + " and metadata.isDeleted = false";
        System.out.println("Dynamic Query " + query);
        return  query;
    }

    public static EntityMetadata getEntityMetadata(Date createdAt, Date updatedAt)
    {
        EntityMetadata entityMetadata = new EntityMetadata();
        entityMetadata.setCreatedAt(createdAt);
        entityMetadata.setUpdatedAt(updatedAt);
        return entityMetadata;
    }
}
