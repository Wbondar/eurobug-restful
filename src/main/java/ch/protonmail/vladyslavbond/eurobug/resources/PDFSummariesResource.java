package ch.protonmail.vladyslavbond.eurobug.resources;

import java.io.File;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import ch.protonmail.vladyslavbond.eurobug.domain.Account;
import ch.protonmail.vladyslavbond.eurobug.domain.Summary;
import ch.protonmail.vladyslavbond.eurobug.utils.NumericIdentificator;

/*import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;*/

public final class PDFSummariesResource
extends SummariesResource
implements Resource
{
    public PDFSummariesResource (Account account)
    {
        super(account);
    }

    /*@POST
    @Path("/create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_HTML)
    public Response create (@FormDataParam("file") InputStream is, @FormDataParam("file") FormDataContentDisposition fileDetail)
    {
        // TODO
        String uploadedFileLocation = "d://uploaded/" + fileDetail.getFileName();

        //writeToFile(is, uploadedFileLocation);

        String output = "File uploaded to : " + uploadedFileLocation;

        return Response.status(200).entity(output).build();
    }*/

    @GET
    @Path("/{summary_id : \\d+}")
    @Produces("application/pdf")
    public Response retrieve (@PathParam("summary_id") NumericIdentificator<Summary> id)
    {
        // TODO
        File file = new File(id.toString( ));

        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", String.format("attachment; filename=%s", id.toString( ).concat(".pdf")));
        return response.build();
    }

    @PUT
    @Path("/{summary_id : \\d+}/update")
    @Produces("application/pdf")
    public Response update (@PathParam("summary_id") NumericIdentificator<Summary> id)
    {
        // TODO
        return this.retrieve(id);
    }

    @DELETE
    @Path("/{summary_id : \\d+}/destroy")
    @Produces(MediaType.TEXT_HTML)
    public Response destroy (@PathParam("summary_id") NumericIdentificator<Summary> id)
    {
    	// TODO
    	return null;
    }
}