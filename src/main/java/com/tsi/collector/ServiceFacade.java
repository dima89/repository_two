package com.tsi.collector;

import com.tsi.entity.Document;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

public interface ServiceFacade {
    List<Document> getAllDocuments();

    Response addDocument(Document document);

    Response getDocumentById(String id, UriInfo uriInfo);

    Response updateDocument(Document documentToUpdate);

    Response deleteDocument(String id, UriInfo uriInfo);
}
