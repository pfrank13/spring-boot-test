package com.github.pfrank13.presentation.boot.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author pfrank
 */
@MappedSuperclass
public abstract class AbstractEntity<ID> {
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModifiedDateTime;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDateTime;

  @Version
  private Integer optimisticLockVersion;

  public abstract ID getId();

  public Integer getOptimisticLockVersion() {
    return optimisticLockVersion;
  }

  public void setOptimisticLockVersion(final Integer optimisticLockVersion) {
    this.optimisticLockVersion = optimisticLockVersion;
  }

  public Date getLastModifiedDateTime() {
    return lastModifiedDateTime;
  }

  public void setLastModifiedDateTime(final Date lastModifiedDateTime) {
    this.lastModifiedDateTime = lastModifiedDateTime;
  }

  public Date getCreatedDateTime() {
    return createdDateTime;
  }

  public void setCreatedDateTime(final Date createdDateTime) {
    this.createdDateTime = createdDateTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (!(o instanceof AbstractEntity)) { return false; }

    AbstractEntity<?> that = (AbstractEntity<?>) o;

    return getId().equals(that.getId());
  }

  @Override
  public int hashCode() {
    return getId().hashCode();
  }

  public static <ID> void setDates(final AbstractEntity<ID> entity){
    entity.setLastModifiedDateTime(new Date());
    if(entity.getId() == null) {
      //Set Created
      entity.setCreatedDateTime(new Date());
    }
  }
}
