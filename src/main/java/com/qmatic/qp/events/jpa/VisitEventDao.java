package com.qmatic.qp.events.jpa;

import org.springframework.data.repository.CrudRepository;

import com.qmatic.qp.events.jpa.model.EventId;
import com.qmatic.qp.events.jpa.model.VisitEvent;

public interface VisitEventDao extends CrudRepository<VisitEvent, EventId> {

}