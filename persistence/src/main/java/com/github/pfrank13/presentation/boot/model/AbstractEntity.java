package com.github.pfrank13.presentation.boot.model;

import java.time.LocalDateTime;

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
  private LocalDateTime lastModifiedDateTime;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdDateTime;

  @Version
  private long optimisticLockVersion;



  public abstract ID getId();

  public long getOptimisticLockVersion() {
    return optimisticLockVersion;
  }

  public void setOptimisticLockVersion(final long optimisticLockVersion) {
    this.optimisticLockVersion = optimisticLockVersion;
  }

  public LocalDateTime getLastModifiedDateTime() {
    return lastModifiedDateTime;
  }

  public void setLastModifiedDateTime(final LocalDateTime lastModifiedDateTime) {
    this.lastModifiedDateTime = lastModifiedDateTime;
  }

  public LocalDateTime getCreatedDateTime() {
    return createdDateTime;
  }

  public void setCreatedDateTime(final LocalDateTime createdDateTime) {
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
}
