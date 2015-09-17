package ch.protonmail.vladyslavbond.eurobug.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.protonmail.vladyslavbond.eurobug.domain.Account;
import ch.protonmail.vladyslavbond.eurobug.domain.Summary;
import ch.protonmail.vladyslavbond.eurobug.domain.Skill;

public final class InternalSummariesResource
extends SummariesResource
implements Resource
{
    public InternalSummariesResource (Account account)
    {
        super(account);
    }

    @POST
    @Path("/create")
    @Produces(MediaType.TEXT_HTML)
    public Response create ( )
    {
    	// TODO
    	return null;
    }

    @GET
    @Path("/{summary_id : \\d+}")
    @Produces(MediaType.TEXT_HTML)
    public Response retrieve (@PathParam("summary_id") Summary summary)
    {
    	// TODO
    	return null;
    }

    @PUT
    @Path("/{summary_id : \\d+}/update/skill/add")
    @Produces(MediaType.TEXT_HTML)
    public Response updateSkillAdd (@PathParam("summary_id") Summary summary, @FormParam("skill_id") Skill skillToAdd)
    {
        // TODO
        return this.retrieve(summary);
    }

    @PUT
    @Path("/{summary_id : \\d+}/update/skill/remove")
    @Produces(MediaType.TEXT_HTML)
    public Response updateSkillRemove (@PathParam("summary_id") Summary summary, @FormParam("skill_id") Skill skillToRemove)
    {
        // TODO
        return this.retrieve(summary);
    }

    @DELETE
    @Path("/{summary_id : \\d+}/destroy")
    @Produces(MediaType.TEXT_HTML)
    public Response destroy (@PathParam("summary_id") Summary summary)
    {
    	// TODO
    	return null;
    }
}