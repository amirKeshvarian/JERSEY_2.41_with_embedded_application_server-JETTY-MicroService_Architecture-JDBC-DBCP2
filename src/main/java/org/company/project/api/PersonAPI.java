package org.company.project.api;

import org.company.project.common.annotation.TestURL;
import org.company.project.common.wrapper.ErrorHandler;
import org.company.project.model.domain.Person;
import org.company.project.model.service.PersonService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Objects;


@Path("/person")
public class PersonAPI {
    @TestURL(url = "http://localhost:9090/api/person/save.do?name=amir&family=keshvarian")
    @Path("/save.do")
    @GET
    @Produces("application/json")
    public Response savePerson (@QueryParam("name") String name, @QueryParam("family") String family){
        try {
            if (Objects.isNull(name)){
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("the name field is empty")
                        .build();
            }
            if (Objects.isNull(family)){
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("the family field is empty")
                        .build();
            }
            PersonService.getInstance().save(new Person(name,family));
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorHandler.getError(e))
                    .build();
        }
    }
}
