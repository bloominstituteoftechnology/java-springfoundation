package com.lambdaschool.foundation.models;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Abstract class that adds standard auditing fields to tables that extend it
 *
 * @MappedSuperclass - Since relational databases do not support inheritance,
 * Spring Boot provides a way to say that this class is a "parent" class whose fields will be included in the child class.
 * The child class is the one that forms the entity for the database.
 * @EntityListeners - When an entity is accessed by the Spring Framework, the argument of the Entity Listener annotation is fired.
 * Thus this annotiation is "listening" for when an entity is accessed and then performs its argument.
 * AuditingEntityListener.class - The class that captures the data for and updates the annotations used in auditing -
 * CreatedBy, CreatedDate, ModifiedBy, ModifiedDate
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class Auditable
{
    /**
     * String field containing the username of who created this row
     */
    @CreatedBy
    protected String createdby;

    /**
     * Date field containing the date and time when the row was created
     * <p>
     * Temporal(TIMESTAMP) - Sets the precision of the date being saved. In this case Date and Time
     */
    @CreatedDate
    @Temporal(TIMESTAMP)
    protected Date createddate;

    /**
     * String field containing the username of who last modified this row
     */
    @LastModifiedBy
    protected String lastmodifiedby;

    /**
     * Date field containing the data and time when the row was last modified
     * <p>
     * Temporal(TIMESTAMP) - Sets the precision of the date being saved. In this case Date and Time
     */
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    protected Date lastmodifieddate;
}