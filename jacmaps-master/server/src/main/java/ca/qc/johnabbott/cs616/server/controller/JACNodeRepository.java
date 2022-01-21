package ca.qc.johnabbott.cs616.server.controller;

import ca.qc.johnabbott.cs616.server.model.JACNode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "jacnode")
public interface JACNodeRepository extends CrudRepository<JACNode, Long> {

}
