package io.ermdev.cshop.rest.role;

import io.ermdev.cshop.data.entity.Role;
import io.ermdev.cshop.commons.Error;
import io.ermdev.cshop.data.service.RoleService;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.mapfierj.SimpleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Path("roles")
public class RoleResource {

    private RoleService roleService;
    private SimpleMapper simpleMapper;

    @Autowired
    public RoleResource(RoleService roleService, SimpleMapper simpleMapper) {
        this.roleService = roleService;
        this.simpleMapper = simpleMapper;
    }

    @GET
    @Path("{roleId}")
    public Response getById(@PathParam("roleId") Long roleId, @Context UriInfo uriInfo) {
        try {
            RoleDto roleDto = simpleMapper.set(roleService.findById(roleId)).mapTo(RoleDto.class);
            RoleResourceLinks roleResourceLinks = new RoleResourceLinks(uriInfo);
            roleDto.getLinks().add(roleResourceLinks.getSelf(roleId));
            return Response.status(Response.Status.FOUND).entity(roleDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @GET
    public Response getById(@Context UriInfo uriInfo) {
        try {
            List<RoleDto> roleDtos = simpleMapper.set(roleService.findAll()).mapToList(RoleDto.class);
            roleDtos.parallelStream().forEach(roleDto -> {
                RoleResourceLinks roleResourceLinks = new RoleResourceLinks(uriInfo);
                roleDto.getLinks().add(roleResourceLinks.getSelf(roleDto.getId()));
            });
            return Response.status(Response.Status.FOUND).entity(roleDtos).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }
    }

    @POST
    public Response add(RoleDto roleDto, @Context UriInfo uriInfo) {
        try {
            Role role = roleService.save(simpleMapper.set(roleDto).mapTo(Role.class));
            RoleResourceLinks roleResourceLinks = new RoleResourceLinks(uriInfo);
            roleDto.setId(role.getId());
            roleDto.getLinks().add(roleResourceLinks.getSelf(roleDto.getId()));
            return Response.status(Response.Status.OK).entity(roleDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    @PUT
    @Path("{roleId}")
    public Response update(@PathParam("roleId") Long roleId, RoleDto roleDto, @Context UriInfo uriInfo) {
        try {
            roleDto.setId(roleId);
            Role role = roleService.save(simpleMapper.set(roleDto).mapTo(Role.class));
            RoleResourceLinks roleResourceLinks = new RoleResourceLinks(uriInfo);
            roleDto = simpleMapper.set(role).mapTo(RoleDto.class);
            roleDto.getLinks().add(roleResourceLinks.getSelf(roleDto.getId()));
            return Response.status(Response.Status.OK).entity(roleDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    @DELETE
    @Path("{roleId}")
    public Response delete(@PathParam("roleId") Long roleId, @Context UriInfo uriInfo) {
        try {
            RoleDto roleDto = simpleMapper.set(roleService.delete(roleId)).mapTo(RoleDto.class);
            RoleResourceLinks roleResourceLinks = new RoleResourceLinks(uriInfo);
            roleDto.getLinks().add(roleResourceLinks.getSelf(roleId));
            return Response.status(Response.Status.OK).entity(roleDto).build();
        } catch (EntityException e) {
            Error error = new Error(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }
}
