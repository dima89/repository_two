package com.tsi.collector;

import com.tsi.entity.Comment;
import com.tsi.entity.Document;
import com.tsi.entity.DocumentBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;
import java.util.stream.Collectors;

@Path("/")
public class Collector
{
    static List<Document> documentList;

    static {
        documentList = populateDocuments();
    }

    @Path("getAllDocuments")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Document> getAllDocuments()
    {
        return documentList;
    }

    @Path("add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDocument(Document document)
    {
        documentList.add(document);
        System.out.println("Created document with id " + document.getId());

        return Response.status(201).entity(document).build();
    }

    @Path("get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getDocumentById(@PathParam("id") String id, @Context UriInfo uriInfo)
    {
       System.out.println("Fetching document with id " + id);

       Document response = documentList.stream()
                .filter(document -> id.equals(document.getId().toString()))
                .findAny()
                .orElse(null);

       if (response != null) {
            System.out.println("Found document with id " + response.getId());
       }

        return Response.status(200).entity(response).location(uriInfo.getBaseUri()).build();
    }

    @Path("update")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDocument(Document documentToUpdate)
    {
        System.out.println("Updating document with id " + documentToUpdate.getId());

        documentList = documentList.stream()
                .filter(document -> !document.getId().equals(documentToUpdate.getId()))
                .collect(Collectors.toList());

        documentList.add(documentToUpdate);

        System.out.println("Updated document with id " + documentToUpdate.getId());

        return Response.status(200).entity(documentToUpdate).build();
    }

    @Path("delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteDocument(@PathParam("id") String id)
    {
        System.out.println("Removing document with id " + id);

        documentList = documentList.stream()
                .filter(document -> !id.equals(document.getId().toString()))
                .collect(Collectors.toList());

        System.out.println("Removed document with id " + id);

        return Response.status(204).build();
    }

    private static List<Document> populateDocuments(){
        List<Document> documentList = new ArrayList<Document>();

        Document documentFour = new DocumentBuilder()
                .setId(UUID.fromString("664a8fa0-312c-48ee-9863-dcb88ca0c50b"))
                .setName("Four name doc")
                .setContent("Four content doc")
                .setTitle("Four title doc")
                .setIndexMap("index one", "index two")
                .setComments("Four coment doc")
                .build();

        System.out.println("Created document with id " + documentFour.getId());

        Document documentFive = new DocumentBuilder()
                .setId(UUID.fromString("d88c925f-a8ec-45f0-836b-0a3e6bb621d1"))
                .setName("Five name doc")
                .setContent("Five content doc")
                .setTitle("Five title doc")
                .setIndexMap("index one", "index two")
                .setComments("Five coment doc")
                .build();

        System.out.println("Created document with id " + documentFive.getId());

        documentList.add(documentFour);
        documentList.add(documentFive);

        return documentList;
    }

}