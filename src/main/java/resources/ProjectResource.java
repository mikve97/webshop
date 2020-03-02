package resources;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.dropwizard.auth.AuthenticationException;

/**
 * @author Mike van Es
 */
@Path("/project")
@Produces(MediaType.APPLICATION_JSON)
public class ProjectResource {

    private final String apiKey;
    private final String userId;
//    ProjectService pService;

    public ProjectResource(String apiKey, String userId) throws SQLException {
        this.apiKey = apiKey;
        this.userId = userId;
//        this.pService = new ProjectService();
    }

    /**
     * Gets all valid projects registerd at digitalefactuur, if we have them at our trip table it adds the corresponding trips.
     * @author Mike van Es
     * @return Response
     * @throws AuthenticationException 
     */
    @Path("/getAllProject")
    @GET
    public Response getAllProjects(@HeaderParam("Token") String TokenHeaderParam) throws AuthenticationException {
//        List<ProjectModel> projects = this.pService.getProjectsFromApi(this.apiKey, this.userId, TokenHeaderParam);

//        if(projects != null){
//            return Response.ok(projects).build();
//        }else{
//            return Response.ok("No projects found").build();
//        }
        return null;
    }

    /**
     * Set a projectmodel in the service layer
     * @author Mike van Es
     * @param project
     * @throws AuthenticationException 
     */
    @Path("/setProject")
    @POST
    public void setProject(@QueryParam("project") String project, @HeaderParam("Token") String TokenHeaderParam) throws AuthenticationException {
//        this.pService.setJsonProject(project, TokenHeaderParam);
    }

    /**
     * Get method to return the projectmodel if we have any. Returns the projectmodel in JSON format
     * @author Mike van Es
     * @return Response
     * @throws AuthenticationException 
     */
    @Path("/getProject")
    @GET
    public Response getProject(@HeaderParam("Token") String TokenHeaderParam) throws AuthenticationException {
//        String jsonProject = pService.getJsonProject(TokenHeaderParam);
//
//        if(jsonProject == ""){
//            return Response.ok(null).build();
//        }else{
//            return Response.ok(jsonProject).build();
        return null;
//        }
    }

    /**
     * Return a project based on a given projectID
     * @author Mike van Es
     * @return Response
     * @throws AuthenticationException 
     */
    @Path("/getProjectById")
    @GET
    public Response getProjectById(@QueryParam("projectId") int projectId, @HeaderParam("Token") String TokenHeaderParam) throws AuthenticationException {
//        ProjectModel projectModel = pService.getProjectById(projectId, TokenHeaderParam);
//        if(projectModel == null){
//            this.pService.getProjectsFromApi(this.apiKey, this.userId, TokenHeaderParam);
//            // Refetch the projects
//            projectModel = pService.getProjectById(projectId, TokenHeaderParam);
//            // If the projects remain null the project simply didnt exist else we return the new project.
//            if(projectModel == null){
//                return Response.ok(null).build();
//            }else{
//                return Response.ok(projectModel).build();
//            }
//        }else{
//            return Response.ok(projectModel).build();
//        }
        return null;
    }
}