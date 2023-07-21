package io.micrommer.controller;

import io.micrommer.model.Client;
import io.quarkus.hibernate.orm.panache.*;
import io.quarkus.panache.common.Page;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.bytebuddy.utility.RandomString;
import org.jboss.resteasy.reactive.server.jaxrs.ResponseBuilderImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/clients")
public class ClientController {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public List<String> getClient() {
        final List<Client> clients = Client.listAll();
        return clients.stream().map(PanacheEntity::toString).collect(Collectors.toList());
    }

    @POST
    @Transactional
    public Response createClient(@QueryParam("name") String name) {
        final var client = new Client(Objects.isNull(name) ? RandomString.make(4) : name, Client.Status.ACTIVE, LocalDateTime.now());
        client.persist();
        if (client.isPersistent())
            return ResponseBuilderImpl.ok().build();
        else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/blocked")
    public List<Client> getBlockedClient() {
        List<Client> blockedClient= Client.list("status", Client.Status.BLOCKED);
        return blockedClient;
    }

    @GET
    @Path("/blocked/first-page")
    public List<Client> getBlockedPaginatedClient() {
        List<Client> blockedClient = Client.find("status", Client.Status.BLOCKED).page(new Page(10)).firstPage().list();
        return blockedClient;
    }

    @GET
    @Path("/blocked/in-week")
    public List<Client> getBlockedInWeekClient() {
        return Client.getAllBlockedUserInPeriod(LocalDateTime.now().minusWeeks(1), LocalDateTime.now());
    }
}
